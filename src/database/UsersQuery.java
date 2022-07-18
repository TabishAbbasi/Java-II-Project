package database;

import javafx.collections.ObservableList;
import model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class UsersQuery {
    private static String loggedInUserName;
    public static boolean loginAttempt(String userName, String password) throws SQLException {
        boolean loginAccepted = false;
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
                break;
            }
        }
        if(!loginAccepted){
            System.out.println("The username or password is incorrect.");
        }

        return loginAccepted;
    }

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

    public static String getUserName(){
        return loggedInUserName;
    }

}
