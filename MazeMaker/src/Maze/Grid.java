package Maze;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Stack;

public class Grid extends JPanel {
    private int rows;
    private int columns;
    private int cellSize;
    public static Cell[][] grid;
    private Cell start;
    private Cell end;
    private Cell logo;
    private MazeGenerator maze;
    private boolean toggle = false;
    public void toggle(){
        toggle = !toggle;
    }

    /**
     *
     * @param columns
     * @param rows
     * @param cellSize
     */
    public Grid(int columns, int rows, int cellSize) {
        this.setBackground(Color.white);
        this.setBorder(BorderFactory.createLineBorder(Color.lightGray));
        this.setLayout(new GridLayout(rows, columns));
        this.rows = rows;
        this.columns = columns;
        this.cellSize = cellSize;
        init();
    }

    /**
     *
     */
    private void init() {
        grid = new Cell[this.rows][this.columns];
        for (int col = 0; col < this.rows; col++) {
            for (int row = 0; row < this.columns; row++) {
                grid[col][row] = new Cell(col, row, this.cellSize);
                this.add(grid[col][row]);
            }
        }
    }

    /**
     * Draws the Maze on the grid
     */
    public void drawMaze(int rows, int columns){
        maze = new MazeGenerator(rows,columns);
        CellOld[][] maze2 = maze.getGrid();
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < columns; col++) {
                // grid[col][row].setText(col+" "+row);
                if(col==0&&row==0){
                    if(maze2[col][row].getWall(1)){
                        grid[row][col].drawTopWall();
//                    System.out.println("Up");
                    }
                    if(maze2[col][row].getWall(2)){
                        grid[row][col].drawDownWall();
//                    System.out.println("Down");
                    }
                    if(maze2[col][row].getWall(3)){
                        grid[row][col].drawLeftWall();
//                    System.out.println("Left");
                    }
                    if(maze2[col][row].getWall(4)){
                        grid[row][col].drawRightWall();
//                    System.out.println("Right");
                    }
                    if(maze2[col][row].getStart()){
                        grid[row][col].drawStart(Cell.start);
                    }
                    if(maze2[col][row].getEnd()){
                        grid[row][col].drawEnd(Cell.end);
                    }
                    if((maze2[col][row].getIsLogo()) && (Cell.logo != null)){
                        grid[row][col].drawLogo(Cell.logo);
                    }
                }else if(col==columns-1&&row==rows-1){
                    if(maze2[col][row].getWall(1)){
                        grid[row][col].drawTopWall();
//                    System.out.println("Up");
                    }
                    if(maze2[col][row].getWall(2)){
                        //grid[row][col].drawDownWall();
//                    System.out.println("Down");
                    }
                    if(maze2[col][row].getWall(3)){
                        grid[row][col].drawLeftWall();
//                    System.out.println("Left");
                    }
                    if(maze2[col][row].getWall(4)){
                        grid[row][col].drawRightWall();
//                    System.out.println("Right");
                    }
                    if(maze2[col][row].getStart()){
                        grid[row][col].drawStart(Cell.start);
                    }
                    if(maze2[col][row].getEnd()){
                        grid[row][col].drawEnd(Cell.end);
                    }
                    if((maze2[col][row].getIsLogo()) && (Cell.logo != null)){
                        grid[row][col].drawLogo(Cell.logo);
                    }
                }
                else{
                    if(maze2[col][row].getWall(1)){
                        grid[row][col].drawTopWall();
//                    System.out.println("Up");
                    }
                    if(maze2[col][row].getWall(2)){
                        grid[row][col].drawDownWall();
//                    System.out.println("Down");
                    }
                    if(maze2[col][row].getWall(3)){
                        grid[row][col].drawLeftWall();
//                    System.out.println("Left");
                    }
                    if(maze2[col][row].getWall(4)){
                        grid[row][col].drawRightWall();
//                    System.out.println("Right");
                    }
                    if(maze2[col][row].getStart()){
                        grid[row][col].drawStart(Cell.start);
                    }
                    if(maze2[col][row].getEnd()){
                        grid[row][col].drawEnd(Cell.end);
                    }
                    if((maze2[col][row].getIsLogo()) && (Cell.logo != null)){
                        grid[row][col].drawLogo(Cell.logo);
                    }
                }

            }
        }
    }

    /**
     *  Draws the solution on the grid
     */
    public void drawSolution(){

        for (int row = 0; row < this.rows; row++) {
            for (int col = 0; col < this.columns; col++) {
                if(grid[row][col].isEnd()){
                    maze.setEndX(col);
                    maze.setEndY(row);
                }else if(grid[row][col].isStart()){
                    maze.setStartX(col);
                    maze.setStartY(row);
                }
            }
        }
        CellOld[][] maze2 = maze.getGrid();
        Stack<CellOld> solution = maze.solveMaze();
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

    //Getters and Setters
    public String getCellDist(){
        return(maze.cellDistribution());
    }
    public String getDeadEnds(){
        return maze.deadEnds();
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



