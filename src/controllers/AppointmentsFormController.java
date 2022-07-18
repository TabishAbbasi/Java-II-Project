package controllers;

import database.AppointmentsQuery;
import database.UsersQuery;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import main.Main;
import model.AlertGenerator;
import model.Appointment;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.IsoFields;
import java.util.Optional;

public class AppointmentsFormController {
    private Stage stage;
    private ObservableList<Appointment> appointmentList = FXCollections.observableArrayList();
    @FXML
    private TableView<Appointment> appointmentTable;
    @FXML
    private TableColumn<Appointment, Integer> appIdCol;
    @FXML
    private TableColumn<Appointment, String> appTitleCol;
    @FXML
    private TableColumn<Appointment, String> appDescCol;
    @FXML
    private TableColumn<Appointment, String> appLocCol;
    @FXML
    private TableColumn<Appointment, String> appConCol;
    @FXML
    private TableColumn<Appointment, String> appTypeCol;
    @FXML
    private TableColumn<Appointment, LocalDate> appDateCol;
    @FXML
    private TableColumn<Appointment, LocalTime> appStartCol;
    @FXML
    private TableColumn<Appointment, LocalTime> appEndCol;
    @FXML
    private TableColumn<Appointment, Integer> appCustIdCol;
    @FXML
    private TableColumn<Appointment, Integer> appUserIdCol;
    @FXML
    private Text currentUserText;

    @FXML
    public void initialize() throws SQLException {
        currentUserText.setText("User: " + UsersQuery.getUserName());
        AppointmentsQuery.retrieveAllAppointments(appointmentList);

        appointmentTable.setItems(appointmentList);
        appIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        appTitleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        appDescCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        appLocCol.setCellValueFactory(new PropertyValueFactory<>("location"));
        appConCol.setCellValueFactory(new PropertyValueFactory<>("contact"));
        appTypeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        appDateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
        appStartCol.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        appEndCol.setCellValueFactory(new PropertyValueFactory<>("endTime"));
        appCustIdCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        appUserIdCol.setCellValueFactory(new PropertyValueFactory<>("userId"));
    }

    public void showAllAppointments(ActionEvent event) throws SQLException {
        AppointmentsQuery.retrieveAllAppointments(appointmentList);
        appointmentTable.setItems(appointmentList);
    }

    public void showMonthAppointments(ActionEvent event) throws SQLException {
        AppointmentsQuery.retrieveAllAppointments(appointmentList);
        LocalDate localDate = LocalDate.now();
        ObservableList<Appointment> filteredAppList = FXCollections.observableArrayList();
        for(Appointment currApp : appointmentList){
            if(localDate.getMonth() == currApp.getDate().getMonth()){
                filteredAppList.add(currApp);
            }
        }
        appointmentTable.setItems(filteredAppList);
    }

    public void showWeekAppointments(ActionEvent event) throws SQLException {
        AppointmentsQuery.retrieveAllAppointments(appointmentList);
        int weekNumber = LocalDate.now().get(IsoFields.WEEK_OF_WEEK_BASED_YEAR);

        ObservableList<Appointment> filteredAppList = FXCollections.observableArrayList();
        for(Appointment currApp : appointmentList){
            int appWeekNumber = currApp.getDate().get(IsoFields.WEEK_OF_WEEK_BASED_YEAR);
            if(weekNumber == appWeekNumber){
                filteredAppList.add(currApp);
            }
        }
        appointmentTable.setItems(filteredAppList);
    }

    public void addAppointment(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load((Main.class.getResource("/views/AddAppointmentForm.fxml"))), 800, 500));
        stage.setResizable(false);
        stage.show();
    }

    public void updateAppointment(ActionEvent event) throws IOException, SQLException {
        if(!appointmentTable.getSelectionModel().isEmpty()){
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/views/UpdateAppointmentForm.fxml"));
            loader.load();

            UpdateAppointmentFormController uAFController = loader.getController();
            uAFController.receiveAppointment(appointmentTable.getSelectionModel().getSelectedItem());

            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.setResizable(false);
            stage.show();
        } else{
            AlertGenerator.generateErrorAlert("Please select an appointment from the table.");
        }
    }

    public void deleteAppointment(ActionEvent event) throws SQLException {
        if(!appointmentTable.getSelectionModel().isEmpty()){
            Appointment deleteAppointment = appointmentTable.getSelectionModel().getSelectedItem();
            Optional<ButtonType> choice = AlertGenerator.generateConfirmAlert("Delete appointment ID: " + deleteAppointment.getId() +
                                                                                " Title: " + deleteAppointment.getTitle() + "?");

            if(choice.isPresent() && choice.get() == ButtonType.OK){
                AppointmentsQuery.deleteAppointment(deleteAppointment.getId());
                AppointmentsQuery.retrieveAllAppointments(appointmentList);
                appointmentTable.setItems(appointmentList);
                AlertGenerator.generateInfoAlert("Appointment ID: " + deleteAppointment.getId() + " has been removed.");
            }

        } else {
            AlertGenerator.generateErrorAlert("Please select an appointment from the table.");
        }
    }

    public void toCustomerForm(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load((Main.class.getResource("/views/CustomerForm.fxml"))), 1600, 400));
        stage.setResizable(false);
        stage.show();
    }
}
