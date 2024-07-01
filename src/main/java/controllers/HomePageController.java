package controllers;

import atlantafx.base.controls.Calendar;
import atlantafx.base.controls.Card;
import atlantafx.base.theme.Styles;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;

import util.Clock;

import java.io.IOException;
import java.net.URL;
import java.time.OffsetDateTime;
import java.util.OptionalInt;
import java.util.ResourceBundle;

import static util.Utility.nextScreenU;
import static util.Utility.addStyleClassU;
import static util.Utility.dataChangedU;
import static util.Utility.styleSideBarU;
import static util.Utility.swapNodesU;

/**
 * The controller class for the home screen.
 */
public class HomePageController extends Controller implements Initializable {

    // These represent Nodes in the scene of the home screen.
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
     * Used to initialize a controller once the root Node has been created.
     *
     * @param url Location used to make paths for the root Node.
     * @param resourceBundle Resources used to locate the root Node.
     */
    @FXML
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // Add style to the sidebar.
        styleSideBarU(homePageSideBar);

        // Add style to the labels.
        addStyleClassU(Styles.TITLE_2, homePageLabel1, homePageLabel2);

        // Create a Calendar.
        Calendar calendar = initializeCalendar();

        // Add the Calendar to a styled card.
        Card card1 = new Card();
        card1.getStyleClass().addAll(Styles.INTERACTIVE);
        card1.setBody(calendar);

        // Swap the placeholder with the Calendar.
        swapNodesU(homePageCalendar, card1, homePageCalendarParent, true, OptionalInt.of(1));

        // Create a BarChart.
        BarChart<String, Number> chart = initializeBarChart();

        // Add the BarChart to a styled card.
        Card card2 = new Card();
        card2.getStyleClass().addAll(Styles.INTERACTIVE);
        card2.setBody(chart);

        // Swap the placeholder with the BarChart.
        swapNodesU(homePageChart, card2, homePageChartParent, true, OptionalInt.of(1));
    }

    /**
     * Logs you out and loads the first screen.
     *
     * @param event Represents an action, in this case signOut icon being clicked.
     * @throws IOException error during input/output operations.
     */
    @FXML
    public void signOut(MouseEvent event) throws IOException {

        nextScreenU("firstPage.fxml", event, homePageIcon.getScene(), getClass());
    }

    /**
     * Loads the search screen.
     *
     * @param event Represents an action, in this case search icon being clicked.
     * @throws IOException error during input/output operations.
     */
    @FXML
    public void search(MouseEvent event) throws IOException {

        nextScreenU("searchPage.fxml", event, homePageIcon.getScene(),
                getClass(), this.data[0], this.data[1]);
    }

    /**
     * Loads the settings screen.
     *
     * @param event Represents an action, in this case settings icon being clicked.
     * @throws IOException error during input/output operations.
     */
    @FXML
    public void settings(MouseEvent event) throws IOException {

        nextScreenU("settingsPage.fxml", event, homePageIcon.getScene(),
                getClass(), this.data[0], this.data[1]);
    }

    /**
     * Creates a calendar Node.
     *
     * @return the calendar.
     */
    private Calendar initializeCalendar() {

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

        // Set up the axes.
        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Genre");
        NumberAxis yAxis = new NumberAxis(0, 80, 10);
        yAxis.setLabel("Number of Borrows");

        // Set up series and add data.
        XYChart.Series<String, Number>[] series = createSeries("April", "May", "June");

        // Create and set up the BarChart.
        BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);
        barChart.setTitle("Trending Books");

        // Add each series to the Chart.
        for (XYChart.Series<String, Number> s : series) {
            barChart.getData().add(s);
        }

        return barChart;
    }

    /**
     * Creates an undefined number of series according to how many series names provided.
     *
     * @param seriesNames an array containing an undefined number of series names.
     * @return the array of series.
     */
    private XYChart.Series[] createSeries(String... seriesNames) {

        // Create an array to store the series.
        XYChart.Series[] seriesArray = new XYChart.Series[seriesNames.length];

        // Loop through each series name.
        for (int i = 0; i < seriesNames.length; i++) {

            // Create a new series and give it a name.
            XYChart.Series<String, Number> s = new XYChart.Series<String, Number>();
            s.setName(seriesNames[i]);

            // Add data to the series.
            addData(s);

            // Add the series to the array.
            seriesArray[i] = s;
        }

        return seriesArray;
    }

    /**
     * Adds data to a given series.
     *
     * @param series the series to add data to.
     */
    private void addData(XYChart.Series<String, Number> series) {

        // Add data to the series.
        series.getData().add(new XYChart.Data<>("Horror", 10));
        series.getData().add(new XYChart.Data<>("Comedy", 20));
        series.getData().add(new XYChart.Data<>("Romance", 60));
        series.getData().add(new XYChart.Data<>("Adventure", 20));
    }

    /**
     * Makes use of the data passed to the controller.
     */
    @Override
    protected void dataChanged() {

        dataChangedU(this.data, homePageLabel1, homePageIcon, getClass());
    }

}
