package controllers;

import database.AppointmentsQuery;
import database.ContactsQuery;
import database.CustomerQuery;
import database.UsersQuery;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import main.Main;
import model.*;

import java.io.IOException;
import java.sql.SQLException;
import java.time.*;

public class UpdateAppointmentFormController {
    private ObservableList<Contact> contactList = FXCollections.observableArrayList();
    private ObservableList<Customer> customerList = FXCollections.observableArrayList();
    private ObservableList<User> userList = FXCollections.observableArrayList();
    private Appointment selectedAppointment = null;
    @FXML
    private TextField idTextBox;
    @FXML
    private TextField titleTextBox;
    @FXML
    private TextField locTextBox;
    @FXML
    private TextField typeTextBox;
    @FXML
    private TextArea descTextArea;
    @FXML
    private ComboBox<Contact> conComBox;
    @FXML
    private ComboBox<Customer> cusComBox;
    @FXML
    private ComboBox<User> userComBox;
    @FXML
    private DatePicker datePicker;
    @FXML
    private ComboBox<LocalTime> startComBox;
    @FXML
    private ComboBox<LocalTime> endComBox;

    public LocalDateTime adjustToEst(LocalTime localTime){
        ZoneId estZoneId = ZoneId.of("America/New_York");
        LocalDateTime localDateTime = LocalDateTime.of(LocalDate.now(), localTime); // 8AM Local
        ZonedDateTime zonedLocalDateTime = localDateTime.atZone(ZoneId.systemDefault()); // 8AM LocalZone

        ZonedDateTime estZonedDateTime = zonedLocalDateTime.withZoneSameInstant(estZoneId); // 8AM EST Zone
        LocalDateTime estLocalDateTime = estZonedDateTime.toLocalDateTime(); // 8AM EST Local
        return estLocalDateTime;
    }

    public void receiveAppointment(Appointment appointment) throws SQLException {
        selectedAppointment = appointment;
        idTextBox.setText(String.valueOf(appointment.getId()));
        titleTextBox.setText(appointment.getTitle());
        locTextBox.setText(appointment.getLocation());
        typeTextBox.setText(appointment.getType());
        descTextArea.setText(appointment.getDescription());
        datePicker.setValue(appointment.getDate());

        ContactsQuery.retrieveAllContacts(contactList);
        conComBox.setItems(contactList);
        Contact contact = ContactsQuery.retrieveContactByName(appointment.getContact());
        conComBox.setValue(contact);

        CustomerQuery.retrieveAllCustomers(customerList);
        cusComBox.setItems(customerList);
        Customer customer = CustomerQuery.retrieveCustomerById(appointment.getCustomerId());
        cusComBox.setValue(customer);

        UsersQuery.retrieveAllUsers(userList);
        userComBox.setItems(userList);
        User user = UsersQuery.retrieveUserById(appointment.getUserId());
        userComBox.setValue(user);

        LocalDateTime openingTime = adjustToEst(LocalTime.of(8,0)); // 8AM
        LocalDateTime closingTime = adjustToEst(LocalTime.of(22,0)); // 10PM

        while(openingTime.isBefore(closingTime.plusSeconds(1))){
            startComBox.getItems().add(openingTime.toLocalTime());
            endComBox.getItems().add(openingTime.toLocalTime());
            openingTime = openingTime.plusMinutes(5);
        }

        startComBox.setValue(appointment.getStartTime());
        endComBox.setValue(appointment.getEndTime());
    }

    public boolean checkForOverlap(LocalTime start, LocalTime end) throws SQLException {
        boolean noConflict = true;
        Customer customer = cusComBox.getValue();
        ObservableList<Appointment> appointmentList = FXCollections.observableArrayList();

        AppointmentsQuery.retrieveAllAppointmentsByCustomer(customer.getId(), appointmentList);
        for(Appointment currApp : appointmentList){
            if(currApp.getId() != selectedAppointment.getId()){
                if(currApp.getStartTime().isBefore(start) && currApp.getEndTime().isAfter(start)){
                    noConflict = false;
                    break;
                }

                if(currApp.getStartTime().isBefore(end) && currApp.getEndTime().isAfter(end)){
                    noConflict = false;
                    break;
                }

                if(currApp.getStartTime().isAfter(start) && currApp.getEndTime().isBefore(end)){
                    noConflict = false;
                    break;
                }
            }
        }

        return noConflict;
    }

    public void onSave(ActionEvent event) throws SQLException, IOException {
        if(titleTextBox.getText().isBlank() || locTextBox.getText().isBlank() ||
                typeTextBox.getText().isBlank() || descTextArea.getText().isBlank()){
            AlertGenerator.generateErrorAlert("Fields must not be Empty.");

        } else{
            LocalTime startTime = startComBox.getSelectionModel().getSelectedItem();
            LocalTime endTime = endComBox.getSelectionModel().getSelectedItem();

            if(startTime.equals(endTime)){
                AlertGenerator.generateErrorAlert("Appointment start time and end times cannot be the same.");

            } else if(startTime.isAfter(endTime)) {
                AlertGenerator.generateErrorAlert("Appointment start time cannot occur before the end time.");

            } else if(!checkForOverlap(startTime, endTime)){
                AlertGenerator.generateErrorAlert("The timing of this appointment overlaps with another appointment.");

            } else{
                int id = Integer.parseInt(idTextBox.getText());
                String title = titleTextBox.getText();
                String description = descTextArea.getText();
                String location = locTextBox.getText();
                String type = typeTextBox.getText();
                LocalDate localDate = datePicker.getValue();
                LocalDateTime startDateTime = LocalDateTime.of(localDate, startTime);
                LocalDateTime endDateTime = LocalDateTime.of(localDate, endTime);
                User user = userComBox.getValue();
                Customer customer = cusComBox.getValue();
                Contact contact = conComBox.getValue();

                AppointmentsQuery.updateAppointment(id, title, description, location, type, startDateTime, endDateTime, user, customer.getId(), contact.getId());
                toAppointmentForm(event);
            }
        }
    }

    public void toAppointmentForm(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load((Main.class.getResource("/views/AppointmentForm.fxml"))), 1600, 400));
        stage.setResizable(false);
        stage.show();
    }
}
