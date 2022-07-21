package controllers;

import database.AppointmentsQuery;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import main.Main;
import model.Appointment;

import java.io.IOException;
import java.sql.SQLException;
import java.time.Month;

/**
 * This class handles all operations in the appointment report form.
 *
 * @author Tabish Abbasi
 */
public class AppointmentReportFormController {
    ObservableList<Appointment> appointmentList = FXCollections.observableArrayList();
    ObservableList<Month> monthList = FXCollections.observableArrayList();
    ObservableList<String> typeList = FXCollections.observableArrayList();

    @FXML
    private ComboBox<Month> monthComBox;
    @FXML
    private ComboBox<String> typeComBox;
    @FXML
    private Label numOfApp;

    /**
     * Adds all months with appointments and all appointment types into their respective
     * combo boxes and updates the number of appointments to the current selection.
     *
     * @throws SQLException
     */
    @FXML
    public void initialize() throws SQLException {
        AppointmentsQuery.retrieveAllAppointments(appointmentList);
        for(Appointment currApp : appointmentList){
            if(!monthList.contains(currApp.getDate().getMonth())){
                monthList.add(currApp.getDate().getMonth());
            }
            if(!typeList.contains(currApp.getType())){
                typeList.add(currApp.getType());
            }
        }

        monthComBox.setItems(monthList);
        monthComBox.getSelectionModel().selectFirst();

        typeComBox.setItems(typeList);
        typeComBox.getSelectionModel().selectFirst();

        Month selectedMonth = monthComBox.getValue();
        String selectedType = typeComBox.getValue();
        int matchingAppointments = 0;
        for(Appointment currApp : appointmentList){
            if(currApp.getDate().getMonth().equals(selectedMonth) && currApp.getType().equals(selectedType)){
                matchingAppointments++;
            }
        }
        numOfApp.setText(String.valueOf(matchingAppointments));
    }

    /**
     * Updates the number of appointments to the current selection.
     */
    public void selectionChanged(){
        Month selectedMonth = monthComBox.getValue();
        String selectedType = typeComBox.getValue();
        int matchingAppointments = 0;
        for(Appointment currApp : appointmentList){
            if(currApp.getDate().getMonth().equals(selectedMonth) && currApp.getType().equals(selectedType)){
                matchingAppointments++;
            }
        }
        numOfApp.setText(String.valueOf(matchingAppointments));
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
