import java.util.ArrayList;
import java.util.Arrays;
import edu.princeton.cs.algs4.*;
import edu.princeton.cs.algs4.Merge;
import edu.princeton.cs.algs4.StdDraw;

public class BruteCollinearPoints {
    private LineSegment [] segments;
    
    public BruteCollinearPoints(Point [] points) {
        if (points == null) 
            throw new java.lang.NullPointerException();
        if (checkDuplicates(points)) {
            throw new java.lang.IllegalArgumentException();
        }
        Point[] sortedPoints = Arrays.copyOf(points, points.length);
        Merge.sort(sortedPoints);
        ArrayList<LineSegment> segmentsList = new ArrayList<LineSegment>();
        
        for (int p = 0; p < sortedPoints.length - 3; ++p) {
            for (int q = p + 1; q < sortedPoints.length - 2; ++q){
                for (int r = q + 1; r < sortedPoints.length - 1; ++r) {
                    for (int s = r + 1; s < sortedPoints.length; ++s) {
                        double slopePQ = sortedPoints[p].slopeTo(sortedPoints[q]);
                        double slopePR = sortedPoints[p].slopeTo(sortedPoints[r]);
                        double slopePS = sortedPoints[p].slopeTo(sortedPoints[s]);
                        if (slopePQ == slopePR && slopePQ == slopePS) {
                            Point pPoint = sortedPoints[p];
                            Point sPoint = sortedPoints[s];
                            LineSegment ln = new LineSegment(pPoint, sPoint);
                            segmentsList.add(ln);
                        }
                    }
                }
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
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}