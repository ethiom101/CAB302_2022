package Database;

import javax.swing.*;
import javax.swing.filechooser.*;
import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

/**
 * Class for connection to the Database from the application
 */
public class Database {

    public static Connection instance = null;

    /**
     * METHOD MODIFIED FROM CAB302 LECTURE 6 CODE
     * The singleton instance of the database connection.
     */
    private Database() throws IOException, SQLException {
        Properties props = new Properties();
        FileInputStream in;
        String filepath = "MazeMaker/db.props";
        File propsFile = new File(filepath);
        while (true) {
            try {
                if (!propsFile.getAbsolutePath().endsWith("props"))
                    throw new FileNotFoundException("Not a properties file");
                in = new FileInputStream(propsFile);
                props.load(in);
                in.close();
            } catch (FileNotFoundException e) {
                String[] answers = {"Select File", "Exit"};
                JOptionPane.showConfirmDialog(null, String.format("Issue with db.props\nError Reads: \"%s\"", e.getMessage()));
                System.exit(0);
            }

            // specify the data source, username and password
            String url = props.getProperty("jdbc.url");
            String username = props.getProperty("jdbc.username");
            String password = props.getProperty("jdbc.password");
            String schema = props.getProperty("jdbc.schema");


            try {
                instance = DriverManager.getConnection(url , username, password);
                Statement stmt = instance.createStatement();
                stmt.executeUpdate("CREATE DATABASE IF NOT EXISTS mazeco");
                stmt.executeUpdate("USE mazeco");
            } catch (SQLException e) {
                String[] answers = {"Retry Connection", "Exit"};
                int answer = JOptionPane.showOptionDialog(
                        null,
                        String.format("Issue connecting to database\nError Reads: \"%s\"", e.getMessage()),
                        "Error Connecting to DB",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE,
                        null,
                        answers,
                        JOptionPane.CANCEL_OPTION);
                switch (answer) {
                    case JOptionPane.YES_OPTION:
                        continue;
                    case JOptionPane.NO_OPTION:
                        System.exit(0);
                }
            }
            break;
        }
    }


    /**
     * METHOD TAKEN FROM CAB302 LECTURE 6 CODE
     * Provides global access to the singleton instance of the UrlSet.
     *
     * @return a handle to the singleton instance of the UrlSet.
     */
    public static Connection getInstance() {
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

