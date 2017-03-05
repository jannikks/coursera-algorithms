import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 *
 */
public class BoardTest {
    @Test
    public void boardHamming10() throws Exception {
        Board board = new Board(new int[][]{{8, 1, 3}, {4, 0, 2}, {7, 6, 5}});

        assertThat(board.manhattan()).isEqualTo(10);
    }

    @Test
    public void hammingShouldMatchSpec() {
        Board board = new Board(new int[][]{
                {8, 1, 3},
                {4, 0, 2},
                {7, 6, 5}
        });
        assertThat(board.hamming()).isEqualTo(5);
    }

    @Test
    public void manhattanShouldMatchSpec() {
        Board board = new Board(new int[][]{
                {8, 1, 3},
                {4, 0, 2},
                {7, 6, 5}
        });
        assertThat(board.manhattan()).isEqualTo(10);

        Iterable<Board> neighbors = board.neighbors();


        for (Board b : neighbors){

        }
        Board boardNeighbour = neighbors.iterator().next();

        System.out.println(boardNeighbour);

        System.out.println(boardNeighbour.hamming());
    }

}