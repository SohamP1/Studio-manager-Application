package impl;

import data.Member;
import enums.Instructor;
import enums.Location;
import enums.Offer;
import enums.Time;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Manages the schedule of fitness classes, including loading classes from a file,
 * growing the internal storage as needed, and printing the schedule. Classes are
 * identified and managed based on different attributes such as class name, instructor,
 * time, and location.
 *
 * @author Sasanka Paththameistreege
 */
public class Schedule {
    /**
     * Initial capacity for the array of fitness classes
     **/
    private static final int INITIAL_CAPACITY = 4;

    /**
     * Array to hold fitness class objects
     **/
    private FitnessClass[] classes;

    /**
     * Number of classes currently in the schedule
     **/
    private int numClasses;

    /**
     * Constructs an empty Schedule with an initial capacity for fitness classes.
     */
    public Schedule() {
        classes = new FitnessClass[INITIAL_CAPACITY];
        numClasses = 0;
    }

    /**
     * Dynamically increases the size of the classes array to accommodate more fitness classes.
     */
    private void grow() {
        FitnessClass[] temp = new FitnessClass[classes.length * 2];
        for (int i = 0; i < classes.length; i++) {
            temp[i] = classes[i];
        }
        classes = temp;
    }

    /**
     * Loads fitness classes from a specified file into the schedule. The file format should match
     * the expected pattern for class information, including the class name, instructor, time, and location.
     *
     * @param file The file from which to load the fitness classes.
     * @throws FileNotFoundException if the specified file does not exist.
     */
    public void load(File file) throws FileNotFoundException {
        Scanner scanner = new Scanner(file);
        while (scanner.hasNextLine()) {
            if (numClasses == classes.length) {
                grow();
            }
            String line = scanner.nextLine();
            String[] parts = line.split("\\s+");
            Offer classInfo = getOfferByClassName(parts[0]);
            if (classInfo == null) {
                continue;
            }
            Instructor instructor = Instructor.valueOf(parts[1].toUpperCase());
            Time time = Time.valueOf(parts[2].toUpperCase());
            Location studio = Location.valueOf(parts[3].toUpperCase());
            FitnessClass fitnessClass = new FitnessClass(classInfo, instructor, studio, time);
            classes[numClasses++] = fitnessClass;
        }
        scanner.close();
    }


    /**
     * Retrieves the Offer enum based on a class name string.
     *
     * @param className The name of the class to match with an Offer.
     * @return The matching Offer or null if no match is found.
     */
    private Offer getOfferByClassName(String className) {
        for (Offer offer : Offer.values()) {
            if (offer.getClassName().equalsIgnoreCase(className)) {
                return offer;
            }
        }
        return null;
    }

    /**
     * Prints the loaded schedule of fitness classes, including details such as class info, instructor,
     * time, and studio location.
     */
    public String getScheduleString() {
        StringBuilder sb = new StringBuilder();
        if(numClasses == 0) {
            sb.append("There are no schedule classes.");
        }
        else {
            sb.append("-list of class schedule-\n");
            for (int i = 0; i < numClasses; i++) {
                FitnessClass fitnessClass = classes[i];
                int hr = fitnessClass.getTime().getHour();
                int min = fitnessClass.getTime().getMinute();
                sb.append(String.format("%s - %s, %01d:%02d, %s%n",
                        fitnessClass.getClassInfo(),
                        fitnessClass.getInstructor(),
                        hr, min,
                        fitnessClass.getStudio().getCity().toUpperCase()));
            }
            sb.append("-end of class list.\n");
        }
        return sb.toString();
    }


    /**
     * Prints classes along with their attendees and guests. Each class is listed with its details
     * followed by the names of registered members and guests if any.
     */
    public String printClassWithAttendees() {
        StringBuilder sb = new StringBuilder();
        if(numClasses == 0) {
            sb.append("There are no schedule classes.");
        }
        else {
        for (int i = 0; i < numClasses; i++) {
            FitnessClass fitnessClass = classes[i];
            int hr = fitnessClass.getTime().getHour();
            int min = fitnessClass.getTime().getMinute();
            sb.append(String.format("%s - %s, %01d:%02d, %s%n",
                    fitnessClass.getClassInfo(),
                    fitnessClass.getInstructor(),
                    hr, min,
                    fitnessClass.getStudio().getCity().toUpperCase()));
            if (classes[i].getMembers().getSize() > 0) {
                sb.append("[Attendees]");
                sb.append(classes[i].getMembers().getMemberListString());
            }
            if(classes[i].getGuests().getSize() > 0) {
                sb.append("[Guests]");
                sb.append(classes[i].getGuests().getMemberListString());
            }
        }
        }
        return sb.toString();
    }

    /**
     * Searches for a fitness class matching specific criteria including offer, instructor, and location.
     *
     * @param offer      The class offer to match.
     * @param instructor The instructor to match.
     * @param location   The location to match.
     * @return The matching FitnessClass or null if no match is found.
     */
    public FitnessClass findClassByCriteria(Offer offer, Instructor instructor, Location location) {
        for (int i = 0; i < numClasses; i++) {
            FitnessClass fc = classes[i];
            if (fc.getClassInfo() == offer && fc.getInstructor() == instructor && fc.getStudio() == location) {
                return fc;
            }
        }
        return null;
    }

    /**
     * Checks for a time conflict between a member's already registered fitness classes and a new class time.
     * This method iterates through all registered classes of the given member and compares their times
     * with the time of a new class being considered for registration. It is used to ensure that members
     * do not register for overlapping classes.
     *
     * @param member       The member whose registered classes are to be checked for a time conflict.
     * @param newClassTime The time of the new class being considered for registration.
     * @return true if there is a time conflict, meaning the member has already registered for a class
     * at the new class's time; false otherwise.
     */
    public boolean checkForMemberTimeConflict(Member member, Time newClassTime) {
        for (int i = 0; i < member.getRegisteredClassCount(); i++) {
            FitnessClass registeredClass = member.getRegisteredClasses()[i];
            if (registeredClass != null && registeredClass.getTime().equals(newClassTime)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Retrieves a list of all scheduled fitness classes.
     * This method goes through the array of classes and collects all non-null
     * fitness class objects into an ArrayList. This allows for easy access
     * to all current classes in the schedule, which can be useful for
     * displaying class information or performing other operations that require
     * a list of classes.
     *
     * @return An ArrayList containing all non-null FitnessClass objects currently
     *         scheduled. If there are no classes, returns an empty list.
     */
    public ArrayList<FitnessClass> getClasses() {
        ArrayList<FitnessClass> classList = new ArrayList<>();

        // Iterate through the classes array and add non-null elements to the ArrayList
        for (int i = 0; i < numClasses; i++) {
            if (classes[i] != null) {
                classList.add(classes[i]);
            }
        }

        return classList;
    }
}
