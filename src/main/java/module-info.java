/**
 * Defines the org.example.cs_213_project_iii module.
 * This module encapsulates the functionality for the CS 213 Project III, including its main
 * application logic and supporting utilities. Also specify required dependencies
 * on other modules and make specific packages available for use by other modules.
 *
 * <p>Use this module to leverage the specific functionalities provided by the CS 213 Project III,
 * ensuring that the encapsulation and module path mechanisms are properly utilized for
 * enhanced modularity and maintainability of the application.</p>
 */
module org.example.cs_213_project_iii {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires org.testng;
    requires junit;

    opens studiomanagerFX to javafx.fxml;
    exports studiomanagerFX;
}