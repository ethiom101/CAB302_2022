package Tests;

import static org.junit.jupiter.api.Assertions.*;

import Maze.Cell;
import Maze.Grid;
//import org.junit.Test;
import org.junit.jupiter.api.*;

public class GridTest {
    /*
    Variables to set up
     */
    Grid grid;

    @BeforeEach
    @Test
    public void setUpGrid() throws Exception {
        Grid grid = new Grid(10,10,10);
    }

    @Test
    public void testDrawMaze() {
        Grid grid = new Grid(10,10,10);
        grid.Solve();
    }

    @Test
    public void testGetCellSize() {
        Grid grid = new Grid(10,10,10);
        assertEquals(10,grid.getCellSize());
    }

    @Test
    public void testSetCellSize() {
        Grid grid = new Grid(10,10,10);
        grid.setCellSize(11);
        assertEquals(11,grid.getCellSize());
    }

    @Test
    public void testGetStart() {
        Grid grid = new Grid(10,10,10);
        grid.getStart();
    }

    @Test
    public void testGetEnd() {
        Grid grid = new Grid(10,10,10);
        grid.getEnd();
    }

    @Test
    public void testGetLogo() {
        Grid grid = new Grid(10,10,10);
        grid.getLogo();
    }
}
