package Maze;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

/**
 *  Class for generating mazes and the mazes functionalities
 */
public class MazeGenerator {

    //Grid size
    private final int gridX;
    private final int gridY;

    //Grid to contain the maze
    public Cell[][] cells;

    //Start and End values
    int startX = 0;
    int startY = 0;

    /**
     * Initialisation of the MazeGenerator Class
     *
     * @param gridX width of the maze
     * @param gridY height of the maze
     */
    public MazeGenerator(int gridX, int gridY) throws Exception {
        if (gridX > 100 || gridY > 100 || gridX < 1 || gridY < 1) {
            throw new Exception("Too Large");
        } else {
            this.gridX = gridX;
            this.gridY = gridY;
            this.cells = new Cell[gridX][gridY];
            this.cells = createMaze(cells);
        }
    }

    /**
     * Calculates the percentage of cells that are dead ends
     *
     * @return percentage value as String
     */
    public String deadEnds(int rows, int columns) {
        int numDeadEnds = 0;
        for (int i = 0; i < columns; i++) {
            for (int j = 0; j < rows; j++) {
                int numWalls = 0;
                for (int k = 1; k < 5; k++) {
                    if (cells[i][j].getWall(k)) {
                        numWalls++;
                    }
                }
                if (numWalls > 2) {
                    numDeadEnds++;

                }
            }
        }
        return (Math.round(((numDeadEnds - 1.0) / (rows * columns) * 100.0) * 100.0) / 100.0 + "%");
    }

    /**
     * Function to create the maze
     *
     * @param grid 2D array of cells to be updated with the maze values
     * @return the updated grid
     */
    public Cell[][] createMaze(Cell[][] grid) {
        int cellPosX = ThreadLocalRandom.current().nextInt(1, gridX - 1);
        int cellPosY = ThreadLocalRandom.current().nextInt(1, gridY - 1);

        //Initialise the cells of the grid
        for (int i = 0; i < gridX; i++) {
            for (int j = 0; j < gridY; j++) {
                grid[i][j] = new Cell(i, j);
            }
        }

        // Draw logo in maze if and only if a logo image has been selected
        if (Cell.logo != null) {
            grid[cellPosX][cellPosY].setVisited(true);
            grid[cellPosX][cellPosY].setLogo();
        }

        //Start point
        grid[0][0].setVisited(true);
        grid[0][0].setStart();
        Cell start = grid[0][0];

        //Variable to track the current cell we are working on
        Cell current = grid[0][0];

        //Checks if the end cell has been chosen and if the maze doing is finished
        boolean hasSetEnd = false;
        boolean finish = false;

        /*
        Code to create the maze until its finished

        Visit cells and then visit that cells unvisited neighbours
        Once there are no more unvisited neighbours than go backwards until you find an unvisited neighbour then start again
        Finish once we go back to the start cell
        */
        while (!finish) {
            //Check a cell for unvisited neighbours and get the list of directions (N S E W) they are in relation to the current cell
            ArrayList<String> unvisited = getListUnvisited(current);

            //If the list is empty, meaning there are no unvisited neighbours left then go backwards
            if (unvisited.isEmpty()) {

                //get the parent cell and go backwards
                Cell newCurrent = backward(current);
                current = newCurrent;

                //If the parent cell is the start cell then end cause the maze is done
                //Using this method to create the cell only once all the cells have been visited will we go back up to the start cell
                if (newCurrent == start && getListUnvisited(newCurrent).isEmpty()) {
                    finish = true;
                }
            }

            //If there are unlisted neighbours then go forward
            else {

                //Go forward
                current = forward(current, unvisited);
                if (!hasSetEnd && current.getRow() == gridX - 1 && current.getColumn() == gridY - 1) {
                    hasSetEnd = true;
                    grid[current.getRow()][current.getColumn()].setEnd();

                }
            }
        }
        //Once everything is done and the maze is made then return the maze
        return grid;
    }


