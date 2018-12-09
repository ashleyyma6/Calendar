import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * MonthCalendar is the panel that shows on the left side of the application;
 * @author Guohua Jiang
 *
 */
public class MonthCalendar extends JPanel {

    private CalendarController controller;
    private JPanel monthCal;//the panel for the monthly calendar
    private ArrayList<JLabel> daysLabels;
    private JLabel monthTitle;//the label for the month name
    private ArrayList<JLabel> weeks;
    private JButton previous, next, createEvent, quit, removeEvent;

    public final static String[] months = {
        "January", "February", "March", "April", "May", "June",
        "July", "August", "September", "October", "November", "December"
    };

    /**
     * Constructor, set up all GUI components for the right side monthly calendar of the application.
     * @param c the controller that contains all functionality
     * @param events all events in the calendar
     */
    MonthCalendar(CalendarController c, final Events events)
    {
        controller = c;
        monthCal = new JPanel();
        monthCal.setLayout(new GridLayout(0, 7));
        daysLabels = new ArrayList<JLabel>();
        monthTitle = new JLabel();


        previous = new JButton("<");
        previous.setBackground(Color.LIGHT_GRAY);
        next = new JButton(">");
        next.setBackground(Color.LIGHT_GRAY);
        createEvent = new JButton("CREATE");
        removeEvent = new JButton("REMOVE");
        quit = new JButton("Quit");
        
        // create a new event button 
  	    quit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                controller.saveEventsToFile();
            	System.exit(0);	
            }
        });
        createEvent.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                CreateEvent ce = new CreateEvent(events, controller);
                ce.setSize(300, 130);
                Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
                ce.setLocation(dim.width/2-ce.getSize().width/2, dim.height/2-ce.getSize().height/2); 
                ce.setVisible(true);
            }
        });

        // behavior of left and right arrow clicks
        addButtonActionListener(previous);
        addButtonActionListener(next);

        //add calendar changing next/previous month buttons
        JPanel lrPanel = new JPanel(new FlowLayout());
        lrPanel.add(previous);
        lrPanel.add(next);

        weeks = new ArrayList<JLabel>();

        weeks.add(new JLabel("S", JLabel.CENTER));
        weeks.add(new JLabel("M", JLabel.CENTER));
        weeks.add(new JLabel("T", JLabel.CENTER));
        weeks.add(new JLabel("W", JLabel.CENTER));
        weeks.add(new JLabel("T", JLabel.CENTER));
        weeks.add(new JLabel("F", JLabel.CENTER));
        weeks.add(new JLabel("S", JLabel.CENTER));

        setLayout(new BorderLayout());
        JPanel topPanel = new JPanel(new BorderLayout());
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(createEvent);
        buttonPanel.add(removeEvent);
        buttonPanel.add(quit);
        topPanel.add(buttonPanel, BorderLayout.NORTH);
        topPanel.add(monthTitle, BorderLayout.WEST);
        topPanel.add(lrPanel, BorderLayout.EAST);
        add(topPanel, BorderLayout.NORTH);
        add(monthCal, BorderLayout.CENTER);
        showMonth();
    }

    /**
     * Gets all the days of the current month, and displays them in
     * tabular/calendar format. Each day is click-able and changes the view on
     * right side of screen depending on which view is being used
     */
    public void showMonth() {
        ArrayList<String> days = showSmallCalendar();
        daysLabels.clear();
        monthCal.removeAll();

        for (String s : days) {
            if (s.contains("*")) {
                JLabel tempLabel = new JLabel(s.substring(1), JLabel.CENTER);
                daysLabels.add(tempLabel);
            } else {

                if (Integer.parseInt(s) == controller.getCalendar().get(Calendar.DATE))
                {
                    JLabel tempLabel = new JLabel(s, JLabel.CENTER);
                    tempLabel.setBorder(BorderFactory.createLineBorder(Color.black));
                    daysLabels.add(tempLabel);

                } else {
                    JLabel tempLabel = new JLabel(s, JLabel.CENTER);
                    Font font = tempLabel.getFont();
                    daysLabels.add(tempLabel);
                }
            }
        }
        for (JLabel jl : weeks) {
            monthCal.add(jl);
        }

        for (JLabel jl : daysLabels) {
            jl.setOpaque(true);
            jl.setBackground(Color.white);
            monthCal.add(jl);
        }

        monthTitle.setText(months[controller.getCurMonth()] + " " + controller.getCurYear());
        addDaysLabelListener();
        monthCal.validate();
        monthCal.repaint();
    }

    /**
     * Get the numbers of days in arrayList matter
     * @return an arraylist of all days
     */
    private ArrayList<String> showSmallCalendar() {

        ArrayList<String> arr = new ArrayList();
        int current = controller.getCurDay();
        GregorianCalendar calendar = controller.getCalendar();

        
        GregorianCalendar temp = new GregorianCalendar(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), 1);
        int blanks = temp.get(Calendar.DAY_OF_WEEK)-1;     
        for (int i = 0; i < blanks; i++) {
            arr.add("*");
        }

        int numDays = calendar.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
        for (int i = 1; i <= numDays; i++) {
            arr.add(String.valueOf(i));
        }

        int remaindingBlanks = ((7-( arr.size() % 7)) == 7) ? 0 : 7-( arr.size() % 7);  
    	for(int i =0; i< remaindingBlanks; i++)
        {
    		arr.add("*");
        }
        calendar.set(GregorianCalendar.DAY_OF_MONTH, current);
        return arr;
    }

    private void addButtonActionListener(final JButton button) {
        button.addActionListener(
                new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	
                if (button.getText().equals("<")) 
                {
                    controller.prevMonth();
                    controller.getAgenda().showView(controller.getCurYear(), controller.getCurMonth(), controller.getCurDay());
                }
                else 
                {
                    controller.nextMonth();
                    controller.getAgenda().showView(controller.getCurYear(), controller.getCurMonth(), controller.getCurDay());
                }
                showMonth();
            }
        });
    }

    /**
     * Attaches mouse clicked listener to daysLabel.
     */
    public void addDaysLabelListener() {
    	
        int tempMonth = controller.getCurMonth();
        int tempYear = controller.getCurYear();
        
        for (final JLabel jl : daysLabels) 
        {
            final int tempMonthCopy = tempMonth;
            final int tempYearCopy = tempYear;
            
            if(jl.getText().matches("\\d+"))
        	{
            jl.addMouseListener(
                    new MouseAdapter() {
                /**
                 * Changes the current view when the mouse clicks
                 */
                @Override
                public void mouseClicked(MouseEvent e) {
                	controller.setCalendarDate(tempYearCopy, tempMonthCopy, Integer.parseInt(jl.getText()));
                	showMonth();
                	controller.getAgenda().showView(tempYearCopy, tempMonthCopy, Integer.parseInt(jl.getText()));
                }

                /**
                 * Highlight the day when the mouse enters 
                 */
                @Override
                public void mouseEntered(MouseEvent e) {
                    jl.setBackground(new Color(222, 222, 222));
                    jl.setCursor(new Cursor(Cursor.HAND_CURSOR));
                }

                /**
                 * Unhighlight the day when mouse is no longer hovering over it
                 */
                @Override
                public void mouseExited(MouseEvent e) {
                    jl.setBackground(Color.white);
                }
            });
        }
        }
    }
}

