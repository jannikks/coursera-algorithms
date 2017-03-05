import edu.princeton.cs.algs4.StdRandom;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class Board {

    private final int[] blocks;
    private final int size;
    private final int moves;
    private final int empty;

    private final int maxDigits;

    public Board(int[][] initialBlocks) {
        this(initialBlocks, 0);
    }

    private Board(int[] initialBlocks) {
        this(initialBlocks, 0);
    }

    private Board(int[] initialBlocks, int moves) {

        this.moves = moves;
        this.size = (int) Math.sqrt(initialBlocks.length);
        this.maxDigits = (int) (StrictMath.log10(initialBlocks.length) + 1.0);

        this.blocks = getCopy(initialBlocks);
        this.empty = blocks.length;
    }

    private Board(int[][] initialBlocks, int moves) {

        this.moves = moves;
        this.size = initialBlocks.length;
        this.blocks = new int[size * size];
        this.empty = blocks.length;

        maxDigits = (int) (StrictMath.log10(blocks.length) + 1.0);

        for (int i = 0; i < size; i++) {
            System.arraycopy(initialBlocks[i], 0, blocks, i * size, size);
        }

        for (int i = 0; i < blocks.length; i++) {
            if (blocks[i] == 0) {
                blocks[i] = blocks.length;
                break;
            }
        }

    }

    public int dimension() {
        return size;
    }

    public int hamming() {

        int wrongBlocks = 0;
        for (int i = 0; i < blocks.length; i++) {
            if ((blocks[i] != empty) && (blocks[i] != i + 1)) {
                wrongBlocks++;
            }
        }

        return wrongBlocks + moves;
    }

    public int manhattan() {

        int sumDistances = 0;
        for (int i = 0; i < blocks.length; i++) {
            if (blocks[i] != empty) {
                sumDistances += getDistance(i);
            }
        }
        return sumDistances + moves;
    }

    public boolean isGoal() {
        for (int i = 0; i < blocks.length; i++) {
            if (blocks[i] != i + 1) {
                return false;
            }
        }
        return true;
    }

    public Board twin() {

        int[] copiedBlocks = getCopy(blocks);

        int swapIndex1 = 0;
        int swapIndex2 = 0;

        while ((swapIndex1 == swapIndex2) || (copiedBlocks[swapIndex1] == empty || copiedBlocks[swapIndex2] == empty)) {
            swapIndex1 = StdRandom.uniform(empty);
            swapIndex2 = StdRandom.uniform(empty);
        }

        swapIndexValues(copiedBlocks, swapIndex1, swapIndex2);

        return new Board(copiedBlocks);
    }

    private void swapIndexValues(int[] copiedBlocks, int swapIndex1, int swapIndex2) {
        int tmpValue = copiedBlocks[swapIndex1];
        copiedBlocks[swapIndex1] = copiedBlocks[swapIndex2];
        copiedBlocks[swapIndex2] = tmpValue;
    }

    private int[] getCopy(int[] originalBlocks) {
        int[] copiedBlocks = new int[originalBlocks.length];
        System.arraycopy(originalBlocks, 0, copiedBlocks, 0, originalBlocks.length);
        return copiedBlocks;
    }

    public boolean equals(Object y) {

        if (y == null) {
            return false;
        }

        if (this == y) {
            return true;
        }

        if (getClass() != y.getClass()) {
            return false;
        }

        Board other = (Board) y;

        if (other.dimension() != dimension()) {
            return false;
        }

        for (int i = 0; i < blocks.length; i++) {
            if (blocks[i] != other.blocks[i]) {
                return false;
            }
        }
        return true;
    }

    public Iterable<Board> neighbors() {

        List<Board> boards = new ArrayList<>();

        int emptyIndex = 0;

        for (int i = 0; i < blocks.length; i++) {
            if (blocks[i] == empty) {
                emptyIndex = i;
                break;
            }
        }

        // treat above
        if (getRowOfIndex(emptyIndex) != 0) {
            int[] aboveBlocks = getCopy(blocks);
            swapIndexValues(aboveBlocks, emptyIndex, emptyIndex - size);
            boards.add(new Board(aboveBlocks));
        }
        // treat below
        if (getRowOfIndex(emptyIndex) != size - 1) {
            int[] belowBlocks = getCopy(blocks);
            swapIndexValues(belowBlocks, emptyIndex, emptyIndex + size);
            boards.add(new Board(belowBlocks));
        }
        // treat left
        if (getColumnOfIndex(emptyIndex) != 0) {
            int[] belowBlocks = getCopy(blocks);
            swapIndexValues(belowBlocks, emptyIndex, emptyIndex - 1);
            boards.add(new Board(belowBlocks));
        }
        // treat right
        if (getColumnOfIndex(emptyIndex) != size - 1) {
            int[] belowBlocks = getCopy(blocks);
            swapIndexValues(belowBlocks, emptyIndex, emptyIndex + 1);
            boards.add(new Board(belowBlocks));
        }

        return boards;
    }

    public String toString() {


        StringBuilder sb = new StringBuilder();

        sb.append(size);
        sb.append('\n');
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                int value = blocks[i * size + j] == empty ? 0 : blocks[i * size + j];
                sb.append(getPadding(value));
                sb.append(value);
            }
            sb.append('\n');
        }

        return sb.toString();
    }

    private String getPadding(int value) {

        int currentDigits = String.valueOf(value).length();

        StringBuilder sb = new StringBuilder();
        sb.append(' ');

        for (int i = 0; i < maxDigits - currentDigits; i++) {
            sb.append(' ');
        }

        return sb.toString();
    }

    private int getDistance(int i) {
        int value = blocks[i];

        int columnDistance = Math.abs(getColumnOfIndex(i) - getColumnOfIndex(value - 1));
        int rowDistance = Math.abs(getRowOfIndex(i) - getRowOfIndex(value - 1));

        return columnDistance + rowDistance;
    }

    private int getColumnOfIndex(int i) {
        return i % size;
    }

    private int getRowOfIndex(int i) {
        return i / size;
    }

    public static void main(String[] args) {

        int[][] blocks = new int[3][3];

        blocks[0][0] = 5;
        blocks[0][1] = 3;
        blocks[0][2] = 4;
        blocks[1][0] = 2;
        blocks[1][1] = 8;
        blocks[1][2] = 0;
        blocks[2][0] = 7;
        blocks[2][1] = 1;
        blocks[2][2] = 6;

        Board board = new Board(blocks);
        System.out.println(board.toString());

        System.out.println(board.neighbors());
    }
}
