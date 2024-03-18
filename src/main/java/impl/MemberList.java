package impl;

import data.*;
import enums.Location;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Represents a dynamic list for managing gym members. It supports various operations such as
 * adding, removing, searching, and sorting members based on different criteria.
 * Members are uniquely identified by their profiles (first name, last name, and date of birth).
 * Additional functionalities include loading member data from a file and printing member lists
 * according to specific sorting criteria.
 *
 * @author Sasanka Paththameistreege
 */
public class MemberList {
    /**
     * Constants defining initial capacity
     **/
    private static final int INITIAL_CAPACITY = 4;

    /**
     * Value indicating a member was not found
     **/
    private static final int NOT_FOUND = -1;

    /**
     * Array to store member objects
     **/
    private Member[] members;

    /**
     * The number of members currently in the list
     **/
    private int size;

    /**
     * Gets the current size of the member list.
     *
     * @return the number of members in the list
     */
    public int getSize() {
        return size;
    }

    /**
     * Constructor for creating an empty MemberList with initial capacity.
     */
    public MemberList() {
        members = new Member[INITIAL_CAPACITY];
        size = 0;
    }

    /**
     * Searches for a member in the list and returns the index if found.
     * Utilizes case-insensitive comparison for first and last names and exact match for date of birth.
     *
     * @param member The member to find in the list.
     * @return The index of the member if found, -1 otherwise.
     */
    private int find(Member member) {
        for (int i = 0; i < size; i++) {
            Profile existingProfile = members[i].getProfile();
            Profile newProfile = member.getProfile();
            if (existingProfile.getFname().equalsIgnoreCase(newProfile.getFname()) &&
                    existingProfile.getLname().equalsIgnoreCase(newProfile.getLname()) &&
                    existingProfile.getDob().equals(newProfile.getDob())) {
                return i;
            }
        }
        return NOT_FOUND;
    }

    /**
     * Increases the capacity of the member array when the current capacity is reached.
     * The capacity is increased by a constant factor.
     */
    private void grow() {
        Member[] temp = new Member[members.length + INITIAL_CAPACITY];
        for (int i = 0; i < size; i++) {
            temp[i] = members[i];
        }
        members = temp;
    }

    /**
     * Checks if the specified member is already present in the list.
     *
     * @param member The member to check for existence.
     * @return true if the member is present, false otherwise.
     */
    public boolean contains(Member member) {
        for (int i = 0; i < size; i++) {
            Profile existingProfile = members[i].getProfile();
            Profile newProfile = member.getProfile();
            if (existingProfile.getFname().equalsIgnoreCase(newProfile.getFname()) &&
                    existingProfile.getLname().equalsIgnoreCase(newProfile.getLname()) &&
                    existingProfile.getDob().equals(newProfile.getDob())) {
                return true;
            }
        }
        return false;
    }


    /**
     * Adds a new member to the list if not already present.
     * The list capacity is increased if necessary.
     *
     * @param member The member to be added.
     * @return true if the member was added successfully, false if the member already exists.
     */
    public boolean add(Member member) {
        if (find(member) != NOT_FOUND) {
            return false;
        }
        if (size == members.length) {
            grow();
        }
        members[size] = member;
        size++;
        return true;
    }

    /**
     * Removes the specified member from the list.
     *
     * @param member The member to be removed.
     * @return true if the member was successfully removed, false if the member was not found.
     */
    public boolean remove(Member member) {
        int index = find(member);
        if (index == NOT_FOUND) {
            return false;
        }
        for (int i = index; i < size - 1; i++) {
            members[i] = members[i + 1];
        }
        members[size - 1] = null;
        size--;
        return true;
    }

