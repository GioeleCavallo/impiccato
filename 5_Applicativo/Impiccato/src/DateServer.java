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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DateServer {
    private static String[] names = {"Franco","Maria","Cicciogamer89","Zanetti","Mark Zucchina"}; 
    private static final int PORT = 9090;
    
    private static  ArrayList<ClientHandler> clients = new ArrayList<>(); 
    private static ExecutorService pool = Executors.newFixedThreadPool(4);

    public static void main(String[] args)  {

    }
    
    public static void go() throws IOException{
        ServerSocket listener = new ServerSocket(PORT);
        
        while(true){
            System.out.println("[SERVER] Waiting for client connection...");
            Socket client = listener.accept();
            System.out.println("[SERVER] Client connected.");
       
            ClientHandler clientThread = new ClientHandler(client, clients);
            clients.add(clientThread);
            pool.execute(clientThread);
        }
    }
    
    public static String getRandomName(){
        String name = names[(int)(Math.random()*names.length)];
        return name;
    }
}
