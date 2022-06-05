package Maze;

import GUI.EditMaze;

import javax.swing.*;
import java.io.*;
import java.sql.*;

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

    public static int connectToDB() {
        // Currently hard coded, link to db.props
        String JDBC_DRIVER = "org.mariadb.jdbc.Driver";
        String DB_URL = "jdbc:mariadb://localhost:3306";

        //  Database credentials
        String USER = "user";
        String PASS = "hdsajkhd";

        try {
            // Register JDBC driver
            Class.forName(JDBC_DRIVER);

            // Open a connection
            System.out.println("Connecting to a selected database...");
            db = DriverManager.getConnection(
                    DB_URL, USER, PASS);
            System.out.println("Connected database successfully...");

            System.out.println("Checking if table 'Mazes' exists");
            String sql = "SELECT EXISTS (" +
                    "SELECT " +
                    "   TABLE_NAME " +
                    "FROM " +
                    "   information_schema.TABLES" +
                    "WHERE" +
                    "   TABLE_NAME = 'Mazes');";

            if (!stmt.execute(sql)) {
                System.out.println("Table 'Mazes' does not exist, initialising");
                sql = "CREATE TABLE 'Mazes'(" +
                        "'ID' int NOT NULL AUTO_INCREMENT PRIMARY KEY," +
                        "'Author' varchar(256) NOT NULL KEY DEFAULT ''," +
                        "'Maze Name' varchar(256) KEY," +
                        "'Height' int NOT NULL DEFAULT 0," +
                        "'Width' int NOT NULL DEFAULT 0," +
                        "'Maze Data' BLOB)";
                stmt.executeUpdate(sql);
            }
            System.out.println("Table 'Mazes' exists");

        } catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
            return 0;
        } catch (LinkageError | ClassNotFoundException e) {
            //Handle errors for Class.forName
            e.printStackTrace();
            return 0;
        }
        return 1;
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

    public static void queryDB(String query) {
        try {
            stmt = db.createStatement();
        } catch (SQLException e) {
            // TODO - Implement exception handling
        }
    }

}
