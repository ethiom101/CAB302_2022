package Maze;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.util.Stack;

public class Grid extends JPanel {
    private static int rows;
    private static int columns;
    private int cellSize;
    public static Cell[][] grid;
    private Cell start;
    private Cell end;
    private Cell current;
    private Cell logo;
    private MazeGenerator maze;
    Stack<Cell> solution = new Stack<Cell>();
    private boolean toggle = false;

    public void toggle() {
        toggle = !toggle;
    }

    public Grid(int height, int width, int cellSize) {
        this.setBackground(Color.white);
        this.setBorder(BorderFactory.createLineBorder(Color.lightGray));
        this.setLayout(new GridLayout(width, height));
        rows = width;
        columns = height;
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


    public void drawMaze(int rows, int columns) {
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

    /**
     * Draws the solution on the grid
     */
    public void drawSolution() {

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < columns; col++) {
                if (grid[row][col].isEnd()) {
                    maze.setEndX(col);
                    maze.setEndY(row);
                } else if (grid[row][col].isStart()) {
                    maze.setStartX(col);
                    maze.setStartY(row);
                }
            }
        }
        Cell[][] path = maze.getGrid();
        Stack<Cell> solution = maze.solveMaze();
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < columns; col++) {
                if (solution.contains(path[col][row])) {
                    if (toggle) {
                        grid[row][col].setOpaque(true);
                        grid[row][col].setBackground(Color.white);
                    } else {
                        grid[row][col].setOpaque(true);
                        grid[row][col].setBackground(Color.pink);
                    }
                }
            }
        }
    }

    //Getters and Setters
    public String getCellDist() {
        return (maze.cellDistribution());
    }

    public String getDeadEnds() {
        return maze.deadEnds();
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
    }

    public Cell getEnd() {
        return this.end;
    }

    public void setEnd(Cell end) {
        this.end = end;
    }

    public Cell getLogo() {
        return this.logo;
    }

    public void setLogo(Cell logo) {
        this.logo = logo;
    }
}



