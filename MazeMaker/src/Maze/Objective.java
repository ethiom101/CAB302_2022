package Maze;

/**
 * Cell type used to indicate the start and end points of a Maze. These cells
 * are never walls.
 */
public class Objective extends Cell {
        private String imagePath;

    /**
     * Constructs an objective cell that will be used in correspondence with an algorithm
     * to indicate the start and end of a Maze.
     *
     * @param size   the size of all cells
     * @param row    which row(s) the cell occupies
     * @param column which column(s) the cell occupies
     * @param isWall states whether the cell is a wall
     */
    public Objective(int size, int row, int column, boolean isWall) {
        super(size, row, column, isWall);
    }


    @Override
    public void setSize(int size) {
        //...TODO
    }

    @Override
    public int getSize() {
        //...TODO
        return 0;
    }

    @Override
    public int getRow() {
        //...TODO
        return 0;
    }

    @Override
    public int getColumn() {
        //...TODO
        return 0;
    }

    @Override
    public void placeCell(int row, int column) {
        //...TODO
    }

    @Override
    public void deleteCell(int row, int column) {
        //...TODO
    }

    @Override
    public void moveCell(int row, int column) {
        //...TODO
    }

    /**
     * Upload images to be used for the start and end of a maze.
     */
    public void uploadImage() {
        //...TODO
    }
}
