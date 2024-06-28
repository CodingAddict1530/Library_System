package controllers;

import atlantafx.base.theme.Styles;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * The controller class for the first screen
 */
public class FirstPageController implements Initializable {

    // These represent Nodes in the scene of the first screen
    @FXML
    private Label firstPageLabel1;
    @FXML
    private Label firstPageLabel2;
    @FXML
    private Button firstPageLoginBtn;
    @FXML
    private Button firstPageSignUpBtn;

    /**
     * Used to initialize a controller once the root Node has been created
     *
     * @param url Location used to make paths for the root Node
     * @param resourceBundle Resources used to locate the root Node
     */
    @FXML
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // Add style to the labels
        firstPageLabel1.getStyleClass().addAll(Styles.TITLE_1);
        firstPageLabel2.getStyleClass().addAll(Styles.TITLE_4);

        // Add style to the buttons
        firstPageLoginBtn.getStyleClass().addAll(Styles.ROUNDED);
        firstPageLoginBtn.setStyle(
                "-fx-background-color: #d2a7fa;" +
                "-fx-text-fill: white;" +
                "-fx-font-weight: bold"
        );
        firstPageSignUpBtn.getStyleClass().addAll(Styles.ROUNDED);
        firstPageSignUpBtn.setStyle(
                "-fx-background-color: #d2a7fa;" +
                        "-fx-text-fill: white;" +
                        "-fx-font-weight: bold"
        );
    }

    /**
     * Loads the login screen
     *
     * @param event Represents an action, in this case login button being clicked
     * @throws IOException error during input/output operations
     */
    @FXML
    public void login(ActionEvent event) throws IOException {

        // Parse and load the fxml file of the login screen
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/loginPage.fxml"));

        // Load next screen
        nextScreen(fxmlLoader, event);
    }

    /**
     * Loads the signup screen
     *
     * @param event Represents an action, in this case signup button being clicked
     * @throws IOException error during input/output operations
     */
    @FXML
    public void signUp(ActionEvent event) throws IOException {

        // Parse and load the fxml file of the signup screen
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/signUpPage.fxml"));

        // Load next screen
        nextScreen(fxmlLoader, event);
    }

    /**
     * Takes the fxmlLoader and loads the next screen
     *
     * @param fxmlLoader loads the hierarchy
     * @param event represents an action
     * @throws IOException error during input/output operations
     */
    private void nextScreen(FXMLLoader fxmlLoader, Event event) throws IOException {

        // Create the root Node
        Parent root = fxmlLoader.load();

        // Pass the stage of the screen to the new stage variable
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        // Create a new scene with the dimensions of the previous one
        Scene scene = new Scene(root, firstPageLabel1.getScene().getWidth(), firstPageLabel1.getScene().getHeight());

        // Add css to the scene
        String css = Objects.requireNonNull(getClass().getResource("/css/index.css")).toExternalForm();
        scene.getStylesheets().add(css);

        // Set up and display the new screen
        stage.setScene(scene);
        stage.show();
    }
}