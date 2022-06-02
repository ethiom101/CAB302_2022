package Maze;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static GUI.EditMaze.*;

public class CellNew extends JLabel {
    private int row;
    private int column;
    private int cellSize;
    public boolean[] walls = {false, false, false, false}; // top, left, down, right
    public int[] wallsCheck = {0, 0, 0, 0}; // top, left, down, right
    private int strokeSize = 2;


    public CellNew(int row, int column, int cellSize) {
        this.row = row;
        this.column = column;
        this.cellSize = cellSize;
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (itemSelector.getSelectedItem() == "Start") {
                    drawStart();
                }

                if (itemSelector.getSelectedItem() == "End") {
                    drawEnd();
                }

                if (itemSelector.getSelectedItem() == "Wall") {
                    drawWall();
                }
                if (itemSelector.getSelectedItem() == "Logo") {
                    drawLogo();
                }


            }
        });
    }

    public void drawStart() {
        this.setOpaque(true);
        this.setBackground(Color.green);
    }

    public void drawEnd() {
        this.setOpaque(true);
        this.setBackground(Color.red);
    }

    public void drawLogo() {
        this.setOpaque(true);
        this.setBackground(Color.blue);
        this.setBorder(new MatteBorder(strokeSize, strokeSize, strokeSize, strokeSize, Color.black));
    }

    public void drawWall() {
        // set top
        if (topWall.isSelected()) {
            if (!walls[0]) {
                wallsCheck[0] = strokeSize;
                walls[0] = true;
            } else {
                wallsCheck[0] = 0;
                walls[0] = false;
            }
            this.setBorder(new MatteBorder(wallsCheck[0], wallsCheck[1], wallsCheck[2], wallsCheck[3], Color.black));
        }
        // set left
        if (leftWall.isSelected()) {
            if (!walls[1]) {
                wallsCheck[1] = strokeSize;
                walls[1] = true;
            } else {
                wallsCheck[1] = 0;
                walls[1] = false;
            }
            this.setBorder(new MatteBorder(wallsCheck[0], wallsCheck[1], wallsCheck[2], wallsCheck[3], Color.black));
        }
        // set down
        if (downWall.isSelected()) {
            if (!walls[2]) {
                wallsCheck[2] = strokeSize;
                walls[2] = true;
            } else {
                wallsCheck[1] = 0;
                walls[2] = false;
            }
            this.setBorder(new MatteBorder(wallsCheck[0], wallsCheck[1], wallsCheck[2], wallsCheck[3], Color.black));
        }
        // set right
        if (rightWall.isSelected()) {
            if (!walls[3]) {
                wallsCheck[3] = strokeSize;
                walls[3] = true;
            } else {
                wallsCheck[3] = strokeSize;
                walls[3] = false;
            }
            this.setBorder(new MatteBorder(wallsCheck[0], wallsCheck[1], wallsCheck[2], wallsCheck[3], Color.black));
        }
    }

    public int getRow() {
        return this.row;
    }

    public int getColumn() {
        return this.column;
    }
}
