package Maze;

/**
 * Logo that is an image that can be placed in the maze as a Wall
 */
public class Logo extends Cell {
    private String imagePath;

    /**
     * Constructor for a Logo that can be placed in the maze as a cell with
     * walls around each side
     *
     * @param size size of the cell
     * @param row row(s) the cell occupies
     * @param column column(s) the cell occupies
     * @param imagePath local image path
     */
    public Logo(int size, int row, int column, String imagePath) {
        super(size, row, column);
        this.imagePath = imagePath;
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
     * Used to upload the image used for the logo in a maze.
     */
    public void uploadLogo(){
        //...TODO
    }
}
