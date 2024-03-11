package impl;

import data.*;
import enums.Instructor;
import enums.Location;
import enums.Offer;


import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.StringTokenizer;

/**
 * The StudioManager class is responsible for managing the operations of a fitness studio, including handling members,
 * scheduling classes, and processing member and guest registrations for classes. It maintains a list of members and a
 * class schedule, offering functionalities to add and remove members, register members and guests for classes, and handle
 * various administrative tasks such as displaying the class schedule and printing membership fees.
 * Key functionalities include:
 * - Loading initial member and class schedule data from files.
 * - Processing user commands for various operations like adding members, canceling memberships, and taking class attendance.
 * - Supporting different types of memberships (Basic, Family, Premium) with distinct registration and cancellation processes.
 * - Handling guest registrations for classes, including validating guest passes and managing attendance.
 * Usage involves initializing the class to load necessary data and then invoking the run method to start processing
 * commands from the console input. The class is designed to be interactive and responds to a predefined set of commands.
 * @author Sasanka Paththameistreege
 */
public class StudioManager {

    /**
     * The list of members registered in the studio. This includes all membership types (e.g., Basic, Family, Premium).
     */
    private final MemberList memberList;

    /**
     * The schedule of fitness classes offered by the studio. This includes class times, instructors, and locations.
     */
    private final Schedule schedule;

    /**
     * Constructs a new StudioManager instance, initializing the member list and class schedule to their default states.
     * It also triggers the loading of initial data into these structures, preparing the studio for operation.
     */
    public StudioManager() {
        memberList = new MemberList();
        schedule = new Schedule();
        //loadInitialData();
    }

//    /**
//     * Initializes the studio manager by loading initial data for members and class schedules.
//     */
//    private void loadInitialData() {
//        try {
//            memberList.load(new File("src/main/java/test/memberList.txt"));
//            memberList.printMemberList();
//            schedule.load(new File("src/main/java/test/classSchedule.txt"));
//            schedule.printSchedule();
//            System.out.println("Studio Manager is up running...");
//        } catch (FileNotFoundException e) {
//            System.err.println("Error loading initial files: " + e.getMessage());
//        }
//    }

    /**
     * Starts the command processing loop to handle user inputs from the console.
     * It listens for commands until the 'Q' command is issued to quit the application.
     */
    public void run() {
        try (Scanner scanner = new Scanner(System.in)) {
            String input;
            while (!(input = scanner.nextLine().trim()).equals("Q")) {
                processCommand(input);
            }
        } catch (Exception e) {
            System.err.println("An error occurred during execution: " + e.getMessage());
        } finally {
            System.out.println("Studio Manager terminated.");
        }
    }

    /**
     * Processes a single command input by the user, directing to the appropriate action
     * based on the command type. It handles the initial tokenization of the input and
     * delegates the execution of the command to another method. If an error occurs during
     * the command processing, it catches and reports the error.
     *
     * @param input The raw command input from the user.
     */
    private void processCommand(String input) {
        StringTokenizer tokenizer = new StringTokenizer(input);
        if (!tokenizer.hasMoreTokens()) return;
        String command = tokenizer.nextToken();
        try {
            executeCommand(command, tokenizer);
        } catch (Exception e) {
            System.err.println("An error occurred processing the command: " + e.getMessage());
        }
    }

