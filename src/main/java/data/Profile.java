package data;

/**
 * Represents the profile of a member in the system, including first name, last name, and date of birth.
 * @author Soham Patel
 */
public class Profile implements Comparable<Profile>{
    /**
     * The first name of the member. This is a part of the member's personal information used for identification
     * and personalization purposes.
     */
    private final String fname;

    /**
     * The last name of the member. Along with the first name, it forms the full name of the member, used for
     * identification and record-keeping.
     */
    private final String lname;

    /**
     * The date of birth of the member. This information is used for age verification and may affect eligibility
     * for certain programs or services.
     */
    private final Date dob;

    /**
     * Constructs a Profile with specified first name, last name, and date of birth.
     *
     * @param fname First name of the member.
     * @param lname Last name of the member.
     * @param dob Date of birth of the member.
     */
    public Profile(String fname, String lname, Date dob) {
        this.fname = fname;
        this.lname = lname;
        this.dob = dob;
    }

    /**
     * Retrieves the first name of the member.
     *
     * @return The first name of the member.
     */
    public String getFname() {
        return fname;
    }

    /**
     * Retrieves the last name of the member.
     *
     * @return The last name of the member.
     */
    public String getLname() {
        return lname;
    }

    /**
     * Retrieves the date of birth of the member.
     *
     * @return The date of birth of the member.
     */
    public Date getDob() {
        return dob;
    }


    /**
     * Compares this profile to another profile based on first name, last name, and then date of birth.
     *
     * @param o The profile to compare to.
     * @return A negative integer, zero, or a positive integer as this profile is less than, equal to, or greater than the specified profile.
     */
    @Override
    public int compareTo(Profile o) {
        int fnameComparison = this.fname.compareToIgnoreCase(o.fname);
        if (fnameComparison != 0) {
            if (fnameComparison > 0) {
                return 1;
            }
            else {
                return -1;
            }
        }
        int lnameComparison = this.lname.compareToIgnoreCase(o.lname);
        if (lnameComparison != 0) {
            if (lnameComparison > 0) {
                return 1;
            }
            else {
                return -1;
            }
        }
        return this.dob.compareTo(o.dob);
    }


    /**
     * Checks if this profile is equal to another object.
     *
     * @param profile The object to compare with this profile.
     * @return true if the specified object is a profile with the same first name, last name, and date of birth; false otherwise.
     */
    @Override
    public boolean equals(Object profile) {
        if(profile instanceof Profile) {
            return this.fname.compareToIgnoreCase(((Profile) profile).fname) == 0 &&
                    this.lname.compareToIgnoreCase(((Profile) profile).lname) == 0 &&
                    this.dob.compareTo(((Profile) profile).dob) == 0;
        }
        return false;
    }

    /**
     * Returns a string representation of the profile.
     *
     * @return A string that represents the profile, formatted as "fname:lname:dob".
     */
    @Override
    public String toString() {
        return fname+":"+lname+":"+dob;
    }


}