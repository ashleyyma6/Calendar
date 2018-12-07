import javax.swing.*;
import java.awt.*;
import java.io.IOException;
/**
 * SimpleCalendar containts the main thread, creates the CalendarFrame and displays it on the window
 * @author Guohua Jiang
 *
 */
public class Main
{
    public static void main(String[] args) throws IOException 
    {
        //the calendarFrame object, the application entrance
        CalendarFrame cf = new CalendarFrame();

        //set the size of the application window
        cf.setSize(new Dimension(700, 700));

        //set the application window to be in the center of screen
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        cf.setLocation(dim.width/2-cf.getSize().width/2, dim.height/2-cf.getSize().height/2);

        cf.setResizable(true);
        cf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        cf.setVisible(true);
    }
}
