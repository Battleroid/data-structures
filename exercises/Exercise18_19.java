/**
 * Created by casey on 2016-01-14.
 */
import javafx.application.Application;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.stage.Stage;

public class Exercise18_19 extends Application {
    @Override // Override the start method in the Application class
    public void start(Stage primaryStage) {
        SierpinskiTrianglePane trianglePane = new SierpinskiTrianglePane();
        Button decBtn = new Button("-");
        Button incBtn = new Button("+");
        decBtn.setOnAction(e -> trianglePane.dec());
        incBtn.setOnAction(e -> trianglePane.inc());
        decBtn.setAlignment(Pos.BOTTOM_LEFT);
        incBtn.setAlignment(Pos.BOTTOM_RIGHT);

        // Pane to hold label, text field, and a button
        HBox hBox = new HBox(10);
        hBox.getChildren().addAll(decBtn, incBtn);
        hBox.setAlignment(Pos.CENTER);

        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(trianglePane);
        borderPane.setBottom(hBox);

        // Create a scene and place it in the stage
        Scene scene = new Scene(borderPane, 200, 210);
        primaryStage.setTitle("SierpinskiTriangle"); // Set the stage title
        primaryStage.setScene(scene); // Place the scene in the stage
        primaryStage.show(); // Display the stage

        scene.widthProperty().addListener(ov -> trianglePane.paint());
        scene.heightProperty().addListener(ov -> trianglePane.paint());

        // init
        trianglePane.setOrder(0);
    }

    /** Pane for displaying triangles */
    static class SierpinskiTrianglePane extends Pane {
        private int order = 0;

        /** Set a new order */
        public void setOrder(int order) {
            this.order = order;
            paint();
        }

        public void dec() {
            this.order--;
            if (this.order < 0) this.order = 0;
            paint();
        }

        public void inc() {
            this.order++;
            paint();
        }

        SierpinskiTrianglePane() {
        }

        protected void paint() {
            // Select three points in proportion to the panel size
            Point2D p1 = new Point2D(getWidth() / 2, 10);
            Point2D p2 = new Point2D(10, getHeight() - 10);
            Point2D p3 = new Point2D(getWidth() - 10, getHeight() - 10);

            this.getChildren().clear(); // Clear the pane before redisplay

            displayTriangles(order, p1, p2, p3);
        }

        private void displayTriangles(int order, Point2D p1,
                                      Point2D p2, Point2D p3) {
            if (order == 0) {
                // Draw a triangle to connect three points
                Polygon triangle = new Polygon();
                triangle.getPoints().addAll(p1.getX(), p1.getY(), p2.getX(),
                        p2.getY(), p3.getX(), p3.getY());
                triangle.setStroke(Color.BLACK);
                triangle.setFill(Color.WHITE);

                this.getChildren().add(triangle);
            }
            else {
                // Get the midpoint on each edge in the triangle
                Point2D p12 = p1.midpoint(p2);
                Point2D p23 = p2.midpoint(p3);
                Point2D p31 = p3.midpoint(p1);

                // Recursively display three triangles
                displayTriangles(order - 1, p1, p12, p31);
                displayTriangles(order - 1, p12, p2, p23);
                displayTriangles(order - 1, p31, p23, p3);
            }
        }
    }

    /**
     * The main method is only needed for the IDE with limited
     * JavaFX support. Not needed for running from the command line.
     */
    public static void main(String[] args) {
        launch(args);
    }
}
