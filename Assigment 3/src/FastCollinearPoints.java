import java.util.ArrayList;
import java.util.Arrays;

import edu.princeton.cs.algs4.*;

public class FastCollinearPoints {
    
    private LineSegment [] segments;
    
    public FastCollinearPoints(Point [] points) {
        if (points == null) 
            throw new java.lang.NullPointerException();
        if (checkDuplicates(points)) {
            throw new java.lang.IllegalArgumentException();
        }
        Point[] sortedPoints = Arrays.copyOf(points, points.length);
        ArrayList<LineSegment> segmentsList = new ArrayList<LineSegment>();
        Arrays.sort(sortedPoints);
        Arrays.sort(sortedPoints, sortedPoints[0].slopeOrder());
        int cont = 0;
        Point zeroPoint = new Point(0,0);
        for (int j = 0; j < sortedPoints.length; ++j) {
            Point origin = points[j];
            Arrays.sort(sortedPoints, origin.slopeOrder());
            
            for (int i = 0; i < sortedPoints.length; ++i) {
                Point current = sortedPoints[i];
                double currentM = origin.slopeTo(current);
                Point maxPoint = zeroPoint;
                
                while (i < sortedPoints.length - 1 && origin.slopeTo(sortedPoints[i + 1]) == currentM) {
                    if (origin.compareTo(sortedPoints[i]) == -1) {
                        if (maxPoint.compareTo(sortedPoints[i]) == -1) 
                            maxPoint = sortedPoints[i];
                        ++i;
                        ++cont;
                    } else break;
                }
                if (origin.slopeTo(sortedPoints[i]) == currentM && origin.compareTo(sortedPoints[i]) == -1) 
                    ++cont;
                if (cont >= 3) {
                    if (maxPoint.compareTo(sortedPoints[i]) == -1) 
                        maxPoint = sortedPoints[i];
                    Point q = maxPoint;
                    segmentsList.add(new LineSegment(origin, q));
                }
                cont = 0;
            }
        }
        segments = segmentsList.toArray(new LineSegment[segmentsList.size()]);
    }
    
    public int numberOfSegments() {
        return segments.length;
    }
    
    public LineSegment[] segments() {
        return Arrays.copyOf(segments, numberOfSegments());
    }
    
    private boolean checkDuplicates (Point [] points) {
        for (int i = 0; i < points.length; ++i) 
            for (int j = 0; j < points.length; ++j) 
                if (points[i].compareTo(points[j]) == 0)
                    return false;
        return true;
    }
    
    public static void main(String[] args) {

        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
