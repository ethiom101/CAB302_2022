package Maze;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

import javax.swing.*;
import javax.swing.border.MatteBorder;

import static GUI.EditMaze.itemSelector;
import static GUI.EditMaze.topWall;

public class MazeGenerator {

    //Grid size
    private static int gridX;
    private static int gridY;
    //Grid
    public static CellOld[][] cells = new CellOld[100][100];

    Stack<CellOld> solution = new Stack<CellOld>();

    public static boolean toggle = false;

    //Start and End values
    private int startX=0;
    private int startY=0;
    private int endX=gridX-1;
    private int endY=gridY-1;

    //Getters and Setters
    public void setEndX(int positionX){
        endX=positionX;
    }
    public void setEndY(int positionY){
        endY=positionY;
    }
    public void setStartX(int positionX){
        startX=positionX;
    }
    public void setStartY(int positionY){
        startY=positionY;
    }
    public void toggleSolution(){
        toggle = !toggle;
    }

    /**
     * Initialisation of the MazeGenerator Class
     * @param gridX
     * @param gridY
     */
    public MazeGenerator(int gridX,int gridY){
        this.gridX = gridX;
        this.gridY = gridY;
        this.cells = createmaze(0,0, cells);
    }

    /**
     * Solves the maze by travelling through the maze and saving the path as a stack
     * @return The solution of the maze in the form of a stack
     */
    public Stack<CellOld> solveMaze(){
        CellOld current = cells[startX][startY];
        Stack<CellOld> visited = new Stack<CellOld>();
        solution.push(current);
        visited.push(current);
        while(true){
            current = solution.peek();
            ArrayList<Integer> direction = getUnvisitedRoute(current,visited);

            if(direction.isEmpty()){
                if(solution.peek()==cells[startX][startY]){
                    break; //Cannot find end
                }
                solution.pop();
            }
            else {
                //Go forward
                CellOld newCurrent = forwardSolution(current, direction);
                solution.push(newCurrent);
                visited.push(newCurrent);
                current = newCurrent;
                if(current==cells[endX][endY]){
                    break; //found end
                }
            }
        }
        return solution;
    }

    /**
     * returns the cell of the next visited cell choosing randomly from the list of directions
     * @param current
     * @param direction
     * @return
     */
    public CellOld forwardSolution(CellOld current, ArrayList<Integer> direction){
        if(direction.get(0)==1){
            return cells[current.getPosx()][current.getPosy()-1];
        }else if(direction.get(0)==2){
            return cells[current.getPosx()][current.getPosy()+1];
        }else if(direction.get(0)==3){
            return cells[current.getPosx()-1][current.getPosy()];
        }else if(direction.get(0)==4){
            return cells[current.getPosx()+1][current.getPosy()];
        }
        return current;
    }

    /**
     * Function for when solving the maze, finding the next cell to go to
     * @param cell current cell that we are looking for the next cell to go
     * @param visited list (stack) of visited cells
     * @return returns a list of directions that the next step in solving the maze could take
     */
    public ArrayList getUnvisitedRoute(CellOld cell,Stack<CellOld> visited){
        ArrayList<Integer> direction = new ArrayList<Integer>();
        for(int i = 1;i<5;i++){
            if(!cell.getWall(i)){
                direction.add(i);
                int[] value = solutionDirectionValue(i);
                if(visited.contains(cells[cell.getPosx()+value[0]][cell.getPosy()+value[1]])){
                    direction.remove(direction.size()-1);
                }
            }
        }
        Collections.shuffle(direction);
        return direction;
    }

    /**
     * Returns the corresponding value of the direction given
     * @param direction direction we want to go
     * @return the direction value so that cell[direction][direction] will result in the next cell to go to
     */
    public int[] solutionDirectionValue(int direction){
        int[] returnPair = {0,0};
        if(direction==1){
            returnPair[1]=-1;
        }else if(direction==2){
            returnPair[1]=1;
        }
        else if(direction==3){
            returnPair[0]=-1;
        }
        else if(direction==4){
            returnPair[0]=1;
        }
        return returnPair;
    }

    /**
     *
     * @return returns the grid of cells of the maze
     */
    public CellOld[][] getGrid(){
        return cells;
    }

    /**
     * calculates the percentage of cells needed to solve the maze
     * @return percentage value
     */
    public String cellDistribution(){
        String cellDist = "";
        cellDist = Math.round((((solution.size() + 1.0) / (gridX * gridY)) * 100)*100.0)/100.0 +"%";
        return cellDist;
    }

    /**
     * Calculates the percentage of cells that are dead ends
     * @return percentage value
     */
    public String deadEnds(){
        int numDeadEnds = 0;
        for(int i = 0; i< gridY; i++) {
            for (int j = 0; j < gridX; j++) {
                int numWalls = 0;
                for(int k=1;k<5;k++){
                    if(cells[i][j].getWall(k)){
                        numWalls++;
                    }
                }
                if(numWalls>2){
                    numDeadEnds++;

                }
            }
        }
        return (Math.round(((numDeadEnds-1.0) / (gridX * gridY) * 100.0) * 100.0) / 100.0 +"%");
    }

