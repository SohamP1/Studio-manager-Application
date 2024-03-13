package studiomanagerFX;

import data.*;
import enums.Instructor;
import enums.Location;
import enums.Offer;
import impl.FitnessClass;
import impl.MemberList;
import impl.Schedule;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;


import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;


public class StudioManagerController {

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
    public StudioManagerController() {
        memberList = new MemberList();
        schedule = new Schedule();
    }

    //Membership Tab
    @FXML
    private TextField firstname;
    @FXML
    private TextField lastname;
    @FXML
    private DatePicker dateOfBirth;
    @FXML
    private TextArea outputArea;

    //class Attendance Tab
    @FXML
    public TextField classFirstname;
    @FXML
    public TextField classLastname;
    @FXML
    public TextArea classGuestPasses;
    @FXML
    public DatePicker classAttendanceDob;

    @FXML
    private TableView<Location> studio_location_table;
    @FXML
    private TableColumn<Location, String> col_city, col_county, col_zip;
    @FXML
    private TableView<FitnessClass> class_schedule_table;
    @FXML
    private TableColumn<FitnessClass, String> col_time, col_class_name, col_instructor, col_studio_location;

    @FXML
    private RadioButton basicToggle, familyToggle, premiumToggle;
    @FXML
    private RadioButton bridgewaterToggle, edisonToggle, franklinToggle, piscatawayToggle, somervilleToggle;
    @FXML
    private RadioButton pilatesToggle,spinningToggle,cardioToggle;
    @FXML
    private RadioButton jeniferToggle,kimToggle,deniseToggle,davisToggle,emmaToggle;
    @FXML
    private RadioButton classBridgewaterToggle, classEdisonToggle, classFranklinToggle, classPiscatawayToggle, classSomervilleToggle;

    private final ToggleGroup memberTypeGroup = new ToggleGroup();
    private final ToggleGroup homeStudioGroup = new ToggleGroup();
    private final ToggleGroup classGroup = new ToggleGroup();
    private final ToggleGroup instructorGroup = new ToggleGroup();
    private final ToggleGroup classAttendanceGroupLocation = new ToggleGroup();

    @FXML
    private ComboBox<Integer> guestPass; // Ensure the ComboBox generic type matches with what you will insert, in this case, Integer.

    public void initialize() {
        // Assign radio buttons to their respective ToggleGroups in Membership Tab
        basicToggle.setToggleGroup(memberTypeGroup);
        familyToggle.setToggleGroup(memberTypeGroup);
        premiumToggle.setToggleGroup(memberTypeGroup);

        bridgewaterToggle.setToggleGroup(homeStudioGroup);
        edisonToggle.setToggleGroup(homeStudioGroup);
        franklinToggle.setToggleGroup(homeStudioGroup);
        piscatawayToggle.setToggleGroup(homeStudioGroup);
        somervilleToggle.setToggleGroup(homeStudioGroup);

        //classAttendance tab assigning the class radio buttons to the group
        pilatesToggle.setToggleGroup(classGroup);
        spinningToggle.setToggleGroup(classGroup);
        cardioToggle.setToggleGroup(classGroup);

        //classAttendance tab assigning the instructors radio buttons to the group
        jeniferToggle.setToggleGroup(instructorGroup);
        kimToggle.setToggleGroup(instructorGroup);
        deniseToggle.setToggleGroup(instructorGroup);
        davisToggle.setToggleGroup(instructorGroup);
        emmaToggle.setToggleGroup(instructorGroup);

        //classAttendance tab assigning the locations radio buttons to the group
        classBridgewaterToggle.setToggleGroup(classAttendanceGroupLocation);
        classEdisonToggle.setToggleGroup(classAttendanceGroupLocation);
        classFranklinToggle.setToggleGroup(classAttendanceGroupLocation);
        classPiscatawayToggle.setToggleGroup(classAttendanceGroupLocation);
        classSomervilleToggle.setToggleGroup(classAttendanceGroupLocation);


        // Populate the ComboBox with values from 0 to 3
        guestPass.getItems().clear(); // Clear existing items if any
        for (int i = 0; i <= 3; i++) {
            guestPass.getItems().add(i);
        }
        guestPass.getSelectionModel().selectFirst(); // Optionally select the first item by default
    }

