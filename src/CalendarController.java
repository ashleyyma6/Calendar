import java.util.List;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Scanner;

/**
 * The Controller class carries out the tasks thats are requested by the view.
 * @author Guohua Jiang
 */
public class CalendarController {

    private Agenda agenda;
    private GregorianCalendar calendar;
    private Events events;
    public final static String[] dayOfWeek = {
        "", "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"
    };

    /**
     * Constructor.
     * @param events events contains a Hashmap that holds all events in the calender and the corresponding dates
     */
    CalendarController(Events events)
    {
        this.events = events;
        calendar = new GregorianCalendar();
        readExcitingEvents();
    }

    /**
     * Default constructor that creates a empty schedule.
     */
    CalendarController()
    {
        // default day view
        calendar = new GregorianCalendar();
        this.events = new Events();
        readExcitingEvents();
    }

    /**
     *Accessor for the GregorianCalendar object.
     * @return a GregorianCalendar object
     */
    public GregorianCalendar getCalendar() {
        return calendar;
    }

    /**
     *Set the agenda view to a specific view.
     * @param a view to an Agenda onject
     */
    public void setCurView(Agenda a) {
        this.agenda = a;
    }

    /**
     * Gets the current agenda view being displayed on screen
     *
     * @return the current agenda view
     */
    public Agenda getAgenda() {
        return this.agenda;
    }

    /**
     * Gets the current month of calendar
     *
     * @return the current month of the calendar
     */
    public int getCurMonth() {
        return calendar.get(GregorianCalendar.MONTH);
    }

    /**
     * Gets the current year of calendar
     *
     * @return the current year of the calendar
     */
    public int getCurYear() {
        return calendar.get(GregorianCalendar.YEAR);
    }

    /**
     * Gets the current day of calendar
     *
     * @return the current day of the calendar
     */
    public int getCurDay() {
        return calendar.get(GregorianCalendar.DAY_OF_MONTH);
    }

    /**
     * Increments the current calendar by one
     */
    public void nextMonth() {
        calendar.add(GregorianCalendar.MONTH, 1);
    }

    /**
     * Decrements the current calendar by one
     */
    public void prevMonth() {
        calendar.add(GregorianCalendar.MONTH, -1);
    }


    /**
     * Gets the a Date object that contains the currant month, day and yeear
     * @return the current day, month, and year in a Date Object
     */
    public Date getDate() {
        return new Date( (getCurMonth()), getCurDay(), getCurYear());
    }



    /**
     * Read exciting events in the events.txt file
     */
    public void readExcitingEvents()
    {
    	File file = new File("events.txt");
 		if(!file.exists()) 
 		{
 		try 
 		{
			file.createNewFile();
		} 
 		catch (IOException e) 
 		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
 		}
 		Scanner inputFile = null;
		try 
		{
			inputFile = new Scanner(file);
		} 
		catch (FileNotFoundException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
 		ArrayList<String> readFile = new ArrayList<String>();
 		
 	    while(inputFile.hasNextLine())
 		{
 			readFile.add(inputFile.nextLine());
 		}
 	
 	    for(int i = 0; i < readFile.size(); i ++)
 	    {	
 	       String date = new String();
 		   String time = new String();
 		   String title = new String();
 		   List<String> list = new ArrayList<String>();
 	       
 		   list = Arrays.asList(readFile.get(i).split("\\s+"));
 		
 		   List<String> list2 = new ArrayList<String>(); 
 		   date = list.get(0).substring(0, list.get(0).length()-1);
 		   
 		   list2 = Arrays.asList(date.split("/"));
 		  
 		   int month = Integer.parseInt(list2.get(0)) -1;
 		   int day = Integer.parseInt(list2.get(1));
 		   int year = Integer.parseInt(list2.get(2));
 		   int startingTime = (list.get(1).substring(list.get(1).length()-2).equals("am"))?  Integer.parseInt(list.get(1).substring(0,list.get(1).length()-2)):Integer.parseInt(list.get(1).substring(0,list.get(1).length()-2))+12;
 		   int endingTime = (list.get(3).substring(list.get(3).length()-2).equals("am"))?  Integer.parseInt(list.get(3).substring(0,list.get(3).length()-2)):Integer.parseInt(list.get(3).substring(0,list.get(3).length()-2))+12;
 		     
 		   for(int index = 4; index < list.size(); index++)
 		   {
 			  title += list.get(index) + " ";
 		   }

 		   if(events.addEvent(new Date(month, day, year), new SimpleEvent(title, startingTime, endingTime) ))
 		   {
 			   events.addEvent(new Date(month, day, year), new SimpleEvent(title, startingTime, endingTime) );
 		   }
 	    }   
 	    inputFile.close();
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

    /**
     * Save all exciting events to events.txt
     */
	public void saveEventsToFile() 
	{
		PrintWriter out = null;
		try 
		{
			out = new PrintWriter("events.txt");
		} 
		catch (FileNotFoundException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ArrayList<String> strings = events.saveEventsToFile();
		for(String s : strings)
		{
			out.println(s);
		}
		out.close();
	}
}
