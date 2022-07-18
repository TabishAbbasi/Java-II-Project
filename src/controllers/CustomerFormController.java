package controllers;

import database.CustomerQuery;
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
import model.Customer;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

public class CustomerFormController {
    private Stage stage;
    private ObservableList<Customer> customerList = FXCollections.observableArrayList();
    @FXML
    private TableView<Customer> customerTable;
    @FXML
    private TableColumn<Customer, Integer> custIdCol;
    @FXML
    private TableColumn<Customer, String> custNameCol;
    @FXML
    private TableColumn<Customer, String> custAddCol;
    @FXML
    private TableColumn<Customer, String> custPostCol;
    @FXML
    private TableColumn<Customer, String> custPhoneCol;
    @FXML
    private TableColumn<Customer, Integer> divIdCol;
    @FXML
    private Text currentUserText;

    @FXML
    public void initialize() throws SQLException {
        currentUserText.setText("User: " + UsersQuery.getUserName());
        CustomerQuery.retrieveAllCustomers(customerList);

        customerTable.setItems(customerList);
        custIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        custNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        custAddCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        custPostCol.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        custPhoneCol.setCellValueFactory(new PropertyValueFactory<>("phone"));
        divIdCol.setCellValueFactory(new PropertyValueFactory<>("divisionId"));
    }

    @FXML
    public void toAppointmentForm(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load((Main.class.getResource("/views/AppointmentForm.fxml"))), 1600, 400));
        stage.setResizable(false);
        stage.show();
    }

    @FXML
    public void addCustomer(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load((Main.class.getResource("/views/AddCustomerForm.fxml"))), 600, 400));
        stage.setResizable(false);
        stage.show();
    }

    @FXML
    public void updateCustomer(ActionEvent event) throws IOException, SQLException {
        if(!customerTable.getSelectionModel().isEmpty()){
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/views/UpdateCustomerForm.fxml"));
            loader.load();

            UpdateCustomerFormController uCFController = loader.getController();
            uCFController.receiveCustomer(customerTable.getSelectionModel().getSelectedItem());

            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.setResizable(false);
            stage.show();
        } else{
            AlertGenerator.generateErrorAlert("Please select a customer from the table.");
        }
    }

    @FXML
    public void deleteCustomer() throws SQLException {
        if(!customerTable.getSelectionModel().isEmpty()){
            Customer removeCustomer = customerTable.getSelectionModel().getSelectedItem();
            Optional<ButtonType> choice = AlertGenerator.generateConfirmAlert("Delete " + removeCustomer.getName() + " and all of their appointments?");

            if(choice.isPresent() && choice.get() == ButtonType.OK){
                CustomerQuery.deleteCustomer(removeCustomer.getId());
                CustomerQuery.retrieveAllCustomers(customerList);
                customerTable.setItems(customerList);
                AlertGenerator.generateInfoAlert(removeCustomer.getName() + " has been removed.");
            }

        } else {
            AlertGenerator.generateErrorAlert("Please select a customer from the table.");
        }

    }

    @FXML
    public void exitApplication(ActionEvent event){
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.close();
    }
}
