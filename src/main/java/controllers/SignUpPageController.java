package controllers;

import atlantafx.base.theme.Styles;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import model.Password;
import model.User;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.util.OptionalInt;
import java.util.ResourceBundle;

import static util.DBUtil.executeQuery;
import static util.DBUtil.getPostreSQLURL;
import static util.DBUtil.hashPassword;
import static util.Utility.createCardU;
import static util.Utility.showPopUpU;
import static util.Utility.addStyleClassU;
import static util.Utility.styleButtonsU;
import static util.Utility.nextScreenU;
import static util.Utility.pauseU;


/**
 * The controller class for the signUp screen
 */
public class SignUpPageController extends Controller implements Initializable {

    // These represent Nodes in the scene of the signup screen.
    @FXML
    private VBox signUpPageContainer;
    @FXML
    private VBox signUpPageParentContainer;
    @FXML
    private TextField signUpPageEmail;
    @FXML
    private PasswordField signUpPagePassword;
    @FXML
    private TextField signUpPageFirstName;
    @FXML
    private TextField signUpPageLastName;
    @FXML
    private Button signUpPageBtn;

    /**
     * Used to initialize a controller once the root Node has been created.
     *
     * @param url Location used to make paths for the root Node.
     * @param resourceBundle Resources used to locate the root Node.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // Embody the signup form into a styled card.
        createCardU(signUpPageContainer, signUpPageParentContainer, OptionalInt.of(1));

        // Add style to the input fields.
        addStyleClassU(Styles.ROUNDED, signUpPageEmail, signUpPagePassword, signUpPageFirstName, signUpPageLastName);

        // Add style to the button.
        styleButtonsU(true, signUpPageBtn);
    }

    /**
     * Removes focus from to the input fields when the container is clicked.
     */
    @FXML
    public void attentionContainer() {

        signUpPageContainer.requestFocus();
    }

    /**
     * Signs you up.
     */
    @FXML
    public void signUp(ActionEvent event){

        // Store the input values from the user.
        String email = signUpPageEmail.getText();
        String password = signUpPagePassword.getText();
        String firstName = signUpPageFirstName.getText();
        String lastName = signUpPageLastName.getText();

        // Check whether every input has data.
        if (email.isEmpty() || password.isEmpty() || firstName.isEmpty() || lastName.isEmpty()) {
            showPopUpU(signUpPageParentContainer, "Fill in all fields", Styles.DANGER);
            return;
        }

        // Define the SQL query.
        String sql = "SELECT * FROM public.users WHERE email = ?";

        try {

            // Store the result of the query.
            ResultSet result = executeQuery(sql, getPostreSQLURL(), email);

            // Check whether at least one row was returned.
            if (result.next()) {

                // Show a popup letting the user know the email already has an account.
                showPopUpU(signUpPageParentContainer, "User with this email already exists", Styles.DANGER);
            } else {

                //Create a new user from the data input.
                User user = new User(firstName, lastName, email);

                // Add the user to the database.
                user.addToDatabase();

                // Create a password object for the new user.
                Password userPassword = new Password(user.getId(), hashPassword(password));

                // Store the password in the database.
                userPassword.addToDatabase();

                // Show a success popup.
                showPopUpU(signUpPageParentContainer, "User added successfully, Logging in!", Styles.SUCCESS);

                // Pause the execution so the user can see the popup message.
                pauseU(() -> {
                    try {

                        // Load the next homePage screen
                        nextScreenU("homePage.fxml", event, signUpPageBtn.getScene(), getClass(), user);
                    } catch (IOException ex) {
                        System.out.println(ex.getMessage());
                    }
                }, Duration.seconds(2));
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

    }

    /**
     * Loads the login screen.
     *
     * @param event Represents an action, in this case login button being clicked.
     * @throws IOException error during input/output operations.
     */
    @FXML
    public void login(MouseEvent event) throws IOException {

        nextScreenU("loginPage.fxml", event, signUpPageBtn.getScene(), getClass());
    }

}
