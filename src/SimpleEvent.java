import java.util.ArrayList;import java.util.Arrays;

import java.util.List;

/**
 * SimpleEvent holds the data for one event in calendar
 * @author Guohua Jiang
 *
 */
public class SimpleEvent implements Event, Comparable<Event>
{
    private String eventName;
    private int startHour; 
    private int endHour;
    
    /**
     * Constructor that sets a name, starting time, and ending time to an event
     * @param name the event name
     * @param start the starting time of event
     * @param end the ending time of event
     */
    public SimpleEvent(String name, int start, int end){
        eventName = name; 
        startHour = start; 
        endHour = end; 
    }
    
    /**
     * @return the even as a string
     */
    public String getEventSummary()
    {
    	String result = new String();
    	String startTime = new String();
		String endtTime = new String();

		//set am pm for the start and end time
    	if(startHour > 12)
    	{
    		startTime = (startHour-12) + "pm";
    	}
    	else
    	{
    		startTime = startHour + "am";
    	}

    	if(endHour > 12)
    	{
    		endtTime = (endHour-12) + "pm";
    	}
    	else
    	{
    		endtTime = endHour + "am";
    	}

    	//get a summary of the event
    	result = startTime + " - " + endtTime + " " + eventName;
    	return result;
    }
    
    /**
     * Gets the event name
     * @return the event name
     */
    public String getName(){
        return eventName;
    }

    /**
     * Gets the events starting time
     * @return the events starting time
     */
    public int getStartHour(){
        return startHour;
    }
    
    /**
     * Gets the events ending time 
     * @return the events ending time 
     */
    public int getEndHour(){
        return endHour;
    }

    @Override
	public int compareTo(Event other) {
		List<String> list1 = new ArrayList<String>();

		if(startHour<other.startHour){return -1;}
		else if (startHour>startHour){return 1;}
		else
		{
		   return 0;
		}
	}

}
