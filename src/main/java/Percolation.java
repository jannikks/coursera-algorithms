import edu.princeton.cs.algs4.WeightedQuickUnionUF;

import java.util.Arrays;

public class Percolation {

    private final int size;

    private final WeightedQuickUnionUF quickUnionUF;

    private final SiteStatus siteStatus;

    // create n-by-n grid, with all sites blocked
    public Percolation(int n) {

        if (n <= 0) {
            throw new IllegalArgumentException();
        }
        this.size = n;

        quickUnionUF = new WeightedQuickUnionUF(size * size + 2);

        // status of sites
        siteStatus = new SiteStatus(size);
    }

    // open site (row, col) if it is not open already
    public void open(int row, int col) {
        checkRange(row, col);

        // middle
        siteStatus.openUp(row, col);

        int currentIndex = getUnionFindIndex(row, col);

        treatAbove(row, col, currentIndex);
        treatBelow(row, col, currentIndex);
        treatRight(row, col, currentIndex);
        treatLeft(row, col, currentIndex);


    }

    private void treatLeft(int row, int col, int currentIndex) {
        // left, if not at edge
        if ((col > 1) && siteStatus.isOpen(row, col - 1)) {
            quickUnionUF.union(currentIndex, getUnionFindIndex(row, col - 1));
        }
    }

    private void treatRight(int row, int col, int currentIndex) {
        // right, if not at edge
        if ((col < size) && siteStatus.isOpen(row, col + 1)) {
            quickUnionUF.union(currentIndex, getUnionFindIndex(row, col + 1));
        }
    }

    private void treatBelow(int row, int col, int currentIndex) {
        if (row == size) {
            // treat bottom row
            quickUnionUF.union(currentIndex, size * size + 1);
        } else if ((row < size) && siteStatus.isOpen(row + 1, col)) {
            // bottom, if not last row
            quickUnionUF.union(currentIndex, getUnionFindIndex(row + 1, col));
        }
    }

    private void treatAbove(int row, int col, int currentIndex) {
        if (row == 1) {
            // treat top row
            quickUnionUF.union(currentIndex, 0);
        } else if ((row > 1) && siteStatus.isOpen(row - 1, col)) {
            // connect above, if not row 1
            quickUnionUF.union(currentIndex, getUnionFindIndex(row - 1, col));
        }
    }

    // is site (row, col) open?
    public boolean isOpen(int row, int col) {
        checkRange(row, col);
        return siteStatus.isOpen(row, col);
    }

    // is site (row, col) full?
    public boolean isFull(int row, int col) {
        checkRange(row, col);
        return isOpen(row, col) && quickUnionUF.connected(0, getUnionFindIndex(row, col));
    }

    // number of open sites
    public int numberOfOpenSites() {
        return siteStatus.numberOfOpenSites();
    }

    // does the system percolate?
    public boolean percolates() {
        return quickUnionUF.connected(0, size * size + 1);
    }

    private void checkRange(int row, int col) {
        if (row > size || col > size || row <= 0 || col <= 0) {
            throw new IndexOutOfBoundsException();
        }
    }

    private int getUnionFindIndex(int row, int column) {
        return (row - 1) * size + column;
    }

    private static class SiteStatus {

        private final Boolean[] siteStatusField;

        private final int size;

        private SiteStatus(int size) {
            this.size = size;
            this.siteStatusField = new Boolean[size * size];
            Arrays.fill(siteStatusField, Boolean.FALSE);
        }

        private boolean isOpen(int row, int column) {
            return siteStatusField[(row - 1) * size + column - 1];
        }

        private void openUp(int row, int column) {
            siteStatusField[(row - 1) * size + column - 1] = Boolean.TRUE;
        }

        private int numberOfOpenSites() {
            return (int) Arrays.stream(siteStatusField)
                    .filter(status -> status.equals(Boolean.TRUE))
                    .count();
        }
    }

    // test client (optional)
    public static void main(String[] args) {


    }
}
