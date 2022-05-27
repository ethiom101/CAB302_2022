package Maze;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

        editMaze.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Edit Maze Button Pressed");
            }
        });
        toggleSolution.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Toggle Solution Button Pressed");
            }
        });
        exportMaze.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Export Button Pressed");
            }
        });
        saveMaze.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Save Maze Button Pressed");
            }
        });

        JPanel eastPanel = new JPanel(new GridLayout(0,1));
        eastPanel.setBorder(BorderFactory.createLineBorder(Color.black));

        JPanel centrePanel = new JPanel();
        MazeGenerator bruh = new MazeGenerator(10,10);
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