    private boolean  validateMembershipInput() {
        String firstName = firstname.getText().trim();
        String lastName = lastname.getText().trim();
        LocalDate dobLocalDate = dateOfBirth.getValue();

        if (firstName.isEmpty()) {
            outputArea.setText("First name is required.");
            return false;
        }

        if (lastName.isEmpty()) {
            outputArea.setText("Last name is required.");
            return false;
        }

        if (dobLocalDate == null) {
            outputArea.setText("Date of birth is required.");
            return false;
        }

        if (memberTypeGroup.getSelectedToggle() == null) {
            outputArea.setText("Please select a member type.");
            return false;
        }

        if (homeStudioGroup.getSelectedToggle() == null) {
            outputArea.setText("Please select a home studio.");
        }
        return true;
    }
    private Date createDateFromLocalDate(LocalDate dobLocalDate) {
        Date dob;
        try {
            // Convert LocalDate to your custom Date format
            dob = new Date(dobLocalDate.getMonthValue(), dobLocalDate.getDayOfMonth(), dobLocalDate.getYear());

            // Assuming this constructor might throw IllegalArgumentException for other reasons
        } catch (IllegalArgumentException e) {
            outputArea.setText("Invalid date format: " + dobLocalDate);
            return null;
        }
        if (dob.isFutureDate()) {
            outputArea.setText("DOB " + dobLocalDate + ": cannot be today or a future date!");
            return null;
        }
        if (!dob.isValid()) {
            outputArea.setText("DOB " + dobLocalDate + ": invalid calendar date!");
            return null;
        }
        if (!dob.isEligible()) {
            outputArea.setText("DOB " + dobLocalDate + ": must be 18 or older to join!");
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
            outputArea.setText(city + ": invalid studio location!");
            return null;
        }
    }

    @FXML
    protected void onclickAddmember(ActionEvent event) {
        outputArea.clear();
        // First, validate the input
        if (!validateMembershipInput()) {
            return; // Stop execution as validation failed
        }

        RadioButton selectedMemberType = (RadioButton) memberTypeGroup.getSelectedToggle();
        String memberType = selectedMemberType != null ? selectedMemberType.getText() : "";


        RadioButton selectedStudioLocation = (RadioButton) homeStudioGroup.getSelectedToggle();
        String studioLocation = selectedStudioLocation != null ? selectedStudioLocation.getText() : "";

        Date dobCustom = createDateFromLocalDate(dateOfBirth.getValue());
        Location location = createLocation(studioLocation);

        // Now, based on the member type, create and add the member
        switch (memberType) {
            case "Basic" -> {
                if (dobCustom != null && location != null && guestPass.getValue() ==0) {
                    Profile profile = new Profile(firstname.getText(), lastname.getText(), dobCustom);
                    Date expire = Date.getCurrentDate().calculateOneMonthLater();
                    Basic newMember = new Basic(profile, expire, location);
                    addBasicMemberToDatabaseForBasic(newMember);
                    clearMembershipInputs();
                }
                else{outputArea.setText("Basic Members Do not Offer Guest Passes");}
            }
            case "Family" -> {
                Profile profile = new Profile(firstname.getText(), lastname.getText(), dobCustom);
                Date expire = Date.getCurrentDate().calculateThreeMonthsLater();
                Family newMember = new Family(profile, expire, location);
                if (dobCustom != null && location != null&& guestPass.getValue()==0) {
                    newMember.takeAttendanceOfGuest(); // setting false cause 0 guest
                    addFamilyMemberToDatabase(newMember);
                    clearMembershipInputs();
                }
                else if (dobCustom != null && location != null&& guestPass.getValue()==1) {
                    newMember.removeAttendanceOfGuest(); // setting true cause 1 guest
                    addFamilyMemberToDatabase(newMember);
                    clearMembershipInputs();
                }
                else{outputArea.setText("Family Members can have max 1 Guest Pass");}
            }
            case "Premium" -> {
                if (dobCustom != null && location != null) {
                    Profile profile = new Profile(firstname.getText(), lastname.getText(), dobCustom);
                    Date expire = Date.getCurrentDate().calculateTwelveMonthsLater();
                    Premium newMember = new Premium(profile, expire, location);
                    newMember.setGuestPass(guestPass.getValue());
                    addPremiumMemberToDatabase(newMember);
                    clearMembershipInputs();
                }
            }
        }
    }

