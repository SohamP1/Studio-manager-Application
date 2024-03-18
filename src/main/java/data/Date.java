package data;

import java.util.Calendar;

/**
 * data.Date class represents a date with month, day, and year components.
 * It implements the Comparable interface to allow comparison between dates.
 * author Soham Patel
 */
public class Date implements Comparable<Date> {
    /**
     * The year component of the date.
     */
    private final int year;

    /**
     * The month component of the date.
     */
    private final int month;

    /**
     * The day component of the date.
     */
    private final int day;

    /**
     * Index constant for accessing the month value from a date array.
     */
    private static final int MONTH_INDEX = 0;

    /**
     * Index constant for accessing the day value from a date array.
     */
    private static final int DAY_INDEX = 1;

    /**
     * Index constant for accessing the year value from a date array.
     */
    private static final int YEAR_INDEX = 2;
    /**
     * Number of years in a leap year cycle
     */
    public static final int QUADRENNIAL = 4;
    /**
     * Number of years in a century
     */
    public static final int CENTENNIAL = 100;
    /**
     * Number of years in a quadricentennial cycle
     */
    public static final int QUATERCENTENNIAL = 400;

    /**
     * The minimum year allowed for the date, used for validation.
     */
    private static final int MIN_YEAR_ALLOWED = 1900;
    /**
     * The minimum valid month value.
     */
    private static final int MIN_MONTH = 1;

    /**
     * The maximum valid month value.
     */
    private static final int MAX_MONTH = 12;

    /**
     * The minimum valid day value.
     */
    private static final int MIN_DAY = 1;
    /**
     * The maximum valid day value in any month.
     */
    private static final int MAX_DAY_IN_MONTH = 31;

    /**
     * Constant representing February for use in leap year calculations.
     */
    private static final int FEBRUARY = 2;

    /**
     * The maximum number of days in February during a leap year.
     */
    private static final int MAX_DAYS_IN_FEB_LEAP_YEAR = 29;
    /**
     * The maximum number of days in February during a non-leap year.
     */
    private static final int MAX_DAYS_IN_FEB = 28;

    /**
     * The maximum number of days in months having 31 days.
     */
    private static final int MAX_DAYS_IN_LONG_MONTH = 31;

    /**
     * The maximum number of days in months having 30 days.
     */
    private static final int MAX_DAYS_IN_SHORT_MONTH = 30;

    /**
     * The age in years to be considered eligible for certain operations or validations.
     */
    private static final int ELIGIBLE_YEAR = 18;

    /**
     * A static Calendar instance used to access the current date according to the system's default time zone and locale.
     * This instance provides the current year, month, and day to support date-related operations and validations.
     */
    static Calendar calendar = Calendar.getInstance();

    /**
     * Stores the current year, based on the system's date.
     */
    private static final int thisYear = calendar.get(Calendar.YEAR);

    /**
     * Stores the current month, based on the system's date.
     */
    private static final int thisMonth = calendar.get(Calendar.MONTH) + 1;

    /**
     * Stores the current day of the month, based on the system's date.
     */
    private static final int thisDay = calendar.get(Calendar.DAY_OF_MONTH);

    /**
     * Constructs a data.Date object representing a specific date.
     *
     * @param month the month of the date.
     * @param day   the day of the month.
     * @param year  the year of the date.
     */
    public Date(int month, int day, int year) {
        this.month = month;
        this.day = day;
        this.year = year;
    }

    /**
     * Constructs a data.Date object from a date string in the format "mm/dd/yyyy".
     *
     * @param dateString the string representation of the date.
     */
    public Date(String dateString) {
        String[] dateParts = dateString.split("/");
        this.month = Integer.parseInt(dateParts[MONTH_INDEX]);
        this.day = Integer.parseInt(dateParts[DAY_INDEX]);
        this.year = Integer.parseInt(dateParts[YEAR_INDEX]);
    }

