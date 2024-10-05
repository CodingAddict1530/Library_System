package controllers;

import atlantafx.base.controls.Card;
import atlantafx.base.theme.Styles;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.control.ButtonBar;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import model.User;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.ResourceBundle;

import static util.Utility.nextScreenU;
import static util.Utility.addStyleClassU;
import static util.Utility.dataChangedU;
import static util.Utility.styleSideBarU;
import static util.Utility.styleButtonsU;
import static util.Utility.addImageU;
import static util.Utility.createCardU;
import static util.DBUtil.executeUpdate;
import static util.DBUtil.getPostreSQLURL;
import static util.DBUtil.hashPassword;
import static util.Clock.CUSTOM_FORMATTER;

/**
 * The controller class for the settings screen.
 */
public class SettingsPageController extends Controller implements Initializable {

    // These represent Nodes in the scene of the search screen.
    @FXML
    private Circle settingsPageIcon;
    @FXML
    private VBox settingsPageSideBar;
    @FXML
    private Label settingsPageLabel1;
    @FXML
    private Label settingsPageLabel2;
    @FXML
    private Label settingsPageLabel3;
    @FXML
    private Label settingsPageLabel4;
    @FXML
    private Label settingsPageLabel5;
    @FXML
    private Label settingsPageBookTitle1;
    @FXML
    private Label settingsPageBookTitle2;
    @FXML
    private Button settingsPageReturnBtn1;
    @FXML
    private Button settingsPageReturnBtn2;
    @FXML
    private VBox settingsPageCard1;
    @FXML
    private HBox settingsPageCard2;
    @FXML
    private HBox settingsPageCard3;
    @FXML
    private HBox settingsPageCard4;
    @FXML
    private VBox settingsPageCard5;
    @FXML
    private VBox settingsPageCard6;
    @FXML
    private VBox settingsPageCard7;
    @FXML
    private VBox settingsPageCard8;
    @FXML
    private VBox settingsPageCard1Parent;
    @FXML
    private HBox settingsPageCard2Parent;
    @FXML
    private HBox settingsPageCard6Parent;
    @FXML
    private HBox settingsPageCard7Parent;
    @FXML
    private HBox settingsPageCard8Parent;
    @FXML
    private ImageView settingsPageImage1;
    @FXML
    private ImageView settingsPageImage2;
    @FXML
    private HBox settingsPageEdit1;
    @FXML
    private HBox settingsPageEdit2;
    @FXML
    private HBox settingsPageEditParent;
    @FXML
    private Label settingsPageName;
    @FXML
    private Label settingsPageEmail;
    @FXML
    private Label settingsPageMemberSince;
    @FXML
    private VBox settingsPageBox1;
    @FXML
    private VBox settingsPageBox2;

    /**
     * Used to initialize a controller once the root Node has been created.
     *
     * @param url Location used to make paths for the root Node.
     * @param resourceBundle Resources used to locate the root Node.
     */
    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // Add style to the sidebar.
        styleSideBarU(settingsPageSideBar);

        // Add style to the labels.
        addStyleClassU(Styles.TITLE_2, settingsPageLabel1, settingsPageLabel2);
        addStyleClassU(Styles.TITLE_3, settingsPageLabel3, settingsPageLabel4, settingsPageLabel5);
        addStyleClassU(Styles.TEXT_CAPTION, settingsPageBookTitle1, settingsPageBookTitle2);

        // Add style to the buttons.
        styleButtonsU(true, settingsPageReturnBtn1, settingsPageReturnBtn2);

        // Create an array to hold the position of child Nodes in parent Nodes respectively.
        int[] index = {1, 1, 3, 3, 5, 1, 1, 1, 2, 4};

        // Create the Node arrays of child and parent.
        Node[][] nodes = getNodes();

        // Loop through the Node array and make each Card.
        for (int i = 0; i < nodes.length; i++) {
            createCardU(nodes[i][0], nodes[i][1], OptionalInt.of(index[i]));
        }

        // Restrict the width of the Edit box.
        settingsPageEditParent.setMaxWidth(500.0);

