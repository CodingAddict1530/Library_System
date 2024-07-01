package controllers;

import atlantafx.base.theme.Styles;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static util.Utility.addStyleClassU;
import static util.Utility.nextScreenU;
import static util.Utility.styleButtonsU;

/**
 * The controller class for the first screen.
 */
public class FirstPageController extends Controller implements Initializable {

    // These represent Nodes in the scene of the first screen being used.
    @FXML
    private Label firstPageLabel1;
    @FXML
    private Label firstPageLabel2;
    @FXML
    private Button firstPageLoginBtn;
    @FXML
    private Button firstPageSignUpBtn;

    /**
     * Used to initialize a controller once the root Node has been created.
     *
     * @param url Location used to make paths for the root Node.
     * @param resourceBundle Resources used to locate the root Node.
     */
    @FXML
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // Add style to the labels.
        addStyleClassU(firstPageLabel1, Styles.TITLE_1);
        addStyleClassU(firstPageLabel2, Styles.TITLE_4);

        // Add style to the buttons.
        styleButtonsU(false, firstPageLoginBtn, firstPageSignUpBtn);
    }

    /**
     * Loads the login screen.
     *
     * @param event Represents an action, in this case login button being clicked.
     * @throws IOException error during input/output operations.
     */
    @FXML
    public void login(ActionEvent event) throws IOException {

        nextScreenU("loginPage.fxml", event, firstPageLabel1.getScene(), getClass());
    }

    /**
     * Loads the signup screen.
     *
     * @param event Represents an action, in this case signup button being clicked.
     * @throws IOException error during input/output operations.
     */
    @FXML
    public void signUp(ActionEvent event) throws IOException {

        nextScreenU("signUpPage.fxml", event, firstPageLabel1.getScene(), getClass());
    }

}