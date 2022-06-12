package Maze;


import javax.swing.border.MatteBorder;
import java.awt.*;

import static Maze.Grid.grid;

/**
 * Functionality for saving mazes and opening them
 */
public class MazeFile {

    /**
     * Saves the maze by outputting an integer that correlates to a type of cell in the grid in a String
     *
     * @param width  the width of the maze being saved
     * @param height the height of the maze being saved
     */
    public static String saveMaze(int width, int height) {
        StringBuilder mazeCells = new StringBuilder();
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (grid[j][i].isStart()) {
                    mazeCells.append("1");
                } else if (grid[j][i].isEnd()) {
                    mazeCells.append("2");
                } else if (grid[j][i].isLogo()) {
                    mazeCells.append("3");
                }

                if (grid[j][i].isTopWall()) {
                    mazeCells.append("4");
                }
                if (grid[j][i].isLeftWall()) {
                    mazeCells.append("5");
                }
                if (grid[j][i].isDownWall()) {
                    mazeCells.append("6");
                }
                if (grid[j][i].isRightWall()) {
                    mazeCells.append("7");
                }
                mazeCells.append("0");
            }
        }
        return mazeCells.toString();
    }

    /**
     * Draws the maze from a String saved by the previous method
     * and uses other methods to draw the maze based on the char read
     * at the Strings specific position.
     * Works by reading each line of the String.
     *
     * @param maze the maze to open
     */
    public static void openMaze(Maze maze) {
        String mazeCells = maze.getMazeCells();
        int cellCounter = 0;
        char cellType = mazeCells.charAt(cellCounter);
        for (int i = 0; i < maze.getWidth(); i++) {
            for (int j = 0; j < maze.getHeight(); j++) {
                if (cellType == '1') {
                    grid[j][i].drawStart(Cell.start);
                    cellCounter++;
                } else if (cellType == '2') {
                    grid[j][i].drawEnd(Cell.end);
                    cellCounter++;
                } else if (cellType == '3') {
                    grid[j][i].drawLogo(Cell.logo);
                    cellCounter++;
                }
                cellType = mazeCells.charAt(cellCounter);


                if (cellType == '4') { // top wall
                    grid[j][i].drawWall[0] = grid[j][i].strokeSize;
                    grid[j][i].isWall[0] = true;
                    try {
                        grid[j - 1][i].drawWall[2] = grid[j][i].strokeSize;
                        grid[j - 1][i].isWall[2] = true;
                        grid[j - 1][i].setBorder(new MatteBorder(grid[j - 1][i].drawWall[0], grid[j - 1][i].drawWall[1], grid[j - 1][i].drawWall[2], grid[j - 1][i].drawWall[3], Color.black));
                    } catch (Exception ignored) {
                        grid[j][i].drawWall[0] = grid[j][i].strokeSize + 1;
                    }
                    grid[j][i].setBorder(new MatteBorder(grid[j][i].drawWall[0], grid[j][i].drawWall[1], grid[j][i].drawWall[2], grid[j][i].drawWall[3], Color.black));
                    cellCounter++;
                    cellType = mazeCells.charAt(cellCounter);
                }

                if (cellType == '5') { // left wall
                    grid[j][i].drawWall[1] = grid[j][i].strokeSize;
                    grid[j][i].isWall[1] = true;
                    try {
                        grid[j][i - 1].drawWall[3] = grid[j][i].strokeSize;
                        grid[j][i - 1].isWall[3] = true;
                        grid[j][i - 1].setBorder(new MatteBorder(grid[j][i - 1].drawWall[0], grid[j][i - 1].drawWall[1], grid[j][i - 1].drawWall[2], grid[j][i - 1].drawWall[3], Color.black));
                    } catch (Exception ignored) {
                        grid[j][i].drawWall[1] = grid[j][i].strokeSize + 1;
                    }
                    grid[j][i].setBorder(new MatteBorder(grid[j][i].drawWall[0], grid[j][i].drawWall[1], grid[j][i].drawWall[2], grid[j][i].drawWall[3], Color.black));
                    cellCounter++;
                    cellType = mazeCells.charAt(cellCounter);
                }
//
                if (cellType == '6') { // down wall
                    grid[j][i].drawWall[2] = grid[j][i].strokeSize;
                    grid[j][i].isWall[2] = true;
                    try {
                        grid[j + 1][i].drawWall[0] = grid[j][i].strokeSize;
                        grid[j + 1][i].isWall[0] = true;
                        grid[j + 1][i].setBorder(new MatteBorder(grid[j + 1][i].drawWall[0], grid[j + 1][i].drawWall[1], grid[j + 1][i].drawWall[2], grid[j + 1][i].drawWall[3], Color.black));
                    } catch (Exception ignored) {
                        grid[j][i].drawWall[2] = grid[j][i].strokeSize + 1;
                    }
                    grid[j][i].setBorder(new MatteBorder(grid[j][i].drawWall[0], grid[j][i].drawWall[1], grid[j][i].drawWall[2], grid[j][i].drawWall[3], Color.black));
                    cellCounter++;
                    cellType = mazeCells.charAt(cellCounter);
                }
//
                if (cellType == '7') { // right wall
                    grid[j][i].drawWall[3] = grid[j][i].strokeSize;
                    grid[j][i].isWall[3] = true;
                    try {
                        grid[j][i + 1].drawWall[1] = grid[j][i].strokeSize;
                        grid[j][i + 1].isWall[1] = true;
                        grid[j][i + 1].setBorder(new MatteBorder(grid[j][i + 1].drawWall[0], grid[j][i + 1].drawWall[1], grid[j][i + 1].drawWall[2], grid[j][i + 1].drawWall[3], Color.black));
                    } catch (Exception ignored) {
                        grid[j][i].drawWall[3] = grid[j][i].strokeSize + 1;
                    }
                    grid[j][i].setBorder(new MatteBorder(grid[j][i].drawWall[0], grid[j][i].drawWall[1], grid[j][i].drawWall[2], grid[j][i].drawWall[3], Color.black));
                    cellCounter++;
                    cellType = mazeCells.charAt(cellCounter);
                }

                if (cellType == '0') {
                    cellCounter++;
                    if (cellCounter < mazeCells.length()) {
                        cellType = mazeCells.charAt(cellCounter);
                    }
                }
            }
        }
    }
}
