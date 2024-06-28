package controllers;

import atlantafx.base.controls.Card;
import atlantafx.base.theme.Styles;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.shape.Circle;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.ResourceBundle;

import static util.Utility.nextScreenU;
import static util.Utility.addStyleClassU;
import static util.Utility.addImageU;
import static util.Utility.styleButtonsU;
import static util.Utility.styleSideBarU;
import static util.Utility.createCardU;

/**
 * The controller class for the settings screen
 */
public class SettingsPageController implements Initializable {

    // These represent Nodes in the scene of the search screen
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
    private HBox settingsPageEdit;
    @FXML
    private HBox settingsPageEditParent;
    @FXML
    private Label settingsPageName;
    @FXML
    private Label settingsPageEmail;
    @FXML
    private VBox settingsPageBox1;
    @FXML
    private VBox settingsPageBox2;

    /**
     * Used to initialize a controller once the root Node has been created
     *
     * @param url Location used to make paths for the root Node
     * @param resourceBundle Resources used to locate the root Node
     */
    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // Add an image to the icon
        addImageU(settingsPageIcon, getClass(), "general", "man1.jpg");

        // Add style to the sidebar
        styleSideBarU(settingsPageSideBar);

        // Add style to the labels
        addStyleClassU(Styles.TITLE_2, settingsPageLabel1, settingsPageLabel2);
        addStyleClassU(Styles.TITLE_3, settingsPageLabel3, settingsPageLabel4, settingsPageLabel5);
        addStyleClassU(Styles.TEXT_CAPTION, settingsPageBookTitle1, settingsPageBookTitle2);

        // Add style to the buttons
        styleButtonsU(true, settingsPageReturnBtn1, settingsPageReturnBtn2);

        // Create an array to hold the position of child Nodes in parent Nodes respectively
        int[] index = {1, 1, 3, 3, 5, 1, 1, 1, 1};

        // Create the Node arrays of child and parent
        Node[][] nodes = getNodes();

        // Loop through the Node array and make each Card
        for (int i = 0; i < nodes.length; i++) {
            createCardU(nodes[i][0], nodes[i][1], OptionalInt.of(index[i]));
        }

        // Restrict the width of the Edit box
        settingsPageEditParent.setMaxWidth(500.0);

        // Add functionality to the edit button. It will open a dialog
        Card editBtn = (Card)settingsPageEditParent.getChildren().get(1);
        editBtn.setOnMouseClicked(e -> {

            // Create the Dialog
            Dialog<String[]> dialog = createDialog();
            dialog.initOwner(settingsPageCard1.getScene().getWindow());

            // Retrieve data from the Dialog and display it
            Optional<String[]> result = dialog.showAndWait();
            if (result.isPresent()) {
                String[] resultString = result.get();
                settingsPageName.setText("Name: " + resultString[0] + " " + resultString[1]);
                settingsPageEmail.setText("Email: " + resultString[2]);
            }

        });

        // Add images to the ImageViews
        addImageU(settingsPageImage1, getClass(), "general", "man2.jpg");
        addImageU(settingsPageImage2, getClass(), "general", "man2.jpg");

        // Adjust minimum widths of the boxes
        settingsPageBox1.setMinWidth(150.0);
        settingsPageBox2.setMinWidth(150.0);
    }

    /**
     * Loads the first screen
     *
     * @param event Represents an action, in this case signOut icon being clicked
     * @throws IOException error during input/output operations
     */
    @FXML
    public void signOut(MouseEvent event) throws IOException {

        nextScreenU("firstPage.fxml", event, settingsPageIcon.getScene(), getClass());
    }

    /**
     * Loads the home screen
     *
     * @param event Represents an action, in this case home icon being clicked
     * @throws IOException error during input/output operations
     */
    @FXML
    public void home(MouseEvent event) throws IOException {

        nextScreenU("homePage.fxml", event, settingsPageIcon.getScene(), getClass());
    }

    /**
     * Loads the search screen
     *
     * @param event Represents an action, in this case search icon being clicked
     * @throws IOException error during input/output operations
     */
    @FXML
    public void search(MouseEvent event) throws IOException {

        nextScreenU("searchPage.fxml", event, settingsPageIcon.getScene(), getClass());
    }

    /**
     * Creates a custom Dialog Object with 4 input fields
     *
     * @return the Dialog
     */
    private Dialog<String[]> createDialog() {

        // Create the dialog and give it a name
        Dialog<String[]> dialog = new Dialog<>();
        dialog.setTitle("Edit Account Details");
        dialog.setHeaderText(null);

        // Set up the buttons of the Dialog
        ButtonType okButtonType = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(okButtonType, ButtonType.CANCEL);

        // Create a StackPane to act as a container for a Grid. Allows padding
        StackPane dialogPane = new StackPane();
        dialogPane.setStyle("-fx-padding: 10;");

        // Create a grid to hole the fields
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        // Create the fields
        TextField firstName = new TextField();
        firstName.setPromptText("First Name");
        TextField lastName = new TextField();
        lastName.setPromptText("Last Name");
        TextField email = new TextField();
        email.setPromptText("Email");
        TextField password = new TextField();
        password.setPromptText("Password");

        // Add the fields to the grid
        grid.add(new Label("First Name:"), 0, 0);
        grid.add(firstName, 1, 0);
        grid.add(new Label("Last Name:"), 0, 1);
        grid.add(lastName, 1, 1);
        grid.add(new Label("Email:"), 0, 2);
        grid.add(email, 1, 2);
        grid.add(new Label("Password:"), 0, 3);
        grid.add(password, 1, 3);

        // Add the Grid to the StackPane
        dialogPane.getChildren().add(grid);

        // Add the StackPane to the Dialog
        dialog.getDialogPane().setContent(dialogPane);

        // Set up a Result Converter to return the data input
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == okButtonType) {
                String[] result = new String[4];
                result[0] = firstName.getText();
                result[1] = lastName.getText();
                result[2] = email.getText();
                result[3] = password.getText();
                return result;
            }
            return null;
        });

        return dialog;
    }

    /**
     * Creates a 2D array of the nodes and their parents
     *
     * @return the Nodes array
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
        Node[] node9 = {settingsPageEdit, settingsPageEditParent};

        // Make an array of the Node arrays
        return new Node[][]{node1, node2, node3, node4, node5, node6, node7, node8, node9};
    }

}
