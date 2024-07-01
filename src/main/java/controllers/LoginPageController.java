package controllers;

import atlantafx.base.theme.Styles;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import model.User;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.time.OffsetDateTime;
import java.util.Objects;
import java.util.OptionalInt;
import java.util.ResourceBundle;

import static util.DBUtil.executeQuery;
import static util.DBUtil.getPostreSQLURL;
import static util.DBUtil.checkPassword;
import static util.Utility.getFileU;
import static util.Utility.createCardU;
import static util.Utility.showPopUpU;
import static util.Utility.addStyleClassU;
import static util.Utility.styleButtonsU;
import static util.Utility.nextScreenU;
import static util.Utility.pauseU;

/**
 * The controller class for the login screen.
 */
public class LoginPageController extends Controller implements Initializable {

    // These represent Nodes in the scene of the login screen.
    @FXML
    private VBox loginPageContainer;
    @FXML
    private VBox loginPageParentContainer;
    @FXML
    private TextField loginPageEmail;
    @FXML
    private PasswordField loginPagePassword;
    @FXML
    private Button loginPageBtn;

    /**
     * Used to initialize a controller once the root Node has been created.
     *
     * @param url Location used to make paths for the root Node.
     * @param resourceBundle Resources used to locate the root Node.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // Embody the login form into a styled card.
        createCardU(loginPageContainer, loginPageParentContainer, OptionalInt.of(1));

        // Add style to the input fields.
        addStyleClassU(Styles.ROUNDED, loginPageEmail, loginPagePassword);

        // Add style to the button.
        styleButtonsU(true, loginPageBtn);

        // Check whether the scene is fully built.
        loginPageBtn.sceneProperty().addListener((observable, oldValue, newScene) -> {
            if (newScene != null) {

                // Make ENTER key fire the login button.
                loginPageBtn.getScene().addEventFilter(KeyEvent.KEY_PRESSED, event -> {
                    if (Objects.requireNonNull(event.getCode()) == KeyCode.ENTER) {
                        loginPageBtn.fire();
                    }
                });
            }
        });
    }

    /**
     * Removes focus from to the input fields when the container is clicked.
     */
    @FXML
    public void attentionContainer() {

        loginPageContainer.requestFocus();
    }

    /**
     * Logs you in.
     *
     * @param event Represents an action, in this case login button being clicked.
     */
    @FXML
    public void login(Event event) {

        // Retrieve the email and password input by the user.
        String email = loginPageEmail.getText();
        String password = loginPagePassword.getText();

        // Check whether every input has data.
        if (email.isEmpty() || password.isEmpty()) {
            showPopUpU(loginPageParentContainer, "Fill in all the fields", Styles.DANGER);
            return;
        }

        // Set up the SQL query.
        String sql = "SELECT * FROM public.users WHERE email = ?";

        try {

            // Store the result of the query.
            ResultSet resultUser = executeQuery(sql, getPostreSQLURL(), email);

            // Check whether at least one row was returned.
            if (resultUser.next()) {

                // Retrieve the user id.
                int user_id = resultUser.getInt("user_id");

                // Define a new SQL query.
                sql = "SELECT password FROM public.passwords WHERE user_id = ?";

                // Store the results of the query.
                ResultSet resultPassword = executeQuery(sql, getPostreSQLURL(), user_id);

                // Check whether at least one row was returned.
                if (resultPassword.next()) {

                    // Check whether the password input matches the stored password.
                    if (checkPassword(password, resultPassword.getString("password"))) {

                        // Show a success popup message.
                        showPopUpU(loginPageParentContainer, "Login successful", Styles.SUCCESS);

                        // Pause the execution to let the user see the popup.
                        pauseU(() -> {
                            try {

                                // Store the returned user into a User object.
                                User user = new User(
                                        resultUser.getInt("user_id"),
                                        resultUser.getString("first_name"),
                                        resultUser.getString("last_name"),
                                        resultUser.getString("email"),
                                        resultUser.getBoolean("booking_record"),
                                        resultUser.getObject("date_added", OffsetDateTime.class)
                                );

                                // Try to retrieve a profile picture for the user.
                                String profilePicture = getFileU(String.valueOf(user.getId()), "images/profilePictures/");
                                if (profilePicture != null) {

                                    // When found create a File object for it.
                                    File profilePictureFile = new File(profilePicture);

                                    // Load the homePage screen, passing the user and the profile picture.
                                    nextScreenU("homePage.fxml", event, loginPageBtn.getScene(), getClass(), user, profilePictureFile.toURI().toString());
                                } else {

                                    // When not found, pass the user and null as the homePage screen loads.
                                    nextScreenU("homePage.fxml", event, loginPageBtn.getScene(), getClass(), user, null);
                                }
                            } catch (Exception ex) {
                                System.out.println(ex.getMessage());
                            }
                        }, Duration.seconds(2));
                    } else {

                        // Show a popup telling the user the password was incorrect.
                        showPopUpU(loginPageParentContainer, "Incorrect password", Styles.DANGER);
                    }
                } else {
                    System.out.println("User has no password!");
                }
            } else {
                // Show a popup telling the user the email is incorrect.
                showPopUpU(loginPageParentContainer, "No user found for that email", Styles.DANGER);
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }


    }

    /**
     * Loads the signup screen.
     *
     * @param event Represents an action, in this case signup button being clicked.
     * @throws IOException error during input/output operations.
     */
    @FXML
    public void signUp(MouseEvent event) throws IOException {

        nextScreenU("signUpPage.fxml", event, loginPageBtn.getScene(), getClass());
    }

    /**
     * Loads the forgot password page. Does nothing so far.
     */
    @FXML
    public void forgotPassword() {

    }

}
