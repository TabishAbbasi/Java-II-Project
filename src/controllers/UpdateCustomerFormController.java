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
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import main.Main;
import model.AlertGenerator;
import model.Country;
import model.Customer;
import model.Division;

import java.io.IOException;
import java.sql.SQLException;

/**
 * This class handles all operations in the update customer form.
 *
 * @author Tabish Abbasi
 */
public class UpdateCustomerFormController {
    private ObservableList<Country> countryList = FXCollections.observableArrayList();
    private ObservableList<Division> divisionList = FXCollections.observableArrayList();
    @FXML
    private TextField idTextBox;
    @FXML
    private TextField nameTextBox;
    @FXML
    private TextField addTextBox;
    @FXML
    private TextField postTextBox;
    @FXML
    private TextField phoneTextBox;
    @FXML
    private ComboBox<Country> countryCombo;
    @FXML
    private ComboBox<Division> divisionCombo;

    /**
     * Adds all the received customer's information into the form.
     *
     * @param customer the received customer
     * @throws SQLException
     */
    public void receiveCustomer(Customer customer) throws SQLException {
        idTextBox.setText(String.valueOf(customer.getId()));
        nameTextBox.setText(customer.getName());
        addTextBox.setText(customer.getAddress());
        postTextBox.setText(customer.getPostalCode());
        phoneTextBox.setText(customer.getPhone());

        CountriesQuery.retrieveAllCountries(countryList);
        countryCombo.setItems(countryList);
        int countryId = DivisionsQuery.retrieveDivisionCountryId(customer.getDivisionId());
        Country country = CountriesQuery.retrieveCountryObject(countryId);
        countryCombo.setValue(country);

        DivisionsQuery.retrieveDivisionsByCountryId(countryCombo.getValue().getId(), divisionList);
        divisionCombo.setItems(divisionList);
        Division division = DivisionsQuery.retrieveDivisionById(customer.getDivisionId());
        divisionCombo.setValue(division);
    }

    /**
     * Updates the combo box to the related country's divisions.
     *
     * @param event a value being set for the country combo box
     * @throws SQLException
     */
    public void setDivisionComboBox(ActionEvent event) throws SQLException {
        DivisionsQuery.retrieveDivisionsByCountryId(countryCombo.getValue().getId(), divisionList);
        divisionCombo.setItems(divisionList);
        divisionCombo.getSelectionModel().selectFirst();
    }

    /**
     * Attempts to update the customer's information in the database. Produces and error alert
     * if any fields are empty.
     *
     * @param event the save button being pressed
     * @throws IOException
     * @throws SQLException
     */
    public void onSave(ActionEvent event) throws IOException, SQLException {
        if(nameTextBox.getText().isBlank() || addTextBox.getText().isBlank() ||
                postTextBox.getText().isBlank() || phoneTextBox.getText().isBlank()){
            AlertGenerator.generateErrorAlert("Fields must not be Empty.");
        } else{
            int customerId = Integer.parseInt(idTextBox.getText());
            String name = nameTextBox.getText();
            String address = addTextBox.getText();
            String postalCode = postTextBox.getText();
            String phone = phoneTextBox.getText();
            Division division = divisionCombo.getValue();

            CustomerQuery.updateCustomer(customerId, name, address, postalCode, phone, division.getId());
            toCustomerForm(event);
        }
    }

    /**
     * Changes the current stage's scene to the customer form.
     *
     * @param event the cancel button being clicked
     * @throws IOException
     */
    public void toCustomerForm(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load((Main.class.getResource("/views/CustomerForm.fxml"))), 1600, 400));
        stage.setResizable(false);
        stage.show();
    }
}
