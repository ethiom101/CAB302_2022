package Maze;

/**
 * Main cell type that creates all the Walls in the maze
 */
public class Wall extends Cell {

    /**
     * Constructor for wall in the maze
     *
     * @param size size of the cell
     * @param row row that the cell occupies
     * @param column column that the cell occupies
     */
    public Wall(int size, int row, int column) {
        super(size, row, column);
    }

    @Override
    public int setSize() {
        //...TODO

        return 0;
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
