package Util;

public interface MazeDataSource {

    void addMaze();

    void getMaze();

    int getSize();

    int deleteMaze();

    void close();
}
