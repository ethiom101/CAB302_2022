package Maze;

import java.util.ArrayList;

public class CellOld {
    private int posx;
    private int posy;
    private Boolean visited = false;
    private Boolean isLogo = false;
    private int value=0;

    private CellOld parent;
    private ArrayList<CellOld> children = new ArrayList<CellOld>();

    private boolean northWall = true;
    private boolean southWall = true;
    private boolean eastWall = true;
    private boolean westWall = true;
    private boolean start = false;
    private boolean end = false;

    public CellOld(int posx, int posy){
        this.posx=posx;
        this.posy=posy;
    }

    //Getters and Setters
    public boolean getIsLogo(){return isLogo;}
    public void setLogo(){isLogo=true;}
    public boolean getStart(){
        return start;
    }
    public boolean getEnd(){
        return end;
    }
    public void setStart(){
        start = true;
    }
    public void setEnd(){
        end = true;
    }
    public void setWall(int wall){
        if(wall == 1){
            northWall = false;
        }else if(wall == 2){
            southWall=false;
        }else if(wall == 3){
            westWall=false;
        }else if(wall == 4){
            eastWall=false;
        }
    }
    public boolean getWall(int wall){
        if(wall == 1){
            return northWall;
        }else if(wall == 2){
            return  southWall;
        }else if(wall == 3){
            return westWall;
        }else if(wall == 4){
            return eastWall;
        }
        return false;
    }
    public CellOld getParent(){
        return parent;
    }
    public void setParent(CellOld parent){
        this.parent=parent;
    }
    public void setChildren(CellOld child){
        this.children.add(child);
    }
    public int getPosx(){
        return posx;
    }
    public int getPosy(){
        return posy;
    }
    public void setVisit(){
        this.visited=true;
    }
    public Boolean getVisit(){
        return visited;
    }
    public void setvalue(){
        this.value = 1;
    }
}

