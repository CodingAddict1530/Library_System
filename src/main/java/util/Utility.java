package util;

import atlantafx.base.controls.Card;
import atlantafx.base.theme.Styles;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.OptionalInt;

public class Utility {

    /**
     * Adds an undefined number of style classes to a given Node
     *
     * @param node the Node to apply the style on
     * @param styleClasses an undefined number of style classes to add
     */
    public static void addStyleClassU(Node node, String... styleClasses) {

        // Loop through each style class
        for (String styleClass : styleClasses) {

            // Add the style class to the node
            node.getStyleClass().add(styleClass);
        }
    }

    /**
     * Adds a given style class to an undefined number of Node
     *
     * @param styleClass the style class to add to the nodes
     * @param nodes an undefined number of nodes
     */
    public static void addStyleClassU(String styleClass, Node... nodes) {

        // Loop through each node
        for (Node node : nodes) {

            // Add the style class to the node
            node.getStyleClass().add(styleClass);
        }
    }

    /**
     * Loads the next screen
     *
     * @param fxmlFileName the name of the fxml file
     * @param event represents an action
     * @param scene the scene of the previous page
     * @throws IOException error during input/output operations
     */
    public static void nextScreenU(String fxmlFileName, Event event, Scene scene, Class<?> runtimeClass)
            throws IOException {

        // Parse and load the fxml file of the next screen
        FXMLLoader fxmlLoader = new FXMLLoader(runtimeClass.getResource(String.format("/fxml/%s", fxmlFileName)));

        // Create the root Node
        Parent root = fxmlLoader.load();

        // Pass the stage of the screen to the new stage variable
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        // Create a new scene with the dimensions of the previous one
        Scene newScene = new Scene(root, scene.getWidth(), scene.getHeight());

        // Add css to the scene
        List<String> cssFromScene1 = scene.getStylesheets();
        newScene.getStylesheets().addAll(cssFromScene1);

        // Set up and display the new screen
        stage.setScene(newScene);
        stage.show();
    }

    /**
     * Creates a Card to wrap around a Node
     *
     * @param node the node to put inside the Card
     * @param parent the parent of the Node
     * @param index the position inside the parent of the Node
     */
    public static void createCardU(Node node, Node parent, OptionalInt index) {

        // Create a new Card object
        Card card = new Card();

        // Add style to the card
        addStyleClassU(card, Styles.INTERACTIVE);

        // Add the Node inside the Card
        card.setBody(node);

        // Check whether the parent is VBox or not
        if (parent instanceof VBox parentVBox) {

            // Swap in the Card for the Node
            swapNodesU(node, card, parentVBox, false,
                    (index.isPresent()) ? OptionalInt.of(index.getAsInt()) : OptionalInt.empty());
        } else {

            // Cast the parent to an HBox
            HBox parentVBox = (HBox) parent;

            // Swap in the Card for the Node
            swapNodesU(node, card, parentVBox, false,
                    (index.isPresent()) ? OptionalInt.of(index.getAsInt()) : OptionalInt.empty());
        }
    }

    /**
     * Fills up a particular Node with an Image
     *
     * @param node the Node to take the Image
     * @param runtimeClass The runtime class of the Node
     * @param parentFolder the folder in which the image is under directly
     * @param fileName the name of the image (include extension)
     */
    public static void addImageU(Node node, Class<?> runtimeClass, String parentFolder, String fileName) {

        // Fetch the image
        Image image = new Image(Objects.requireNonNull(runtimeClass.getResourceAsStream(
                String.format("/images/%s/%s", parentFolder, fileName))));

        // Check whether the Node is an ImageView
        if (node instanceof ImageView imageView) {

            // Add the Image
            imageView.setImage(image);
        } else {

            // Cast the Node to a Circle and add the Image
            Circle circle = (Circle) node;
            circle.setFill(new ImagePattern(image));
        }
    }

    /**
     * Add style to each given button
     *
     * @param isSmall whether the button should be small or not
     * @param buttons an array with an undefined number of buttons
     */
    public static void styleButtonsU(boolean isSmall, Button... buttons) {

        // Loop through each button
        for (Button button : buttons) {

            // Add style to the Button
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
     * Adds style to the provided sidebar
     *
     * @param sideBar the sidebar to be styled
     */
    public static void styleSideBarU(VBox sideBar) {

        // Add style to the sidebar
        sideBar.setStyle(
                "-fx-background-color: #d2a7fa;" +
                "-fx-background-radius: 15, 15, 0, 0"
        );
    }

    /**
     * Swaps Nodes inside a parent Node whether the position is known or not
     *
     * @param nodeRemove the Node to be removed
     * @param nodeAdd the Node to be added
     * @param parent the parent Node
     * @param grow Whether VGrow or HGrow shall be set
     * @param position the position of the nodeRemove in the parent. (Optional)
     */
    public static void swapNodesU(Node nodeRemove, Node nodeAdd, Node parent, boolean grow, OptionalInt position) {

        // Initialize an index variable
        int index = 0;

        // Check whether the parent is a VBox
        if (parent instanceof VBox parentVBox) {

            // Check whether position variable has a value
            if (position.isEmpty()) {

                // If it is empty, find the position of the child in the parent
                for (int i = 0; i < parentVBox.getChildren().size(); i++) {
                    if (parentVBox.getChildren().get(i) == nodeRemove) {
                        index = i;
                        break;
                    }
                }
            } else {
                index = position.getAsInt();
            }

            // Swap the nodes
            parentVBox.getChildren().remove(nodeRemove);
            parentVBox.getChildren().add(index, nodeAdd);

            // Set VGrow if applicable
            if (grow) {
                VBox.setVgrow(nodeAdd, Priority.ALWAYS);
            }

        } else {

            // Cast the parent to an HBox
            HBox parentHBox = (HBox) parent;

            // Check whether position variable has a value
            if (position.isEmpty()) {

                // If it is empty, find the position of the child in the parent
                for (int i = 0; i < parentHBox.getChildren().size(); i++) {
                    if (parentHBox.getChildren().get(i) == nodeRemove) {
                        index = i;
                        break;
                    }
                }
            } else {
                index = position.getAsInt();
            }

            // Swap the nodes
            parentHBox.getChildren().remove(nodeRemove);
            parentHBox.getChildren().add(index, nodeAdd);

            // Set HGrow if applicable
            if (grow) {
                HBox.setHgrow(nodeAdd, Priority.ALWAYS);
            }
        }
    }

}
