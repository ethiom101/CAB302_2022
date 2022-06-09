package Maze;

import GUI.EditMaze;
import org.testng.internal.collections.Pair;

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

    public static Pair<Integer, String> connectToDB() {
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
                        "'ID' int UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY," +
                        "'Author' varchar(256) NOT NULL KEY DEFAULT ''," +
                        "'MazeName' varchar(256) KEY," +
                        "'Height' int NOT NULL DEFAULT 0," +
                        "'Width' int NOT NULL DEFAULT 0," +
                        "'MazeData' BLOB)";
                stmt.executeUpdate(sql);
                stmt.executeUpdate("COMMIT");
            }
            System.out.println("Table 'Mazes' exists");

        } catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
            return new Pair<Integer, String>(-1, se.getMessage());
        } catch (LinkageError | ClassNotFoundException e) {
            //Handle errors for Class.forName
            e.printStackTrace();
            return new Pair<Integer, String>(-2, e.getMessage());
        }
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
                String sql = "INSERT INTO 'Mazes' (Author, MazeName, Height, Width, MazeData) VALUES(" +
                        String.format("'%s',", author) +
                        String.format("'%s',", mazeName) +
                        String.format("%d,", maze.getHeight()) +
                        String.format("%d,", maze.getWidth()) +
                        "?)";
                PreparedStatement statement = db.prepareStatement(sql);
                statement.setBinaryStream(1, maze.getBinaryGrid());
            }
            else{ // Otherwise, update the pre-existing entry
                String sql = "UPDATE 'Mazes" +
                        "SET Author = '" + author + "', MazeName = '" + mazeName +
                        String.format("', height = %d, width = %d, MazeData = ?", maze.getHeight(), maze.getWidth()) +
                        String.format("WHERE ID = %d", maze.getID());
                PreparedStatement statement = db.prepareStatement(sql);
                statement.setBinaryStream(1, maze.getBinaryGrid());
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
