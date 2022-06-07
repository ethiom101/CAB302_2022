package Maze;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;

import static GUI.EditMaze.*;

public class Grid extends JPanel {
    private static int rows;
    private static int columns;
    private int cellSize;
    public static Cell[][] grid;
    private Cell start;
    private Cell end;
    private Cell logo;
    private MazeGenerator maze;
    private MazeSolver solver;
    public boolean toggle = true;

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
     *
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


    public void drawMaze(int rows, int columns) throws Exception {
        maze = new MazeGenerator(columns, rows);
        Cell[][] cells = maze.getGrid();
        System.out.println(Grid.rows);
        System.out.println(Grid.columns);
        for (int row = 0; row < Grid.rows; row++) {
            for (int col = 0; col < Grid.columns; col++) {
                // grid[col][row].setText(col+" "+row);

                if (cells[col][row].getColumn() == Grid.rows - 1) {
                    grid[row][col].drawWall[2] = 2;
                    grid[row][col].isWall[2] = true;
                    grid[row][col].setBorder(new MatteBorder(grid[row][col].drawWall[0], grid[row][col].drawWall[1], grid[row][col].drawWall[2], grid[row][col].drawWall[3], Color.black));

                }
                if (cells[col][row].getWall(1)) {
                    grid[row][col].drawTopWall();
                }

                if (cells[col][row].getRow() == Grid.columns - 1) {
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


    //Getters and Setters
    public String getCellDist() {
        return (solver.cellDistribution(rows, columns));
    }

    public String getDeadEnds() {
        return maze.deadEnds(rows, columns);
    }

    public static int getColumns() {
        return columns;
    }

    public static int getRows() {
        return rows;
    }

    public int getCellSize() {
        return this.cellSize;
    }

    public void setCellSize(int cellSize) {
        this.cellSize = cellSize;
    }

    public Cell getStart() {
        return this.start;
    }

    public void setStart(Cell start) {
        this.start = start;
        toggleSolution.setEnabled((start != null) && (this.end != null));
        if (this.solver != null) {
            solver.clearSolution();
            toggle = true;
            solver = null;
        }
    }

    public Cell getEnd() {
        return this.end;
    }

    public void setEnd(Cell end) {
        this.end = end;
        toggleSolution.setEnabled((end != null) && (this.start != null));
        if (this.solver != null) {
            solver.clearSolution();
            toggle = true;
            solver = null;
        }
    }

    public Cell getLogo() {
        return this.logo;
    }

    public void setLogo(Cell logo) {
        this.logo = logo;
    }
}



