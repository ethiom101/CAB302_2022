package Util;

import GUI.EditMaze;
import Maze.Cell;
import Maze.Grid;
import org.testng.internal.collections.Pair;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.io.*;
import java.sql.*;
import java.util.Objects;

import static Maze.Grid.grid;


public class MazeFile {

    public static Connection db = null;
    private static Statement stmt = null;


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
        if ((option == JFileChooser.APPROVE_OPTION && (file.getAbsolutePath().endsWith(".maze")))) {
            BufferedReader reader = new BufferedReader(new FileReader(file.getAbsolutePath()));
            String line;
            new EditMaze();
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
        } else {
            JOptionPane.showMessageDialog(null, file + "' is not a .maze file", "Invalid File Type'", JOptionPane.WARNING_MESSAGE);
            return false;
        }
    }

    public static Pair<Integer, String> connectToDB() {
        // Currently hard coded, link to db.props
//        String JDBC_DRIVER = "org.mariadb.jdbc.Driver";
        String DB_URL = "jdbc:mariadb://localhost:3306";

        //  Database credentials
        String USER = "root";
        String PASS = "root";

        try {
            // Register JDBC driver
//            Class.forName(JDBC_DRIVER);

            // Open a connection
            System.out.println("Connecting to server...");
            db = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = db.createStatement();
            System.out.println("Connected to server successfully!");

            System.out.println("Checking if database 'mazedb' exists...");
            String sql = "SHOW DATABASES";
            ResultSet res = stmt.executeQuery(sql);
            boolean exists = false;
            while(res.next()){
                String result = res.getString("Database");
                if (Objects.equals(result, "mazedb")){
                    exists = true;
                    System.out.println("Database 'mazedb' exists!");
                    break;
                }
            }
            if (!exists){
                System.out.println("Database 'mazedb' does not exist, initialising...");
                sql = "CREATE DATABASE `mazedb`";
                stmt.executeUpdate(sql);
                System.out.println("Done!");
            }
            sql = "USE mazedb";
            stmt.executeUpdate(sql);

            System.out.println("Checking if table 'Mazes' exists...");
            sql = "SELECT EXISTS (SELECT TABLE_NAME FROM information_schema.TABLES WHERE TABLE_NAME = 'Mazes');";
            res = stmt.executeQuery(sql);
            res.last();
            if (!res.getBoolean(1)) {
                res.close();
                System.out.println("Table 'Mazes' does not exist, initialising");
                sql = "CREATE TABLE `Mazes`(" +
                        "`ID` int UNSIGNED NOT NULL AUTO_INCREMENT," +
                        "`Author` varchar(256) NOT NULL DEFAULT ''," +
                        "`MazeName` varchar(256)," +
                        "`Height` int NOT NULL DEFAULT 0," +
                        "`Width` int NOT NULL DEFAULT 0," +
                        "`MazeData` BLOB," +
                        "`CreationTimeStamp` TIMESTAMP NOT NULL DEFAULT (CURRENT_TIMESTAMP)," +
                        "`LastModifiedTimestamp` TIMESTAMP NOT NULL DEFAULT (CURRENT_TIMESTAMP)," +
                        "PRIMARY KEY (ID))";
                stmt.executeUpdate(sql);
                stmt.executeUpdate("COMMIT");
            }
            System.out.println("Table 'Mazes' exists");

        } catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
            return new Pair<Integer, String>(-1, se.getMessage());
        }
//        catch (LinkageError | ClassNotFoundException e) {
//            //Handle errors for Class.forName
//            e.printStackTrace();
//            return new Pair<Integer, String>(-2, e.getMessage());
//        }
        return new Pair<Integer, String>(0, "");
    }

    public static void disconnectFromDB() {
        try {
            if (stmt != null) {
                db.close();
            }
        } catch (SQLException se) {
        }// do nothing
        try {
            if (db != null) {
                db.close();
            }
        } catch (SQLException se) {
            se.printStackTrace();
        }
        System.out.println("Disconnected from database");
    }

    // Overloaded function since maze name is optional
    public static int insertIntoDB(Grid maze, String author) {
        return insertIntoDB(maze, author, "");
    }

    public static int insertIntoDB(Grid maze, String author, String mazeName) {
        try {
            // If we know the maze was not loaded from the database, insert as a new maze
            if (maze.getID() < 0) {
                String sql = "INSERT INTO `Mazes` (Author, MazeName, Height, Width, MazeData) VALUES(" +
                        String.format("'%s',", author) +
                        String.format("'%s',", mazeName) +
                        String.format("%d,", maze.getHeight()) +
                        String.format("%d,", maze.getWidth()) +
                        "?)";
                PreparedStatement statement = db.prepareStatement(sql);
                statement.setBinaryStream(1, maze.getBinaryGrid());
                statement.executeUpdate();
            }
            else{ // Otherwise, update the pre-existing entry
                String sql = "UPDATE Mazes" +
                        "SET Author = '" + author + "', MazeName = '" + mazeName +
                        String.format("', height = %d, width = %d, MazeData = ?, ", maze.getHeight(), maze.getWidth()) +
                        "LastModifiedTimestamp = CURRENT_TIMESTAMP, " +
                        String.format("WHERE ID = %d", maze.getID());
                PreparedStatement statement = db.prepareStatement(sql);
                statement.setBinaryStream(1, maze.getBinaryGrid());
                statement.executeUpdate();
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
        return 0;
    }


    public static void queryDB(String query) {
        try {
            stmt = db.createStatement();
        } catch (SQLException e) {
            // TODO - Implement exception handling
        }
    }

    }
