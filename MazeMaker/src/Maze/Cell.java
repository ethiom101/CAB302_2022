package Maze;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Arrays;

import static GUI.EditMaze.*;
import static Maze.Grid.grid;
import static Maze.Images.resizeImage;

public class Cell extends JLabel {
    private Cell parents;
    private final int row;
    private final int column;
    public boolean[] isWall = {false, false, false, false}; // top, left, down, right
    int[] drawWall = {0, 0, 0, 0}; // top, left, down, right
    private boolean isStart;
    private boolean isEnd;
    private boolean isLogo;
    private boolean visited = false;
    public ArrayList<Cell> next;
    public static ImageIcon start = new ImageIcon("arrow.png");
    public static ImageIcon end = new ImageIcon("arrow.png");
    public static ImageIcon logo = null;
    int strokeSize = 1;

    private boolean northWall = true;
    private boolean southWall = true;
    private boolean eastWall = true;
    private boolean westWall = true;


    public Cell(int row, int column) {
        this.row = row;
        this.column = column;
        next = new ArrayList<>();

        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) { // left click
                    System.out.println(row + " " + column);
                    System.out.println("Start: " + isStart);
                    System.out.println("End: " + isEnd);
                    System.out.println(Arrays.toString(isWall));
                    if (itemSelector.getSelectedItem() == "Start") {
                        drawStart(start);
                    }

                    if (itemSelector.getSelectedItem() == "End") {
                        drawEnd(end);
                    }

                    if (itemSelector.getSelectedItem() == "Wall") {
                        if ((topWall.isSelected())) {
                            drawTopWall();
                        }
                        if ((leftWall.isSelected())) {
                            drawLeftWall();
                        }
                        if ((downWall.isSelected())) {
                            drawDownWall();
                        }
                        if ((rightWall.isSelected())) {
                            drawRightWall();
                        }
                    }
                    if (itemSelector.getSelectedItem() == "Logo") {
                        drawLogo(logo);
                    }
                }
                if (e.getButton() == MouseEvent.BUTTON3) { // left click
                    resetCell();
                }
            }
        });
    }

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

    public void setImage(ImageIcon image, int cellSize) {
        this.setIcon(resizeImage(image, cellSize, cellSize));
    }

    public boolean isStart() {
        return isStart;
    }

    public boolean isEnd() {
        return isEnd;
    }

    public boolean isLogo() {
        return isLogo;
    }

    public boolean isTopWall() {
        return isWall[0];
    }

    public boolean isLeftWall() {
        return isWall[1];
    }

    public boolean isDownWall() {
        return isWall[2];
    }

    public boolean isRightWall() {
        return isWall[3];
    }

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

    public int getRow() {
        return this.row;
    }

    public int getColumn() {
        return this.column;
    }

    public void setVisited(boolean bool) {
        this.visited = bool;
    }

    public boolean getVisited() {
        return visited;
    }

    public void setParents(Cell parent) {
        this.parents = parent;
    }

    public Cell getParents() {
        return parents;
    }

    public void setLogo() {
        this.isLogo = true;
    }

    public boolean getLogo() {
        return this.isLogo;
    }

    public void setStart() {
        this.isStart = true;
    }

    public boolean getStart() {
        return this.isStart;
    }

    public void setEnd() {
        this.isEnd = true;
    }

    public boolean getEnd() {
        return this.isEnd;
    }
}
