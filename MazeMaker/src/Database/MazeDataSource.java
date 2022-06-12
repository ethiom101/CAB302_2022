package Database;

import Maze.Maze;

import java.util.Set;

// CLASS MODIFIED FROM CAB302 LECTURE 6
/**
 * Provides functionality needed by any data source for the Maze Maker application
 */
public interface MazeDataSource {

    /**
     * Adds a Maze to the database
     *
     * @param maze maze to add
     */
    void addMaze(Maze maze);

    /**
     * Updates an existing mazes data in the databse
     *
     * @param maze maze to update
     */
    void updateMaze(Maze maze);

    /**
     * Extracts all the details of a Maze from the database based on the ID passed.
     *
     * @param ID The ID as an Integer to search for.
     * @return all details in a Maze object for the ID
     */
    Maze getMaze(int ID);

    /**
     * Deletes a Maze from the database based on the ID passed
     *
     * @param ID The name to delete from the address book.
     */
    void deleteMaze(int ID);


    /**
     * Finalizes any resources used by the data source and ensures data is persisted.
     */
    void close();

    /**
     * Retrieves a set of IDs from the data source that are used to browse mazes
     *
     * @return set of IDs.
     */
    Set<Integer> idSet();
}
