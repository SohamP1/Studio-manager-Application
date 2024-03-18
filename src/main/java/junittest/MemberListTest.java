package junittest;

import data.*;
import enums.Location;
import impl.MemberList;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * The {@code MemberListTest} class is designed for testing the functionality of the {@code MemberList} class.
 * It includes a series of test methods that verify the correct behavior of {@code MemberList}'s methods under various conditions.
 * The default constructor of this class is used implicitly by the testing framework to instantiate the test class before
 * executing the test methods. This setup facilitates the preparation of a controlled testing environment for the {@code MemberList} functionalities.
 * @author Soham Patel
 */
public class MemberListTest {

    /**
     * A static reference to a {@link MemberList} object used for testing purposes.
     * This member list is intended to be shared across all test cases within the
     * {@code MemberListTest} class. It provides a common setup for managing a collection
     * of {@link Member} objects that can be used to test the functionalities and methods
     * of the {@code MemberList} class in a consistent manner.
     */
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