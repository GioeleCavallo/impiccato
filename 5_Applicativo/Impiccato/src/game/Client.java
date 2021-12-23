package game;

/**
 * Questa classe crea un client che si collegherà al server.
 *
 * @author Gioele Cavallo
 * @version 07.10.2021
 */
import exceptions.InvalidNameException;
import exceptions.InvalidIpException;
import helper.Helper;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import views.ApplicationView;
import views.panels.GamePanel;

public class Client extends Thread {

    private static String SERVER_IP = "127.0.0.1";
    private static int SERVER_PORT = 9090;
    private static Player player;

    private final ApplicationView JFRAME;
    private ArrayList<String> players;
    private String error = null;
    private PrintWriter out;
    private BufferedReader in;
    private Socket socket;
    private ServerConnection serverCon;

    public String comunication;
    
    private Thread threadConnection;

    public String getError() {
        return this.error;
    }

    public static Player getPlayer() {
        return player;
    }

    public void setPlayers(ArrayList<String> plrs) {
        this.players = plrs;
        if(this.JFRAME.getCurrentPanel() instanceof GamePanel gP){
            gP.refreshTable();
        }
    }

    public ArrayList<String> getPlayers() {
        return this.players;
    }

    public void setError(String error) {
        System.out.println("YEAA");
        this.error = error;
    }

    public void setToken(String token) {
        player.setToken(token);
    }

    public Client(ApplicationView jFrame) throws InvalidNameException {
        this(new Player("Unknow"), jFrame);
    }

    public Client(Player plr, ApplicationView jFrame) {
        this.JFRAME = jFrame;
        try {
            this.openConnection(); // viene aperta la connessione
            try {
                player = new Player(plr.getName());
            } catch (InvalidNameException ex) {}
            String packet = "%" + plr.getName() + "," + plr.getToken() + "," + plr.getPoints() + "%";
            if (Helper.isValid(plr.getName())) {
                packet = "%" + plr.getName() + "," + plr.getToken() + "," + plr.getPoints() + "%";
                this.sendPacket("check " + plr.getName());

                player = plr;

            } else {
                System.out.println("Invalid name, please rewrite it."); 
            }

        } catch (IOException ex) {
            System.out.println("impossible to create Client, maybe the server is busy.");
            System.out.println("try in another time");
        }
    }

    public void openConnection() throws IOException {

        // creazione del socket con il quale avverrà la connessione al server
        this.socket = new Socket(SERVER_IP, SERVER_PORT);

        // creazione della connessione al server tramite socket
        this.serverCon = new ServerConnection(this.socket, this);

        // dichiarazione del output al quale verrano mandati i pacchetti
        this.out = new PrintWriter(this.socket.getOutputStream(), true);

        // dichiarazione del input dal quale vengono presi i comandi del Client
        this.in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));

        // apertura della connessione e messa in funzione
        this.threadConnection = new Thread(this.serverCon);
        this.threadConnection.start();

    }

    public void closeConnection() {
        try {
            this.socket.close();
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * @param pack : il messaggio da mandare al server 
     */
    public void sendPacket(String msg) {
        try {
            // viene aperta la connessione al server
            this.openConnection();
            
            // Viene creato il pacchetto che serve per l'identificazione del player
            String packet = "%" + player.getName() + "," + player.getToken() + "," + player.getPoints() + "%";

            // se il comando é 'quit' allora termina l'applicazione 
            // chiudendo la connessione al socket.
            if (msg.toLowerCase().equals("quit")) {
                this.out.println(packet + "close");
                this.socket.close();
                System.exit(0);
                return;
            } else if (msg.toLowerCase().contains("get name")) { 
                System.out.println(player.getName());
                return;
            }
            
            // viene mandato al printWriter il pacchetto creato
            this.out.println(packet + msg); 
        } catch (IOException ioe) {
            System.out.println("Unable to send the packet");
        }
    }

    /*
    public void startConnection(Player plr) throws IOException, InvalidNameException {
        this.player = plr;
        System.out.println(this.player);
        System.out.println("Welcome to the client");
        //BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));
        Socket socket = new Socket(SERVER_IP, SERVER_PORT);
        ServerConnection serverCon = new ServerConnection(socket);

        this.out = new PrintWriter(socket.getOutputStream(), true);
        this.socket = new Socket(SERVER_IP, SERVER_PORT);

        // creazione della connessione al server tramite socket
        this.serverCon = new ServerConnection(socket);

        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

        new Thread(serverCon).start();
        if (checkName(plr.getName())) {
            try {
                throw InvalidNameException();
            } catch (Exception ex) {
                //Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        DateServer.addPlayer(player);
        System.out.println(DateServer.getClientNumber());
        
    }
     */
    public boolean checkName(String name) {
        return Helper.isValid(name);
    }

    /*public void changeName() {
        String packet = "%" + player.getName() + "," + player.getToken() + "," + player.getPoints() + "%";
    }*/
    //codice per programma al terminale
    /*
    public void go() {

        System.out.println("Welcome to the client");
        BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));
        boolean isSetted = false;
        try {
            // ciclo per settare il nome dell' utente
            while (!isSetted) {
                System.out.println("Please tip a name:");
                System.out.print("> ");
                String name = keyboard.readLine();
                if (Helper.isValid(name)) {
                    isSetted = true;
                    for (Player arr : DateServer.getPlayers()) {
                        if (arr.equals(player)) {
                            isSetted = false;
                            System.out.println("this name is already used");
                        }
                    }
                    if (isSetted) {
                        player.setName(name);
                        System.out.println("Hi " + player.getName());
                    }
                } else {
                    System.out.println("Invalid name, please rewrite it.");
                }
            }
        } catch (IOException ioe) {
            System.out.println("problem in Client read keyboard");
        }

        this.start();
    }
     */
    @Override
    public void run() {
        try {
            BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));

            // creazione del socket per la connessione al server
            //Socket socket = new Socket(SERVER_IP, SERVER_PORT);
            // creazione della connessione al server tramite socket
            //ServerConnection serverCon = new ServerConnection(socket);
            //PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            //new Thread(serverCon).start();
            // creazione del "pacchetto" di dati da trasmettere al ClientHandler con le richieste
            String packet = "%" + player.getName() + "," + player.getToken() + "," + player.getPoints() + "%";
            System.out.println("pack " + packet);

            out.println(packet + "");
            while (true) {
                //packet = "%" + player.getName() + "," + player.getToken() + "," + player.getPoints() + "%";
                System.out.print("> ");
                String command = keyboard.readLine();

                if (command.toLowerCase().equals("quit")) {
                    DateServer.removePlayer(player);
                    break;
                }
                out.println(packet + command);

            }
            //packet = "%" + player.getName() + "," + player.getToken() + "," + player.getPoints() + "%";
            out.println(packet + "close");
            socket.close();
            System.exit(0);
        } catch (IOException ioe) {
            System.out.println("Problem: IOException :(");
        }
    }

    public static void main(String[] args) throws IOException {
        //Client.go();
    }

    public static void changeIp(String ip) throws InvalidIpException {
        String PATTERN = "^((0|1\\d?\\d?|2[0-4]?\\d?|25[0-5]?|[3-9]\\d?)\\.){3}(0|1\\d?\\d?|2[0-4]?\\d?|25[0-5]?|[3-9]\\d?)$";

        if (ip.matches(PATTERN)) {
            SERVER_IP = ip;
        } else {
            throw new InvalidIpException();
        }
    }
}
