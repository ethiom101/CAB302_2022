package Maze;

/**
 * Cell type used in Children Mazes to indicate the start and end of a maze
 * with images
 */
public class Objective extends Cell {
        private String imagePath;

    /**
     * Constructor for the start and end images of a children maze
     *
     * @param size size of the cell
     * @param row row that the cell is in
     * @param column column that the cell is in
     * @param imagePath the local path to the image
     */
    public Objective(int size, int row, int column, String imagePath) {
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
     * Upload images to be used for the start and end of a maze.
     * @param imagePath path to local file containing image
     */
    public void uploadImage(String imagePath) {
        //...TODO
    }
}
