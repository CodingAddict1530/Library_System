package util;

import atlantafx.base.controls.Card;
import atlantafx.base.controls.Notification;
import atlantafx.base.theme.Styles;
import atlantafx.base.util.Animations;
import controllers.Controller;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.User;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.material2.Material2AL;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.OptionalInt;
import java.util.stream.Stream;

public class Utility {

    /**
     * Adds an undefined number of style classes to a given Node.
     *
     * @param node the Node to apply the style on.
     * @param styleClasses an undefined number of style classes to add.
     */
    public static void addStyleClassU(Node node, String... styleClasses) {

        // Loop through each style class.
        for (String styleClass : styleClasses) {

            // Add the style class to the node.
            node.getStyleClass().add(styleClass);
        }
    }

    /**
     * Adds a given style class to an undefined number of Node.
     *
     * @param styleClass the style class to add to the nodes.
     * @param nodes an undefined number of nodes.
     */
    public static void addStyleClassU(String styleClass, Node... nodes) {

        // Loop through each node.
        for (Node node : nodes) {

            // Add the style class to the node.
            node.getStyleClass().add(styleClass);
        }
    }

    /**
     * Loads the next screen.
     *
     * @param fxmlFileName the name of the fxml file.
     * @param event represents an action.
     * @param scene the scene of the previous page.
     * @throws IOException error during input/output operations.
     */
    public static void nextScreenU(String fxmlFileName, Event event, Scene scene, Class<?> runtimeClass, Object... data)
            throws IOException {

        // Parse and load the fxml file of the next screen.
        FXMLLoader fxmlLoader = new FXMLLoader(runtimeClass.getResource(String.format("/fxml/%s", fxmlFileName)));

        // Create the root Node.
        Parent root = fxmlLoader.load();

        // Pass the stage of the screen to the new stage variable.
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        // Create a new scene with the dimensions of the previous one.
        Scene newScene = new Scene(root, scene.getWidth(), scene.getHeight());

        // Get the controller of next page and pass data.
        Controller controller = fxmlLoader.getController();
        controller.setData(data);

        // Add css to the scene.
        List<String> cssFromScene1 = scene.getStylesheets();
        newScene.getStylesheets().addAll(cssFromScene1);

        // Set up and display the new screen.
        stage.setScene(newScene);
        stage.show();
    }

    /**
     * Creates a Card to wrap around a Node.
     *
     * @param node the node to put inside the Card.
     * @param parent the parent of the Node.
     * @param index the position inside the parent of the Node.
     */
    public static void createCardU(Node node, Node parent, OptionalInt index) {

        // Create a new Card object.
        Card card = new Card();

        // Add style to the Card.
        addStyleClassU(card, Styles.INTERACTIVE);

        // Add the Node inside the Card.
        card.setBody(node);

        // Check whether the parent is VBox or not.
        if (parent instanceof VBox parentVBox) {

            // Swap in the Card for the Node.
            swapNodesU(node, card, parentVBox, false,
                    (index.isPresent()) ? OptionalInt.of(index.getAsInt()) : OptionalInt.empty());
        } else {

            // Cast the parent to an HBox.
            HBox parentVBox = (HBox) parent;

            // Swap in the Card for the Node.
            swapNodesU(node, card, parentVBox, false,
                    (index.isPresent()) ? OptionalInt.of(index.getAsInt()) : OptionalInt.empty());
        }
    }

    /**
     * Fills up a particular Node with an Image.
     *
     * @param node the Node to take the Image.
     * @param runtimeClass The runtime class of the Node.
     * @param parentFolder the folder in which the image is under directly.
     * @param fileName the name of the image (include extension).
     */
    public static void addImageU(Node node, Class<?> runtimeClass, String parentFolder, String fileName) {

        // Fetch the image.
        Image image = new Image(Objects.requireNonNull(runtimeClass.getResourceAsStream(
                String.format("/images/%s/%s", parentFolder, fileName))));

        // Check whether the Node is an ImageView.
        if (node instanceof ImageView imageView) {

            // Add the Image.
            imageView.setImage(image);
        } else {

            // Cast the Node to a Circle and add the Image.
            Circle circle = (Circle) node;
            circle.setFill(new ImagePattern(image));
        }
    }

