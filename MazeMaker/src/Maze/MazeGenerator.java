package Maze;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

import static Maze.Grid.grid;

public class MazeGenerator {

    //Grid size
    private int gridX;
    private int gridY;
    //Grid

    public Cell[][] cells;

    Stack<Cell> solution = new Stack<Cell>();

    public static boolean toggle = false;

    //Start and End values
    int startX = 0;
    int startY = 0;
    int endX = gridX - 1;
    int endY = gridY - 1;

    /**
     * Initialisation of the MazeGenerator Class
     *
     * @param gridX
     * @param gridY
     */
    public MazeGenerator(int gridX, int gridY) throws Exception{
        if(gridX>100||gridY>100||gridX<1||gridY<1){
            throw new Exception("Too Large");
        }else {
            this.gridX = gridX;
            this.gridY = gridY;
            this.endX=gridX-1;
            this.endY=gridY-1;
            this.cells = new Cell[gridX][gridY];
            this.cells = createMaze(cells);
        }
    }

    /**
     * Solves the maze by travelling through the maze and saving the path as a stack
     *
     * @return The solution of the maze in the form of a stack
     */
    public Stack<Cell> solveMaze() {
        Cell current = cells[startX][startY];
        Stack<Cell> visited = new Stack<>();
        solution = new Stack<>();
        solution.push(current);
        visited.push(current);

        while (true) {
            current = solution.peek();
            ArrayList<Integer> direction = getUnvisitedRoute(current, visited);


            if (direction.isEmpty()) {
                if (solution.peek() == cells[startX][startY]) {
                    break; //Cannot find end
                }
                solution.pop();
                System.out.println("Pop");
            } else {
                //Go forward

                Cell newCurrent = forwardSolution(current, direction);
                solution.push(newCurrent);
                visited.push(newCurrent);
                current = newCurrent;
                if (current == cells[endX][endY]) {
                    break; //found end
                }
            }
        }
        this.solution = solution;
        return solution;
    }

    /**
     * returns the cell of the next visited cell choosing randomly from the list of directions
     *
     * @param current
     * @param direction
     * @return
     */
    public Cell forwardSolution(Cell current, ArrayList<Integer> direction) {
        if (direction.get(0) == 1) {
            System.out.println("Up");
            return cells[current.getRow()][current.getColumn() - 1];
        } else if (direction.get(0) == 2) {
            System.out.println("Down");
            return cells[current.getRow()][current.getColumn() + 1];
        } else if (direction.get(0) == 3) {
            System.out.println("Left");
            return cells[current.getRow() - 1][current.getColumn()];

        } else if (direction.get(0) == 4) {
            System.out.println("Right");
            return cells[current.getRow() + 1][current.getColumn()];
        }
        return current;
    }

    /**
     * Function for when solving the maze, finding the next cell to go to
     *
     * @param cell    current cell that we are looking for the next cell to go
     * @param visited list (stack) of visited cells
     * @return returns a list of directions that the next step in solving the maze could take
     */
    public ArrayList getUnvisitedRoute(Cell cell, Stack<Cell> visited) {
        ArrayList<Integer> direction = new ArrayList<Integer>();
        for (int i = 1; i < 5; i++) {
            if (!cell.getWall(i)) {
                direction.add(i);
                int[] value = solutionDirectionValue(i);
                if (visited.contains(cells[cell.getRow() + value[0]][cell.getColumn() + value[1]])) {
                    direction.remove(direction.size() - 1);
                }
            }
        }
        Collections.shuffle(direction);
        return direction;
    }

    /**
     * Returns the corresponding value of the direction given
     *
     * @param direction direction we want to go
     * @return the direction value so that cell[direction][direction] will result in the next cell to go to
     */
    public int[] solutionDirectionValue(int direction) {
        int[] returnPair = {0, 0};
        if (direction == 1) {
            returnPair[1] = -1;
        } else if (direction == 2) {
            returnPair[1] = 1;
        } else if (direction == 3) {
            returnPair[0] = -1;
        } else if (direction == 4) {
            returnPair[0] = 1;
        }
        return returnPair;
    }

