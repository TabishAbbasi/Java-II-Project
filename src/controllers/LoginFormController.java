package controllers;

import database.AppointmentsQuery;
import database.UsersQuery;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import main.Main;
import model.AlertGenerator;
import model.Appointment;
import model.User;

import java.io.IOException;
import java.sql.SQLException;
import java.time.*;
import java.util.Locale;
import java.util.ResourceBundle;

public class LoginFormController {
    private Stage stage;
    @FXML
    private Text titleText;
    @FXML
    private Label idLabel;
    @FXML
    private TextField idTextBox;
    @FXML
    private Label passwordLabel;
    @FXML
    private PasswordField passwordTextBox;
    @FXML
    private Button loginBtn;
    @FXML
    private Label zoneLabel;

    @FXML
    public void initialize(){
        zoneLabel.setText("Zone: " + ZoneId.systemDefault().getId());
        ResourceBundle rb = ResourceBundle.getBundle("/lang/Lang", Locale.getDefault());
        if(Locale.getDefault().getLanguage().equals("fr")){
            String title = rb.getString("Low") + " " +
                            rb.getString("Budget") + " " +
                            rb.getString("Scheduling") + " " +
                            rb.getString("App");
            titleText.setText(title);
            idLabel.setText(rb.getString("username"));
            passwordLabel.setText(rb.getString("password"));
            loginBtn.setText(rb.getString("Login"));
        }
    }

    public LocalDateTime adjustToEst(LocalTime localTime){
        ZoneId estZoneId = ZoneId.of("America/New_York");
        LocalDateTime localDateTime = LocalDateTime.of(LocalDate.now(), localTime); // Entered Time Local
        ZonedDateTime zonedLocalDateTime = localDateTime.atZone(ZoneId.systemDefault()); // Entered Time LocalZone

        ZonedDateTime estZonedDateTime = zonedLocalDateTime.withZoneSameInstant(estZoneId); // Entered Time EST Zone
        LocalDateTime estLocalDateTime = estZonedDateTime.toLocalDateTime(); // Entered Time EST Local
        return estLocalDateTime;
    }

    public void checkForUpcomingApp() throws SQLException {
        ObservableList<Appointment> appointmentList = FXCollections.observableArrayList();
        AppointmentsQuery.retrieveAllAppointments(appointmentList);

        LocalTime loginTime = LocalTime.from(adjustToEst(LocalTime.now()));
        LocalTime loginTimePlusTime = loginTime.plusMinutes(15);

        User user = UsersQuery.retrieveUserByName(UsersQuery.getUserName());
        boolean noUpcomingApp = true;

        for(Appointment currApp : appointmentList){
            if(currApp.getUserId() == user.getId()){
                if(loginTime.isBefore(currApp.getStartTime()) && loginTimePlusTime.isAfter(currApp.getStartTime())){
                    AlertGenerator.generateInfoAlert("Appoinment: \nID: " + currApp.getId() +
                            "\nDate: " + currApp.getDate() +
                            "\nTime: " + currApp.getStartTime() +
                            "\nIs starting within 15 minutes.");
                    noUpcomingApp = false;
                    break;
                }
            }
        }

        if(noUpcomingApp){
            AlertGenerator.generateInfoAlert("There are no upcoming appointments.");
        }
    }

    public void onLogin(ActionEvent event) throws SQLException, IOException {
        if(idTextBox.getText().isBlank() || passwordTextBox.getText().isBlank()){

            if(Locale.getDefault().getLanguage().equals("fr")){
                AlertGenerator.generateErrorAlert("Le pseudo ou mot de passe est incorect.");
            }else{
                AlertGenerator.generateErrorAlert("The username or password is incorrect.");
            }

        } else{
            String userName = idTextBox.getText();
            String passWord = passwordTextBox.getText();

            if(UsersQuery.loginAttempt(userName, passWord)){
                stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                stage.setScene(new Scene(FXMLLoader.load((Main.class.getResource("/views/CustomerForm.fxml"))), 1600, 400));
                stage.setResizable(false);
                stage.show();
                checkForUpcomingApp();
            } else{
                    //Record Invalid Login to login_activity.txt
                if(Locale.getDefault().getLanguage().equals("fr")){
                    AlertGenerator.generateErrorAlert("Le pseudo ou mot de passe est incorect.");
                }else {
                    AlertGenerator.generateErrorAlert("Username or password is incorrect.");
                }

            }

        }

    }

}
