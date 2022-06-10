package GUI;

import Maze.Maze;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import static GUI.HomePage.data;
import static GUI.HomePage.mazeBrowser;

/**
 * GUI for browsing mazes extracted from the database
 */
public class BrowseMaze extends JFrame {
    public JScrollPane browseArea = new JScrollPane();
    public JPanel Mazes = new JPanel();
    // distance between each maze area
    private static final int height = 225;
    private static int count = 0;

    /**
     * Empty constructor
     */
    public BrowseMaze() {
    }

    /**
     * Used to open the browse maze page
     */
    public void open() {

        // Setting up frame
        this.setTitle("Browse Mazes");
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setResizable(false);
        this.setLayout(new BorderLayout());

        // Scrollable area
        browseArea.setPreferredSize(new Dimension(800, 700));
        browseArea.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        browseArea.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        browseArea.setViewportView(Mazes);

        // Mazes
        Mazes.setBackground(new Color(234, 234, 234));

        // adds and retrieves each maze from the database to the screen
        // and creates and instance of each of those mazes in the application
        // to be edited directly
        for (int ID : data.getIDs()) {
            Maze maze = data.get(ID);
            maze.setID(ID);
            mazeBrowser.addMaze(maze);
        }

        // Adding to frame
        this.add(browseArea);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e){
                new HomePage();
            }
        });

        pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }

    // details for a maze
    public void addMaze(Maze maze) {
        JPanel mazeSection = new JPanel();
        mazeSection.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 5));
        mazeSection.setBorder(BorderFactory.createLineBorder(new Color(181, 181, 181), 2, true));
        mazeSection.setBackground(Color.white);
        mazeSection.setPreferredSize(new Dimension(700, 220));

        JLabel mazeDetails = new JLabel();
        mazeDetails.setPreferredSize(new Dimension(210, 160));
        mazeDetails.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 5));

        // details section
        JLabel mazeTitle = new JLabel("Title: " + maze.getName());
        mazeTitle.setPreferredSize(new Dimension(1000, 35));

        JLabel mazeAuthor = new JLabel("Author: " + maze.getAuthor());
        mazeAuthor.setPreferredSize(new Dimension(1000, 35));

        JLabel mazeDateCreated = new JLabel("Date Created: " + maze.getDateCreated());
        mazeDateCreated.setPreferredSize(new Dimension(1000, 35));

        JLabel mazeLastEdited = new JLabel("Last Edited: " + maze.getDateLastModified());
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

        JButton edit = new JButton("Edit");
        edit.setPreferredSize(new Dimension(100, 40));
        edit.addActionListener(e -> {
            // open maze to edit TODO
        });

        JButton delete = new JButton("Delete");
        delete.setPreferredSize(new Dimension(100, 40));
        delete.addActionListener(e -> {
            // delete from database & browse maze
            data.remove(maze.getID());
            Mazes.remove(mazeSection);
            repaint();
        });

        JButton export = new JButton("Export");
        export.setPreferredSize(new Dimension(100, 40));
        export.addActionListener(e -> {
            // Export maze as image (include solution or not option) TODO
        });

        buttons.add(edit);
        buttons.add(delete);
        buttons.add(export);

        // adding components
        mazeSection.add(mazeDetails);
        mazeSection.add(mazeImage);
        mazeSection.add(buttons);

        // adding to scroll panel screen
        count++;
        Mazes.setPreferredSize(new Dimension(800, (height * count) + 20)); // 20 is an offset to allow space at bottom of frame
        Mazes.add(mazeSection);
    }
}
