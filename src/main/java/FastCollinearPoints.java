package done;

import java.util.Arrays;

/**
 *
 */

public class FastCollinearPoints {

    private Point[] points;

    private int numberOfSegments = 0;

    private LineSegment[] lineSegments;

    public FastCollinearPoints(Point[] points) {

        this.points = points;
        if (points == null) {
            throw new NullPointerException();
        }

        if (containsDuplicates(points)) {
            throw new IllegalArgumentException();
        }

        lineSegments = new LineSegment[points.length];

        Point[] copyForOriginSelection = getPointCopy();
        Arrays.sort(copyForOriginSelection);

        for (Point origin : copyForOriginSelection) {

            Point[] copy = getPointCopy();

            Arrays.sort(copy, origin.slopeOrder());

            int currentStart = 0;
            double lastSlope = origin.slopeTo(copy[1]);

            for (int i = 1; i < copy.length; i++) {

                if (origin.compareTo(copy[i]) == 0){
                    continue;
                }

                if (origin.slopeTo(copy[i]) != lastSlope) {
                    addPotentialSegment(copy, origin, currentStart, i-1);
                    currentStart = i + 1;
                }
                lastSlope = origin.slopeTo(copy[i]);
            }
        }
    }

    private void addPotentialSegment(Point[] copy, Point origin, int currentStart, int currentEnd) {
        if (currentEnd - currentStart < 3) {
            return;
        }
        Point[] sameSlopes = new Point[currentEnd - currentStart + 1];
        System.arraycopy(copy, currentStart, sameSlopes, 0, currentEnd - currentStart + 1);

        Arrays.sort(sameSlopes);

        if (origin.compareTo(sameSlopes[0]) == 0) {

            if (numberOfSegments == lineSegments.length) {
                expandSegmentArray();
            }
            lineSegments[numberOfSegments] = new LineSegment(sameSlopes[0], sameSlopes[sameSlopes.length - 1]);
            numberOfSegments++;
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

    private Point[] getPointCopy() {
        Point[] copiedPoints = new Point[points.length];
        System.arraycopy(points, 0, copiedPoints, 0, points.length);
        return copiedPoints;
    }


    private boolean containsDuplicates(Point[] points) {

        Point[] copiedPoints = getPointCopy();
        Arrays.sort(copiedPoints);

        for (int i = 0; i < copiedPoints.length - 1; i++) {
            if (copiedPoints[i].compareTo(copiedPoints[i + 1]) == 0) {
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

    public static void main(String[] args) {
        Point[] array = {
                new Point(1, 5),
                new Point(1, 1),
                new Point(0, 0),
                new Point(2, 2),
                new Point(4, 4),
                new Point(3, 3),
                new Point(10, 20),
        };

        FastCollinearPoints fastCollinearPoints = new FastCollinearPoints(array);

        System.out.println(fastCollinearPoints.numberOfSegments());
        System.out.println(Arrays.toString(fastCollinearPoints.segments()));
    }
}
