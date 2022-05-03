package Maze;

import GUI.TheGUI;

import javax.swing.*;
import java.awt.*;
import java.util.Locale;

public class Main {


    public static void main(String[] args) {
        JFrame frame = new JFrame("app");
        frame.setContentPane(new TheGUI().window);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setJMenuBar(TheGUI.createMenu());
        frame.pack();
        frame.setVisible(true);
    }
}
