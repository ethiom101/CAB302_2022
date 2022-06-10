package Database;

import javax.swing.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;


public class Database {

    public static Connection instance = null;

    /**
     * Constructor initializes the connection.
     */
    // METHOD TAKEN FROM CAB302 LECTURE 6 CODE
    private Database() throws IOException, SQLException {
        Properties props = new Properties();
        FileInputStream in;
        try {
            in = new FileInputStream("MazeMaker/db.props");
            props.load(in);
            in.close();

            // specify the data source, username and password
            String url = props.getProperty("jdbc.url");

            String username = props.getProperty("jdbc.username");
            String password = props.getProperty("jdbc.password");
            String schema = props.getProperty("jdbc.schema");

            // get a connection
            instance = DriverManager.getConnection(url + "/" + schema, username,
                    password);
        } catch (SQLException | FileNotFoundException SQL) {
            String[] answers = {"Select File", "Exit"};
            int answer = JOptionPane.showOptionDialog(
                    null,
                    "Issue with 'db.props', could not find file or username/password is incorrect or schema is incorrect or port is wrong, would you like to select a file from your device?",
                    "Database File Not Found",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE,
                    null,
                    answers,
                    0);
            if (answer == 0) {
                // Search for file
                JFileChooser fileChooser = new JFileChooser();
                int option = fileChooser.showOpenDialog(null);
                File file = fileChooser.getSelectedFile();
                // Retry
                if (option == JFileChooser.APPROVE_OPTION && (file.getAbsolutePath().endsWith(".props"))) {
                    in = new FileInputStream(file.getAbsolutePath());
                    props.load(in);
                    in.close();

                    // specify the data source, username and password
                    String url = props.getProperty("jdbc.url");

                    String username = props.getProperty("jdbc.username");
                    String password = props.getProperty("jdbc.password");
                    String schema = props.getProperty("jdbc.schema");

                    // get a connection
                    instance = DriverManager.getConnection(url + "/" + schema, username,
                            password);
                } else {
                    // Exit
                    JOptionPane.showMessageDialog(null,
                            "File selected was not a '.props file'",
                            "Invalid File Type",
                            JOptionPane.ERROR_MESSAGE);
                    System.exit(0);
                }
            }
        }
    }

        /**
         * Provides global access to the singleton instance of the UrlSet.
         *
         * @return a handle to the singleton instance of the UrlSet.
         */
        // METHOD TAKEN FROM CAB302 LECTURE 6 CODE
        public static Connection getInstance () {
            if (instance == null) {
                try {
                    new Database();
                } catch (IOException | SQLException e) {
                    e.printStackTrace();
                }
            }
            return instance;
        }
    }

