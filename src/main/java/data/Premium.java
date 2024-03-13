package data;

import enums.Location;

/**
 * Represents a Premium membership within the fitness club system. Premium members have access to additional guest passes.
 * This class extends the Member class to incorporate Premium-specific attributes and behaviors.
 *
 * @author Soham Patel
 */
public class Premium extends Member {

    /**
     * The number of guest passes currently available to the member. Guest passes allow the member to bring guests
     * to the fitness classes or facilities.
     */
    private int guestPass;

    /**
     * The cost for one month of Premium membership, providing access to exclusive services and benefits
     * not available with other membership types.
     */
    private static final double ONE_MONTH_MEMBERSHIP_COST = 59.99;


    /**
     * Constructs a Premium member with specified profile, expiration date, and home studio location.
     * Initializes with a default number of guest passes.
     *
     * @param profile    The profile of the member.
     * @param expire     The expiration date of the membership.
     * @param homeStudio The home studio location of the member.
     */
    public Premium(Profile profile, Date expire, Location homeStudio) {
        super(profile, expire, homeStudio);
    }

    /**
     * Calculates the billing amount for the Premium membership.
     *
     * @return The total billing amount for the membership period.
     */
    @Override
    public double bill() {
        return ONE_MONTH_MEMBERSHIP_COST * 11;
    }


    /**
     * Compares this Premium member to another object for equality. It leverages the {@code equals} method
     * from the superclass {@code Member} to ensure consistency in equality checks across different member types.
     *
     * @param obj the object to be compared for equality with this Premium member.
     * @return true if the specified object is equal to this Premium member, false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    /**
     * Reduces the number of available guest passes by one, simulating the attendance of a guest.
     */
    public void takeAttendanceOfGuest() {
        guestPass -= 1;
    }

    /**
     * Optionally increases the number of available guest passes by one. This method simulates revoking a guest's attendance.
     */
    public void removeGuest() {
        guestPass += 1;
    }

    /**
     * Checks if any guest passes are available.
     *
     * @return true if at least one guest pass is available, false otherwise.
     */
    public boolean hasGuestPass() {
        return guestPass != 0;
    }

    /**
     * Compares this Premium member with another Member object to determine their natural ordering.
     * This method delegates to the superclass's {@code compareTo} method to utilize the comparison logic defined in the {@code Member} class,
     * ensuring consistency in the comparison across different types of memberships.
     *
     * @param other The Member object to be compared against this Premium member.
     * @return a negative integer, zero, or a positive integer as this Premium member
     * is less than, equal to, or greater than the specified Member object.
     */
    @Override
    public int compareTo(Member other) {
        return super.compareTo(other);
    }

    /**
     * Provides a string representation of the Premium member including the number of guest passes available.
     *
     * @return The string representation of the Premium member.
     */
    @Override
    public String toString() {
        String expirationMessage = isMembershipExpired() ?
                "Membership expired " + getExpire().toString() :
                "Membership expires " + getExpire().toString();
        String guestPassStatus = isMembershipExpired() ?
                "guest-pass remaining: not eligible" :
                "guest-pass remaining: " + guestPass;

        return String.format("%s:%s:%s, %s, Home Studio: %s, %s, %s, (Premium) %s",
                this.getProfile().getFname(),
                this.getProfile().getLname(),
                this.getProfile().getDob().toString(),
                expirationMessage,
                this.getHomeStudio().getCity().toUpperCase(),
                this.getHomeStudio().getZipCode(),
                this.getHomeStudio().getCounty().toUpperCase(),
                guestPassStatus);
    }

    /**
     * Sets the number of guest passes available to a premium member.
     * This method updates the number of guest passes a premium member can use. Guest passes allow
     * premium members to bring guests to the facility, providing flexibility and added value to the
     * membership.
     *
     * @param guestPass the number of guest passes to be set for the premium member. Must be non-negative.
     */
    public void setGuestPass(int guestPass) {
        this.guestPass = guestPass;
    }
}