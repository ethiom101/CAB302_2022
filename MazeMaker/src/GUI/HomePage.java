package GUI;

import Database.BrowseMazeData;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * GUI for the home maze page
 */
public class HomePage extends JFrame {

    // Page variables
    public static EditMaze mazeEditor;
    public static BrowseMaze mazeBrowser = new BrowseMaze();
    // the data instance from the database
    public static BrowseMazeData data = new BrowseMazeData();

    JButton newMaze = new JButton("New Maze");
    JButton browseMaze = new JButton("Browse Mazes");

    public HomePage() {
        // Frame
        this.setTitle("Maze Maker");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(500, 300);
        this.setResizable(false);
        this.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));
        this.setLocationRelativeTo(null);

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent arg0) {
                data.persist();
                System.exit(0);
            }
        });
        // Components
        this.add(newMaze);
        newMaze.setPreferredSize(new Dimension(200, 200));
        newMaze.addActionListener(e -> {
            this.dispose();
            mazeEditor = new EditMaze();
        });
        this.add(browseMaze);
        browseMaze.setPreferredSize(new Dimension(200, 200));
        browseMaze.addActionListener(e -> {
            this.dispose();
            mazeBrowser.open();
        });
        this.setVisible(true);
        }
    }
