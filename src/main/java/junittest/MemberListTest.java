package junittest;

import data.*;
import enums.Location;
import impl.MemberList;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Unit tests for the MemberList class. This class tests the functionality of adding and removing
 * members from the MemberList. It checks for both successful and failed additions and removals,
 * including scenarios with basic, family, and premium members, ensuring that duplicates are not
 * added and that removals behave as expected.
 *
 * @author Soham Patel
 */
public class MemberListTest {

    static MemberList members = new MemberList();

    /**
     * test method to add basic Member.
     * accepted output - true
     * actual output - true
     */
    @Test
    public void addBasicMember () {

        Basic newMember = new Basic(new Profile("Jerry", "Brown", new Date("6/30/1979")), new Date("01/12/2025"), Location.BRIDGEWATER);
        assertTrue(members.add(newMember));
    }

    /**
     * test method which adds the basic member which is already in the list.
     * accepted output - false
     * actual output - false
     */
    @Test
    public void failAddingBasicMember () {

        Basic newMember = new Basic(new Profile("Jerry", "Brown", new Date("6/30/1979")), new Date("01/12/2025"), Location.BRIDGEWATER);
        assertFalse(members.add(newMember));
    }


    /**
     * test method to add family Member.
     * accepted output - true
     * actual output - true
     */
    @Test
    public void addFamilyMember () {
        Family newMember = new Family(new Profile("Jane", "Doe", new Date("5/1/1996")), new Date("01/12/2014"), Location.BRIDGEWATER);
        assertTrue(members.add(newMember));
    }

    /**
     * test method which adds the family member which is already in the list.
     * accepted output - false
     * actual output - false
     */
    @Test
    public void failAddingFamilyMember () {
        Family newMember = new Family(new Profile("Jane", "Doe", new Date("5/1/1996")), new Date("01/12/2014"), Location.BRIDGEWATER);
        assertFalse(members.add(newMember));
    }

    /**
     * test method which adds the premium.
     * accepted output - true
     * actual output - true
     */
    @Test
    public void addPremiumMember () {
        Premium newMember = new Premium(new Profile("Mary", "Lindsey", new Date("12/01/1989")), new Date("01/10/2024"), Location.BRIDGEWATER);
        assertTrue(members.add(newMember));
    }

    /**
     * test method which adds the premium member which is already in the list.
     * accepted output - false
     * actual output - false
     */
    @Test
    public void failAddingPremiumMember () {
        Premium newMember = new Premium(new Profile("Mary", "Lindsey", new Date("12/01/1989")), new Date("01/10/2024"), Location.BRIDGEWATER);
        assertFalse(members.add(newMember));
    }

    /**
     * test method to remove premium member form the list.
     * accepted output - true
     * actual output - true
     */
    @Test
    public void successfullyRemoveMember() {
        Premium newMember = new Premium(new Profile("Mary", "Lindsey", new Date("12/01/1989")), new Date("01/10/2024"), Location.BRIDGEWATER);
        assertTrue(members.remove(newMember));

    }

    /**
     * test method which tries to remove the premium member which is not in the list.
     * accepted output - false
     * actual output - false
     */
    @Test
    public void failRemovingMember () {
        Premium newMember = new Premium(new Profile("Mary", "Lindsey", new Date("12/01/1989")), new Date("01/10/2024"), Location.BRIDGEWATER);
        assertFalse(members.remove(newMember));
    }
}