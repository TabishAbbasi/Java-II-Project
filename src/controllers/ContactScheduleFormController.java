package controllers;

import database.AppointmentsQuery;
import database.ContactsQuery;
import database.UsersQuery;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import main.Main;
import model.Appointment;
import model.Contact;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalTime;
import java.util.Date;

/**
 * This class handles all operations in the contact schedule form.
 *
 * @author Tabish Abbasi
 */
public class ContactScheduleFormController {
    private ObservableList<Appointment> appointmentList = FXCollections.observableArrayList();
    private ObservableList<Contact> contactList = FXCollections.observableArrayList();
    @FXML
    private TableView<Appointment> scheduleTable;
    @FXML
    private TableColumn<Appointment, Integer> appIdCol;
    @FXML
    private TableColumn<Appointment, String> titleCol;
    @FXML
    private TableColumn<Appointment, String> typeCol;
    @FXML
    private TableColumn<Appointment, String> descCol;
    @FXML
    private TableColumn<Appointment, Date> dateCol;
    @FXML
    private TableColumn<Appointment, LocalTime> startCol;
    @FXML
    private TableColumn<Appointment, LocalTime> endCol;
    @FXML
    private TableColumn<Appointment, Integer> cusIdCol;
    @FXML
    private ComboBox<Contact> conComBox;
    @FXML
    private Text currentUserText;

    /**
     * Adds all contacts from the database to combo box and displays the selected contact's
     * appointments in the table.
     *
     * @throws SQLException
     */
    @FXML
    public void initialize() throws SQLException {
        currentUserText.setText("User: " + UsersQuery.getUserName());
        ContactsQuery.retrieveAllContacts(contactList);
        conComBox.setItems(contactList);
        conComBox.getSelectionModel().selectFirst();

        Contact contact = conComBox.getValue();
        AppointmentsQuery.retrieveAllAppointmentsByContact(contact.getId(), appointmentList);

        scheduleTable.setItems(appointmentList);
        appIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        descCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
        startCol.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        endCol.setCellValueFactory(new PropertyValueFactory<>("endTime"));
        cusIdCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));
    }

    /**
     * Updates the table to display the selected contact's appointments.
     *
     * @param event a value being set for the contact combo box
     * @throws SQLException
     */
    public void updateTable(ActionEvent event) throws SQLException {
        Contact contact = conComBox.getValue();
        AppointmentsQuery.retrieveAllAppointmentsByContact(contact.getId(), appointmentList);
        scheduleTable.setItems(appointmentList);
    }

    /**
     * Changes the current stage's scene to the appointment form.
     *
     * @param event the Appointments button being clicked
     * @throws IOException
     */
    public void toAppointmentForm(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load((Main.class.getResource("/views/AppointmentForm.fxml"))), 1600, 400));
        stage.setResizable(false);
        stage.show();
    }
}