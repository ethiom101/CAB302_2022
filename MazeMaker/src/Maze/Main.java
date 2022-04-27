package Maze;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        JFrame jframe = new JFrame("Maze");
        jframe.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        jframe.setPreferredSize(new Dimension(400,400));

        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        fileMenu.add("1");
        fileMenu.add("2");
        fileMenu.add("3");
        menuBar.add(fileMenu);
        JMenu editMenu = new JMenu("Edit");
        editMenu.add("1");
        editMenu.add("2");
        editMenu.add("3");
        menuBar.add(editMenu);
        jframe.setJMenuBar(menuBar);

        JPanel panel = new JPanel();
        JLabel label = new JLabel();
        label.setText("Maze");
        panel.add(label);
        jframe.add(panel);

        jframe.pack();
        jframe.setLocationRelativeTo(null);
        jframe.setVisible(true);
    }
}
