package Maze;

import javax.swing.*;
import java.io.File;

/**
 * Stores maze data to be added to the database
 */
public class Maze {

    private int ID;
    private String author;
    private String name;
    private int height;
    private int width;
    private String dateCreated;
    private String dateLastModified;
    private String mazeCells;
    private ImageIcon startImage;
    private ImageIcon endImage;
    private ImageIcon logoImage;
    private ImageIcon mazePicture;

    /**
     * Empty constructor
     */
    public Maze() {
    }

    /**
     * Constructor setting values for a Maze's details
     * @param author author of the maze
     * @param name name of the maze
     * @param height height of the maze
     * @param width width of the maze
     * @param dateLastModified the date the maze was last edited/saved
     * @param mazeCells the cell values used to draw the maze
     * @param startImage start image of the maze that was uploaded
     * @param endImage end image of the maze that was uploaded
     * @param logoImage logo image of the maze the was uploaded
     * @param mazePicture picture of the maze to use for reference when browsing
     */
    public Maze(String author, String name, int height, int width, String dateLastModified,
                String mazeCells, ImageIcon startImage, ImageIcon endImage, ImageIcon logoImage, ImageIcon mazePicture) {
        this.author = author;
        this.name = name;
        this.height = height;
        this.width = width;
        this.dateCreated = String.valueOf(java.time.LocalDate.now());
        this.dateLastModified = dateLastModified;
        this.mazeCells = mazeCells;
        this.startImage = startImage;
        this.endImage = endImage;
        this.logoImage = logoImage;
        this.mazePicture = mazePicture;
    }

    /**
     * @return the author of the maze
     */
    public String getAuthor() {
        return author;
    }

    /**
     * @param author the author to set
     */
    public void setAuthor(String author) {
        this.author = author;
    }

    /**
     * @return gets the name of the maze
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return gets the height of the maze
     */
    public int getHeight() {
        return height;
    }

    /**
     * @param height the height to set
     */
    public void setHeight(int height) {
        this.height = height;
    }

    /**
     * @return gets the width of the maze
     */
    public int getWidth() {
        return width;
    }

    /**
     * @param width the width to set
     */
    public void setWidth(int width) {
        this.width = width;
    }

    /**
     * @return the date the maze was created
     */
    public String getDateCreated() {
        return dateCreated;
    }

    /**
     * this is only used to retrieve a maze, not actually update the date
     * @param date sets the date the maze was created
     */
    public void setDateCreated(String date) {
        this.dateCreated = date;
    }

    /**
     * @return gets the date the maze was last modified
     */
    public String getDateLastModified() {
        return dateLastModified;
    }

    /**
     * @param date the date to set
     */
    public void setDateLastModified(String date) {
        this.dateLastModified = date;
    }

    /**
     * this is used to correspond mazes to those in the database
     * @return gets the ID of the maze
     */
    public int getID() {
        return ID;
    }

    /**
     * this is used to initialize the ID of a maze whenever a maze is created
     * @param ID the ID of the maze
     */
    public void setID(int ID) {
        this.ID = ID;
    }


    public String getMazeCells() {
        return mazeCells;
    }

    public void setMazeCells(String mazeCells) {
        this.mazeCells = mazeCells;
    }
}
