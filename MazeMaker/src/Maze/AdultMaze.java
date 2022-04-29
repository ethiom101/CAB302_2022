package Maze;

/**
 * Non-limited maze design with difficulties ranging from easy to advanced. Intended
 * for more mature audiences. Starting and end points are indicated by arrows with
 * openings in the wall.
 */
public class AdultMaze extends Maze {


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
    public AdultMaze(int numRow, int numColumn, String title, String author, String dateCreated, String lastEdited) {
        super(numRow, numColumn, title, author, dateCreated, lastEdited);
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
    public void toggleSolution() {
        //...TODO
    }

    @Override
    public String[] getMazeDetails() {
        //...TODO
        return new String[0];
    }

    /**
     * Sets the start indication of a maze to an arrow pointing inwards into the
     * maze from a gap somewhere in the outer wall
     *
     * @param start the starting cell of a maze
     */
    public void setStart(Objective start) {
        //...TODO
    }

    /**
     * Sets the end indication of a maze to an arrow pointing outwards towards a
     * gap somewhere on the maze's outer wall
     *
     * @param end the ending cell of a maze
     */
    public void setEnd(Objective end) {
        //...TODO
    }
}

