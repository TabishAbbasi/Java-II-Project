package database;

import javafx.collections.ObservableList;
import model.Appointment;
import model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class AppointmentsQuery {
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

    public static void retrieveAllAppointmentsById(int searchId, ObservableList<Appointment> appointmentList) throws SQLException {
        appointmentList.clear();
        String sql = "SELECT FROM APPOINTMENTS WHERE Customer_ID = ?";
        PreparedStatement ps = JDBC.makePreparedStatement(sql);
        ps.setInt(1, searchId);
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

    public static void updateAppointment(int appId, String title, String description, String location, String type, LocalDateTime startDateTime,
                                         LocalDateTime endDateTime, User user, int customerId, int contactId) throws SQLException {
        String sql = "UPDATE APPOINTMENTS SET Title = ? Description = ? Location = ? Type = ? Start = ? End = ? " +
                "Last_Update = ? Last_Updated_By = ? Customer_ID = ? User_ID = ? Contact_ID = ? WHERE Appointment_ID = ?";
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

    public static void deleteAppointment(int id) throws SQLException {
        String sql = "DELETE FROM APPOINTMENTS WHERE Appointment_ID = ?";
        PreparedStatement ps = JDBC.makePreparedStatement(sql);
        ps.setInt(1, id);
        int rowsAffected = ps.executeUpdate();
        System.out.println(rowsAffected + " appointments deleted.");
    }

    public static void deleteAllAppointmentsById(int customerId) throws SQLException {
        String sql = "DELETE FROM APPOINTMENTS WHERE Customer_ID = ?";
        PreparedStatement ps = JDBC.makePreparedStatement(sql);
        ps.setInt(1, customerId);
        int rowsAffected = ps.executeUpdate();
        System.out.println(rowsAffected + " appointments deleted.");
    }
}
