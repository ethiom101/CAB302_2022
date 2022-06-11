package GUI;

import Database.BrowseMazeData;
import Maze.Cell;
import Maze.Grid;
import Maze.Maze;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

import static GUI.HomePage.*;
import static Util.Images.*;
import static Maze.MazeFile.saveMaze;

/**
 * GUI for the edit maze page
 */
public class EditMaze extends JFrame {

    private final JPanel mazePanel = new JPanel();
    private final JPanel sideBar = new JPanel();

    // Maze Components
    public static Grid mazeGrid;
    private int cellSize = 70;
    private int mazeWidth = 10;
    private int mazeHeight = 10;


    // Side Bar Components
    //  new grid
    private final JPanel newGrid = new JPanel();
    private JSpinner mazeRows;
    private JSpinner mazeColumns;
    private final JButton newGridButton = new JButton("New Grid");
    // cell slider
    private final JSlider cellSlider = new JSlider(0, 100, cellSize);
    // item picker
    private final ImageIcon IMG = new ImageIcon("MazeMaker/arrow.png");
    private final JPanel itemPicker = new JPanel();
    private final String[] items = {"Start", "End", "Wall", "Logo",};
    private String selectedItem;
    public JComboBox<String> itemSelector = new JComboBox<>(items);
    private final JButton changeImage = new JButton("Change");
    private final JButton resetImage = new JButton("Reset");
    private final JButton rotateImage = new JButton("Rotate");
    public JRadioButton topWall = new JRadioButton("Top");
    public JRadioButton downWall = new JRadioButton("Down");
    public JRadioButton leftWall = new JRadioButton("Left");
    public JRadioButton rightWall = new JRadioButton("Right");
    private final ButtonGroup wallSelections = new ButtonGroup();
    private final JPanel selectedImage = new JPanel();
    public JLabel startImage = new JLabel();
    private final JLabel endImage = new JLabel();
    private final JLabel logoImage = new JLabel();
    // generate maze
    private final JButton generateMaze = new JButton("Generate New Maze");
    // toggle solution
    public JButton toggleSolution = new JButton("Toggle Solution");
    // statistics
    private final JPanel statistics = new JPanel();
    private final JLabel percentageTravel = new JLabel("% of Cells To Win:");
    private final JLabel percentageDeadEnd = new JLabel("% of Dead Ends:");
    // others
    private final JButton save = new JButton("Save");
    private final JButton update = new JButton("Update");
    private final JButton export = new JButton("Export");
    private final JButton exit = new JButton("Exit");
    private final String[] answers = {"Save", "Don't Save", "Cancel"};