    /**
     * calculates the percentage of cells needed to solve the maze
     *
     * @return percentage value as String
     */
    public String cellDistribution(int rows, int columns) {
        String cellDist = "";
        cellDist = Math.round((((solution.size() + 1.0) / (rows * columns)) * 100) * 100.0) / 100.0 + "%";
        return cellDist;
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
                //System.out.println(i+" "+j);
                grid[i][j] = new Cell(i, j);
            }
        }
        // Draw logo in maze if and only if a logo image has been selected
        if (Cell.logo != null) {
            grid[cellPosX][cellPosY].setVisited();
            grid[cellPosX][cellPosY].setLogo();
        }
        //Start point
        grid[0][0].setVisited();
        grid[0][0].setStart();
        Cell start = grid[0][0];

        //Variable to the end cell
        Cell end;

        //Variable to track the current cell we are working on
        Cell current = grid[0][0];

        //Checks if the end cell has been choosen and if the maze doing is finished
        boolean hasSetEnd = false;
        boolean finish = false;

        //Code to create the maze until its finished
        //
        //Visit cells and then visit that cells unvisited neighbours
        //Once there are no more unvisitied neighbours then go backwards until you find an unvisited neighbour then start again
        //Finish once we go back to the start cell
        while (!finish) {
            //System.out.println("Current cell is "+Integer.toString(current.getPosx())+" "+Integer.toString(current.getPosy())+" and is visited? "+current.getVisit());

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
                if (!hasSetEnd) {
                    solution.pop();
                }
            }
            //If there are univisted neighbours then go forward
            else {
                //Go forward

                Cell newCurrent = forward(current, unvisited);
                current = newCurrent;
                if (!hasSetEnd && current.getRow() == gridX - 1 && current.getColumn() == gridY - 1) {
                    hasSetEnd = true;
                    end = current;
                    grid[current.getRow()][current.getColumn()].setEnd();

                }
                if (!hasSetEnd) {
                    solution.add(current);
                }
            }
        }
        //Once everything is done and the maze is made then return the maze
        return grid;
    }

    /**
     * Forward part of the maze creator. Receive the current cell and and a list of available directions
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
        cells[current.getRow() + directionValue[0]][current.getColumn() + directionValue[1]].setVisited();

        //Check direction and delete walls on the current and parent cell
        if (listDirection.get(0) == "N") {
            cells[current.getRow()][current.getColumn()].setWall(1);
            cells[current.getRow() + directionValue[0]][current.getColumn() + directionValue[1]].setWall(2);
        } else if (listDirection.get(0) == "S") {
            cells[current.getRow()][current.getColumn()].setWall(2);
            cells[current.getRow() + directionValue[0]][current.getColumn() + directionValue[1]].setWall(1);
        } else if (listDirection.get(0) == "W") {
            cells[current.getRow()][current.getColumn()].setWall(3);
            cells[current.getRow() + directionValue[0]][current.getColumn() + directionValue[1]].setWall(4);
        } else if (listDirection.get(0) == "E") {
            cells[current.getRow()][current.getColumn()].setWall(4);
            cells[current.getRow() + directionValue[0]][current.getColumn() + directionValue[1]].setWall(3);
        }

        //set child of current cell
        Cell newCurrent = cells[current.getRow() + directionValue[0]][current.getColumn() + directionValue[1]];
        cells[current.getRow()][current.getColumn()].setChildren(newCurrent);

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
        //Create two lists of directions (NSWE). Had to make two because it was acting up
        ArrayList<String> direction = new ArrayList<String>();
        ArrayList<String> returnDirection = new ArrayList<String>();
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

        //number of directions to check
        int size = direction.size();
        //Go through the list of directions and remove them if they have been visited already
        for (int i = 0; i < size; i++) {
            int[] directionValue = directionValue(direction.get(i));
            if (cells[current.getRow() + directionValue[0]][current.getColumn() + directionValue[1]].getVisited()) {
                String toRemove = direction.get(i);
                returnDirection.remove(toRemove);
            }
        }
        //Return list of directions of neighbours that havent been visited yet
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
        int[] bruh = {0, 0};

        //Check direction and set the corresponding x/y value
        if (Objects.equals(direction, "N")) {
            bruh[1] = -1;
        } else if (Objects.equals(direction, "S")) {
            bruh[1] = 1;
        } else if (Objects.equals(direction, "E")) {
            bruh[0] = 1;
        } else if (Objects.equals(direction, "W")) {
            bruh[0] = -1;
        } else {
            System.out.print("ERROR not a valid direction");
        }
        return bruh;
    }

    /**
     * @return returns the grid of cells of the maze
     */
    public Cell[][] getGrid() {
        return cells;
    }

    /**
     * Sets the End X position of the maze
     * @param positionX integer position you want to set it to
     */
    public void setEndX(int positionX) {
        endX = positionX;
    }

    /**
     * Sets the End Y position of the maze
     * @param positionY integer position you want to set it to
     */
    public void setEndY(int positionY) {
        endY = positionY;
    }

    /**
     * Sets the Start X position of the maze
     * @param positionX integer position you want to set it to
     */
    public void setStartX(int positionX) {
        startX = positionX;
    }

    /**
     * Sets the Start Y position of the maze
     * @param positionY integer position you want to set it to
     */
    public void setStartY(int positionY) {
        startY = positionY;
    }

    /**
     * Toggles the boolean value toggle
     */
    public void toggleSolution() {
        toggle = !toggle;
    }

    //DO we need this?
    public Cell[][] returnGird() {
        return cells;
    }
}

