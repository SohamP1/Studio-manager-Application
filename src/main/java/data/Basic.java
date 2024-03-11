package data;

import enums.Location;

/**
 * Represents a Basic membership in the gym system. Extends the Member class to include
 * attributes and behaviors specific to Basic memberships, such as tracking the number of
 * classes attended and calculating the bill based on attendance.
 *
 * @author Soham Patel
 */
public class Basic extends Member {

    /** The number of classes attended by the member. */
    private int numClasses;

    /**
     * The cost of one month's membership. This constant represents the standard rate
     * for a month without any additional classes beyond the included maximum.
     */
    private static final double ONE_MONTH_MEMBERSHIP_COST = 39.99;

    /**
     * The cost per class for each class attended beyond the maximum allowed number
     * of classes included in the membership cost.
     */
    private static final double EX_CLASS_COST = 10;

    /**
     * The maximum number of classes that are included in the monthly membership cost.
     * Attending more than this number of classes incurs additional charges per class.
     */
    private static final double MAX_CLASS = 4;


    /**
     * Constructs a Basic member with a profile, expiration date, and home studio.
     * Initializes the number of attended classes to 0.
     *
     * @param profile    The member's profile.
     * @param expire     The expiration date of the membership.
     * @param homeStudio The home studio of the member.
     */
    public Basic(Profile profile, Date expire, Location homeStudio) {
        super(profile, expire, homeStudio);
        numClasses = 0;
    }

    /**
     * Calculates the total billing amount for the membership period, including any extra charges
     * for classes attended beyond the allowed maximum.
     *
     * @return The total billing amount.
     */
    @Override
    public double bill() {
        return ONE_MONTH_MEMBERSHIP_COST + exCharge();
    }

    /**
     * Calculates extra charges incurred from attending more classes than the membership allows.
     *
     * @return Extra charges based on the number of additional classes attended.
     */
    private double exCharge() {
        if (numClasses > MAX_CLASS) {
            return EX_CLASS_COST * (numClasses - MAX_CLASS);
        }
        return 0;
    }

    /**
     * Compares this Basic member to another object for equality. Delegates to the superclass's implementation.
     * This ensures that Basic members are compared based on Member class equality criteria.
     *
     * @param obj the object to be compared for equality with this Basic member.
     * @return true if the specified object is equal to this Basic member, false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }


    /**
     * Compares this Basic member with another Member object to determine their natural ordering.
     * Delegates to the superclass's compareTo method to utilize Member class's comparison logic.
     *
     * @param other the Member object to be compared against this Basic member.
     * @return a negative integer, zero, or a positive integer as this Basic member
     * is less than, equal to, or greater than the specified Member object.
     */
    @Override
    public int compareTo(Member other) {
        return super.compareTo(other);
    }


    /**
     * Provides a string representation of the Basic member including the number of classes attended.
     *
     * @return The string representation of the Basic member.
     */
    @Override
    public String toString() {
        return super.toString() + ", (Basic) number of classes attended: " + numClasses;
    }

    /**
     * Increments the count of classes attended by the member.
     */
    public void attendClass() {
        numClasses++;
    }

}