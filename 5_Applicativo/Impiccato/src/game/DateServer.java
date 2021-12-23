package game;

/**
 * Questa classe é il server al quale i client si collegano e fanno le richieste
 *
 * @author Gioele Cavallo
 * @version 07.09.2021
 */
import java.net.ServerSocket;
import java.net.Socket;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DateServer {

    private static String[] names = {"Franco", "Maria", "Cicciogamer89", "Zanetti", "Mark Zucchina"};
    private static final int PORT = 9090;

    private static ArrayList<ClientHandler> clients = new ArrayList<>();
    private static ExecutorService pool = Executors.newFixedThreadPool(4);
    private static ArrayList<Player> players = new ArrayList<>();
    private static GameHandler gameHandler = new GameHandler();

    public static ArrayList<ClientHandler> getClientHandler() {
        return clients;
    }

    public static void endRoundInfo(String token) {
        Game gm = gameHandler.getGameFromToken(token);
        List<Player> plrs = gm.getPlayers();
        for (Player plr : plrs) {
            plr.setFinished(false);
            plr.deleteGuessedChar();
        }
    }

    public static GameHandler getGameHandler() {
        return gameHandler;
    }

    public static int getClientNumber() {
        return clients.size();
    }

    public static void addPlayer(Player player) /*throws InvalidNameException*/ {
        /*for (Player plr : players) {
            if (plr.getName().equals(player.getName())) {
                throw new InvalidNameException();
            }
        }*/
        players.add(player);
    }

    public static void addPlayerOnGame(Player player, String token) {
        LinkedList<Game> games = gameHandler.getGames();
        for (Game gm : games) {
            if (gm.getToken().equals(token)) {
                gm.addPlayer(player);
                return;
            }
        }

    }

    public static void setAdmin(String name, boolean admin) {
        for (Player plr : players) {
            if (plr.getName().equals(name)) {
                plr.setAdmin(admin);
                return;
            }
        }
    }

    public static void addGame(Game game) {
        gameHandler.addGame(game);
    }

    public static void removeGame(Game game) {
        gameHandler.removeGame(game);
    }

    public static void removePlayer(Player player) {
        player = getPlayer(player);
        players.remove(player);
        removePlayerFromGame(player);
    }

    public static void removePlayerFromGame(Player plr) {
        for (Game gm : gameHandler.getGames()) {
            if (gm.getToken().equals(plr.getToken())) {
                plr = getPlayer(plr);
                gm.removePlayer(plr);
                plr.setAdmin(false);
                plr.setIsOnGameStarted(false);
                plr.setErrors(0);
                plr.getGuessedChars().removeAll(plr.getGuessedChars());
                return;
            }
        }
    }

    public static void removeHandler(ClientHandler handler) {
        clients.remove(handler);
    }

    public static Player getPlayer(Player plr) {
        for (Player player : players) {
            if (player.equals(plr)) {
                return player;
            }
        }
        return null;
    }

    public static ArrayList<Player> getPlayers() {
        return players;
    }

    public static void go() throws IOException {
        System.out.println("Welcome to the server");
        
        // apre la porta alla quale si collegheranno i client
        ServerSocket listener = new ServerSocket(PORT);

        while (true) {
            try {
                // aspetta che si colleghi un client
                System.out.println("[SERVER] Waiting for client connection...");
                
                Socket client = listener.accept();
                System.out.println("[SERVER] Client connected.");

                // crea un ClientHandler per il client appena connesso
                ClientHandler clientThread = new ClientHandler(client, clients);
                clients.add(clientThread);
                try {
                    pool.execute(clientThread);
                    
                } catch (IllegalArgumentException iae) {                   
                }
                
                // debugging per sapere quante connessioni ci sono
                System.out.println("connections: " + clients.size());
            } catch (IllegalArgumentException iae) {
            }
        }
    }

    public static String getRandomName() {
        String name = names[(int) (Math.random() * names.length)];
        return name;
    }

    public static void main(String[] args) throws IOException {
        //DateServer.go();
    }
}