    /**
     * Executes the specific command identified by the 'processCommand' method. This method
     * contains a switch statement that calls the corresponding method based on the command
     * type. It is designed to simplify the main command processing method by handling the
     * execution logic for each command.
     *
     * @param command The command to execute, extracted from the user input.
     * @param tokenizer StringTokenizer containing any additional parameters needed for the command.
     */
    private void executeCommand(String command, StringTokenizer tokenizer) {
        switch (command) {
            case "AB":
                addBasicMember(tokenizer);
                break;
            case "AF":
                addFamilyMember(tokenizer);
                break;
            case "AP":
                addPremiumMember(tokenizer);
                break;
            case "C":
                cancelMembership(tokenizer);
                break;
            case "S":
                displayClassSchedule();
                break;
            case "PM":
                printMembersByProfile();
                break;
            case "PC":
                printMembersByCounty();
                break;
            case "PF":
                printMembershipFees();
                break;
            case "R":
                takeClassAttendance(tokenizer);
                break;
            case "U":
                removeMemberFromClass(tokenizer);
                break;
            case "RG":
                registerGuest(tokenizer);
                break;
            case "UG":
                removeGuestFromClass(tokenizer);
                break;
            case "":
                break;
            default:
                System.out.println(command + " is an invalid command!");
        }
    }

    /**
     * Adds a basic member to the studio based on provided information.
     *
     * @param tokens A StringTokenizer containing member information.
     */
    private void addBasicMember(StringTokenizer tokens) {
        if (tokens.countTokens() < 4) {
            System.out.println("Missing data tokens.");
            return;
        }

        String firstName = tokens.nextToken();
        String lastName = tokens.nextToken();
        String dobString = tokens.nextToken();
        String city = tokens.nextToken();

        Date dob = createDateFromToken(dobString);
        Location location = createLocation(city);

        if (dob != null && location != null) {
            Profile profile = new Profile(firstName, lastName, dob);
            Date expire = calculateExpirationDate();

            Basic newMember = new Basic(profile, expire, location);
            addBasicMemberToDatabaseForBasic(newMember);
        }
    }

    /**
     * Creates a Date object from a provided string, ensuring it represents a valid, past date that meets
     * age eligibility criteria. This method validates the date format, checks for future dates, validates
     * the calendar date, and verifies age eligibility based on the date of birth (DOB).
     *
     * @param dobString The date string to convert into a Date object.
     * @return A valid Date object if the input meets all criteria, null otherwise.
     */
    private Date createDateFromToken(String dobString) {
        Date dob;
        try {
            dob = new Date(dobString);
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid date format: " + dobString);
            return null;
        }

        if (dob.isFutureDate()) {
            System.out.println("DOB " + dobString + ": cannot be today or a future date!");
            return null;
        }
        if (!dob.isValid()) {
            System.out.println("DOB " + dobString + ": invalid calendar date!");
            return null;
        }
        if (!dob.isEligible()) {
            System.out.println("DOB " + dobString + ": must be 18 or older to join!");
            return null;
        }

        return dob;
    }

    /**
     * Creates a Location enum instance from a string representing a city. This method ensures the city
     * name is a valid, recognized studio location within the system.
     *
     * @param city The city name to convert into a Location enum.
     * @return A Location enum instance if the city is valid, null otherwise.
     */
    private Location createLocation(String city) {
        try {
            return Location.valueOf(city.toUpperCase());
        } catch (IllegalArgumentException e) {
            System.out.println(city + ": invalid studio location!");
            return null;
        }
    }

    /**
     * Calculates an expiration date for a generic membership, setting it to one month from the current date.
     * This method provides a standard way to calculate membership expiration for unspecified membership types.
     *
     * @return The expiration Date set one month from the current date.
     */
    private Date calculateExpirationDate() {
        Date today = Date.getCurrentDate();
        return today.calculateOneMonthLater();
    }

    /**
     * Adds a Basic member to the studio's member database, performing checks for duplicate profiles. This method
     * prints feedback indicating whether the addition was successful or if the member already exists in the database.
     *
     * @param newMember The new Basic member to be added to the database.
     */
    private void addBasicMemberToDatabaseForBasic(Basic newMember) {
        if (!memberList.add(newMember)) {
            System.out.println(newMember.getProfile().getFname() + " " + newMember.getProfile().getLname() +
                    " is already in the member database.");
        } else {
            System.out.println(newMember.getProfile().getFname() + " " + newMember.getProfile().getLname() + " added.");
        }
    }