    /**
     * Loads members from a specified file. The file format is expected to be specific and
     * compatible with the method's parsing logic.
     *
     * @param file The file from which to load members.
     * @throws FileNotFoundException if the specified file does not exist.
     */
    public void load(File file) throws FileNotFoundException {
        Scanner scanner = new Scanner(file);
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] parts = line.split("\\s+");
            String type = parts[0];
            String firstName = parts[1];
            String lastName = parts[2];
            Date dob = new Date(parts[3]);
            Date expiration = new Date(parts[4]);
            Location location = Location.valueOf(parts[5].toUpperCase());
            Member member = switch (type) {
                case "B" -> new Basic(new Profile(firstName, lastName, dob), expiration, location);
                case "F" -> new Family(new Profile(firstName, lastName, dob), expiration, location);
                case "P" -> new Premium(new Profile(firstName, lastName, dob), expiration, location);
                default -> throw new IllegalArgumentException("Invalid member type");
            };
            this.add(member);
        }
        scanner.close();
    }

    /**
     * Generates and returns a string representation of all members currently in the list.
     * This method iterates through the member list, appending each member's string representation
     * to a {@link StringBuilder} object, resulting in a formatted list of members. The returned string
     * starts with a header line, includes each member on a new line, and ends with a footer line,
     * indicating the end of the member list.
     *
     * @return A string containing a formatted list of all members, each on a new line,
     * enclosed between header and footer lines.
     */
    public String getMemberListString() {
        StringBuilder sb = new StringBuilder("\n-list of members loaded-\n");
        for (int i = 0; i < size; i++) {
            sb.append(members[i].toString()).append("\n");
        }
        sb.append("-end of list-\n\n");
        return sb.toString();
    }


    /**
     * Generates and returns a string representation of the member list sorted by member profiles,
     * using a bubble sort algorithm. The sorting criteria prioritize the last name, then the first name,
     * and finally the date of birth of the members. The method starts by checking if the member list is empty,
     * appending a message if so. Otherwise, it sorts the members and appends each member's string representation
     * to a StringBuilder object. The resulting string includes a header indicating the sorting criteria, the sorted list of members,
     * each on a new line, and a footer marking the end of the list.
     *
     * @return A string containing the sorted list of members by their profiles, each on a new line,
     * enclosed between a header indicating the sorting criteria and a footer line.
     * If the collection is empty, a message indicating this is returned instead.
     */
    public String printByMember() {
        StringBuilder sb = new StringBuilder();
        if (size == 0) {
            sb.append("Collection is empty!");
            return sb.toString();
        }
        bubbleSort();
        sb.append("\n-list of members sorted by member profiles-\n");
        bubbleSort();
        sb.append(printMembers());
        sb.append("-end of list-\n");
        return sb.toString();
    }

    /**
     * Sorts the members using a bubble sort algorithm based on their profile information.
     */
    private void bubbleSort() {
        for (int i = 0; i < size - 1; i++) {
            for (int j = 0; j < size - i - 1; j++) {
                if (compareMembers(members[j], members[j + 1]) > 0) {
                    swap(j, j + 1);
                }
            }
        }
    }

    /**
     * Swaps two members in the array.
     *
     * @param i The index of the first member.
     * @param j The index of the second member.
     */
    private void swap(int i, int j) {
        Member temp = members[i];
        members[i] = members[j];
        members[j] = temp;
    }

    /**
     * Compares two members based on their profile information.
     *
     * @param m1 The first member.
     * @param m2 The second member.
     * @return A negative integer, zero, or a positive integer as the first argument is less than,
     * equal to, or greater than the second.
     */
    static int compareMembers(Member m1, Member m2) {
        if (m1 == null && m2 == null) {
            return 0;
        } else if (m1 == null) {
            return -1;
        } else if (m2 == null) {
            return 1;
        }

        int lastNameComparison = m1.getProfile().getLname().compareTo(m2.getProfile().getLname());
        if (lastNameComparison != 0) {
            return lastNameComparison;
        }

        int firstNameComparison = m1.getProfile().getFname().compareTo(m2.getProfile().getFname());
        if (firstNameComparison != 0) {
            return firstNameComparison;
        }

        return m1.getProfile().getDob().compareTo(m2.getProfile().getDob());
    }

    /**
     * Generates and returns a string representation of the member list sorted by county and zip code.
     * The sorting is performed first by county names in a case-insensitive manner. If two members belong
     * to the same county, they are then sorted by their zip codes. The method checks if the member list
     * is empty and returns a message indicating this if true. Otherwise, it performs the sorting and
     * constructs a formatted string that includes a header, the sorted list of members each on a new line,
     * and a footer marking the end of the list.
     *
     * @return A string containing the sorted list of members by county and zip code, each on a new line,
     * enclosed between a header and a footer line. If the collection is empty, a message
     * indicating this is returned instead.
     */
    public String printByCounty() {
        StringBuilder sb = new StringBuilder();
        if (size == 0) {
            sb.append("Collection is empty!");
            return sb.toString();
        }
        sb.append("\n-list of members sorted by county then zipcode-\n");
        for (int i = 0; i < members.length - 1; i++) {
            for (int j = 0; j < members.length - i - 1; j++) {
                if (compareMembersByCounty(members[j], members[j + 1]) > 0) {
                    Member temp = members[j];
                    members[j] = members[j + 1];
                    members[j + 1] = temp;
                }
            }
        }
        sb.append(printMembers());
        sb.append("-end of list-\n");
        return sb.toString();
    }

    /**
     * Compares two members based on their home studio's county and zip code.
     * Helper method for printByCounty.
     *
     * @param m1 The first member to compare.
     * @param m2 The second member to compare.
     * @return A negative integer, zero, or a positive integer as the first argument is less than,
     * equal to, or greater than the second, based on county and zip code.
     */

    private int compareMembersByCounty(Member m1, Member m2) {
        if (m1 == null && m2 == null) {
            return 0;
        } else if (m1 == null) {
            return -1;
        } else if (m2 == null) {
            return 1;
        }

        Location location1 = m1.getHomeStudio();
        Location location2 = m2.getHomeStudio();

        int countyComparison = location1.getCounty().compareToIgnoreCase(location2.getCounty());
        if (countyComparison != 0) {
            return countyComparison;
        }
        return location1.getZipCode().compareTo(location2.getZipCode());
    }

    /**
     * Generates and returns a string representation of all members along with their next due fees.
     * This method iterates over the member list, calculates each member's next due amount using
     * their specific billing method, and constructs a formatted string. The resulting string includes
     * a header indicating the purpose, the list of members with their next due amounts formatted
     * to two decimal places, and a footer line. If the member collection is empty, a message indicating
     * this is returned instead.
     *
     * @return A string containing the list of members with their next due fees, each member on a new line,
     * enclosed between header and footer lines. If the collection is empty, a message indicating
     * this is returned.
     */
    public String printFees() {
        StringBuilder sb = new StringBuilder();
        if (size == 0) {
            sb.append("Collection is empty!");
            return sb.toString();
        }
        sb.append("\n-list of members with next dues-\n");
        for (Member member : members) {
            if (member != null) {
                double nextDueAmount = member.bill();
                sb.append(member).append(" [next due: $").append(String.format("%.2f", nextDueAmount)).append("] \n");
            }
        }
        sb.append("-end of list-");
        return sb.toString();
    }

    /**
     * Generates and returns a string representation of the members in the list.
     * This utility method iterates over the members array and appends the string representation
     * of each member to a StringBuilder object. The method ensures each member is listed on a new line,
     * resulting in a cleanly formatted list of members. This method is useful for creating an overview
     * of all members without additional information such as due fees or sorting criteria.
     *
     * @return A string containing the details of each member in the list, each on a new line. If a member
     * is null (indicating an empty slot in the array), it is skipped in the output.
     */
    public String printMembers() {
        StringBuilder sb = new StringBuilder();
        for (Member member : members) {
            if (member != null) sb.append(member).append("\n");
        }
        return sb.toString();
    }

    /**
     * Retrieves a member from the list matching the given profile.
     *
     * @param profile The profile to match against members in the list.
     * @return The matching member if found, null otherwise.
     */
    public Member retrieveMember(Profile profile) {
        for (int i = 0; i < size; i++) {
            Profile existingProfile = members[i].getProfile();
            if (existingProfile.getFname().equalsIgnoreCase(profile.getFname()) &&
                    existingProfile.getLname().equalsIgnoreCase(profile.getLname()) &&
                    existingProfile.getDob().equals(profile.getDob())) {
                return members[i];
            }
        }
        return null;
    }

    /**
     * Adds a guest to the list of members. This method is used to dynamically add guests, potentially for tracking guest visits or temporary memberships. If the current array of members is full, it automatically increases its size to accommodate the new guest. This method assumes that a "guest" is treated similarly to a member for the purposes of list management.
     *
     * @param member The guest to be added to the list. This can be an instance of any class that extends Member, including guests with specific attributes if applicable.
     */
    public void addGuest(Member member) {
        if (size == members.length) {
            grow();
        }
        members[size] = member;
        size++;
    }
}

