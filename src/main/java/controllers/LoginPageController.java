package controllers;

import atlantafx.base.theme.Styles;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.OptionalInt;
import java.util.ResourceBundle;

import static util.Utility.nextScreenU;
import static util.Utility.addStyleClassU;
import static util.Utility.createCardU;
import static util.Utility.styleButtonsU;

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
        createCardU(loginPageContainer, loginPageParentContainer, OptionalInt.of(1));

        // Add style to the input fields
        addStyleClassU(Styles.ROUNDED, loginPageEmail, loginPagePassword);

        // Add style to the button
        styleButtonsU(true, loginPageBtn);
    }

    /**
     * Removes focus from to the input fields when the container is clicked
     */
    @FXML
    public void attentionContainer() {
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

        nextScreenU("homePage.fxml", event, loginPageBtn.getScene(), getClass());
    }

    /**
     * Loads the signup screen
     *
     * @param event Represents an action, in this case signup button being clicked
     * @throws IOException error during input/output operations
     */
    @FXML
    public void signUp(MouseEvent event) throws IOException {

        nextScreenU("signUpPage.fxml", event, loginPageBtn.getScene(), getClass());
    }

    /**
     * Loads the forgot password page. Does nothing so far
     */
    @FXML
    public void forgotPassword() {

    }

}
