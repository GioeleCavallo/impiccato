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

public class Client {

    private static String SERVER_IP = "127.0.0.1";
    private static int SERVER_PORT = 9090;
    private static Player player = new Player("Unknow");
    
    private PrintWriter out;
    private Socket socket;
    private ServerConnection serverCon;
    
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

    public boolean checkName(String name) {
        return Helper.isValid(name);
    }

    public void changeName() {
        String packet = "%" + player.getName() + "," + player.getToken() + "," + player.getPoints() + "%";
    }

    public static void go() throws IOException {

        System.out.println("Welcome to the client");
        BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));
        boolean isSetted = false;

        // ciclo per settare il nome dell' utente
        while (!isSetted) {
            System.out.println("Please tip a name:");
            System.out.print("> ");
            String name = keyboard.readLine();
            if (Helper.isValid(name)) {
                isSetted = true;
                player.setName(name);
                System.out.println("Hi " + player.getName());
            } else {
                for (Player arr : DateServer.getPlayers()) {
                    if (arr.equals(player)) {
                        System.out.println("this name is already used");
                    }
                }
                System.out.println("Invalid name, please rewrite it.");
            }
        }

        // creazione del socket per la connessione al server
        Socket socket = new Socket(SERVER_IP, SERVER_PORT);

        // creazione della connessione al server tramite socket
        ServerConnection serverCon = new ServerConnection(socket);

        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

        new Thread(serverCon).start();

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
            } else if (command.toLowerCase().contains("get name")) {
                System.out.println(player.getName());
            }
            out.println(packet + command);

        }
        //packet = "%" + player.getName() + "," + player.getToken() + "," + player.getPoints() + "%";
        out.println(packet + "close");
        socket.close();
        System.exit(0);
    }

    public static void main(String[] args) throws IOException {
        Client.go();
    }

    public static void changeIp(String ip) throws InvalidIpException {
        String PATTERN = "^((0|1\\d?\\d?|2[0-4]?\\d?|25[0-5]?|[3-9]\\d?)\\.){3}(0|1\\d?\\d?|2[0-4]?\\d?|25[0-5]?|[3-9]\\d?)$";

        if (ip.matches(PATTERN)) {
            SERVER_IP = ip;
        } else {
            throw new InvalidIpException();
        }
    }

    private Exception InvalidNameException() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private Exception InvalidNameExceptions() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
