package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * This class is responsible for establishing a connection with the database,
 * closing that connection, as well as producing prepared statements for
 * interacting with the database.
 */
public abstract class JDBC {
    private static final String protocol = "jdbc";
    private static final String vendor = ":mysql:";
    private static final String location = "//localhost/";
    private static final String databaseName = "client_schedule";
    private static final String jdbcUrl = protocol + vendor + location + databaseName + "?connectionTimeZone = SERVER"; // LOCAL
    private static final String driver = "com.mysql.cj.jdbc.Driver"; // Driver reference
    private static final String userName = "sqlUser"; // Username
    private static String password = "Passw0rd!"; // Password
    private static Connection connection = null;  // Connection Interface

    /**
     * Establishes a connection with the database.
     */
    public static void makeConnection() {

        try {
            Class.forName(driver); // Locate Driver
            connection = DriverManager.getConnection(jdbcUrl, userName, password); // reference Connection object
            System.out.println("Connection successful!");
        }
        catch(ClassNotFoundException e) {
            System.out.println("Error:" + e.getMessage());
        }
        catch(SQLException e) {
            System.out.println("Error:" + e.getMessage());
        }
    }

    /**
     * Closes the connection with the database.
     */
    public static void closeConnection() {
        try {
            connection.close();
            System.out.println("Connection closed!");
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    /**
     * Prepares and returns a PreparedStatement.
     *
     * @param sqlStatement the sql command for interacting with the database
     * @return the PreparedStatement
     * @throws SQLException
     */
    public static PreparedStatement makePreparedStatement(String sqlStatement) throws SQLException {
        PreparedStatement ps = null;
        if (connection != null){
            ps = connection.prepareStatement(sqlStatement);
        } else{
            System.out.println("Prepared Statement Creation Failed!");
        }

        return ps;
    }
}
