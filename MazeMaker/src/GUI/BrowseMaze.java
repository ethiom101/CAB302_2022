package GUI;

import javax.swing.*;
import java.awt.*;

/**
 * GUI for the browse maze page
 */
public class BrowseMaze extends JFrame {
    public static void main(String[] args){
        new BrowseMaze();
    }

    public BrowseMaze(){
        initUI();
    }

    public void initUI(){
        JFrame frame = new JFrame();
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(30,30,10,10));
        panel.setLayout(new GridLayout(1,2));

        JButton SearchButton = new JButton("Search");
        JButton OpenButton = new JButton("Open");
        JLabel name = new JLabel("Name");
        JLabel author = new JLabel("Author");
        JTextField nameInput = new JTextField();
        JTextField authorInput = new JTextField();

        JList MazeList = new JList();
        MazeList.setFixedCellWidth(200);
        //Need to give scroll pane data
        JScrollPane scroller = new JScrollPane();
        scroller
                .setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scroller
                .setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scroller.setMinimumSize(new Dimension(200, 150));
        scroller.setPreferredSize(new Dimension(250, 150));
        scroller.setMaximumSize(new Dimension(250, 200));

        panel.add(scroller);
        panel.add(name);
        panel.add(nameInput);
        panel.add(author);
        panel.add(authorInput);
        panel.add(SearchButton);
        panel.add(OpenButton);


        frame.add(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Browse Maze");
        frame.pack();
        frame.setVisible(true);
    }
}
