package database;

import javafx.collections.ObservableList;
import model.Contact;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This class handles all queries to the Contacts table in the database.
 *
 * @author Tabish Abbasi
 */
public class ContactsQuery {
    /**
     * Retrieves all contacts in the database and stores them in a list.
     *
     * @param contactList the list to store all the contacts
     * @throws SQLException
     */
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

    /**
     * Retrieves a single contact with the matching name.
     *
     * @param contactName the contact's name
     * @return the contact with the matching name
     * @throws SQLException
     */
    public static Contact retrieveContactByName(String contactName) throws SQLException {
        String sql = "SELECT * FROM CONTACTS WHERE Contact_Name = ?";
        PreparedStatement ps = JDBC.makePreparedStatement(sql);
        ps.setString(1, contactName);
        ResultSet rs = ps.executeQuery();
        rs.next();
        Contact contact = new Contact(rs.getInt("Contact_ID"), rs.getString("Contact_Name"), rs.getString("Email"));
        return contact;
    }

    /**
     * Retrieves the name of the contact with the matching ID.
     *
     * @param contactId the contact's ID
     * @return the name of the matching contact
     * @throws SQLException
     */
    public static String retrieveContactName(int contactId) throws SQLException {
        String sql = "SELECT * FROM CONTACTS WHERE Contact_ID = ?";
        PreparedStatement ps = JDBC.makePreparedStatement(sql);
        ps.setInt(1, contactId);
        ResultSet rs = ps.executeQuery();
        rs.next();
        return rs.getString("Contact_Name");
    }
}
