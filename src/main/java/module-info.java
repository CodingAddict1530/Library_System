module com.library_system.librarysystem {
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
    requires atlantafx.base;
    requires java.desktop;

    opens com.library_system.librarysystem to javafx.fxml;
    exports com.library_system.librarysystem;
    exports controllers;
    opens controllers to javafx.fxml;
}