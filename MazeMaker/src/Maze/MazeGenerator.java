package Maze;

import java.awt.*;
import java.util.*;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.MatteBorder;

public class MazeGenerator
{
    //Grid size, cause its one value it must be square, can split it to x and y later
    private static int gridX;
    private static int gridY;
    //Grid
    public static cell[][] gridFinal = new cell[100][100];
    public static ArrayList<cell> solution = new ArrayList<cell>();
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
        this.gridFinal = createmaze(0,0, gridFinal);
        //drawMaze(grid2);
    }

    /**
     * Draws the maze
     */
    public static JPanel drawMaze(){
        cell[][] grid = gridFinal;
        //Set up frame and panel
        //JFrame frame = new JFrame("Maze");
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(gridY, gridX));

        int bruh = 0;
        //Draw maze
        for(int i = 0; i< gridY; i++){
            for(int j = 0; j< gridX; j++){
                //Each cell is a label with borders
                JLabel label = new JLabel();
                Border blackline = BorderFactory.createLineBorder(Color.black);
                //System.out.println("Cell "+i+" "+j+" has north "+grid[i][j].getWall(1)+" has south "+grid[i][j].getWall(2)+" has west "+grid[i][j].getWall(3)+" has east"+grid[i][j].getWall(4));

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

                //Colour start and end cells
                if(grid[j][i].getStart()){
                    label.setOpaque(true);
                    label.setBackground(Color.blue);
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
//                label.setText(String.valueOf(bruh));
//                bruh++;
                panel.add(label);
            }
        }
        panel.setPreferredSize(new Dimension(400,400));
//        frame.getContentPane().add(panel);
//        frame.pack();
//        frame.setVisible(true);
//        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        return panel;
    }
    public static void toggleSolution(){
        toggle = !toggle;
    }
    public static String cellDistribution(){
        return String.valueOf((solution.size()+1.0)/(gridX*gridY)*100)+"%";
    }

    /**
     *
     * @param x start x
     * @param y start y
     * @param grid grid
     * @return the updated grid
     */
    public static cell[][] createmaze(int x, int y, cell[][] grid){
        //Initialise the cells of the grid
        for(int i = 0; i< gridX; i++){
            for(int j = 0; j<gridY; j++){
                //System.out.println(i+" "+j);
                grid[i][j] = new cell(i,j);
            }
        }
        //Start point
        grid[x][y].setVisit();
        grid[x][y].setStart();
        cell start = grid[x][y];

        //Variable to the end cell
        cell end;

        //Variable to track the current cell we are working on
        cell current = grid[x][y];

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
                if(!hasSetEnd){
                    hasSetEnd=true;
                    end = current;
                    grid[current.getPosx()][current.getPosy()].setEnd();
                    System.out.println("Found end");
                }

                //get the parent cell and go backwards
                cell newCurrent = backward(current);
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
                cell newCurrent = forward(current,unvisited);
                current = newCurrent;
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
    public static cell forward(cell current,ArrayList<String> listDirection){
        //Shuffle the list of directions so maze generator is more random
        Collections.shuffle(listDirection);
        //Choose the first direction from randomised list
        //Get the direction values from x and y of the cell we are going to visit
        int[] directionValue = directionValue(listDirection.get(0));

        //set visited and the parent cell of the new cell to be the current cell
        gridFinal[current.getPosx()+directionValue[0]][current.getPosy()+directionValue[1]].setParent(current);
        gridFinal[current.getPosx()+directionValue[0]][current.getPosy()+directionValue[1]].setVisit();

        //Check direction and delete walls on the current and parent cell
        if(listDirection.get(0)=="N"){
            gridFinal[current.getPosx()][current.getPosy()].setWall(1);
            gridFinal[current.getPosx()+directionValue[0]][current.getPosy()+directionValue[1]].setWall(2);
        }else if(listDirection.get(0)=="S"){
            gridFinal[current.getPosx()][current.getPosy()].setWall(2);
            gridFinal[current.getPosx()+directionValue[0]][current.getPosy()+directionValue[1]].setWall(1);
        }
        else if(listDirection.get(0)=="W"){
            gridFinal[current.getPosx()][current.getPosy()].setWall(3);
            gridFinal[current.getPosx()+directionValue[0]][current.getPosy()+directionValue[1]].setWall(4);
        }
        else if(listDirection.get(0)=="E"){
            gridFinal[current.getPosx()][current.getPosy()].setWall(4);
            gridFinal[current.getPosx()+directionValue[0]][current.getPosy()+directionValue[1]].setWall(3);
        }

        //set child of current cell
        cell newCurrent = gridFinal[current.getPosx()+directionValue[0]][current.getPosy()+directionValue[1]];
        gridFinal[current.getPosx()][current.getPosy()].setChildren(newCurrent);

        //return child
        return gridFinal[current.getPosx()+directionValue[0]][current.getPosy()+directionValue[1]];

    }

    /**
     *
     * @param current current cell
     * @return the parent cell
     */
    public static cell backward(cell current){

        //Get parent cell of the current cell
        cell newCurrent = current.getParent();
        //System.out.println("Parent is "+newCurrent.getPosx()+" "+newCurrent.getPosy());
        return newCurrent;
    }

    //Get a list of directions of unvisited neighbours
    public static ArrayList<String> getListUnvisited(cell current){

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
            if (gridFinal[current.getPosx()+directionValue[0]][current.getPosy()+directionValue[1]].getVisit()==true){
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
        if(direction=="N"){
            bruh[1]=-1;
        }
        else if(direction=="S"){
            bruh[1]=1;
        }
        else if(direction=="E"){
            bruh[0]=1;
        }
        else if(direction=="W"){
            bruh[0]=-1;
        }
        else{
            System.out.print("ERROR not a valid direction");
        }
        return bruh;
    }
    public static cell[][] returnGird(){
        return gridFinal;
    }

    //Unused


//    public cell visitneighbours(cell current){
//        ArrayList<String> direction = new ArrayList<String>();
//        direction.add("N");
//        direction.add("S");
//        direction.add("E");
//        direction.add("W");
//        Collections.shuffle(direction);
//        if(current.getPosx()==0){
//            direction.remove("W");
//        }
//        else if (current.getPosx()==gridsize-1){
//            direction.remove("E");
//        }
//        if (current.getPosy()==0){
//            direction.remove("N");
//        }
//        else if(current.getPosy()==gridsize-1){
//            direction.remove("S");
//        }
//        for(String i:direction){
//            if(checkNeighbour(current,i)){
//                int[] directionValue = directionValue(i);
//                cell newCurrent = grid[current.getPosx()+directionValue[0]][current.getPosy()+directionValue[1]];
//                newCurrent.setParent(current);
//                current.setChildren(newCurrent);
//                break;
//            }
//        }
//        return
//    }
//
//    public boolean checkNeighbour(cell current,String direction){
//        int[] directionValue = directionValue(direction);
//        if (grid[current.getPosx()+directionValue[0]][current.getPosy()+directionValue[1]].getVisit()==false){
//            return true;
//        }
//        return false;
//    }
//
//    public void returnUncheckNeighbours(cell current){
//        ArrayList<String> unvisitedNeighbours = new ArrayList<String>();
//
//        if(grid[current.getPosx()][current.getPosy()+1].getVisit()==false){
//            unvisitedNeighbours.add("N");
//        }
//    }
}

