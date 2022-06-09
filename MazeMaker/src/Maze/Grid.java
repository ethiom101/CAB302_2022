package Maze;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;

import static GUI.HomePage.mazeEditor;

/**
 * Class for the grid of the maze
 */
public class Grid extends JPanel {
    private final int rows;
    private final int columns;
    private int cellSize;
    public static Cell[][] grid;
    private Cell start;
    private Cell end;
    private Cell logo;
    private MazeGenerator maze;
    private MazeSolver solver;
    public boolean toggle = true;

    /**
     * Grid class instance
     * @param width Width of the grid
     * @param height Height of the grid
     * @param cellSize Cell size of the grid
     */
    public Grid(int width, int height, int cellSize) {
        this.setBackground(Color.white);
        this.setBorder(BorderFactory.createLineBorder(Color.lightGray));
        this.setLayout(new GridLayout(height, width));
        rows = height;
        columns = width;
        this.cellSize = cellSize;
        init();
    }

    /**
     * Initialise the grid of cells to display the maze
     */
    private void init() {
        grid = new Cell[rows][columns];
        for (int col = 0; col < rows; col++) {
            for (int row = 0; row < columns; row++) {
                grid[col][row] = new Cell(col, row);
                this.add(grid[col][row]);
            }
        }
    }


    /**
     * Draws the maze onto the grid
     * @param rows Number of rows the maze has
     * @param columns Number of columns the maze has
     * @throws Exception Exception thrown when the rows and columns are greater than 100 or less than 1
     */
    public void drawMaze(int rows, int columns) throws Exception {
        maze = new MazeGenerator(columns, rows);
        Cell[][] cells = maze.getGrid();
        for (int row = 0; row < this.rows; row++) {
            for (int col = 0; col < this.columns; col++) {
                // grid[col][row].setText(col+" "+row);

                if (cells[col][row].getColumn() == this.rows - 1) {
                    grid[row][col].drawWall[2] = 2;
                    grid[row][col].isWall[2] = true;
                    grid[row][col].setBorder(new MatteBorder(grid[row][col].drawWall[0], grid[row][col].drawWall[1], grid[row][col].drawWall[2], grid[row][col].drawWall[3], Color.black));

                }
                if (cells[col][row].getWall(1)) {
                    grid[row][col].drawTopWall();
                }

                if (cells[col][row].getRow() == this.columns - 1) {
                    grid[row][col].drawWall[3] = 2;
                    grid[row][col].isWall[3] = true;
                    grid[row][col].setBorder(new MatteBorder(grid[row][col].drawWall[0], grid[row][col].drawWall[1], grid[row][col].drawWall[2], grid[row][col].drawWall[3], Color.black));

                }
                if (cells[col][row].getWall(3)) {
                    grid[row][col].drawLeftWall();
                }


                if (cells[col][row].getStart()) {
                    grid[row][col].drawStart(Cell.start);
                    grid[row][col].drawTopWall();
                }
                if (cells[col][row].getEnd()) {
                    grid[row][col].drawEnd(Cell.end);
                    grid[row][col].drawWall[2] = 0;
                    grid[row][col].isWall[2] = false;
                    grid[row][col].setBorder(new MatteBorder(grid[row][col].drawWall[0], grid[row][col].drawWall[1], grid[row][col].drawWall[2], grid[row][col].drawWall[3], Color.black));

                }
                if ((cells[col][row].getLogo()) && (Cell.logo != null)) {
                    grid[row][col].drawLogo(Cell.logo);
                    grid[row][col].drawRightWall();
                    grid[row][col].drawDownWall();
                }
            }
        }
    }


    /**
     * Solves the maze and draws the solution onto the maze
     */
    public void Solve() {
        solver = new MazeSolver(getStart(), getEnd());
        if (!toggle) {
            solver.clearSolution();
            toggle = true;

        } else {
            solver.drawSolution();
            toggle = false;
        }
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < columns; col++) {
                grid[row][col].setVisited(false);
            }
        }
    }


    /**
     * Get the percentage of cells needed to solve the maze
     * @return String of the percentage of cells needed to solve the maze
     */
    public String getCellDist() {
        return (solver.cellDistribution(rows, columns));
    }

    /**
     * Get the percentage of dead ends in the maze
     * @return String of the percentage of dead ends in the maze
     */
    public String getDeadEnds() {
        return maze.deadEnds(rows, columns);
    }

    /**
     * Return the cell size of the cells in this grid
     * @return Integer value of the cell size
     */
    public int getCellSize() {
        return this.cellSize;
    }

    /**
     * Set the cell size of this grid
     * @param cellSize Integer for the cell size we want to set
     */
    public void setCellSize(int cellSize) {
        this.cellSize = cellSize;
    }

    /**
     * Returns the start cell
     * @return Cell that is the start cell of this grid
     */
    public Cell getStart() {
        return this.start;
    }

    /**
     * Sets the start cell of this grid
     * @param start Cell we want to set to start
     */
    public void setStart(Cell start) {
        this.start = start;
        mazeEditor.toggleSolution.setEnabled((start != null) && (this.end != null));
        if (this.solver != null) {
            solver.clearSolution();
            toggle = true;
            solver = null;
        }
    }

    /**
     * Returns the end cell
     * @return Cell that is the end cell of this grid
     */
    public Cell getEnd() {
        return this.end;
    }

    /**
     * Sets the end cell of this grid
     * @param end Cell we want to set to end
     */
    public void setEnd(Cell end) {
        this.end = end;
        mazeEditor.toggleSolution.setEnabled((end != null) && (this.start != null));
        if (this.solver != null) {
            solver.clearSolution();
            toggle = true;
            solver = null;
        }
    }

    /**
     * Returns the logo cell
     * @return Cell that is the logo cell of this grid
     */
    public Cell getLogo() {
        return this.logo;
    }

    /**
     * Sets the logo cell of this grid
     * @param logo Cell we want to set to a logo cell
     */
    public void setLogo(Cell logo) {
        this.logo = logo;
    }
}