        // Add functionality to the edit button. It will open a dialog.
        Card editBtn = (Card)settingsPageEditParent.getChildren().get(2);
        editBtn.setOnMouseClicked(event1 -> {

            // Create the Dialog.
            Dialog<String[]> dialog = createDialog();
            dialog.initOwner(settingsPageCard1.getScene().getWindow());

            // Retrieve data from the Dialog.
            Optional<String[]> result = dialog.showAndWait();
            
            // Check whether there is any data.
            if (result.isPresent()) {
                
                // Retrieve the data from the Dialog.
                String[] resultString = result.get();
                
                // Create a user object of the current user.
                User user = (User) data[0];
                
                // Check whether a first name was entered.
                if (!Objects.equals(resultString[0], "")) {
                    
                    // Set the user's first name to the new value.
                    user.setFirst_name(resultString[0]);
                }

                // Check whether a last name was entered.
                if (!Objects.equals(resultString[1], "")) {

                    // Set the user's last name to the new value.
                    user.setLast_name(resultString[1]);
                }

                // Check whether an email was entered.
                if (!Objects.equals(resultString[2], "")) {

                    // Set the user's email to the new value.
                    user.setEmail(resultString[2]);
                }

                // Check whether a password was entered.
                if (!Objects.equals(resultString[3], "")) {

                    // Set the query to update the user's password to the new value.
                    String sql = "UPDATE public.passwords SET " +
                            "password = ? " +
                            "WHERE user_id = ?";
                    try {
                        
                        // Execute the query.
                        executeUpdate(sql, getPostreSQLURL(), hashPassword(resultString[3]), user.getId());
                    } catch (SQLException ex) {
                        System.out.println(ex.getMessage());
                    }
                }
                
                // Save the changes made to the user to the database.
                user.saveChanges();
                
                // Update the user stored in the data array.
                data[0] = user;
                
                // Call dataChanged() to make use of the new data.
                dataChanged();

                // Save the new profile picture if any.
                saveProfile(resultString);
            }

        });

        Card editBtn2 = (Card) settingsPageEditParent.getChildren().get(4);
        HBox.setHgrow(editBtn2, Priority.ALWAYS);
        editBtn2.setOnMouseClicked(event2 -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Your profile picture will be deleted");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to delete this picture?");

            ButtonType yesBtn = new ButtonType("Yes", ButtonBar.ButtonData.YES);
            ButtonType cancelBtn = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

            alert.getButtonTypes().setAll(yesBtn, cancelBtn);
            alert.initOwner(settingsPageBox1.getScene().getWindow());

