package Maze;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.MatteBorder;

public class MazeGenerator
{
    private static int gridsize = 10;
    public static cell[][] grid = new cell[gridsize][gridsize];


    public static void main(String[] args)
    {
        cell[][] grid2 = createmaze(0,0,grid);
        drawMaze(grid2);

    }
    public static void drawMaze(cell[][] grid){
        JFrame frame = new JFrame("Maze");
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(gridsize,gridsize));
        for(int i =0;i<gridsize;i++){
            for(int j=0;j<gridsize;j++){
                JLabel label = new JLabel();
                Border blackline = BorderFactory.createLineBorder(Color.black);
                System.out.println("Cell "+i+" "+j+" has north "+grid[i][j].getWall(1)+" has south "+grid[i][j].getWall(2)+" has west "+grid[i][j].getWall(3)+" has east"+grid[i][j].getWall(4));
                int north = 0;
                int south = 0;
                int east = 0;
                int west = 0;
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
                if(grid[j][i].getStart()){
                    label.setOpaque(true);
                    label.setBackground(Color.blue);
                }
                if(grid[j][i].getEnd()){
                    label.setOpaque(true);
                    label.setBackground(Color.red);
                }
                label.setBorder( new MatteBorder(north, west, south, east, Color.black));
                label.setText(i+" "+j);
                panel.add(label);
            }
        }
        panel.setPreferredSize(new Dimension(400,400));
        frame.getContentPane().add(panel);
        frame.pack();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

    }
    public static cell[][] createmaze(int x, int y, cell[][] grid){
        for(int i =0;i<gridsize;i++){
            for(int j=0;j<gridsize;j++){
                grid[i][j] = new cell(i,j);
            }
        }
        grid[x][y].setVisit();
        grid[x][y].setStart();
        cell start = grid[x][y];
        cell end;
        cell current = grid[x][y];
        Boolean hasSetEnd = false;
        Boolean finish = false;

        while(!finish){
            System.out.println("Current cell is "+Integer.toString(current.getPosx())+" "+Integer.toString(current.getPosy())+" and is visited? "+current.getVisit());
            ArrayList<String> unvisited = getListUnvisited(current);

            if(unvisited.isEmpty()){
                //go backward
                System.out.println("DONE");
                if(!hasSetEnd){
                    hasSetEnd=true;
                    end = current;
                    grid[current.getPosx()][current.getPosy()].setEnd();
                    System.out.println("Found end");
                }
                cell newCurrent = backward(current);
                current = newCurrent;

                if(newCurrent==start){
                    finish=true;
                    System.out.println("LOL1");
                    break;
                }
                else if (newCurrent.getPosx()==0 && newCurrent.getPosy()==0){
                    finish=true;
                    System.out.println("LOL2");
                    break;
                }
            }else{
                //Go forward
                cell newCurrent = forward(current,unvisited);
                current = newCurrent;
            }
        }
        return grid;
    }
    public static cell forward(cell current,ArrayList<String> listDirection){
        Collections.shuffle(listDirection);
        int[] directionValue = directionValue(listDirection.get(0));

        grid[current.getPosx()+directionValue[0]][current.getPosy()+directionValue[1]].setParent(current);
        grid[current.getPosx()+directionValue[0]][current.getPosy()+directionValue[1]].setVisit();

        if(listDirection.get(0)=="N"){
            grid[current.getPosx()][current.getPosy()].setWall(1);
            grid[current.getPosx()+directionValue[0]][current.getPosy()+directionValue[1]].setWall(2);
        }else if(listDirection.get(0)=="S"){
            grid[current.getPosx()][current.getPosy()].setWall(2);
            grid[current.getPosx()+directionValue[0]][current.getPosy()+directionValue[1]].setWall(1);
        }
        else if(listDirection.get(0)=="W"){
            grid[current.getPosx()][current.getPosy()].setWall(3);
            grid[current.getPosx()+directionValue[0]][current.getPosy()+directionValue[1]].setWall(4);
        }
        else if(listDirection.get(0)=="E"){
            grid[current.getPosx()][current.getPosy()].setWall(4);
            grid[current.getPosx()+directionValue[0]][current.getPosy()+directionValue[1]].setWall(3);
        }

        cell newCurrent = grid[current.getPosx()+directionValue[0]][current.getPosy()+directionValue[1]];
        grid[current.getPosx()][current.getPosy()].setChildren(newCurrent);

        return grid[current.getPosx()+directionValue[0]][current.getPosy()+directionValue[1]];

    }

    public static cell backward(cell current){
        cell newCurrent = current.getParent();
        System.out.println("Parent is "+newCurrent.getPosx()+" "+newCurrent.getPosy());
        return newCurrent;
    }

    public static ArrayList<String> getListUnvisited(cell current){
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
        Collections.shuffle(direction);
        if(current.getPosx()==0){
            direction.remove("W");
            returnDirection.remove("W");
        }
        else if (current.getPosx()==gridsize-1){
            direction.remove("E");
            returnDirection.remove("E");
        }
        if (current.getPosy()==0){
            direction.remove("N");
            returnDirection.remove("N");
        }
        else if(current.getPosy()==gridsize-1){
            direction.remove("S");
            returnDirection.remove("S");
        }

        int size = direction.size();
        for(int i = 0;i<size;i++){
            int[] directionValue = directionValue(direction.get(i));
            if (grid[current.getPosx()+directionValue[0]][current.getPosy()+directionValue[1]].getVisit()==true){
                String toRemove = direction.get(i);
                returnDirection.remove(toRemove);
            }
        }
        return returnDirection;
    }

    public static int[] directionValue(String direction){
        int[] bruh = {0,0};
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

