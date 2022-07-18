package database;

import javafx.collections.ObservableList;
import model.Contact;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ContactsQuery {
    public static void retrieveAllContacts(ObservableList<Contact> contactList) throws SQLException {
        contactList.clear();
        String sql = "SELECT * FROM CONTACTS";
        PreparedStatement ps = JDBC.makePreparedStatement(sql);
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            int id = rs.getInt("Contact_ID");
            String name = rs.getString("Contact_Name");
            String email = rs.getString("Email");
            contactList.add(new Contact(id, name, email));
        }
    }

    public static Contact retrieveContactByName(String contactName) throws SQLException {
        String sql = "SELECT FROM CONTACTS WHERE Contact_Name = ?";
        PreparedStatement ps = JDBC.makePreparedStatement(sql);
        ps.setString(1, contactName);
        ResultSet rs = ps.executeQuery();
        Contact contact = new Contact(rs.getInt("Contact_ID"), rs.getString("Contact_Name"), rs.getString("Email"));
        return contact;
    }

    public static String retrieveContactName(int id) throws SQLException {
        String sql = "SELECT FROM CONTACTS WHERE Contact_ID = ?";
        PreparedStatement ps = JDBC.makePreparedStatement(sql);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        return rs.getString("Contact_Name");
    }
}