    /**
     * Fills up a particular Node with an Image.
     *
     * @param node the Node to take the Image.
     * @param path the path to the Image.
     */
    public static void addImageU(Node node, String path) {

        // Fetch the image.
        Image image = new Image(path);

        // Check whether the Node is an ImageView.
        if (node instanceof ImageView imageView) {

            // Add the Image.
            imageView.setImage(image);
        } else {

            // Cast the Node to a Circle and add the Image.
            Circle circle = (Circle) node;
            circle.setFill(new ImagePattern(image));
        }
    }

    /**
     * Add style to each given button.
     *
     * @param isSmall whether the button should be small or not.
     * @param buttons an array with an undefined number of buttons.
     */
    public static void styleButtonsU(boolean isSmall, Button... buttons) {

        // Loop through each button.
        for (Button button : buttons) {

            // Add style to the Button.
            addStyleClassU(button, Styles.ROUNDED, (isSmall) ? Styles.SMALL : Styles.MEDIUM);
            if (isSmall) {
                button.setPrefWidth(100);
            }
            button.setStyle(
                    "-fx-background-color: #d2a7fa;" +
                    "-fx-text-fill: white;" +
                    "-fx-font-weight: bold");
        }
    }

    /**
     * Adds style to the provided sidebar.
     *
     * @param sideBar the sidebar to be styled.
     */
    public static void styleSideBarU(VBox sideBar) {

        // Add style to the sidebar.
        sideBar.setStyle(
                "-fx-background-color: #d2a7fa;" +
                "-fx-background-radius: 15, 15, 0, 0"
        );
    }

    /**
     * Swaps Nodes inside a parent Node whether the position is known or not.
     *
     * @param nodeRemove the Node to be removed.
     * @param nodeAdd the Node to be added.
     * @param parent the parent Node.
     * @param grow Whether VGrow or HGrow shall be set.
     * @param position the position of the nodeRemove in the parent. (Optional).
     */
    public static void swapNodesU(Node nodeRemove, Node nodeAdd, Node parent, boolean grow, OptionalInt position) {

        // Initialize an index variable.
        int index = 0;

        // Check whether the parent is a VBox.
        if (parent instanceof VBox parentVBox) {

            // Check whether position variable has a value.
            if (position.isEmpty()) {

                // If it is empty, find the position of the child in the parent.
                for (int i = 0; i < parentVBox.getChildren().size(); i++) {
                    if (parentVBox.getChildren().get(i) == nodeRemove) {
                        index = i;
                        break;
                    }
                }
            } else {
                index = position.getAsInt();
            }

            // Swap the nodes.
            parentVBox.getChildren().remove(nodeRemove);
            parentVBox.getChildren().add(index, nodeAdd);

            // Set VGrow if applicable.
            if (grow) {
                VBox.setVgrow(nodeAdd, Priority.ALWAYS);
            }

        } else {

            // Cast the parent to an HBox.
            HBox parentHBox = (HBox) parent;

            // Check whether position variable has a value.
            if (position.isEmpty()) {

                // If it is empty, find the position of the child in the parent.
                for (int i = 0; i < parentHBox.getChildren().size(); i++) {
                    if (parentHBox.getChildren().get(i) == nodeRemove) {
                        index = i;
                        break;
                    }
                }
            } else {
                index = position.getAsInt();
            }

            // Swap the nodes.
            parentHBox.getChildren().remove(nodeRemove);
            parentHBox.getChildren().add(index, nodeAdd);

            // Set HGrow if applicable.
            if (grow) {
                HBox.setHgrow(nodeAdd, Priority.ALWAYS);
            }
        }
    }

