package done;

import java.util.Arrays;

/**
 *
 */
public class BruteCollinearPoints {

    private final Point[] points;

    private int numberOfSegments;

    private LineSegment[] lineSegments;

    public BruteCollinearPoints(Point[] points) {

        this.points = points;
        if (points == null) {
            throw new NullPointerException();
        }

        if (containsDuplicates(points)) {
            throw new IllegalArgumentException();
        }

        lineSegments = new LineSegment[points.length];

        for (int p1 = 0; p1 < points.length - 3; p1++) {
            for (int p2 = p1 + 1; p2 < points.length - 2; p2++) {
                for (int p3 = p2 + 1; p3 < points.length - 1; p3++) {
                    for (int p4 = p3 + 1; p4 < points.length; p4++) {
                        if (sameSlopes(p1, p2, p3, p4)) {
                            if (numberOfSegments == lineSegments.length) {
                                expandSegmentArray();
                            }
                            addLineSegment(p1, p2, p3, p4);
                            numberOfSegments++;
                        }

                    }
                }
            }
        }
    }

    public int numberOfSegments() {
        return numberOfSegments;
    }       // the number of line segments

    public LineSegment[] segments() {
        LineSegment[] cappedSegments = new LineSegment[numberOfSegments()];
        System.arraycopy(lineSegments, 0, cappedSegments, 0, numberOfSegments());
        return cappedSegments;
    }

    private boolean containsDuplicates(Point[] points) {

        Point[] copiedPoints = new Point[points.length];

        System.arraycopy(points, 0, copiedPoints, 0, points.length);

        Arrays.sort(copiedPoints);

        for (int i = 0; i < copiedPoints.length - 1; i++) {
            if (copiedPoints[i].compareTo(copiedPoints[i+1]) == 0){
                return true;
            }
        }
        return false;
    }

    private void expandSegmentArray() {

        LineSegment[] biggerArray = (LineSegment[]) new Object[numberOfSegments * 2];
        System.arraycopy(lineSegments, 0, biggerArray, 0, lineSegments.length);
        this.lineSegments = biggerArray;
    }

    private void addLineSegment(int p1, int p2, int p3, int p4) {

        Point[] p = {points[p1], points[p2], points[p3], points[p4]};
        Arrays.sort(p);
        lineSegments[numberOfSegments] = new LineSegment(p[0], p[3]);
    }

    // checks slopes between p and q, between p and r, and between p and s are all equal.
    private boolean sameSlopes(int p1, int p2, int p3, int p4) {

        double baseSlope = points[p1].slopeTo(points[p2]);
        return baseSlope == (points[p1].slopeTo(points[p3])) && baseSlope == (points[p1].slopeTo(points[p4]));
    }


    public static void main(String[] args) {
        Point[] array = {
                new Point(1, 5),
                new Point(1, 1),
                new Point(0, 0),
                new Point(2, 2),
                new Point(3, 3),
                new Point(3, 3),
        };

        BruteCollinearPoints bruteCollinearPoints = new BruteCollinearPoints(array);

        System.out.println(bruteCollinearPoints.numberOfSegments());
        System.out.println(Arrays.toString(bruteCollinearPoints.segments()));
    }
}
