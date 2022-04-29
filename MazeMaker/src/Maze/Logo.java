package Maze;

/**
 * A type of wall that always has walls on every side of the cell, in
 * other words is fully encased by walls. A logo takes the appearance of an
 * image that can be placed in the maze and occupy larger or smaller areas than
 * other cells.
 */
public class Logo extends Wall {
    private String imagePath;

    /**
     * Constructs a logo that will be used in correspondence with an algorithm
     * to place one of these cells in the maze that can be removed or added later.
     *
     * @param size   the size of all cells
     * @param row    which row(s) the cell occupies
     * @param column which column(s) the cell occupies
     * @param isWall states whether the cell contains a wall
     */
    public Logo(int size, int row, int column, boolean isWall) {
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
     * Used to upload the image used for the logo in a maze.
     */
    public void uploadLogo(){
        //...TODO
    }
}
