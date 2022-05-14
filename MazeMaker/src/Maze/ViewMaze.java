package Maze;

import javax.swing.*;
import java.awt.*;

public class ViewMaze {

    public static void main(String[] args){
        viewMaze();
    }

    public static void viewMaze(){
        JFrame frame = new JFrame();

        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        panel.setLayout(new BorderLayout());
        panel.setPreferredSize(new Dimension(450,250));

        Button editMaze = new Button("Edit Maze");
        Button toggleSolution = new Button("Toggle Solution");
        Button exportMaze = new Button("Export Maze");
        Button saveMaze = new Button("Save Maze");

        JPanel eastPanel = new JPanel(new GridLayout(0,1));
        eastPanel.setBorder(BorderFactory.createLineBorder(Color.black));

        JPanel centrePanel = new JPanel();
        JLabel label = new JLabel("Stock Maze");
        centrePanel.add(label);
        centrePanel.setBorder(BorderFactory.createLineBorder(Color.black));

        eastPanel.add(editMaze);
        eastPanel.add(toggleSolution);
        eastPanel.add(exportMaze);
        eastPanel.add(saveMaze);

//        BorderLayout eastBorder = new BorderLayout();
//        eastBorder.setHgap(10);
//        eastBorder.setVgap(10);
//        eastPanel.setLayout(eastBorder);

        panel.add(eastPanel,BorderLayout.WEST);
        panel.add(centrePanel,BorderLayout.CENTER);

        frame.add(panel,BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("View Maze");
        frame.pack();
        frame.setVisible(true);
    }

}
