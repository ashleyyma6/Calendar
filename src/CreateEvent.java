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
 * @author Guohua Jiang
 */
class CreateEvent extends JFrame implements ActionListener 
{
    private Events events;
    private JPanel innerPanel;
    private JTextField eventNameTf;
    private JTextArea to, today;
    private JComboBox timePicker1, timePicker2, startingTime, endingTime, eventPicker;
    private JLabel errorMsg;
    private CalendarController controller;

    /**
     * Constructor, sets up the GUI components for the CreatEvent panel
     * @param event an Events object that contains all events in the calendar
     * @param c the controller that carries out all functionalities 
     */
    public CreateEvent(Events event, CalendarController c)
    {
        events = event;
        innerPanel = new JPanel();
        innerPanel.setLayout(new BorderLayout());
        controller = c;

        eventNameTf = new JTextField("Untitled event");
        eventNameTf.setPreferredSize( new Dimension( 300, 28 ) );
        eventNameTf.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
            	eventNameTf.setText("");
            }
        });

       JPanel temPanel = new JPanel(new FlowLayout());
       today = new JTextArea((controller.getCurMonth()+1) + "/" + controller.getCurDay() + "/" + (controller.getCurYear()%100));
       today.setBackground( null );
       to = new JTextArea("to");
       to.setBackground( null );
       
       String[] times= {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"};
       startingTime = new JComboBox(times);
       startingTime.setSelectedIndex(0);
       
       endingTime = new JComboBox(times);
       startingTime.setSelectedIndex(0);
       
       String[] time = {"am", "pm"};
       timePicker1 = new JComboBox(time);
       timePicker1.setSelectedIndex(0);
       
       timePicker2 = new JComboBox(time);
       timePicker2.setSelectedIndex(0);
       temPanel.add(today);

       // pick event
        String[] eventsTypes = {"Simple Event","Reminder"};
        eventPicker = new JComboBox(eventsTypes);
        eventPicker.setSelectedIndex(0);


       
       temPanel.add(startingTime);
       temPanel.add(timePicker1);
       temPanel.add(to);
       temPanel.add(endingTime);
       temPanel.add(timePicker2);
       temPanel.add(eventPicker);// add the selection to the panel



       errorMsg = new JLabel();
       errorMsg.setForeground(Color.red);

       JButton submitButton = new JButton("SAVE");
       submitButton.setBackground(Color.WHITE);
       submitButton.addActionListener(this);
       temPanel.add(submitButton);
        
       innerPanel.add(eventNameTf,BorderLayout.NORTH);
       innerPanel.add(temPanel,BorderLayout.CENTER);
       innerPanel.add(errorMsg,BorderLayout.SOUTH);
        

       // create some padding
       add(innerPanel);       
       setVisible(true);

    }

	@Override
	public void actionPerformed(ActionEvent a) 
	{
		// TODO Auto-generated method stub
		errorMsg.setText("");
        String eventName = eventNameTf.getText();
        String eventType = "simple";

        if(eventPicker.getSelectedItem().equals("Reminder"))
        {
            eventType = "reminder";
            if(eventName.equals("Untitled event")) eventName = "This is a reminder";
        }
        Date eventDate = new Date(controller.getCurMonth(),  controller.getCurDay(), controller.getCurYear());
        int eventStartHour = Integer.parseInt((String) startingTime.getSelectedItem());
        if(timePicker1.getSelectedItem().equals("pm"))
        {
        	eventStartHour += 12;
        }
        
        int eventEndHour = Integer.parseInt((String) endingTime.getSelectedItem());
        if(timePicker2.getSelectedItem().equals("pm"))
        {
        	eventEndHour += 12;
        }

        SimpleEvent newEvent = new SimpleEvent(eventName, eventStartHour, eventEndHour);
        newEvent.setEventType(eventType);
        if (events.addEvent(eventDate, newEvent)) {
            this.setVisible(false);
            this.dispose();
            controller.getAgenda().showView(controller.getCurYear(), controller.getCurMonth(), controller.getCurDay());
        } else {
            errorMsg.setText("Error: Schedule conflicts.");
        }
	}
}