    // constructor for creating a new maze
    public EditMaze() {
        this.setTitle("untitled maze");
        initGUI();
        update.setVisible(false);


        exit.addActionListener(e -> {
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
                new SaveMaze();
            }
            if (answer == 1) {
                // don't save and exit
                this.dispose();
                new HomePage();
            }
        });
    }

    // constructor for opening existing maze
    public EditMaze(Maze maze) {
        this.setTitle(maze.getName());
        this.mazeWidth = maze.getWidth();
        this.mazeHeight = maze.getHeight();
        initGUI();
        generateMaze.setVisible(false);
        newGridButton.setVisible(false);
        mazeRows.setEnabled(false);
        mazeColumns.setEnabled(false);
        save.setVisible(false);
        update.setVisible(true);

        update.addActionListener(e -> {
            Maze mazeUpdated = new Maze(
                    maze.getAuthor(),
                    maze.getName(),
                    maze.getHeight(),
                    maze.getWidth(),
                    String.valueOf(java.time.LocalDate.now()),
                    saveMaze(maze.getWidth(), maze.getHeight()),
                    null,
                    null,
                    null,
                    null);
            mazeUpdated.setID(maze.getID());
            data.update(mazeUpdated);
            data = new BrowseMazeData();
            JOptionPane.showMessageDialog(null,
                    maze.getName() + "saved",
                    "Save Successful",
                    JOptionPane.INFORMATION_MESSAGE);
        });

        exit.addActionListener(e -> {
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
            }
            if (answer == 1) {
                // don't save and exit
                this.dispose();
                mazeBrowser = new BrowseMaze();
                mazeBrowser.open();
            }
        });
    }

    public void initGUI() {
        Cell.start = IMG;
        Cell.end = IMG;
        Cell.logo = null;

        // Frame

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setMinimumSize(new Dimension(250, 725));
        this.setResizable(false);
        this.setLayout(new BorderLayout());

        // Adding components to frame
        this.add(sideBar, BorderLayout.WEST);
        this.add(mazePanel, BorderLayout.CENTER);

        // Creating Components

        // Maze
        mazePanel.setBackground(new Color(234, 234, 234));
        mazeGrid = new Grid(mazeWidth, mazeHeight, cellSize);
        mazePanel.add(mazeGrid);
        mazeGrid.setPreferredSize(new Dimension(mazeWidth * cellSize, mazeHeight * cellSize));
        mazeGrid.setBorder(BorderFactory.createLineBorder(Color.gray));


        // Sidebar
        sideBar.setBackground(new Color(234, 234, 234));
        sideBar.setPreferredSize(new Dimension(250, 100));
        sideBar.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 20));
        // Change maze size
        sideBar.add(newGrid);
        newGrid.setBorder(BorderFactory.createLineBorder(new Color(181, 181, 181), 2, true));
        newGrid.setPreferredSize(new Dimension(230, 150));
        newGrid.add(new JLabel("Rows:"));
        mazeRows = new JSpinner(new SpinnerNumberModel(mazeWidth, 5, 100, 1));
        newGrid.add(mazeRows);
        mazeRows.setPreferredSize(new Dimension(50, 25));
        newGrid.add(new JLabel("Columns:"));
        mazeColumns = new JSpinner(new SpinnerNumberModel(mazeHeight, 5, 100, 1));
        newGrid.add(mazeColumns);
        mazeColumns.setPreferredSize(new Dimension(50, 25));
        newGrid.add(newGridButton);
        newGridButton.setPreferredSize(new Dimension(200, 25));
        newGridButton.addActionListener(e -> {
                    // update grid implementation
                    this.mazeWidth = (int) mazeRows.getValue();
                    this.mazeHeight = (int) mazeColumns.getValue();
                    this.cellSize = cellSlider.getValue();
                    mazeGrid = new Grid(this.mazeWidth, this.mazeHeight, this.cellSize);
                    mazePanel.removeAll();
                    mazePanel.add(mazeGrid);
                    mazeGrid.setPreferredSize(new Dimension(mazeWidth * cellSize, mazeHeight * cellSize));
                    mazeGrid.setBorder(BorderFactory.createLineBorder(Color.lightGray));
                    mazeGrid.revalidate();
                    mazeGrid.repaint();
                    mazeGrid.setCellSize(cellSize);
                    mazePanel.revalidate();
                    mazePanel.repaint();
                    itemSelector.setEnabled(true);
                    topWall.setEnabled(true);
                    leftWall.setEnabled(true);
                    downWall.setEnabled(true);
                    rightWall.setEnabled(true);
                    leftWall.setSelected(true);
                    leftWall.setSelected(true);
                    downWall.setSelected(true);
                    topWall.setSelected(true);
                    changeImage.setEnabled(true);
                    rotateImage.setEnabled(true);
                    toggleSolution.setEnabled(false);
                    this.pack();
                }
        );

        // Cell size slider
        JLabel cellSizeLabel = new JLabel(String.valueOf(cellSize));
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
            if (cellSlider.getValue() == 0) {
                cellSlider.setValue(1);
            }
            cellSizeLabel.setText(String.valueOf(cellSlider.getValue()));
            this.cellSize = cellSlider.getValue();
            mazeGrid.setPreferredSize(new Dimension(mazeWidth * cellSize, mazeHeight * cellSize));
            if (mazeGrid.getStart() != null) {
                mazeGrid.getStart().setImage(Cell.start, cellSize);
            }
            if (mazeGrid.getEnd() != null) {
                mazeGrid.getEnd().setImage(Cell.end, cellSize);
            }
            if (mazeGrid.getLogo() != null) {
                mazeGrid.getLogo().setImage(Cell.logo, cellSize);
            }


            mazeGrid.setCellSize(cellSize);
            mazeGrid.repaint();
            mazeGrid.revalidate();
            pack();
        });


        // Item Picker
        sideBar.add(itemPicker);
        itemPicker.setBorder(BorderFactory.createLineBorder(new Color(181, 181, 181), 2, true));
        itemPicker.setPreferredSize(new Dimension(230, 100));
        itemPicker.add(new JLabel("Item Picker:"));
        itemPicker.add(itemSelector);
        itemSelector.setPreferredSize(new Dimension(100, 25));
        // change image button
        itemPicker.add(changeImage);
        selectedImage.add(startImage);
        startImage.setIcon(resizeImage(IMG, 100, 100));

        selectedImage.add(endImage);
        endImage.setIcon(resizeImage(IMG, 100, 100));


        changeImage.addActionListener(e -> {
            // change image implementation
            JFileChooser fileChooser = new JFileChooser();
            int response = fileChooser.showOpenDialog(null); // select file to open
            if (response == JFileChooser.APPROVE_OPTION) {
                // resizing image
                File file = new File(fileChooser.getSelectedFile().getAbsolutePath());
                ImageIcon imageLogo = new ImageIcon(String.valueOf(file));
                imageLogo = resizeImage(imageLogo, 100, 100);
                if (itemSelector.getSelectedItem() == "Start") {
                    startImage.setIcon(imageLogo);
                    Cell.start = imageLogo;
                    if (mazeGrid.getStart() != null) {
                        mazeGrid.getStart().setIcon(imageLogo);
                        mazeGrid.getStart().setIcon(resizeImage(imageLogo, this.cellSize, this.cellSize));
                    }
                }
                if (itemSelector.getSelectedItem() == "End") {
                    endImage.setIcon(imageLogo);
                    Cell.end = imageLogo;
                    if (mazeGrid.getEnd() != null) {
                        mazeGrid.getEnd().setIcon(imageLogo);
                        mazeGrid.getEnd().setIcon(resizeImage(imageLogo, this.cellSize, this.cellSize));
                    }
                }
                if (itemSelector.getSelectedItem() == "Logo") {
                    Cell.logo = imageLogo;
                    logoImage.setIcon(imageLogo);
                    if (mazeGrid.getLogo() != null) {
                        mazeGrid.getLogo().setIcon(imageLogo);
                        mazeGrid.getLogo().setIcon(resizeImage(imageLogo, this.cellSize, this.cellSize));
                    }
                }
                resetImage.setEnabled(true);
            }
        });
        // reset image button
        itemPicker.add(resetImage);
        resetImage.setEnabled(false);
        resetImage.addActionListener(e -> {
            // remove logo implementation
            if (itemSelector.getSelectedItem() == "Start") {
                startImage.setIcon(resizeImage(IMG, 100, 100));
                if (mazeGrid.getStart() != null) {
                    mazeGrid.getStart().setIcon(resizeImage(IMG, this.cellSize, this.cellSize));
                    Cell.start = IMG;
                }
            }
            if (itemSelector.getSelectedItem() == "End") {
                endImage.setIcon(resizeImage(IMG, 100, 100));
                if (mazeGrid.getEnd() != null) {
                    mazeGrid.getEnd().setIcon(resizeImage(IMG, this.cellSize, this.cellSize));
                    Cell.end = IMG;
                }
            }
            if (itemSelector.getSelectedItem() == "Logo") {
                logoImage.setIcon(null);
                Cell.logo = null;
                if (mazeGrid.getLogo() != null) {
                    mazeGrid.getLogo().resetCell();
                    mazeGrid.getLogo().setIcon(null);
                }
            }
            resetImage.setEnabled(false);

        });
        // rotate image button
        itemPicker.add(rotateImage);
        rotateImage.addActionListener((e -> {

            if (itemSelector.getSelectedItem() == "Start") {
                try {
                    Cell.start = rotateImage(Cell.start);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                if (mazeGrid.getStart() != null) {
                    mazeGrid.getStart().setImage(Cell.start, this.cellSize);
                }
                startImage.setIcon(resizeImage(Cell.start, 100, 100));
            }

            if (itemSelector.getSelectedItem() == "End") {
                try {
                    Cell.end = rotateImage(Cell.end);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                if (mazeGrid.getEnd() != null) {
                    mazeGrid.getEnd().setImage(Cell.end, this.cellSize);
                }
                endImage.setIcon(resizeImage(Cell.end, 100, 100));

            }
            if (itemSelector.getSelectedItem() == "Logo") {
                if (Cell.logo != null) {
                    try {
                        Cell.logo = rotateImage(Cell.logo);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    if (mazeGrid.getLogo() != null) {
                        mazeGrid.getLogo().setImage(Cell.logo, this.cellSize);
                    }
                    logoImage.setIcon(resizeImage(Cell.logo, 100, 100));
                }
            }
        }));


        // wall selector
        itemPicker.add(topWall);
        topWall.setVisible(false);
        itemPicker.add(downWall);
        downWall.setVisible(false);
        itemPicker.add(leftWall);
        leftWall.setVisible(false);
        itemPicker.add(rightWall);
        rightWall.setVisible(false);
        wallSelections.add(topWall);
        wallSelections.add(downWall);
        wallSelections.add(leftWall);
        wallSelections.add(rightWall);
        // logo
        sideBar.add(selectedImage);
        selectedImage.setBorder(BorderFactory.createLineBorder(new Color(181, 181, 181), 2, true));
        selectedImage.setPreferredSize(new Dimension(110, 110));
        selectedImage.add(logoImage);
        logoImage.setIcon(null);


        itemSelector.addActionListener(e -> {
            // item selector implementation
            if (itemSelector.getSelectedItem() == "Wall") {
                topWall.setVisible(true);
                downWall.setVisible(true);
                leftWall.setVisible(true);
                rightWall.setVisible(true);
                changeImage.setVisible(false);
                resetImage.setVisible(false);
                rotateImage.setVisible(false);
                selectedImage.setVisible(false);
                logoImage.setVisible(false);
                endImage.setVisible(false);
                startImage.setVisible(false);
            } else if (itemSelector.getSelectedItem() == "Logo") {
                changeImage.setVisible(true);
                topWall.setVisible(false);
                downWall.setVisible(false);
                leftWall.setVisible(false);
                rightWall.setVisible(false);
                resetImage.setVisible(true);
                rotateImage.setVisible(true);
                selectedImage.setVisible(true);
                logoImage.setVisible(true);
                endImage.setVisible(false);
                startImage.setVisible(false);
            } else if (itemSelector.getSelectedItem() == "End") {
                changeImage.setVisible(true);
                resetImage.setVisible(true);
                rotateImage.setVisible(true);
                topWall.setVisible(false);
                downWall.setVisible(false);
                leftWall.setVisible(false);
                rightWall.setVisible(false);
                selectedImage.setVisible(true);
                logoImage.setVisible(false);
                endImage.setVisible(true);
                startImage.setVisible(false);
            } else if (itemSelector.getSelectedItem() == "Start") {
                changeImage.setVisible(true);
                resetImage.setVisible(true);
                rotateImage.setVisible(true);
                topWall.setVisible(false);
                downWall.setVisible(false);
                leftWall.setVisible(false);
                rightWall.setVisible(false);
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
            this.mazeWidth = (int) mazeRows.getValue();
            this.mazeHeight = (int) mazeColumns.getValue();
            this.cellSize = cellSlider.getValue();
            mazeGrid = new Grid(this.mazeWidth, this.mazeHeight, this.cellSize);
            mazePanel.removeAll();
            try {
                mazeGrid.drawMaze(this.mazeHeight, this.mazeWidth);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            mazePanel.add(mazeGrid);

            percentageDeadEnd.setText("% of Dead Ends:");
            percentageTravel.setText("% of Cells To Win:");
            mazeGrid.setPreferredSize(new Dimension(mazeWidth * cellSize, mazeHeight * cellSize));
            mazeGrid.setBorder(BorderFactory.createLineBorder(Color.lightGray));
            mazeGrid.revalidate();
            mazeGrid.repaint();
            mazeGrid.setCellSize(cellSize);
            mazePanel.revalidate();
            mazePanel.repaint();
            itemSelector.setEnabled(true);
            topWall.setEnabled(true);
            leftWall.setEnabled(true);
            downWall.setEnabled(true);
            rightWall.setEnabled(true);
            leftWall.setSelected(true);
            leftWall.setSelected(true);
            downWall.setSelected(true);
            topWall.setSelected(true);
            changeImage.setEnabled(true);
            rotateImage.setEnabled(true);
            this.pack();
        });
        // toggle solution
        sideBar.add(toggleSolution);
        toggleSolution.setEnabled(false);
        toggleSolution.setPreferredSize(new Dimension(150, 40));
        toggleSolution.addActionListener(e -> {
            mazeGrid.Solve();
            percentageTravel.setText("% of Cells To Win: " + mazeGrid.getCellDist());
            percentageDeadEnd.setText("% of Dead Ends: " + mazeGrid.getDeadEnds());
            if (!mazeGrid.toggle) {
                itemSelector.setEnabled(false);
                selectedItem = (String) itemSelector.getSelectedItem();
                itemSelector.setSelectedItem(null);
                topWall.setEnabled(false);
                leftWall.setEnabled(false);
                downWall.setEnabled(false);
                rightWall.setEnabled(false);
                leftWall.setSelected(false);
                leftWall.setSelected(false);
                downWall.setSelected(false);
                topWall.setSelected(false);
                changeImage.setEnabled(false);
                rotateImage.setEnabled(false);
            } else {
                itemSelector.setEnabled(true);
                itemSelector.setSelectedItem(selectedItem);
                topWall.setEnabled(true);
                leftWall.setEnabled(true);
                downWall.setEnabled(true);
                rightWall.setEnabled(true);
                leftWall.setSelected(true);
                leftWall.setSelected(true);
                downWall.setSelected(true);
                topWall.setSelected(true);
                changeImage.setEnabled(true);
                rotateImage.setEnabled(true);
            }
        });

        // statistics
        sideBar.add(statistics);
        statistics.setBorder(BorderFactory.createLineBorder(new Color(181, 181, 181), 2, true));
        statistics.setPreferredSize(new Dimension(230, 50));
        statistics.add(percentageTravel);
        percentageTravel.setPreferredSize(new Dimension(200, 15));
        statistics.add(percentageDeadEnd);
        percentageDeadEnd.setPreferredSize(new Dimension(200, 15));

        // others
        sideBar.add(save);
        sideBar.add(update);
        save.addActionListener(e -> new SaveMaze());

        sideBar.add(export);
        export.addActionListener(e -> {
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
        });
        sideBar.add(exit);


        itemSelector.setEnabled(true);
        itemSelector.setSelectedItem("Start");
        topWall.setEnabled(true);
        leftWall.setEnabled(true);
        downWall.setEnabled(true);
        rightWall.setEnabled(true);
        leftWall.setSelected(true);
        leftWall.setSelected(true);
        downWall.setSelected(true);
        topWall.setSelected(true);
        changeImage.setEnabled(true);
        rotateImage.setEnabled(true);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    public int returnHeight() {
        return this.mazeHeight;
    }

    public int returnWidth() {
        return this.mazeWidth;
    }
}