    /**
     * Function to create the maze
     * @param x start x
     * @param y start y
     * @param grid grid
     * @return the updated grid
     */
    public CellOld[][] createmaze(int x, int y, CellOld[][] grid){
        int cellPosX = ThreadLocalRandom.current().nextInt(1,gridX-1 );
        int cellPosY = ThreadLocalRandom.current().nextInt(1,gridY-1 );

        //Initialise the cells of the grid
        for(int i = 0; i< gridX; i++){
            for(int j = 0; j<gridY; j++){
                //System.out.println(i+" "+j);
                grid[i][j] = new CellOld(i,j);
            }
        }
        //Start point
        System.out.println("Logo");
        System.out.println(cellPosX);
        System.out.println(cellPosY);
        grid[cellPosX][cellPosY].setVisit();
        grid[cellPosX][cellPosY].setLogo();
        grid[x][y].setVisit();
        grid[x][y].setStart();
        CellOld start = grid[x][y];

        //Variable to the end cell
        CellOld end;

        //Variable to track the current cell we are working on
        CellOld current = grid[x][y];

        //Checks if the end cell has been choosen and if the maze doing is finished
        Boolean hasSetEnd = false;
        Boolean finish = false;

        //Code to create the maze until its finished
        //
        //Visit cells and then visit that cells unvisited neighbours
        //Once there are no more unvisitied neighbours then go backwards until you find an unvisited neighbour then start again
        //Finish once we go back to the start cell
        while(!finish){
            //System.out.println("Current cell is "+Integer.toString(current.getPosx())+" "+Integer.toString(current.getPosy())+" and is visited? "+current.getVisit());

            //Check a cell for unvisited neighbours and get the list of directions (N S E W) they are in relation to the current cell
            ArrayList<String> unvisited = getListUnvisited(current);

            //If the list is empty, meaning there are no unvisited neighbours left then go backwards
            if(unvisited.isEmpty()){
                //get the parent cell and go backwards
                CellOld newCurrent = backward(current);
                current = newCurrent;

                //If the parent cell is the start cell then end cause the maze is done
                //Using this method to create the cell only once all the cells have been visited will we go back up to the start cell
                if(newCurrent==start && getListUnvisited(newCurrent).isEmpty()){
                    finish=true;
                }
            }
            //If there are univisted neighbours then go forward
            else{
                //Go forward

                CellOld newCurrent = forward(current,unvisited);
                current = newCurrent;
                if(!hasSetEnd&&current.getPosx()==gridX-1&&current.getPosy()==gridY-1){
                    hasSetEnd=true;
                    end = current;
                    grid[current.getPosx()][current.getPosy()].setEnd();

                }
                if(!hasSetEnd){
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
     * @param current current cell that we are on
     * @param listDirection List of the directions where there are unvisited neighbours
     * @return the new current cell
     */
    public static CellOld forward(CellOld current, ArrayList<String> listDirection){
        //Shuffle the list of directions so maze generator is more random
        Collections.shuffle(listDirection);
        //Choose the first direction from randomised list
        //Get the direction values from x and y of the cell we are going to visit
        int[] directionValue = directionValue(listDirection.get(0));

        //set visited and the parent cell of the new cell to be the current cell
        cells[current.getPosx()+directionValue[0]][current.getPosy()+directionValue[1]].setParent(current);
        cells[current.getPosx()+directionValue[0]][current.getPosy()+directionValue[1]].setVisit();

        //Check direction and delete walls on the current and parent cell
        if(listDirection.get(0)=="N"){
            cells[current.getPosx()][current.getPosy()].setWall(1);
            cells[current.getPosx()+directionValue[0]][current.getPosy()+directionValue[1]].setWall(2);
        }else if(listDirection.get(0)=="S"){
            cells[current.getPosx()][current.getPosy()].setWall(2);
            cells[current.getPosx()+directionValue[0]][current.getPosy()+directionValue[1]].setWall(1);
        }
        else if(listDirection.get(0)=="W"){
            cells[current.getPosx()][current.getPosy()].setWall(3);
            cells[current.getPosx()+directionValue[0]][current.getPosy()+directionValue[1]].setWall(4);
        }
        else if(listDirection.get(0)=="E"){
            cells[current.getPosx()][current.getPosy()].setWall(4);
            cells[current.getPosx()+directionValue[0]][current.getPosy()+directionValue[1]].setWall(3);
        }

        //set child of current cell
        CellOld newCurrent = cells[current.getPosx()+directionValue[0]][current.getPosy()+directionValue[1]];
        cells[current.getPosx()][current.getPosy()].setChildren(newCurrent);

        //return child
        return cells[current.getPosx()+directionValue[0]][current.getPosy()+directionValue[1]];
    }

    /**
     * returns the parent cell of the current cell
     * @param current current cell
     * @return the parent cell
     */
    public static CellOld backward(CellOld current){
        return current.getParent();
    }

    /**
     * Get a list of unvisited neighbours of the current cell
     * @param current the current cell we are looking for neighbours
     * @return a list directions where there are unvisited neighbour
     */
    public static ArrayList<String> getListUnvisited(CellOld current){
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
        if(current.getPosx()==0){
            direction.remove("W");
            returnDirection.remove("W");
        }
        else if (current.getPosx()== gridX -1){
            direction.remove("E");
            returnDirection.remove("E");
        }
        if (current.getPosy()==0){
            direction.remove("N");
            returnDirection.remove("N");
        }
        else if(current.getPosy()== gridY -1){
            direction.remove("S");
            returnDirection.remove("S");
        }

        //number of directions to check
        int size = direction.size();
        //Go through the list of directions and remove them if they have been visited already
        for(int i = 0;i<size;i++){
            int[] directionValue = directionValue(direction.get(i));
            if (cells[current.getPosx() + directionValue[0]][current.getPosy() + directionValue[1]].getVisit()){
                String toRemove = direction.get(i);
                returnDirection.remove(toRemove);
            }
        }
        //Return list of directions of neighbours that havent been visited yet
        return returnDirection;
    }

    /**
     * Return a pair of x y values which gives the direction of the next cell to go to given direction
     * @param direction direction which we wanna go
     * @return pair of integers
     */
    public static int[] directionValue(String direction){
        //pair to return
        int[] bruh = {0,0};

        //Check direction and set the corresponding x/y value
        if(Objects.equals(direction, "N")){
            bruh[1]=-1;
        }
        else if(Objects.equals(direction, "S")){
            bruh[1]=1;
        }
        else if(Objects.equals(direction, "E")){
            bruh[0]=1;
        }
        else if(Objects.equals(direction, "W")){
            bruh[0]=-1;
        }
        else{
            System.out.print("ERROR not a valid direction");
        }
        return bruh;
    }
    public static CellOld[][] returnGird(){
        return cells;
    }
}

//    public ArrayList<CellOld> getSolution(){
//        return solution;
//    }
/**
 * Draws the maze
 */
//    public JPanel drawMaze(){
//
//        CellOld[][] grid = cells;
//        //Set up frame and panel
//        //JFrame frame = new JFrame("Maze");
//        JPanel panel = new JPanel();
//        panel.setLayout(new GridLayout(gridY, gridX));
//        panel.setBackground(Color.WHITE);
//
//        //Draw maze
//        for(int i = 0; i< gridY; i++){
//            for(int j = 0; j< gridX; j++){
//                int row = i;
//                int col = j;
//
//                //Each wall is represented by an int
//                int north = 0;
//                int south = 0;
//                int east = 0;
//                int west = 0;
//                //get the wall value from the grid and add the corresponding wall
//                if(grid[j][i].getWall(1)){
//                    north=2;
//                }
//                if(grid[j][i].getWall(2)){
//                    south=2;
//                }
//                if(grid[j][i].getWall(3)){
//                    west=2;
//                }
//                if(grid[j][i].getWall(4)){
//                    east=2;
//                }
//
//                //Each cell is a label with borders
//                JLabel label = new JLabel();
//                int finalWest = west;
//                int finalSouth = south;
//                int finalEast = east;
//                int finalNorth = north;
//                label.addMouseListener(new MouseAdapter() {
//                    @Override
//                    public void mousePressed(MouseEvent e) {
//                        System.out.println(grid[row][col]);
//                        if (itemSelector.getSelectedItem() == "Start") {
//                            label.setOpaque(true);
//                            label.setBackground(Color.GREEN);
//                            grid[col][row].setStart();
//
//                        }
//                        if (itemSelector.getSelectedItem() == "End") {
//                            label.setOpaque(true);
//                            label.setBackground(Color.RED);
//                        }
//                        if (itemSelector.getSelectedItem() == "Wall") {
//                            if (topWall.isSelected()) {
//
//                                if (finalNorth == 2) {
//                                    label.setBorder(new MatteBorder(0, finalWest, finalSouth, finalEast, Color.black));
//                                } else {
//                                    label.setBorder(new MatteBorder(2, finalWest, finalSouth, finalEast, Color.black));
//                                }
//                            }
//
//
//                        }
//                    }
//                });
//
//
//
//                //Colour start and end cells
//                if(grid[j][i].getStart()){
//                    label.setOpaque(true);
//                    label.setBackground(Color.green);
//                }
//                if(grid[j][i].getEnd()){
//                    label.setOpaque(true);
//                    label.setBackground(Color.red);
//                }
//                if(toggle==true&&solution.contains(grid[j][i])&&!grid[j][i].getEnd()){
//                    label.setOpaque(true);
//                    label.setBackground(Color.pink);
//                }
//                //set border of label
//                label.setBorder( new MatteBorder(north, west, south, east, Color.black));
//                panel.add(label);
//            }
//        }
//        return panel;
//    }

