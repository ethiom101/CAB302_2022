package Maze;

import GUI.EditMaze;

import javax.swing.*;
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
            for (int i = 0; i < grid.length; i++) {
                for (int j = 0; j < grid[i].length; j++) {
                    if (grid[i][j].isStart()) {
                        outputWriter.write("1");
                    } else if (grid[i][j].isEnd()) {
                        outputWriter.write("2");
                    } else if (grid[i][j].isLogo()) {
                        outputWriter.write("3");
                    } else {
                        outputWriter.write("0");
                    }

                    if (grid[i][j].isTopWall()) {
                        outputWriter.write("4");
                    }
                    if (grid[i][j].isLeftWall()) {
                        outputWriter.write("5");
                    }
                    if (grid[i][j].isDownWall()) {
                        outputWriter.write("6");
                    }
                    if (grid[i][j].isRightWall()) {
                        outputWriter.write("7");
                    }
                }
                outputWriter.newLine();
            }
            outputWriter.flush();
            outputWriter.close();
        }
    }

    public static void openMaze() throws IOException {
        JFileChooser fileChooser = new JFileChooser();
        int option = fileChooser.showOpenDialog(null);
        if (option == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            BufferedReader reader = new BufferedReader(new FileReader(file.getAbsolutePath()));
            String line = null;
            new EditMaze();
            for (int i = 0; i < 10; i++) {
                line = reader.readLine();
                for (int j = 0; j < 10; j++) {

                    //nodeList[i][j].setColor(Color.BLACK);
                    int nodeType = Character.getNumericValue(line.charAt(j));
                    System.out.println("node is " + nodeType);
                    switch (nodeType) {
                        case 0:
                            grid[i][j].resetCell();
                            break;
                        case 1:
                            grid[i][j].drawStart(Cell.start);
                            break;

                        case 2:
                            grid[i][j].drawEnd(Cell.end);
                            break;
                        case 3:
                            grid[i][j].drawLogo(Cell.logo);
                            break;
                        case 4:
                            grid[i][j].drawTopWall();
                            break;
                        case 5:
                            grid[i][j].drawLeftWall();
                            break;
                        case 6:
                            grid[i][j].drawDownWall();
                            break;
                        case 7:
                            grid[i][j].drawRightWall();
                            break;
                    }
                }
            }
            reader.close();

        }
    }
}
