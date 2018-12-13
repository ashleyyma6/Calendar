import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.util.*;

/**
 * Contains all the events that are currently scheduled in the calendar
 * @author Guohua Jiang, Lianshi Gan, Zhao Liu, Yuehongxiao Ma
 */
public class Events {

    private Map<Date, ArrayList<SimpleEvent>> eventsList;
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
     *
     * @param cl the change listener to add to list
     */
    public void addChangeListener(ChangeListener cl) {
        listeners.add(cl);
    }

    /**
     * Adds a new event to the calendar
     *
     * @param date the date on which the new event will be scheduled
     * @param dayEvents the new events details
     * @return true if event was successfully added, false if there was a
     * conflict with another event
     */


    // add event
    public boolean addEvent(Date date, SimpleEvent dayEvents)
    {

        if (dayEvents.getStartHour() > dayEvents.getEndHour()) 
        {
            return false;
        } //invalid format

        ArrayList<SimpleEvent> eventList = new ArrayList<SimpleEvent>();

        int[] dayEventsHours = new int[24];
        for (int i = 0; i < dayEventsHours.length; i++) 
        {
            dayEventsHours[i] = -1;
        }
        for (int i = dayEvents.getStartHour()-1; i <= dayEvents.getEndHour()-1; i++) {
            dayEventsHours[i] = 1;
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
            for (SimpleEvent e : eventList)
            {
                for (int i = e.getStartHour()-1; i <= e.getEndHour()-1; i++) 
                {
                	if (dayEventsHours[i] == 1) 
                    {
                    	return false;
                    }
                }
            }
            eventList.add(dayEvents);
            Collections.sort(eventList);
            eventsList.put(dates.get(index), eventList);
            updateAllListeners();
            return true;
        }
        else 
        {
            eventList = new ArrayList();
            eventList.add(dayEvents);
            dates.add(date);
            Collections.sort(dates);
            eventsList.put(date, eventList);
            updateAllListeners();
            return true;
        }
    }

    /**
     * Remove an event to the calendar
     * @param date the date on which the event was scheduled
     * @param dayEvents the events details
     * @return true if event was successfully deleted, false if cannot find the event
     */
    public boolean cancelEvent(Date date, SimpleEvent dayEvents)
    {

        if (dayEvents.getStartHour() > dayEvents.getEndHour())
        {
            return false;
        } //invalid format

        ArrayList<SimpleEvent> eventList = new ArrayList<SimpleEvent>();

        int[] dayEventsHours = new int[24];
        for (int i = 0; i < dayEventsHours.length; i++)
        {
            dayEventsHours[i] = -1;
        }
        for (int i = dayEvents.getStartHour()-1; i <= dayEvents.getEndHour()-1; i++) {
            dayEventsHours[i] = 1;
        }


        // found whether we have a previous recorded list or not
        int index = 0;
        for(index = 0; index < dates.size(); index++)
        {
            if(dates.get(index).checkDate(date.getMonth(), date.getDay(), date.getYear()))
            {
                eventList = eventsList.get(dates.get(index));;
                break;
            }
        }


        // if events for this date already exist, cancel part of that arraylist.
        // check whether there is a event at this time or not, if yes, cancel it
            ArrayList<SimpleEvent> eventList2 = new ArrayList<SimpleEvent>();
            for (SimpleEvent e : eventList)
            {
                eventList2.add(e);
            }

            for (SimpleEvent e : eventList2)
            {
                for (int i = e.getStartHour()-1; i <= e.getEndHour()-1; i++)
                {
                    if (dayEventsHours[i] == 1)
                    {
                        eventList.remove(e);
                    }
                }
            }

            Collections.sort(eventList);
            eventsList.put(dates.get(index), eventList);
            updateAllListeners();
            return true;


    } // end of cancel event

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
    public ArrayList<SimpleEvent> getEventsForDate(Date date) {
        ArrayList<SimpleEvent> dayEvents = null;


        for (Date d : dates) {
            if (d.checkDate(date.getMonth(), date.getDay(), date.getYear())) {
                dayEvents = eventsList.get(d);
            }
        }

        if (dayEvents != null) {
            Collections.sort(dayEvents);
        }
        return dayEvents;
    }
    /**
     * Save all events information as strings to a result list
     * @return all events information list
     */
	public ArrayList<String> saveEventsToFile()
	{
		ArrayList<String> result = new ArrayList<String>();
		for(int i = 0; i < dates.size(); i++)
		{	
			ArrayList<SimpleEvent> DE = eventsList.get(dates.get(i));
			for(SimpleEvent de : DE )
			{
				String temp = new String();
				temp = (dates.get(i).getMonth()+1) + "/" + dates.get(i).getDay() + "/" + dates.get(i).getYear() + ": " + de.toString();
				result.add(temp);
			}
		}
		
		return result;
	}
}
