/**
 *
 * @author Gioele Cavallo
 * @version 07.10.2021
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.LinkedList;
import javax.swing.JOptionPane;

public class Client {
    
    private static final String SERVER_IP = "127.0.0.1";
    private static final int SERVER_PORT = 9090;
    private static Player player = new Player("Unknow");
    
    
    public static void go() throws IOException { 
        Socket socket = new Socket(SERVER_IP,SERVER_PORT);
        
        ServerConnection serverCon = new ServerConnection(socket);

        BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(socket.getOutputStream(),true);
        
        new Thread(serverCon).start();
        while(true){
            String packet = "%"+player.getName()+","+player.getToken()+","+player.getPoints()+"%";
            System.out.println("> ");
            String command = keyboard.readLine();

            if(command.toLowerCase().equals("quit")){
                break;
            }else if(command.toLowerCase().contains("set name")){
                String copyCommand = command;
                command = "changed name,"+player.getName();
                int firstSpace = copyCommand.indexOf(" ");
                if(firstSpace != -1){
                    player.setName(copyCommand.substring(firstSpace+5, copyCommand.length()));
                    System.out.println("name changed in: "+player.getName());
                }

            }else if(command.toLowerCase().contains("get name")){
                System.out.println(player.getName());
                //command = "";

            }else if(command.toLowerCase().contains("get players")){
                LinkedList<Player> playerOnGame = ApplicationHandler.gameHandler.getPlayers();
                for (int i = 0; i < playerOnGame.size(); i++) {
                    System.out.println(playerOnGame.get(i).getName());
                } 
                                   
            }
            out.println(packet+command);
            
            
        }
        socket.close();
        System.exit(0);
    }
    
    public static void main(String[] args) {
    }
}
