package Maze;

/**
 * Non-limited maze design with difficulties ranging from easy to advanced.
 */
public class AdultMaze extends Maze {

    /**
     * Constructs mazes with starting and end points indicated by an opening in the maze,
     * this is the default maze design.
     *
     * @param numRow    The number of rows the maze will have
     * @param numColumn The number of rows the columns will have
     */
    public AdultMaze(int numRow, int numColumn) {
        super(numRow, numColumn);
    }

    @Override
    public void drawMaze() {
        //...TODO
    }

    @Override
    public void drawSolution() {
        //...TODO
    }

    @Override
    public void clearMaze() {
        //...TODO
    }

    @Override
    public void clearSolution() {
        //...TODO
    }

    @Override
    public String[] setMazeDetails() {
        //...TODO
        return new String[0];
    }

    @Override
    public String[] getMazeDetails() {
        //...TODO
        return new String[0];
    }
}
