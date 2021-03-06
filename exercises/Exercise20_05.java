import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.beans.property.DoubleProperty;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * Created by casey on 1/27/16.
 */
public class Exercise20_05 extends Application {
    @Override // Override the start method in the Application class
    public void start(Stage primaryStage) {
        MultipleBallPane ballPane = new MultipleBallPane();
        ballPane.setStyle("-fx-border-color: yellow");


        // Pause and resume animation
        Button btPause = new Button("Pause");
        Button btResume = new Button("Resume");
        btPause.setOnAction(e -> ballPane.pause());
        btResume.setOnAction(e -> ballPane.play());

        Button btAdd = new Button("+");
        Button btSubtract = new Button("-");
        HBox hBox = new HBox(10);
        hBox.getChildren().addAll(btPause, btResume, btAdd, btSubtract);
        hBox.setAlignment(Pos.CENTER);

        // Add or remove a ball
        btAdd.setOnAction(e -> ballPane.add());
        btSubtract.setOnAction(e -> ballPane.subtract());

        // Mouse event for adding ball
        ballPane.setOnMouseClicked(e -> ballPane.add(e.getX(), e.getY()));

        // Use a scroll bar to control animation speed
        ScrollBar sbSpeed = new ScrollBar();
        sbSpeed.setMax(20);
        sbSpeed.setValue(10);
        ballPane.rateProperty().bind(sbSpeed.valueProperty());

        BorderPane pane = new BorderPane();
        pane.setCenter(ballPane);
        pane.setTop(sbSpeed);
        pane.setBottom(hBox);

        // Create a scene and place the pane in the stage
        Scene scene = new Scene(pane, 640, 480);
        primaryStage.setTitle("MultipleBounceBall"); // Set the stage title
        primaryStage.setScene(scene); // Place the scene in the stage
        primaryStage.show(); // Display the stage
    }

    private class MultipleBallPane extends Pane {
        private Timeline animation;

        public MultipleBallPane() {
            // Create an animation for moving the ball
            animation = new Timeline(
                    new KeyFrame(Duration.millis(50), e -> moveBall()));
            animation.setCycleCount(Timeline.INDEFINITE);
            animation.play(); // Start animation
        }

        public void add() {
            Color color = new Color(Math.random(),
                    Math.random(), Math.random(), 0.5);
            getChildren().add(new Ball(30, 30, 20, color));
        }

        public void add(double x, double y) {
            Color color = new Color(Math.random(),
                    Math.random(), Math.random(), 0.5);
            getChildren().add(new Ball(x, y, 20, color));
        }

        public void subtract() {
            if (getChildren().size() > 0) {
                getChildren().remove(getChildren().size() - 1);
            }
        }

        public void play() {
            animation.play();
        }

        public void pause() {
            animation.pause();
        }

        public void increaseSpeed() {
            animation.setRate(animation.getRate() + 0.1);
        }

        public void decreaseSpeed() {
            animation.setRate(
                    animation.getRate() > 0 ? animation.getRate() - 0.1 : 0);
        }

        public DoubleProperty rateProperty() {
            return animation.rateProperty();
        }

        protected void moveBall() {
            for (Node node : this.getChildren()) {
                Ball ball = (Ball) node;
                // Check boundaries
                if (ball.getCenterX() < ball.getRadius() ||
                        ball.getCenterX() > getWidth() - ball.getRadius()) {
                    ball.dx *= -1; // Change ball move direction
                }
                if (ball.getCenterY() < ball.getRadius() ||
                        ball.getCenterY() > getHeight() - ball.getRadius()) {
                    ball.dy *= -1; // Change ball move direction
                }

                // Check for collision
                for (int i = 0; i < this.getChildren().size(); i++) {
                    Ball ballB = (Ball) this.getChildren().get(i);
                    if (ballB == ball) continue;

                    // skip everything, we're not even touching the outer bounds so forget visual collision detection
                    if (!ballB.intersects(ball.getBoundsInLocal())) continue;

                    // Instead of using the bounds (which are rectangular) use the visual intersection of two shapes
                    // to see if they are colliding, might be overkill, but certainly more accurate
                    Shape intersection = Shape.intersect(ballB, ball);

                    // if the intersection has any width, we have collided, otherwise there is no collision, -1 indicates no collision
                    if (intersection.getBoundsInLocal().getWidth() != -1) {
                        // remove balls from children
                        this.getChildren().removeAll(ball, ballB);

                        // // new position and check bounds
                        double radius = ball.getRadius() + ballB.getRadius();
                        double x = (ballB.getCenterX() - radius < 0 || ballB.getCenterX() + radius > getWidth()) ? radius + 1 : ballB.getCenterX();
                        double y = (ballB.getCenterY() - radius < 0 || ballB.getCenterY() + radius > getHeight()) ? radius + 1 : ballB.getCenterY();

                        // // new ball
                        Ball ballC = new Ball(x, y, radius, ball.color);
                        this.getChildren().add(ballC);
                    }
                }

                // Adjust ball position
                ball.setCenterX(ball.dx + ball.getCenterX());
                ball.setCenterY(ball.dy + ball.getCenterY());
            }
        }
    }

    class Ball extends Circle {
        private double dx = 1, dy = 1;
        protected Color color;

        Ball(double x, double y, double radius, Color color) {
            super(x, y, radius);
            this.color = color;
            setFill(this.color); // Set ball color
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
