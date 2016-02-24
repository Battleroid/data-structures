import javafx.application.Application;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.util.ArrayList;

/**
 * Created by casey on 2016-02-23.
 */
public class Exercise22_09_NA extends Application {
    BorderPane bp = new BorderPane();

    @Override
    public void start(Stage stage) throws Exception {
        Point[] sample = new Point[]{
                new Point(20, 10), new Point(50, 50),
                new Point(10, 20), new Point(100, 30),
                new Point(100, 200), new Point(30, 30),
        };

        ArrayList<Point> result = convexHull(sample);
        System.out.println("Convex Hull: " + result.toString());
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
