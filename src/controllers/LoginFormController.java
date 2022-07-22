package controllers;

import database.AppointmentsQuery;
import database.UsersQuery;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
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

/**
 * This class handles all operations on the login form.
 *
 * @author Tabish Abbasi
 */
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

    /**
     * Displays the system's default time zone and translates the form into French if
     * the system's default language is set to French.
     */
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

    /**
     * Checks to see if the currently logged-in user has any appointments within 15 minutes
     * of logging in. A lambda expression is used to filter the appointments from a list
     * containing all the appointments.
     * <p>
     *     If the user has an appointment, displays them in an alert, and if the user does not
     *     have any appointments within 15 minutes, displays an alert to let the user know that
     *     they have no upcoming appointments.
     * </p>
     *
     * @throws SQLException
     */
    public void checkForUpcomingApp() throws SQLException {
        ObservableList<Appointment> appointmentList = FXCollections.observableArrayList();
        AppointmentsQuery.retrieveAllAppointments(appointmentList);

        User user = UsersQuery.retrieveUserByName(UsersQuery.getUserName());
        FilteredList<Appointment> filteredList = new FilteredList<>(appointmentList, app -> // Lambda
                app.getUserId() == user.getId());

        LocalTime loginTime = LocalTime.now();
        LocalTime loginTimePlusTime = loginTime.plusMinutes(15);

        boolean noUpcomingApp = true;
        for(Appointment currApp : filteredList){
            if(loginTime.isBefore(currApp.getStartTime()) && loginTimePlusTime.isAfter(currApp.getStartTime())){
                AlertGenerator.generateInfoAlert("Appoinment: \nID: " + currApp.getId() +
                        "\nDate: " + currApp.getDate() +
                        "\nTime: " + currApp.getStartTime() +
                        "\nIs starting within 15 minutes.");
                noUpcomingApp = false;
                break;
            }
        }

        if(noUpcomingApp){
            AlertGenerator.generateInfoAlert("There are no upcoming appointments.");
        }
    }


    /**
     * Makes a login request to the database using the entered username and password.
     * <p>
     *     Displays an error alert if:
     *     <ul>
     *         <li>Any of the fields are blank.</li>
     *         <li>No matches were found for the entered username and password.</li>
     *     </ul>
     * </p>
     *
     * @param event the login button being clicked
     * @throws SQLException
     * @throws IOException
     */
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
                if(Locale.getDefault().getLanguage().equals("fr")){
                    AlertGenerator.generateErrorAlert("Le pseudo ou mot de passe est incorect.");
                }else {
                    AlertGenerator.generateErrorAlert("Username or password is incorrect.");
                }

            }

        }

    }

}
