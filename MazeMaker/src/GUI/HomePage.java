package GUI;

import Util.MazeFile;
import Util.Pair;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

import static Util.Database.connectToDB;
import static Util.Database.disconnectFromDB;
import static Util.MazeFile.openMaze;

/**
 * GUI for the home maze page
 */
public class HomePage extends JFrame {

    // Page variables
    public static EditMaze mazeEditor;

    JButton newMaze = new JButton("New Maze");
    JButton browseMaze = new JButton("Browse Mazes");
    JButton importMaze = new JButton("Import Maze");

    public HomePage() {
        // Frame
        this.setTitle("Maze Maker");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(700, 300 );
        this.setResizable(false);
        this.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));
        this.setLocationRelativeTo(null);

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent arg0){
                disconnectFromDB();
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
        browseMaze.addActionListener(e->{
            this.dispose();
            new BrowseMaze();
        });
        this.add(importMaze);
        importMaze.setPreferredSize(new Dimension(200, 200));
        importMaze.addActionListener(e -> {
            try {
                boolean maze = openMaze();
                if (maze) {
                    this.dispose();
                }


            } catch (IOException ignored) {}
        });
        this.setVisible(true);
        boolean loop = true;
        Pair<Integer, String> result = connectToDB();
        while (loop){
            if (result.first() < 0) {
                String[] options = {"Yes", "No", "Retry"};
                int confirm = JOptionPane.showOptionDialog(null,
                        String.format("Error connecting to Database!\nError: %s\nDo you wish to continue?", result.second()),
                        "Database Error",
                        JOptionPane.YES_NO_CANCEL_OPTION,
                        JOptionPane.WARNING_MESSAGE,
                        null,
                        options,
                        options[1]);
                switch (confirm) {
                    case (JOptionPane.YES_OPTION) -> loop = false;
                    case (JOptionPane.CANCEL_OPTION) -> {
                        result = connectToDB();
                        continue;
                    }
                    default -> this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
                }
            }
        }
    }
}
