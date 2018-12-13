import java.util.ArrayList;import java.util.Arrays;
import java.util.List;

/**
 * DayEvents holds the data for one event in calendar
 * @author Guohua Jiang
 *
 */
public class SimpleEvent implements Event, Comparable<Event>
{
    private String eventName;
    private int eventStartHour;
    private int eventEndHour;
    private String eventType;
    
    /**
     * Constructor that sets a name, starting time, and ending time to an event
     * @param name the event name
     * @param start the starting time of event
     * @param end the ending time of event
     */
    public SimpleEvent(String name, int start, int end){
        eventName = name;
        eventStartHour = start;
        eventEndHour = end;
        eventType = "simple";
    }

    public String getEventType(){return eventType;}

    public void setEventType(String type){eventType = type;}
    
    /**
     * @return the even as a string
     */
    public String getEventSummary()
    {
    	String result = new String();
    	String startTime = new String();
    	if(eventStartHour > 12)
    	{
    		startTime = (eventStartHour-12) + "pm";
    	}
    	else
    	{
    		startTime = eventStartHour + "am";
    	}
    	
    	String endtTime = new String();
    	if(eventEndHour > 12)
    	{
    		endtTime = (eventEndHour-12) + "pm";
    	}
    	else
    	{
    		endtTime = eventEndHour + "am";
    	}
    	
    	result = startTime + " - " + endtTime + " " + eventName;
    	return result;
    }
    
    /**
     * Gets the event name
     * @return the event name
     */
    public String getEventName(){
        return eventName;
    }

    /**
     * Gets the events starting time
     * @return the events starting time
     */
    public int getEventStartHour(){
        return eventStartHour;
    }
    
    /**
     * Gets the events ending time 
     * @return the events ending time 
     */
    public int getEventEndHour(){
        return eventEndHour;
    }

    @Override
	public int compareTo(Event other) {
		List<String> list1 = new ArrayList<String>();

		if(eventStartHour<other.eventStartHour){return -1;}
		else if (eventStartHour>eventStartHour){return 1;}
		else
		{
		   return 0;
		}
	}
}
