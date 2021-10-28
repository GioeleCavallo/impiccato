package game;

/**
 *
 * @author gioelecavallo
 * @version 07.10.2021
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServerConnection implements Runnable {

    private Socket server;
    private Socket client;
    private BufferedReader in;

    public ServerConnection(Socket s) throws IOException {
        server = s;
        in = new BufferedReader(new InputStreamReader(server.getInputStream()));
    }

    @Override
    public void run() {
        try {
            while (true) {
                String serverResponse = in.readLine();
                if (serverResponse == null) {
                    break;
                }
                System.out.println("[SERVER] " + serverResponse);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            try {
                in.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

    }

    /*public static void main(String[] args) {
        
    }*/
}
