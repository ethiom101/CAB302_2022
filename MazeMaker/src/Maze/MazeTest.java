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

   // @Test
   // public void testSolution() throws Exception {
   //     MazeGenerator maze = new MazeGenerator(10,10);
   //     Stack<Cell> solution = maze.solveMaze();
   //     assertEquals(true,solution.getClass().equals(maze.solveMaze().getClass()));
   // }

    @Test
    public void testSolutionDirectionValue() throws Exception {
        MazeGenerator maze = new MazeGenerator(10,10);
        assertEquals(0,maze.solutionDirectionValue(1)[0]);
        assertEquals(-1,maze.solutionDirectionValue(1)[1]);
    }

    @Test
    public void testCellDistribution() throws Exception {
        MazeGenerator maze = new MazeGenerator(10,10);
        String test = maze.cellDistribution(10,10);
        assertEquals(test,maze.cellDistribution(10,10));
    }

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
        maze.setEndX(10);
        maze.setEndY(10);
    }
}
