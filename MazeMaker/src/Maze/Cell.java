package Maze;

/**
 * A form of a unit used quantify the inner workings of a maze.
 * Each row and column of a maze consists of a single cell. The main
 * cell of a maze is a wall, but cells can also be occupied by logos that can
 * also act as walls. For children's mazes the cells at the start and end of a
 * maze are indicated by images, while adult mazes are indicated by arrows.
 */
public abstract class Cell {
    private int size;
    private int row;
    private int column;
    private boolean wall;

    /**
     * Constructs a cell that will be used in correspondence with an algorithm
     * to generate a full maze of these cells.
     *
     * @param size   the size of all cells
     * @param row    which row(s) the cell occupies
     * @param column which column(s) the cell occupies
     * @param isWall states whether the cell contains a wall
     */
    public Cell(int size, int row, int column, boolean isWall) {
        this.size = size;
        this.row = row;
        this.column = column;
        this.wall = isWall;
    }

    /**
     * The size of cells occupying the maze changes based on the size of the maze.
     * Smaller mazes will have larger cell sizes ("occupy a larger size per cell").
     *
     * @param size size that all cells occupying a maze will be
     */
    public abstract void setSize(int size);

    /**
     * Gets the maze's cell size.
     *
     * @return size of cells
     */
    public abstract int getSize();

    /**
     * Gets the corresponding row that the cell is in.
     *
     * @return the row where the cell is
     */
    public abstract int getRow();

    /**
     * Gets the corresponding column that the cell is in.
     *
     * @return the column where the cell is
     */
    public abstract int getColumn();

    /**
     * Place a cell into the maze.
     *
     * @param row    which row the cell will be placed
     * @param column which column the cell will be placed
     */
    public abstract void placeCell(int row, int column);

    /**
     * Deletes a cell fom the maze.
     *
     * @param row    which row the cell will be deleted
     * @param column which column the cell will be deleted
     */
    public abstract void deleteCell(int row, int column);

    /**
     * Move a cell in the maze.
     *
     * @param row    which row the cell will be moved to
     * @param column which column the cell will be moved to
     */
    public abstract void moveCell(int row, int column);
}