    /**
     * Adds a family member to the studio, extending basic membership with family-specific benefits.
     *
     * @param tokens A StringTokenizer containing member information.
     */
    private void addFamilyMember(StringTokenizer tokens) {
        if (tokens.countTokens() < 4) {
            System.out.println("Missing data tokens.");
            return;
        }

        String firstName = tokens.nextToken();
        String lastName = tokens.nextToken();
        String dobString = tokens.nextToken();
        String city = tokens.nextToken();

        if (!isValidDateFormat(dobString)) {
            System.out.println("The date contains characters.");
            return;
        }

        Date dob = createDateFromToken(dobString);
        Location location = createLocation(city);

        if (dob != null && location != null) {
            Profile profile = new Profile(firstName, lastName, dob);
            Date expire = calculateExpirationDateForFamily();

            Family newMember = new Family(profile, expire, location);
            addFamilyMemberToDatabase(newMember);
        }
    }

    /**
     * Calculates the expiration date for a Family membership based on the current date.
     * This method sets the expiration date to three months from today, adhering to the policy
     * for Family memberships, which typically offer a shorter duration compared to Premium memberships.
     *
     * @return The expiration Date set three months from the current date.
     */
    private Date calculateExpirationDateForFamily() {
        Date today = Date.getCurrentDate();
        return today.calculateThreeMonthsLater();
    }

    /**
     * Adds a Family member to the studio's member database, ensuring unique membership through profile comparison.
     * If the member already exists in the database, a message indicating duplication is printed. Otherwise,
     * a confirmation message is printed, indicating the successful addition of the new Family member.
     *
     * @param newMember The new Family member to be added to the database.
     */
    private void addFamilyMemberToDatabase(Family newMember) {
        if (!memberList.add(newMember)) {
            System.out.println(newMember.getProfile().getFname() + " " + newMember.getProfile().getLname() +
                    " is already in the member database.");
        } else {
            System.out.println(newMember.getProfile().getFname() + " " + newMember.getProfile().getLname() + " added.");
        }
    }

    /**
     * Adds a premium member to the studio, offering the highest level of benefits.
     *
     * @param tokens A StringTokenizer containing member information.
     */
    private void addPremiumMember(StringTokenizer tokens) {
        if (tokens.countTokens() < 4) {
            System.out.println("Missing data tokens.");
            return;
        }

        String firstName = tokens.nextToken();
        String lastName = tokens.nextToken();
        String dobString = tokens.nextToken();
        String city = tokens.nextToken();

        if (!isValidDateFormat(dobString)) {
            System.out.println("The date contains characters.");
            return;
        }

        Date dob = createDateFromToken(dobString);
        Location location = createLocation(city);

        if (dob != null && location != null) {
            Profile profile = new Profile(firstName, lastName, dob);
            Date expire = calculateExpirationDateForPremium();

            Premium newMember = new Premium(profile, expire, location);
            addPremiumMemberToDatabase(newMember);
        }
    }

    /**
     * Calculates the expiration date for a Premium membership based on the current date.
     * This method specifically sets the expiration date to eleven months from the current date,
     * aligning with the policy for Premium memberships.
     *
     * @return The expiration Date set eleven months from the current date.
     */
    private Date calculateExpirationDateForPremium() {
        Date today = Date.getCurrentDate();
        return today.calculateTwelveMonthsLater();
    }

    /**
     * Adds a Premium member (considered here as a Family member for context) to the studio's member database.
     * It checks for duplicates based on the member's profile and prints a message indicating whether the addition
     * was successful or if the member already exists in the database.
     *
     * @param newMember The new Premium member to be added to the database.
     */
    private void addPremiumMemberToDatabase(Premium newMember) {
        if (!memberList.add(newMember)) {
            System.out.println(newMember.getProfile().getFname() + " " + newMember.getProfile().getLname() +
                    " is already in the member database.");
        } else {
            System.out.println(newMember.getProfile().getFname() + " " + newMember.getProfile().getLname() + " added.");
        }
    }