            Optional<ButtonType> resultAlert = alert.showAndWait();
            if (resultAlert.isPresent() && resultAlert.get() == yesBtn) {
                deleteProfile();
            }
        });

        // Add images to the ImageViews
        addImageU(settingsPageImage1, getClass(), "general", "book1.jpg");
        addImageU(settingsPageImage2, getClass(), "general", "book2.jpg");

        // Adjust minimum widths of the boxes
        settingsPageBox1.setMinWidth(150.0);
        settingsPageBox2.setMinWidth(150.0);
    }

    /**
     * Loads the first screen.
     *
     * @param event Represents an action, in this case signOut icon being clicked.
     * @throws IOException error during input/output operations.
     */
    @FXML
    public void signOut(MouseEvent event) throws IOException {

        nextScreenU("firstPage.fxml", event, settingsPageIcon.getScene(), getClass());
    }

    /**
     * Loads the home screen.
     *
     * @param event Represents an action, in this case home icon being clicked.
     * @throws IOException error during input/output operations.
     */
    @FXML
    public void home(MouseEvent event) throws IOException {

        nextScreenU("homePage.fxml", event, settingsPageIcon.getScene(),
                getClass(), this.data[0], this.data[1]);
    }

    /**
     * Loads the search screen.
     *
     * @param event Represents an action, in this case search icon being clicked.
     * @throws IOException error during input/output operations.
     */
    @FXML
    public void search(MouseEvent event) throws IOException {

        nextScreenU("searchPage.fxml", event, settingsPageIcon.getScene(),
                getClass(), this.data[0], this.data[1]);
    }

    /**
     * Creates a custom Dialog Object with 4 input fields and a file upload field.
     *
     * @return the Dialog.
     */
    private Dialog<String[]> createDialog() {

        // Create the dialog and give it a name.
        Dialog<String[]> dialog = new Dialog<>();
        dialog.setTitle("Edit Account Details");
        dialog.setHeaderText(null);

        // Set up the buttons of the Dialog.
        ButtonType okButtonType = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(okButtonType, ButtonType.CANCEL);

        // Create a StackPane to act as a container for a Grid. Allows padding.
        StackPane dialogPane = new StackPane();
        dialogPane.setStyle("-fx-padding: 10;");

        // Create a grid to hole the fields.
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        // Create the fields.
        TextField firstName = new TextField();
        firstName.setPromptText("First Name");
        TextField lastName = new TextField();
        lastName.setPromptText("Last Name");
        TextField email = new TextField();
        email.setPromptText("Email");
        PasswordField password = new PasswordField();
        password.setPromptText("Password");

        // Create an array to store the file from the lambda expression.
        File[] file = new File[1];
        
        // Create a button for uploading a picture.
        Button uploadBtn = new Button("Upload");
        
        // Set a lambda to execute when the button is pressed.
        uploadBtn.setOnAction(event -> {
            
            // Create a fileChooser.
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open Resource File");
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg")
            );
            
            // Retrieve the file uploaded.
            File f = fileChooser.showOpenDialog(settingsPageIcon.getScene().getWindow());
            
            // Add the new file to the array
            file[0] = f;

        });

        // Add the fields to the grid.
        grid.add(new Label("First Name: "), 0, 0);
        grid.add(firstName, 1, 0);
        grid.add(new Label("Last Name: "), 0, 1);
        grid.add(lastName, 1, 1);
        grid.add(new Label("Email: "), 0, 2);
        grid.add(email, 1, 2);
        grid.add(new Label("Password: "), 0, 3);
        grid.add(password, 1, 3);
        grid.add(new Label("Profile picture: "), 0, 4);
        grid.add(uploadBtn, 1, 4);

        // Add the Grid to the StackPane.
        dialogPane.getChildren().add(grid);

        // Add the StackPane to the Dialog.
        dialog.getDialogPane().setContent(dialogPane);

        // Set up a Result Converter to return the data input.
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == okButtonType) {
                String[] result = new String[5];
                result[0] = firstName.getText();
                result[1] = lastName.getText();
                result[2] = email.getText();
                result[3] = password.getText();
                result[4] = (file[0] != null) ? String.valueOf(file[0]) : null;
                return result;
            }
            return null;
        });

        return dialog;
    }

    /**
     * Creates a 2D array of the nodes and their parents.
     *
     * @return the Nodes array.
     */
    private Node[][] getNodes() {
        
        Node[] node1 = {settingsPageCard1, settingsPageCard1Parent};
        Node[] node2 = {settingsPageCard2, settingsPageCard2Parent};
        Node[] node3 = {settingsPageCard3, settingsPageCard2Parent};
        Node[] node4 = {settingsPageCard4, settingsPageCard1Parent};
        Node[] node5 = {settingsPageCard5, settingsPageCard1Parent};
        Node[] node6 = {settingsPageCard6, settingsPageCard6Parent};
        Node[] node7 = {settingsPageCard7, settingsPageCard7Parent};
        Node[] node8 = {settingsPageCard8, settingsPageCard8Parent};
        Node[] node9 = {settingsPageEdit1, settingsPageEditParent};
        Node[] node10 = {settingsPageEdit2, settingsPageEditParent};

        // Make an array of the Node arrays.
        return new Node[][]{node1, node2, node3, node4, node5, node6, node7, node8, node9, node10};
    }

    /**
     * Makes use of the data passed to the controller.
     */
    @Override
    protected void dataChanged() {
        
        dataChangedU(this.data, settingsPageLabel1, settingsPageIcon, getClass());
        
        // Check whether there is data.
        if (this.data != null && this.data.length > 0) {
            
            // Create a user object from the current user.
            User user = (User) this.data[0];
            
            // Update the fields.
            settingsPageName.setText("Name: " + user.getFirst_name() + " " + user.getLast_name());
            settingsPageEmail.setText("Email: " + user.getEmail());
            settingsPageMemberSince.setText("Member since: " + CUSTOM_FORMATTER.format(user.getDate_added()));
        }
    }

    /**
     * Save the uploaded photo as the profile picture.
     * 
     * @param resultString the array containing data entered into the dialog.
     */
    private void saveProfile(String[] resultString) {
        
        // Check whether a file was uploaded.
        if (resultString[4] != null) {
            
            // Create a file object from the uploaded file.
            File file = new File(resultString[4]);
            
            try {
                
                // Create a user object from the current user.
                User user = (User)this.data[0];
                
                // Split the name of the file to get the extension.
                String[] parts = file.getName().split("\\.");
                String extension = parts[parts.length - 1];
                
                // Create the new profile picture file on the system.
                File profilePicture = new File(String.format("src/main/resources/images/profilePictures/%d.%s",
                        user.getId(), extension));
                
                // Copy the contents.
                Files.copy(file.toPath(), profilePicture.toPath(), StandardCopyOption.REPLACE_EXISTING);
                
                // Use the new image in the icon.
                addImageU(settingsPageIcon, file.toURI().toString());
                
                // Call setData() to implicitly call dataChanged() and make use of the new data.
                this.setData(user, file.toURI().toString());
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    /**
     * Deletes the user's profile picture.
     */
    private void deleteProfile() {

        // Check whether there is a profile picture.
        if (this.data[1] == null) {
            return;
        }

        // Create a URI for the user's profile picture.
        URI path = URI.create((String) this.data[1]);
        
        // Set the user's profile picture to null.
        this.setData(this.data[0], null);
        
        // Create a Path Object from the file.
        Path filePath = Paths.get(path);
        
        try {
            
            // Delete the picture.
            Files.delete(filePath);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

}
