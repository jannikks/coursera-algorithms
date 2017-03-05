package done;

import edu.princeton.cs.algs4.StdDraw;

import java.util.Comparator;

/**
 *
 */

public class Point implements Comparable<Point> {


    private final int x;     // x-coordinate of this point
    private final int y;     // y-coordinate of this point

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void draw() {
        StdDraw.point(x, y);
    }

    public void drawTo(Point that) {
        StdDraw.line(this.x, this.y, that.x, that.y);

    }

    public String toString() {

        return "(" + x + ", " + y + ")";
    }

    @Override
    public int compareTo(Point that) {
        if (y < that.y) {
            return -1;
        }
        if (y == that.y) {
            if (x < that.x) {
                return -1;
            }
            if (x == that.x) {
                return 0;
            }
        }
        return 1;
    }

    public double slopeTo(Point that) {


        if (x == that.x && y != that.y) {
            //vertical
            return Double.POSITIVE_INFINITY;
        } else if (x == that.x && y == that.y) {
            //equal
            return Double.NEGATIVE_INFINITY;
        } else if (that.y - y == 0) {
            return 0.0;
        }

        return (that.y - y) / (double)(that.x - x);
    }

    public Comparator<Point> slopeOrder() {
        return (p1, p2) -> Double.compare(slopeTo(p1), slopeTo(p2));
    }

    public static void main(String[] args) {

        Point p1 = new Point(283, 171);
        Point p2 = new Point(283, 171);

        System.out.println(p1.slopeTo(p2));
    }
}
