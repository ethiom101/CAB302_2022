package GUI;

import javax.swing.*;
import java.awt.*;

import Maze.Maze;
import Database.BrowseMazeData;
import static GUI.HomePage.*;

/**
 * GUI for the save maze page
 */
public class SaveMaze extends JFrame {

    public SaveMaze(){
        initUI();
    }

    public void initUI(){
        this.setSize(300,200);
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(30,30,10,10));
        panel.setLayout(new GridLayout(0,2));
        JButton saveButton = new JButton("Save");
        JLabel name = new JLabel("Name");
        JLabel author = new JLabel("Author");
        JTextField nameInput = new JTextField();
        JTextField authorInput = new JTextField();

        panel.add(name);
        panel.add(nameInput);
        panel.add(author);
        panel.add(authorInput);
        panel.add(saveButton);

        this.add(panel);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setTitle("Save Maze");

        saveButton.addActionListener(e -> {
            // Checks if both fields have been inputed
            if ((nameInput.getText() != null) && !(nameInput.getText().equals(("")))
            && (authorInput.getText() != null) && !(authorInput.getText().equals(("")))) {
                // Creates a new maze
                Maze maze = new Maze(
                        authorInput.getText(),
                        nameInput.getText(),
                        mazeEditor.returnHeight(),
                        mazeEditor.returnWidth(),
                        String.valueOf(java.time.LocalDate.now()),
                        null,
                        null,
                        null,
                        null,
                        null);
                // adds the maze to the database
                data.add(maze);
                // updates the instance of the data used in the current application
                data = new BrowseMazeData();
                this.dispose();
                JOptionPane.showMessageDialog(null,
                        maze.getName() + " saved to database",
                        "Save Successful",
                        JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null,
                        "Ensure all fields are filled out",
                        "Error Saving",
                        JOptionPane.ERROR_MESSAGE);
            }
        });
        this.setVisible(true);
    }
}
