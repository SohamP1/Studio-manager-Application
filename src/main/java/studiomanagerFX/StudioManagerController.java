package studiomanagerFX;

import data.*;
import enums.Instructor;
import enums.Location;
import enums.Offer;
import impl.FitnessClass;
import impl.MemberList;
import impl.Schedule;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;


import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;


/**
 * The {@code StudioManagerController} class is responsible for handling all user interactions within the Studio Manager application.
 * It connects the application's GUI, defined in an associated FXML file, with the underlying data models and business logic.
 * This controller manages various functionalities including membership management, class attendance tracking, and schedule management.
 *
 * <p>Key features include:</p>
 * <ul>
 *     <li>Adding and cancelling memberships.</li>
 *     <li>Loading member and schedule data from files.</li>
 *     <li>Registering and unregistering members or guests for fitness classes.</li>
 *     <li>Displaying and sorting member information and class schedules.</li>
 * </ul>
 *
 * <p>This class utilizes JavaFX annotations to bind GUI components to controller methods, enabling interactive and responsive user experiences.
 * It demonstrates a comprehensive implementation of event handling, data validation, and dynamic content updating based on user actions.</p>
 * @author Sasanka Paththameistreege
 */
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

    /**
     * {Membership Tab}
     * TextField for the  member's First name, Last name.
     */
    @FXML
    private TextField firstname, lastname;

    /**
     * {Membership Tab}
     * DatePicker for selecting the {Membership Tab} Date of birth.
     */
    @FXML
    private DatePicker dateOfBirth;

    /**
     * {Membership Tab}
     * RadioButton for selecting the "Basic,Family,Premium" membership type.
     */
    @FXML
    private RadioButton basicToggle, familyToggle, premiumToggle;

    /**
     * {Membership Tab}
     * RadioButton for selecting the "Bridgewater, Edison, Franklin, Piscataway, Somerville" Studio Location.
     */
    @FXML
    private RadioButton bridgewaterToggle, edisonToggle, franklinToggle, piscatawayToggle, somervilleToggle;

    /**
     * {Membership Tab}
     * ToggleGroup for managing the selection of membership types.
     * This group contains RadioButtons for "Basic", "Family", and "Premium" membership options,
     * ensuring that only one membership type can be selected at a time within the Membership Tab.
     */
    private final ToggleGroup memberTypeGroup = new ToggleGroup();

    /**
     * {Membership Tab}
     * ToggleGroup for managing the selection of home studio locations.
     * This group encompasses RadioButtons for various locations such as "Bridgewater", "Edison",
     * "Franklin", "Piscataway", and "Somerville", allowing members to select their preferred home studio.
     * Only one location can be chosen at a time.
     */
    private final ToggleGroup homeStudioGroup = new ToggleGroup();


    /**
     * {Membership Tab}
     * The ComboBox generic type ensuring that matches with what you will insert, in this case, number of guest passes.
     */
    @FXML
    private ComboBox<Integer> guestPass;

    /**
     * {Class Attendance Tab}
     * TextField for entering the First name and Last name of a member or guest attending a class.
     */
    @FXML
    public TextField classFirstname, classLastname;

    /**
     * {Class Attendance Tab}
     * TextArea for displaying the number of guest passes a member has available or has used.
     */
    @FXML
    public TextArea classGuestPasses;

    /**
     * {Class Attendance Tab}
     * DatePicker for selecting the date of class attendance.
     */
    @FXML
    public DatePicker classAttendanceDob;

    /**
     * {Class Attendance Tab}
     * RadioButtons for selecting the type of fitness class (e.g., Pilates, Spinning, Cardio).
     */
    @FXML
    private RadioButton pilatesToggle, spinningToggle, cardioToggle;

    /**
     * {Class Attendance Tab}
     * RadioButtons for selecting the instructor for the class.
     */
    @FXML
    private RadioButton jeniferToggle, kimToggle, deniseToggle, davisToggle, emmaToggle;

    /**
     * {Class Attendance Tab}
     * RadioButtons for selecting the location of the class.
     */
    @FXML
    private RadioButton classBridgewaterToggle, classEdisonToggle, classFranklinToggle, classPiscatawayToggle, classSomervilleToggle;

    /**
     * {Class Attendance Tab}
     * A ToggleGroup for the class type selection in the Class Attendance Tab. This group contains
     * RadioButtons for different types of fitness classes (e.g., Pilates, Spinning, Cardio),
     * ensuring that only one class type can be selected at a time by the user.
     */
    private final ToggleGroup classGroup = new ToggleGroup();

    /**
     * {Class Attendance Tab}
     * A ToggleGroup for instructor selection in the Class Attendance Tab. It includes RadioButtons
     * for each instructor (e.g., Jenifer, Kim, Denise, Davis, Emma), allowing the user to select
     * a single instructor for the class. This ensures a clear and concise choice for class registration.
     */
    private final ToggleGroup instructorGroup = new ToggleGroup();

    /**
     * {Class Attendance Tab}
     * A ToggleGroup dedicated to the selection of the class location within the Class Attendance Tab.
     * This group comprises RadioButtons for various studio locations (e.g., Bridgewater, Edison, Franklin,
     * Piscataway, Somerville), facilitating the user's selection of one preferred location for attending the class.
     */
    private final ToggleGroup classAttendanceGroupLocation = new ToggleGroup();


    /**
     * {Class Schedule Tab}
     * TableView for displaying studio locations.
     */
    @FXML
    private TableView<Location> studio_location_table;

    /**
     * {Class Schedule Tab}
     * TableColumns for displaying the city, county, and ZIP code of studio locations.
     */
    @FXML
    private TableColumn<Location, String> col_city, col_county, col_zip;

    /**
     * {Class Schedule Tab}
     * TableView for displaying the schedule of fitness classes.
     */
    @FXML
    private TableView<FitnessClass> class_schedule_table;

    /**
     * {Class Schedule Tab}
     * TableColumns for displaying the time, class name, instructor, and location of fitness classes.
     */
    @FXML
    private TableColumn<FitnessClass, String> col_time, col_class_name, col_instructor, col_studio_location;

    /**
     * Common TextArea for displaying output messages, including validation, success, or error messages,
     * related to actions performed.
     */
    @FXML
    private TextArea outputArea;


    /**
     * Initializes the controller class. This method orchestrates the setup process by calling
     * individual setup methods for different sections of the UI. It ensures that UI components
     * are correctly initialized and ready for user interaction once the application starts.
     */
    public void initialize() {
        setupMembershipOptions();
        setupClassAttendanceOptions();
        disableDatePickersEditing();
        populateGuestPassComboBox();
        initializeStudioLocationTable();
    }

    /**
     * Sets up the membership options in the Membership Tab. This includes configuring the ToggleGroups
     * for membership types (Basic, Family, Premium) and home studios, as well as setting the default values
     * and event handlers for the ComboBox and radio buttons related to membership options.
     */
    private void setupMembershipOptions() {
        basicToggle.setToggleGroup(memberTypeGroup);
        familyToggle.setToggleGroup(memberTypeGroup);
        premiumToggle.setToggleGroup(memberTypeGroup);

        basicToggle.setOnAction(event -> guestPass.setValue(0));
        familyToggle.setOnAction(event -> guestPass.setValue(1));
        premiumToggle.setOnAction(event -> guestPass.setValue(3));

        bridgewaterToggle.setToggleGroup(homeStudioGroup);
        edisonToggle.setToggleGroup(homeStudioGroup);
        franklinToggle.setToggleGroup(homeStudioGroup);
        piscatawayToggle.setToggleGroup(homeStudioGroup);
        somervilleToggle.setToggleGroup(homeStudioGroup);

        guestPass.setValue(0); // Set default value of the ComboBox
    }

    /**
     * Configures the class attendance options in the Class Attendance Tab. It assigns radio buttons to
     * their respective ToggleGroups for class types, instructors, and class locations. This setup ensures
     * that users can select the appropriate options when registering or attending a class.
     */
    private void setupClassAttendanceOptions() {
        pilatesToggle.setToggleGroup(classGroup);
        spinningToggle.setToggleGroup(classGroup);
        cardioToggle.setToggleGroup(classGroup);

        jeniferToggle.setToggleGroup(instructorGroup);
        kimToggle.setToggleGroup(instructorGroup);
        deniseToggle.setToggleGroup(instructorGroup);
        davisToggle.setToggleGroup(instructorGroup);
        emmaToggle.setToggleGroup(instructorGroup);

        classBridgewaterToggle.setToggleGroup(classAttendanceGroupLocation);
        classEdisonToggle.setToggleGroup(classAttendanceGroupLocation);
        classFranklinToggle.setToggleGroup(classAttendanceGroupLocation);
        classPiscatawayToggle.setToggleGroup(classAttendanceGroupLocation);
        classSomervilleToggle.setToggleGroup(classAttendanceGroupLocation);
    }

    /**
     * Disables the text editing feature in the DatePicker controls to prevent users from typing dates
     * directly. This method ensures that dates are only selected through the DatePicker's calendar UI,
     * promoting consistency and reducing input errors.
     */
    private void disableDatePickersEditing() {
        classAttendanceDob.getEditor().setDisable(true);
        dateOfBirth.getEditor().setDisable(true);
    }

    /**
     * Populates the ComboBox used for selecting the number of guest passes a member has available. This
     * method fills the ComboBox with values and sets a default selection, ensuring the UI is correctly
     * initialized for user interaction regarding guest pass options.
     */
    private void populateGuestPassComboBox() {
        guestPass.getItems().clear(); // Clear existing items
        for (int i = 0; i <= 3; i++) {
            guestPass.getItems().add(i);
        }
        guestPass.getSelectionModel().selectFirst(); // Optionally select the first item by default
    }

    /**
     * Initializes the studio location table in the Studio Location Tab. This includes setting up the
     * column value factories and populating the table with data from the Location enum. This setup
     * ensures that the table displays the available studio locations to the user.
     */
    private void initializeStudioLocationTable() {
        col_city.setCellValueFactory(cellData -> Bindings.createStringBinding(() -> cellData.getValue().getCity().toUpperCase()));
        col_county.setCellValueFactory(cellData -> Bindings.createStringBinding(() -> cellData.getValue().getCounty().toUpperCase()));
        col_zip.setCellValueFactory(cellData -> Bindings.createStringBinding(() -> cellData.getValue().getZipCode().toUpperCase()));

        ObservableList<Location> locations = FXCollections.observableArrayList(Location.values());
        studio_location_table.setItems(locations);
    }

    /**
     * Validates the user input for the membership form in the Membership Tab. This method checks
     * if the first name, last name, date of birth, membership type, and home studio fields have
     * been properly filled out by the user. It also provides feedback directly in the UI through
     * the outputArea text area for any missing or incorrect information.
     *
     * @return boolean indicating whether the input is valid. Returns {@code true} if all required
     * fields are filled correctly; otherwise, returns {@code false} and displays an
     * appropriate message to the user.
     */
    private boolean validateMembershipInput() {
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
            outputArea.setText("Date of Birth is required.");
            return false;
        }

        if (memberTypeGroup.getSelectedToggle() == null) {
            outputArea.setText("Please select a member type.");
            return false;
        }
        return true;
    }

    /**
     * Converts a {@link LocalDate} object to a custom {@link Date} object. Validates the converted
     * date to ensure it is not a future date, is a valid calendar date, and the member is eligible
     * by age. Displays error messages in the output area for any validation failures.
     *
     * @param dobLocalDate The {@link LocalDate} object representing the date of birth to be converted.
     * @return A custom {@link Date} object representing the date of birth, or {@code null} if the
     * input date fails validation checks.
     */
    private Date createDateFromLocalDate(LocalDate dobLocalDate) {
        Date dob;
        try {
            // Convert LocalDate to your custom Date format
            dob = new Date(dobLocalDate.getMonthValue(), dobLocalDate.getDayOfMonth(), dobLocalDate.getYear());
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
            outputArea.setText("Please select a home studio.");
            return null;
        }
    }

    /**
     * Handles the "Add Member" button click event. This method clears the output area,
     * validates the membership input fields, and processes the addition of a new member
     * based on the validated input. If the input is not valid or the member cannot be added,
     * appropriate messages are displayed in the output area. The method delegates the
     * creation and addition of the member to the {@code processAdd} method based on the
     * member type selected.
     */
    @FXML
    protected void onclickAddmember() {
        outputArea.clear();
        if (!validateMembershipInput()) {
            return; // Stop execution as validation failed
        }

        RadioButton selectedMemberType = (RadioButton) memberTypeGroup.getSelectedToggle();
        String memberType = selectedMemberType != null ? selectedMemberType.getText() : "";


        RadioButton selectedStudioLocation = (RadioButton) homeStudioGroup.getSelectedToggle();
        String studioLocation = selectedStudioLocation != null ? selectedStudioLocation.getText() : "";

        Date dobCustom = createDateFromLocalDate(dateOfBirth.getValue());
        if (dobCustom == null) {
            // Since dobCustom validation and creation are happening inside createDateFromLocalDate,
            // no further action needed here
            return;
        }

        Location location = createLocation(studioLocation);
        if (location == null) {
            // If the location is not valid, exit the method
            return;
        }
        processAdd(memberType, dobCustom, location);
    }

    /**
     * Processes the addition of a new member to the database. This method is responsible for
     * creating a new member object based on the member type, date of birth, and location.
     * It handles the specific requirements and validations for each membership type (Basic,
     * Family, Premium) and updates the database and output area accordingly.
     *
     * @param memberType The type of membership for the new member (Basic, Family, Premium).
     * @param dobCustom  The date of birth of the new member, converted from LocalDate to the custom Date format.
     * @param location   The location selected for the new member's home studio.
     */
    private void processAdd(String memberType, Date dobCustom, Location location) {
        // Based on the member type, create and add the member
        switch (memberType) {
            case "Basic" -> {
                if (guestPass.getValue() == 0) {
                    Profile profile = new Profile(firstname.getText(), lastname.getText(), dobCustom);
                    Date expire = Date.getCurrentDate().calculateOneMonthLater();
                    Basic newMember = new Basic(profile, expire, location);
                    addBasicMemberToDatabaseForBasic(newMember);
                } else {
                    outputArea.setText("Basic Members Do not Offer Guest Passes");
                }
            }
            case "Family" -> {
                Profile profile = new Profile(firstname.getText(), lastname.getText(), dobCustom);
                Date expire = Date.getCurrentDate().calculateThreeMonthsLater();
                Family newMember = new Family(profile, expire, location);
                if (guestPass.getValue() == 0) {
                    newMember.takeAttendanceOfGuest(); // setting false cause 0 guest
                    addFamilyMemberToDatabase(newMember);
                } else if (guestPass.getValue() == 1) {
                    newMember.removeAttendanceOfGuest(); // setting true cause 1 guest
                    addFamilyMemberToDatabase(newMember);
                } else {
                    outputArea.setText("Family Members can have max 1 Guest Pass");
                }
            }
            case "Premium" -> {
                Profile profile = new Profile(firstname.getText(), lastname.getText(), dobCustom);
                Date expire = Date.getCurrentDate().calculateTwelveMonthsLater();
                Premium newMember = new Premium(profile, expire, location);
                newMember.setGuestPass(guestPass.getValue());
                addPremiumMemberToDatabase(newMember);
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

    /**
     * {Membership Tab}
     * Handles the action event triggered by clicking the "Cancel Membership" button. This method
     * clears any previous messages in the output area, validates the input fields for membership
     * cancellation, and attempts to remove a member from the studio's membership database based on
     * the provided profile information. A confirmation message is displayed in the output area if
     * the member is successfully removed, or an error message if the member cannot be found in the
     * database.
     */
    @FXML
    protected void onclickCancelMembership() {
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
    }

    /**
     * {Membership Tab}
     * Triggered by clicking the "Load Members" button, this method attempts to load the members
     * from a predefined text file into the studio's member list. It first clears any previous
     * messages in the output area, then reads the member data from a file located at a specific
     * path and updates the member list accordingly. A success message displaying the updated member
     * list is shown in the output area if the operation is successful. If the file cannot be found
     * or another error occurs during loading, an error message is displayed.
     */
    @FXML
    protected void onclickLoadMembers() {
        outputArea.clear();
        try {
            memberList.load(new File("src/main/java/test/memberList.txt"));
            outputArea.setText("Updating member list...\n" + memberList.getMemberListString());
        } catch (FileNotFoundException e) {
            outputArea.setText("Error loading initial files: " + e.getMessage());
        }
    }


    /**
     * {Class Attendance Tab}
     * Retrieves the selected class type from the class group radio buttons.
     *
     * @return The text of the selected class type radio button, or an empty string if none is selected.
     */
    private String getSelectedClass() {
        RadioButton selected = (RadioButton) classGroup.getSelectedToggle();
        return selected != null ? selected.getText() : "";
    }

    /**
     * {Class Attendance Tab}
     * Retrieves the selected instructor from the instructor group radio buttons.
     *
     * @return The text of the selected instructor radio button, or an empty string if none is selected.
     */
    private String getSelectedInstructor() {
        RadioButton selected = (RadioButton) instructorGroup.getSelectedToggle();
        return selected != null ? selected.getText() : "";
    }

    /**
     * {Class Attendance Tab}
     * Retrieves the selected location from the class attendance group location radio buttons.
     *
     * @return The text of the selected location radio button, or an empty string if none is selected.
     */
    private String getSelectedLocation() {
        RadioButton selected = (RadioButton) classAttendanceGroupLocation.getSelectedToggle();
        return selected != null ? selected.getText() : "";
    }

    /**
     * {Class Attendance Tab}
     * Validates the input fields related to class attendance, including the first name, last name,
     * and date of birth of the attendee, as well as ensuring that a class, instructor, and location
     * have been selected.
     *
     * @return true if all inputs are valid and selected, false otherwise.
     */
    private boolean validateClassAttendanceInput() {
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
        return true;
    }

    /**
     * {Class Attendance Tab}
     * Handles the action event triggered by clicking the "Register Member for Class" button. It validates
     * class attendance input, retrieves the member based on the provided profile, and registers the member
     * for the selected class if all validations pass and no conflicts exist.
     */
    @FXML
    protected void onclickRegisterMemberClass() {
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
     * @param lastName  The last name of the member.
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
     * @param member     The member to validate.
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
     * @param className      The name of the class.
     * @param instructorName The name of the instructor.
     * @param studioName     The name of the studio.
     * @return The FitnessClass instance if found, null otherwise.
     */
    private FitnessClass findFitnessClass(String className, String instructorName, String studioName) {
        return schedule.findClassByCriteria(Offer.valueOf(className.toUpperCase()), Instructor.valueOf(instructorName.toUpperCase()), Location.valueOf(studioName.toUpperCase()));
    }

    /**
     * Prints a message indicating that the specified fitness class does not exist.
     *
     * @param className      The name of the class.
     * @param instructorName The name of the instructor.
     * @param studioName     The name of the studio.
     */
    private void printClassDoesNotExist(String className, String instructorName, String studioName) {
        outputArea.setText(className + " by " + instructorName + " does not exist at " + studioName);
    }

    /**
     * Checks if a member is already registered in the specified class.
     *
     * @param fitnessClass The class to check for member registration.
     * @param member       The member to check.
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
     * @param member       The member to check for time conflicts.
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
     * @param member       The member attending the class.
     */
    private void recordAttendance(FitnessClass fitnessClass, Member member) {
        String zip = fitnessClass.getStudio().getZipCode();
        String county = fitnessClass.getStudio().getCounty();
        outputArea.setText(member.getProfile().getFname() + " " + member.getProfile().getLname() +
                " attendance recorded " + fitnessClass.getClassInfo().getClassName().toUpperCase() + " at " + fitnessClass.getStudio().getCity().toUpperCase() + ", " + zip + ", " + county.toUpperCase());
        if (member instanceof Basic) {
            ((Basic) member).attendClass();
            classGuestPasses.setText("0");
        }
        if (member instanceof Family) {
            if (!((Family) member).hasGuestPass()) {
                classGuestPasses.setText("0");
            } else {
                classGuestPasses.setText("1");
            }
        }
        if (member instanceof Premium) {
            classGuestPasses.setText(Integer.toString(((Premium) member).getGuestPass()));
        }

        fitnessClass.addMember(member);
        member.registerClass(fitnessClass);

    }


    /**
     * {Class Attendance Tab}
     * Handles the action event triggered by clicking the "Unregister Member from Class" button.
     * It validates the class attendance input, retrieves the member based on the provided profile,
     * and attempts to unregister the member from the selected fitness class. Feedback on the
     * operation's success or failure is displayed in the output area.
     */
    @FXML
    protected void onclickUnregisterMemberClass() {
        outputArea.clear();
        if (!validateClassAttendanceInput()) {
            return; // Stop processing as validation failed
        }
        Date dobCustom = createDateFromLocalDate(classAttendanceDob.getValue());
        Profile profile = new Profile(classFirstname.getText().trim(), classLastname.getText().trim(), dobCustom);

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

        if (fitnessClass == null) {
            outputArea.setText("Class not found.");
            return;
        }

        String time = fitnessClass.getTime().toString();

        if (fitnessClass.removeMember(member)) {
            member.unregisterClass(fitnessClass);
            outputArea.setText(String.format("%s %s is removed from %s, %s, %s",
                    classFirstname.getText().trim(), classLastname.getText().trim(), getSelectedInstructor().toUpperCase(),
                    formatTime(time), fitnessClass.getStudio()));
        } else {
            outputArea.setText(String.format("%s %s is not in %s, %s, %s",
                    classFirstname.getText().trim(), classLastname.getText().trim(), getSelectedInstructor().toUpperCase(),
                    formatTime(time), fitnessClass.getStudio()));
        }
    }

    /**
     * {Class Attendance Tab}
     * Triggered by clicking the "Register Guest for Class" button, this method validates class
     * attendance input, retrieves the member associated with the guest, and registers the guest
     * for the selected class based on membership eligibility and class availability. It handles
     * different scenarios such as membership expiration and location restrictions, providing
     * appropriate feedback in the output area.
     */
    @FXML
    protected void onclickRegisterGuestClass() {
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
        handleGuestRegistration(member, fitnessClass, getSelectedLocation());
    }

    /**
     * Handles the registration of a guest in a fitness class, considering membership type and guest pass availability.
     * It enforces studio policies on guest attendance, including location restrictions and guest pass usage.
     *
     * @param member       The member who is bringing the guest.
     * @param fitnessClass The class in which the guest is to be registered.
     * @param studioName   The name of the studio where the class is held.
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
        }
    }

    /**
     * Processes the attendance of a family member's guest, including validation of guest passes and
     * registration in the class. This method updates both the member's and the class's records.
     *
     * @param member       The family member bringing the guest.
     * @param fitnessClass The fitness class the guest will attend.
     * @param zip          The zip code of the studio where the class is held.
     * @param county       The county of the studio.
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
     * @param member       The premium member bringing the guest.
     * @param fitnessClass The fitness class the guest will attend.
     * @param zip          The zip code of the studio.
     * @param county       The county of the studio.
     */
    private void handlePremiumGuest(Member member, FitnessClass fitnessClass, String studioName, Location homeStudio, String zip, String county) {
        if (studioName.equalsIgnoreCase(homeStudio.name()) && ((Premium) member).hasGuestPass()) {
            processGuestAttendance(fitnessClass, member, zip, county);
        } else {
            if (!((Premium) member).hasGuestPass()) {
                outputArea.setText(member.getProfile().getFname() + " " + member.getProfile().getLname() + " guest pass not available.");
                classGuestPasses.setText(Integer.toString(((Premium) member).getGuestPass()));
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
     * @param member       The member who is bringing the guest.
     * @param zip          The zip code of the studio where the class is held.
     * @param county       The county of the studio.
     */
    private void processGuestAttendance(FitnessClass fitnessClass, Member member, String zip, String county) {
        if (member instanceof Family) {
            ((Family) member).takeAttendanceOfGuest();
            classGuestPasses.setText("0");
        } else if (member instanceof Premium) {
            ((Premium) member).takeAttendanceOfGuest();
            classGuestPasses.setText(Integer.toString(((Premium) member).getGuestPass()));
        }
        outputArea.setText(member.getProfile().getFname() + " " + member.getProfile().getLname() +
                " (guest) attendance recorded " + fitnessClass.getClassInfo().getClassName().toUpperCase() + " at " + fitnessClass.getStudio().getCity().toUpperCase() + ", " + zip + ", " + county.toUpperCase());
        fitnessClass.addGuest(member);
    }

    /**
     * Prints a message indicating a mismatch between the guest's intended class location and the member's home studio.
     * This can occur when studio policies restrict guest attendance based on the member's home studio.
     *
     * @param member     The member attempting to bring a guest.
     * @param studioName The name of the studio where the class is held.
     */
    private void printGuestHomeStudioMismatch(Member member, String studioName) {
        outputArea.setText(member.getProfile().getFname() + " " + member.getProfile().getLname() +
                " (guest) is attending a class at " + studioName.toUpperCase() +
                " - home studio at " + member.getHomeStudio().getCity().toUpperCase());
    }

    /**
     * {Class Attendance Tab}
     * Handles the action event triggered by clicking the "Unregister Guest from Class" button.
     * This method validates the class attendance input and proceeds to unregister a guest
     * associated with a member from a selected fitness class. It ensures that the guest is
     * correctly removed based on the member's profile and updates the guest count accordingly.
     * Feedback about the operation, whether successful or not, is displayed in the output area.
     * Specific actions are taken based on the member type (Family or Premium) to correctly
     * manage guest passes.
     */
    @FXML
    protected void onclickUnregisterGuestClass() {
        outputArea.clear();
        if (!validateClassAttendanceInput()) {
            return; // Stop processing as validation failed
        }
        Date dobCustom = createDateFromLocalDate(classAttendanceDob.getValue());
        Profile profile = new Profile(classFirstname.getText().trim(), classLastname.getText().trim(), dobCustom);
        Member member = memberList.retrieveMember(profile);
        if (member == null) {
            outputArea.setText("Member not found.");
            return;
        }

        FitnessClass fitnessClass = schedule.findClassByCriteria(Offer.valueOf(getSelectedClass().toUpperCase()),
                Instructor.valueOf(getSelectedInstructor().toUpperCase()), Location.valueOf(getSelectedLocation().toUpperCase())
        );

        if (fitnessClass == null) {
            outputArea.setText("Class not found.");
            return;
        }
        String time = fitnessClass.getTime().toString();
        if (fitnessClass.removeGuest(member)) {
            member.unregisterClass(fitnessClass);
            if (member instanceof Family) {
                ((Family) member).removeAttendanceOfGuest();
                outputArea.setText(firstname.getText().trim() + " " + lastname.getText().trim() + " (guest) is removed from " + getSelectedInstructor().toUpperCase() + ", " + formatTime(time) + ", " + fitnessClass.getStudio());
                classGuestPasses.setText("1");
            } else if (member instanceof Premium) {
                ((Premium) member).removeGuest();
                outputArea.setText(firstname.getText().trim() + " " + lastname.getText().trim() + " (guest) is removed from " + getSelectedInstructor().toUpperCase() + ", " + formatTime(time) + ", " + fitnessClass.getStudio());
                classGuestPasses.setText(Integer.toString(((Premium) member).getGuestPass()));
            }
        } else {
            outputArea.setText(firstname.getText().trim() + " " + lastname.getText().trim() + " (guest) is not in " + getSelectedInstructor().toUpperCase() + ", " + formatTime(time) + ", " + fitnessClass.getStudio());
        }
    }

    /**
     * Prompts the user to choose a file from the file system using a file chooser dialog.
     * This method is primarily used for selecting schedule or member list files to be loaded into the application.
     *
     * @return The selected {@link File} object, or {@link null} if no file was selected.
     */
    private File chooseFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Schedule File");
        Stage stage = (Stage) outputArea.getScene().getWindow(); // Assuming outputArea is a UI component
        return fileChooser.showOpenDialog(stage);
    }

    /**
     * Triggered by the "Load Schedule" button click. It uses {@code chooseFile} to let the user select a schedule file,
     * and then attempts to load the fitness classes from the selected file into the application.
     * Displays a success or error message in the output area based on the outcome.
     */
    @FXML
    protected void onclickLoadSchedule() {
        File file = chooseFile(); // Implement a method to choose a file
        if (file != null) {
            try {
                loadFitnessClassesFromFile(file);
            } catch (FileNotFoundException e) {
                outputArea.setText("Error loading schedule file: " + e.getMessage());
            }
        }
    }

    /**
     * Loads fitness classes from the specified file into the application. Updates the class schedule table
     * with the loaded data and handles file not found exceptions by displaying an error message.
     *
     * @param file The {@link File} from which to load the fitness classes.
     * @throws FileNotFoundException if the specified file does not exist.
     */
    private void loadFitnessClassesFromFile(File file) throws FileNotFoundException {
        // Create a temporary schedule object to check if the file contains any classes
        Schedule tempSchedule = new Schedule();
        tempSchedule.load(file);

        if (tempSchedule.getNumClasses() == 0) {
            outputArea.setText("The file does not contain any class schedule.");
            return; // Exit the method since there are no classes to load
        }

        // If the file contains classes, proceed with loading them into the application
        schedule.load(file);

        // Clear existing items in the table
        class_schedule_table.getItems().clear();

        // Get the existing items in the table
        ObservableList<FitnessClass> items = FXCollections.observableArrayList();
        class_schedule_table.setItems(items);
        // Add the new items from the loaded schedule
        items.addAll(schedule.getClasses());
        col_class_name.setCellValueFactory(new PropertyValueFactory<>("classInfo"));
        col_instructor.setCellValueFactory(new PropertyValueFactory<>("instructor"));
        col_time.setCellValueFactory(new PropertyValueFactory<>("time"));
        col_studio_location.setCellValueFactory(new PropertyValueFactory<>("studio"));

        col_class_name.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getClassInfo().toString()));
        col_instructor.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getInstructor().toString()));
        col_time.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTime().toString()));
        col_studio_location.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getStudio().toString()));
    }


    /**
     * Triggered by clicking a button to print the member list sorted by profile information.
     * Clears the output area and displays the sorted member list.
     */
    @FXML
    protected void onclickPrintByProfile() {
        outputArea.clear();
        outputArea.setText(memberList.printByMember());
    }

    /**
     * Triggered by clicking a button to print the member list sorted by county.
     * Clears the output area and displays the sorted member list emphasizing the members' county.
     */
    @FXML
    protected void onclickPrintByCounty() {
        outputArea.clear();
        outputArea.setText(memberList.printByCounty());
    }

    /**
     * Triggered by clicking a button to print the member list with next due payments.
     * Clears the output area and displays the member list with their respective next due payment details.
     */
    @FXML
    protected void onclickPrintWithNextDues() {
        outputArea.clear();
        outputArea.setText(memberList.printFees());
    }

    /**
     * Triggered by clicking a button to display the current class schedule.
     * Clears the output area and prints the entire class schedule in a readable format.
     */
    @FXML
    protected void onclickShowSchedule() {
        outputArea.clear();
        outputArea.setText(schedule.getScheduleString());

    }

    /**
     * Triggered by clicking a button to display a list of attendees for each class.
     * Clears the output area and prints the class names along with their attendees.
     */
    @FXML
    protected void onclickShowAttendees() {
        outputArea.clear();
        outputArea.setText(schedule.printClassWithAttendees());
    }

    /**
     * Triggered by clicking a button to display the studio locations.
     * Clears the output area and prints all studio locations available in the system.
     */
    @FXML
    protected void onclickShowStudioLocations() {
        outputArea.clear();
        StringBuilder stringBuilder = new StringBuilder();
        for (Location location : Location.values()) {
            stringBuilder.append(location.toString()).append("\n");
        }
        outputArea.setText(stringBuilder.toString());
    }

    /**
     * Clears the content of the text area used for displaying messages, feedback, and outputs to the user.
     * This method ensures the output area is ready for new messages after clearing previous content.
     */
    @FXML
    protected void onclickClearTextArea() {
        outputArea.clear();

    }

}