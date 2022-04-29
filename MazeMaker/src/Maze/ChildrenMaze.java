package Maze;

/**
 * An overall easier maze design for a younger audience with a few additions.
 * Mazes designed for a younger audience will differ from those directed at
 * more mature audiences, with the use of images to indicate the mazes start
 * and end, as well as overall easier mazes with fewer rows and columns to
 * help to design better mazes for this audience.
 */
public class ChildrenMaze extends Maze {

    private Objective startImage;
    private Objective endImage;

    /**
     * Constructor for a Maze that initializes the components and details needed,
     * including title, author, and dates associated with that Maze, as well as
     * the number of rows and columns that will be used with the Maze's generator
     * and solver.
     *
     * @param numRow      Maze's the number of rows
     * @param numColumn   Maze's the number of columns
     * @param title       the Maze's title
     * @param author      the Maze's author/designer
     * @param dateCreated the Maze's date of creation
     * @param lastEdited  date when the Maze was last edited
     */
    public ChildrenMaze(int numRow, int numColumn, String title, String author, String dateCreated, String lastEdited) {
        super(numRow, numColumn, title, author, dateCreated, lastEdited);
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
    public void toggleSolution() {
        //...TODO
    }

    @Override
    public String[] getMazeDetails() {
        //...TODO
        return new String[0];
    }

    /**
     * Sets the start indication of a maze to the appearance of a chosen image,
     * oppose to an opening in the maze.
     *
     * @param start the starting cell of a maze
     */
    public void setStart(Objective start) {
        //...TODO
    }

    /**
     * Sets the end indication of a maze to the appearance of a chosen image,
     * oppose to an opening in the maze.
     *
     * @param end the ending cell of a maze
     */
    public void setEnd(Objective end) {
        //...TODO
    }
}
