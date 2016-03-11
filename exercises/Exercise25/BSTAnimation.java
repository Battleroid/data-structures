package Exercise25;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.util.Iterator;

public class BSTAnimation extends Application {
    /**
     * The main method is only needed for the IDE with limited
     * JavaFX support. Not needed for running from the command line.
     */
    public static void main(String[] args) {
        launch(args);
    }

    @Override // Override the start method in the Application class
    public void start(Stage primaryStage) {
        BST<Integer> tree = new BST<>(); // Create a tree

        BorderPane pane = new BorderPane();
        BSTView view = new BSTView(tree); // Create a View
        pane.setCenter(view);

        TextField tfKey = new TextField();
        tfKey.setPrefColumnCount(3);
        tfKey.setAlignment(Pos.BASELINE_RIGHT);
        Button btInsert = new Button("Insert");
        Button btDelete = new Button("Delete");
        Button btInOrder = new Button("In Order");
        Button btPreOrder = new Button("Pre Order");
        Button btPostOrder = new Button("Post Order");
        HBox hBox = new HBox(8);
        hBox.getChildren().addAll(new Label("Enter a key: "),
                tfKey, btInsert, btDelete, btInOrder, btPreOrder, btPostOrder);
        hBox.setAlignment(Pos.CENTER);
        pane.setBottom(hBox);

        btInsert.setOnAction(e -> {
            int key = Integer.parseInt(tfKey.getText());
            if (tree.search(key)) { // key is in the tree already
                view.displayTree();
                view.setStatus(key + " is already in the tree");
            } else {
                tree.insert(key); // Insert a new key
                view.displayTree();
                view.setStatus(key + " is inserted in the tree");
            }
        });

        btDelete.setOnAction(e -> {
            int key = Integer.parseInt(tfKey.getText());
            if (!tree.search(key)) { // key is not in the tree
                view.displayTree();
                view.setStatus(key + " is not in the tree");
            } else {
                tree.delete(key); // Delete a key
                view.displayTree();
                view.setStatus(key + " is deleted from the tree");
            }
        });

        btInOrder.setOnAction(e -> {
            String ordering = "In Order: [ ";
            for (Integer i : tree) {
                ordering += i + " ";
            }
            view.setOrdering(ordering + "]");
        });

        btPreOrder.setOnAction(e -> {
            String ordering = "Pre Order: [ ";
            for (Iterator<Integer> it = tree.preorderIterator(); it.hasNext(); ) {
                ordering += it.next() + " ";
            }
            view.setOrdering(ordering + " ]");
        });

        btPostOrder.setOnAction(e -> {
            String ordering = "Post Order: [ ";
            for (Iterator<Integer> it = tree.postorderIterator(); it.hasNext(); ) {
                ordering += it.next() + " ";
            }
            view.setOrdering(ordering + " ]");
        });

        // Create a scene and place the pane in the stage
        Scene scene = new Scene(pane, 450, 250);
        primaryStage.setTitle("BSTAnimation"); // Set the stage title
        primaryStage.setScene(scene); // Place the scene in the stage
        primaryStage.show(); // Display the stage
        scene.widthProperty().addListener(ov -> view.displayTree());
        scene.heightProperty().addListener(ov -> view.displayTree());

        // testing
        int[] keys = {10, 5, 7, 6, 3, 2, 1, 15, 17, 19, 18};
        for (int k : keys) tree.insert(k);
        view.displayTree();
    }
}
