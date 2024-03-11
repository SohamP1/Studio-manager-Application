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

    opens cs_213_project_iii.fitness.studiomanager to javafx.fxml;
    exports cs_213_project_iii.fitness.studiomanager;
}