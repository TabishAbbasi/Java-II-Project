package model;

/**
 * This class produces a Customer object to store customer information.
 */
public class Customer {
    private int id;
    private String name;
    private String address;
    private String postalCode;
    private String phone;
    private int divisionId;

    /**
     * Constructor for the Customer class.
     *
     * @param id the Customer ID
     * @param name the Customer name
     * @param address the Customer address
     * @param postalCode the Customer postal code
     * @param phone the Customer phone number
     * @param divisionId the Customer division ID
     */
    public Customer(int id, String name, String address, String postalCode, String phone, int divisionId) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.postalCode = postalCode;
        this.phone = phone;
        this.divisionId = divisionId;
    }

    /**
     * Returns the Customer's ID.
     *
     * @return the Customer's ID
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the Customer's ID.
     *
     * @param id the ID to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Returns the Customer's name.
     *
     * @return the Customer's name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the Customer's name.
     *
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the Customer's address.
     *
     * @return the Customer's address
     */
    public String getAddress() {
        return address;
    }

    /**
     * Sets the Customer's address.
     *
     * @param address the address to set
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Returns the Customer's postal code.
     *
     * @return the Customer's postal code
     */
    public String getPostalCode() {
        return postalCode;
    }

    /**
     * Sets the Customer's postal code.
     *
     * @param postalCode the postal code to set
     */
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    /**
     * Returns the Customer's phone number.
     *
     * @return the Customer's phone number
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Sets the Customer's phone number.
     *
     * @param phone the phone number to set
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * Returns the Customer's division ID.
     *
     * @return the Customer's division ID
     */
    public int getDivisionId() {
        return divisionId;
    }

    /**
     * Sets the Customer's division ID.
     *
     * @param divisionId the division ID to set
     */
    public void setDivisionId(int divisionId) {
        this.divisionId = divisionId;
    }

    /**
     * Returns the Customer's ID as a string.
     *
     * @return the Customer's ID as a string
     */
    @Override
    public String toString(){
        return String.valueOf(this.id);
    }
}
