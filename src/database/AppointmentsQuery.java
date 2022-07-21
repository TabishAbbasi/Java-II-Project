package database;

import javafx.collections.ObservableList;
import model.Appointment;
import model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * This class handles any queries to the Appointments table in the database.
 *
 * @author Tabish Abbasi
 */
public class AppointmentsQuery {

    /**
     * Retrieves all appointments from the database and stores them in a list.
     *
     * @param appointmentList the list to store all the appointments
     * @throws SQLException
     */
    public static void retrieveAllAppointments(ObservableList<Appointment> appointmentList) throws SQLException {
        appointmentList.clear();
        String sql = "SELECT * FROM APPOINTMENTS";
        PreparedStatement ps = JDBC.makePreparedStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()){
            int id = rs.getInt("Appointment_ID");
            String title = rs.getString("Title");
            String description = rs.getString("Description");
            String location = rs.getString("Location");
            String type = rs.getString("Type");
            LocalDateTime startDateTime = rs.getTimestamp("Start").toLocalDateTime();
            LocalDateTime endDateTime = rs.getTimestamp("End").toLocalDateTime();
            int customerId = rs.getInt("Customer_ID");
            int userId = rs.getInt("User_ID");
            int contactId = rs.getInt("Contact_ID");
            String contactName = ContactsQuery.retrieveContactName(contactId);

            appointmentList.add(new Appointment(id, title, description, location, contactName, type, startDateTime.toLocalDate(),
                                                startDateTime.toLocalTime(), endDateTime.toLocalTime(), customerId, userId));
        }
    }

    /**
     * Retrieves all the appointments in the database matching the customer ID and stores them in
     * a list.
     *
     * @param customerSearchId the matching customer ID
     * @param appointmentList the list to store all the appointments in
     * @throws SQLException
     */
    public static void retrieveAllAppointmentsByCustomer(int customerSearchId, ObservableList<Appointment> appointmentList) throws SQLException {
        appointmentList.clear();
        String sql = "SELECT * FROM APPOINTMENTS WHERE Customer_ID = ?";
        PreparedStatement ps = JDBC.makePreparedStatement(sql);
        ps.setInt(1, customerSearchId);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            int id = rs.getInt("Appointment_ID");
            String title = rs.getString("Title");
            String description = rs.getString("Description");
            String location = rs.getString("Location");
            String type = rs.getString("Type");
            LocalDateTime startDateTime = rs.getTimestamp("Start").toLocalDateTime();
            LocalDateTime endDateTime = rs.getTimestamp("End").toLocalDateTime();
            int customerId = rs.getInt("Customer_ID");
            int userId = rs.getInt("User_ID");
            int contactId = rs.getInt("Contact_ID");
            String contactName = ContactsQuery.retrieveContactName(contactId);

            appointmentList.add(new Appointment(id, title, description, location, contactName, type, startDateTime.toLocalDate(),
                    startDateTime.toLocalTime(), endDateTime.toLocalTime(), customerId, userId));
        }
    }

    /**
     * Retrieves all appointments from the database matching the contact ID and storing them in a list
     *
     * @param contactSearchId the matching contact ID
     * @param appointmentList the list to store all the appointments
     * @throws SQLException
     */
    public static void retrieveAllAppointmentsByContact(int contactSearchId, ObservableList<Appointment> appointmentList) throws SQLException {
        appointmentList.clear();
        String sql = "SELECT * FROM APPOINTMENTS WHERE Contact_ID = ?";
        PreparedStatement ps = JDBC.makePreparedStatement(sql);
        ps.setInt(1, contactSearchId);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            int id = rs.getInt("Appointment_ID");
            String title = rs.getString("Title");
            String description = rs.getString("Description");
            String location = rs.getString("Location");
            String type = rs.getString("Type");
            LocalDateTime startDateTime = rs.getTimestamp("Start").toLocalDateTime();
            LocalDateTime endDateTime = rs.getTimestamp("End").toLocalDateTime();
            int customerId = rs.getInt("Customer_ID");
            int userId = rs.getInt("User_ID");
            int contactId = rs.getInt("Contact_ID");
            String contactName = ContactsQuery.retrieveContactName(contactId);

            appointmentList.add(new Appointment(id, title, description, location, contactName, type, startDateTime.toLocalDate(),
                    startDateTime.toLocalTime(), endDateTime.toLocalTime(), customerId, userId));
        }
    }

    /**
     * Inserts an appointment into the database.
     *
     * @param title the appointment title
     * @param description the appointment description
     * @param location the appointment location
     * @param type the appointment type
     * @param startDateTime the appointment start datetime
     * @param endDateTime the appointment end datetime
     * @param user the user that inserted the appointment
     * @param customerId the customer's ID pertaining to the appointment
     * @param contactId the contact's ID pertaining to the appointment
     * @throws SQLException
     */
    public static void insertAppointment(String title, String description, String location, String type, LocalDateTime startDateTime,
                                         LocalDateTime endDateTime, User user, int customerId, int contactId) throws SQLException {
        String sql = "INSERT INTO APPOINTMENTS (Title, Description, Location, Type, Start, End, Create_Date," +
                " Created_By, Last_Update, Last_Updated_By, Customer_ID, User_ID, Contact_ID) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = JDBC.makePreparedStatement(sql);
        ps.setString(1, title);
        ps.setString(2, description);
        ps.setString(3, location);
        ps.setString(4, type);
        ps.setTimestamp(5, Timestamp.valueOf(startDateTime));
        ps.setTimestamp(6, Timestamp.valueOf(endDateTime));
        ps.setTimestamp(7, Timestamp.valueOf(LocalDateTime.now()));
        ps.setString(8, user.getUserName());
        ps.setTimestamp(9, Timestamp.valueOf(LocalDateTime.now()));
        ps.setString(10, user.getUserName());
        ps.setInt(11, customerId);
        ps.setInt(12, user.getId());
        ps.setInt(13, contactId);
        int rowsAffected = ps.executeUpdate();
        System.out.println(rowsAffected + " rows added to appointments table.");
    }

    /**
     * Updates an appointment's values excluding its ID.
     *
     * @param appId the matching appointment's ID
     * @param title the appointment title
     * @param description the appointment description
     * @param location the appointment location
     * @param type the appointment type
     * @param startDateTime the appointment start datetime
     * @param endDateTime the appointment end datetime
     * @param user the user the updated the appointment
     * @param customerId the customer's ID pertaining to the appointment
     * @param contactId the contact's ID pertaining to the appointment
     * @throws SQLException
     */
    public static void updateAppointment(int appId, String title, String description, String location, String type, LocalDateTime startDateTime,
                                         LocalDateTime endDateTime, User user, int customerId, int contactId) throws SQLException {
        String sql = "UPDATE APPOINTMENTS SET Title = ?, Description = ?, Location = ?, Type = ?, Start = ?, End = ?, " +
                "Last_Update = ?, Last_Updated_By = ?, Customer_ID = ?, User_ID = ?, Contact_ID = ? WHERE Appointment_ID = ?";
        PreparedStatement ps = JDBC.makePreparedStatement(sql);
        ps.setString(1, title);
        ps.setString(2, description);
        ps.setString(3, location);
        ps.setString(4, type);
        ps.setTimestamp(5, Timestamp.valueOf(startDateTime));
        ps.setTimestamp(6, Timestamp.valueOf(endDateTime));
        ps.setTimestamp(7, Timestamp.valueOf(LocalDateTime.now()));
        ps.setString(8, user.getUserName());
        ps.setInt(9, customerId);
        ps.setInt(10, user.getId());
        ps.setInt(11, contactId);
        ps.setInt(12, appId);
        int rowsAffected = ps.executeUpdate();
        System.out.println(rowsAffected + " rows updated.");

    }

    /**
     * Deletes an appointment with the matching appointment's ID.
     *
     * @param appointmentId the matching ID
     * @throws SQLException
     */
    public static void deleteAppointment(int appointmentId) throws SQLException {
        String sql = "DELETE FROM APPOINTMENTS WHERE Appointment_ID = ?";
        PreparedStatement ps = JDBC.makePreparedStatement(sql);
        ps.setInt(1, appointmentId);
        int rowsAffected = ps.executeUpdate();
        System.out.println(rowsAffected + " appointments deleted.");
    }

    /**
     * Deletes all appointments with the matching customer's ID.
     *
     * @param customerId the matching customer's ID
     * @throws SQLException
     */
    public static void deleteAllAppointmentsById(int customerId) throws SQLException {
        String sql = "DELETE FROM APPOINTMENTS WHERE Customer_ID = ?";
        PreparedStatement ps = JDBC.makePreparedStatement(sql);
        ps.setInt(1, customerId);
        int rowsAffected = ps.executeUpdate();
        System.out.println(rowsAffected + " appointments deleted.");
    }
}
