package cs_213_project_iii.fitness.studiomanager;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class StudioManagerController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}