    /**
     * Adds a Basic member to the studio's member database, performing checks for duplicate profiles. This method
     * prints feedback indicating whether the addition was successful or if the member already exists in the database.
     *
     * @param newMember The new Basic member to be added to the database.
     */
    private void addBasicMemberToDatabaseForBasic(Basic newMember) {
        if (!memberList.add(newMember)) {
            outputArea.setText(newMember.getProfile().getFname() + " " + newMember.getProfile().getLname() +
                    " is already in the member database.");
        } else {
            outputArea.setText(newMember.getProfile().getFname() + " " + newMember.getProfile().getLname() + " added.");
        }
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
            outputArea.setText(newMember.getProfile().getFname() + " " + newMember.getProfile().getLname() +
                    " is already in the member database.");
        } else {
            outputArea.setText(newMember.getProfile().getFname() + " " + newMember.getProfile().getLname() + " added.");
        }
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
            outputArea.setText(newMember.getProfile().getFname() + " " + newMember.getProfile().getLname() +
                    " is already in the member database.");
        } else {
            outputArea.setText(newMember.getProfile().getFname() + " " + newMember.getProfile().getLname() + " added.");
        }
    }

    @FXML
    protected void onclickCancelMembership(ActionEvent event) {
        outputArea.clear();
        if (!validateMembershipInput()) {
            return; // Stop execution as validation failed
        }
        Date dobCustom = createDateFromLocalDate(dateOfBirth.getValue());
        Profile profile = new Profile(firstname.getText(), lastname.getText(), dobCustom);

        Member member = new Member(profile, null, null);
        boolean removed = memberList.remove(member);

        if (removed) {
            outputArea.setText(firstname.getText() + " " + lastname.getText() + " removed.");
        } else {
            outputArea.setText(firstname.getText() + " " + lastname.getText() + " is not in the member database.");
        }
        clearMembershipInputs();
    }


    @FXML
    protected void onclickLoadMembers() {
        outputArea.clear();
        try {
            memberList.load(new File("src/main/java/test/memberList.txt"));
            schedule.load(new File("src/main/java/test/classSchedule.txt"));
            outputArea.setText("Studio Manager is up running...\n" + memberList.getMemberListString() + schedule.getScheduleString());
        } catch (FileNotFoundException e) {
            outputArea.setText("Error loading initial files: " + e.getMessage());
        }
    }


    /**
     * Class Attendance TAB
     */

    //getting radio buttons text value in class Attendance tab
    private String getSelectedClass() {
        RadioButton selected = (RadioButton) classGroup.getSelectedToggle();
        return selected != null ? selected.getText() : "";
    }

    private String getSelectedInstructor() {
        RadioButton selected = (RadioButton) instructorGroup.getSelectedToggle();
        return selected != null ? selected.getText() : "";
    }

    private String getSelectedLocation() {
        RadioButton selected = (RadioButton) classAttendanceGroupLocation.getSelectedToggle();
        return selected != null ? selected.getText() : "";
    }


    private boolean validateClassAttendanceInput() {
        // Validate text fields are not empty
        String firstName = classFirstname.getText().trim();
        String lastName = classLastname.getText().trim();
        LocalDate attendanceDate = classAttendanceDob.getValue();

        if (firstName.isEmpty()) {
            outputArea.setText("First name is required for class attendance.");
            return false;
        }

        if (lastName.isEmpty()) {
            outputArea.setText("Last name is required for class attendance.");
            return false;
        }

        if (attendanceDate == null) {
            outputArea.setText("Date of Birth is required.");
            return false;
        }

        // Validate that a radio button is selected in each ToggleGroup
        if (classGroup.getSelectedToggle() == null) {
            outputArea.setText("Please select a class.");
            return false;
        }

        if (instructorGroup.getSelectedToggle() == null) {
            outputArea.setText("Please select an instructor.");
            return false;
        }

        if (classAttendanceGroupLocation.getSelectedToggle() == null) {
            outputArea.setText("Please select a location for the class.");
            return false;
        }

        // If all validations pass
        return true;
    }
