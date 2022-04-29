package GUI;

import javax.swing.*;
import java.awt.*;


public class TheGUI extends JFrame {
    public static JMenuBar createMenu()
    {
        JMenuBar menuBar = new JMenuBar();
        JMenu file = new JMenu("File");
        JMenuItem newMaze = new JMenuItem("New");
        JMenuItem save = new JMenuItem("Save");
        JMenuItem load = new JMenuItem("Load");
        JMenuItem print = new JMenuItem("Print");

        file.add(newMaze);
        file.add(save);
        file.add(load);
        file.add(print);
        menuBar.add(file);

        return menuBar;
    }
    public JPanel window;
    private JPanel mazeAreaPanel;
    private JPanel optionsPanel;
    private JButton solveMazeButton;
    private JButton changeLogoButton;
    private JButton changeStartImageButton;
    private JButton changeEndImageButton;
    private JMenuBar menuBar;
    private JMenu Menu;
    private JMenu Menu3;
    private JMenuItem load;
    private JMenuItem save;
    private JMenuItem export;
    private JMenuItem delete;
    private JMenuItem createMaze;
}



