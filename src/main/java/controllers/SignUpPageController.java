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
 * The controller class for the signUp screen
 */
public class SignUpPageController implements Initializable {

    // These represent Nodes in the scene of the signup screen
    @FXML
    private VBox signUpPageContainer;
    @FXML
    private VBox signUpPageParentContainer;
    @FXML
    private TextField signUpPageEmail;
    @FXML
    private TextField signUpPagePassword;
    @FXML
    private TextField signUpPageFirstName;
    @FXML
    private TextField signUpPageLastName;
    @FXML
    private Button signUpPageBtn;

    /**
     * Used to initialize a controller once the root Node has been created
     *
     * @param url Location used to make paths for the root Node
     * @param resourceBundle Resources used to locate the root Node
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // Embody the signup form into a styled card
        Card card = new Card();
        card.getStyleClass().addAll(Styles.INTERACTIVE);
        card.setBody(signUpPageContainer);
        signUpPageParentContainer.getChildren().remove(signUpPageContainer);
        signUpPageParentContainer.getChildren().add(1, card);

        // Add style to the input fields
        signUpPageEmail.getStyleClass().addAll(Styles.ROUNDED);
        signUpPagePassword.getStyleClass().addAll(Styles.ROUNDED);
        signUpPageFirstName.getStyleClass().addAll(Styles.ROUNDED);
        signUpPageLastName.getStyleClass().addAll(Styles.ROUNDED);

        // Add style to the button
        signUpPageBtn.getStyleClass().addAll(Styles.ROUNDED, Styles.SMALL);
        signUpPageBtn.setStyle(
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
        signUpPageContainer.requestFocus();
    }

    /**
     * Signs you up, currently does nothing
     *
     * @param event Represents an action, in this case signup button being clicked
     * @throws IOException error during input/output operations
     */
    @FXML
    public void signUp(ActionEvent event) throws IOException {

    }

    /**
     * Loads the login screen
     *
     * @param event Represents an action, in this case login button being clicked
     * @throws IOException error during input/output operations
     */
    @FXML
    public void login(MouseEvent event) throws IOException {

        // Parse and load the fxml file of the login screen
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/loginPage.fxml"));

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
        Scene scene = new Scene(root, signUpPageBtn.getScene().getWidth(), signUpPageBtn.getScene().getHeight());

        // Add css to the scene
        String css = Objects.requireNonNull(getClass().getResource("/css/index.css")).toExternalForm();
        scene.getStylesheets().add(css);

        // Set up and display the new screen
        stage.setScene(scene);
        stage.show();
    }
}
