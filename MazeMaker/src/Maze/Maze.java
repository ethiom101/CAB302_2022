package Maze;

/**
 * Stores essential maze data and communicates with other classes.
 */
public abstract class Maze {
    private int numRow;
    private int numColumn;
    protected Cell mazeStart;
    protected Cell mazeEnd;
    private MazeGenerator generator;
    private MazeSolver solver;
    private String[] mazeDetails;

    /**
     * When constructed a maze needs to have a specified size,
     * with the maximum size being 100x100 (100 rows and 100 columns).
     * @param numRow The number of rows the maze will have
     * @param numColumn The number of columns the maze will have
     */
    public Maze(int numRow, int numColumn) {
        this.numRow = numRow;
        this.numColumn = numColumn;
        this.generator = new MazeGenerator();
        this.solver = new MazeSolver();
        this.mazeDetails = getMazeDetails();
    }

    /**
     * Takes a generated maze's data and creates a visual representation of maze.
     */
    public abstract void drawMaze();

    /**
     * Takes a generated maze's solution and creates a visual representation of
     * the most optimal path from the start point to the end point.
     */
    public abstract void drawSolution();

    /**
     * Reset a maze to a blank state.
     */
    public abstract void clearMaze();

    /**
     * Remove to solution from the screen.
     */
    public abstract void clearSolution();

    /**
     * Sets the mazes details including author, title, date created last edited
     * and whether the maze is complete and ready to be exported.
     * @return the maze's details
     */
    public abstract String[] setMazeDetails();

    /**
     * Gets a mazes details.
     * @return the maze's details
     */
    public abstract String[] getMazeDetails();
}