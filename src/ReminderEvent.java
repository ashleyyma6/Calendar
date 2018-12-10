import java.util.ArrayList;import java.util.Arrays;
import java.util.TimerTask;
import java.util.List;
import java.time.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
/**
 * DayEvents holds the data for one event in calendar
 */

public class ReminderEvent extends TimerTask implements Event, Comparable<Event>
{
    private String eventName;
    private int startHour;
    private int endHour;
    private Date date;

    /**
     * Constructor that sets a name, starting time, and ending time to an event
     * @param name the event name
     * @param start the starting time of event
     * @param end the ending time of event
     */
    public ReminderEvent(String name, int start, int end){
        eventName = name;
        startHour = start;
        endHour = end;
    }

    /**
     * @return the even as a string
     */
    public String getEventSummary()
    {
        String result = new String();
        String startTime = new String();
        String endtTime = new String();

        //set am pm for the start and end time
        if(startHour > 12)
        {
            startTime = (startHour-12) + "pm";
        }
        else
        {
            startTime = startHour + "am";
        }

        if(endHour > 12)
        {
            endtTime = (endHour-12) + "pm";
        }
        else
        {
            endtTime = endHour + "am";
        }

        //get a summary of the event
        result = startTime + " - " + endtTime + " " + eventName;
        return result;
    }

    /**
     * Gets the event name
     * @return the event name
     */
    public String getName(){
        return eventName;
    }

    /**
     * Gets the events starting time
     * @return the events starting time
     */
    public int getStartHour(){
        return startHour;
    }

    /**
     * Gets the events ending time
     * @return the events ending time
     */
    public int getEndHour(){
        return endHour;
    }

    public Date getDate(){return date;}

    @Override
    public int compareTo(Event other) {
        List<String> list1 = new ArrayList<String>();

        if(startHour<other.startHour){return -1;}
        else if (startHour>startHour){return 1;}
        else
        {
            return 0;
        }
    }

    public void run(){
        while (LocalTime.now().getHour() <= startHour) {
            if (LocalTime.now().getHour() == startHour) {
                showReminder();
            }
        }
    }

    public void showReminder() {
        JFrame f = new JFrame();
        int timeInterval = 1000;
        final JDialog dialog = new JDialog(f, "Test", true);
        //Test should be the event detail
        Timer timer = new Timer(timeInterval, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.setVisible(false);
                dialog.dispose();
            }
        });
        timer.setRepeats(false);
        timer.start();

        dialog.setVisible(true);
        System.out.println("Dialog closed");
    }
}
