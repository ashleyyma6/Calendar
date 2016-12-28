/**
 * Date class hold day, month, and year information for a day that has events.
 * @author Guohua Jiang
 *
 */
public class Date implements Comparable<Date>
{
    private int year;
    private int month;
    private int day;
    
    /**
     * Constructs a Date object with the given year, month and day.
     * @param m Month
     * @param d Day
     * @param y Year
     */
    Date(int m, int d, int y )
    {
    	year = y;
    	month = m;
    	day = d;
    }
    
    /**
     *  Get year of the date.
     * @return year
     */
    public int getYear()
    {
    	return year;
    }
    
    /**
     * Get month of the date.
     * @return month.
     */
    public int getMonth()
    {
    	return month;
    }
    
    /**
     * Get the day of the date.
     * @return day.
     */
    public int getDay()
    {
    	return day;
    }
    
    /**
     * Check if a given date has events.
     * @param m month of the date.
     * @param d day of the day.
     * @param y year of the date.
     * @return true if the date in our record false otherwise.
     */
    public boolean checkDate(int m, int d, int y)
    {
    	return (year == y) && (month == m) && (day== d);
    }

	@Override
	public int compareTo(Date other) {
		if(year < other.year) return -1;
		else if(year > other.year) return 1;
		else
		{
			if(month < other.month) return -1;
			else if(month > other.month) return 1;
			else
			{
				if(day < other.day) return -1;
				else if(day > other.day) return 1;
				else return 0;
			}
		}
	}
    
   
}