package database;

import javafx.collections.ObservableList;
import model.Customer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * This class handles all queries to the Customers table in the database.
 *
 * @author Tabish Abbasi
 */
public abstract class CustomerQuery {

    /**
     * Retrieves all the customers in the database and stores them in a list.
     *
     * @param customerList the list to store the customers
     * @throws SQLException
     */
    public static void retrieveAllCustomers(ObservableList<Customer> customerList) throws SQLException {
        customerList.clear();
        String sql = "SELECT * FROM CUSTOMERS";
        PreparedStatement ps = JDBC.makePreparedStatement(sql);
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            int id = rs.getInt("Customer_ID");
            String name = rs.getString("Customer_Name");
            String address = rs.getString("Address");
            String postalCode = rs.getString("Postal_Code");
            String phone = rs.getString("Phone");
            int divisionId = rs.getInt("Division_ID");
            customerList.add(new Customer(id, name, address, postalCode, phone, divisionId));
        }

    }

    /**
     * Retrieves a single customer with the matching ID.
     *
     * @param customerId the customer's ID
     * @return the customer with the matching ID
     * @throws SQLException
     */
    public static Customer retrieveCustomerById(int customerId) throws SQLException {
        String sql = "SELECT * FROM CUSTOMERS WHERE Customer_ID = ?";
        PreparedStatement ps = JDBC.makePreparedStatement(sql);
        ps.setInt(1, customerId);
        ResultSet rs = ps.executeQuery();
        rs.next();
        int id = rs.getInt("Customer_ID");
        String name = rs.getString("Customer_Name");
        String address = rs.getString("Address");
        String postalCode = rs.getString("Postal_Code");
        String phone = rs.getString("Phone");
        int divisionId = rs.getInt("Division_ID");
        Customer customer = new Customer(id, name, address, postalCode, phone, divisionId);
        return customer;
    }

    /**
     * Inserts a customer into the database.
     *
     * @param name the customer's name
     * @param address the customer's address
     * @param postalCode the customer's postal code
     * @param phone the customer's phone number
     * @param divisionId the customer's province/division's ID
     * @throws SQLException
     */
    public static void insertCustomer(String name, String address, String postalCode, String phone, int divisionId) throws SQLException {
        String sql = "INSERT INTO CUSTOMERS (Customer_Name, Address, Postal_Code, Phone, Create_Date," +
                " Created_By, Last_Update, Last_Updated_By, Division_ID) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = JDBC.makePreparedStatement(sql);
        ps.setString(1, name);
        ps.setString(2, address);
        ps.setString(3, postalCode);
        ps.setString(4, phone);
        ps.setTimestamp(5, Timestamp.valueOf(LocalDateTime.now())); //DATETIME
        ps.setString( 6, UsersQuery.getUserName());
        ps.setTimestamp(7, Timestamp.valueOf(LocalDateTime.now())); //TIMESTAMP
        ps.setString(8, UsersQuery.getUserName());
        ps.setInt(9, divisionId);
        int rowsAffected = ps.executeUpdate();
        System.out.println(rowsAffected + " rows added to customers table.");
    }

    /**
     * Updates the customer in the database with the matching ID.
     *
     * @param customerId the customer's ID
     * @param name the customer's name
     * @param address the customer's address
     * @param postalCode the customer's postal code
     * @param phone the customer's phone number
     * @param divisionId the customer's province/division's ID
     * @throws SQLException
     */
    public static void updateCustomer(int customerId, String name, String address, String postalCode, String phone, int divisionId) throws SQLException {
        String sql = "UPDATE CUSTOMERS SET Customer_Name = ?, Address = ?, Postal_Code = ?, Phone = ?, " +
                    "Last_Update = ?, Last_Updated_By = ?, Division_ID = ? WHERE Customer_ID = ?";
        PreparedStatement ps = JDBC.makePreparedStatement(sql);
        ps.setString(1, name);
        ps.setString(2, address);
        ps.setString(3, postalCode);
        ps.setString(4, phone);
        ps.setTimestamp(5, Timestamp.valueOf(LocalDateTime.now()));
        ps.setString(6, UsersQuery.getUserName());
        ps.setInt(7, divisionId);
        ps.setInt(8, customerId);
        int rowsAffected = ps.executeUpdate();
        System.out.println(rowsAffected + " rows updated.");
    }

    /**
     * Deletes a customer from the database with the matching ID.
     *
     * @param customerId the customer's ID
     * @throws SQLException
     */
    public static void deleteCustomer(int customerId) throws SQLException {
        AppointmentsQuery.deleteAllAppointmentsById(customerId);
        String sql = "DELETE FROM CUSTOMERS WHERE Customer_ID = ?";
        PreparedStatement ps = JDBC.makePreparedStatement(sql);
        ps.setInt(1, customerId);
        int rowsAffected = ps.executeUpdate();
        System.out.println(rowsAffected + " customers deleted.");
    }
}
