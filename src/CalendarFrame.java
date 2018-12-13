import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.io.IOException;

/**
 * CalendarFrame is the JFrame that holds all GUI components.
 * @author Guohua Jiang
 */
public class CalendarFrame extends JFrame implements ChangeListener {

    private CalendarController controller;
    private Events events;  
    private Agenda agenda;
    final JPanel rightPanel;

    /**
     * Default constructor.
     * @throws IOException
     */
    public CalendarFrame() throws IOException 
    {
        events = new Events();

        agenda = new Agenda(events);
        rightPanel  = new JPanel();
        events.addChangeListener(this);

        controller = new CalendarController(events);
        controller.setCurView(agenda);

        final MonthCalendar smallCal = new MonthCalendar(controller, events);
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BorderLayout());

        JPanel tempPanel = new JPanel(new FlowLayout());
        tempPanel.add(smallCal);
        smallCal.setPreferredSize(new Dimension(250, 250));
   
        leftPanel.add(tempPanel, BorderLayout.CENTER);

        rightPanel.setLayout(new BorderLayout());

        setLayout(new BorderLayout());
        add(leftPanel, BorderLayout.WEST);
        add(rightPanel, BorderLayout.CENTER);
        setTitle("Calendar");

        rightPanel.add(agenda, BorderLayout.CENTER);
        rightPanel.validate();
        controller.getAgenda().showView(controller.getCurYear(), controller.getCurMonth(), controller.getCurDay());
        rightPanel.repaint();
    }

 
    /**
     * If new events are added to model, updates the current view automatically
     *
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
