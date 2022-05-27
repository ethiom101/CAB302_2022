package Maze;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//import static Maze.EditMaze.editMaze;

public class ViewMaze {

    //Testing
    public static void main(String[] args)
    {
        ViewMaze viewMaze = new ViewMaze();

    }

    public ViewMaze(){
        JFrame frame = new JFrame();

        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        panel.setLayout(new BorderLayout());
        panel.setPreferredSize(new Dimension(550,450));

        Button editMaze = new Button("Edit Maze");
        editMaze.addActionListener((event)-> {
            editMaze_Func();
            System.out.print("yes");
        });
        Button toggleSolution = new Button("Toggle Solution");
        Button exportMaze = new Button("Export Maze");
        Button saveMaze = new Button("Save Maze");

        JPanel eastPanel = new JPanel(new GridLayout(0,1));
        eastPanel.setBorder(BorderFactory.createLineBorder(Color.black));

        JPanel centrePanel = new JPanel();
        JLabel label = new JLabel("Stock Maze");
        MazeGenerator bruh = new MazeGenerator(10,10);
        centrePanel.add(bruh.drawMaze());
        centrePanel.setBorder(BorderFactory.createLineBorder(Color.black));
        centrePanel.setPreferredSize(new Dimension(400,400));

        eastPanel.add(editMaze);
        eastPanel.add(toggleSolution);
        eastPanel.add(exportMaze);
        eastPanel.add(saveMaze);

        panel.add(eastPanel,BorderLayout.WEST);
        panel.add(centrePanel,BorderLayout.CENTER);

        frame.getContentPane().add(panel,BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("View Maze");
        frame.pack();
        frame.setVisible(true);
    }

    public static void editMaze_Func(){
        EditMaze editMaze = new EditMaze();
    }

}