    /**
     * Cancels a membership based on the provided profile information.
     *
     * @param tokens A StringTokenizer containing member identification details.
     */
    private void cancelMembership(StringTokenizer tokens) {
        if (tokens.countTokens() < 3) {
            System.out.println("Not enough information provided.");
            return;
        }
        String firstName = tokens.nextToken();
        String lastName = tokens.nextToken();
        String dobString = tokens.nextToken();
        if (!isValidDateFormat(dobString)) {
            System.out.println("The date contains characters.");
            return;
        }
        Date dob = new Date(dobString);
        Profile profile = new Profile(firstName, lastName, dob);

        Member member = new Member(profile, null, null);
        boolean removed = memberList.remove(member);

        if (removed) {
            System.out.println(firstName + " " + lastName + " removed.");
        } else {
            System.out.println(firstName + " " + lastName + " is not in the member database.");
        }
    }

    /**
     * Checks if the provided date string matches a valid date format.
     * The valid date format expected is MM/DD/YYYY, which includes one or two digits for the month and day,
     * and exactly four digits for the year. This method uses a regular expression to validate the format.
     *
     * @param dateString The date string to be validated.
     * @return true if the date string matches the valid format, false otherwise.
     */
    private boolean isValidDateFormat(String dateString) {
        String datePattern = "\\d{1,2}/\\d{1,2}/\\d{4}";
        return dateString.matches(datePattern);
    }

    /**
     * Displays the current class schedule along with registered members.
     */
    private void displayClassSchedule() {
        System.out.println("-Fitness classes-");

        schedule.printClassWithAttendees();

        System.out.println("-end of class list.\n");
    }

    /**
     * Prints a list of members sorted by their profile information.
     */
    private void printMembersByProfile() {
        memberList.printByMember();
    }

    /**
     * Prints a list of members grouped by their county of residence.
     */
    private void printMembersByCounty() {
        memberList.printByCounty();
    }

    /**
     * Calculates and prints the total membership fees for all members.
     */
    private void printMembershipFees() {
        memberList.printFees();
    }

    /**
     * Registers a member's attendance for a class, handling various membership and registration checks.
     *
     * @param tokens A StringTokenizer containing class and member information for attendance registration.
     */
    private void takeClassAttendance(StringTokenizer tokens) {
        if (!hasSufficientTokens(tokens)) {
            return;
        }
        String className = tokens.nextToken();
        String instructorName = tokens.nextToken();
        String studioName = tokens.nextToken();
        String firstName = tokens.nextToken();
        String lastName = tokens.nextToken();
        String dobString = tokens.nextToken();
        if (!validateInputs(className, instructorName, studioName)) {
            return;
        }
        Profile profile = createProfile(firstName, lastName, dobString);
        Member member = retrieveMember(profile);
        if (member == null) {
            printMemberNotFound(firstName, lastName, dobString);
            return;
        }
        if (isMembershipExpired(member)) {
            return;
        }
        if (!isValidHomeStudio(member, studioName)) {
            return;
        }
        FitnessClass fitnessClass = findFitnessClass(className, instructorName, studioName);
        if (fitnessClass == null) {
            printClassDoesNotExist(className, instructorName, studioName);
            return;
        }
        if (isMemberAlreadyRegistered(fitnessClass, member)) {
            return;
        }
        if (hasTimeConflict(fitnessClass, member)) {
            return;
        }
        recordAttendance(fitnessClass, member);
    }

    /**
     * Validates if the provided StringTokenizer has a sufficient number of tokens to proceed with the operation.
     * This method ensures that the command has all required information before processing.
     *
     * @param tokens StringTokenizer containing the command and parameters.
     * @return true if the number of tokens is sufficient, false otherwise.
     */
    private boolean hasSufficientTokens(StringTokenizer tokens) {
        if (tokens.countTokens() < 6) {
            System.out.println("Missing data tokens.");
            return false;
        }
        return true;
    }

