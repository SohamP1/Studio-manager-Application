package data;

import enums.Location;
import impl.FitnessClass;

/**
 * Represents a member of the gym, encapsulating member details, membership expiration, and class registration.
 *
 * @author Soham Patel
 */
public class Member implements Comparable<Member> {

    /**
     * The profile information for this member, including name and date of birth.
     */
    private Profile profile;

    /**
     * The date on which the member's current membership is set to expire.
     */
    private Date expire;

    /**
     * The designated home studio location for this member, determining where they primarily attend classes.
     */
    private Location homeStudio;

    /**
     * The maximum number of fitness classes a member is allowed to register for within a certain period.
     */
    private static final int MAX_CLASSES = 10;

    /**
     * An array to store references to the fitness classes for which this member has registered.
     */
    private FitnessClass[] registeredClasses = new FitnessClass[MAX_CLASSES];

    /**
     * The current number of classes for which the member has registered. This count should not exceed MAX_CLASSES.
     */
    private int registeredClassCount = 0;

    /**
     * Constructs a member with specified profile, expiration date, and home studio.
     *
     * @param profile    Member's profile.
     * @param expire     Membership expiration date.
     * @param homeStudio Member's home studio.
     */

    public Member(Profile profile, Date expire, Location homeStudio) {
        this.profile = profile;
        this.expire = expire;
        this.homeStudio = homeStudio;
    }

    /**
     * Retrieves the profile associated with this member.
     *
     * @return Profile containing the member's first name, last name, and date of birth.
     */
    public Profile getProfile() {
        return profile;
    }

    /**
     * Retrieves the expiration date of the member's membership.
     *
     * @return The expiration date of the membership.
     */
    public Date getExpire() {
        return expire;
    }

    /**
     * Retrieves the home studio location of the member.
     *
     * @return The home studio location of the member.
     */
    public Location getHomeStudio() {
        return homeStudio;
    }

    /**
     * Calculates the billing amount for the member. This method should be overridden by subclasses.
     *
     * @return Billing amount.
     */
    public double bill() {

        return 0;
    }

    /**
     * Compares this member with another object for equality based on profile information.
     *
     * @param obj Object to compare.
     * @return true if the objects are equal, false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Member other = (Member) obj;
        return this.profile.equals(other.profile);
    }

    /**
     * Provides a string representation of this member, including profile and membership details.
     *
     * @return String representation of the member.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        String expirationStatus = isMembershipExpired() ? "Membership expired" : "Membership expires " + this.getExpire().toString();
        sb.append(String.format("%s:%s:%s, %s, Home Studio: %s, %s, %s",
                this.getProfile().getFname(),
                this.getProfile().getLname(),
                this.getProfile().getDob(),
                expirationStatus,
                this.getHomeStudio().getCity().toUpperCase(),
                this.getHomeStudio().getZipCode(),
                this.getHomeStudio().getCounty().toUpperCase()));
        return sb.toString();
    }

    /**
     * Checks if the membership has expired.
     *
     * @return true if expired, false otherwise.
     */
    public boolean isMembershipExpired() {
        Date today = new Date(Date.todayDate());
        return this.expire.compareTo(today) < 0;
    }

    /**
     * Compares this member with another member for ordering.
     *
     * @param other Member to compare.
     * @return Comparison result.
     */
    @Override
    public int compareTo(Member other) {
        int lastNameComparison = this.profile.getLname().compareTo(other.profile.getLname());
        if (lastNameComparison != 0) return lastNameComparison;

        int firstNameComparison = this.profile.getFname().compareTo(other.profile.getFname());
        if (firstNameComparison != 0) return firstNameComparison;

        return this.profile.getDob().compareTo(other.profile.getDob());
    }

    /**
     * Determines the type of membership the member has based on their class instance.
     *
     * @return A string representing the type of membership (BASIC, FAMILY, PREMIUM, UNKNOWN).
     */
    public String getMembershipType() {
        if (this instanceof Basic) {
            return "BASIC";
        } else if (this instanceof Family) {
            return "FAMILY";
        } else if (this instanceof Premium) {
            return "PREMIUM";
        } else {
            return "UNKNOWN";
        }
    }

    /**
     * Registers a class for the member.
     *
     * @param fitnessClass Class to register.
     */
    public void registerClass(FitnessClass fitnessClass) {
        if (registeredClassCount < registeredClasses.length) {
            registeredClasses[registeredClassCount++] = fitnessClass;
        } else {
            System.out.println("Maximum classes reached.");
        }
    }

    /**
     * Unregisters a class for the member.
     *
     * @param fitnessClass Class to deregister.
     */
    public void unregisterClass(FitnessClass fitnessClass) {
        for (int i = 0; i < registeredClassCount; i++) {
            if (registeredClasses[i].equals(fitnessClass)) {
                for (int j = i; j < registeredClassCount - 1; j++) {
                    registeredClasses[j] = registeredClasses[j + 1];
                }
                registeredClasses[registeredClassCount - 1] = null;
                registeredClassCount--;
                break;
            }
        }
    }


    /**
     * Retrieves the count of classes the member has registered for.
     *
     * @return The total number of registered classes for the member.
     */
    public int getRegisteredClassCount() {
        return registeredClassCount;
    }

    /**
     * Retrieves the array of FitnessClass objects the member has registered for.
     *
     * @return An array of registered FitnessClass objects.
     */
    public FitnessClass[] getRegisteredClasses() {
        return registeredClasses;
    }
}