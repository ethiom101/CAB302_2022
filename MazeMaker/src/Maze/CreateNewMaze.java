package Maze;

import javax.swing.*;
import java.awt.*;

public class CreateNewMaze {
    public static void main(String[] args){
        CreateNewMaze bruh = new CreateNewMaze();
    }
    public CreateNewMaze(){
        JFrame frame = new JFrame();

        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        panel.setLayout(new BorderLayout());
        panel.setPreferredSize(new Dimension(550,450));

        JLabel mazeType = new JLabel("Maze Type");
        JLabel mazeSize = new JLabel("Maze Size");
        JLabel logoImage = new JLabel("Logo Image");
        JLabel title = new JLabel("Maze Generator");

        JTextField mazeTypeText = new JTextField();
        JTextField mazeSizeText = new JTextField();
        JButton createMaze = new JButton("Create Maze");
        JButton back = new JButton("Back");
        JButton chooseImage = new JButton("Choose Image");

        JPanel northPanel = new JPanel(new GridLayout(0,2));
        northPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        northPanel.add(back);
        northPanel.add(title);


        JPanel eastPanel = new JPanel(new GridLayout(0,1));
        eastPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        eastPanel.add(mazeTypeText);
        eastPanel.add(mazeSizeText);
        eastPanel.add(chooseImage);


//        JPanel centrePanel = new JPanel(new GridLayout(0,1));
//        centrePanel.setBorder(BorderFactory.createLineBorder(Color.black));
//        centrePanel.add(createMaze);

        JPanel westPanel = new JPanel(new GridLayout(0,1));
        westPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        westPanel.add(mazeType);
        westPanel.add(mazeSize);
        westPanel.add(logoImage);

        JPanel southPanel = new JPanel(new GridLayout(0,1));
        southPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        southPanel.add(createMaze);

        panel.add(eastPanel,BorderLayout.CENTER);
        //panel.add(centrePanel,BorderLayout.CENTER);
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
