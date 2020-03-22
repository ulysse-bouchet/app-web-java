package persistance;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Class that connects the app to the database
 * @author Bouchet Ulysse & Tadjer Badr
 */
public class DBConnect {
    //Database adress
    private static final String ADRESS = "jdbc:oracle:thin:@localhost:1521:XE";
    //Database user
    private static final String USER = "ulysse";
    //Database password
    private static final String PASSWORD = "password";

    //The instance of the connection
    private static Connection conn;

    //Loads the Oracle driver class
    static {
        try {
            Class.forName("oracle.jdbc.OracleDriver");
        } catch (ClassNotFoundException e) { e.printStackTrace(); }
    }

    /**
     * Get a connection to the database, to prevent timeouts
     * @return The instance of the connection to the database
     */
    public static Connection connect() {
        try {
            //If the connection is closed or has not been initialized
            if (conn == null || conn.isClosed()) {
                //Sets the connection to the database
                conn = DriverManager.getConnection(ADRESS, USER, PASSWORD);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //Returns the instance of the database
        return conn;
    }
}
