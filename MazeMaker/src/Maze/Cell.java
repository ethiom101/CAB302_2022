package Maze;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static GUI.EditMaze.*;
import static Maze.Images.resizeImage;

public class Cell extends JLabel {
    private int row;
    private int column;
    private int cellSize;
    public boolean[] isWall = {false, false, false, false}; // top, left, down, right
    private int[] drawWall = {0, 0, 0, 0}; // top, left, down, right
    private boolean isStart;
    private boolean isEnd;
    private boolean isLogo;
    public static ImageIcon start = new ImageIcon("arrow.png");
    public static ImageIcon end = new ImageIcon("arrow.png");;
    public static ImageIcon logo;
    int strokeSize = 3;


    public Cell(int row, int column, int cellSize) {
        this.row = row;
        this.column = column;
        this.cellSize = cellSize;

        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) { // left click
                    System.out.println(row + " " + column);
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
        start = (resizeImage(start, Grid.getCellSize(), Grid.getCellSize()));
        if (this.isStart) {
            this.setIcon(null);
            this.isStart = false;
            Grid.setStart(null);
        } else if (Grid.getStart() != null) {
            Grid.getStart().isStart = false;
            Grid.getStart().setIcon(null);
            this.setIcon(start);
            this.isStart = true;
            Grid.setStart(this);
        } else if (Grid.getStart() == null){
            this.setIcon(start);
            this.isStart = true;
            Grid.setStart(this);
        }
        if (this.isEnd) {
            Grid.getEnd().setIcon(null);
            Grid.setEnd(null);
            this.isEnd = false;
            this.isStart = true;
            this.setIcon(start);
        }
        if (this.isLogo) {
            Grid.getLogo().setIcon(null);
            Grid.getLogo().isWall[0] = false;
            Grid.getLogo().isWall[1] = false;
            Grid.getLogo().isWall[2] = false;
            Grid.getLogo().isWall[3] = false;
            Grid.getLogo().drawWall[0] = 0;
            Grid.getLogo().drawWall[1] = 0;
            Grid.getLogo().drawWall[2] = 0;
            Grid.getLogo().drawWall[3] = 0;
            Grid.getLogo().setBorder(new MatteBorder(drawWall[0], drawWall[1], drawWall[2], drawWall[3], Color.black));
            Grid.setLogo(null);
            this.isStart = false;
            this.isEnd = true;
            this.setIcon(start);
        }
    }

    public void drawEnd(ImageIcon end) {
        end = (resizeImage(end, Grid.getCellSize(), Grid.getCellSize()));
        if (this.isEnd) {
            this.setIcon(null);
            this.isEnd = false;
            Grid.setEnd(null);
        } else if (Grid.getEnd() != null) {
            Grid.getEnd().isEnd = false;
            Grid.getEnd().setIcon(null);
            this.setIcon(end);
            this.isEnd = true;
            Grid.setEnd(this);
        } else if (Grid.getEnd() == null){
            this.setIcon(end);
            this.isEnd = true;
            Grid.setEnd(this);
        }
        if (this.isStart) {
            Grid.getStart().setIcon(null);
            Grid.setStart(null);
            this.isStart = false;
            this.isEnd = true;
            this.setIcon(end);
        }
        if (this.isLogo) {
            Grid.getLogo().setIcon(null);
            Grid.getLogo().isWall[0] = false;
            Grid.getLogo().isWall[1] = false;
            Grid.getLogo().isWall[2] = false;
            Grid.getLogo().isWall[3] = false;
            Grid.getLogo().drawWall[0] = 0;
            Grid.getLogo().drawWall[1] = 0;
            Grid.getLogo().drawWall[2] = 0;
            Grid.getLogo().drawWall[3] = 0;
            Grid.getLogo().setBorder(new MatteBorder(drawWall[0], drawWall[1], drawWall[2], drawWall[3], Color.black));
            Grid.setLogo(null);
            this.isStart = false;
            this.isEnd = true;
            this.setIcon(end);
        }
    }

