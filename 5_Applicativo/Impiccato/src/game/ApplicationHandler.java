package game;

/**
 *
 * @author gioele.cavallo
 */
import exceptions.InvalidNameException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import views.ApplicationView;

public class ApplicationHandler {

    public static void main(String[] args) throws IOException, InvalidNameException {
        
  
        BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));
        boolean choise = false;
        System.out.println("print 's' to start server or 'c' to start client");
        while (!choise) {       
            
            System.out.println("> ");
            String command = keyboard.readLine();
            if (command.equals("s")) {
                choise = true;
                DateServer.go();
            } else if (command.equals("c")) {
                choise = true;
                ApplicationView.go();
            }
        }
    }
}
