
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.Timer;

public class timer {

    private static int cnt;

    public static void main(String args[]) throws InterruptedException {
        ActionListener taskPerformer = new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                System.out.println("end");
            }
        };
        Timer timer = new Timer(1000, taskPerformer);
        timer.setRepeats(false);
        timer.start();

        Thread.sleep(1500);

    }

}
