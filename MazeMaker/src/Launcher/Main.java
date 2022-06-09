package Launcher;

import GUI.HomePage;
import Maze.Grid;
import Maze.MazeFile;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


public class Main{
    private static Object lock = new Object();
    public static void main(String[] args) {
        HomePage home = new HomePage();
    }
}
