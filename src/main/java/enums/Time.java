package enums;

/**
 * Enum representing different times of the day for fitness classes.
 * Each enum constant corresponds to a specific time slot.
 *
 * @author Soham Patel
 */
public enum Time {

    /** Morning time slot starting at 9:30 AM */
    MORNING(9, 30),

    /** Afternoon time slot starting at 14:00 PM. */
    AFTERNOON(14, 0),

    /** Evening time slot starting at 18:30 PM. */
    EVENING(18, 30);

    /** Hour part of the time slot */
    private final int hour;

    /** Minute part of the time slot */
    private final int minute;

    /**
     * Constructor for the time enum constants.
     *
     * @param hour   The hour part of the time.
     * @param minute The minute part of the time.
     */
    Time(int hour, int minute) {
        this.hour = hour;
        this.minute = minute;
    }

    /**
     * Returns the hour part of the time slot.
     *
     * @return The hour.
     */
    public int getHour() {
        return hour;
    }

    /**
     * Returns the minute part of the time slot.
     *
     * @return The minute.
     */
    public int getMinute() {
        return minute;
    }

    /**
     * Provides a string representation of the time in HH:MM format.
     *
     * @return A string representation of the time.
     */
    @Override
    public String toString() {
        return String.format("%02d:%02d", hour, minute);
    }
}