    /**
     * Creates a profile instance from provided details including first name, last name, and date of birth.
     *
     * @param firstName The first name of the member.
     * @param lastName The last name of the member.
     * @param dobString The date of birth of the member in String format.
     * @return A new Profile instance.
     */
    private Profile createProfile(String firstName, String lastName, String dobString) {
        return new Profile(firstName, lastName, new Date(dobString));
    }

    /**
     * Retrieves a member from the member list based on the provided profile.
     *
     * @param profile The profile to search for in the member list.
     * @return The Member instance if found, null otherwise.
     */
    private Member retrieveMember(Profile profile) {
        return memberList.retrieveMember(profile);
    }

    /**
     * Prints a message indicating that the member was not found in the database.
     *
     * @param firstName The first name of the member.
     * @param lastName The last name of the member.
     * @param dobString The date of birth of the member.
     */
    private void printMemberNotFound(String firstName, String lastName, String dobString) {
        System.out.println(firstName + " " + lastName + " " + dobString + " is not in the member database.");
    }

    /**
     * Checks if the member's membership has expired.
     *
     * @param member The member whose membership status is to be checked.
     * @return true if the membership has expired, false otherwise.
     */
    private boolean isMembershipExpired(Member member) {
        if (member.isMembershipExpired()) {
            System.out.println(member.getProfile().getFname() + " " + member.getProfile().getLname() +
                    " " + member.getProfile().getDob() + " membership expired.");
            return true;
        }
        return false;
    }

    /**
     * Validates if the member's home studio matches the specified studio for attending a class.
     *
     * @param member The member to validate.
     * @param studioName The name of the studio where the class is held.
     * @return true if the home studio matches or the condition is not applicable, false otherwise.
     */
    private boolean isValidHomeStudio(Member member, String studioName) {
        Location homeStudio = member.getHomeStudio();
        if (member instanceof Basic && !studioName.equalsIgnoreCase(homeStudio.name())) {
            System.out.println(member.getProfile().getFname() + " " + member.getProfile().getLname() +
                    " is attending a class at " + studioName.toUpperCase() + " - [" + member.getMembershipType() +
                    "] home studio at " + member.getHomeStudio().getCity().toUpperCase());
            return false;
        }
        return true;
    }

    /**
     * Finds a fitness class based on the specified criteria.
     *
     * @param className The name of the class.
     * @param instructorName The name of the instructor.
     * @param studioName The name of the studio.
     * @return The FitnessClass instance if found, null otherwise.
     */
    private FitnessClass findFitnessClass(String className, String instructorName, String studioName) {
        return schedule.findClassByCriteria(Offer.valueOf(className.toUpperCase()), Instructor.valueOf(instructorName.toUpperCase()), Location.valueOf(studioName.toUpperCase()));
    }

    /**
     * Prints a message indicating that the specified fitness class does not exist.
     *
     * @param className The name of the class.
     * @param instructorName The name of the instructor.
     * @param studioName The name of the studio.
     */
    private void printClassDoesNotExist(String className, String instructorName, String studioName) {
        System.out.println(className + " by " + instructorName + " does not exist at " + studioName);
    }

    /**
     * Checks if a member is already registered in the specified class.
     *
     * @param fitnessClass The class to check for member registration.
     * @param member The member to check.
     * @return true if the member is already registered, false otherwise.
     */
    private boolean isMemberAlreadyRegistered(FitnessClass fitnessClass, Member member) {
        if (fitnessClass.isMemberRegistered(member)) {
            System.out.println(member.getProfile().getFname() + " " + member.getProfile().getLname() + " is already in the class.");
            return true;
        }
        return false;
    }

