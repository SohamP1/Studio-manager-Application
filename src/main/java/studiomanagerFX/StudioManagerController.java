package studiomanagerFX;

import data.Basic;
import data.Date;
import data.Profile;
import enums.Location;
import impl.FitnessClass;
import impl.MemberList;
import impl.Schedule;
import impl.StudioManager;
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

    @FXML
    protected void onclickAddmember(ActionEvent event) {
        // First, validate the input
        validateMembershipInput();

        RadioButton selectedMemberType = (RadioButton) memberTypeGroup.getSelectedToggle();
        String memberType = selectedMemberType != null ? selectedMemberType.getText() : "";


        RadioButton selectedStudioLocation = (RadioButton) homeStudioGroup.getSelectedToggle();
        String studioLocation = selectedStudioLocation != null ? selectedStudioLocation.getText() : "";

        LocalDate dob = dateOfBirth.getValue();
        Date dobCustom = new Date(dob.getMonthValue(), dob.getDayOfMonth(), dob.getYear());

        // Now, based on the member type, create and add the member
        if (memberType.equals("Basic")) {

        } else if (memberType.equals("Family")) {

        } else if (memberType.equals("Premium")) {

        }

        // Provide feedback in outputArea about the addition, e.g.,
        outputArea.setText(memberType + " member added successfully.");
    }

    @FXML
    protected void onclickCancelMembership(ActionEvent event) {
        validateMembershipInput();
        outputArea.setText("You clicked the Cancel ");
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