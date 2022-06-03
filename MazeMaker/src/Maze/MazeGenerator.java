package Maze;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.*;

import javax.swing.*;
import javax.swing.border.MatteBorder;

import static GUI.EditMaze.itemSelector;
import static GUI.EditMaze.topWall;

public class MazeGenerator {

    //Grid size, cause its one value it must be square, can split it to x and y later
    private static int gridX;
    private static int gridY;
    //Grid
    public static CellOld[][] cells = new CellOld[100][100];
    public ArrayList<CellOld> solution = new ArrayList<CellOld>();
    public static boolean toggle = false;


//    /**
//     * Just to test
//     * @param args
//     */
//    public static void main(String[] args)
//    {
//        cell[][] grid2 = createmaze(0,0,grid);
//        drawMaze(grid2);
//    }
    public MazeGenerator(int gridX,int gridY){
        this.gridX = gridX;
        this.gridY=gridY;
        this.cells = createmaze(0,0, cells);
        //drawMaze(grid2);
    }

    public CellOld[][] getGrid(){
        return cells;
    }
    public ArrayList<CellOld> getSolution(){
        return solution;
    }
    /**
     * Draws the maze
     */
    public JPanel drawMaze(){

        CellOld[][] grid = cells;
        //Set up frame and panel
        //JFrame frame = new JFrame("Maze");
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(gridY, gridX));
        panel.setBackground(Color.WHITE);

        //Draw maze
        for(int i = 0; i< gridY; i++){
            for(int j = 0; j< gridX; j++){
                int row = i;
                int col = j;

                //Each wall is represented by an int
                int north = 0;
                int south = 0;
                int east = 0;
                int west = 0;
                //get the wall value from the grid and add the corresponding wall
                if(grid[j][i].getWall(1)){
                    north=2;
                }
                if(grid[j][i].getWall(2)){
                    south=2;
                }
                if(grid[j][i].getWall(3)){
                    west=2;
                }
                if(grid[j][i].getWall(4)){
                    east=2;
                }

                //Each cell is a label with borders
                JLabel label = new JLabel();
                int finalWest = west;
                int finalSouth = south;
                int finalEast = east;
                int finalNorth = north;
                label.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mousePressed(MouseEvent e) {
                        System.out.println(grid[row][col]);
                        if (itemSelector.getSelectedItem() == "Start") {
                            label.setOpaque(true);
                            label.setBackground(Color.GREEN);
                            grid[col][row].setStart();

                        }
                        if (itemSelector.getSelectedItem() == "End") {
                            label.setOpaque(true);
                            label.setBackground(Color.RED);
                        }
                        if (itemSelector.getSelectedItem() == "Wall") {
                            if (topWall.isSelected()) {

                                if (finalNorth == 2) {
                                    label.setBorder(new MatteBorder(0, finalWest, finalSouth, finalEast, Color.black));
                                } else {
                                    label.setBorder(new MatteBorder(2, finalWest, finalSouth, finalEast, Color.black));
                                }
                            }


                        }
                    }
                });



                //Colour start and end cells
                if(grid[j][i].getStart()){
                    label.setOpaque(true);
                    label.setBackground(Color.green);
                }
                if(grid[j][i].getEnd()){
                    label.setOpaque(true);
                    label.setBackground(Color.red);
                }
                if(toggle==true&&solution.contains(grid[j][i])&&!grid[j][i].getEnd()){
                    label.setOpaque(true);
                    label.setBackground(Color.pink);
                }
                //set border of label
                label.setBorder( new MatteBorder(north, west, south, east, Color.black));
                panel.add(label);
            }
        }
        return panel;
    }
    public void toggleSolution(){
        toggle = !toggle;
    }

    public String cellDistribution(){
        String cellDist = "";
        cellDist = (solution.size() + 1.0) / (gridX * gridY) * 100 +"%";
        return cellDist;
    }

    /**
     *
     * @param x start x
     * @param y start y
     * @param grid grid
     * @return the updated grid
     */
    public CellOld[][] createmaze(int x, int y, CellOld[][] grid){
        //Initialise the cells of the grid
        for(int i = 0; i< gridX; i++){
            for(int j = 0; j<gridY; j++){
                //System.out.println(i+" "+j);
                grid[i][j] = new CellOld(i,j);
            }
        }
        //Start point
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
                //If end cell hasnt been chosen yet then make this cell the end cell
//                if(!hasSetEnd){
//                    hasSetEnd=true;
//                    end = current;
//                    grid[current.getPosx()][current.getPosy()].setEnd();
//                    System.out.println("Found end");
//                }

                //get the parent cell and go backwards
                CellOld newCurrent = backward(current);
                current = newCurrent;

                //If the parent cell is the start cell then end cause the maze is done
                //Using this method to create the cell only once all the cells have been visited will we go back up to the start cell
                if(newCurrent==start && getListUnvisited(newCurrent).isEmpty()){
                    finish=true;
                    System.out.println("LOL1");
                    break;
                }
                else if (newCurrent.getPosx()==0 && newCurrent.getPosy()==0){
                    finish=true;
                    System.out.println("LOL2");
                    break;
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
                    System.out.println("Found end");
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
     * Forward part of the maze creator
     * @param current current cell that we are on
     * @param listDirection List of the directions where there are unvisited neighbours
     * @return
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
     *
     * @param current current cell
     * @return the parent cell
     */
    public static CellOld backward(CellOld current){
        return current.getParent();
    }

    //Get a list of directions of unvisited neighbours
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
     * @return
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