    /**
     * Checks for a time conflict between the member's existing class registrations and the new class.
     *
     * @param fitnessClass The new class the member wishes to attend.
     * @param member The member to check for time conflicts.
     * @return true if there is a time conflict, false otherwise.
     */
    private boolean hasTimeConflict(FitnessClass fitnessClass, Member member) {
        if (schedule.checkForMemberTimeConflict(member, fitnessClass.getTime())) {
            String time = fitnessClass.getTime().toString();
            System.out.println("Time conflict - " + member.getProfile().getFname() + " " + member.getProfile().getLname() + " is in another class held at " + formatTime(time) + " - " +
                    fitnessClass.getInstructor().name() + ", " + formatTime(time) + ", " + fitnessClass.getStudio().getCity().toUpperCase());
            return true;
        }
        return false;
    }

    /**
     * Records attendance for a member in a fitness class.
     *
     * @param fitnessClass The class where the member's attendance is to be recorded.
     * @param member The member attending the class.
     */
    private void recordAttendance(FitnessClass fitnessClass, Member member) {
        String zip = fitnessClass.getStudio().getZipCode();
        String county = fitnessClass.getStudio().getCounty();
        System.out.println(member.getProfile().getFname() + " " + member.getProfile().getLname() +
                " attendance recorded " + fitnessClass.getClassInfo().getClassName().toUpperCase() + " at " + fitnessClass.getStudio().getCity().toUpperCase() + ", " + zip + ", " + county.toUpperCase());
        if (member instanceof Basic) {
            ((Basic) member).attendClass();
        }
        fitnessClass.addMember(member);
        member.registerClass(fitnessClass);
    }

    /**
     * Validates the inputs for class name, instructor name, and studio name by checking their existence in the enums.
     *
     * @param className The name of the class.
     * @param instructorName The name of the instructor.
     * @param studioName The name of the studio.
     * @return true if all inputs are valid, false otherwise.
     */
    private boolean validateInputs(String className, String instructorName, String studioName) {
        try {
            Instructor.valueOf(instructorName.toUpperCase());
        } catch (IllegalArgumentException e) {
            System.out.println(instructorName + " - instructor does not exist.");
            return false;
        }

        try {
            Offer.valueOf(className.toUpperCase());
        } catch (IllegalArgumentException e) {
            System.out.println(className + " - class name does not exist.");
            return false;
        }

        try {
            Location.valueOf(studioName.toUpperCase());
        } catch (IllegalArgumentException e) {
            System.out.println(studioName + " - invalid studio location.");
            return false;
        }

        return true;
    }

    /**
     * Utility method to format time for printing. Removes leading zeros and formats time in a human-readable format.
     *
     * @param time The time string to format.
     * @return A formatted time string.
     */
    private String formatTime(String time) {
        if (time.startsWith("0")) {
            return time.substring(1);
        }
        return time;
    }

    /**
     * Removes a member from a class based on provided information. This method updates the class registration
     * to reflect the member's removal, ensuring accurate attendance tracking.
     *
     * @param tokens A StringTokenizer containing information about the class and member to remove.
     */
    private void removeMemberFromClass(StringTokenizer tokens) {
        if (tokens.countTokens() < 6) {
            System.out.println("Missing data tokens.");
            return;
        }
        String className = tokens.nextToken();
        String instructorName = tokens.nextToken();
        String studioName = tokens.nextToken();
        String firstName = tokens.nextToken();
        String lastName = tokens.nextToken();
        String dobString = tokens.nextToken();


        Profile profile = new Profile(firstName, lastName, new Date(dobString));
        Member member = memberList.retrieveMember(profile);
        FitnessClass fitnessClass = schedule.findClassByCriteria(Offer.valueOf(className.toUpperCase()), Instructor.valueOf(instructorName.toUpperCase()), Location.valueOf(studioName.toUpperCase()));
        String time = fitnessClass.getTime().toString();
        member.unregisterClass(fitnessClass);
        if (fitnessClass.removeMember(member)) {

            System.out.println(firstName + " " + lastName + " is removed from " + instructorName.toUpperCase() + ", " + formatTime(time) + ", " + fitnessClass.getStudio());
        } else {
            System.out.println(firstName + " " + lastName + " is not in " + instructorName.toUpperCase() + ", " + formatTime(time) + ", " + fitnessClass.getStudio());
        }
    }

