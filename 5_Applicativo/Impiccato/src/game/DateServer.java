package game;

/**
 *
 * @author Gioele Cavallo
 * @version 07.09.2021
 */

import java.io.BufferedReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DateServer {
    private static String[] names = {"Franco","Maria","Cicciogamer89","Zanetti","Mark Zucchina"}; 
    private static final int PORT = 9090;
    
    private static ArrayList<ClientHandler> clients = new ArrayList<>(); 
    private static ExecutorService pool = Executors.newFixedThreadPool(4);
    private static ArrayList<Player> players = new ArrayList<>();
    private static GameHandler gameHandler = new GameHandler();
    
    public static void main(String[] args)  {

    }
    
    public static GameHandler getGameHandler(){
        return gameHandler;
    }
    public static int getClientNumber(){
        return clients.size();
    }
    
    public static void addPlayer(Player player){
        players.add(player);
    }
    
    public static void addPlayerOnGame(Player player, String token){
        LinkedList<Game> games = gameHandler.getGames();
        for(Game gm : games){
            if(gm.getToken().equals(token)){
                gm.addPlayer(player);
                return;
            } 
        }

    }
    
    public static void setAdmin(String name, boolean admin){
        for(Player plr : players){
            if(plr.getName().equals(name)){
                plr.setAdmin(admin);
                return;
            }
        }
    }
    
    public static void addGame(Game game){
        gameHandler.addGame(game);
    }
    
    public static void removeGame(Game game){
        gameHandler.removeGame(game);
    }
    
    public static void removePlayer(Player player){
        players.remove(player);
    }
    
     public static void removeHandler(ClientHandler handler){
        clients.remove(handler);
    }
    
    public static Player getPlayer(Player plr){
        for (Player player : players) {
            if(player.equals(plr)){
                return player;
            }
        }
        return null;
    }
    
    public static ArrayList<Player> getPlayers(){
        return players;
    }
    
    public static void go() throws IOException{
        
        System.out.println("Welcome to the server");
        ServerSocket listener = new ServerSocket(PORT);
        
        while(true){
            System.out.println("[SERVER] Waiting for client connection...");
            Socket client = listener.accept();
            System.out.println("[SERVER] Client connected.");
       
            ClientHandler clientThread = new ClientHandler(client, clients);
            clients.add(clientThread);
            pool.execute(clientThread);
            System.out.println("Players: " + clients.size());
            
        }
    }
    
    public static String getRandomName(){
        String name = names[(int)(Math.random()*names.length)];
        return name;
    }
}
