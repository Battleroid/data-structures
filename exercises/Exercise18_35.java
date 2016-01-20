import javafx.application.Application;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

/**
 * Created by casey on 2016-01-19.
 */
public class Exercise18_35 extends Application {
    @Override
    public void start(Stage stage) {
        HTree htree = new HTree();
        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(htree);

        Scene scene = new Scene(borderPane, 500, 500);
        stage.setTitle("HTree");
        stage.setScene(scene);
        stage.show();
        stage.setResizable(false);

        scene.widthProperty().addListener(ov -> htree.paint());
        scene.heightProperty().addListener(ov -> htree.paint());

        htree.setSteps(4);
    }

    static class HTree extends Pane {
        private int steps = 0;

        public void setSteps(int steps) {
            this.steps = steps >= 0 ? steps : 0;
            paint();
        }

        public void paint() {
            this.getChildren().clear();

            // constants
            double w = this.getWidth() / 4f;
            double h = this.getHeight() / 4f;

            // middle, left, right
            Line middle = new Line(w, h * 2, w * 3, h * 2);
            Line left = new Line(middle.getStartX(), middle.getStartY() - h, middle.getStartX(), middle.getStartY() + h);
            Line right = new Line(middle.getEndX(), middle.getEndY() - h, middle.getEndX(), middle.getEndY() + h);

            // add
            this.getChildren().addAll(middle, left, right);
            createH(steps - 1, left);
            createH(steps - 1, right);
        }

        private double distance(double x1, double y1, double x2, double y2) {
            return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
        }

        private double distance(Line line) {
            return Math.sqrt(Math.pow(line.getEndX() - line.getStartX(), 2) + Math.pow(line.getEndY() - line.getStartY(), 2));
        }

        private Point2D midpoint(Line line) {
            double x = ((line.getStartX() + line.getEndX()) / 2.0);
            double y = ((line.getStartY() + line.getEndY()) / 2.0);
            return new Point2D(x, y);
        }

        private void createH(int steps, Line origin) {
            if (steps == 0) return;

            Point2D midpoint = midpoint(origin);
            double length = distance(origin.getStartX(), origin.getEndY(), midpoint.getX(), midpoint.getY());

            // top
            Line topM = new Line(origin.getStartX() - length / 2, origin.getStartY(), origin.getStartX() + length / 2, origin.getStartY());
            Line topL = new Line(topM.getStartX(), topM.getStartY() - length / 2, topM.getStartX(), topM.getStartY() + length / 2);
            Line topR = new Line(topM.getEndX(), topM.getStartY() - length / 2, topM.getEndX(), topM.getStartY() + length / 2);

            // bottom
            Line botM = new Line(origin.getStartX() - length / 2, origin.getEndY(), origin.getStartX() + length / 2, origin.getEndY());
            Line botL = new Line(botM.getStartX(), botM.getStartY() - length / 2, botM.getStartX(), botM.getStartY() + length / 2);
            Line botR = new Line(botM.getEndX(), botM.getStartY() - length / 2, botM.getEndX(), botM.getStartY() + length / 2);

            // add
            this.getChildren().addAll(topM, topL, topR, botM, botL, botR);
            createH(steps - 1, topL);
            createH(steps - 1, topR);
            createH(steps - 1, botL);
            createH(steps - 1, botR);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