    /**
     * Registers a guest for a class, subject to guest pass availability and membership type restrictions.
     *
     * @param tokens A StringTokenizer containing class and guest information for registration.
     */
    private void registerGuest(StringTokenizer tokens) {
        if (!hasSufficientTokens(tokens)) {
            return;
        }
        String className = tokens.nextToken();
        String instructorName = tokens.nextToken();
        String studioName = tokens.nextToken();
        String firstName = tokens.nextToken();
        String lastName = tokens.nextToken();
        String dobString = tokens.nextToken();
        if (!validateInputs(className, instructorName, studioName)) {
            return;
        }
        Profile profile = createProfile(firstName, lastName, dobString);
        Member member = retrieveMember(profile);
        if (member == null) {
            printMemberNotFound(firstName, lastName, dobString);
            return;
        }
        if (isMembershipExpired(member)) {
            return;
        }
        FitnessClass fitnessClass = findFitnessClass(className, instructorName, studioName);
        handleGuestRegistration(member, fitnessClass, studioName);
    }

    /**
     * Handles the registration of a guest in a fitness class, considering membership type and guest pass availability.
     * It enforces studio policies on guest attendance, including location restrictions and guest pass usage.
     *
     * @param member The member who is bringing the guest.
     * @param fitnessClass The class in which the guest is to be registered.
     * @param studioName The name of the studio where the class is held.
     */
    private void handleGuestRegistration(Member member, FitnessClass fitnessClass, String studioName) {
        String zip = fitnessClass.getStudio().getZipCode();
        String county = fitnessClass.getStudio().getCounty();
        Location homeStudio = member.getHomeStudio();

        if (member instanceof Basic) {
            System.out.println(member.getProfile().getFname() + " " + member.getProfile().getLname() + " [BASIC] - no guest pass.");
            return;
        }

        if (member instanceof Family) {
            handleFamilyGuest(member, fitnessClass, studioName, homeStudio, zip, county);
        } else if (member instanceof Premium) {
            handlePremiumGuest(member, fitnessClass, studioName, homeStudio, zip, county);
        }
    }

    /**
     * Processes the attendance of a family member's guest, including validation of guest passes and
     * registration in the class. This method updates both the member's and the class's records.
     *
     * @param member The family member bringing the guest.
     * @param fitnessClass The fitness class the guest will attend.
     * @param zip The zip code of the studio where the class is held.
     * @param county The county of the studio.
     */
    private void handleFamilyGuest(Member member, FitnessClass fitnessClass, String studioName, Location homeStudio, String zip, String county) {
        if (studioName.equalsIgnoreCase(homeStudio.name()) && ((Family) member).hasGuestPass()) {
            processGuestAttendance(fitnessClass, member, zip, county);
        } else {
            if (!((Family) member).hasGuestPass()) {
                System.out.println(member.getProfile().getFname() + " " + member.getProfile().getLname() + " guest pass not available.");
            } else {
                printGuestHomeStudioMismatch(member, studioName);
            }
        }
    }

    /**
     * Processes the attendance of a premium member's guest, applying similar validations as for family guests.
     * Premium membership often includes more flexible guest pass usage.
     *
     * @param member The premium member bringing the guest.
     * @param fitnessClass The fitness class the guest will attend.
     * @param zip The zip code of the studio.
     * @param county The county of the studio.
     */
    private void handlePremiumGuest(Member member, FitnessClass fitnessClass, String studioName, Location homeStudio, String zip, String county) {
        if (studioName.equalsIgnoreCase(homeStudio.name()) && ((Premium) member).hasGuestPass()) {
            processGuestAttendance(fitnessClass, member, zip, county);
        } else {
            if (!((Premium) member).hasGuestPass()) {
                System.out.println(member.getProfile().getFname() + " " + member.getProfile().getLname() + " guest pass not available.");
            } else {
                printGuestHomeStudioMismatch(member, studioName);
            }
        }
    }

