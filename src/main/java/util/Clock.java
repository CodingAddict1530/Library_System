package util;

import atlantafx.base.theme.Styles;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

import static util.Utility.addStyleClassU;

public class Clock extends VBox {

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
        addStyleClassU(clockLabel, Styles.TITLE_2);

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
