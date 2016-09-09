import edu.princeton.cs.algs4.StdRandom;

public class PercolationStats {
    
    private double[] threshold;

    public PercolationStats(int n, int t) {
        if (n <= 0 || t <= 0) 
            throw new IllegalArgumentException("No negative Values");
        threshold = new double[t];
        for (int i = 0; i < t; ++i) {
            Percolation perc = new Percolation(n);
            double openTiles = 0.0;
            while (!perc.percolates()) {
                int randI = StdRandom.uniform(n) + 1;
                int randJ = StdRandom.uniform(n) + 1;
                if (!perc.isOpen(randI, randJ)) {
                    ++openTiles;
                    perc.open(randI, randJ);
                }
                   
            }
            threshold[i] = openTiles / (double) (n*n);
        }        
    }
    
    public double mean() {
        double sumValues = 0.0;
        for (int i = 0; i < threshold.length; ++i) {
            sumValues += threshold[i];
        }
        return (sumValues / (double) threshold.length);
    }
    
    public double stddev() {
        double sumValues = 0.0;
        double meanVal = mean();
        for (int i = 0; i < threshold.length; ++i) {
            sumValues += ((threshold[i] - meanVal) * (threshold[i] - meanVal));
        }
        return Math.sqrt((sumValues / (double) threshold.length));
    }
    
    public double confidenceLo() {
        final double Z = 1.96;
        double lowConfInterval = mean() - ((Z*stddev())/Math.sqrt(threshold.length));
        return lowConfInterval;
    }
    
    public double confidenceHi() {
        final double Z = 1.96;
        final double SQRT_OF_N = Math.sqrt(threshold.length);
        double highConfInterval = mean() + ((Z*stddev())/SQRT_OF_N);
        return highConfInterval;
    }
    
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int t = Integer.parseInt(args[1]);
        PercolationStats stats = new PercolationStats(n, t);
        System.out.printf("%.17f\n", stats.mean());
        System.out.printf("%.17f\n", stats.stddev());
        System.out.printf("%.17f\n", stats.confidenceLo());
        System.out.printf("%.17f\n", stats.confidenceHi());
    }


}
