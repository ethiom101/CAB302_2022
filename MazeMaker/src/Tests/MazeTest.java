package Tests;

import static org.junit.jupiter.api.Assertions.*;

import Maze.MazeGenerator;
import Maze.MazeSolver;
import org.junit.jupiter.api.*;

public class MazeTest {
    /*
    Variables to set up
     */
    MazeGenerator maze;
    MazeSolver solver;

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
            solver.cellDistribution(-1,-1);
        });
    }

    @Test
    public void cellDistTooBig() throws Exception{
        assertThrows(Exception.class, () -> {
            solver.cellDistribution(101,101);
        });
    }

    @Test
    public void testDirectionValue() throws Exception{
        assertThrows(Exception.class, () -> {
            maze.directionValue("A");
        });
    }
}
