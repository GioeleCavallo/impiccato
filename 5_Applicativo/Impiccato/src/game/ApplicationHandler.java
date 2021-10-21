package game;

/**
 *
 * @author gioele.cavallo
 */

import java.io.BufferedReader;
import java.io.IOException;
        
import java.io.InputStreamReader;

public class ApplicationHandler {
    
    
    public static void main(String[] args) throws IOException {
        BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));
        boolean choise = false;
        while(!choise){
            System.out.println("> ");
            String command = keyboard.readLine();
            if(command.equals("s")){
                choise = true;
                DateServer.go();
            }else if(command.equals("c")){
                choise = true;
                Client.go();
            }
        }
    }
}
