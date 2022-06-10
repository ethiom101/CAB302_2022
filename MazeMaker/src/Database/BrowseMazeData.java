package Database;

import Maze.Maze;

import javax.swing.*;

import java.util.Set;

public class BrowseMazeData {

    private final DefaultListModel<Integer> listModel;
    private final MazeDataJDBC mazeData;

    public BrowseMazeData() {
        listModel = new DefaultListModel<>();
        mazeData = new MazeDataJDBC();

        for (int ID : mazeData.idSet()) {
            System.out.println(ID);
            listModel.addElement(ID);
        }
    }

    public void add(Maze maze) {
        if (!listModel.contains(maze.getID())) {
            listModel.addElement((maze.getID()));
            mazeData.addMaze(maze);
        }
    }

    public void remove(Object key) {
        listModel.removeElement(key);
        mazeData.deleteMaze((Integer) key);
    }

    public void persist() {
        mazeData.close();
    }

    public Maze get(Object key) {
        return mazeData.getMaze((Integer) key);
    }

    public Set<Integer> getIDs() {
        return mazeData.idSet();
    }
}
