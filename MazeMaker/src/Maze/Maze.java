package Maze;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Stores maze data to be added to the database
 */
public class Maze {

    private int ID;
    private String author;
    private String name;
    private int height;
    private int width;
    private String dateCreated = dateLastModified();
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
     *
     * @param author           author of the maze
     * @param name             name of the maze
     * @param height           height of the maze
     * @param width            width of the maze
     * @param dateLastModified the date the maze was last edited/saved
     * @param mazeCells        the cell values used to draw the maze
     * @param startImage       start image of the maze that was uploaded
     * @param endImage         end image of the maze that was uploaded
     * @param logoImage        logo image of the maze the was uploaded
     * @param mazePicture      picture of the maze to use for reference when browsing
     */
    public Maze(String author, String name, int height, int width, String dateLastModified,
                String mazeCells, ImageIcon startImage, ImageIcon endImage, ImageIcon logoImage, ImageIcon mazePicture) {
        this.author = author;
        this.name = name;
        this.height = height;
        this.width = width;
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
     *
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

    public static String dateLastModified() {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        return format.format(date);
    }

    /**
     * this is used to correspond mazes to those in the database
     *
     * @return gets the ID of the maze
     */
    public int getID() {
        return ID;
    }

    /**
     * this is used to initialize the ID of a maze whenever a maze is created
     *
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

    /*
     *  Returns a binary stream of the image data for saving into DB
     *  Takes int relating to image number as follows:
     *      1 - Start image
     *      2 - End Image
     *      3 - Logo Image
     *      4 - Maze Image
     */
    public ByteArrayInputStream getImage(int imageNumber) {
        ImageIcon currImage = new ImageIcon();
        switch (imageNumber) {
            case 1:
                currImage = this.startImage;
                break;
            case 2:
                currImage = this.endImage;
                break;
            case 3:
                currImage = this.logoImage;
                break;
            case 4:
                currImage = this.mazePicture;
                break;
            default:
                return null;
        }
        if (currImage == null) {
            byte[] empty = new byte[]{};
            return new ByteArrayInputStream(empty);
        }

        BufferedImage buffImage = new BufferedImage(currImage.getIconWidth(), currImage.getIconHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = buffImage.createGraphics();
        currImage.paintIcon(null, g2d, 0, 0);
        g2d.dispose();

        try (ByteArrayOutputStream bo = new ByteArrayOutputStream()) {
            ImageOutputStream ios = ImageIO.createImageOutputStream(bo);
            try {
                ImageIO.write(buffImage, "png", ios);
            } finally {
                ios.close();
            }
            ByteArrayInputStream bi = new ByteArrayInputStream(bo.toByteArray());
            return bi;
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /*
     *  Sets an image in the maze from a byte array returned from the DB
     *  Takes int relating to image number as follows:
     *      1 - Start image
     *      2 - End Image
     *      3 - Logo Image
     *      4 - Maze Image
     */

    public void setImage(int imageNumber, InputStream imageData) {
        ByteArrayOutputStream bo = new ByteArrayOutputStream();
        try {
            while (true) {
                int result = imageData.read();
                if (result < 0) {
                    imageData.close();
                    break;
                }
                bo.write(result);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        byte[] empty = new byte[]{};
        ImageIcon currImage;
        if (bo.toByteArray() == empty)
            currImage = null;
        else
            currImage = new ImageIcon(bo.toByteArray());

        switch (imageNumber) {
            case 1:
                this.startImage = currImage;
                break;
            case 2:
                this.endImage = currImage;
                break;
            case 3:
                this.logoImage = currImage;
                break;
            case 4:
                this.mazePicture = currImage;
                break;
            default:
                break;
        }
    }
}
