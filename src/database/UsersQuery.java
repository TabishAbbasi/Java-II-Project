package database;

import javafx.collections.ObservableList;
import model.User;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;

/**
 * This class handles any queries to the User table in the database.
 *
 * @author Tabish Abbasi
 */
public abstract class UsersQuery {
    private static String loggedInUserName;

    /**
     * Queries the database to find a User with a matching username and password
     * and records each success or failure into the login_activity.txt file.
     *
     * @param userName the entered username
     * @param password the entered password
     * @return true if the credentials find a match, false otherwise
     * @throws SQLException
     * @throws IOException
     */
    public static boolean loginAttempt(String userName, String password) throws SQLException, IOException {
        boolean loginAccepted = false;
        File loginTxt = new File("login_activity.txt");
        loginTxt.createNewFile();
        FileWriter fw = new FileWriter(loginTxt, true); // https://stackoverflow.com/questions/13729625/overwriting-txt-file-in-java

        String sql = "SELECT * FROM USERS";
        PreparedStatement ps = JDBC.makePreparedStatement(sql);
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            String currentUserName = rs.getString("User_Name");
            String currentPassword = rs.getString("Password");

            if(currentUserName.equals(userName) && currentPassword.equals(password)){
                System.out.println("Login Successful!");
                System.out.println(userName + " has logged in.");
                loginAccepted = true;
                loggedInUserName = userName;
                fw.write("User " + userName + " successfully logged in at " + LocalDate.now() + " " + LocalTime.now(ZoneId.of("UTC")) + " UTC\n");
                break;
            }
        }
        if(!loginAccepted){
            System.out.println("The username or password is incorrect.");
            fw.write("User " + userName + " gave invalid login at " + LocalDate.now() + " " + LocalTime.now(ZoneId.of("UTC")) + " UTC\n");
        }

        fw.flush();
        fw.close();
        return loginAccepted;
    }

    /**
     * Retrieves all users from the User table in the database and stores them in a list.
     *
     * @param userList the list to store all the queried users
     * @throws SQLException
     */
    public static void retrieveAllUsers(ObservableList<User> userList) throws SQLException {
        userList.clear();
        String sql = "SELECT * FROM USERS";
        PreparedStatement ps = JDBC.makePreparedStatement(sql);
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            int id = rs.getInt("User_ID");
            String userName = rs.getString("User_Name");
            userList.add(new User(id, userName));
        }
    }

    /**
     * Retrieves a single user from the database that matches the entered ID.
     *
     * @param userId the users matching ID
     * @return the user with the matching ID
     * @throws SQLException
     */

    public static User retrieveUserById(int userId) throws SQLException {
        String sql = "SELECT * FROM USERS WHERE User_ID = ?";
        PreparedStatement ps = JDBC.makePreparedStatement(sql);
        ps.setInt(1, userId);
        ResultSet rs = ps.executeQuery();
        rs.next();
        int id = rs.getInt("User_ID");
        String userName = rs.getString("User_Name");
        User user = new User(id, userName);
        return user;
    }

    /**
     * Retrieves a single user from the database that matches the entered username
     *
     * @param name the users matching username
     * @return the user with the matching username
     * @throws SQLException
     */
    public static User retrieveUserByName(String name) throws SQLException {
        String sql = "SELECT * FROM USERS WHERE User_Name = ?";
        PreparedStatement ps = JDBC.makePreparedStatement(sql);
        ps.setString(1, name);
        ResultSet rs = ps.executeQuery();
        rs.next();
        int id = rs.getInt("User_ID");
        String userName = rs.getString("User_Name");
        User user = new User(id, userName);
        return user;
    }

    /**
     * Returns the currently logged-in user's username.
     *
     * @return the currently logged-in user's username
     */
    public static String getUserName(){
        return loggedInUserName;
    }

}
