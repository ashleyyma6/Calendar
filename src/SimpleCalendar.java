import javax.swing.*;
import java.awt.*;
import java.io.IOException;
/**
 * SimpleCalendar containts the main thread, creates the CalendarFrame and displays it on the window
 * @author Guohua Jiang, Lianshi Gan, Zhao Liu, Yuehongxiao Ma
 */
public class SimpleCalendar
{
    public static void main(String[] args) throws IOException 
    {
        CalendarFrame cf = new CalendarFrame(); // create the frame
        cf.setSize(new Dimension(700, 700)); // set the size
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize(); // get screen size
        Thread thread = new Thread() {
            public void run() {
                cf.setLocation(dim.width / 2 - cf.getSize().width / 2, dim.height / 2 - cf.getSize().height / 2);   // locate the frame
                cf.setResizable(true);  // resize
                cf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // close
                cf.setVisible(true); // visible
                System.out.println("Test Show Main Thread");
            }
        };
        thread.start();
    }
}
