package studiomanagerFX;

import data.Basic;
import data.Date;
import data.Profile;
import enums.Instructor;
import enums.Location;
import enums.Offer;
import enums.Time;
import impl.FitnessClass;
import impl.MemberList;
import impl.Schedule;
import impl.StudioManager;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;


import java.io.*;
import java.time.LocalDate;
import java.util.Scanner;


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
    private TableView<FitnessClass> class_schedule_table;
    @FXML
    private TableColumn<FitnessClass, String> col_time;

    @FXML
    private TableColumn<FitnessClass, String> col_class_name;

    @FXML
    private TableColumn<FitnessClass, String> col_instructor;

    @FXML
    private TableColumn<FitnessClass, String> col_studio_location;

    @FXML
    private TextArea outputArea1;

    @FXML
    private RadioButton basicToggle, familyToggle, premiumToggle;
    @FXML
    private RadioButton bridgewaterToggle, edisonToggle, franklinToggle, piscatawayToggle, somervilleToggle;

    private final ToggleGroup memberTypeGroup = new ToggleGroup();
    private final ToggleGroup homeStudioGroup = new ToggleGroup();

    @FXML
    private ComboBox<Integer> guestPass; // Ensure the ComboBox generic type matches with what you will insert, in this case, Integer.

    @FXML
    private TableView<Location> studio_location_table;

    @FXML
    private TableColumn<Location, String> col_city;

    @FXML
    private TableColumn<Location, String> col_county;

    @FXML
    private TableColumn<Location, String> col_zip;
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

        // Initialize columns in studio location tab
        col_city.setCellValueFactory(cellData -> Bindings.createStringBinding(() -> cellData.getValue().getCity().toUpperCase()));
        col_county.setCellValueFactory(cellData -> Bindings.createStringBinding(() -> cellData.getValue().getCounty().toUpperCase()));
        col_zip.setCellValueFactory(cellData -> Bindings.createStringBinding(() -> cellData.getValue().getZipCode().toUpperCase()));

        // Populate the table in studio location tab with Location enum values
        ObservableList<Location> locations = FXCollections.observableArrayList(Location.values());
        studio_location_table.setItems(locations);
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
         //   schedule.load(new File("src/main/java/test/classSchedule.txt"));
            outputArea.setText("Studio Manager is up running...\n" + memberList.getMemberListString() + schedule.getScheduleString());
        } catch (FileNotFoundException e) {
            outputArea.setText("Error loading initial files: " + e.getMessage());
        }
    }

    // Method to choose a file from the system
    private File chooseFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Schedule File");
        // Set initial directory, file extension filters, etc., if needed

        // Show the open file dialog
        Stage stage = (Stage) outputArea.getScene().getWindow(); // Assuming outputArea is a UI component
        return fileChooser.showOpenDialog(stage);
    }
    @FXML
    protected void onclickLoadSchedule(ActionEvent event) {
        File file = chooseFile(); // Implement a method to choose a file
        if (file != null) {
            try {
                loadFitnessClassesFromFile(file);
            } catch (FileNotFoundException e) {
                outputArea.setText("Error loading schedule file: " + e.getMessage());
            }
        }
    }

    private void loadFitnessClassesFromFile(File file) throws FileNotFoundException {

        schedule.load(file);

        // Clear existing items in the table
       // class_schedule_table.getItems().clear();

        // Iterate through the FitnessClass array and add each class to the table
        for (FitnessClass fitnessClass : schedule.getClasses()) {
            class_schedule_table.getItems().add(fitnessClass);
        }
        // Set cell value factories for each column
        col_class_name.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getClassInfo().toString()));
        col_instructor.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getInstructor().toString()));
        col_time.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getTime().toString()));
        col_studio_location.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getStudio().toString()));

    }

}