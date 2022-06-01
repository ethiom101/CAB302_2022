package Maze;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


public class ExportImage {
    /**
     * Method to export image of the maze generated
     * @param panel contains image of maze to export
     * @param fileName name of image file
     * @throws IOException
     */
    public static void exportImage(Component panel, String fileName) throws IOException {
        BufferedImage image = new BufferedImage(panel.getWidth(), panel.getHeight(), BufferedImage.TYPE_INT_RGB);
        panel.paint(image.getGraphics());
        ImageIO.write(image, "png", new File(fileName));
    }
}
