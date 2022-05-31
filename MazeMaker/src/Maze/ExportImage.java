package Maze;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


public class ExportImage {
    public static void exportImage(Component panel, String fileName) throws IOException {
        BufferedImage image = new BufferedImage(panel.getWidth(), panel.getHeight(), BufferedImage.TYPE_INT_RGB);
        panel.paint(image.getGraphics());
        ImageIO.write(image, "png", new File(fileName));
    }
}
