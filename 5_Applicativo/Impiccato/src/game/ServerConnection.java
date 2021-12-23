package game;

/**
 * Questa classe crea la connessione al server tramite socket.
 *
 * @author gioelecavallo
 * @version 07.10.2021
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;

public class ServerConnection implements Runnable {

    private Socket server;
    private Socket client;
    private BufferedReader in;
    private Client c;

    /**
     * @param s : socket del server al quale connettersi
     * @param clientC : il client che apre questa connessione
     * @throws IOException : in caso la connessione non fosse possibile
     *
     */
    public ServerConnection(Socket s, Client clientC) throws IOException {
        server = s;
        in = new BufferedReader(new InputStreamReader(server.getInputStream()));
        c = clientC;
    }

    @Override
    public void run() {
        try {
            while (true) {
                // aspetta che gli si venga mandato un messaggio
                String serverResponse = in.readLine();
                if (serverResponse == null) {
                    break;
                }

                // stampa nella sua console cosa viene mandato per debugging
                System.out.println("[SERVER] " + serverResponse);

                // elabora il messaggio mandatogli
                elaborateServerResponse(serverResponse);
            }
        } catch (IOException ex) {
        } finally {
            try {
                in.close();
            } catch (IOException ex) {
            }
        }

    }

    /**
     * @param response : la risposta mandatagli dal ClientHandler
     */
    private void elaborateServerResponse(String response) {

        if (c.getPlayer().isOnGameStarted()) {
            System.out.println("recived letter: " + response);
            if (response.startsWith("r")) { //r == right
                c.comunication = response.substring(1, response.length());

            } else if (response.startsWith("f")) { // false
                c.comunication = "false";
            }
        } else if (response.startsWith("InvalidNameException")) {
            c.setError("used"); // se il nome é già utilizzato
        } else if (response.startsWith("token not valid")) {
            c.setError("token not valid"); // se la partita non esiste
        } else if (response.startsWith("set ")) {
            String token = response.split(" ")[1];
            c.setToken(token);
        } else if (response.startsWith("refresh ")) {
            String players = response.split(" ")[1];
            int count = 0;
            for (int i = 0; i < players.length(); i++) {
                if (players.charAt(i) == '%') {
                    count++;
                }
            }
            ArrayList<String> plrs = new ArrayList<String>();
            String[] arr = response.split("%");
            for (int i = 0; i < count / 2; i += 3) {
                plrs.add(arr[i] + arr[i + 1] + arr[i + 2]);
            }
            c.setPlayers(plrs);
        }
    }

    /*public static void main(String[] args) {
        
    }*/
}
