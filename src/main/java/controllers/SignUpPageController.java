package controllers;

import atlantafx.base.theme.Styles;
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
        createCardU(signUpPageContainer, signUpPageParentContainer, OptionalInt.of(1));

        // Add style to the input fields
        addStyleClassU(Styles.ROUNDED, signUpPageEmail, signUpPagePassword, signUpPageFirstName, signUpPageLastName);

        // Add style to the button
        styleButtonsU(true, signUpPageBtn);
    }

    /**
     * Removes focus from to the input fields when the container is clicked
     */
    @FXML
    public void attentionContainer() {
        signUpPageContainer.requestFocus();
    }

    /**
     * Signs you up, currently does nothing
     */
    @FXML
    public void signUp(){

    }

    /**
     * Loads the login screen
     *
     * @param event Represents an action, in this case login button being clicked
     * @throws IOException error during input/output operations
     */
    @FXML
    public void login(MouseEvent event) throws IOException {

        nextScreenU("loginPage.fxml", event, signUpPageBtn.getScene(), getClass());
    }

}
