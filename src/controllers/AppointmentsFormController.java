package controllers;

import database.AppointmentsQuery;
import database.UsersQuery;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
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

/**
 * This class handles all operations in the appointment form.
 */
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

    /**
     * Adds all appointments from the database into the table.
     *
     * @throws SQLException
     */
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

    /**
     * Displays all appointments from the database into the table.
     *
     * @param event the All radio button being clicked
     * @throws SQLException
     */
    public void showAllAppointments(ActionEvent event) throws SQLException {
        AppointmentsQuery.retrieveAllAppointments(appointmentList);
        appointmentTable.setItems(appointmentList);
    }

    /**
     * Displays all appointments from the database that begin in the current month. A lambda expression
     * is used to filter the appointments from a list containing all the appointments.
     *
     * @param event the Current Month radio button being clicked
     * @throws SQLException
     */
    public void showMonthAppointments(ActionEvent event) throws SQLException {
        AppointmentsQuery.retrieveAllAppointments(appointmentList);
        FilteredList<Appointment> filteredList = new FilteredList<>(appointmentList, app -> //Lambda
                app.getDate().getMonth().equals(LocalDate.now().getMonth()));
        appointmentTable.setItems(filteredList);
    }

    /**
     * Displays all appointments from the database that begin in the current week. A lambda expression
     * is used to filter the appointments from a list containing all the appointments.
     *
     * @param event the Current Week radio button being clicked
     * @throws SQLException
     */
    public void showWeekAppointments(ActionEvent event) throws SQLException {
        AppointmentsQuery.retrieveAllAppointments(appointmentList);
        int weekNumber = LocalDate.now().get(IsoFields.WEEK_OF_WEEK_BASED_YEAR); // https://stackhowto.com/how-to-get-the-week-number-from-a-date-in-java/
        FilteredList<Appointment> filteredList = new FilteredList<>(appointmentList, app -> //Lambda
                weekNumber == app.getDate().get(IsoFields.WEEK_OF_WEEK_BASED_YEAR));
        appointmentTable.setItems(filteredList);
    }

    /**
     * Changes the current stage's scene to the add appointment form.
     *
     * @param event the add button being clicked
     * @throws IOException
     */
    public void addAppointment(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load((Main.class.getResource("/views/AddAppointmentForm.fxml"))), 800, 500));
        stage.setResizable(false);
        stage.show();
    }

    /**
     * Changes the current stage's scene to the update appointment form and sends the selected appointment to the
     * forms controller.
     *
     * @param event the update button being clicked
     * @throws IOException
     */
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

    /**
     * Deletes the currently selected appointment from the database.
     *
     * @param event
     * @throws SQLException
     */
    public void deleteAppointment(ActionEvent event) throws SQLException {
        if(!appointmentTable.getSelectionModel().isEmpty()){
            Appointment deleteAppointment = appointmentTable.getSelectionModel().getSelectedItem();
            Optional<ButtonType> choice = AlertGenerator.generateConfirmAlert(
                    "Delete appointment ID: " + deleteAppointment.getId() + " Title: " + deleteAppointment.getTitle() + "?");
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

    /**
     * Changes the current stage's scene to the contact schedule form.
     *
     * @param event the contact schedule button being clicked
     * @throws IOException
     */
    public void toContactSchedule(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load((Main.class.getResource("/views/ContactScheduleForm.fxml"))), 1600, 400));
        stage.setResizable(false);
        stage.show();
    }

    /**
     * Changes the current stage's scene to the appointment report form.
     *
     * @param event the appointment report button being clicked
     * @throws IOException
     */
    public void toAppointmentReport(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load((Main.class.getResource("/views/AppointmentReportForm.fxml"))), 600, 400));
        stage.setResizable(false);
        stage.show();
    }

    /**
     * Changes the current stage's scene to the customer form.
     *
     * @param event the customers button being clicked
     * @throws IOException
     */
    public void toCustomerForm(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load((Main.class.getResource("/views/CustomerForm.fxml"))), 1600, 400));
        stage.setResizable(false);
        stage.show();
    }
}
