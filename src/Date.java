/**
 * Date class hold day, month, and year information for a day that has events.
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

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }else if(this == obj){
            return true;
        }

        return false;
    }

    public boolean checkDate(int m, int d, int y)
    {
        return (year == y) && (month == m) && (day== d);
    }
}