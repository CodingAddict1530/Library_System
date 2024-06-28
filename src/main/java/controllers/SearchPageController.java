package controllers;

import atlantafx.base.controls.Card;
import atlantafx.base.theme.Styles;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * The controller class for the search screen
 */
public class SearchPageController implements Initializable {

    // These represent Nodes in the scene of the search screen
    @FXML
    private Circle searchPageIcon;
    @FXML
    private VBox searchPageSideBar;
    @FXML
    private Label searchPageLabel1;
    @FXML
    private Label searchPageLabel2;
    @FXML
    private TextField searchPageSearchBox;
    @FXML
    private Button searchPageSearchBtn;
    @FXML
    private Button searchPageCategoryBtn1;
    @FXML
    private Button searchPageCategoryBtn2;
    @FXML
    private Button searchPageCategoryBtn3;
    @FXML
    private Button searchPageCategoryBtn4;
    @FXML
    private Button searchPageCategoryBtn5;
    @FXML
    private HBox searchPageBookParent;

    /**
     * Used to initialize a controller once the root Node has been created
     *
     * @param url Location used to make paths for the root Node
     * @param resourceBundle Resources used to locate the root Node
     */
    @FXML
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // Add an image to the icon
        Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/general/man1.jpg")));
        searchPageIcon.setFill(new ImagePattern(image));

        // Add style to the sidebar
        searchPageSideBar.setStyle(
                "-fx-background-color: #d2a7fa;" +
                "-fx-background-radius: 15, 15, 0, 0"
        );

        // Add style to the labels
        searchPageLabel1.getStyleClass().addAll(Styles.TITLE_2);
        searchPageLabel2.getStyleClass().addAll(Styles.TITLE_2);

        // Add style to the search box
        searchPageSearchBox.getStyleClass().addAll(Styles.ROUNDED);

        // Add style to the buttons
        styleButtons(searchPageCategoryBtn1, searchPageCategoryBtn2, searchPageCategoryBtn3,
                searchPageCategoryBtn4, searchPageCategoryBtn5, searchPageSearchBtn);

        // Set max width of the HBox in a ScrollPane to the maximum
        searchPageBookParent.setMaxWidth(Double.MAX_VALUE);

        // Add books to the HBox
        addBooks(makeCard(), makeCard(), makeCard(), makeCard(), makeCard(), makeCard(), makeCard(), makeCard(),
                makeCard(), makeCard(), makeCard(), makeCard(), makeCard(), makeCard(), makeCard(), makeCard(),
                makeCard(), makeCard(), makeCard(), makeCard(), makeCard(), makeCard(), makeCard(), makeCard(),
                makeCard(), makeCard(), makeCard(), makeCard(), makeCard(), makeCard(), makeCard(), makeCard(),
                makeCard(), makeCard(), makeCard(), makeCard(), makeCard(), makeCard(), makeCard(), makeCard(),
                makeCard(), makeCard(), makeCard(), makeCard(), makeCard(), makeCard(), makeCard(), makeCard());
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
     * Loads the settings screen
     *
     * @param event Represents an action, in this case settings icon being clicked
     * @throws IOException error during input/output operations
     */
    @FXML
    public void settings(MouseEvent event) throws IOException {

        // Parse and load the fxml file of the settings screen
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/settingsPage.fxml"));

        // Load next screen
        nextScreen(fxmlLoader, event);
    }

    /**
     * Adds style to multiple buttons
     *
     * @param buttons and unidentified number of Button objects
     */
    private void styleButtons(Button... buttons) {

        // Loop through all buttons
        for (Button button : buttons) {

            // Add style to the buttons
            button.getStyleClass().addAll(Styles.ROUNDED, Styles.SMALL);
            button.setPrefWidth(100);
            button.setStyle(
                    "-fx-background-color: #d2a7fa;" +
                    "-fx-text-fill: white;" +
                    "-fx-font-weight: bold");
        }
    }

    /**
     * Makes cards containing a book's information
     *
     * @return the card
     */
    private Card makeCard() {

        // Create and style the card
        Card card = new Card();
        card.getStyleClass().addAll(Styles.INTERACTIVE);

        // Add a VBox containing the book information using makeBookVbox()
        card.setBody(makeBookVbox(true));

        return card;
    }

    /**
     * Makes a VBox containing information of a book
     *
     * @param par1 Used to determine whether the VBox will contain a book, or it's for padding
     * @return the VBox representing information of a book
     */
    private VBox makeBookVbox(boolean par1) {

        // Create a VBox
        VBox vBox = new VBox();

        // Add style to the VBox
        vBox.setAlignment((par1) ? Pos.CENTER : Pos.TOP_LEFT);
        vBox.setPadding(new Insets(0.0, 0.0,0.0, 0.0));
        vBox.setSpacing(0.0);
        vBox.setPrefWidth((par1) ? 100.0 : 21.0);
        vBox.setPrefHeight((par1) ? 200.0 : 127.0);

        // Check whether the VBox will contain a book, true means it will.
        if (par1) {

            // Create an ImageView and style it
            Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/general/man2.jpg")));
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(101.0);
            imageView.setFitHeight(90.0);
            imageView.setPreserveRatio(true);
            imageView.setSmooth(true);
            imageView.setCache(false);

            // Add the ImageView to the VBox
            vBox.getChildren().add(imageView);

            // Create a Label and style it
            Label label1 = new Label("Book");
            label1.setFont(Font.font("System Bold", FontWeight.BOLD, 11));
            label1.setTextFill(Paint.valueOf("#000"));

            // Create a Label and style it
            vBox.getChildren().add(label1);

            // Create a Label and style it
            Label label2 = new Label("Label");
            label2.setFont(Font.font("System Bold", 11));
            label2.setTextFill(Paint.valueOf("#000"));

            // Create a Label and style it
            vBox.getChildren().add(label2);
        }

        return vBox;
    }

    /**
     * Add a give number of books to an HBox for display
     *
     * @param cards an unidentified number of Cards
     */
    private void addBooks(Card... cards) {

        // Loop through all the cards
        for (Card card : cards) {

            // Add a VBox for padding
            searchPageBookParent.getChildren().add(makeBookVbox(false));

            // Add the Card
            searchPageBookParent.getChildren().add(card);
        }
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
        Scene scene = new Scene(root, searchPageIcon.getScene().getWidth(), searchPageIcon.getScene().getHeight());

        // Add css to the scene
        String css = Objects.requireNonNull(getClass().getResource("/css/index.css")).toExternalForm();
        scene.getStylesheets().add(css);

        // Set up and display the new screen
        stage.setScene(scene);
        stage.show();
    }
}
