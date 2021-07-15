package connection;

import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnect {
    Connection connect;

    public static Connection Connect() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection connect = DriverManager.getConnection("jdbc:mysql://localhost/movie_rental", "root", "root");

            return connect;
        } catch(Exception e) {
            JOptionPane.showMessageDialog(null, e);

            return null;
        }
    }
}
