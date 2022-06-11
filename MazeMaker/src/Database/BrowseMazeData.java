package Database;

import Maze.Maze;

import javax.swing.*;

import java.util.Set;

/**
 * CLASS MODIFIED FROM CAB302 LECTURE 6
 * This version uses an MazeDataSource and its methods to retrieve data
 */
public class BrowseMazeData {

    private final DefaultListModel<Integer> listModel;
    private final MazeDataJDBC mazeData;

    /**
     * Constructor initializes the list model that holds names as Strings and
     * attempts to read any data saved from previous invocations of the
     * application.
     *
     */
    public BrowseMazeData() {
        listModel = new DefaultListModel<>();
        mazeData = new MazeDataJDBC();

        for (int ID : mazeData.idSet()) {
            System.out.println(ID);
            listModel.addElement(ID);
        }
    }

    /**
     * Adds a maze to the list of mazes.
     *
     * @param maze A maze to add to the list of mazes.
     */
    public void add(Maze maze) {
        if (!listModel.contains(maze.getID())) {
            listModel.addElement((maze.getID()));
            mazeData.addMaze(maze);
        }
    }

    public void update(Maze maze) {
        mazeData.updateMaze(maze);
    }

    /**
     * Based on the ID of a Maze in the maze list, delete the Maze.
     *
     * @param key the ID object of a maze
     */
    public void remove(Object key) {
        listModel.removeElement(key);
        mazeData.deleteMaze((Integer) key);
    }

    /**
     * Saves the data in the maze list using a persistence mechanism.
     */
    public void persist() {
        mazeData.close();
    }

    /**
     * Retrieves Maze details from the model.
     *
     * @param key the ID to retrieve.
     * @return the Maze object related to the ID.
     */
    public Maze get(Object key) {
        return mazeData.getMaze((Integer) key);
    }

    /**
     * @return the number of mazes in the maze list.
     */
    public Set<Integer> getIDs() {
        return mazeData.idSet();
    }
}
