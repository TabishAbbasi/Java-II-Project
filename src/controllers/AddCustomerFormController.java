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
import model.Division;

import java.io.IOException;
import java.sql.SQLException;

public class AddCustomerFormController {
    private ObservableList<Country> countryList = FXCollections.observableArrayList();
    private ObservableList<Division> divisionList = FXCollections.observableArrayList();
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

    @FXML
    public void initialize() throws SQLException {
        CountriesQuery.retrieveAllCountries(countryList);
        countryCombo.setItems(countryList);
        countryCombo.getSelectionModel().selectFirst();
        DivisionsQuery.retrieveDivisionsByCountryId(countryCombo.getValue().getId(), divisionList);
        divisionCombo.setItems(divisionList);
        divisionCombo.getSelectionModel().selectFirst();
    }

    public void setDivisionComboBox(ActionEvent event) throws SQLException {
        DivisionsQuery.retrieveDivisionsByCountryId(countryCombo.getValue().getId(), divisionList);
        divisionCombo.setItems(divisionList);
        divisionCombo.getSelectionModel().selectFirst();
    }

    public void onSave(ActionEvent event) throws IOException, SQLException {
        if(nameTextBox.getText().isBlank() || addTextBox.getText().isBlank() ||
            postTextBox.getText().isBlank() || phoneTextBox.getText().isBlank()){
            AlertGenerator.generateErrorAlert("Fields must not be Empty.");
        } else{
            String name = nameTextBox.getText();
            String address = addTextBox.getText();
            String postalCode = postTextBox.getText();
            String phone = phoneTextBox.getText();
            Division division = divisionCombo.getValue();

            CustomerQuery.insertCustomer(name, address, postalCode, phone, division.getId());
            toCustomerForm(event);
        }

    }

    public void toCustomerForm(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load((Main.class.getResource("/views/CustomerForm.fxml"))), 1600, 400));
        stage.setResizable(false);
        stage.show();
    }
}
