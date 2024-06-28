package controllers;

import atlantafx.base.controls.Card;
import atlantafx.base.theme.Styles;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

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

    /**
     * Used to initialize a controller once the root Node has been created
     *
     * @param url Location used to make paths for the root Node
     * @param resourceBundle Resources used to locate the root Node
     */
    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // Add an image to the icon
        Image image1 = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/general/man1.jpg")));
        settingsPageIcon.setFill(new ImagePattern(image1));

        // Add style to the sidebar
        settingsPageSideBar.setStyle(
                "-fx-background-color: #d2a7fa;" +
                "-fx-background-radius: 15, 15, 0, 0"
        );

        // Add style to the labels
        settingsPageLabel1.getStyleClass().addAll(Styles.TITLE_2);
        settingsPageLabel2.getStyleClass().addAll(Styles.TITLE_2);
        settingsPageLabel3.getStyleClass().addAll(Styles.TITLE_3);
        settingsPageLabel4.getStyleClass().addAll(Styles.TITLE_3);
        settingsPageLabel5.getStyleClass().addAll(Styles.TITLE_3);
        settingsPageBookTitle1.getStyleClass().addAll(Styles.TEXT_CAPTION);
        settingsPageBookTitle2.getStyleClass().addAll(Styles.TEXT_CAPTION);

        // Add style to the buttons
        styleButtons(settingsPageReturnBtn1, settingsPageReturnBtn2);

        // Create an array to hold the position of child Nodes in parent Nodes respectively
        int[] index = {1, 1, 3, 3, 5, 1, 1, 1, 1};

        // Create the Node arrays of child and parent
        Node[] node1 = {settingsPageCard1, settingsPageCard1Parent};
        Node[] node2 = {settingsPageCard2, settingsPageCard2Parent};
        Node[] node3 = {settingsPageCard3, settingsPageCard2Parent};
        Node[] node4 = {settingsPageCard4, settingsPageCard1Parent};
        Node[] node5 = {settingsPageCard5, settingsPageCard1Parent};
        Node[] node6 = {settingsPageCard6, settingsPageCard6Parent};
        Node[] node7 = {settingsPageCard7, settingsPageCard7Parent};
        Node[] node8 = {settingsPageCard8, settingsPageCard8Parent};
        Node[] node9 = {settingsPageEdit, settingsPageEditParent};

        // Make the Cards
        makeCards(index, node1, node2, node3, node4, node5, node6, node7, node8, node9);

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
        Image image2 = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/general/man2.jpg")));
        settingsPageImage1.setImage(image2);
        Image image3 = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/general/man2.jpg")));
        settingsPageImage2.setImage(image3);
    }

    /**
     * Loads the first screen
     *
     * @param event Represents an action, in this case signOut icon being clicked
     * @throws IOException error during input/output operations
     */
    @FXML
    public void signOut(MouseEvent event) throws IOException {

        // Parse and load the fxml file of the first screen
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/firstPage.fxml"));

        // Load next screen
        nextScreen(fxmlLoader, event);
    }

    /**
     * Loads the home screen
     *
     * @param event Represents an action, in this case home icon being clicked
     * @throws IOException error during input/output operations
     */
    @FXML
    public void home(MouseEvent event) throws IOException {

        // Parse and load the fxml file of the home screen
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/homePage.fxml"));

        // Load next screen
        nextScreen(fxmlLoader, event);
    }

    /**
     * Loads the search screen
     *
     * @param event Represents an action, in this case search icon being clicked
     * @throws IOException error during input/output operations
     */
    @FXML
    public void search(MouseEvent event) throws IOException {

        // Parse and load the fxml file of the search screen
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/searchPage.fxml"));

        // Load next screen
        nextScreen(fxmlLoader, event);
    }

    /**
     * Makes multiple cards and adds them to parent Nodes
     *
     * @param index an array with positions in the parent Node to add the Card respectively
     * @param nodes an unidentified number of Node arrays each having a child and parent Node
     */
    private void makeCards(int[] index, Node[]... nodes) {

        // Loop through each Node
        for (int i = 0; i < nodes.length; i++) {

            // Make a card from the child Node using makeCard()
            Card card = makeCard(nodes[i][0]);

            // Check whether the parent Node is a VBox or not
            if (nodes[i][1] instanceof VBox) {

                // Create a VBox and replace the child with the Card
                VBox parent = (VBox)nodes[i][1];
                parent.getChildren().remove(nodes[i][0]);
                parent.getChildren().add(index[i], card);
                VBox.setVgrow(card, Priority.ALWAYS);
            } else {

                // Create an HBox and replace the child with the Card
                HBox parent = (HBox)nodes[i][1];
                parent.getChildren().remove(nodes[i][0]);
                parent.getChildren().add(index[i], card);
                HBox.setHgrow(card, Priority.ALWAYS);
            }
        }
    }

    /**
     * Place a Node into a Card
     *
     * @param child the Node to place into a Card
     * @return the Card
     */
    private Card makeCard(Node child) {

        // Create and style a Card
        Card card = new Card();
        card.getStyleClass().addAll(Styles.INTERACTIVE);

        // Place the Node into the Card
        card.setBody(child);

        return card;
    }

    /**
     * Adds style to various buttons
     *
     * @param buttons an unidentified number of Button objects
     */
    private void styleButtons(Button... buttons) {

        // Loop through each Button
        for (Button button : buttons) {

            // Add style to the Button
            button.getStyleClass().addAll(Styles.ROUNDED, Styles.SMALL);
            button.setPrefWidth(100);
            button.setStyle(
                    "-fx-background-color: #d2a7fa;" +
                            "-fx-text-fill: white;" +
                            "-fx-font-weight: bold");
        }
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
        Scene scene = new Scene(root, settingsPageIcon.getScene().getWidth(), settingsPageIcon.getScene().getHeight());

        // Add css to the scene
        String css = Objects.requireNonNull(getClass().getResource("/css/index.css")).toExternalForm();
        scene.getStylesheets().add(css);

        // Set up and display the new screen
        stage.setScene(scene);
        stage.show();
    }
}
