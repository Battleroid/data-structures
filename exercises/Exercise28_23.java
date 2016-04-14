/**
 * Created by casey on 2016-04-14.
 */

import Exercise28_01Extra.Edge;
import Exercise28_01Extra.Graph;
import Exercise28_01Extra.UnweightedGraph;
import javafx.application.Application;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;

public class Exercise28_23 extends Application {
    @Override // Override the start method in the Application class
    public void start(Stage primaryStage) {
        // Create a scene and place it in the stage
        Scene scene = new Scene(new RectanglePane(), 450, 350);
        primaryStage.setTitle("Connected Rectangles"); // Set the stage title
        primaryStage.setScene(scene); // Place the scene in the stage
        primaryStage.show(); // Display the stage
    }

    /**
     * Panel for displaying rectangles
     */
    class RectanglePane extends Pane {
        public RectanglePane() {
            this.setOnMouseClicked(e -> {
                Point2D pt = new Point2D(e.getX(), e.getY());
                if (!isInsideRectangle(pt)) {
                    Rectangle r = new Rectangle(pt.getX(), pt.getY(), 25, 25);
                    getChildren().add(r);
                    colorIfConnected();
                }
            });
        }

        /**
         * Returns true if the point is inside an existing rectangle
         */
        private boolean isInsideRectangle(Point2D p) {
            for (Node rect : this.getChildren())
                if (rect.contains(p))
                    return true;

            return false;
        }

        /**
         * Color all rectangles if they are connected
         */
        private void colorIfConnected() {
            if (getChildren().size() == 0)
                return; // No rectangles in the pane

            // Build the edges
            java.util.List<Edge> edges =
                    new java.util.ArrayList<>();
            for (int i = 0; i < getChildren().size(); i++)
                for (int j = i + 1; j < getChildren().size(); j++)
                    if (overlaps((Rectangle) (getChildren().get(i)),
                            (Rectangle) (getChildren().get(j)))) {
                        edges.add(new Edge(i, j));
                        edges.add(new Edge(j, i));
                    }

            // Create a graph with rectangles as vertices
            Graph<Node> graph = new UnweightedGraph<>
                    ((java.util.List<Node>) getChildren(), edges);
            UnweightedGraph<Node>.SearchTree tree = graph.dfs(0);
            boolean isAllRectConnected = getChildren().size() == tree
                    .getNumberOfVerticesFound();

            for (Node rect : getChildren()) {
                if (isAllRectConnected) { // All rectangles are connected
                    ((Rectangle) rect).setFill(Color.RED);
                } else {
                    ((Rectangle) rect).setStroke(Color.BLACK);
                    ((Rectangle) rect).setFill(Color.WHITE);
                }
            }
        }
    }

    /**
     * Checks for visual intersection of two rectangles.
     *
     * @param rect1
     * @param rect2
     * @return
     */
    public static boolean overlaps(Rectangle rect1, Rectangle rect2) {
        Shape xsect = Shape.intersect(rect1, rect2);
        return xsect.getBoundsInLocal().getHeight() != -1;
    }

    /**
     * The main method is only needed for the IDE with limited
     * JavaFX support. Not needed for running from the command line.
     */
    public static void main(String[] args) {
        launch(args);
    }
}
