import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * CreateEvent class asks the user for event details and creates a new event in
 *
 * @author Guohua Jiang
 */
class CreateEvent extends JFrame implements ActionListener {
    private Events events;
    private JPanel innerPanel;
    private JTextField eventNameTf;
    private JTextArea to, today;
    private JComboBox TimePicker1, TimePicker2, startingHour, endingHour,eventTypePicker;
    private JLabel errorMsg;
    private CalendarController controller;

    /**
     * Constructor, sets up the GUI components for the CreatEvent panel
     * @param event an Events object that contains all events in the calendar
     * @param c     the controller that carries out all functionalities
     */
    CreateEvent(Events event, CalendarController c) {
        events = event;
        innerPanel = new JPanel();
        innerPanel.setLayout(new BorderLayout());
        controller = c;

        eventNameTf = new JTextField("Untitled event");
        eventNameTf.setPreferredSize( new Dimension( 300, 28 ) );
        eventNameTf.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                eventNameTf.setText("");
            }
        });

        JPanel temPanel = new JPanel(new FlowLayout());
        today = new JTextArea((controller.getCurMonth() + 1) + "/" + controller.getCurDay() + "/" + (controller.getCurYear() % 100));
        today.setBackground(null);
        to = new JTextArea("to");
        to.setBackground(null);

        String[] eventType = {"Basic","Reminder"};
        eventTypePicker = new JComboBox(eventType);

        String[] hour = {"1", "2", "3", "4", "5", "6", "7", "8", "9-", "10", "11", "12"};
        startingHour = new JComboBox(hour);
        startingHour.setSelectedIndex(0);

        endingHour = new JComboBox(hour);
        startingHour.setSelectedIndex(0);

        String[] time = {"am", "pm"};
        //for startingTime
        TimePicker1 = new JComboBox(time);
        TimePicker1.setSelectedIndex(0);

        //for endingTime
        TimePicker2 = new JComboBox(time);
        TimePicker2.setSelectedIndex(0);

        temPanel.add(today);

        temPanel.add(startingHour);
        temPanel.add(TimePicker1);
        temPanel.add(to);
        temPanel.add(endingHour);
        temPanel.add(TimePicker2);
        temPanel.add(eventTypePicker);

        errorMsg = new JLabel();
        errorMsg.setForeground(Color.red);

        JButton submitButton = new JButton("SAVE");
        submitButton.setBackground(Color.WHITE);
        submitButton.addActionListener(this);
        temPanel.add(submitButton);

        errorMsg = new JLabel();
        errorMsg.setForeground(Color.red);

        innerPanel.add(eventNameTf, BorderLayout.NORTH);
        innerPanel.add(temPanel, BorderLayout.CENTER);
        innerPanel.add(errorMsg, BorderLayout.SOUTH);

        // create some padding
        add(innerPanel);
        setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent a) {
        // TODO Auto-generated method stub
        errorMsg.setText("");
        String eventName = eventNameTf.getText();
        Date eventDate = new Date(controller.getCurMonth(), controller.getCurDay(), controller.getCurYear());
        int eventStartHour = Integer.parseInt((String) startingHour.getSelectedItem());
        if (TimePicker1.getSelectedItem().equals("pm")) {
            eventStartHour += 12;
        }

        int eventEndHour = Integer.parseInt((String) endingHour.getSelectedItem());
        if (TimePicker2.getSelectedItem().equals("pm")) {
            eventEndHour += 12;
        }


        Event newEvent = null;
        if(eventTypePicker.getSelectedItem().equals("Basic")){
            newEvent = new SimpleEvent(eventName, eventStartHour, eventEndHour);
        }
        else if(eventTypePicker.getSelectedItem().equals("Reminder")){
            newEvent = new ReminderEvent(eventName, eventStartHour, eventEndHour);
        }

        if (events.addEvent(eventDate, newEvent)) {
            this.setVisible(false);
            this.dispose();
            controller.getAgenda().showView(controller.getCurYear(), controller.getCurMonth(), controller.getCurDay());
        } else {
            errorMsg.setText("Error: Schedule conflicts.");
        }
    }
}