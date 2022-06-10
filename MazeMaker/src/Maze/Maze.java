package Maze;

import javax.swing.*;
import java.io.File;

public class Maze {

    private int ID;
    private String author;
    private String name;
    private int height;
    private int width;
    private String dateCreated;
    private String dateLastModified;
    private File mazeFile;
    private ImageIcon startImage;
    private ImageIcon endImage;
    private ImageIcon logoImage;
    private ImageIcon mazePicture;

    public Maze() {
    }

    public Maze(String author, String name, int height, int width, String dateLastModified,
                File mazeFile, ImageIcon startImage, ImageIcon endImage, ImageIcon logoImage, ImageIcon mazePicture) {
        this.author = author;
        this.name = name;
        this.height = height;
        this.width = width;
        this.dateCreated = String.valueOf(java.time.LocalDate.now());
        this.dateLastModified = dateLastModified;
        this.mazeFile = mazeFile;
        this.startImage = startImage;
        this.endImage = endImage;
        this.logoImage = logoImage;
        this.mazePicture = mazePicture;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String date) {
        this.dateCreated = date;
    }

    public String getDateLastModified() {
        return dateLastModified;
    }

    public void setDateLastModified(String date) {
        this.dateLastModified = date;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }
}