//////////////////////////////// Register Member //////////////////////////////////////////
    public void onclickRegisterMemberClass(ActionEvent actionEvent) {
        outputArea.clear();
        if (!validateClassAttendanceInput()) {
            return; // Stop processing as validation failed
        }
        Date dobCustom = createDateFromLocalDate(classAttendanceDob.getValue());
        Profile profile = new Profile(classFirstname.getText().trim(), classLastname.getText().trim(), dobCustom);
        Member member = retrieveMember(profile);
        if (member == null) {
            printMemberNotFound(classFirstname.getText().trim(), classLastname.getText().trim(), dobCustom);
            return;
        }
        if (isMembershipExpired(member)) {
            return;
        }
        if (!isValidHomeStudio(member, getSelectedLocation())) {
            return;
        }
        FitnessClass fitnessClass = findFitnessClass(getSelectedClass(), getSelectedInstructor(), getSelectedLocation());
        if (fitnessClass == null) {
            printClassDoesNotExist(getSelectedClass(), getSelectedInstructor(), getSelectedLocation());
            return;
        }
        if (isMemberAlreadyRegistered(fitnessClass, member)) {
            return;
        }
        if (hasTimeConflict(fitnessClass, member)) {
            return;
        }
        recordAttendance(fitnessClass, member);
        clearClassAttendanceInputs();
    }
    //clear the inputs after registration
    private void clearClassAttendanceInputs() {
        classFirstname.clear();
        classLastname.clear();
        classAttendanceDob.setValue(null);
        classGroup.getSelectedToggle().setSelected(false);
        instructorGroup.getSelectedToggle().setSelected(false);
        classAttendanceGroupLocation.getSelectedToggle().setSelected(false);
    }
    //clear the inputs after registration
    private void clearMembershipInputs() {
        firstname.clear();
        lastname.clear();
        dateOfBirth.setValue(null);
        memberTypeGroup.getSelectedToggle().setSelected(false);
        homeStudioGroup.getSelectedToggle().setSelected(false);
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
    private void printMemberNotFound(String firstName, String lastName, Date dobString) {
        outputArea.setText(firstName + " " + lastName + " " + dobString + " is not in the member database.");
    }

    /**
     * Checks if the member's membership has expired.
     *
     * @param member The member whose membership status is to be checked.
     * @return true if the membership has expired, false otherwise.
     */
    private boolean isMembershipExpired(Member member) {
        if (member.isMembershipExpired()) {
            outputArea.setText(member.getProfile().getFname() + " " + member.getProfile().getLname() +
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
            outputArea.setText(member.getProfile().getFname() + " " + member.getProfile().getLname() +
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
        outputArea.setText(className + " by " + instructorName + " does not exist at " + studioName);
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
            outputArea.setText(member.getProfile().getFname() + " " + member.getProfile().getLname() + " is already in the class.");
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
            outputArea.setText("Time conflict - " + member.getProfile().getFname() + " " + member.getProfile().getLname() + " is in another class held at " + formatTime(time) + " - " +
                    fitnessClass.getInstructor().name() + ", " + formatTime(time) + ", " + fitnessClass.getStudio().getCity().toUpperCase());
            return true;
        }
        return false;
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
     * Records attendance for a member in a fitness class.
     *
     * @param fitnessClass The class where the member's attendance is to be recorded.
     * @param member The member attending the class.
     */
    private void recordAttendance(FitnessClass fitnessClass, Member member) {
        String zip = fitnessClass.getStudio().getZipCode();
        String county = fitnessClass.getStudio().getCounty();
        outputArea.setText(member.getProfile().getFname() + " " + member.getProfile().getLname() +
                " attendance recorded " + fitnessClass.getClassInfo().getClassName().toUpperCase() + " at " + fitnessClass.getStudio().getCity().toUpperCase() + ", " + zip + ", " + county.toUpperCase());
        if (member instanceof Basic) {
            ((Basic) member).attendClass();
        }
        fitnessClass.addMember(member);
        member.registerClass(fitnessClass);
    }

    //////////////////////////////// Unregister Member //////////////////////////////////////////
    public void onclickUnregisterMemberClass(ActionEvent actionEvent) {
        outputArea.clear();
        if (!validateClassAttendanceInput()) {
            return; // Stop processing as validation failed
        }

        // Assuming classAttendanceDob is correctly defined and accessible here
        Date dobCustom = createDateFromLocalDate(classAttendanceDob.getValue());
        Profile profile = new Profile(classFirstname.getText().trim(), classLastname.getText().trim(), dobCustom);

        // Ensure member is not null before proceeding
        Member member = memberList.retrieveMember(profile);
        if (member == null) {
            outputArea.setText("Member not found.");
            return;
        }

        FitnessClass fitnessClass = schedule.findClassByCriteria(
                Offer.valueOf(getSelectedClass().toUpperCase()),
                Instructor.valueOf(getSelectedInstructor().toUpperCase()),
                Location.valueOf(getSelectedLocation().toUpperCase())
        );

        // Check if fitnessClass is not null before proceeding
        if (fitnessClass == null) {
            outputArea.setText("Class not found.");
            return;
        }

        String time = fitnessClass.getTime().toString();

        if (fitnessClass.removeMember(member)) {
            outputArea.setText(String.format("%s %s is removed from %s, %s, %s",
                    classFirstname.getText().trim(),
                    classLastname.getText().trim(),
                    getSelectedInstructor().toUpperCase(),
                    formatTime(time),
                    fitnessClass.getStudio()));
        } else {
            outputArea.setText(String.format("%s %s is not in %s, %s, %s",
                    classFirstname.getText().trim(),
                    classLastname.getText().trim(),
                    getSelectedInstructor().toUpperCase(),
                    formatTime(time),
                    fitnessClass.getStudio()));
        }
        clearClassAttendanceInputs();
    }

//////////////////////////  Register Guest and Remove Guest //////////////////////////////
    public void onclickRegisterGuestClass(ActionEvent actionEvent) {
        outputArea.clear();
        if (!validateClassAttendanceInput()) {
            return; // Stop processing as validation failed
        }
        Date dobCustom = createDateFromLocalDate(classAttendanceDob.getValue());
        Profile profile = new Profile(classFirstname.getText().trim(), classLastname.getText().trim(), dobCustom);
        Member member = retrieveMember(profile);
        if (member == null) {
            printMemberNotFound(classFirstname.getText().trim(), classLastname.getText().trim(), dobCustom);
            return;
        }
        if (isMembershipExpired(member)) {
            return;
        }
        if (!isValidHomeStudio(member, getSelectedLocation())) {
            return;
        }
        FitnessClass fitnessClass = findFitnessClass(getSelectedClass(), getSelectedInstructor(), getSelectedLocation());
        if (fitnessClass == null) {
            printClassDoesNotExist(getSelectedClass(), getSelectedInstructor(), getSelectedLocation());
            return;
        }
        if (isMemberAlreadyRegistered(fitnessClass, member)) {
            return;
        }
        if (hasTimeConflict(fitnessClass, member)) {
            return;
        }
        handleGuestRegistration(member, fitnessClass, getSelectedLocation());
        clearClassAttendanceInputs();
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
            outputArea.setText(member.getProfile().getFname() + " " + member.getProfile().getLname() + " [BASIC] - no guest pass.");
            return;
        }

        if (member instanceof Family) {
            handleFamilyGuest(member, fitnessClass, studioName, homeStudio, zip, county);

        } else if (member instanceof Premium) {
            handlePremiumGuest(member, fitnessClass, studioName, homeStudio, zip, county);
            String guestPasses = String.valueOf(((Premium) member).getGuestPass());
            classGuestPasses.setText(guestPasses);
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
                outputArea.setText(member.getProfile().getFname() + " " + member.getProfile().getLname() + " guest pass not available.");
                classGuestPasses.setText("0");
            } else {
                printGuestHomeStudioMismatch(member, studioName);
                classGuestPasses.setText("1");
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
                outputArea.setText(member.getProfile().getFname() + " " + member.getProfile().getLname() + " guest pass not available.");
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
        outputArea.setText(member.getProfile().getFname() + " " + member.getProfile().getLname() +
                " (guest) attendance recorded " + fitnessClass.getClassInfo().getClassName().toUpperCase() + " at " + fitnessClass.getStudio().getCity().toUpperCase() + ", " + zip + ", " + county.toUpperCase());
        fitnessClass.addGuest(member);
        member.registerClass(fitnessClass);
        classGuestPasses.setText("1");
    }

    /**
     * Prints a message indicating a mismatch between the guest's intended class location and the member's home studio.
     * This can occur when studio policies restrict guest attendance based on the member's home studio.
     *
     * @param member The member attempting to bring a guest.
     * @param studioName The name of the studio where the class is held.
     */
    private void printGuestHomeStudioMismatch(Member member, String studioName) {
        outputArea.setText(member.getProfile().getFname() + " " + member.getProfile().getLname() +
                " (guest) is attending a class at " + studioName.toUpperCase() +
                " - home studio at " + member.getHomeStudio().getCity().toUpperCase());
    }

    public void onclickUnregisterGuestClass(ActionEvent actionEvent) {
        outputArea.clear();
        if (!validateClassAttendanceInput()) {
            return; // Stop processing as validation failed
        }

        // Assuming classAttendanceDob is correctly defined and accessible here
        Date dobCustom = createDateFromLocalDate(classAttendanceDob.getValue());
        Profile profile = new Profile(classFirstname.getText().trim(), classLastname.getText().trim(), dobCustom);

        // Ensure member is not null before proceeding
        Member member = memberList.retrieveMember(profile);
        if (member == null) {
            outputArea.setText("Member not found.");
            return;
        }

        FitnessClass fitnessClass = schedule.findClassByCriteria(
                Offer.valueOf(getSelectedClass().toUpperCase()),
                Instructor.valueOf(getSelectedInstructor().toUpperCase()),
                Location.valueOf(getSelectedLocation().toUpperCase())
        );

        // Check if fitnessClass is not null before proceeding
        if (fitnessClass == null) {
            outputArea.setText("Class not found.");
            return;
        }
        String time = fitnessClass.getTime().toString();
        member.unregisterClass(fitnessClass);
        if (fitnessClass.removeGuest(member)) {
            if (member instanceof Family) {
                ((Family) member).removeAttendanceOfGuest();
                outputArea.setText(firstname.getText().trim() + " " + lastname.getText().trim() + " (guest) is removed from " + getSelectedInstructor().toUpperCase() + ", " + formatTime(time) + ", " + fitnessClass.getStudio());
            } else if (member instanceof Premium) {
                ((Premium) member).removeGuest();
                outputArea.setText(firstname.getText().trim() + " " + lastname.getText().trim() + " (guest) is removed from " + getSelectedInstructor().toUpperCase() + ", " + formatTime(time) + ", " + fitnessClass.getStudio());
            }
        } else {
            outputArea.setText(firstname.getText().trim() + " " + lastname.getText().trim() + " (guest) is not in " + getSelectedInstructor().toUpperCase() + ", " + formatTime(time) + ", " + fitnessClass.getStudio());
        }
        clearClassAttendanceInputs();
    }
}