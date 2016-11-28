import java.util.ArrayList;
import java.util.TreeSet;

import edu.princeton.cs.algs4.*;
public class PointSET {

    private TreeSet<Point2D> pointSet;

    public PointSET() {
        pointSet = new TreeSet<>();
    }

    public boolean isEmpty() {
        return pointSet.size() == 0;
    }

    public int size() {
        return pointSet.size();
    }

    public void insert(Point2D p) {
        pointSet.add(p);
    }

    public boolean contains(Point2D p) {
        return pointSet.contains(p);
    }

    public void draw() {
 
    }

    public Iterable<Point2D> range(RectHV rect) {
        return getRange(rect);
    }

    private ArrayList<Point2D> getRange(RectHV rect){
        ArrayList<Point2D> inRangeSet = new ArrayList<>();
        for (Point2D p : pointSet) {
            if (rect.contains(p)) inRangeSet.add(p);
        }
        return inRangeSet;
    }

    public Point2D nearest(Point2D p) {
        double minDistance = Double.MAX_VALUE;
        Point2D nearest = null;
        for (Point2D point : pointSet) {
            if (point.distanceSquaredTo(p) < minDistance) {
                nearest = point;
                minDistance = point.distanceSquaredTo(p);
            }
        }
        return nearest;
    }
}
