package GUI;

import javax.swing.*;
import java.awt.*;

public class BrowseMaze extends JFrame {
    JScrollPane browseArea = new JScrollPane();
    JPanel Mazes = new JPanel();
    // distance between each maze area
    private static final int height = 225;
    private static int count = 0;

    public BrowseMaze() {
        init();
        pack();
        addMaze("test", "test", "test", "test");
        addMaze("test", "test", "test", "test");
        addMaze("test", "test", "test", "test");
        addMaze("test", "test", "test", "test");
        addMaze("test", "test", "test", "test");
        this.setVisible(true);
        this.setLocationRelativeTo(null);

    }

    // Initialise frame
    public void init() {
        this.setTitle("Maze Maker");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setLayout(new BorderLayout());

        browseArea.setPreferredSize(new Dimension(800, 700));
        browseArea.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        browseArea.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        browseArea.setViewportView(Mazes);
        Mazes.setBackground(new Color(234, 234, 234));
        this.add(browseArea);
    }

    // details for a maze
    public void addMaze(String title, String author, String dateCreated, String lastEdited) {
        JPanel maze = new JPanel();
        maze.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 5));
        maze.setBorder(BorderFactory.createLineBorder(new Color(181, 181, 181), 2, true));
        maze.setBackground(Color.white);
        maze.setPreferredSize(new Dimension(700, 220));

        JLabel mazeDetails = new JLabel();
        mazeDetails.setPreferredSize(new Dimension(210, 160));
        mazeDetails.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 5));

        // details section
        JLabel mazeTitle = new JLabel("Title: " + title);
        mazeTitle.setPreferredSize(new Dimension(1000, 35));

        JLabel mazeAuthor = new JLabel("Author: " + author);
        mazeAuthor.setPreferredSize(new Dimension(1000, 35));

        JLabel mazeDateCreated = new JLabel("Date Created: " + dateCreated);
        mazeDateCreated.setPreferredSize(new Dimension(1000, 35));

        JLabel mazeLastEdited = new JLabel("Last Edited: " + lastEdited);
        mazeLastEdited.setPreferredSize(new Dimension(1000, 35));

        mazeDetails.add(mazeTitle);
        mazeDetails.add(mazeAuthor);
        mazeDetails.add(mazeDateCreated);
        mazeDetails.add(mazeLastEdited);

        // maze image section
        JLabel mazeImage = new JLabel();
        mazeImage.setPreferredSize(new Dimension(190, 190));
        mazeImage.setBorder(BorderFactory.createLineBorder(new Color(181, 181, 181), 2, true));

        // buttons
        JLabel buttons = new JLabel();
        buttons.setBackground(Color.red);
        buttons.setPreferredSize(new Dimension(200, 200));
        buttons.setLayout(new FlowLayout(FlowLayout.LEFT, 100, 20));

        JButton open = new JButton("Open");
        open.setPreferredSize(new Dimension(100, 40));

        JButton edit = new JButton("Edit");
        edit.setPreferredSize(new Dimension(100, 40));

        JButton export = new JButton("Export");
        export.setPreferredSize(new Dimension(100, 40));

        buttons.add(open);
        buttons.add(edit);
        buttons.add(export);

        // adding components
        maze.add(mazeDetails);
        maze.add(mazeImage);
        maze.add(buttons);

        // adding to scroll panel screen
        count++;
        Mazes.setPreferredSize(new Dimension(800, (height * count) + 20)); // 20 is an offset to allow space at bottom of frame
        Mazes.add(maze);
    }
}
