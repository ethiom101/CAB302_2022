package Maze;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.Test;
import org.junit.jupiter.api.*;

import java.util.Stack;

public class MazeTest {
    /*

     */
    MazeGenerator maze;

    @BeforeEach @Test
    public void setUpMazeGenerator() throws Exception {
        MazeGenerator maze = new MazeGenerator(10,10);
    }

    @Test
    public void testTooLargeMaze() throws Exception {
        assertThrows(Exception.class, () -> {
            new MazeGenerator(101,101);
        });    }

    @Test
    public void testTooSmallMaze() throws Exception {
        assertThrows(Exception.class, () -> {
            new MazeGenerator(-1,-1);
        });    }

    @Test
    public void cellDistTooSmall() throws Exception{
        assertThrows(Exception.class, () -> {
            maze.cellDistribution(-1,-1);
        });
    }

    @Test
    public void cellDistTooBig() throws Exception{
        assertThrows(Exception.class, () -> {
            maze.cellDistribution(101,101);
        });
    }

    @Test
    public void test() throws Exception {
        MazeGenerator maze = new MazeGenerator(10,10);
        Stack<Cell> solution = maze.solveMaze();
        assertEquals(true,solution.getClass().equals(maze.solveMaze().getClass()));
    }
}
