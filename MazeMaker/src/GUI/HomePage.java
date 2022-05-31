package GUI;

import javax.swing.*;
import java.awt.*;

public class HomePage extends JFrame {
    JButton newMaze = new JButton("New Maze");
    JButton browseMaze = new JButton("Browse Mazes");
    JButton importMaze = new JButton("Import Maze");

    public HomePage() {
        // Frame
        this.setTitle("Edit Maze");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(700, 300 );
        this.setResizable(false);
        this.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));
        this.setLocationRelativeTo(null);

        // Components
        this.add(newMaze);
        newMaze.setPreferredSize(new Dimension(200, 200));
        newMaze.addActionListener(e -> {
            this.dispose();
            new EditMaze();
        });
        this.add(browseMaze);
        browseMaze.setPreferredSize(new Dimension(200, 200));
        this.add(importMaze);
        importMaze.setPreferredSize(new Dimension(200, 200));

        this.setVisible(true);
    }
}