package data;

import enums.Location;

/**
 * Represents a Family membership within the fitness club system.
 * This class extends Member and includes a single-use guest pass feature.
 *
 * @author Soham Patel
 */
public class Family extends Member {

    /**
     * The standard cost for one month of membership, not accounting for any additional fees or services.
     */
    private static final double ONE_MONTH_MEMBERSHIP_COST = 49.99;

    /**
     * Indicates whether a guest pass is available for use. A value of true means a guest pass is available,
     * whereas false indicates it is not.
     */
    private boolean guest;

    /**
     * Constructs a Family member with a profile, expiration date, and home studio location.
     *
     * @param profile    The member's profile information.
     * @param expire     The expiration date of the membership.
     * @param homeStudio The home studio location of the member.
     */
    public Family(Profile profile, Date expire, Location homeStudio) {
        super(profile, expire, homeStudio);
        this.guest = true;
    }

    /**
     * Calculates and returns the bill amount for Family membership.
     *
     * @return The bill amount for the next period.
     */
    @Override
    public double bill() {
        return ONE_MONTH_MEMBERSHIP_COST * 3;
    }

    /**
     * Marks the guest pass as used, indicating that a guest has attended a class. This method is called
     * when a guest uses their one-time pass to attend a specific class, and sets the availability of the
     * guest pass to false.
     */
    public void takeAttendanceOfGuest() {
        this.guest = false;
    }

    /**
     * Resets the guest pass to available, allowing it to be used again. This method can be used in scenarios
     * where a guest pass was mistakenly marked as used or if the member's guest pass privileges are renewed.
     */
    public void removeAttendanceOfGuest() {
        this.guest = true;
    }

    /**
     * Checks if a guest pass is available.
     *
     * @return true if the guest pass is available, otherwise false.
     */
    public boolean hasGuestPass() {
        return this.guest;
    }

    /**
     * Provides a string representation of the Family member including the guest pass status.
     *
     * @return A string summary of the Family member's details.
     */
    @Override
    public String toString() {
        String expirationMessage = isMembershipExpired() ?
                "Membership expired " + getExpire().toString() :
                "Membership expires " + getExpire().toString();
        String guestPassStatus;
        if (isMembershipExpired()) {
            guestPassStatus = "guest-pass remaining: not eligible";
        } else {
            guestPassStatus = "guest-pass remaining: " + (this.guest ? "1" : "0");
        }

        return String.format("%s:%s:%s, %s, Home Studio: %s, %s, %s, (Family) %s",
                this.getProfile().getFname(),
                this.getProfile().getLname(),
                this.getProfile().getDob().toString(),
                expirationMessage,
                this.getHomeStudio().getCity().toUpperCase(),
                this.getHomeStudio().getZipCode(),
                this.getHomeStudio().getCounty().toUpperCase(),
                guestPassStatus);
    }


}
