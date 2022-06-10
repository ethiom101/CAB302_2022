package Util;

import Maze.Grid;

import java.sql.*;
import java.util.Objects;


public class Database {
    public static Connection db = null;
    private static Statement stmt = null;

    public static Pair<Integer, String> connectToDB() {
        // Currently hard coded, link to db.props
//        String JDBC_DRIVER = "org.mariadb.jdbc.Driver";
        String DB_URL = "jdbc:mariadb://localhost:3306";

        //  Database credentials
        String USER = "user";
        String PASS = "hdsajkhd";

        try {
            // Open a connection
            System.out.println("Connecting to server...");
            db = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = db.createStatement();
            System.out.println("Connected to server successfully!");

            System.out.println("Checking if database 'mazeco' exists...");
            String sql = "SHOW DATABASES";
            ResultSet res = stmt.executeQuery(sql);
            boolean exists = false;
            while(res.next()){
                String result = res.getString("Database");
                if (Objects.equals(result, "mazeco")){
                    exists = true;
                    System.out.println("Database 'mazeco' exists!");
                    break;
                }
            }
            if (!exists){
                System.out.println("Database 'mazeco' does not exist, initialising...");
                sql = "CREATE DATABASE `mazeco`";
                stmt.executeUpdate(sql);
                System.out.println("Done!");
            }
            sql = "USE mazeco";
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


    public static ResultSet queryDB(String query) {
        try {
            stmt = db.createStatement();
            String sql = new String();
            if (Objects.equals(query, "")){
                sql = "SELECT * FROM Mazes";
            }
            else {
                sql = query;
            }
            return stmt.executeQuery(sql);
        } catch (SQLException | NullPointerException e) {
            e.printStackTrace();
        }
        return null; // If we've gotten here something's gone wrong with the DB connection
    }
}
