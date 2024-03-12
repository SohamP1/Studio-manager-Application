package studiomanagerFX;

import data.*;
import enums.Location;
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

    @FXML
    private TextField firstname;
    @FXML
    private TextField lastname;
    @FXML
    private DatePicker dateOfBirth;
    @FXML
    private TextArea outputArea;

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

    private final ToggleGroup memberTypeGroup = new ToggleGroup();
    private final ToggleGroup homeStudioGroup = new ToggleGroup();

    @FXML
    private ComboBox<Integer> guestPass; // Ensure the ComboBox generic type matches with what you will insert, in this case, Integer.

    public void initialize() {
        // Assign radio buttons to their respective ToggleGroups
        basicToggle.setToggleGroup(memberTypeGroup);
        familyToggle.setToggleGroup(memberTypeGroup);
        premiumToggle.setToggleGroup(memberTypeGroup);

        bridgewaterToggle.setToggleGroup(homeStudioGroup);
        edisonToggle.setToggleGroup(homeStudioGroup);
        franklinToggle.setToggleGroup(homeStudioGroup);
        piscatawayToggle.setToggleGroup(homeStudioGroup);
        somervilleToggle.setToggleGroup(homeStudioGroup);

        // Populate the ComboBox with values from 0 to 3
        guestPass.getItems().clear(); // Clear existing items if any
        for (int i = 0; i <= 3; i++) {
            guestPass.getItems().add(i);
        }
        guestPass.getSelectionModel().selectFirst(); // Optionally select the first item by default
    }

    private void validateMembershipInput() {
        String firstName = firstname.getText().trim();
        String lastName = lastname.getText().trim();
        LocalDate dobLocalDate = dateOfBirth.getValue();

        if (firstName.isEmpty()) {
            outputArea.setText("First name is required.");
            return;
        }

        if (lastName.isEmpty()) {
            outputArea.setText("Last name is required.");
            return;
        }

        if (dobLocalDate == null) {
            outputArea.setText("Date of birth is required.");
            return;
        }

        if (memberTypeGroup.getSelectedToggle() == null) {
            outputArea.setText("Please select a member type.");
            return;
        }

        if (homeStudioGroup.getSelectedToggle() == null) {
            outputArea.setText("Please select a home studio.");
        }
    }
    private Date createDateFromLocalDate(LocalDate dobLocalDate) {
        if (dobLocalDate == null) {
            outputArea.setText("Date of birth is not specified.");
            return null;
        }
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
        // First, validate the input
        validateMembershipInput();

        RadioButton selectedMemberType = (RadioButton) memberTypeGroup.getSelectedToggle();
        String memberType = selectedMemberType != null ? selectedMemberType.getText() : "";


        RadioButton selectedStudioLocation = (RadioButton) homeStudioGroup.getSelectedToggle();
        String studioLocation = selectedStudioLocation != null ? selectedStudioLocation.getText() : "";

        Date dobCustom = createDateFromLocalDate(dateOfBirth.getValue());
        Location location = createLocation(studioLocation);

        // Now, based on the member type, create and add the member
        switch (memberType) {
            case "Basic" -> {
                if (dobCustom != null && location != null) {
                    Profile profile = new Profile(firstname.getText(), lastname.getText(), dobCustom);
                    Date expire = Date.getCurrentDate().calculateOneMonthLater();
                    Basic newMember = new Basic(profile, expire, location);
                    addBasicMemberToDatabaseForBasic(newMember);
                }
            }
            case "Family" -> {
                if (dobCustom != null && location != null) {
                    Profile profile = new Profile(firstname.getText(), lastname.getText(), dobCustom);
                    Date expire = Date.getCurrentDate().calculateThreeMonthsLater();
                    Family newMember = new Family(profile, expire, location);
                    addFamilyMemberToDatabase(newMember);
                }
            }
            case "Premium" -> {
                if (dobCustom != null && location != null) {
                    Profile profile = new Profile(firstname.getText(), lastname.getText(), dobCustom);
                    Date expire = Date.getCurrentDate().calculateTwelveMonthsLater();
                    Premium newMember = new Premium(profile, expire, location);
                    newMember.setGuestPass(guestPass.getValue());
                    addPremiumMemberToDatabase(newMember);
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
        validateMembershipInput();
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


    @FXML
    protected void onclickLoadMembers() {
        try {
            memberList.load(new File("src/main/java/test/memberList.txt"));
            schedule.load(new File("src/main/java/test/classSchedule.txt"));
            outputArea.setText("Studio Manager is up running...\n" + memberList.getMemberListString() + schedule.getScheduleString());
        } catch (FileNotFoundException e) {
            outputArea.setText("Error loading initial files: " + e.getMessage());
        }
    }
}