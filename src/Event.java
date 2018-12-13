public interface Event extends Comparable<Event>{
    String eventName = new String();
    int eventStartHour = -1;
    int eventEndHour = -1;

    String getEventSummary();
    String getEventName();
    int getEventStartHour();
    int getEventEndHour();
    int compareTo(Event other);
}
