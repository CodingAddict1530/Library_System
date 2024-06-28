package com.library_system.librarysystem;

import atlantafx.base.theme.PrimerLight;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

/**
 * Executes the whole application
 */
public class Application extends javafx.application.Application {

    /**
     * Starts the application under the GUI
     *
     * @param stage the core window of the application
     * @throws IOException error during input/output operations
     */
    @Override
    public void start(Stage stage) throws IOException {

        // Add AtlantaFX theme (PrimerLight in this case)
        Application.setUserAgentStylesheet(new PrimerLight().getUserAgentStylesheet());

        // Parse and load the fxml file of the first screen
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/firstPage.fxml"));

        // Obtain the root Node
        Parent root = fxmlLoader.load();

        // Add root Node to the scene to hold all the other nodes
        Scene scene = new Scene(root, 1000, 600);

        // Add css to the scene
        String css = Objects.requireNonNull(getClass().getResource("/css/index.css")).toExternalForm();
        scene.getStylesheets().add(css);

        // Set up the stage with a title and logo, then add the scene to it
        stage.setTitle("Library System");
        Image logo = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/general/logo.jpg")));
        stage.getIcons().add(logo);
        stage.setScene(scene);
        stage.setFullScreen(false);
        stage.show();
    }

    /**
     * Start point of the program
     * Calls the launch() method from java.application.Application
     * Will implicitly call the start() method
     *
     * @param args input stream
     */
    public static void main(String[] args) {
        launch(args);
    }
}