    /**
     * Creates and shows a popup message.
     *
     * @param parent the parent to hold the popup.
     * @param text the text to be displayed in the popup.
     * @param style Style of the popup for example success, danger, etc.
     */
    public static void showPopUpU(VBox parent, String text, String style) {

        // Create the notification Object and style it.
        Notification message = new Notification(text, new FontIcon(Material2AL.ADD_ALERT));
        addStyleClassU(message, style, Styles.ELEVATED_1);
        message.setPrefHeight(Region.USE_PREF_SIZE);
        message.setMaxHeight(Region.USE_PREF_SIZE);
        StackPane.setAlignment(message, Pos.TOP_CENTER);
        StackPane.setMargin(message, new Insets(10, 10, 0, 0));

        // Create an animation for sliding in the popup.
        Timeline show = Animations.slideInDown(message, Duration.seconds(250));
        if (parent.getChildren().get(0)  instanceof Notification) {
            parent.getChildren().remove(0);
        }
        parent.getChildren().add(0, message);
        show.playFromStart();

        // Add a lambda expression to execute when the popup is closed.
        message.setOnClose(event1 -> {

            // Create an animation for sliding out the popup.
            Timeline close = Animations.slideOutUp(message, Duration.millis(250));
            close.setOnFinished(event2 -> {
                parent.getChildren().remove(message);
            });
            close.playFromStart();
        });
    }

    /**
     * Pauses the execution of code for a given time, the executes the provided method.
     *
     * @param method the method to get executed.
     * @param duration the duration of the pause.
     */
    public static void pauseU(Runnable method, Duration duration) {

        // Create a PauseTransition Object.
        PauseTransition pause = new PauseTransition(duration);

        // Set the method to run when the pause has finished.
        pause.setOnFinished(e -> {
            try {
                method.run();
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        });
        pause.play();
    }

    /**
     * Retrieves a file.
     *
     * @param fileName the name of the file.
     * @param directory the parent directory of the file
     * @return the path to the file.
     */
    public static String getFileU(String fileName, String directory) {
        try {

            // Try to find the file.
            Path foundFile = findFileU(Paths.get("src/main/resources/" + directory), fileName);
            if (foundFile != null) {

                // If found return its path.
                return foundFile.toFile().toString();
            } else {

                // Else return null.
                return null;
            }
        } catch (Exception ex) {
            return ex.getMessage();
        }
    }

    /**
     * Searches for a file.
     *
     * @param startDir the directory to start searching in.
     * @param fileName the name of the file.
     * @return a Path object for the file.
     * @throws IOException error during input/output operations.
     */
    private static Path findFileU(Path startDir, String fileName) throws IOException {

        // Try to find the file.
        try (Stream<Path> stream = Files.walk(startDir);) {

            // Return the Path of the first file matching the file name, or null if none.
            return stream
                    .filter(Files::isRegularFile)
                    .filter(path -> path.getFileName().toString().startsWith(fileName))
                    .findFirst()
                    .orElse(null);
        }
    }

    /**
     * Applies changes to the Label and profile picture when Controller data changes.
     *
     * @param data the controller data.
     * @param label1 the Label to be edited.
     * @param icon the Icon to be edited.
     * @param runtimeClass the runtime class (The controller).
     */
    public static void dataChangedU(Object[] data, Label label1, Circle icon, Class<?> runtimeClass) {

        // Check whether there is data.
        if (data != null && data.length > 0) {

            // Create a User object from the current user
            User user = (User) data[0];

            // Set profile to the path of the profile picture.
            String profile = (String) data[1];

            // Edit the text of the Label
            label1.setText(user.getFirst_name() + " " + user.getLast_name());

            // Check whether there is a profile picture.
            if (profile != null) {

                // Update the profile picture.
                addImageU(icon, profile);
            } else {

                // Set profile picture to the default picture.
                addImageU(icon, runtimeClass, "general", "user.png");
            }
        }
    }

}
