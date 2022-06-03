package Maze;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Grid extends JPanel {
    private int rows;
    private int columns;
    private int cellSize;

    public static ImageIcon startImage = new ImageIcon("arrow.png");

    public static Cell[][] grid;
    private Cell start;
    private Cell end;
    private Cell logo;
    private MazeGenerator maze;
    private boolean toggleGenerator = false;
    private boolean toggle = false;

    public void toggleGeno(){
        toggleGenerator = !toggleGenerator;
    }
    public void toggle(){
        toggle = !toggle;
    }

    public Grid(int columns, int rows, int cellSize) {
        this.setBackground(Color.white);
        this.setBorder(BorderFactory.createLineBorder(Color.lightGray));
        this.setLayout(new GridLayout(rows, columns));
        this.rows = rows;
        this.columns = columns;
        this.cellSize = cellSize;
        this.maze = new MazeGenerator(rows,columns);
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

    public void drawMaze(){
        if(!toggleGenerator){
            maze = new MazeGenerator(rows,columns);
        }
        CellOld[][] maze2 = maze.getGrid();
        int i = 0;
        for (int row = 0; row < this.rows; row++) {
            for (int col = 0; col < this.columns; col++) {
//                grid[col][row].setText(i+"");
//                i++;
                if(maze2[col][row].getWall(1)){
                    grid[row][col].drawTopWall();
                }
                if(maze2[col][row].getWall(2)){
                    grid[row][col].drawDownWall();
                }
                if(maze2[col][row].getWall(3)){
                    grid[row][col].drawLeftWall();
                }
                if(maze2[col][row].getWall(4)){
                    grid[row][col].drawRightWall();
                }
                if(maze2[col][row].getStart()){

                    grid[row][col].drawStart(startImage);
                }
                if(maze2[col][row].getEnd()){

                    grid[row][col].drawEnd(startImage);
                }
            }
        }
    }
    public void drawSolution(){
        CellOld[][] maze2 = maze.getGrid();
        ArrayList<CellOld> solution = maze.getSolution();
        for (int row = 0; row < this.rows; row++) {
            for (int col = 0; col < this.columns; col++) {
                if(solution.contains(maze2[col][row])){
                    if(toggle){
                        grid[row][col].setOpaque(true);
                        grid[row][col].setBackground(Color.white);
                    }else {
                        grid[row][col].setOpaque(true);
                        grid[row][col].setBackground(Color.pink);
                    }
                }
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



