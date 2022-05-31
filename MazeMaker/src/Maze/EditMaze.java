package Maze;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class EditMaze {
    JFrame frame = new JFrame();
    JPanel centrePanel = new JPanel();
    //Testing
    public static void main(String[] args) throws IOException {
        MazeGenerator bruh = new MazeGenerator(20,10);
        EditMaze editMaze = new EditMaze(bruh);
        // Uncomment to export maze as image
        // ExportImage.exportImage(editMaze.centrePanel,"maze.png");
    }


    public EditMaze(MazeGenerator maze) throws IOException {


        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        panel.setLayout(new BorderLayout());
        panel.setPreferredSize(new Dimension(550,450));

        Button editMaze = new Button("Edit Walls");
        Button toggleSolution = new Button("Toggle Solution");
        Button exportMaze = new Button("Edit End Points");
        Button saveMaze = new Button("Edit Logo");

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


        centrePanel.add(maze.drawMaze());
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
