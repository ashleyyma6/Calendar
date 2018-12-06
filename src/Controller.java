import java.util.Calendar;
import java.util.GregorianCalendar;


/**
 * The Controller class carries out the tasks thats are requested by the view.
 * @author Guohua Jiang
 */
public class Controller {

    private Agenda agenda;
    private GregorianCalendar calendar;
    private Events events;
    public final static String[] dayOfWeek = {
        "", "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"
    };

    /**
     * Constructor.
     * @param events events holds all events in the calender and the corresponding information
     */
    Controller(Events events) 
    {
        this.events = events;
        calendar = new GregorianCalendar();
        //readExcitingEvents();
    }

    /**
     * Default constructor that creates a empty schedule.
     */
    Controller()
    {
        // default day view
        calendar = new GregorianCalendar();
        this.events = new Events();
        //readExcitingEvents();
    }

    /**
     *Accessor for the GregorianCalendar object.
     * @return a GregorianCalendar object
     */
    public GregorianCalendar getCalendar() {
        return calendar;
    }

    /**
     * Set the agenda view to a specific view.
     * @param a view to an Agenda object
     */
    public void setCurView(Agenda a) {
        this.agenda = a;
    }

    /**
     * Gets the current agenda view being displayed on screen
     * @return the current agenda view
     */
    public Agenda getAgenda() {
        return this.agenda;
    }

    /**
     * Gets the current month of calendar
     * @return the current month of the calendar
     */
    public int getCurMonth() {
        return calendar.get(GregorianCalendar.MONTH);
    }

    /**
     * Gets the current year of calendar
     * @return the current year of the calendar
     */
    public int getCurYear() {
        return calendar.get(GregorianCalendar.YEAR);
    }

    /**
     * Gets the current day of calendar
     * @return the current day of the calendar
     */
    public int getCurDay() {
        return calendar.get(GregorianCalendar.DAY_OF_MONTH);
    }

    /**
     * Increments the current calendar by one
     */
    public void nextDay() {
        calendar.add(GregorianCalendar.DAY_OF_MONTH, 1);
    }

    /**
     * Decrements the current calendar by one
     */
    public void prevDay() {
        calendar.add(GregorianCalendar.DAY_OF_MONTH, -1);
    }


    /**
     * Gets the a Date object that contains the currant month, day and yeear
     * @return the current day, month, and year in a Date Object
     */
    public Date getDate() {
        return new Date( (getCurMonth()), getCurDay(), getCurYear());
    }



    /**
     * Sets the calendar date to today's date
     */
    public void todayDate() {
        calendar.set(GregorianCalendar.YEAR, Calendar.getInstance().get(GregorianCalendar.YEAR));
        calendar.set(GregorianCalendar.MONTH, Calendar.getInstance().get(GregorianCalendar.MONTH));
        calendar.set(GregorianCalendar.DAY_OF_MONTH, Calendar.getInstance().get(GregorianCalendar.DAY_OF_MONTH));
    }
    
    /**
     * Gets the current day of the week of calendar
     * @return A string of the current day of the week ie. Sunday, Monday, etc
     */
    public String getDayOfWeek() {
        return dayOfWeek[calendar.get(GregorianCalendar.DAY_OF_WEEK)];
    }

    /**
     * Changes the current calendar date to the a specified date
     * @param year 
     * @param month
     * @param day
     */
    public void setCalendar(int year, int month, int day)
    {
        calendar = new GregorianCalendar(year, month, day);
    }


}
