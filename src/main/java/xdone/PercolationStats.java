package xdone;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private final int trials;
    private final double[] outcomes;

    // perform trials independent experiments on an n-by-n grid
    public PercolationStats(int n, int trials) {

        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException();
        }
        this.trials = trials;
        this.outcomes = new double[trials];

        for (int trial = 0; trial < trials; trial ++){
            performTrial(n, trial);
        }
    }

    private void performTrial(int n, int trial) {
        Percolation percolation = new Percolation(n);

        while (!percolation.percolates()) {

            int row = StdRandom.uniform(1, n + 1);
            int column = StdRandom.uniform(1, n + 1);

            if (!percolation.isOpen(row, column)) {
                percolation.open(row, column);
            }
        }
        outcomes[trial] = (double) percolation.numberOfOpenSites() / (double) (n * n);
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(outcomes);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(outcomes);
    }

    // low  endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - ((1.96 * stddev()) / Math.sqrt(trials));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + ((1.96 * stddev()) / Math.sqrt(trials));
    }

    // test client (described below)
    public static void main(String[] args) {

        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);
        PercolationStats ps = new PercolationStats(n, trials);

        StdOut.println("mean                    = " + ps.mean());
        StdOut.println("stddev                  = " + ps.stddev());
        StdOut.println("95% confidence interval = [" + ps.confidenceLo() + ", " + ps.confidenceHi() + "]");
    }
}