    public void drawLogo(ImageIcon logo) {
        if (logo != null) {
            logo = (resizeImage(logo, Grid.getCellSize(), Grid.getCellSize()));
            if (this.isLogo) {
                this.setIcon(null);
                this.isWall[0] = false;
                this.isWall[1] = false;
                this.isWall[2] = false;
                this.isWall[3] = false;
                this.drawWall[0] = 0;
                this.drawWall[1] = 0;
                this.drawWall[2] = 0;
                this.drawWall[3] = 0;
                this.setBorder(new MatteBorder(drawWall[0], drawWall[1], drawWall[2], drawWall[3], Color.black));
                this.isLogo = false;
                Grid.setLogo(null);
            } else if (Grid.getLogo() != null) {
                Grid.getLogo().isLogo = false;
                Grid.getLogo().isWall[0] = false;
                Grid.getLogo().isWall[1] = false;
                Grid.getLogo().isWall[2] = false;
                Grid.getLogo().isWall[3] = false;
                Grid.getLogo().drawWall[0] = 0;
                Grid.getLogo().drawWall[1] = 0;
                Grid.getLogo().drawWall[2] = 0;
                Grid.getLogo().drawWall[3] = 0;
                Grid.getLogo().setBorder(new MatteBorder(drawWall[0], drawWall[1], drawWall[2], drawWall[3], Color.black));
                Grid.getLogo().setIcon(null);
                this.setIcon(logo);
                this.isWall[0] = true;
                this.isWall[1] = true;
                this.isWall[2] = true;
                this.isWall[3] = true;
                this.drawWall[0] = strokeSize;
                this.drawWall[1] = strokeSize;
                this.drawWall[2] = strokeSize;
                this.drawWall[3] = strokeSize;
                this.setBorder(new MatteBorder(drawWall[0], drawWall[1], drawWall[2], drawWall[3], Color.black));
                this.isLogo = true;
                Grid.setLogo(this);
            } else if (Grid.getLogo() == null){
                this.setIcon(logo);
                this.isWall[0] = true;
                this.isWall[1] = true;
                this.isWall[2] = true;
                this.isWall[3] = true;
                this.drawWall[0] = strokeSize;
                this.drawWall[1] = strokeSize;
                this.drawWall[2] = strokeSize;
                this.drawWall[3] = strokeSize;
                this.setBorder(new MatteBorder(drawWall[0], drawWall[1], drawWall[2], drawWall[3], Color.black));
                this.isLogo = true;
                Grid.setLogo(this);
            }
            if (this.isStart) {
                Grid.getStart().setIcon(null);
                Grid.setStart(null);
                this.isStart = false;
                this.isLogo = true;
                this.setIcon(logo);
                this.isWall[0] = true;
                this.isWall[1] = true;
                this.isWall[2] = true;
                this.isWall[3] = true;
                this.drawWall[0] = strokeSize;
                this.drawWall[1] = strokeSize;
                this.drawWall[2] = strokeSize;
                this.drawWall[3] = strokeSize;
                this.setBorder(new MatteBorder(drawWall[0], drawWall[1], drawWall[2], drawWall[3], Color.black));

            }
            if (this.isEnd) {
                Grid.getEnd().setIcon(null);
                this.isEnd = false;
                this.isStart = true;
                this.setIcon(logo);
                this.isWall[0] = true;
                this.isWall[1] = true;
                this.isWall[2] = true;
                this.isWall[3] = true;
                this.drawWall[0] = strokeSize;
                this.drawWall[1] = strokeSize;
                this.drawWall[2] = strokeSize;
                this.drawWall[3] = strokeSize;
                this.setBorder(new MatteBorder(drawWall[0], drawWall[1], drawWall[2], drawWall[3], Color.black));
                Grid.setEnd(null);

            }
        }
    }

    public void drawTopWall() {
        if (!isWall[0]) {
            drawWall[0] = strokeSize;
            isWall[0] = true;
        } else {
            drawWall[0] = 0;
            isWall[0] = false;
        }
        this.setBorder(new MatteBorder(drawWall[0], drawWall[1], drawWall[2], drawWall[3], Color.black));
    }

    public void drawLeftWall() {
            if (!isWall[1]) {
                drawWall[1] = strokeSize;
                isWall[1] = true;
            } else {
                drawWall[1] = 0;
                isWall[1] = false;
            }
            this.setBorder(new MatteBorder(drawWall[0], drawWall[1], drawWall[2], drawWall[3], Color.black));
    }

    public void drawDownWall() {
            if (!isWall[2]) {
                drawWall[2] = strokeSize;
                isWall[2] = true;
            } else {
                drawWall[2] = 0;
                isWall[2] = false;
            }
            this.setBorder(new MatteBorder(drawWall[0], drawWall[1], drawWall[2], drawWall[3], Color.black));
    }

    public void drawRightWall() {
//        if (rightWall.isSelected()) {
//            if (!isWall[3]) {
//                drawWall[3] = strokeSize;
//                isWall[3] = true;
//            } else {
//                drawWall[3] = 0;
//                isWall[3] = false;
//            }
//            this.setBorder(new MatteBorder(drawWall[0], drawWall[1], drawWall[2], drawWall[3], Color.black));
//        }
        if (!isWall[3]) {
            drawWall[3] = strokeSize;
            isWall[3] = true;
        } else {
            drawWall[3] = 0;
            isWall[3] = false;
        }
        this.setBorder(new MatteBorder(drawWall[0], drawWall[1], drawWall[2], drawWall[3], Color.black));
    }

    public void resetCell() {
        this.isWall[0] = false;
        this.isWall[1] = false;
        this.isWall[2] = false;
        this.isWall[3] = false;
        this.drawWall[0] = 0;
        this.drawWall[1] = 0;
        this.drawWall[2] = 0;
        this.drawWall[3] = 0;
        this.setBorder(null);

        if (this.isStart) {
            this.isStart = false;
            Grid.setStart(null);
        }

        if (this.isEnd) {
            this.isEnd = false;
            Grid.setEnd(null);
        }

        if (this.isLogo) {
            this.isLogo = false;
            Grid.setLogo(null);
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

    public int getRow() {
        return this.row;
    }

    public int getColumn() {
        return this.column;
    }
}
