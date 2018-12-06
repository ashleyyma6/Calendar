import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.util.*;

/**
 * Contains all the events that are currently scheduled in the calendar
 * * @author Guohua Jiang
 *
 */
public class Events {

    private Map<Date, ArrayList<Event>> eventsList;
    private ArrayList<Date> dates;
    private ArrayList<ChangeListener> listeners;

    public Events() 
    {
        eventsList = new HashMap();
        dates = new ArrayList<Date>();
        listeners = new ArrayList();
    }

    /**
     * Adds a change listener to list so that it will be notified of any changes
     * in the treemap
     * @param cl the change listener to add to list
     */
    public void addChangeListener(ChangeListener cl) {
        listeners.add(cl);
    }

    /**
     * Adds a new event to the calendar
     *
     * @param date the date on which the new event will be scheduled
     * @param Event the new events details
     * @return true if event was successfully added, false if there was a
     * conflict with another event
     */
    public boolean addEvent(Date date, Event Event) 
    {

        if (Event.getStartHour() > Event.getEndHour()) 
        {
            return false;
        } //invalid format

        ArrayList<Event> eventList = new ArrayList<Event>();

        int[] EventHours = new int[24];
        for (int i = 0; i < EventHours.length; i++) 
        {
            EventHours[i] = -1;
        }
        for (int i = Event.getStartHour()-1; i <= Event.getEndHour()-1; i++) {
            EventHours[i] = 1;
        }

        boolean found = false;
        int index = 0;
        for(index = 0; index < dates.size(); index++)
		{
		     if(dates.get(index).checkDate(date.getMonth(), date.getDay(), date.getYear()))
		     {
		    	 eventList = eventsList.get(dates.get(index));
		    	 found = true;
		    	 break;
		     }
		}
        
        
        // if events for this date already exist, add to that arraylist.
        if (found == true) 
        {
            for (Event e : eventList) 
            {
                for (int i = e.getStartHour()-1; i <= e.getEndHour()-1; i++) 
                {
                	if (EventHours[i] == 1) 
                    {
                    	return false;
                    }
                }
            }
            eventList.add(Event);
            Collections.sort(eventList);
            eventsList.put(dates.get(index), eventList);
            updateAllListeners();
            return true;
        }
        else 
        {
            eventList = new ArrayList();
            eventList.add(Event);
            dates.add(date);
            Collections.sort(dates);
            eventsList.put(date, eventList);
            updateAllListeners();
            return true;
        }
    }

    /**
     * Update all listeners with new information if state of treemap changes
     */
    public void updateAllListeners() {
        ChangeEvent event = new ChangeEvent(this);
        for (ChangeListener listener : listeners) {
            listener.stateChanged(event);
        }
    }


    /**
     * Retrieves all events in sorted order by start hour for a given date
     *
     * @param date the date to retrieve all events for
     * @return Sorted event list on given date
     */
    public ArrayList<Event> getEventsForDate(Date date) {
        ArrayList<Event> Event = null;


        for (Date d : dates) {
            if (d.checkDate(date.getMonth(), date.getDay(), date.getYear())) {
                Event = eventsList.get(d);
            }
        }

        if (Event != null) {
            //need fix
            Collections.sort(Event);
        }
        return Event;
    }

	public ArrayList<String> saveEventsToFile()
	{
		ArrayList<String> result = new ArrayList<String>();
		for(int i = 0; i < dates.size(); i++)
		{	
			ArrayList<Event> DE = eventsList.get(dates.get(i));
			for(Event de : DE )
			{
				String temp = new String();
				temp = (dates.get(i).getMonth()+1) + "/" + dates.get(i).getDay() + "/" + dates.get(i).getYear() + ": " + de.toString();
				result.add(temp);
			}
		}
		
		return result;
	}
}
