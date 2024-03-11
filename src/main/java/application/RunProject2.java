package application;

import impl.StudioManager;

/**
 * The runner class for Project2
 *
 * @author Soham Patel
 */
public class RunProject2 {
    /**
     * The main entry point for the application.
     * Creates an instance of StudioManager and initiates the application run sequence.
     *
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args) {
        new StudioManager().run();
    }
}