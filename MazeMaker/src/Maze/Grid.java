package Maze;

import javax.swing.*;
import java.awt.*;

public class Grid extends JPanel {
    private int rows;
    private int columns;
    private int cellSize;


    public static CellNew[][] grid;
    private Cell start, end;

    public Grid(int rows, int columns, int cellSize) {
        this.setBackground(Color.white);
        this.setBorder(BorderFactory.createLineBorder(Color.lightGray));
        this.setLayout(new GridLayout(rows, columns));
        this.rows = rows;
        this.columns = columns;
        this.cellSize = cellSize;
        init();
    }

    private void init() {
        grid = new CellNew[this.rows][this.columns];
        for (int row = 0; row < this.rows; row++) {
            for (int col = 0; col < this.columns; col++) {
                grid[row][col] = new CellNew(row, col, this.cellSize);
                this.add(grid[row][col]);
            }
        }
    }

    // public void resetMaze(){
    //     for (int row=0;row<w;row++) {
    //         for (int col =0;col<w;col++) {
    //             grid[row][col].resetCell();
    //         }
    //     }
    // }

    public void drawMaze() {
        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.columns; j++) {

            }
        }
    }
}
