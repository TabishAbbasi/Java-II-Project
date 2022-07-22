package controllers;

import database.CountriesQuery;
import database.CustomerQuery;
import database.DivisionsQuery;
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
import model.Country;
import model.Customer;
import model.Division;

import java.io.IOException;
import java.sql.SQLException;

/**
 * This class handles all operations in the customer report form.
 *
 * @author Tabish Abbasi
 */
public class CustomerReportFormController {
    private ObservableList<Customer> customerList = FXCollections.observableArrayList();
    private ObservableList<Country> countryList = FXCollections.observableArrayList();
    private ObservableList<Division> divisionList = FXCollections.observableArrayList();

    @FXML
    private ComboBox<Country> countryComBox;
    @FXML
    private ComboBox<Division> divComBox;
    @FXML
    private Label numOfCustomers;

    /**
     * Adds all countries and the currently selected country into their respective combo boxes
     * and updates the number of customers to the current selection.
     *
     * @throws SQLException
     */
    @FXML
    public void initialize() throws SQLException {
        CustomerQuery.retrieveAllCustomers(customerList);

        CountriesQuery.retrieveAllCountries(countryList);
        countryComBox.setItems(countryList);
        countryComBox.getSelectionModel().selectFirst();

        DivisionsQuery.retrieveDivisionsByCountryId(countryComBox.getValue().getId(), divisionList);
        divComBox.setItems(divisionList);
        divComBox.getSelectionModel().selectFirst();

        int customersInDiv = 0;
        for(Customer currCustomer: customerList){
            if(currCustomer.getDivisionId() == divComBox.getValue().getId()){
                customersInDiv++;
            }
        }

        numOfCustomers.setText(String.valueOf(customersInDiv));
    }

    /**
     * Updates the division combo box and updates the number of customers to the current selection.
     *
     * @throws SQLException
     */
    public void countryChanged() throws SQLException {
        CustomerQuery.retrieveAllCustomers(customerList);

        DivisionsQuery.retrieveDivisionsByCountryId(countryComBox.getValue().getId(), divisionList);
        divComBox.setItems(divisionList);
        divComBox.getSelectionModel().selectFirst();

        int customersInDiv = 0;
        for(Customer currCustomer: customerList){
            if(currCustomer.getDivisionId() == divComBox.getValue().getId()){
                customersInDiv++;
            }
        }

        numOfCustomers.setText(String.valueOf(customersInDiv));
    }

    /**
     * Updates the number of customers to the current selection.
     *
     * @throws SQLException
     */
    public void divisionChanged() throws SQLException {
        CustomerQuery.retrieveAllCustomers(customerList);

        int customersInDiv = 0;
        for(Customer currCustomer: customerList){
            if(currCustomer.getDivisionId() == divComBox.getValue().getId()){
                customersInDiv++;
            }
        }

        numOfCustomers.setText(String.valueOf(customersInDiv));
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
