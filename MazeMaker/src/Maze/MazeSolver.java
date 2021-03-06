package Maze;

import java.awt.*;
import java.util.ArrayList;
import java.util.Stack;

import static Maze.Grid.grid;

/**
 * Class for finding a path from the start to the end of the grid
 */
public class MazeSolver extends Thread {

    private final Cell start;
    private final Cell end;
    private Cell current;
    private final Stack<Cell> visited;
    private final ArrayList<Cell> path;
    private boolean finished = false;

    /**
     * Maze solver class instance
     * @param start the start cell of the maze
     * @param end the end cell of the maze
     */
    public MazeSolver(Cell start, Cell end) {
        this.start = start;
        this.end = end;
        visited = new Stack<>();
        path = new ArrayList<>();
        current = this.start;
        path.add(this.start);
        if ((this.start != null) && (this.end != null)) {
            this.start.setVisited(true);
            solveMaze();
        }
    }

    /**
     * clears the solution by erasing all the cells of the solution
     */
    public void clearSolution() {
        if (this.start != null) {
            for (Cell cell : path) {
                cell.setOpaque(true);
                cell.setBackground(Color.WHITE);
            }
        }
    }

    /**
     * Draws the solution of the maze on the cells
     */
    public void drawSolution() {
        if (this.start != null) {
            for (Cell cell : path) {
                cell.setOpaque(true);
                cell.setBackground(Color.pink);
            }
        }
    }

    /**
     * Solves the maze
     */
    private void solveMaze() {
        while (!finished) {
            Cell next = getNextCell(current);
            setParents(current, next);
            if (next == end) {
                Path();
                finished = true;
                return;
            }

            if (next != null) {
                visited.push(next);
                next.setVisited(true);

            } else {
                if (!visited.isEmpty()) {
                    current = visited.pop();
                } else {
                    finished = true;
                }
            }
        }
    }

    /**
     * Making path have the cells from end to start
     */
    private void Path() {
        path.add(end);
        Cell Parent = end.getParents();
        while (Parent != start) {
            path.add(Parent);
            Parent = Parent.getParents();
        }
    }

    /**
     * Sets the parent of the next cell with current cell
     * @param current parent cell to be added
     * @param next cell to have parent added
     */
    private void setParents(Cell current, Cell next) {
        if (next != null) {
            next.setParents(current);
        }
        if (current != null)
            current.getNext().add(next);
    }

    /**
     * Returns the next cell we want to go to from the current cell based on what walls are around it and if they have been visited already
     * @param current Current cell we want to go forward on
     * @return The next forward cell from the current cell
     */
    private Cell getNextCell(Cell current) {
        ArrayList<Cell> neighbours = new ArrayList<>();

        try {
            if (!grid[current.getRow() - 1][current.getColumn()].getVisited() && !current.isWall[0]) {
                neighbours.add(grid[current.getRow() - 1][current.getColumn()]); // top
            }
        } catch (Exception ignored) {
        }

        try {
            if (!grid[current.getRow()][current.getColumn() - 1].getVisited() && !current.isWall[1]) {
                neighbours.add(grid[current.getRow()][current.getColumn() - 1]); //left
            }
        } catch (Exception ignored) {
        }

        try {
            if (!grid[current.getRow() + 1][current.getColumn()].getVisited() && !current.isWall[2]) {
                neighbours.add(grid[current.getRow() + 1][current.getColumn()]); // down
            }
        } catch (Exception ignored) {
        }

        try {
            if (!grid[current.getRow()][current.getColumn() + 1].getVisited() && !current.isWall[3]) {
                neighbours.add(grid[current.getRow()][current.getColumn() + 1]); //right
            }
        } catch (Exception ignored) {
        }

        if (neighbours.isEmpty())
            return null;

        return neighbours.get(0);
    }


    /**
     * calculates the percentage of cells needed to solve the maze
     * @return percentage value as String
     */
    public String cellDistribution(int rows, int columns) {
        String cellDist;
        cellDist = Math.round((((path.size() + 1.0) / (rows * columns)) * 100) * 100.0) / 100.0 + "%";
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
                for (int k = 0; k < 4; k++) {
                    if (grid[i][j].isWall[k]) {
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


}
