import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Stack;

/**
 * Created by casey on 2016-02-23.
 */
public class Exercise22_09_GA {
    public static void main(String[] args) {
        Point[] sample = new Point[]{
                new Point(10, 20), new Point(100, 30),
                new Point(20, 10), new Point(50, 50),
                new Point(100, 200), new Point(30, 30),
        };

        ArrayList<Point> result = convexHull(sample);
        System.out.println("Initial Points: " + Arrays.toString(sample));
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

    public static double distance(Point a, Point b) {
        return Math.pow(a.x - b.x, 2) + Math.pow(a.y - b.y, 2);
    }

    static class AngleComparator implements Comparator<Point> {
        private Point p0;

        public AngleComparator(Point p0) {
            this.p0 = p0;
        }

        public int compare(Point a, Point b) {
            int o = orientation(p0, a, b);
            if (o == 0) return (distance(p0, b) >= distance(p0, a)) ? -1 : 1;
            return (o == 2) ? -1 : 1;
        }
    }

    public static Point nextTo(Stack<Point> s) {
        Point p = s.lastElement();
        s.pop();
        Point next = s.lastElement();
        s.push(p);
        return next;
    }

    public static ArrayList<Point> convexHull(Point[] points) {
        Point[] from = points.clone();

        // find starting point
        int min = 0;
        double minx = points[min].x;
        double miny = points[min].y;
        for (int i = 1; i < from.length; i++) {
            double x = from[i].x;
            double y = from[i].y;
            minx = from[min].x;
            miny = from[min].y;

            // y is flipped as javafx coordinate system starts from top left
            if ((y > miny) || (miny == y && x < minx)) {
                miny = y;
                min = i;
            }
        }

        // sort based on angle
        Point p0 = from[min];
        Arrays.sort(from, new AngleComparator(p0));

        // build hull
        int n = 1;
        for (int i = 1; i < from.length; i++) {
            while (i < from.length - 1 && orientation(p0, from[i], from[i + 1]) == 0) i++;
            from[n] = from[i];
            n++;
        }

        Stack<Point> s = new Stack<>();
        s.push(from[0]);
        s.push(from[1]);
        s.push(from[2]);

        for (int i = 3; i < n; i++) {
            while (orientation(nextTo(s), s.lastElement(), from[i]) != 2) s.pop();
            s.push(from[i]);
        }

        // add to hull
        ArrayList<Point> hull = new ArrayList<>();
        for (Point p : s) {
            hull.add(p);
        }

        return hull;
    }
}
