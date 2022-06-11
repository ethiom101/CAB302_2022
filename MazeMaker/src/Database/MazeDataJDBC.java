package Database;

import Maze.Maze;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Set;
import java.util.TreeSet;

/**
 * CLASS MODIFIED FROM CAB302 LECTURE 6
 * Class for retrieving data from the XML file holding the maze's list.
 */
public class MazeDataJDBC implements MazeDataSource {

    public static final String CREATE_TABLE =
            "CREATE TABLE IF NOT EXISTS `mazes` (" +
                    "`ID` INT(10) NOT NULL AUTO_INCREMENT," +
	                "`Author` VARCHAR(50) NOT NULL," +
	                "`MazeName` VARCHAR(50) NOT NULL," +
	                "`Height` INT(10) NOT NULL," +
	                "`Width` INT(10) NOT NULL," +
	                "`DateCreated` VARCHAR(50) NOT NULL," +
	                "`DateLastEdited` VARCHAR(50) NOT NULL," +
	                "`MazeCells` MEDIUMTEXT NOT NULL," +
                    "`StartImage` MEDIUMBLOB NULL DEFAULT NULL," +
	                "`EndImage` MEDIUMBLOB NULL DEFAULT NULL," +
	                "`LogoImage` MEDIUMBLOB NULL DEFAULT NULL," +
	                "`MazePicture` MEDIUMBLOB NULL DEFAULT NULL," +
                    "PRIMARY KEY (`ID`) USING BTREE," +
                    "UNIQUE INDEX `ID` (`ID`) USING BTREE" +
                    ");";


    public static final String INSERT_MAZE = "INSERT INTO mazes " +
            "(Author, MazeName, Height, Width, DateCreated, DateLastEdited, MazeCells, StartImage, EndImage, LogoImage, MazePicture) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";

    public static final String UPDATE_MAZE = "UPDATE mazes SET " +
            "Author = ?, MazeName = ?, Height = ?, Width = ?, DateCreated = ?, DateLastEdited = ?, MazeCells = ?, StartImage = ?, EndImage = ?, LogoImage = ?, MazePicture = ? " +
            "WHERE ID=?;";

    private static final String GET_IDs = "SELECT ID FROM mazes";

    public static final String GET_ID = "SELECT MAX(ID) FROM mazes";

    private static final String GET_MAZE = "SELECT * FROM mazes WHERE ID=?";

    private static final String DELETE_MAZE = "DELETE FROM mazes WHERE ID=?";

    private final Connection connection;
    private PreparedStatement addMaze;
    private PreparedStatement updateMaze;
    private PreparedStatement getIDList;
    private PreparedStatement getID;
    private PreparedStatement getMaze;
    private PreparedStatement deleteMaze;

    public MazeDataJDBC() {
        connection = Database.getInstance();
        try {
            Statement st = connection.createStatement();
            st.execute(CREATE_TABLE);
            addMaze = connection.prepareStatement(INSERT_MAZE);
            updateMaze = connection.prepareStatement(UPDATE_MAZE);
            getIDList = connection.prepareStatement(GET_IDs);
            getID = connection.prepareStatement(GET_ID);
            getMaze = connection.prepareStatement(GET_MAZE);
            deleteMaze = connection.prepareStatement(DELETE_MAZE);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // TODO add the currently uploaded images of the maze to the database (do last)
    @Override
    public void addMaze(Maze maze) {
        try {
            addMaze.setString(1, maze.getAuthor());
            addMaze.setString(2, maze.getName());
            addMaze.setString(3, String.valueOf(maze.getHeight()));
            addMaze.setString(4, String.valueOf(maze.getWidth()));
            addMaze.setString(5, maze.getDateCreated());
            addMaze.setString(6, maze.getDateLastModified());
            addMaze.setString(7, maze.getMazeCells());
            addMaze.setString(8, null);
            addMaze.setString(9, null);
            addMaze.setString(10, null);
            addMaze.setString(11, null);
            addMaze.execute();
            // Gets the latest ID from the database
            ResultSet rs;
            try {
                rs = getID.executeQuery();
                rs.next();
                maze.setID(rs.getInt("MAX(ID)"));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void updateMaze(Maze maze) {
        try {
            updateMaze.setString(1, maze.getAuthor());
            updateMaze.setString(2, maze.getName());
            updateMaze.setString(3, String.valueOf(maze.getHeight()));
            updateMaze.setString(4, String.valueOf(maze.getWidth()));
            updateMaze.setString(5, maze.getDateCreated());
            updateMaze.setString(6, maze.getDateLastModified());
            updateMaze.setString(7, maze.getMazeCells());
            updateMaze.setString(8, null);
            updateMaze.setString(9, null);
            updateMaze.setString(10, null);
            updateMaze.setString(11, null);
            updateMaze.setInt(12, maze.getID());
            updateMaze.execute();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    // TODO continue updating this as you add more stuff that is saved to the data base
    @Override
    public Maze getMaze(int ID) {
        Maze maze = new Maze();
        ResultSet rs;
        try {
            getMaze.setInt(1, ID);
            rs = getMaze.executeQuery();
            rs.next();
            maze.setAuthor(rs.getString("Author"));
            maze.setName(rs.getString("MazeName"));
            maze.setHeight(rs.getInt("Height"));
            maze.setWidth(rs.getInt("Width"));
            maze.setDateCreated(rs.getString("DateCreated"));
            maze.setDateLastModified(rs.getString("DateLastEdited"));
            maze.setMazeCells(rs.getString("MazeCells"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return maze;
    }

    @Override
    public Set<Integer> idSet() {
        Set<Integer> IDs = new TreeSet<>();
        ResultSet rs;
        try {
            rs = getIDList.executeQuery();
            while (rs.next()) {
                IDs.add(rs.getInt("ID"));
            }
        } catch (SQLException e) {
            return IDs;
        }
        return IDs;
    }

    @Override
    public void deleteMaze(int ID) {
        try {
            deleteMaze.setInt(1, ID);
            deleteMaze.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
