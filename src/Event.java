public interface Event extends Comparable<Event>{
    String eventName = new String();
    int startHour = -1;
    int endHour = -1;
    Date date = null;

    String getEventSummary();
    String getName();
    int getStartHour();
    int getEndHour();
    Date getDate();
    int compareTo(Event other);
}
