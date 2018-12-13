import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.lang.reflect.*;

/**
 * Displays all the events scheduled for a certain day
 */
public class Agenda extends JPanel {

    JLabel dateTitle;
    JScrollPane scrollPane;
    JTable leftTable, rightTable;
    JPanel panel;
    CalendarController controller;
    Events events;
    Color colorSimpleEvent,colorReminderEvent;
    public static final String[] title = {
        "1 am", "2 am", "3 am", "4 am", "5 am", "6 am", "7 am", "8 am", "9 am", "10 am", "11 am",
        "12 am", "1 pm", "2 pm", "3 pm", "4 pm", "5 pm", "6 pm", "7 pm", "8 pm", "9 pm", "10 pm", 
        "11 pm", "12 pm"
    };

    /**
     * Sets up the GUI needed to display the events for a given day
     *
     * @param events contains all the events currently in the calendar
     * @throws IOException 
     */
    public Agenda(Events events) throws IOException {
        dateTitle = new JLabel();
        //colorSimpleEvent = new Color(100,200,150); // the event color
        colorSimpleEvent = new Color(152, 217, 233); // the event color
        colorReminderEvent = new Color(100,200,150);
        panel = new JPanel(new BorderLayout());
        scrollPane = new JScrollPane(panel);
        controller = new CalendarController();
        this.events = events;
        this.setLayout(new BorderLayout());

        //use reflection class to call method
        try{
            Method method = this.getClass().getMethod("showToday");
            method.invoke(this);
            //showToday();
        }catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Makes a left side of table that houses all the hours of the day
     */
    private void setLeftTable(ArrayList<SimpleEvent> list) {

        Object[][] obj = new Object[24][1];
        for (int i = 0; i < 24; i++) {
            obj[i][0] = title[i];
        }
        Object[] temp = {""};
        
        if (list != null) {
            final int[] hrs = new int[24];
            for (SimpleEvent de : list) {
                int startHr = de.getEventStartHour() -1;
                int endHr = de.getEventEndHour() - 1 ;
                if(de.getEventType().equals("reminder")) {
                    while (startHr <= endHr) {
                        hrs[startHr++] = 2;
                    }
                }
                else
                {
                    while (startHr <= endHr) {
                        hrs[startHr++] = 1;
                    }
                }
            }

            leftTable = new JTable(obj, temp) 
            {
                @Override
                public Component prepareRenderer(TableCellRenderer renderer, int Index_row, int Index_col) {
                    Component comp = super.prepareRenderer(renderer, Index_row, Index_col);
                    // if event at the given hour, color it in. Otherwise leave it white
                    if (hrs[Index_row] == 1) {
                        comp.setBackground(colorSimpleEvent);
                    }
                    else if(hrs[Index_row] == 2) {
                        comp.setBackground(colorReminderEvent);
                    }
                    else {
                        comp.setBackground(Color.white);
                    }
                    return comp;
                }
            };
        } 
        else 
        {
        	leftTable = new JTable(obj, temp);
        }
        
        
        
        leftTable.setTableHeader(null);
        leftTable.setRowHeight(40);
        leftTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        leftTable.getColumnModel().getColumn(0).setPreferredWidth(50);
        leftTable.setGridColor(Color.lightGray);
        leftTable.setEnabled(false);
           
    }

    /**
     * Makes a right-hand side of table that houses all the events on the day,
     * and displays them in a graphical way as blocks of time
     *
     * @param list the list of events for the given day to display in panel
     */
    private void setRightTable(ArrayList<SimpleEvent> list) {
        Object[][] obj = new Object[24][1];
        Object[] temp = {""};

        if (list != null) {
            final int[] hrs = new int[24];
            for (SimpleEvent de : list) {
                int startHr = de.getEventStartHour() -1;
                int endHr = de.getEventEndHour() - 1 ;

                obj[startHr][0] = de.getEventName();


                if(de.getEventType().equals("reminder")) {
                    while (startHr <= endHr) {
                        hrs[startHr++] = 2;
                    }
                }
                else
                {
                    while (startHr <= endHr) {
                        hrs[startHr++] = 1;
                    }
                }
            }

            rightTable = new JTable(obj, temp) 
            {
                @Override
                public Component prepareRenderer(TableCellRenderer renderer, int Index_row, int Index_col) {
                    Component comp = super.prepareRenderer(renderer, Index_row, Index_col);
                    // if event at the given hour, color it in. Otherwise leave it white
                    if (hrs[Index_row] == 1) {
                        comp.setBackground(colorSimpleEvent);
                    }
                    else if(hrs[Index_row] == 2) {
                        comp.setBackground(colorReminderEvent);
                    }
                    else {
                        comp.setBackground(Color.white);
                    }
                    return comp;
                }
            };
        } 
        else 
        {
            rightTable = new JTable(obj, temp);
        }

        rightTable.setTableHeader(null);
        rightTable.setRowHeight(40);
        rightTable.setGridColor(Color.lightGray);
        rightTable.setEnabled(false);
    }

    /**
     * Shows the events on a day in tabular format with left and right columns; sets the current date at the top
     * @param list the list of events for the given day to display in panel
     */
    private void showDayView(ArrayList<SimpleEvent> list) {
        this.invalidate();
        panel.removeAll();

        setLeftTable(list);
        setRightTable(list);
        setDateTitle(controller.getDayOfWeek() + " " + (controller.getCurMonth() + 1) + "/" + controller.getCurDay());

        panel.add(leftTable, BorderLayout.WEST);
        panel.add(rightTable, BorderLayout.CENTER);

        this.add(dateTitle, BorderLayout.NORTH);
        this.add(scrollPane, BorderLayout.CENTER);
        this.validate();
        this.repaint();
    }

    /**
     * Sets the date to be displayed at the top of screen
     * @param date The date to display at the top of day view
     */
    private void setDateTitle(String date) {
        this.dateTitle.setText(date);
    }

    public void showToday() 
    {
    	controller.todayDate();
        showDayView(events.getEventsForDate(controller.getDate()));
    }

    /**
     * Shows the day view for a specified date
     * @param year the year to display
     * @param month the month to display
     * @param day the day to display
     */
    public void showView(int year, int month, int day) {
    	controller.setCalendar(year, month, day);
        showDayView(events.getEventsForDate(controller.getDate()));
    }
}

