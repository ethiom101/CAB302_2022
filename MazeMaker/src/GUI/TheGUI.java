package GUI;

import javax.swing.*;

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
}


