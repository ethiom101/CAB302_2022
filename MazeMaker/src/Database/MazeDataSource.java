package Database;

import Maze.Maze;

import java.util.Set;

public interface MazeDataSource {

    void addMaze(Maze maze);

    Maze getMaze(int ID);

    void deleteMaze(int ID);

    void close();

    Set<Integer> idSet();
}
