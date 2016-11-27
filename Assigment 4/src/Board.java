
public class Board {
    
    private int [][] tiles;
    
    public Board(int [][] blocks) {
        tiles = new int [blocks.length][blocks.length];
        for (int i = 0; i < blocks.length; ++i)
            tiles[i] = blocks[i].clone();
        
        
    }
    
    public int dimension() {
        return tiles.length;
    }
    
    public int hamming() { 
        int shouldNumber = 1;
        int numCorrect = 0;
        for (int i = 0; i < tiles.length; ++i) {
            for (int j = 0; j < tiles.length; ++j) {
                if (tiles[i][j] == shouldNumber)
                    ++numCorrect;
                ++shouldNumber;
            }
        }
        return numCorrect;
    }
    
    public int manhattan() {
        int manhCont = 0;
        for (int i = 0; i < tiles.length; ++i) {
            for (int j = 0; j < tiles.length; ++j) {
                if (tiles[i][j] != 0) {
                    int correctI = (tiles[i][j] - 1) / tiles.length;
                    int correctJ = (tiles[i][j] - 1) % tiles.length;
                    manhCont += distPoints(j, j, correctI, correctJ);
                }
            }
        }    
        return manhCont;
    }
    
    private int  distPoints (int p, int q, int r, int s) {
        int deltaX = Math.abs(p - r);
        int deltaY = Math.abs(r -s);
        return deltaX + deltaY;
    }
    
    public boolean isGoal() {
        int shoudNum = 1;
        for (int i = 0; i < tiles.length; ++i) {
            for (int j = 0; j < tiles.length; ++j) {
                if (tiles[i][j] != 0) {
                    if (tiles[i][j] != shoudNum)
                        return false;
                }
                ++shoudNum;
            }
        }
        return true;
    }
    
    public Board twin() {
        return null; 
    }
    
    public boolean equals(Object y) {
        return false;
    }
    
    public Iterable<Board> neighbors() {
        return null;
    }
    
    public String toString() {
        int n = tiles.length;
        StringBuilder s = new StringBuilder();
        s.append(n + "\n");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                s.append(String.format("%2d ", tiles[i][j]));
            }
            s.append("\n");
        }
        return s.toString();
    }

}
