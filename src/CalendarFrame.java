import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.io.IOException;

/**
 * MVC
 * Model: Event, agenda, events
 * View: Calendar Frame
 * C: Calendar controller
 *
 * CalendarFrame is the JFrame that holds all GUI components of the application
 */
public class CalendarFrame extends JFrame implements ChangeListener {

    private CalendarController controller;
    private Events events;// store all the events in this calendar
    private Agenda agenda;// the agenda is the right side table part in the application
    final JPanel rightPanel;//the panel in the right side to hold agenda
    JPanel leftPanel;

    /**
     * Default constructor to set up the GUI frame in the application
     * @throws IOException
     */
    public CalendarFrame() throws IOException
    {
        //initialization

        setTitle("Calendar");//set application title
        this.setLayout(new BorderLayout());//set the frame to border layout

        events = new Events();//all the events in this calendar
        agenda = new Agenda(events);//the agenda of the above events

        controller = new CalendarController();//set the controller to control the calendar
        controller.setCurView(agenda);//set the current agenda to the calendar

        rightPanel  = new JPanel();
        rightPanel.setLayout(new BorderLayout());
        leftPanel = new JPanel();
        leftPanel.setLayout(new BorderLayout());

        events.addChangeListener(this);//for the changing in the events

        //set the calendar on the left side of the application
        final MonthCalendar leftMonthCalendar = new MonthCalendar(controller, events);

        //add monthly calendar to a temporary panel wrapper and then add to the left side of the application
        JPanel monthCalendarPanel = new JPanel(new FlowLayout());
        monthCalendarPanel.add(leftMonthCalendar);
        leftMonthCalendar.setPreferredSize(new Dimension(250, 250));
        leftPanel.add(monthCalendarPanel, BorderLayout.CENTER);

        //add agenda to the right side of the application
        rightPanel.add(agenda, BorderLayout.CENTER);
        rightPanel.validate();
        controller.getAgenda().showView(controller.getCurYear(), controller.getCurMonth(), controller.getCurDay());
        rightPanel.repaint();

        //add right left panel to the frame
        add(leftPanel, BorderLayout.WEST);
        add(rightPanel, BorderLayout.CENTER);
    }
 
    /**
     * If new events are added to model, updates the current view automatically
     * @param e The event that changed the state
     */
    public void stateChanged(ChangeEvent e) 
    {
    	rightPanel.removeAll();
        rightPanel.add(agenda, BorderLayout.CENTER);
        rightPanel.validate();
        rightPanel.repaint();
        this.repaint();
    }
}
