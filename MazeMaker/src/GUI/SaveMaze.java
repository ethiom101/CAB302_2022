package GUI;

import javax.swing.*;
import java.awt.*;

/**
 * GUI for the save maze page
 */
public class SaveMaze extends JFrame {

    public SaveMaze(){
        initUI();
    }

    public void initUI(){
        JFrame frame = new JFrame();
        frame.setSize(300,200);
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

        frame.add(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Save Maze");
        //frame.pack();

        frame.setVisible(true);
    }
}
