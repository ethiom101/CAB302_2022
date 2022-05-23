package Maze;

import javax.swing.*;
import java.awt.*;

public class EditMaze {
    //Testing
    public static void main(String[] args){
        EditMaze editMaze = new EditMaze();
    }


    public EditMaze(){
        JFrame frame = new JFrame();

        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        panel.setLayout(new BorderLayout());
        panel.setPreferredSize(new Dimension(550,450));

        Button editMaze = new Button("Edit Maze");
        Button toggleSolution = new Button("Toggle Solution");
        Button exportMaze = new Button("Export Maze");
        Button saveMaze = new Button("Save Maze");

        JPanel eastPanel = new JPanel(new GridLayout(0,1));
        eastPanel.setBorder(BorderFactory.createLineBorder(Color.black));

        JPanel centrePanel = new JPanel();
        MazeGenerator bruh = new MazeGenerator(10);
        centrePanel.add(bruh.drawMaze());
        centrePanel.setBorder(BorderFactory.createLineBorder(Color.black));

        eastPanel.add(editMaze);
        eastPanel.add(toggleSolution);
        eastPanel.add(exportMaze);
        eastPanel.add(saveMaze);

        panel.add(eastPanel,BorderLayout.WEST);
        panel.add(centrePanel,BorderLayout.CENTER);

        frame.add(panel,BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Edit Maze");
        frame.pack();
        frame.setVisible(true);
    }

}
