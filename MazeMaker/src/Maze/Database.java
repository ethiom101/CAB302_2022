package Maze;

import java.util.List;

/**
 * Handles data functionality used by the application.
 */
public interface Database {

    /**
     * Retrieves all mazes in the database that have been uploaded to the
     * database to be browsed
     * through.
     *
     * @return a list of mazes
     */
    List<Maze> getMaze();

    /**
     * Sorts mazes based on title, author, date created or last edited.
     */
    void sortMaze();

    /**
     * Loads a maze from the database to be viewed or edited.
     */
    void loadMaze();

    /**
     * Saves a maze that is being edited to the database.
     */
    void saveMaze();

    /**
     * Deletes a maze from the database
     */
    void deleteMaze();

    /**
     * Exports a selection of maze(s) form the database as image files with and
     * without their solutions.
     */
    public void exportMaze();
}
