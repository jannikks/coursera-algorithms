import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 *
 */
public class Solver {

    private boolean originalSolvable = false;

    private Node solutionNode;
    private final List<Board> solutionList = new ArrayList<>();

    public Solver(Board initial) {
        if (initial == null) {
            throw new NullPointerException();
        }

        MinPQ<Node> minPQ = new MinPQ<>(new PriorityOrder());
        MinPQ<Node> alternatePQ = new MinPQ<>(new PriorityOrder());

        minPQ.insert(new Node(initial, null, 1));
        alternatePQ.insert(new Node(initial.twin(), null, 1));

        boolean alternateSolvable = false;
        while (!originalSolvable && !alternateSolvable) {
            originalSolvable = iteration(minPQ);
            alternateSolvable = iteration(alternatePQ);
        }
        if (originalSolvable) {
            assembleSolutionList();
        }
    }

    private boolean iteration(MinPQ<Node> q) {

        Node currentNode = q.delMin();

        if (currentNode.board.isGoal()) {
            solutionNode = currentNode;
            return true;
        }

        for (Board b : currentNode.board.neighbors()) {

            if (currentNode.previous == null || !b.equals(currentNode.previous.board)) {
                q.insert(new Node(b, currentNode, currentNode.moves + 1));
            }
        }
        return false;
    }

    public boolean isSolvable() {
        return originalSolvable;
    }

    public int moves() {
        return solutionList.size() - 1;
    }

    private void assembleSolutionList() {
        Stack<Node> solutionStack = new Stack<>();

        Node currentNode = solutionNode;
        while (currentNode != null) {
            solutionStack.push(currentNode);
            currentNode = currentNode.previous;
        }

        while (!solutionStack.isEmpty()) {
            solutionList.add(solutionStack.pop().board);
        }
    }

    public Iterable<Board> solution() {
        if (solutionList.isEmpty()){
            return null;
        }
        return solutionList;
    }

    private static class PriorityOrder implements Comparator<Node> {

        @Override
        public int compare(Node s1, Node s2) {
            int priority1 = s1.board.manhattan() + s1.moves;
            int priority2 = s2.board.manhattan() + s2.moves;

            return Integer.compare(priority1, priority2);
        }
    }

    private static class Node {
        private Board board;
        private Node previous;
        private int moves;

        Node(Board board, Node previous, int moves) {
            this.board = board;
            this.previous = previous;
            this.moves = moves;
        }
    }

    public static void main(String[] args) {

        int[][] blocks = new int[3][3];

        blocks[0][0] = 0;
        blocks[0][1] = 3;
        blocks[0][2] = 1;
        blocks[1][0] = 4;
        blocks[1][1] = 2;
        blocks[1][2] = 5;
        blocks[2][0] = 7;
        blocks[2][1] = 8;
        blocks[2][2] = 6;

        Board initial = new Board(blocks);

        Solver solver = new Solver(initial);
        System.out.println(solver.isSolvable());
        System.out.println(solver.solution());

    }
}
