package game;

/**
 * Questa classe crea un client che si collegherà al server.
 *
 * @author Gioele Cavallo
 * @version 07.10.2021
 */
import helper.Helper;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.LinkedList;
import javax.swing.JOptionPane;

public class Client {

    private static final String SERVER_IP = "127.0.0.1";
    private static final int SERVER_PORT = 9090;
    private static Player player = new Player("Unknow");

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
}
