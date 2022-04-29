package Maze;

/**
 * Cell type that is not able to be passed through, also takes up majority of
 * a Maze as a maze is made up of Walls.
 */
public class Wall extends Cell {


    /**
     * Constructs a cell that will be used in correspondence with an algorithm
     * to generate a full maze of these cells.
     *
     * @param size   the size of all cells
     * @param row    which row(s) the cell occupies
     * @param column which column(s) the cell occupies
     * @param isWall states whether the cell contains a wall
     */
    public Wall(int size, int row, int column, boolean isWall) {
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
     * Rotates a Wall when editing a maze.
     */
    public void rotateWall() {
        //...TODO
    }
}
