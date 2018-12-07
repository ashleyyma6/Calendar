import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Agenda holds and show all the events scheduled for a certain day in a table
 */
public class Agenda extends JPanel {

    JLabel timeTitleLabel;//the hour time in the daily schedule table
    JScrollPane scrollPane;//for scroll to check the daily schedule
    JTable leftColumn, rightColumn;//left row and right row of the table
    JPanel panel;
    CalendarController controller;
    Events events;//the collection of events
    Color color;
    public static final String[] timeTitles = {"1 am", "2 am", "3 am", "4 am", "5 am", "6 am", "7 am", "8 am", "9 am", "10 am", "11 am", "12 am", "1 pm", "2 pm", "3 pm", "4 pm", "5 pm", "6 pm", "7 pm", "8 pm", "9 pm", "10 pm", "11 pm", "12 pm"};

    /**
     * Sets up the GUI needed to display the events, agenda for a given day
     * @param events contains all the events currently in the calendar
     * @throws IOException
     */
    public Agenda(Events events) throws IOException {

        //initialization
        this.setLayout(new BorderLayout());//set the agenda panel to border layout
        timeTitleLabel = new JLabel();
        panel = new JPanel(new BorderLayout());
        scrollPane = new JScrollPane(panel);
        color = new Color(152, 217, 233);//event defult color? light green
        this.events = events;
        controller = new CalendarController();

        showToday();

    }

    /**
     * Makes a left side of table that displays all the hours of the day
     * @param list a list holds all the events currently in the calendar
     */
    private void setLeftColumn(ArrayList<Event> list) {
        Object[][] obj = new Object[24][1];
        for (int i = 0; i < 24; i++) {
            obj[i][0] = timeTitles[i];
        }

        Object[] temp = {""};
        //if there is events
        if (list != null) {
            final int[] hrs = new int[24];
            for (Event e : list) {
                int startHr = e.getStartHour() - 1;
                int endHr = e.getEndHour() - 1;
                while (startHr <= endHr) {
                    hrs[startHr++] = 1;
                }
            }

            leftColumn = new JTable(obj, temp) {
                @Override
                public Component prepareRenderer(TableCellRenderer renderer, int Index_row, int Index_col) {
                    Component comp = super.prepareRenderer(renderer, Index_row, Index_col);
                    // if event at the given hour, color it in. Otherwise leave it white
                    if (hrs[Index_row] == 1) {
                        comp.setBackground(color);
                    } else {
                        comp.setBackground(Color.white);
                    }
                    return comp;
                }
            };
        } else {
            leftColumn = new JTable(obj, temp);
        }


        leftColumn.setTableHeader(null);
        leftColumn.setRowHeight(40);
        leftColumn.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        leftColumn.getColumnModel().getColumn(0).setPreferredWidth(50);
        leftColumn.setGridColor(Color.lightGray);
        leftColumn.setEnabled(false);

    }

    /**
     * Makes a right-hand side of table that houses all the events on the day,
     * and displays them in a graphical way as blocks of time
     * @param list the list of events for the given day to display in panel
     */
    private void setRightColumn(ArrayList<Event> list) {
        Object[][] obj = new Object[24][1];
        Object[] temp = {""};

        if (list != null) {
            final int[] hrs = new int[24];
            for (Event de : list) {
                int startHr = de.getStartHour() - 1;
                int endHr = de.getEndHour() - 1;

                obj[startHr][0] = de.getName();

                while (startHr <= endHr) {
                    hrs[startHr++] = 1;
                }
            }

            rightColumn = new JTable(obj, temp) {
                @Override
                public Component prepareRenderer(TableCellRenderer renderer, int Index_row, int Index_col) {
                    Component comp = super.prepareRenderer(renderer, Index_row, Index_col);
                    // if event at the given hour, color it in. Otherwise leave it white
                    if (hrs[Index_row] == 1) {
                        comp.setBackground(color);
                    } else {
                        comp.setBackground(Color.white);
                    }
                    return comp;
                }
            };
        } else {
            rightColumn = new JTable(obj, temp);
        }

        rightColumn.setTableHeader(null);
        rightColumn.setRowHeight(40);
        rightColumn.setGridColor(Color.lightGray);
        rightColumn.setEnabled(false);
    }

    /**
     * Shows the events on a day in tabular format with left and right columns; sets the current date at the top
     * @param list the list of events for the given day to display in panel
     */
    private void displayDayView(ArrayList<Event> list) {
        this.invalidate();
        panel.removeAll();

        setLeftColumn(list);
        setRightColumn(list);
        setDateTitle(controller.getDayOfWeek() + " " + (controller.getCurMonth() + 1) + "/" + controller.getCurDay());

        panel.add(leftColumn, BorderLayout.WEST);
        panel.add(rightColumn, BorderLayout.CENTER);

        this.add(timeTitleLabel, BorderLayout.NORTH);
        this.add(scrollPane, BorderLayout.CENTER);
        this.validate();
        this.repaint();
    }

    /**
     * Sets the date to be displayed at the top of screen
     * @param date The date to display at the top of day view
     */
    private void setDateTitle(String date) {
        this.timeTitleLabel.setText(date);
    }

    public void showToday() {
        controller.todayDate();
        displayDayView(events.getEventsForDate(controller.getDate()));
    }

    /**
     * Shows the day view for a specified date
     * @param year  the year to display
     * @param month the month to display
     * @param day   the day to display
     */
    public void showView(int year, int month, int day) {
        controller.setCalendar(year, month, day);
        displayDayView(events.getEventsForDate(controller.getDate()));
    }
}