    /**
     * Generates the current date.
     *
     * @return A new Date object representing today's date.
     */
    public static Date getCurrentDate() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        return new Date(month, day, year);
    }


    /**
     * equals method to compare other date object
     *
     * @param obj Object passed through the method to compare it with.
     * @return true if the dates are same, false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Date date = (Date) obj;
        return month == date.month && day == date.day && year == date.year;
    }

    /**
     * check the given date.
     * check if the date is a valid calendar date.
     *
     * @return true if the date is valid, false otherwise.
     */
    public boolean isValid() {
        if (year < MIN_YEAR_ALLOWED || year > thisYear || month < MIN_MONTH || month > MAX_MONTH || day < MIN_DAY || day > MAX_DAY_IN_MONTH) {
            return false;
        }
        if (year == thisYear) {
            if (month == thisMonth) {
                if (day > thisDay) {
                    return false;
                }
            } else if (month < thisMonth) {
                return isGoodMonth(month, day, year);
            }
            return false;
        }

        return isGoodMonth(month, day, year);
    }

    /**
     * Determines if the specified month has the correct number of days.
     *
     * @param month The month to check.
     * @param day   The day to validate within the month.
     * @param year  The year, for leap year consideration.
     * @return true if the day is valid for the month, false otherwise.
     */
    private boolean isGoodMonth(int month, int day, int year) {
        if (month == FEBRUARY) {
            if (isLeapYear(year)) {
                return day <= MAX_DAYS_IN_FEB_LEAP_YEAR;
            } else {
                return day <= MAX_DAYS_IN_FEB;
            }
        }
        return switch (month) {
            case 1, 3, 5, 7, 8, 10, 12 -> day <= MAX_DAYS_IN_LONG_MONTH;
            case 4, 6, 9, 11 -> day <= MAX_DAYS_IN_SHORT_MONTH;
            default -> false;
        };
    }

    /**
     * Helper method for isValid to find if the year is leap year or not.
     *
     * @param year check if the year is a leap year.
     * @return true if the year is leap, false otherwise.
     */
    private boolean isLeapYear(int year) {
        if (year % QUADRENNIAL == 0) {
            if (year % CENTENNIAL == 0) {
                return year % QUATERCENTENNIAL == 0;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

    /**
     * Compares this date with another date.
     *
     * @param otherDate The date to compare against.
     * @return a negative integer, zero, or a positive integer as this date is less than, equal to, or greater than the specified date.
     */
    @Override
    public int compareTo(Date otherDate) {
        if (this.year < otherDate.year) {
            return -1;
        } else if (this.year > otherDate.year) {
            return 1;
        }

        if (this.month < otherDate.month) {
            return -1;
        } else if (this.month > otherDate.month) {
            return 1;
        }

        if (this.day < otherDate.day) {
            return -1;
        } else if (this.day > otherDate.day) {
            return 1;
        } else {
            return 0;
        }

    }

    /**
     * Checks if this date is before today's date.
     *
     * @return true if this date is in the past; otherwise, false.
     */
    public boolean isEligible() {
        int minEligibleYear = thisYear - ELIGIBLE_YEAR;
        if (year < minEligibleYear) {
            return true;
        } else if (year == minEligibleYear) {
            if (month <= thisMonth) {
                return true;
            }
        }
        return false;

    }

    /**
     * Checks if this date is after today's date.
     *
     * @return true if this date is in the future; otherwise, false.
     */
    public boolean isFutureDate() {
        Calendar today = Calendar.getInstance();
        Calendar inputDate = Calendar.getInstance();
        inputDate.set(year, month - 1, day);
        return inputDate.after(today);
    }

    /**
     * Gets the current date in the format mm/dd/yyyy.
     *
     * @return A string representing today's date.
     */
    public static String todayDate() {
        return thisMonth + "/" + thisDay + "/" + thisYear;
    }

    /**
     * Calculates the date one month later from this date.
     *
     * @return A new Date object representing the date one month after this date.
     */
    public Date calculateOneMonthLater() {
        Calendar cal = Calendar.getInstance();
        cal.set(year, month - 1, day);
        cal.add(Calendar.MONTH, 1);
        return new Date(cal.get(Calendar.MONTH) + 1, cal.get(Calendar.DAY_OF_MONTH), cal.get(Calendar.YEAR));
    }

    /**
     * Calculates the date three months later from this date.
     *
     * @return A new Date object representing the date three months after this date.
     */
    public Date calculateThreeMonthsLater() {
        Calendar cal = Calendar.getInstance();
        cal.set(year, month - 1, day);
        cal.add(Calendar.MONTH, 3);

        return new Date(cal.get(Calendar.MONTH) + 1, cal.get(Calendar.DAY_OF_MONTH), cal.get(Calendar.YEAR));
    }

    /**
     * Calculates the date twelve months later from this date.
     *
     * @return A new Date object representing the date eleven months after this date.
     */
    public Date calculateTwelveMonthsLater() {
        Calendar cal = Calendar.getInstance();
        cal.set(year, month - 1, day);
        cal.add(Calendar.MONTH, 12);

        return new Date(cal.get(Calendar.MONTH) + 1, cal.get(Calendar.DAY_OF_MONTH), cal.get(Calendar.YEAR));
    }

    /**
     * Returns a string representation of this date in the format mm/dd/yyyy.
     *
     * @return The string representation of the date.
     */
    @Override
    public String toString() {
        return this.month + "/" + this.day + "/" + this.year;
    }
}