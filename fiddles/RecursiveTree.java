import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.transform.NonInvertibleTransformException;
import javafx.stage.Stage;

/**
 * Created by casey on 2016-01-14.
 */
public class RecursiveTree extends Application {
    @Override
    public void start(Stage stage) {
        FiddlePane fp = new FiddlePane();
        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(fp);

        Scene scene = new Scene(borderPane, 500, 500);
        stage.setTitle("Tree");
        stage.setScene(scene);
        stage.show();

        scene.widthProperty().addListener(ov -> fp.paint());
        scene.heightProperty().addListener(ov -> fp.paint());

        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                switch (event.getCode()) {
                    case Z: fp.decStep(); break;
                    case X: fp.incStep(); break;
                    case C: fp.decLen();  break;
                    case V: fp.incLen();  break;
                    case B: fp.decRot();  break;
                    case N: fp.incRot();  break;
                }
            }
        });

        fp.setSteps(6);
    }

    static class FiddlePane extends Pane {
        private int steps = 3;
        private double theta = -90;
        private double rotAngle = 20;
        private int len = 10;

        public void incStep() {
            this.steps++;
            paint();
        }

        public void decStep() {
            this.steps = steps > 0 ? this.steps -= 1 : 0;
            paint();
        }

        public void setSteps(int steps) {
            this.steps = steps >= 0 ? steps : 0;
            paint();
        }

        public void incLen() {
            this.len += 5;
            paint();
        }

        public void decLen() {
            this.len = this.len >= 5 ? this.len -= 5 : 5;
            paint();
        }

        public void setLen(int len) {
            this.len = len >= 5 ? len : 5;
            paint();
        }

        public void incRot() {
            this.rotAngle += 5;
            paint();
        }

        public void decRot() {
            this.rotAngle -= 5;
            paint();
        }

        public void setRot(double rot) {
            this.rotAngle = rot;
            paint();
        }

        FiddlePane() {
        }

        protected void paint() {
            // clear
            this.getChildren().clear();

            Point2D p1 = new Point2D(getWidth() / 2, getHeight());
            Point2D p2 = new Point2D(getWidth() / 2, getHeight() - (this.len * this.steps));
            Line origin = new Line(p1.getX(), p1.getY(), p2.getX(), p2.getY());
            origin.setStrokeWidth(this.steps);
            origin.setStroke(Color.BLACK);

            // init
            this.getChildren().addAll(origin);
            createTree(this.steps, this.theta, origin);
        }

        private void createTree(int steps, double angle, Line line) {
            if (steps == 0) return;

            Point2D p0 = new Point2D(line.getEndX(), line.getEndY());
            Point2D p1 = new Point2D(
                    p0.getX() + (Math.cos(Math.toRadians(angle - this.rotAngle)) * steps * this.len),
                    p0.getY() + (Math.sin(Math.toRadians(angle - this.rotAngle)) * steps * this.len)
            );
            Point2D p2 = new Point2D(
                    p0.getX() + (Math.cos(Math.toRadians(angle + this.rotAngle)) * steps * this.len),
                    p0.getY() + (Math.sin(Math.toRadians(angle + this.rotAngle)) * steps * this.len)
            );

            // l & r branches
            Line left = new Line(p0.getX(), p0.getY(), p1.getX(), p1.getY());
            Line right = new Line(p0.getX(), p0.getY(), p2.getX(), p2.getY());
            double thickness = ((steps - 1) / (float) steps) * steps;
            if (thickness <= 0.5) thickness = 0.5;
            left.setStrokeWidth(thickness);
            right.setStrokeWidth(thickness);

            // add and continue
            this.getChildren().addAll(left, right);
            createTree(steps - 1, angle - 20, left);
            createTree(steps - 1, angle + 20, right);
        }
    }
}