    /**
     * Forward part of the maze creator. Receive the current cell and a list of available directions
     * Choose one at random and travel to that cell
     *
     * @param current       current cell that we are on
     * @param listDirection List of the directions where there are unvisited neighbours
     * @return the new current cell
     */
    public Cell forward(Cell current, ArrayList<String> listDirection) {

        //Shuffle the list of directions so maze generator is more random
        Collections.shuffle(listDirection);

        //Choose the first direction from randomised list
        //Get the direction values from x and y of the cell we are going to visit
        int[] directionValue = directionValue(listDirection.get(0));

        //set visited and the parent cell of the new cell to be the current cell
        cells[current.getRow() + directionValue[0]][current.getColumn() + directionValue[1]].setParents(current);
        cells[current.getRow() + directionValue[0]][current.getColumn() + directionValue[1]].setVisited(true);

        //Check direction and delete walls on the current and parent cell
        if (Objects.equals(listDirection.get(0), "N")) {
            cells[current.getRow()][current.getColumn()].setWall(1);
            cells[current.getRow() + directionValue[0]][current.getColumn() + directionValue[1]].setWall(2);
        } else if (Objects.equals(listDirection.get(0), "S")) {
            cells[current.getRow()][current.getColumn()].setWall(2);
            cells[current.getRow() + directionValue[0]][current.getColumn() + directionValue[1]].setWall(1);
        } else if (Objects.equals(listDirection.get(0), "W")) {
            cells[current.getRow()][current.getColumn()].setWall(3);
            cells[current.getRow() + directionValue[0]][current.getColumn() + directionValue[1]].setWall(4);
        } else if (Objects.equals(listDirection.get(0), "E")) {
            cells[current.getRow()][current.getColumn()].setWall(4);
            cells[current.getRow() + directionValue[0]][current.getColumn() + directionValue[1]].setWall(3);
        }

        //return child
        return cells[current.getRow() + directionValue[0]][current.getColumn() + directionValue[1]];
    }


    /**
     * returns the parent cell of the current cell
     *
     * @param current current cell
     * @return the parent cell
     */
    public static Cell backward(Cell current) {
        return current.getParents();
    }


    /**
     * Get a list of unvisited neighbours of the current cell
     *
     * @param current the current cell we are looking for neighbours
     * @return a list directions where there are unvisited neighbour
     */
    public ArrayList<String> getListUnvisited(Cell current) {

        //Create two lists of directions (N-S-W-E). Had to make two because it was acting up
        ArrayList<String> direction = new ArrayList<>();
        ArrayList<String> returnDirection = new ArrayList<>();
        direction.add("N");
        direction.add("S");
        direction.add("E");
        direction.add("W");
        returnDirection.add("N");
        returnDirection.add("S");
        returnDirection.add("E");
        returnDirection.add("W");

        //Shuffle list
        Collections.shuffle(direction);

        //If cell is on the edge of the grid then remove the directions where it would go off the grid
        if (current.getRow() == 0) {
            direction.remove("W");
            returnDirection.remove("W");
        } else if (current.getRow() == gridX - 1) {
            direction.remove("E");
            returnDirection.remove("E");
        }

        if (current.getColumn() == 0) {
            direction.remove("N");
            returnDirection.remove("N");
        } else if (current.getColumn() == gridY - 1) {
            direction.remove("S");
            returnDirection.remove("S");
        }

        //Go through the list of directions and remove them if they have been visited already
        for (String s : direction) {
            int[] directionValue = directionValue(s);
            if (cells[current.getRow() + directionValue[0]][current.getColumn() + directionValue[1]].getVisited()) {
                returnDirection.remove(s);
            }
        }
        //Return list of directions of neighbours that haven't been visited yet
        return returnDirection;
    }


    /**
     * Return a pair of x y values which gives the direction of the next cell to go to given direction
     *
     * @param direction direction which we wanna go
     * @return pair of integers
     */
    public int[] directionValue(String direction) {

        //pair to return
        int[] value = {0, 0};

        //Check direction and set the corresponding x/y value
        if (Objects.equals(direction, "N")) {
            value[1] = -1;
        } else if (Objects.equals(direction, "S")) {
            value[1] = 1;
        } else if (Objects.equals(direction, "E")) {
            value[0] = 1;
        } else if (Objects.equals(direction, "W")) {
            value[0] = -1;
        } else {
            throw new Error("ERROR not a valid direction");
        }

        return value;
    }

    /**
     * @return returns the grid of cells of the maze
     */
    public Cell[][] getGrid() {
        return cells;
    }

    /**
     * Sets the Start X position of the maze
     *
     * @param positionX integer position you want to set it to
     */
    public void setStartX(int positionX) {
        startX = positionX;
    }

    /**
     * Sets the Start Y position of the maze
     *
     * @param positionY integer position you want to set it to
     */
    public void setStartY(int positionY) {
        startY = positionY;
    }
}

