package impl;

import data.Member;
import enums.Instructor;
import enums.Location;
import enums.Offer;
import enums.Time;

/**
 * Represents a fitness class in the gym management system.
 * Each fitness class is associated with a specific class type, instructor, location, time, and lists of members and guests.
 *
 * @author Sasanka Paththameistreege
 */
public class FitnessClass {
    /** The type of fitness class being offered, such as Pilates, Spinning, or Cardio. */
    private Offer classInfo;

    /** The instructor leading the fitness class, ensuring quality and expertise in the session. */
    private Instructor instructor;

    /** The location where the fitness class is held, identifying the studio's physical address or name. */
    private Location studio;

    /** The time slot when the fitness class takes place, including morning, afternoon, or evening sessions. */
    private Time time;

    /** A list of members who have registered to attend the fitness class, tracking attendance and participation. */
    private MemberList members;

    /** A list of guests attending the class alongside members, often utilizing guest passes for access. */
    private MemberList guests;


    /**
     * Constructs a FitnessClass with specified class information, instructor, location, and time.
     *
     * @param classInfo  Specific type of class being offered.
     * @param instructor Instructor leading the class.
     * @param studio     Location where the class is held.
     * @param time       Time slot of the class.
     */
    public FitnessClass(Offer classInfo, Instructor instructor, Location studio, Time time) {
        this.classInfo = classInfo;
        this.instructor = instructor;
        this.studio = studio;
        this.time = time;
        this.members = new MemberList();
        this.guests = new MemberList();
    }



    /**
     * Retrieves the type of fitness class offered.
     *
     * @return The class type as an Offer enum.
     */
    public Offer getClassInfo() {
        return classInfo;
    }

    /**
     * Retrieves the instructor leading the fitness class.
     *
     * @return The instructor as an Instructor enum.
     */
    public Instructor getInstructor() {
        return instructor;
    }

    /**
     * Retrieves the location of the fitness class.
     *
     * @return The studio location as a Location enum.
     */
    public Location getStudio() {
        return studio;
    }

    /**
     * Retrieves the time slot of the fitness class.
     *
     * @return The class time as a Time enum.
     */
    public Time getTime() {
        return time;
    }

    /**
     * Retrieves the list of members registered for the fitness class.
     *
     * @return A MemberList containing the members attending the class.
     */
    public MemberList getMembers() {
        return members;
    }

    /**
     * Retrieves the list of guests attending the fitness class.
     *
     * @return A MemberList containing the guests attending the class.
     */
    public MemberList getGuests() {
        return guests;
    }


    /**
     * Adds a guest to the fitness class.
     *
     * @param member The guest member to add.
     */
    public void addGuest(Member member) {
        guests.addGuest(member);
    }

    /**
     * Removes a guest from the fitness class.
     *
     * @param member The guest member to remove.
     * @return true if the guest was successfully removed; false otherwise.
     */
    public boolean removeGuest(Member member) {
        return guests.remove(member);
    }

    /**
     * Checks if a member is already registered for this fitness class.
     *
     * @param member The member to check.
     * @return true if the member is already registered; false otherwise.
     */
    public boolean isMemberRegistered(Member member) {
        return members.contains(member);
    }

    /**
     * Checks if a guest is already registered for this fitness class.
     *
     * @param member The member to check.
     * @return true if the guest is already registered; false otherwise.
     */
    public boolean isGuestRegistered(Member member) {
        return guests.contains(member);
    }

    /**
     * Attempts to add a member to this fitness class.
     *
     * @param member The member to add.
     * return true if the member was successfully added; false if the member is already registered.
     */
    public void addMember(Member member) {
        if (isMemberRegistered(member)) {
            return;
        }
        members.add(member);
    }

    /**
     * Removes a member from the fitness class.
     *
     * @param member The member to remove.
     * @return true if the member was successfully removed; false otherwise.
     */
    public boolean removeMember(Member member) {
        return members.remove(member);
    }

}
