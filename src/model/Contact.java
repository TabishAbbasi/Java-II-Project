package model;

/**
 * This class produces a Contact object to store contact information.
 */
public class Contact {
    private int id;
    private String name;
    private String email;

    /**
     * Constructor for the Contact class.
     *
     * @param id the Contact ID
     * @param name the Contact name
     * @param email the Contact email
     */
    public Contact(int id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    /**
     * Returns the Contact's ID.
     *
     * @return the Contact's ID
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the Contact's ID.
     *
     * @param id the ID to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Returns the Contact's name.
     *
     * @return the Contact's name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the Contact's name.
     *
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the Contact's email.
     *
     * @return the Contact's email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the Contact's email.
     *
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Returns the Contact's name as a string.
     *
     * @return the Contact's name as a string
     */
    @Override
    public String toString(){
        return this.name;
    }
}
