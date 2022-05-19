package Maze;

/**
 * Abstract class for Mazes that describes the essential data and communications
 * with other classes required to generate a maze.
 */
public abstract class Maze {
    private int numRow;
    private int numColumn;
    private CellOLD mazeStart;
    private CellOLD mazeEnd;
    private MazeGeneratorOLD generator;
    private MazeSolver solver;
    private String title;
    private String author;
    private String dateCreated;
    private String lastEdited;

    /**
     * Constructor for a Maze that initializes the components and details needed,
     * including title, author, and dates associated with that Maze, as well as
     * the number of rows and columns that will be used with the Maze's generator
     * and solver.
     *
     * @param numRow Maze's the number of rows
     * @param numColumn Maze's the number of columns
     * @param title the Maze's title
     * @param author the Maze's author/designer
     * @param dateCreated the Maze's date of creation
     * @param lastEdited date when the Maze was last edited
     */
    public Maze(int numRow, int numColumn, String title, String author, String dateCreated, String lastEdited) {
        this.numRow = numRow;
        this.numColumn = numColumn;
        this.generator = new MazeGeneratorOLD();
        this.solver = new MazeSolver();
        this.title = title;
        this.author = author;
        this.dateCreated = dateCreated;
        this.lastEdited = lastEdited;

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
     * toggleable action to see and remove a Maze's solution.
     */
    public abstract void toggleSolution();

    /**
     * Gets a mazes details including title, author, date created and last edited,
     * in the form of an array for ease of access.
     *
     * @return the maze's details in the form of an array
     */
    public abstract String[] getMazeDetails();
}