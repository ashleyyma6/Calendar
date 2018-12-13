public interface Event extends Comparable<Event>{
    String eventName = new String();
    int startHour = -1;
    int endHour = -1;

    String toString();
    String getName();
    int getStartHour();
    int getEndHour();
    int compareTo(Event other);
}
