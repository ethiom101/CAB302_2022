package Util;

import GUI.EditMaze;
import GUI.HomePage;
import Maze.Cell;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.io.*;

import static Maze.Grid.grid;


public class MazeFile {


    public static void saveMaze() throws IOException {
        JFileChooser fileChooser = new JFileChooser();
        int response = fileChooser.showSaveDialog(null);
        if (response == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            String path = file.getAbsolutePath().endsWith(".maze") ? "" : ".maze";
            BufferedWriter outputWriter = new BufferedWriter(new FileWriter(file.getAbsolutePath() + path));
            for (int i = 0; i < 10; i++) {
                for (int j = 0; j < 10; j++) {
                    if (grid[j][i].isStart()) {
                        outputWriter.write("1");
                        outputWriter.newLine();
                    } else if (grid[j][i].isEnd()) {
                        outputWriter.write("2");
                        outputWriter.newLine();
                    } else if (grid[j][i].isLogo()) {
                        outputWriter.write("3");
                        outputWriter.newLine();
                    }

                    if (grid[j][i].isTopWall()) {
                        outputWriter.write("4");
                        outputWriter.newLine();
                    }
                    if (grid[j][i].isLeftWall()) {
                        outputWriter.write("5");
                        outputWriter.newLine();
                    }
                    if (grid[j][i].isDownWall()) {
                        outputWriter.write("6");
                        outputWriter.newLine();
                    }
                    if (grid[j][i].isRightWall()) {
                        outputWriter.write("7");
                        outputWriter.newLine();
                    }
                    outputWriter.write("0");
                    outputWriter.newLine();
                }
            }
            outputWriter.flush();
            outputWriter.close();
        }
    }

    public static boolean openMaze() throws IOException {
        JFileChooser fileChooser = new JFileChooser();
        int option = fileChooser.showOpenDialog(null);
        File file = fileChooser.getSelectedFile();
        if (option == JFileChooser.APPROVE_OPTION && (file.getAbsolutePath().endsWith(".maze"))) {
            BufferedReader reader = new BufferedReader(new FileReader(file.getAbsolutePath()));
            String line;
            HomePage.mazeEditor = new EditMaze();
            for (int i = 0; i < 10; i++) {
                System.out.println("here");
                for (int j = 0; j < 10; j++) {
                    line = reader.readLine();
                    int cellType = Character.getNumericValue(line.charAt(0));
                    System.out.println("node is " + cellType);
                    if (cellType == 1) {
                        grid[j][i].drawStart(Cell.start);
                        line = reader.readLine();
                        cellType = Character.getNumericValue(line.charAt(0));
                        System.out.println("node is " + cellType);
                    } else if (cellType == 2) {
                        grid[j][i].drawEnd(Cell.end);
                        line = reader.readLine();
                        cellType = Character.getNumericValue(line.charAt(0));
                        System.out.println("node is " + cellType);
                    } else if (cellType == 3) {
                        grid[j][i].drawLogo(Cell.logo);
                        line = reader.readLine();
                        cellType = Character.getNumericValue(line.charAt(0));
                        System.out.println("node is " + cellType);


                    }
                    if (cellType == 4) { // top wall
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
                        line = reader.readLine();
                        cellType = Character.getNumericValue(line.charAt(0));
                        System.out.println("node is " + cellType);
                    }

                    if (cellType == 5) { // left wall
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
                        line = reader.readLine();
                        cellType = Character.getNumericValue(line.charAt(0));
                        System.out.println("node is " + cellType);
                    }
//
                    if (cellType == 6) { // down wall
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
                        line = reader.readLine();
                        cellType = Character.getNumericValue(line.charAt(0));
                        System.out.println("node is " + cellType);
                    }
//
                    if (cellType == 7) { // right wall
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
                        line = reader.readLine();
                        cellType = Character.getNumericValue(line.charAt(0));
                        System.out.println("node is " + cellType);
                    }
                }
            }
            reader.close();
            return true;
        } else if (option == JFileChooser.APPROVE_OPTION) {
            JOptionPane.showMessageDialog(null, "'" + file + "' is not a .maze file", "Invalid File Type", JOptionPane.WARNING_MESSAGE);
            return false;
        } else {
            return false;
        }
    }
}
