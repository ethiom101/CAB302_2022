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
        MazeGenerator bruh = new MazeGenerator(20,10);
        ViewMaze viewMaze = new ViewMaze(bruh);
    }

    public ViewMaze(MazeGenerator maze){
        JFrame frame = new JFrame();

        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        panel.setLayout(new BorderLayout());
        panel.setPreferredSize(new Dimension(550,450));

        //Buttons and ActionListeners
        Button editMaze = new Button("Edit Maze");
        editMaze.addActionListener((event)-> {
            //Do things that edits the maze n stuff
            editMaze_Func(maze);
            System.out.println("EditMaze menu generated");
        });
        Button toggleSolution = new Button("Toggle Solution");
        toggleSolution.addActionListener((event)-> {
            //Do a thing here that shows/hides the maze solution
            System.out.println("Toggle Solution button pressed");
        });
        Button exportMaze = new Button("Export Maze");
        exportMaze.addActionListener((event)-> {
            //Do thing here that outputs maze as image or whatever
            System.out.println("Export Maze button pressed");
        });
        Button saveMaze = new Button("Save Maze");
        saveMaze.addActionListener((event)-> {
            //Do thing here that saves maze to menu
            System.out.println("Save Maze button pressed");
        });

        JPanel eastPanel = new JPanel(new GridLayout(0,1));
        eastPanel.setBorder(BorderFactory.createLineBorder(Color.black));

        JPanel centrePanel = new JPanel();
        JLabel label = new JLabel("Stock Maze");

        centrePanel.add(maze.drawMaze());
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

    public static void editMaze_Func(MazeGenerator maze){
        EditMaze editMaze = new EditMaze(maze);
    }

}
