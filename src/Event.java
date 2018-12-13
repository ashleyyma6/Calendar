/**
 * Event interface for the reuse of an event's basic features and adding more continence for the operations related to different kind of events
 * It is the blue print of all kinds of events.
 * @author Lianshi Gan, Zhao Liu, Yuehongxiao Ma
 * */
public interface Event extends Comparable<Event>{
    String eventName = new String();
    int startHour = -1;
    int endHour = -1;

    void setName(String _eventName);
    void setStartHour(int _StartHour);
    void setEndHour(int _EndHour);
    String toString();
    String getName();
    int getStartHour();
    int getEndHour();
    int compareTo(Event other);
}
