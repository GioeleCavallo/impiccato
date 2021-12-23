package game;

/**
 *
 * @author gioele.cavallo
 */
import exceptions.InvalidNameException;
import java.io.IOException;

public class ApplicationHandler {

    public static void main(String[] args) throws IOException, InvalidNameException {
        DateServer.go();
    }
}
