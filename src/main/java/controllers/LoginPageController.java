package controllers;

import atlantafx.base.controls.Card;
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
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * The controller class for the login screen
 */
public class LoginPageController implements Initializable {

    // These represent Nodes in the scene of the login screen
    @FXML
    private VBox loginPageContainer;
    @FXML
    private VBox loginPageParentContainer;
    @FXML
    private TextField loginPageEmail;
    @FXML
    private TextField loginPagePassword;
    @FXML
    private Button loginPageBtn;

    /**
     * Used to initialize a controller once the root Node has been created
     *
     * @param url Location used to make paths for the root Node
     * @param resourceBundle Resources used to locate the root Node
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // Embody the login form into a styled card
        Card card = new Card();
        card.getStyleClass().addAll(Styles.INTERACTIVE);
        card.setBody(loginPageContainer);
        loginPageParentContainer.getChildren().remove(loginPageContainer);
        loginPageParentContainer.getChildren().add(1, card);

        // Add style to the input fields
        loginPageEmail.getStyleClass().addAll(Styles.ROUNDED);
        loginPagePassword.getStyleClass().addAll(Styles.ROUNDED);

        // Add style to the button
        loginPageBtn.getStyleClass().addAll(Styles.ROUNDED, Styles.SMALL);
        loginPageBtn.setStyle(
                "-fx-background-color: #d2a7fa;" +
                        "-fx-text-fill: white;" +
                        "-fx-font-weight: bold"
        );
    }

    /**
     * Removes focus from to the input fields when the container is clicked
     *
     * @param event Represents an action, in this case the container being clicked
     * @throws IOException error during input/output operations
     */
    @FXML
    public void attentionContainer(MouseEvent event) throws IOException {
        loginPageContainer.requestFocus();
    }

    /**
     * Logs you in
     *
     * @param event Represents an action, in this case login button being clicked
     * @throws IOException error during input/output operations
     */
    @FXML
    public void login(ActionEvent event) throws IOException {

        // Parse and load the fxml file of the home screen
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/homePage.fxml"));

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
    public void signUp(MouseEvent event) throws IOException {

        // Parse and load the fxml file of the signUp screen
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/signUpPage.fxml"));

        // Load next screen
        nextScreen(fxmlLoader, event);
    }

    /**
     * Loads the forgot password page. Does nothing so far
     *
     * @param event Represents an action, in this case login button being clicked
     * @throws IOException error during input/output operations
     */
    @FXML
    public void forgotPassword(MouseEvent event) throws IOException {

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
        Scene scene = new Scene(root, loginPageEmail.getScene().getWidth(), loginPageEmail.getScene().getHeight());

        // Add css to the scene
        String css = Objects.requireNonNull(getClass().getResource("/css/index.css")).toExternalForm();
        scene.getStylesheets().add(css);

        // Set up and display the new screen
        stage.setScene(scene);
        stage.show();
    }
}
