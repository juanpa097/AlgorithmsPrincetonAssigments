import java.util.ArrayList;

import javax.naming.spi.DirStateFactory;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

public class KdTree {
    private static final int DIMENTION = 2;
    private static final int DIMX = 0;
    private static final int DIMY = 1;
    private static final int RIGHT = 1;
    private static final int LEFT = 2;
    private static final int BOTH = 3;
    
    private Node root;
    private int numNodes;

    public KdTree() {
        root = null;
        numNodes = 0;
    }

    public boolean isEmpty() {
        return root == null;
    }

    public int size() {
        return numNodes;
    }

    public void insert(Point2D p) {
        if (!contains(p))
        root = insert(root, p, 0);
    }

    private Node insert(Node x, Point2D p, int level) {
        if (x == null) {
            ++numNodes;
            return new Node(p); 
        }
        int dim = level % DIMENTION;
        if (comparte(x.p, p, dim)) // To Right
            x.right = insert(x.right, p, level+1);
        else  // To left
            x.left = insert(x.left, p, level+1);
        return x;
    }

    // Returns true if q belongs to the right of p, false otherwise.
    private boolean comparte (Point2D p, Point2D q, int dim) {
        if (dim == DIMX)
            return q.x() >= p.x();
        if (dim == DIMY)
            return q.y() >= p.y();

        return false;
    }

    public boolean contains(Point2D p) {
        Node x = root;
        int level = 0;
        while (x != null) {
            if (x.p.compareTo(p) == 0) return true;
            int dim = level % DIMENTION;
            if (comparte(x.p, p, dim)) x = x.right;
            else x = x.left;
            ++level;
        }
        return false;
    }

    public void draw() {
        //draw(root);
    }
    
    private void draw(Node t) {
        if (t != null) {
            StdDraw.setPenRadius(0.03);
            StdDraw.setPenColor(StdDraw.RED);
            t.p.draw();
            draw(t.left);
            draw(t.right);
        }
    }

    public Iterable<Point2D> range(RectHV rect) {
        ArrayList<Point2D> list = new ArrayList<>();
        getRange(list, root, rect, 0);
        return list;
    }

    private void getRange (ArrayList<Point2D> list, Node t, RectHV rect, int level) {
        if (t != null) {
            if (rect.contains(t.p)) list.add(t.p);
            int dim = level % DIMENTION;
            if (comparte(t.p, rect, dim) == BOTH) {
                getRange(list, t.right, rect, level + 1);
                getRange(list, t.left, rect, level + 1);
            }
            if (comparte(t.p, rect, dim) == RIGHT) 
                getRange(list, t.right, rect, level + 1);
            if (comparte(t.p, rect, dim) == LEFT) 
                getRange(list, t.left, rect, level + 1);
        }
    }

    private int comparte (Point2D p, RectHV rect, int dim) {
        if (dim == DIMX) {
            if (rect.xmax() >= p.x() && rect.xmin() <= p.x())
                return BOTH;
            if (rect.xmax() >= p.x() && rect.xmin() >= p.x())
                return RIGHT;
            if (rect.xmax() < p.x() && rect.xmin() < p.x())
                return LEFT;
        }
        if (dim == DIMY) {
            if (rect.ymax() >= p.y() && rect.ymin() <= p.y())
                return BOTH;
            if (rect.ymax() >= p.y() && rect.ymin() >= p.y())
                return RIGHT;
            if (rect.ymax() < p.y() && rect.ymin() < p.y())
                return LEFT;
        }
        return 4;
    }

    private Point2D cand;
    private Double best;
    
    public Point2D nearest(Point2D p) {
        best = Double.MAX_VALUE;
        cand = null;
        nearest(root, p,0);
        if (cand == null) System.out.println("Cand Is NULL");
        return cand;
    }
    
    private void nearest(Node x, Point2D p, int debth)  {
        if (x != null) {
            Double newDistance;
            boolean modified = false;
            if (p.distanceSquaredTo(x.p) < best) {
                cand = x.p;
                newDistance = p.distanceSquaredTo(x.p);
                modified = true;
            }
            int cp = debth % DIMENTION;
            Node otherBranch = null;

            if (comparte(x.p, p, cp)) {
                otherBranch = x.left;
                nearest(x.right,p,debth+1);
            } else {
                otherBranch = x.right;
                nearest(x.left,p,debth+1);
            }
            
            // nearest(otherBranch,p,debth+1);
            
            if (otherBranch != null && otherBranch.p != null) {
                if (Math.abs(otherBranch.p.distanceSquaredTo(p)) < best) 
                    nearest(otherBranch,p,debth+1);
            }
            
        }
    }
    

    private static class Node {
        private Point2D p;      // the point
        private RectHV rect;    // the axis-aligned rectangle corresponding to this node
        private Node left;        // the left/bottom subtree
        private Node right;        // the right/top subtree

        public Node (Point2D np) {
            p = np;
            left = null;
            right = null;
        }
     }
    
    /*public static void main(String[] args) {
        KdTree pSet = new KdTree();
        
        Point2D p1 = new Point2D  (0.206107, 0.095492);
        Point2D p2 = new Point2D  (0.975528, 0.654508);
        Point2D p3 = new Point2D  (0.024472, 0.345492);
        Point2D p4 = new Point2D  (0.793893, 0.095492);
        Point2D p5 = new Point2D  (0.793893, 0.904508);
        Point2D p6 = new Point2D  (0.975528, 0.345492);
        Point2D p7 = new Point2D  (0.206107, 0.904508);
        Point2D p8 = new Point2D  (0.500000, 0.000000);
        Point2D p9 = new Point2D  (0.024472, 0.654508);
        Point2D p10 = new Point2D  (0.500000, 1.000000);

        pSet.insert(p1);
        pSet.insert(p2);
        pSet.insert(p3);
        pSet.insert(p4);
        pSet.insert(p5);
        pSet.insert(p6);
        pSet.insert(p7);
        pSet.insert(p8);
        pSet.insert(p9);
        pSet.insert(p10);
        
        pSet.draw();
    }*/

}
