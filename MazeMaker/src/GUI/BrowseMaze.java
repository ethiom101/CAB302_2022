package GUI;

import Maze.Maze;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

import static GUI.EditMaze.mazeGrid;
import static GUI.HomePage.*;
import static Maze.MazeFile.openMaze;
import static Util.Images.exportImage;

/**
 * GUI for browsing mazes extracted from the database
 */
public class BrowseMaze extends JFrame {
    public JScrollPane browseArea = new JScrollPane();
    public JPanel Mazes = new JPanel();
    public JButton back = new JButton("Back");
    public JButton sortName = new JButton("Sort By Title");
    public JButton sortAuthor = new JButton("Sort By Author");
    public JButton sortDateCreated = new JButton("Sort By Date Created");
    public JButton sortDateModified = new JButton("Sort By Date Edited");
    // distance between each maze area
    private static final int height = 225;
    private int count = 0;

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
        browseArea.setPreferredSize(new Dimension(800, 800));
        browseArea.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        browseArea.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        browseArea.setViewportView(Mazes);

        // Mazes
        Mazes.setBackground(new Color(234, 234, 234));

        Mazes.add(back);
        back.setPreferredSize(new Dimension(100, 50));
        back.addActionListener(e -> {
            new HomePage();
            this.dispose();
        });

        Mazes.add(sortName);
        sortName.setPreferredSize(new Dimension(120, 50));
        sortName.addActionListener(e -> {
            Mazes.removeAll();
            Mazes.add(back);
            Mazes.add(sortName);
            Mazes.add(sortAuthor);
            Mazes.add(sortDateCreated);
            Mazes.add(sortDateModified);
            for (String name : data.getNames()) {
                Maze maze = data.getName(name);
                mazeBrowser.addMaze(maze);
            }
            revalidate();
        });

        Mazes.add(sortAuthor);
        sortAuthor.setPreferredSize(new Dimension(120, 50));
        sortAuthor.addActionListener(e -> {
            Mazes.removeAll();
            Mazes.add(back);
            Mazes.add(sortName);
            Mazes.add(sortAuthor);
            Mazes.add(sortDateCreated);
            Mazes.add(sortDateModified);
            for (String author : data.getAuthors()) {

                Maze maze = data.getAuthor(author);
                mazeBrowser.addMaze(maze);
            }
            revalidate();
        });

        Mazes.add(sortDateCreated);
        sortDateCreated.setPreferredSize(new Dimension(160, 50));
        sortDateCreated.addActionListener(e -> {
            Mazes.removeAll();
            Mazes.add(back);
            Mazes.add(sortName);
            Mazes.add(sortAuthor);
            Mazes.add(sortDateCreated);
            Mazes.add(sortDateModified);
            for (String dateCreated : data.getDatesCreated()) {
                Maze maze = data.getCreated(dateCreated);
                mazeBrowser.addMaze(maze);
            }
            revalidate();
        });

        Mazes.add(sortDateModified);
        sortDateModified.setPreferredSize(new Dimension(160, 50));
        sortDateModified.addActionListener(e -> {
            Mazes.removeAll();
            Mazes.add(back);
            Mazes.add(sortName);
            Mazes.add(sortAuthor);
            Mazes.add(sortDateCreated);
            Mazes.add(sortDateModified);
            for (String dateEdited : data.getDatesEdited()) {
                Maze maze = data.getEdited(dateEdited);
                mazeBrowser.addMaze(maze);
            }
            revalidate();
        });


        // adds and retrieves each maze from the database to the screen
        // and creates and instance of each of those mazes in the application
        // to be edited directly
        for (int ID : data.getIDs()) {
            Maze maze = data.get(ID);
            maze.setID(ID);
            mazeBrowser.addMaze(maze);
            count++;
        }
        // an offset account for the space at the top of the frame and to allow space at the bottom of the frame
        int offset = 80;
        Mazes.setPreferredSize(new Dimension(800, (height * count) + offset));


        // Adding to frame
        this.add(browseArea);

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

        // buttons
        JLabel buttons = new JLabel();
        buttons.setBackground(Color.red);
        buttons.setPreferredSize(new Dimension(200, 200));
        buttons.setLayout(new FlowLayout(FlowLayout.LEFT, 100, 20));

        JButton edit = new JButton("Edit");
        edit.setPreferredSize(new Dimension(100, 40));
        edit.addActionListener(e -> {
            mazeEditor = new EditMaze(maze);
            openMaze(maze);
            this.dispose();
        });

        JButton delete = new JButton("Delete");
        delete.setPreferredSize(new Dimension(100, 40));
        delete.addActionListener(e -> {
            // delete from database & browse maze
            data.remove(maze.getID());
            Mazes.remove(mazeSection);
            repaint();
            revalidate();
        });

        JButton export = new JButton("Export");
        export.setPreferredSize(new Dimension(100, 40));
        export.addActionListener(e -> {

            String[] answers = {"Export With Solution", "Export Without Solution",};
            int answer = JOptionPane.showOptionDialog(
                    null,
                    "Would you like to export with the solution visible?",
                    "Export with or without solution",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.INFORMATION_MESSAGE,
                    null,
                    answers,
                    0);
            if (answer == 0) {
                // with solution
                mazeEditor = new EditMaze(maze);
                mazeEditor.setVisible(false);
                openMaze(maze);
                mazeGrid.Solve();
                JFileChooser fileChooser = new JFileChooser();
                int response = fileChooser.showSaveDialog(null); // select where to save file
                if (response == JFileChooser.APPROVE_OPTION) {
                    File file = new File(fileChooser.getSelectedFile().getAbsolutePath());
                    try {
                        exportImage(mazeGrid, file + ".png");
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
                mazeEditor.dispose();
            }
            else if (answer == 1) {
                // without solution
                mazeEditor = new EditMaze(maze);
                mazeEditor.setVisible(false);
                openMaze(maze);
                JFileChooser fileChooser = new JFileChooser();
                int response = fileChooser.showSaveDialog(null); // select where to save file
                if (response == JFileChooser.APPROVE_OPTION) {
                    File file = new File(fileChooser.getSelectedFile().getAbsolutePath());
                    try {
                        exportImage(mazeGrid, file + ".png");
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
                mazeEditor.dispose();
            }
        });

        buttons.add(edit);
        buttons.add(delete);
        buttons.add(export);

        // adding components
        mazeSection.add(mazeDetails);
        mazeSection.add(buttons);


        Mazes.add(mazeSection);
    }
}
