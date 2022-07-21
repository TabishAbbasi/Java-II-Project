package model;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * This class produces an Appointment object to store appointment information.
 *
 * @author Tabish Abbasi
 */
public class Appointment {
    private int id;
    private String title;
    private String description;
    private String location;
    private String contactName;
    private String type;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private int customerId;
    private int userId;

    /**
     * Constructor for the Appointment class/
     *
     * @param id the appointment's ID
     * @param title the appointment's title
     * @param description the appointment's description
     * @param location the appointment's location
     * @param contactName the appointment's contact's name
     * @param type the appointment's type
     * @param date the appointment's date
     * @param startTime the appointment's start time
     * @param endTime the appointment's end time
     * @param customerId the appointment's customer's ID
     * @param userId the ID of the user who created the appointment
     */
    public Appointment(int id, String title, String description, String location, String contactName, String type, LocalDate date, LocalTime startTime, LocalTime endTime, int customerId, int userId) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.location = location;
        this.contactName = contactName;
        this.type = type;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.customerId = customerId;
        this.userId = userId;
    }

    /**
     * Returns the appointment's ID
     *
     * @return the appointment's ID
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the appointment's ID
     *
     * @param id the ID to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Returns the appointment's title.
     *
     * @return the appointment's title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the appointment's title.
     *
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Returns the appointment's description.
     *
     * @return the appointment's description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the appointment's description.
     *
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Returns the appointment's location.
     *
     * @return the appointment's location
     */
    public String getLocation() {
        return location;
    }

    /**
     * Sets the appointment's location.
     *
     * @param location the location to set
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * Returns the appointment's contact's name.
     *
     * @return the appointment's contact's name
     */
    public String getContactName() {
        return contactName;
    }

    /**
     * Sets the appointment's contact's name
     *
     * @param contactName the contact's name to set
     */
    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    /**
     * Returns the appointment's type.
     *
     * @return the appointment's type
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the appointment's type.
     *
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Returns the appointment's date.
     *
     * @return the appointment's date
     */
    public LocalDate getDate() {
        return date;
    }

    /**
     * Sets the appointment's date.
     *
     * @param date the date to set
     */
    public void setDate(LocalDate date) {
        this.date = date;
    }

    /**
     * Returns the appointment's start time.
     *
     * @return the appointment's start time
     */
    public LocalTime getStartTime() {
        return startTime;
    }

    /**
     * Sets the appointment's start time.
     *
     * @param startTime the start time to set
     */
    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    /**
     * Returns the appointment's end time.
     *
     * @return the appointment's end time
     */
    public LocalTime getEndTime() {
        return endTime;
    }

    /**
     * Sets the appointment's end time.
     *
     * @param endTime the end time to set
     */
    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    /**
     * Returns the appointment's customer's ID.
     *
     * @return the appointment's customer's ID.
     */
    public int getCustomerId() {
        return customerId;
    }

    /**
     * Sets the appointment's customer's ID.
     *
     * @param customerId the customer's ID to set
     */
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    /**
     * Returns the appointment's creator's ID.
     *
     * @return the appointment's creator's ID
     */
    public int getUserId() {
        return userId;
    }

    /**
     * Sets the appointment's creator's ID.
     *
     * @param userId the creator's ID to set
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }
}
