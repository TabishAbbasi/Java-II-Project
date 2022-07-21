package model;

/**
 * This class produces a Division object to store division information.
 *
 * @author Tabish Abbasi
 */
public class Division {
    private int id;
    private String name;

    /**
     * Constructor for the Division class.
     *
     * @param id the Division ID
     * @param name the Division name
     */
    public Division(int id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * Returns the Division's ID.
     *
     * @return the Division's ID
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the Division's ID.
     *
     * @param id the ID to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Returns the Division's name.
     *
     * @return the Division's name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the Division's name.
     *
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the Division's name as a string.
     *
     * @return the Division's name as a string
     */
    @Override
    public String toString(){
        return this.name;
    }
}
