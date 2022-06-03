package Maze;

import javax.swing.*;
import java.awt.*;

public class Grid extends JPanel {
    private int rows;
    private int columns;
    private int cellSize;

    public static Cell[][] grid;
    private Cell start;
    private Cell end;
    private Cell logo;


    public Grid(int columns, int rows, int cellSize) {
        this.setBackground(Color.white);
        this.setBorder(BorderFactory.createLineBorder(Color.lightGray));
        this.setLayout(new GridLayout(rows, columns));
        this.rows = rows;
        this.columns = columns;
        this.cellSize = cellSize;
        init();
    }

    private void init() {
        grid = new Cell[this.rows][this.columns];
        for (int col = 0; col < this.rows; col++) {
            for (int row = 0; row < this.columns; row++) {
                grid[col][row] = new Cell(col, row, this.cellSize);
                this.add(grid[col][row]);
            }
        }
    }

    public int getColumns() {
        return this.columns;
    }

    public int getRows() {
        return this.rows;
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



