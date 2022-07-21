package model;

/**
 * This class produces a Country object to store country information.
 *
 * @author Tabish Abbasi
 */
public class Country {
    private int id;
    private String name;

    /**
     * Constructor for the Country class.
     *
     * @param id the Country ID
     * @param name the Country name
     */
    public Country(int id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * Returns the Country's ID.
     *
     * @return the Country's ID
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the Country's ID.
     *
     * @param id the ID to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Returns the Country's name.
     *
     * @return the Country's name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the Country's name.
     *
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the Country's name as a string.
     *
     * @return the Country's name as a string
     */
    @Override
    public String toString(){
        return this.name;
    }
}
