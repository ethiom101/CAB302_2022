package Util;

import static org.junit.jupiter.api.Assertions.*;

import Maze.MazeGenerator;
import Maze.MazeSolver;
import org.junit.Test;
import org.junit.jupiter.api.*;

import java.util.Stack;

public class MazeTest {
    /*

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

//    @Test
//    public void testCellDistribution() throws Exception {
//        String test = solver.cellDistribution(10,10);
//        //assertEquals(test,solver.cellDistribution(10,10));
//    }

    @Test
    public void testDeadEnds() throws Exception {
        MazeGenerator maze = new MazeGenerator(10,10);
        String test = maze.deadEnds(10,10);
        assertTrue(test instanceof String);
    }

    @Test
    public void testMazeGeneratorSetters() throws Exception{
        MazeGenerator maze = new MazeGenerator(10,10);
        maze.setStartX(0);
        maze.setStartY(0);
    }

    @Test
    public void testdirectionValue() throws Exception{
        assertThrows(Exception.class, () -> {
            maze.directionValue("A");
        });
    }
}
