public interface Event extends Comparable<Event>{
    String eventName = new String();
    int eventStartHour = -1;
    int eventEndHour = -1;
    String eventType = new String();

    void setEventName(String _eventName);
    void setEventStartHour(int _eventStartHour);
    void setEventEndHour(int _eventEndHour);
    String getEventName();
    int getEventStartHour();
    int getEventEndHour();
    String getEventSummary();
    int compareTo(Event other);
}
