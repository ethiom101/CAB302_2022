package GUI;

import Maze.ExportImage;
import Maze.MazeGenerator;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class EditMaze extends JFrame {
    public JPanel mazePanel = new JPanel();
    public JPanel sideBar = new JPanel();

    // Maze Components
    JPanel maze;
    MazeGenerator mazeGenerator;
    int cellSize = 50;
    public int mazeWidth = 5;
    public int mazeHeight = 5;

    // Side Bar Components
    //  new grid
    public JPanel newGrid = new JPanel();
    public JSpinner mazeRows = new JSpinner(new SpinnerNumberModel(5, 5, 100, 1));
    public JSpinner mazeColumns = new JSpinner(new SpinnerNumberModel(5, 5, 100, 1));
    public JButton updateGrid = new JButton("Update Grid");
    // cell slider
    public JSlider cellSlider = new JSlider(0, 100, 50);
    // item picker
    public JPanel itemPicker = new JPanel();
    public String[] items = {"Start", "End", "Wall", "Logo",};
    public JComboBox itemSelector = new JComboBox(items);
    public JButton changeImage = new JButton("Change Image");
    public JButton resetImage = new JButton("Reset");
    public JRadioButton topWall = new JRadioButton("Top");
    public JRadioButton downWall = new JRadioButton("Down");
    public JRadioButton leftWall = new JRadioButton("Left");
    public JRadioButton rightWall = new JRadioButton("Right");
    public ButtonGroup wallSelections = new ButtonGroup();
    public JButton removeLogo = new JButton("Remove");
    public JPanel selectedImage = new JPanel();
    public JLabel startImage = new JLabel();
    public JLabel endImage = new JLabel();
    public JLabel logoImage = new JLabel();
    // generate maze
    public JButton generateMaze = new JButton("Generate New Maze");
    // toggle solution
    public JButton toggleSolution = new JButton("Toggle Solution");
    // statistics
    public JPanel statistics = new JPanel();
    public JLabel percentageTravel = new JLabel("% of Cells To Win:");
    public JLabel percentageDeadEnd = new JLabel("% of Dead Ends:");
    public JLabel difficulty = new JLabel("Difficulty:");
    // others
    public JButton save = new JButton("Save");
    public JButton export = new JButton("Export");
    public JButton exit = new JButton("Exit");
    String[] answers = {"Save", "Don't Save", "Cancel"};


    public EditMaze() {

        // Frame
        this.setTitle("Edit Maze");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1000, 750);
        this.setResizable(true);
        this.setLayout(new BorderLayout());
        this.setLocationRelativeTo(null);

        // Adding components to frame
        this.add(sideBar, BorderLayout.WEST);
        this.add(mazePanel, BorderLayout.CENTER);

        // Creating Components

        // Maze
        mazePanel.setBackground(Color.WHITE);


        // Sidebar
        sideBar.setBackground(new Color(234, 234, 234));
        sideBar.setPreferredSize(new Dimension(250, 100));
        sideBar.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 20));
        // Change maze size
        sideBar.add(newGrid);
        newGrid.setBorder(BorderFactory.createLineBorder(new Color(181, 181, 181), 2, true));
        newGrid.setPreferredSize(new Dimension(230, 150));
        newGrid.add(new JLabel("Rows:"));
        newGrid.add(mazeRows);
        mazeRows.addChangeListener(e -> {
            // Rows change listener implementation
            System.out.println(mazeRows.getValue());

        });
        mazeRows.setPreferredSize(new Dimension(50, 25));
        newGrid.add(new JLabel("Columns:"));
        newGrid.add(mazeColumns);
        mazeColumns.addChangeListener(e -> {
            // Columns change listener implementation
            System.out.println(mazeColumns.getValue());

        });
        mazeColumns.setPreferredSize(new Dimension(50, 25));
        newGrid.add(updateGrid);
        updateGrid.setPreferredSize(new Dimension(200, 25));
        updateGrid.addActionListener(e -> {
                    // update grid implementation
                    System.out.println("Update Grid");
                    this.mazeWidth = (int) mazeRows.getValue();
                    this.mazeHeight = (int) mazeColumns.getValue();
                    this.cellSize = cellSlider.getValue();
                    this.maze.setPreferredSize(new Dimension(mazeWidth * cellSize, mazeHeight * cellSize));
                    maze.revalidate();
                    maze.repaint();
                }
        );

        // Cell size slider
        JLabel cellSizeLabel = new JLabel("50");
        newGrid.add(cellSizeLabel);
        cellSizeLabel.setPreferredSize(new Dimension(25, 20));
        newGrid.add(cellSlider);
        cellSlider.setPaintTicks(true);
        cellSlider.setPaintTrack(true);
        cellSlider.setPaintLabels(true);
        cellSlider.setMinorTickSpacing(5);
        cellSlider.setMajorTickSpacing(10);
        cellSlider.setPreferredSize(new Dimension(225, 45));
        cellSlider.addChangeListener(e -> {
            System.out.println(cellSlider.getValue());
            cellSizeLabel.setText(String.valueOf(cellSlider.getValue()));
        });


        // Item Picker
        sideBar.add(itemPicker);
        itemPicker.setBorder(BorderFactory.createLineBorder(new Color(181, 181, 181), 2, true));
        itemPicker.setPreferredSize(new Dimension(230, 80));
        itemPicker.add(new JLabel("Item Picker:"));
        itemPicker.add(itemSelector);
        itemSelector.setPreferredSize(new Dimension(100, 25))
        // change image button
        ;
        itemPicker.add(changeImage);
        selectedImage.add(startImage);
        changeImage.addActionListener(e -> {
            // change image implementation
            JFileChooser fileChooser = new JFileChooser();
            int response = fileChooser.showOpenDialog(null); // select file to open
            if (response == JFileChooser.APPROVE_OPTION) {
                // resizing image
                File file = new File(fileChooser.getSelectedFile().getAbsolutePath());
                ImageIcon imageLogo = new ImageIcon(String.valueOf(file));
                Image image = imageLogo.getImage();
                Image resizedLogo = image.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
                imageLogo = new ImageIcon(resizedLogo);
                if (itemSelector.getSelectedItem() == "Start") {
                    startImage.setIcon(imageLogo);
                }
                if (itemSelector.getSelectedItem() == "End") {
                    endImage.setIcon(imageLogo);
                }
                if (itemSelector.getSelectedItem() == "Logo") {
                    logoImage.setIcon(imageLogo);
                }
            }
        });
        changeImage.setPreferredSize(new Dimension(120, 25));
        // reset image button
        itemPicker.add(resetImage);
        selectedImage.add(endImage);
        resetImage.addActionListener(e -> {
            // remove logo implementation
            if (itemSelector.getSelectedItem() == "Start") {
                startImage.setIcon(null);
            }
            if (itemSelector.getSelectedItem() == "End") {
                endImage.setIcon(null);
            }

        });
        resetImage.setPreferredSize(new Dimension(80, 25));
        // wall selector
        itemPicker.add(topWall);
        topWall.addActionListener(e -> {
            // top radio button implementation
            System.out.println("Top Wall Selected");
        });
        topWall.setVisible(false);
        itemPicker.add(downWall);
        downWall.addActionListener(e -> {
            // down radio button implementation
            System.out.println("Down Wall Selected");
        });
        downWall.setVisible(false);
        itemPicker.add(leftWall);
        leftWall.addActionListener(e -> {
            // left radio button implementation
            System.out.println("Left Wall Selected");
        });
        leftWall.setVisible(false);
        itemPicker.add(rightWall);
        rightWall.addActionListener(e -> {
            // right radio button implementation
            System.out.println("Right Wall Selected");
        });
        rightWall.setVisible(false);
        wallSelections.add(topWall);
        wallSelections.add(downWall);
        wallSelections.add(leftWall);
        wallSelections.add(rightWall);
        // logo
        itemPicker.add(removeLogo);
        removeLogo.addActionListener(e -> {
            // remove logo implementation
            logoImage.setIcon(null);
        });
        removeLogo.setVisible(false);
        sideBar.add(selectedImage);
        selectedImage.setBorder(BorderFactory.createLineBorder(new Color(181, 181, 181), 2, true));
        selectedImage.setPreferredSize(new Dimension(110, 115));
        selectedImage.add(logoImage);


        itemSelector.addActionListener(e -> {
            // item selector implementation
            System.out.println(itemSelector.getSelectedItem());
            if (itemSelector.getSelectedItem() == "Wall") {
                topWall.setVisible(true);
                downWall.setVisible(true);
                leftWall.setVisible(true);
                rightWall.setVisible(true);
                changeImage.setVisible(false);
                resetImage.setVisible(false);
                removeLogo.setVisible(false);
                selectedImage.setVisible(false);
                logoImage.setVisible(false);
                endImage.setVisible(false);
                startImage.setVisible(false);
            } else if (itemSelector.getSelectedItem() == "Logo") {
                changeImage.setVisible(true);
                removeLogo.setVisible(true);
                topWall.setVisible(false);
                downWall.setVisible(false);
                leftWall.setVisible(false);
                rightWall.setVisible(false);
                resetImage.setVisible(false);
                selectedImage.setVisible(true);
                logoImage.setVisible(true);
                endImage.setVisible(false);
                startImage.setVisible(false);
            } else if (itemSelector.getSelectedItem() == "End") {
                changeImage.setVisible(true);
                resetImage.setVisible(true);
                topWall.setVisible(false);
                downWall.setVisible(false);
                leftWall.setVisible(false);
                rightWall.setVisible(false);
                removeLogo.setVisible(false);
                selectedImage.setVisible(true);
                logoImage.setVisible(false);
                endImage.setVisible(true);
                startImage.setVisible(false);
            } else {
                changeImage.setVisible(true);
                resetImage.setVisible(true);
                topWall.setVisible(false);
                downWall.setVisible(false);
                leftWall.setVisible(false);
                rightWall.setVisible(false);
                removeLogo.setVisible(false);
                selectedImage.setVisible(true);
                logoImage.setVisible(false);
                endImage.setVisible(false);
                startImage.setVisible(true);
            }
        });

        // generate maze
        sideBar.add(generateMaze);
        generateMaze.setPreferredSize(new Dimension(150, 40));
        generateMaze.addActionListener(e -> {
            // generate maze implementation
            mazePanel.removeAll();
            System.out.println("Generate New Maze");
            this.mazeGenerator = new MazeGenerator(mazeWidth, mazeHeight);
            this.maze = this.mazeGenerator.drawMaze();
            mazePanel.add(this.maze);
            percentageTravel.setText("% of Cells To Win: " + this.mazeGenerator.cellDistribution());
            this.maze.setPreferredSize(new Dimension(mazeWidth * cellSize, mazeHeight * cellSize));
            this.revalidate();
            this.repaint();
        });
        // toggle solution
        sideBar.add(toggleSolution);
        toggleSolution.setPreferredSize(new Dimension(150, 40));
        toggleSolution.addActionListener(e -> {
            // toggle solution implementation
            System.out.println("Toggle Solution");
            mazePanel.removeAll();
            this.mazeGenerator.toggleSolution();
            this.maze = this.mazeGenerator.drawMaze();
            mazePanel.add(this.maze);
            this.maze.setPreferredSize(new Dimension(mazeWidth * cellSize, mazeHeight * cellSize));
            this.revalidate();
            this.repaint();
        });

        // statistics
        sideBar.add(statistics);
        statistics.setBorder(BorderFactory.createLineBorder(new Color(181, 181, 181), 2, true));
        statistics.setPreferredSize(new Dimension(230, 70));
        statistics.add(percentageTravel);
        percentageTravel.setPreferredSize(new Dimension(200, 15));
        statistics.add(percentageDeadEnd);
        percentageDeadEnd.setPreferredSize(new Dimension(200, 15));
        statistics.add(difficulty);
        difficulty.setPreferredSize(new Dimension(200, 15));

        // others
        sideBar.add(save);
        sideBar.add(export);
        export.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            int response = fileChooser.showSaveDialog(null); // select where to save file
            if (response == JFileChooser.APPROVE_OPTION) {
                File file = new File(fileChooser.getSelectedFile().getAbsolutePath());
                try {
                    ExportImage.exportImage(this.maze, file + ".png");
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
        sideBar.add(exit);

        exit.addActionListener(e -> {
            System.out.println("Exit");
            int answer = JOptionPane.showOptionDialog(
                    null,
                    "Would you like to save before exiting?",
                    "Untitled Maze",
                    JOptionPane.YES_NO_CANCEL_OPTION,
                    JOptionPane.WARNING_MESSAGE,
                    null,
                    answers,
                    0);
            if (answer == 0) {
                // save the maze
                System.out.println("Save");
            }
            if (answer == 1) {
                // don't save and exit
                System.out.println("Don't Save");
                this.dispose();
                HomePage homePage = new HomePage();
            }
        });


        this.setVisible(true);
    }
}
