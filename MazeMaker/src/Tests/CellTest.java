package Tests;

import Maze.Cell;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CellTest {
    /*
    Variables to set up
     */
    Cell cell;

    @BeforeEach
    @Test
    public void setUpCell() throws Exception {
        Cell cell = new Cell(10, 10);
    }

    @Test
    public void testIsStart() {
        Cell cell = new Cell(10, 10);
        cell.isStart();
    }

    @Test
    public void testIsEnd() {
        Cell cell = new Cell(10, 10);
        cell.isEnd();
    }

    @Test
    public void testIsLogo() {
        Cell cell = new Cell(10, 10);
        cell.isLogo();
    }

    @Test
    public void testGetRow() {
        Cell cell = new Cell(10, 10);
        assertEquals(10,cell.getRow());
    }

    @Test
    public void testGetColumn() {
        Cell cell = new Cell(10, 10);
        assertEquals(10,cell.getColumn());
    }

    @Test
    public void testGetVisit() {
        Cell cell = new Cell(10, 10);
        cell.setVisited(true);
        assertTrue(cell.getVisited());
    }

    @Test
    public void testGetParent() {
        Cell cell = new Cell(10, 10);
        Cell parent = new Cell(9, 10);
        cell.setParents(parent);
        assertEquals(cell.getParents(),parent);
    }

    @Test
    public void testGetLogo() {
        Cell cell = new Cell(10, 10);
        cell.setLogo();
        assertTrue(cell.getLogo());
    }

    @Test
    public void testGetStart() {
        Cell cell = new Cell(10, 10);
        cell.setStart();
        assertTrue(cell.getStart());
    }

    @Test
    public void testGetEnd() {
        Cell cell = new Cell(10, 10);
        cell.setEnd();
        assertTrue(cell.getEnd());
    }

    @Test
    public void testSetWall() {
        Cell cell = new Cell(10, 10);
        cell.setWall(1);
        assertFalse(cell.getWall(1));
    }
}