    /**
     * Records the attendance of a guest in a class, ensuring the guest pass is properly utilized and marked.
     * This method updates the fitness class and member records to reflect the guest's attendance.
     *
     * @param fitnessClass The class where the guest's attendance is recorded.
     * @param member The member who is bringing the guest.
     * @param zip The zip code of the studio where the class is held.
     * @param county The county of the studio.
     */
    private void processGuestAttendance(FitnessClass fitnessClass, Member member, String zip, String county) {
        if (member instanceof Family) {
            ((Family) member).takeAttendanceOfGuest();
        } else if (member instanceof Premium) {
            ((Premium) member).takeAttendanceOfGuest();
        }
        System.out.println(member.getProfile().getFname() + " " + member.getProfile().getLname() +
                " (guest) attendance recorded " + fitnessClass.getClassInfo().getClassName().toUpperCase() + " at " + fitnessClass.getStudio().getCity().toUpperCase() + ", " + zip + ", " + county.toUpperCase());
        fitnessClass.addGuest(member);
        member.registerClass(fitnessClass);
    }

    /**
     * Prints a message indicating a mismatch between the guest's intended class location and the member's home studio.
     * This can occur when studio policies restrict guest attendance based on the member's home studio.
     *
     * @param member The member attempting to bring a guest.
     * @param studioName The name of the studio where the class is held.
     */
    private void printGuestHomeStudioMismatch(Member member, String studioName) {
        System.out.println(member.getProfile().getFname() + " " + member.getProfile().getLname() +
                " (guest) is attending a class at " + studioName.toUpperCase() +
                " - home studio at " + member.getHomeStudio().getCity().toUpperCase());
    }

    /**
     * Removes a guest from a class, updating the class and guest attendance records accordingly.
     *
     * @param tokens A StringTokenizer containing class and guest information for deregistration.
     */
    private void removeGuestFromClass(StringTokenizer tokens) {
        if (tokens.countTokens() < 6) {
            System.out.println("Missing data tokens.");
            return;
        }
        String className = tokens.nextToken();
        String instructorName = tokens.nextToken();
        String studioName = tokens.nextToken();
        String firstName = tokens.nextToken();
        String lastName = tokens.nextToken();
        String dobString = tokens.nextToken();

        try {
            Offer.valueOf(className.toUpperCase());
        } catch (IllegalArgumentException e) {
            System.out.println(className + " - class name does not exist.");
            return;
        }

        Profile profile = new Profile(firstName, lastName, new Date(dobString));
        Member member = memberList.retrieveMember(profile);
        FitnessClass fitnessClass = schedule.findClassByCriteria(Offer.valueOf(className.toUpperCase()), Instructor.valueOf(instructorName.toUpperCase()), Location.valueOf(studioName.toUpperCase()));
        String time = fitnessClass.getTime().toString();
        member.unregisterClass(fitnessClass);
        if (fitnessClass.removeGuest(member)) {
            if (member instanceof Family) {
                ((Family) member).removeAttendanceOfGuest();
                System.out.println(firstName + " " + lastName + " (guest) is removed from " + instructorName.toUpperCase() + ", " + formatTime(time) + ", " + fitnessClass.getStudio());
            } else if (member instanceof Premium) {
                ((Premium) member).removeGuest();
                System.out.println(firstName + " " + lastName + " (guest) is removed from " + instructorName.toUpperCase() + ", " + formatTime(time) + ", " + fitnessClass.getStudio());
            }
        } else {
            System.out.println(firstName + " " + lastName + " (guest) is not in " + instructorName.toUpperCase() + ", " + formatTime(time) + ", " + fitnessClass.getStudio());
        }

    }

}