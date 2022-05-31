package GUI;

import Maze.EditMaze;
import Maze.MazeGenerator;
import Maze.ViewMaze;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


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

        //Adding action Listeners to menu Items
        newMaze.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MazeGenerator randMaze = new MazeGenerator(20,10);
                viewMaze_Func(randMaze);
            }
        });

        return menuBar;
    }
    public JPanel window;
    private JPanel mazeAreaPanel;
    private JPanel optionsPanel;
    private JButton solveMazeButton;
    private JButton changeLogoButton;
    private JButton changeStartImageButton;
    private JButton changeEndImageButton;

    //Functions that allow the menu items in 'Main' to create new mazes
    public static void viewMaze_Func(MazeGenerator maze){
        ViewMaze viewMaze = new ViewMaze (maze);
    }
}



