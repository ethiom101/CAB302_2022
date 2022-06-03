package Maze;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;


public class Images {
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

    public static ImageIcon resizeImage(ImageIcon image, int width, int height) {
        Image imageImage = image.getImage();
        if (width > 5) {
            Image resizedImage = imageImage.getScaledInstance(width - 5, height - 2, Image.SCALE_SMOOTH);
            return new ImageIcon(resizedImage);
        } else {
            Image resizedImage = imageImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            return new ImageIcon(resizedImage);
        }

    }
    
    // FUNCTION RETRIEVED FROM "https://www.delftstack.com/howto/java/java-rotate-image/#:~:text=Using%20this%20object%2C%20we%20call,Finally%2C%20we%20call%20graphics2D"
    public static ImageIcon rotateImage(ImageIcon image) throws IOException {
        BufferedImage imageToRotate = imageIconToBufferedImage(image);
        int widthOfImage = imageToRotate.getWidth();
        int heightOfImage = imageToRotate.getHeight();
        int typeOfImage = imageToRotate.getType();

        BufferedImage newImageFromBuffer = new BufferedImage(widthOfImage, heightOfImage, typeOfImage);

        Graphics2D graphics2D = newImageFromBuffer.createGraphics();

        graphics2D.rotate(Math.toRadians(90), widthOfImage / 2, heightOfImage / 2);
        graphics2D.drawImage(imageToRotate, null, 0, 0);

        return new ImageIcon(newImageFromBuffer);
    }

    // FUNCTION RETRIEVED FROM "http://www.java2s.com/example/java-utility-method/imageicon-to-bufferedimage/imageicontobufferedimage-imageicon-icon-88232.html"
    private static BufferedImage imageIconToBufferedImage(ImageIcon icon) {
        BufferedImage bufferedImage = new BufferedImage(icon.getIconWidth(), icon.getIconHeight(),
                BufferedImage.TYPE_INT_ARGB);
        Graphics graphics = bufferedImage.createGraphics();
        icon.paintIcon(null, graphics, 0, 0);
        graphics.dispose();
        return bufferedImage;
    }

}
