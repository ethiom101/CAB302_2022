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
                    "PRIMARY KEY (`ID`) USING BTREE," +
                    "UNIQUE INDEX `ID` (`ID`) USING BTREE" +
                    ");";


    public static final String INSERT_MAZE = "INSERT INTO mazes " +
            "(Author, MazeName, Height, Width, DateCreated, DateLastEdited, MazeCells, StartImage, EndImage, LogoImage) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";

    public static final String UPDATE_MAZE = "UPDATE mazes SET " +
            "Author = ?, MazeName = ?, Height = ?, Width = ?, DateLastEdited = ?, MazeCells = ?, StartImage = ?, EndImage = ?, LogoImage = ?, " +
            "WHERE ID=?;";

    private static final String GET_IDs = "SELECT ID FROM mazes";
    private static final String GET_NAMES = "SELECT MazeName FROM mazes";
    private static final String GET_AUTHORS = "SELECT Author FROM mazes";
    private static final String GET_DATES_CREATED = "SELECT DateCreated FROM mazes";
    private static final String GET_DATES_EDITED = "SELECT DateLastEdited FROM mazes";

    public static final String GET_ID = "SELECT MAX(ID) FROM mazes";

    private static final String GET_MAZE = "SELECT * FROM mazes WHERE ID=?";
    private static final String GET_MAZE_NAME = "SELECT * FROM mazes WHERE MazeName=?";
    private static final String GET_MAZE_AUTHOR = "SELECT * FROM mazes WHERE Author=?";
    private static final String GET_MAZE_CREATED = "SELECT * FROM mazes WHERE DateCreated=?";
    private static final String GET_MAZE_EDITED = "SELECT * FROM mazes WHERE DateLastEdited=?";

    private static final String DELETE_MAZE = "DELETE FROM mazes WHERE ID=?";

    private final Connection connection;
    private PreparedStatement addMaze;
    private PreparedStatement updateMaze;
    private PreparedStatement getIDList;
    private PreparedStatement getNameList;
    private PreparedStatement getAuthorList;
    private PreparedStatement getCreatedList;
    private PreparedStatement getEditedList;
    private PreparedStatement getID;
    private PreparedStatement getMaze;
    private PreparedStatement getMazeName;
    private PreparedStatement getAuthor;
    private PreparedStatement getCreated;
    private PreparedStatement getEdited;
    private PreparedStatement deleteMaze;

    public MazeDataJDBC() {
        connection = Database.getInstance();
        try {
            Statement st = connection.createStatement();
            st.execute(CREATE_TABLE);
            addMaze = connection.prepareStatement(INSERT_MAZE);
            updateMaze = connection.prepareStatement(UPDATE_MAZE);
            getIDList = connection.prepareStatement(GET_IDs);
            getNameList = connection.prepareStatement(GET_NAMES);
            getAuthorList = connection.prepareStatement(GET_AUTHORS);
            getCreatedList = connection.prepareStatement(GET_DATES_CREATED);
            getEditedList = connection.prepareStatement(GET_DATES_EDITED);
            getID = connection.prepareStatement(GET_ID);
            getMaze = connection.prepareStatement(GET_MAZE);
            getMazeName = connection.prepareStatement(GET_MAZE_NAME);
            getAuthor = connection.prepareStatement(GET_MAZE_AUTHOR);
            getCreated = connection.prepareStatement(GET_MAZE_CREATED);
            getEdited = connection.prepareStatement(GET_MAZE_EDITED);
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
            addMaze.setBinaryStream(8, maze.getImage(1));
            addMaze.setBinaryStream(9, maze.getImage(2));
            if (maze.getImage(3) != null) {
                addMaze.setBinaryStream(10, maze.getImage(3));
            } else {
                addMaze.setBinaryStream(10, null);
            }
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
            updateMaze.setString(5, maze.getDateLastModified());
            updateMaze.setString(6, maze.getMazeCells());
            updateMaze.setBinaryStream(7, maze.getImage(1));
            updateMaze.setBinaryStream(8, maze.getImage(2));
            updateMaze.setBinaryStream(9, maze.getImage(3));
            updateMaze.setInt(10, maze.getID());
            updateMaze.execute();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

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
            maze.setImage(1, rs.getBinaryStream("StartImage"));
            maze.setImage(2, rs.getBinaryStream("EndImage"));
            maze.setImage(3, rs.getBinaryStream("LogoImage"));

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return maze;
    }

    public Maze getMazeName(String name) {
        Maze maze = new Maze();
        ResultSet rs;
        try {
            getMazeName.setString(1, name);
            rs = getMazeName.executeQuery();
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

    public Maze getMazeAuthor(String author) {
        Maze maze = new Maze();
        ResultSet rs;
        try {
            getAuthor.setString(1, author);
            rs = getAuthor.executeQuery();
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

    public Maze getMazeCreated(String dateCreated) {
        Maze maze = new Maze();
        ResultSet rs;
        try {
            getCreated.setString(1, dateCreated);
            rs = getCreated.executeQuery();
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

    public Maze getMazeEdited(String dateEdited) {
        Maze maze = new Maze();
        ResultSet rs;
        try {
            getEdited.setString(1, dateEdited);
            rs = getEdited.executeQuery();
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

    public Set<String> nameSet() {
        Set<String> names = new TreeSet<>();
        ResultSet rs;
        try {
            rs = getNameList.executeQuery();
            while (rs.next()) {
                names.add(rs.getString("MazeName"));
            }
        } catch (SQLException e) {
            return names;
        }
        return names;
    }

    public Set<String> authorSet() {
        Set<String> authors = new TreeSet<>();
        ResultSet rs;
        try {
            rs = getAuthorList.executeQuery();
            while (rs.next()) {
                authors.add(rs.getString("Author"));
            }
        } catch (SQLException e) {
            return authors;
        }
        return authors;
    }

    public Set<String> createdSet() {
        Set<String> datesCreated = new TreeSet<>();
        ResultSet rs;
        try {
            rs = getCreatedList.executeQuery();
            while (rs.next()) {
                datesCreated.add(rs.getString("DateCreated"));
            }
        } catch (SQLException e) {
            return datesCreated;
        }
        return datesCreated;
    }

    public Set<String> editedSet() {
        Set<String> datesEdited = new TreeSet<>();
        ResultSet rs;
        try {
            rs = getEditedList.executeQuery();
            while (rs.next()) {
                datesEdited.add(rs.getString("DateLastEdited"));
            }
        } catch (SQLException e) {
            return datesEdited;
        }
        return datesEdited;
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
