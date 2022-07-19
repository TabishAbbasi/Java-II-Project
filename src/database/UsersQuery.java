package database;

import javafx.collections.ObservableList;
import model.User;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;

public abstract class UsersQuery {
    private static String loggedInUserName;
    public static boolean loginAttempt(String userName, String password) throws SQLException, IOException {
        boolean loginAccepted = false;
        File loginTxt = new File("/login_activity.exe");
        FileWriter fw = new FileWriter(loginTxt);

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
                fw.write("User " + userName + " successfully logged in at " + LocalDateTime.now(ZoneId.of("UTC")) + "UTC\n");
                break;
            }
        }
        if(!loginAccepted){
            System.out.println("The username or password is incorrect.");
            fw.write("User " + userName + " gave invalid login at " + LocalDateTime.now(ZoneId.of("UTC")) + "UTC\n");
        }

        fw.flush();
        fw.close();
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

    public static String getUserName(){
        return loggedInUserName;
    }

}
