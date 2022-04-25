package Maze;

/**
 * An overall easier maze design for a younger audience with a few additions.
 */
public class ChildrenMaze extends Maze {

    private Objective startImage;
    private Objective endImage;
    /**
     * Mazes designed for a younger audience will differ from those directed at
     * an older audience. The use of images to indicate the mazes start and end,
     * as well as overall easier mazes with fewer rows and columns will help to
     * design better mazes for this audience.
     *
     * @param numRow the number of rows the maze will have
     * @param numColumn the number of columns the maze will have
     */
    public ChildrenMaze(int numRow, int numColumn) {
        super(numRow, numColumn);
    }

    @Override
    public void drawMaze() {
        // ...TODO
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

    /**
     * Gives the start and end's of a maze an appearance in the form of chosen
     * images, oppose to an opening in the maze.
     */
    public void placeImages() {
        //...TODO
    }
}
