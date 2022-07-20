package model;

/**
 * This class produces a User object to store user information.
 */
public class User {
    private int id;
    private String userName;

    /**
     * Constructor for the User class.
     *
     * @param id the User ID
     * @param userName the User username
     */
    public User(int id, String userName) {
        this.id = id;
        this.userName = userName;
    }

    /**
     * Returns the User's ID.
     *
     * @return the User's ID
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the User's ID.
     *
     * @param id the ID to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Returns the User's username.
     *
     * @return the User's username
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Sets the User's username.
     *
     * @param userName the username to set
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * Returns the User's ID as a string.
     *
     * @return the User's ID as a string
     */
    @Override
    public String toString(){
        return String.valueOf(this.id);
    }
}
