import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private WeightedQuickUnionUF percUF;
    private boolean[][] percolationGrid;

    public Percolation(int n) {
        percUF = new WeightedQuickUnionUF((n * n) + 2);
        percolationGrid = new boolean[n][n];
        for (int i = 0; i < percolationGrid.length; ++i) {
            for (int j = 0; j < percolationGrid[i].length; ++j)
                percolationGrid[i][j] = false;
        }
        for (int i = 0; i < n; ++i) {
            int virtualTop = n * n;
            percUF.union(virtualTop, i);
        }
    }

    public void open(int i, int j) {
        --i;
        --j;
        int n = percolationGrid.length;
        if (i < 0 || i >= n || j < 0 || j >= n)
            throw new IndexOutOfBoundsException("Index Out Of Bounds" + i + " " + j);
        percolationGrid[i][j] = true;
        int tileValue = (n * i) + j;
        if (j + 1 < n && percolationGrid[i][j + 1]) {
            percUF.union(tileValue, tileValue + 1);
        }
        if (j - 1 >= 0 && percolationGrid[i][j - 1]) {
            percUF.union(tileValue, tileValue - 1);
        }
        if (i - 1 >= 0 && percolationGrid[i - 1][j]) {
            percUF.union(tileValue, tileValue - n);
        }
        if (i + 1 < n && percolationGrid[i + 1][j]) {
            percUF.union(tileValue, tileValue + n);
        }
        if (tileValue >= (n * (n - 1))) {
        	int virtualBottom = (n * n) + 1;
            percUF.union(virtualBottom, tileValue);
        }
    }

    public boolean isOpen(int i, int j) {
        --i;
        --j;
        int n = percolationGrid.length;
        if (i < 0 || i >= n || j < 0 || j >= n)
            return false;
        
            //throw new IndexOutOfBoundsException("Index Out Of Bounds");
        return percolationGrid[i][j];
    }

    public boolean isFull(int i, int j) {
        --i;
        --j;
        int n = percolationGrid.length;
        int tileValue = (n * i) + j;
        boolean conectedToVirtualTop = false;
        if (i < 0 || i >= n || j < 0 || j >= n)
            return false;
        if (percolationGrid[i][j]) {
            if (tileValue < n)
                return true;
            if (j + 1 < n && percolationGrid[i][j + 1] && !conectedToVirtualTop) {
                conectedToVirtualTop = percUF.connected((n * n), tileValue + 1);
            }
            if (j - 1 >= 0 && percolationGrid[i][j - 1] && !conectedToVirtualTop) {
                conectedToVirtualTop = percUF.connected((n * n), tileValue - 1);
            }
            if (i - 1 >= 0 && percolationGrid[i - 1][j] && !conectedToVirtualTop) {
                conectedToVirtualTop = percUF.connected((n * n), tileValue - n);
            }
            if (i + 1 < n && percolationGrid[i + 1][j] && !conectedToVirtualTop) {
                conectedToVirtualTop = percUF.connected((n * n), tileValue + n);
            }
            /*if (i == n - 1) {
            	if (!isFull(i + 1, j + 2)) {
                    System.out.println(i + " " + (j));
                    conectedToVirtualTop = false;
                }
            }*/
        }
        return conectedToVirtualTop;
    }

    public boolean percolates() {
        int n = percolationGrid.length;
        boolean topOpen = false;
        for (int i = 0; i < percolationGrid.length; ++i)
            if (percolationGrid[0][i])
                topOpen = true;
        if (!topOpen)
            return false;
        return percUF.connected(n * n, (n * n) + 1);
    }
}