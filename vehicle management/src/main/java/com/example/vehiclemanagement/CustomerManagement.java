package com.example.vehiclemanagement;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class CustomerManagement {

    // TableView for displaying customers
    @FXML
    private TableView<Customer> customerTable;
    @FXML
    private TableColumn<Customer, String> idCol;
    @FXML
    private TableColumn<Customer, String> nameCol;
    @FXML
    private TableColumn<Customer, String> contactInfoCol;
    @FXML
    private TableColumn<Customer, String> drivingLicenseCol;

    // Form fields
    @FXML
    private TextField fullNameField;
    @FXML
    private TextField contactInfoField;
    @FXML
    private TextField drivingLicenseNumberField;

    // Observable list of customers
    private ObservableList<Customer> customerList = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        // Set up columns for the TableView
        idCol.setCellValueFactory(cellData -> cellData.getValue().getIdProperty());
        nameCol.setCellValueFactory(cellData -> cellData.getValue().getFullNameProperty());
        contactInfoCol.setCellValueFactory(cellData -> cellData.getValue().getContactInfoProperty());
        drivingLicenseCol.setCellValueFactory(cellData -> cellData.getValue().getDrivingLicenseNumberProperty());

        // Load existing customer data
        loadCustomerData();
    }

    // Handle "Add Customer" button click
    @FXML
    private void handleAddCustomer(ActionEvent event) {
        String fullName = fullNameField.getText();
        String contactInfo = contactInfoField.getText();
        String drivingLicenseNumber = drivingLicenseNumberField.getText();

        if (fullName.isEmpty() || contactInfo.isEmpty() || drivingLicenseNumber.isEmpty()) {
            System.out.println("Please fill in all fields.");
            return;
        }

        addCustomerToDatabase(fullName, contactInfo, drivingLicenseNumber);
        clearForm();
    }

    // Add customer to the database
    private void addCustomerToDatabase(String fullName, String contactInfo, String drivingLicenseNumber) {
        String query = "INSERT INTO customers (Name, ContactInfo, DrivingLicenseNumber) VALUES (?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, fullName);
            statement.setString(2, contactInfo);
            statement.setString(3, drivingLicenseNumber);

            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Customer added successfully!");
                loadCustomerData();
            } else {
                System.out.println("Failed to add customer.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Load customer data
    private void loadCustomerData() {
        customerList.clear();

        String query = "SELECT * FROM customers";

        try (Connection connection = DatabaseConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                int id = resultSet.getInt("CustomerID");
                String name = resultSet.getString("Name");
                String contactInfo = resultSet.getString("ContactInfo");
                String drivingLicenseNumber = resultSet.getString("DrivingLicenseNumber");

                customerList.add(new Customer(id, name, contactInfo, drivingLicenseNumber));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        customerTable.setItems(customerList);
    }

    // Clear form fields
    private void clearForm() {
        fullNameField.clear();
        contactInfoField.clear();
        drivingLicenseNumberField.clear();
    }
    public class Customers implements Initializable {
        @FXML
        private TableView<Customer> customerTable;

        @FXML
        private TableColumn<Customer, String> nameCol;

        @FXML
        private TableColumn<Customer, String> contactInfoCol;

        @Override
        public void initialize(URL url, ResourceBundle resourceBundle) {
            nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
            contactInfoCol.setCellValueFactory(new PropertyValueFactory<>("contactInfo"));
        }
    }

    @FXML
    private void handleLogout(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/vehiclemanagement/Login.fxml"));
            Scene scene = new Scene(loader.load());
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Login");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void handleManageProfile(ActionEvent actionEvent) {}
    public void hoverButton(MouseEvent mouseEvent) {}
    public void exitButton(MouseEvent mouseEvent) {}
    public void handleVehicles(ActionEvent actionEvent) {}
    public void handleCustomers(ActionEvent actionEvent) {}
    public void handlePayments(ActionEvent actionEvent) {}
    public void handleReports(ActionEvent actionEvent) {}

    public void handleDeleteCustomer(ActionEvent actionEvent) {
    }

    // --- Customer Model Class ---
    public static class Customer {
        private final StringProperty id;
        private final StringProperty fullName;
        private final StringProperty contactInfo;
        private final StringProperty drivingLicenseNumber;

        public Customer(int id, String fullName, String contactInfo, String drivingLicenseNumber) {
            this.id = new SimpleStringProperty(String.valueOf(id));
            this.fullName = new SimpleStringProperty(fullName);
            this.contactInfo = new SimpleStringProperty(contactInfo);
            this.drivingLicenseNumber = new SimpleStringProperty(drivingLicenseNumber);
        }

        public StringProperty getIdProperty() { return id; }
        public StringProperty getFullNameProperty() { return fullName; }
        public StringProperty getContactInfoProperty() { return contactInfo; }
        public StringProperty getDrivingLicenseNumberProperty() { return drivingLicenseNumber; }
    }
}
