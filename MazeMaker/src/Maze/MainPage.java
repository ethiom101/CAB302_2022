package Maze;

import javax.swing.*;
import java.awt.*;

public class MainPage {
    public static void main(String[] args){
        MainPage bruh = new MainPage();
    }
    public MainPage(){
        JFrame frame = new JFrame();

        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        panel.setLayout(new BorderLayout());
        panel.setPreferredSize(new Dimension(550,450));

        Button browse = new Button("Browse Maze's");
        Button createMaze = new Button("Create Maze");
        createMaze.addActionListener((e)->{
            new CreateNewMaze();
            frame.dispose();
        });
        Button importFiles = new Button("Import Files");
        Button exit = new Button("Exit");
        JLabel title = new JLabel("Maze Solver");

        JPanel northPanel = new JPanel(new GridLayout(0,1));
        northPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        northPanel.add(title);

        JPanel eastPanel = new JPanel(new GridLayout(0,1));
        eastPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        eastPanel.add(importFiles);

        JPanel centrePanel = new JPanel(new GridLayout(0,1));
        centrePanel.setBorder(BorderFactory.createLineBorder(Color.black));
        centrePanel.add(createMaze);

        JPanel westPanel = new JPanel(new GridLayout(0,1));
        westPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        westPanel.add(browse);

        JPanel southPanel = new JPanel(new GridLayout(0,1));
        southPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        southPanel.add(exit);

        panel.add(eastPanel,BorderLayout.EAST);
        panel.add(centrePanel,BorderLayout.CENTER);
        panel.add(westPanel,BorderLayout.WEST);
        panel.add(northPanel,BorderLayout.NORTH);
        panel.add(southPanel,BorderLayout.SOUTH);

        frame.add(panel,BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Edit Maze");
        frame.pack();
        frame.setVisible(true);
    }
}
