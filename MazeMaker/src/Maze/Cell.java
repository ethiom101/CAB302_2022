package Maze;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Arrays;

import static GUI.EditMaze.*;
import static GUI.HomePage.mazeEditor;
import static Maze.Grid.grid;
import static Util.Images.resizeImage;

/**
 * Class for the Cell values of the maze
 */
public class Cell extends JLabel {
    private Cell parents;
    private final int row;
    private final int column;
    public boolean[] isWall = {false, false, false, false}; // top, left, down, right
    public int[] drawWall = {0, 0, 0, 0}; // top, left, down, right
    private boolean isStart;
    private boolean isEnd;
    private boolean isLogo;
    private boolean visited = false;
    private final ArrayList<Cell> next;
    public static ImageIcon start = new ImageIcon("arrow.png");
    public static ImageIcon end = new ImageIcon("arrow.png");
    public static ImageIcon logo = null;
    public int strokeSize = 1;
    private boolean northWall = true;
    private boolean southWall = true;
    private boolean eastWall = true;
    private boolean westWall = true;

    /**
     * Cell class for the maze
     * @param row The row this cell is in
     * @param column The column this cell is in
     */
    public Cell(int row, int column) {
        this.row = row;
        this.column = column;
        next = new ArrayList<>();

        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) { // left click
                    if (mazeEditor.itemSelector.getSelectedItem() == "Start") {
                        drawStart(start);
                    }

                    if (mazeEditor.itemSelector.getSelectedItem() == "End") {
                        drawEnd(end);
                    }

                    if (mazeEditor.itemSelector.getSelectedItem() == "Wall") {
                        if ((mazeEditor.topWall.isSelected())) {
                            drawTopWall();
                        }
                        if ((mazeEditor.leftWall.isSelected())) {
                            drawLeftWall();
                        }
                        if ((mazeEditor.downWall.isSelected())) {
                            drawDownWall();
                        }
                        if ((mazeEditor.rightWall.isSelected())) {
                            drawRightWall();
                        }
                    }
                    if (mazeEditor.itemSelector.getSelectedItem() == "Logo") {
                        drawLogo(logo);
                    }
                }
                if (e.getButton() == MouseEvent.BUTTON3) { // left click
                    resetCell();
                }
            }
        });
    }

    /**
     * Draws the start image for the start cell
     * @param start Start image for the start cell
     */
    public void drawStart(ImageIcon start) {
        start = (resizeImage(start, mazeGrid.getCellSize(), mazeGrid.getCellSize()));
        if (this.isStart) {
            this.setIcon(null);
            this.isStart = false;
            mazeGrid.setStart(null);
        } else if (mazeGrid.getStart() != null) {
            mazeGrid.getStart().isStart = false;
            mazeGrid.getStart().setIcon(null);
            this.setIcon(start);
            this.isStart = true;
            mazeGrid.setStart(this);
        } else if (mazeGrid.getStart() == null) {
            this.setIcon(start);
            this.isStart = true;
            mazeGrid.setStart(this);
        }
        if (this.isEnd) {
            mazeGrid.getEnd().setIcon(null);
            mazeGrid.setEnd(null);
            this.isEnd = false;
            this.isStart = true;
            this.setIcon(start);
        }
        if (this.isLogo) {
            mazeGrid.getLogo().setIcon(null);
            mazeGrid.getLogo().isWall[0] = false;
            mazeGrid.getLogo().isWall[1] = false;
            mazeGrid.getLogo().isWall[2] = false;
            mazeGrid.getLogo().isWall[3] = false;
            mazeGrid.getLogo().drawWall[0] = 0;
            mazeGrid.getLogo().drawWall[1] = 0;
            mazeGrid.getLogo().drawWall[2] = 0;
            mazeGrid.getLogo().drawWall[3] = 0;
            mazeGrid.getLogo().setBorder(new MatteBorder(drawWall[0], drawWall[1], drawWall[2], drawWall[3], Color.black));
            mazeGrid.setLogo(null);
            this.isStart = false;
            this.isEnd = true;
            this.setIcon(start);
        }
    }

    /**
     * Draws end image for the end cell
     * @param end The image we want for the end cell
     */
    public void drawEnd(ImageIcon end) {
        end = (resizeImage(end, mazeGrid.getCellSize(), mazeGrid.getCellSize()));
        if (this.isEnd) {
            this.setIcon(null);
            this.isEnd = false;
            mazeGrid.setEnd(null);
        } else if (mazeGrid.getEnd() != null) {
            mazeGrid.getEnd().isEnd = false;
            mazeGrid.getEnd().setIcon(null);
            this.setIcon(end);
            this.isEnd = true;
            mazeGrid.setEnd(this);
        } else if (mazeGrid.getEnd() == null) {
            this.setIcon(end);
            this.isEnd = true;
            mazeGrid.setEnd(this);
        }
        if (this.isStart) {
            mazeGrid.getStart().setIcon(null);
            mazeGrid.setStart(null);
            this.isStart = false;
            this.isEnd = true;
            this.setIcon(end);
        }
        if (this.isLogo) {
            mazeGrid.getLogo().setIcon(null);
            mazeGrid.getLogo().isWall[0] = false;
            mazeGrid.getLogo().isWall[1] = false;
            mazeGrid.getLogo().isWall[2] = false;
            mazeGrid.getLogo().isWall[3] = false;
            mazeGrid.getLogo().drawWall[0] = 0;
            mazeGrid.getLogo().drawWall[1] = 0;
            mazeGrid.getLogo().drawWall[2] = 0;
            mazeGrid.getLogo().drawWall[3] = 0;
            mazeGrid.getLogo().setBorder(new MatteBorder(drawWall[0], drawWall[1], drawWall[2], drawWall[3], Color.black));
            mazeGrid.setLogo(null);
            this.isStart = false;
            this.isEnd = true;
            this.setIcon(end);
        }
    }

    /**
     * Draws the Logo
     * @param logo The logo image we want to draw
     */
    public void drawLogo(ImageIcon logo) {
        if (logo != null) {
            logo = (resizeImage(logo, mazeGrid.getCellSize(), mazeGrid.getCellSize()));
            if (this.isLogo) {
                this.setIcon(null);
                eraseAllWalls();
                this.isLogo = false;
                mazeGrid.setLogo(null);
            } else if (mazeGrid.getLogo() != null) {
                mazeGrid.getLogo().isLogo = false;
                mazeGrid.getLogo().eraseAllWalls();
                mazeGrid.getLogo().setIcon(null);
                this.setIcon(logo);
                drawAllWalls();
                this.isLogo = true;
                mazeGrid.setLogo(this);
            } else if (mazeGrid.getLogo() == null) {
                this.setIcon(logo);
                drawAllWalls();
                this.isLogo = true;
                mazeGrid.setLogo(this);
            }
            if (this.isStart) {
                mazeGrid.getStart().setIcon(null);
                mazeGrid.setStart(null);
                this.isStart = false;
                this.isLogo = true;
                this.setIcon(logo);
                drawAllWalls();

            }
            if (this.isEnd) {
                mazeGrid.getEnd().setIcon(null);
                this.isEnd = false;
                this.isStart = true;
                this.setIcon(logo);
                mazeGrid.setEnd(null);
                drawAllWalls();

            }
        }
    }

    /**
     * Draws all the walls of the cell
     */
    public void drawAllWalls() {
        this.isWall[0] = true;
        this.isWall[1] = true;
        this.isWall[2] = true;
        this.isWall[3] = true;
        this.drawWall[0] = strokeSize;
        this.drawWall[1] = strokeSize;
        this.drawWall[2] = strokeSize;
        this.drawWall[3] = strokeSize;
        try {
            grid[this.row + 1][this.column].isWall[0] = true;
            grid[this.row][this.column + 1].isWall[1] = true;
            grid[this.row - 1][this.column].isWall[2] = true;
            grid[this.row][this.column - 1].isWall[3] = true;

            grid[this.row + 1][this.column].drawWall[0] = strokeSize;
            grid[this.row][this.column + 1].drawWall[1] = strokeSize;
            grid[this.row - 1][this.column].drawWall[2] = strokeSize;
            grid[this.row][this.column - 1].drawWall[3] = strokeSize;

            grid[this.row + 1][this.column].setBorder(new MatteBorder(grid[this.row + 1][this.column].drawWall[0], grid[this.row + 1][this.column].drawWall[1], grid[this.row + 1][this.column].drawWall[2], grid[this.row + 1][this.column].drawWall[3], Color.black));
            grid[this.row][this.column + 1].setBorder(new MatteBorder(grid[this.row][this.column + 1].drawWall[0], grid[this.row][this.column + 1].drawWall[1], grid[this.row][this.column + 1].drawWall[2], grid[this.row][this.column + 1].drawWall[3], Color.black));
            grid[this.row - 1][this.column].setBorder(new MatteBorder(grid[this.row - 1][this.column].drawWall[0], grid[this.row - 1][this.column].drawWall[1], grid[this.row - 1][this.column].drawWall[2], grid[this.row - 1][this.column].drawWall[3], Color.black));
            grid[this.row][this.column - 1].setBorder(new MatteBorder(grid[this.row][this.column - 1].drawWall[0], grid[this.row][this.column - 1].drawWall[1], grid[this.row][this.column - 1].drawWall[2], grid[this.row][this.column - 1].drawWall[3], Color.black));
            }
        catch (Exception ignored) {
            drawWall[0] = strokeSize + 1;
            drawWall[1] = strokeSize + 1;
            drawWall[2] = strokeSize + 1;
            drawWall[3] = strokeSize + 1;
        }
        this.setBorder(new MatteBorder(drawWall[0], drawWall[1], drawWall[2], drawWall[3], Color.black));

    }

    /**
     * Erases the walls of the cell
     */
    public void eraseAllWalls() {
        this.isWall[0] = false;
        this.isWall[1] = false;
        this.isWall[2] = false;
        this.isWall[3] = false;
        this.drawWall[0] = 0;
        this.drawWall[1] = 0;
        this.drawWall[2] = 0;
        this.drawWall[3] = 0;
        this.setBorder(new MatteBorder(drawWall[0], drawWall[1], drawWall[2], drawWall[3], Color.black));
        try {
            grid[this.row + 1][this.column].isWall[0] = false;
            grid[this.row + 1][this.column].drawWall[0] = 0;
            grid[this.row + 1][this.column].setBorder(new MatteBorder(grid[this.row + 1][this.column].drawWall[0], grid[this.row + 1][this.column].drawWall[1], grid[this.row + 1][this.column].drawWall[2], grid[this.row + 1][this.column].drawWall[3], Color.black));
        } catch (Exception ignored) {}
        try {
            grid[this.row][this.column + 1].isWall[1] = false;
            grid[this.row][this.column + 1].drawWall[1] = 0;
            grid[this.row][this.column + 1].setBorder(new MatteBorder(grid[this.row][this.column + 1].drawWall[0], grid[this.row][this.column + 1].drawWall[1], grid[this.row][this.column + 1].drawWall[2], grid[this.row][this.column + 1].drawWall[3], Color.black));
        } catch (Exception ignored) {}
        try {
            grid[this.row - 1][this.column].isWall[2] = false;
            grid[this.row - 1][this.column].drawWall[2] = 0;
            grid[this.row - 1][this.column].setBorder(new MatteBorder(grid[this.row - 1][this.column].drawWall[0], grid[this.row - 1][this.column].drawWall[1], grid[this.row - 1][this.column].drawWall[2], grid[this.row - 1][this.column].drawWall[3], Color.black));
        } catch (Exception ignored) {}
        try {
            grid[this.row][this.column - 1].isWall[3] = false;
            grid[this.row][this.column - 1].drawWall[3] = 0;
            grid[this.row][this.column - 1].setBorder(new MatteBorder(grid[this.row][this.column - 1].drawWall[0], grid[this.row][this.column - 1].drawWall[1], grid[this.row][this.column - 1].drawWall[2], grid[this.row][this.column - 1].drawWall[3], Color.black));
        } catch (Exception ignored) {}
    }

    /**
     * Draw the top wall of the cell
     */
    public void drawTopWall() {
        if (!isWall[0]) {
            drawWall[0] = strokeSize;
            isWall[0] = true;
            try {
                grid[this.row - 1][this.column].drawWall[2] = strokeSize;
                grid[this.row - 1][this.column].isWall[2] = true;
                grid[this.row - 1][this.column].setBorder(new MatteBorder(grid[this.row - 1][this.column].drawWall[0], grid[this.row - 1][this.column].drawWall[1], grid[this.row - 1][this.column].drawWall[2], grid[this.row - 1][this.column].drawWall[3], Color.black));
            } catch (Exception ignored) {
                drawWall[0] = strokeSize + 1;
            }
        } else {
            drawWall[0] = 0;
            isWall[0] = false;
            try {
                grid[this.row - 1][this.column].drawWall[2] = 0;
                grid[this.row - 1][this.column].isWall[2] = false;
                grid[this.row - 1][this.column].setBorder(new MatteBorder(grid[this.row - 1][this.column].drawWall[0], grid[this.row - 1][this.column].drawWall[1], grid[this.row - 1][this.column].drawWall[2], grid[this.row - 1][this.column].drawWall[3], Color.black));
            } catch (Exception ignored) {}
        }
        this.setBorder(new MatteBorder(drawWall[0], drawWall[1], drawWall[2], drawWall[3], Color.black));
    }

    /**
     * Draws the left wall of the cell
     */
    public void drawLeftWall() {
        if (!isWall[1]) {
            drawWall[1] = strokeSize;
            isWall[1] = true;
            try {
                grid[this.row][this.column - 1].drawWall[3] = strokeSize;
                grid[this.row][this.column - 1].isWall[3] = true;
                grid[this.row][this.column - 1].setBorder(new MatteBorder(grid[this.row][this.column - 1].drawWall[0], grid[this.row][this.column - 1].drawWall[1], grid[this.row][this.column - 1].drawWall[2], grid[this.row][this.column - 1].drawWall[3], Color.black));
            } catch (Exception ignored) {
                drawWall[1] = strokeSize + 1;
            }
        } else {
            drawWall[1] = 0;
            isWall[1] = false;
            try {
                grid[this.row][this.column - 1].drawWall[3] = 0;
                grid[this.row][this.column - 1].isWall[3] = false;
                grid[this.row][this.column - 1].setBorder(new MatteBorder(grid[this.row][this.column - 1].drawWall[0], grid[this.row][this.column - 1].drawWall[1], grid[this.row][this.column - 1].drawWall[2], grid[this.row][this.column - 1].drawWall[3], Color.black));
            } catch (Exception ignored) {}
        }
        this.setBorder(new MatteBorder(drawWall[0], drawWall[1], drawWall[2], drawWall[3], Color.black));
    }

    /**
     * Draws the bottom wall of the cell
     */
    public void drawDownWall() {
        if (!isWall[2]) {
            drawWall[2] = strokeSize;
            isWall[2] = true;
            try {
                grid[this.row + 1][this.column].drawWall[0] = strokeSize;
                grid[this.row + 1][this.column].isWall[0] = true;
                grid[this.row + 1][this.column].setBorder(new MatteBorder(grid[this.row + 1][this.column].drawWall[0], grid[this.row + 1][this.column].drawWall[1], grid[this.row + 1][this.column].drawWall[2], grid[this.row + 1][this.column].drawWall[3], Color.black));
            } catch (Exception ignored) {
                drawWall[2] = strokeSize + 1;}
        } else {
            drawWall[2] = 0;
            isWall[2] = false;
            try {
                grid[this.row + 1][this.column].drawWall[0] = 0;
                grid[this.row + 1][this.column].isWall[0] = false;
                grid[this.row + 1][this.column].setBorder(new MatteBorder(grid[this.row + 1][this.column].drawWall[0], grid[this.row + 1][this.column].drawWall[1], grid[this.row + 1][this.column].drawWall[2], grid[this.row + 1][this.column].drawWall[3], Color.black));
            } catch (Exception ignored) {}
        }
        this.setBorder(new MatteBorder(drawWall[0], drawWall[1], drawWall[2], drawWall[3], Color.black));
    }

    /**
     * Draws the right wall of the cell
     */
    public void drawRightWall() {
        if (!isWall[3]) {
            drawWall[3] = strokeSize;
            isWall[3] = true;
            try {
                grid[this.row][this.column + 1].drawWall[1] = strokeSize;
                grid[this.row][this.column + 1].isWall[1] = true;
                grid[this.row][this.column + 1].setBorder(new MatteBorder(grid[this.row][this.column + 1].drawWall[0], grid[this.row][this.column + 1].drawWall[1], grid[this.row][this.column + 1].drawWall[2], grid[this.row][this.column + 1].drawWall[3], Color.black));
            } catch (Exception ignored) {
                drawWall[3] = strokeSize + 1;
            }
        } else {
            drawWall[3] = 0;
            isWall[3] = false;
            try {
                grid[this.row][this.column + 1].drawWall[1] = 0;
                grid[this.row][this.column + 1].isWall[1] = false;
                grid[this.row][this.column + 1].setBorder(new MatteBorder(grid[this.row][this.column + 1].drawWall[0], grid[this.row][this.column + 1].drawWall[1], grid[this.row][this.column + 1].drawWall[2], grid[this.row][this.column + 1].drawWall[3], Color.black));
            } catch (Exception ignored) {}
        }
        this.setBorder(new MatteBorder(drawWall[0], drawWall[1], drawWall[2], drawWall[3], Color.black));
    }

    /**
     * Resets cell so it is empty
     */
    public void resetCell() {
        eraseAllWalls();

        if (this.isStart) {
            this.isStart = false;
            mazeGrid.setStart(null);
        }

        if (this.isEnd) {
            this.isEnd = false;
            mazeGrid.setEnd(null);
        }

        if (this.isLogo) {
            this.isLogo = false;
            mazeGrid.setLogo(null);
        }
        this.setBackground(Color.white);
        this.setIcon(null);
        this.removeAll();
    }

    /**
     * Set image that we want to use
     * @param image The image file
     * @param cellSize The size of the cell so we can scale the image
     */
    public void setImage(ImageIcon image, int cellSize) {
        this.setIcon(resizeImage(image, cellSize, cellSize));
    }

    /**
     * Getter for if this is the start cell
     * @return Boolean value depending on if this is the start cell
     */
    public boolean isStart() {
        return isStart;
    }

    /**
     * Getter for if this is the end cell
     * @return Boolean value depending on if this is the end cell
     */
    public boolean isEnd() {
        return isEnd;
    }

    /**
     * Getter for if this is a logo cell
     * @return Boolean value depending on if this is a logo cell
     */
    public boolean isLogo() {
        return isLogo;
    }

    /**
     * Getter if the top wall is drawn on this cell
     * @return Boolean if top wall is drawn on this cell
     */
    public boolean isTopWall() {
        return isWall[0];
    }

    /**
     * Getter if the left wall is drawn on this cell
     * @return Boolean if left wall is drawn on this cell
     */
    public boolean isLeftWall() {
        return isWall[1];
    }

    /**
     * Getter if the bottom wall is drawn on this cell
     * @return Boolean if bottom wall is drawn on this cell
     */
    public boolean isDownWall() {
        return isWall[2];
    }

    /**
     *Getter if the right wall is drawn on this cell
     * @return Boolean if right wall is drawn on this cell
     */
    public boolean isRightWall() {
        return isWall[3];
    }

    /**
     * Removes the wall from the cell
     * @param wall Integer value for which direction wall is being removed (1,2,3,4)=(N,S,W,E)
     */
    public void setWall(int wall) {
        if (wall == 1) {
            northWall = false;
        } else if (wall == 2) {
            southWall = false;
        } else if (wall == 3) {
            westWall = false;
        } else if (wall == 4) {
            eastWall = false;
        }
    }

    /**
     * Getter to return if the cell has a wall in the direction given
     * @param wall Integer value for which direction wall is being returned (1,2,3,4)=(N,S,W,E)
     * @return Boolean value if the wall is present or not in that direction
     */
    public boolean getWall(int wall) {
        if (wall == 1) {
            return northWall;
        } else if (wall == 2) {
            return southWall;
        } else if (wall == 3) {
            return westWall;
        } else if (wall == 4) {
            return eastWall;
        }
        return false;
    }

    /**
     * Getter for this cells row position
     * @return Integer value for this cells row
     */
    public int getRow() {
        return this.row;
    }

    /**
     * Getter for this cell's column position
     * @return Integer value for this cells column
     */
    public int getColumn() {
        return this.column;
    }

    /**
     * Set if this cell has been visited or not
     * @param bool Boolean value to set whether the cell has been visited or not
     */
    public void setVisited(boolean bool) {
        this.visited = bool;
    }

    /**
     * Getter for if this cell has been visited or not
     * @return Boolean value for if this cell has been visited or not
     */
    public boolean getVisited() {
        return visited;
    }

    /**
     * Set the parent of this cell
     * @param parent The parent cell of this cell
     */
    public void setParents(Cell parent) {
        this.parents = parent;
    }

    /**
     * Get parent cell of this cell
     * @return The parent cell of this cell
     */
    public Cell getParents() {
        return parents;
    }

    /**
     * Set that this cell is a logo cell
     */
    public void setLogo() {
        this.isLogo = true;
    }

    /**
     * Return if this cell is a logo cell or not
     * @return Boolean value indicating whether this cell is a logo cell or not
     */
    public boolean getLogo() {
        return this.isLogo;
    }

    /**
     * Set that this cell is the start cell
     */
    public void setStart() {
        this.isStart = true;
    }

    /**
     * Return if this cell is a start cell or not
     * @return Boolean value indicating whether this cell is the start cell or not
     */
    public boolean getStart() {
        return this.isStart;
    }

    /**
     * Set that this cell is the end cell
     */
    public void setEnd() {
        this.isEnd = true;
    }

    /**
     * Return if this cell is an end cell or not
     * @return Boolean value indicating whether this end is the start cell or not
     */
    public boolean getEnd() {
        return this.isEnd;
    }

    public ArrayList<Cell> getNext() {
        return next;
    }
}
