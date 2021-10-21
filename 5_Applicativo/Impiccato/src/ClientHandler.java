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
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClientHandler implements Runnable {

    private Socket client;
    private BufferedReader in;
    private PrintWriter  out;
    private ArrayList<ClientHandler> clients;
    private static GameHandler gameHandler;
    
    public ClientHandler(Socket clientSocket, ArrayList<ClientHandler> clients) throws IOException{
        this.client = clientSocket; 
        this.clients = clients;
        in = new BufferedReader(new InputStreamReader(client.getInputStream())); 
        out = new PrintWriter(client.getOutputStream(), true );
        gameHandler = new GameHandler();
    }
    
    @Override
    public void run() {
        try{
            while(true){
                System.out.println("Attending reply");
                String request = in.readLine();
                String playerIdentifier =request.substring(request.indexOf("%")+1,request.indexOf("%", request.indexOf("%") + 1));
                String[] playerArray = playerIdentifier.split(",");
                out.printf("[%s] [%s] [%s]",playerArray[0],playerArray[1],playerArray[2]);
                Player player = new Player(playerArray[0],playerArray[1],Integer.parseInt(playerArray[2]));
                
                request = request.substring(request.indexOf("%", request.indexOf("%") + 1)+1,request.length());
                if(request.equals("name")){
                    out.println(DateServer.getRandomName());
                }else if(request.startsWith("say")){
                   int firstSpace = request.indexOf(" ");
                   if(firstSpace != -1){
                       outToAll(request.substring(firstSpace+1), player);
                   }
                }else if(request.equals("create game")){
                    if(isOnAGame(player)){
                        out.print("you are already on a game");
                    }else if(nameTaked(player)){
                        out.println("Your name is already taken, please change it to create a new game");
                    }else {
                        Game gm = new Game();
                        player.setToken(gm.getToken());
                        gm.addPlayer(player);
                        ApplicationHandler.gameHandler.addGame(gm);
                        out.printf("Game created. Token: %s",gm.getToken());
                        out.println("");
                    }
                }else if(request.equals("get token")){
                    if(isOnAGame(player)){
                        out.printf("Token: %s%n",player.getToken());
                    }else if(nameTaked(player)){
                        out.println("Your name is already taken, please change it to create a new game");
                    }else{
                        out.println("you aren't plaing yet");
                    }
                }else if(request.startsWith("changed name,")){
                    request = request.substring(request.indexOf(",")+1,request.length());
                    out.println("request:: "+request);
                    LinkedList<Player> playerOnGame = ApplicationHandler.gameHandler.getPlayers();
                     for (int i = 0; i < playerOnGame.size(); i++) {
                        if(playerOnGame.get(i).getName().equals(request)){
                            playerOnGame.get(i).setName(player.getName());
                        } 
                    }
                }
            }
        }catch(IOException ioe)
        {
            System.err.println("IO Exception in client handler");
            System.err.println(ioe.getStackTrace());
            
        }finally{
            out.close();
            try {
                in.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    private void outToAll(String message,Player from) {
        for(ClientHandler aClient : clients){
            aClient.out.println(from.getName()+": "+message);
        }
    }
    
    public static void main(String[] args) {
        
    }

    private boolean isOnAGame(Player player) {
        LinkedList<Game> games = gameHandler.getGames();
        for (int i = 0; i < games.size(); i++) {
            if(games.get(i).getPlayers().contains(player)){
                return true;
            }
        }
        return false;
    }

    private boolean nameTaked(Player player) {
        LinkedList<Player> playerOnGame = ApplicationHandler.gameHandler.getPlayers();
        for (int i = 0; i < playerOnGame.size(); i++) {
            if(playerOnGame.get(i).getName().equals(player.getName())){
                return true;
            } 
        }
        return false;
    }
}
