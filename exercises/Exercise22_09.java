import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;

/**
 * Created by casey on 2016-02-23.
 */
public class Exercise22_09 extends Application {
    BorderPane bp = new BorderPane();

    @Override
    public void start(Stage stage) throws Exception {
        Point[] sample = new Point[]{
                new Point(20, 10), new Point(50, 50),
                new Point(10, 20), new Point(100, 30),
                new Point(100, 200), new Point(30, 30),
        };

        ArrayList<Point> result = convexHull(sample);
        System.out.println("Left with: " + result.toString());

        // for animated spawning of dots
        Circle[] sampleDots = new Circle[sample.length];
        for (int i = 0; i < sampleDots.length; i++) {
            sampleDots[i] = new Circle(sample[i].x, sample[i].y, 3);
            sampleDots[i].setFill(Color.RED);
            sampleDots[i].opacityProperty().set(0);
            if (result.contains(sample[i])) sampleDots[i].setFill(Color.DARKCYAN);
            bp.getChildren().add(sampleDots[i]);
            sampleDots[i].toFront();
        }

        // polygon of result
        Polygon p = new Polygon();
        p.setFill(Color.TRANSPARENT);
        p.setStroke(Color.BLACK);
        p.setStrokeWidth(3);
        for (Point pt : result) {
            p.getPoints().addAll(Double.valueOf(pt.x), Double.valueOf(pt.y));
        }
        bp.getChildren().add(p);

        // scene & stage related
        Scene scene = new Scene(bp, 250, 250);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("Sample Convex Hull");
        stage.show();

        // animated spawning of dots
        p.toBack();
        Timeline tl = new Timeline();
        for (int i = 0; i < sampleDots.length; i++) {
            tl.getKeyFrames().add(
                    new KeyFrame(Duration.seconds(1), new KeyValue(sampleDots[i].opacityProperty(), 1.0))
            );
        }
        tl.setCycleCount(1);
        tl.play();
    }

    // struct like class for points
    static class Point {
        int x;
        int y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public String toString() {
            return "(" + this.x + ", " + this.y + ")";
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

    public static int orientation(Point a, Point b, Point c) {
        int ret = (b.y - a.y) * (c.x - b.x) - (b.x - a.x) * (c.y - b.y);
        if (ret == 0) return 0;
        return (ret > 0) ? 1 : 2;
    }

    public static ArrayList<Point> convexHull(Point[] points) {
        ArrayList<Point> hull = new ArrayList<>();

        int l = 0;
        for (int i = 1; i < points.length; i++) {
            if (points[i].x < points[l].x) l = i;
        }

        int p = l, q;
        do {
            hull.add(points[p]);
            q = (p + 1) % points.length;
            for (int i = 0; i < points.length; i++) {
                if (orientation(points[p], points[i], points[q]) == 2) {
                    q = i;
                }
            }
            p = q;
        } while (p != l);

        return hull;
    }
}
