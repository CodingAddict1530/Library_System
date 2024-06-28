package controllers;

import atlantafx.base.controls.Calendar;
import atlantafx.base.controls.Card;
import atlantafx.base.theme.Styles;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * The controller class for the home screen
 */
public class HomePageController implements Initializable {

    // These represent Nodes in the scene of the home screen
    @FXML
    private Circle homePageIcon;
    @FXML
    private VBox homePageSideBar;
    @FXML
    private Label homePageLabel1;
    @FXML
    private Label homePageLabel2;
    @FXML
    private HBox homePageCalendar;
    @FXML
    private VBox homePageCalendarParent;
    @FXML
    private HBox homePageChart;
    @FXML
    private VBox homePageChartParent;

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
        homePageIcon.setFill(new ImagePattern(image));

        // Add style to the sidebar
        homePageSideBar.setStyle(
                "-fx-background-color: #d2a7fa;" +
                "-fx-background-radius: 15, 15, 0, 0"
        );

        // Add style to the labels
        homePageLabel1.getStyleClass().addAll(Styles.TITLE_2);
        homePageLabel2.getStyleClass().addAll(Styles.TITLE_2);

        // Create a Calendar
        Calendar calendar = initializeCalendar();

        // Add the Calendar to a styled card
        Card card1 = new Card();
        card1.getStyleClass().addAll(Styles.INTERACTIVE);
        card1.setBody(calendar);
        homePageCalendarParent.getChildren().remove(homePageCalendar);
        homePageCalendarParent.getChildren().add(1, card1);
        VBox.setVgrow(card1, Priority.ALWAYS);

        // Create a BarChart
        BarChart<String, Number> chart = initializeBarChart();

        // Add the BarChart to a styled card
        Card card2 = new Card();
        card2.getStyleClass().addAll(Styles.INTERACTIVE);
        card2.setBody(chart);
        homePageChartParent.getChildren().remove(homePageChart);
        homePageChartParent.getChildren().add(1, card2);
        VBox.setVgrow(card2, Priority.ALWAYS);
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
     * Creates a calendar Node
     *
     * @return the calendar
     */
    private Calendar initializeCalendar() {

        class Clock extends VBox {

            // Initialize the formatters
            static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("EEEE, LLLL dd, yyyy");
            static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss");

            /**
             * Instantiates a Clock object
             */
            public Clock() {

                // Set a label for the current time
                Label clockLabel = new Label(TIME_FORMATTER.format(OffsetDateTime.now()));

                // Add style to the label
                clockLabel.getStyleClass().addAll(Styles.TITLE_2);

                // Set a label for the current date
                Label dateLabel = new Label(DATE_FORMATTER.format(OffsetDateTime.now()));

                // Add style to the Clock object
                setStyle("-fx-border-width: 0 0 0.5 0; -fx-border-color: -color-border-default;");
                setSpacing(10);

                // Add the labels to the Clock object
                getChildren().setAll(clockLabel, dateLabel);

                // Set up a Timeline to create the changing time animation
                Timeline timeline = new Timeline(new KeyFrame(
                        Duration.seconds(1),
                        e -> {
                            OffsetDateTime time = OffsetDateTime.now();
                            clockLabel.setText(TIME_FORMATTER.format(time));
                        }
                ));
                timeline.setCycleCount(Animation.INDEFINITE);
                timeline.playFromStart();
            }
        }

        // Create a Calendar and add a Clock
        Calendar calendar = new Calendar(OffsetDateTime.now().toLocalDate());
        calendar.setTopNode(new Clock());

        return calendar;
    }

    /**
     * Creates a BarChart Node
     *
     * @return the bar chart
     */
    private BarChart<String, Number> initializeBarChart() {

        // Set up the axes
        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Genre");
        NumberAxis yAxis = new NumberAxis(0, 80, 10);
        yAxis.setLabel("Number of Borrows");

        // Set up April series and add data
        XYChart.Series<String, Number> apr = new XYChart.Series<String, Number>();
        apr.setName("April");
        apr.getData().add(new XYChart.Data<>("Horror", 10));
        apr.getData().add(new XYChart.Data<>("Comedy", 20));
        apr.getData().add(new XYChart.Data<>("Romance", 60));
        apr.getData().add(new XYChart.Data<>("Adventure", 20));

        // Set up May series and add data
        XYChart.Series<String, Number> may = new XYChart.Series<String, Number>();
        may.setName("May");
        may.getData().add(new XYChart.Data<>("Horror", 80));
        may.getData().add(new XYChart.Data<>("Comedy", 20));
        may.getData().add(new XYChart.Data<>("Romance", 10));
        may.getData().add(new XYChart.Data<>("Adventure", 10));

        // Set up June series and add data
        XYChart.Series<String, Number> jun = new XYChart.Series<String, Number>();
        jun.setName("June");
        jun.getData().add(new XYChart.Data<>("Horror", 30));
        jun.getData().add(new XYChart.Data<>("Comedy", 50));
        jun.getData().add(new XYChart.Data<>("Romance", 40));
        jun.getData().add(new XYChart.Data<>("Adventure", 70));

        // Create and set up the BarChart
        BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);
        barChart.setTitle("Trending Books");
        barChart.getData().addAll(apr, may, jun);

        return barChart;
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
        Scene scene = new Scene(root, homePageIcon.getScene().getWidth(), homePageIcon.getScene().getHeight());

        // Add css to the scene
        String css = Objects.requireNonNull(getClass().getResource("/css/index.css")).toExternalForm();
        scene.getStylesheets().add(css);

        // Set up and display the new screen
        stage.setScene(scene);
        stage.show();
    }
}
