package studiomanagerFX;

import enums.Location;
import impl.FitnessClass;
import impl.MemberList;
import impl.Schedule;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;


import java.io.File;
import java.io.FileNotFoundException;


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
    private ToggleGroup memberType;
    @FXML
    private ToggleGroup studioLocation;
    @FXML
    private TextArea outputArea;

    @FXML
    protected void onclickAddmember(ActionEvent event) {
        outputArea.setText("You clicked the Add memeber");
    }

    @FXML
    protected void onclickCancelMembership(ActionEvent event) {
        outputArea.setText("You clicked the Cancel ");
    }


    /**
     * Initializes the studio manager by loading initial data for members and class schedules.
     */
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

    @FXML
    private TableView<Location> studio_location_table;
    @FXML
    private TableColumn<Location, String> col_city, col_county, col_zip;

    @FXML
    private TableView<FitnessClass> class_schedule_table;
    @FXML
    private TableColumn<FitnessClass, String> col_time, col_class_name, col_instructor, col_studio_location;
}