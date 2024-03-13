package studiomanagerFX;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Main application class for the Studio Manager Application.
 * This class extends {@link Application} and serves as the entry point for the JavaFX application.
 */
public class StudioManagerMain extends Application {
    /**
     * Starts the application and sets up the primary stage with a scene loaded from an FXML file.
     * This method is called after the application has been initialized.
     *
     * @param stage The primary stage for this application, onto which the application scene can be set.
     *              The primary stage is provided by the platform.
     * @throws IOException If there is an issue loading the FXML file.
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(StudioManagerMain.class.getResource("studioManagerView.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 700);
        stage.setTitle("Welcome to Studio Manager Application!");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * The main entry point for all JavaFX applications.
     * The start method is called after the init method has returned, and after the system is ready for the application to begin running.
     *
     * @param args the command line arguments passed to the application.
     *             An application may get these parameters using the getParameters() method.
     */
    public static void main(String[] args) {
        launch();
    }
}