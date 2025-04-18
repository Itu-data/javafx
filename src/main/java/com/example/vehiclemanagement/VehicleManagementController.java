package com.example.vehiclemanagement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;

public class VehicleManagementController {

    @FXML private TableView<Vehicle> vehicleTable;
    @FXML private TableColumn<Vehicle, Integer> vehicleIdCol;
    @FXML private TableColumn<Vehicle, String> brandCol;
    @FXML private TableColumn<Vehicle, String> modelCol;
    @FXML private TableColumn<Vehicle, String> categoryCol;
    @FXML private TableColumn<Vehicle, Double> rentalPriceCol;
    @FXML private TableColumn<Vehicle, String> availabilityCol;

    private ObservableList<Vehicle> vehicleList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // Set up the TableView columns
        vehicleIdCol.setCellValueFactory(cell -> cell.getValue().vehicleIdProperty().asObject());
        brandCol.setCellValueFactory(cell -> cell.getValue().brandProperty());
        modelCol.setCellValueFactory(cell -> cell.getValue().modelProperty());
        categoryCol.setCellValueFactory(cell -> cell.getValue().categoryProperty());
        rentalPriceCol.setCellValueFactory(cell -> cell.getValue().rentalPricePerDayProperty().asObject());
        availabilityCol.setCellValueFactory(cell -> cell.getValue().availabilityStatusProperty());

        // Load vehicle data from the database
        loadVehicleData();
    }

    private void loadVehicleData() {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "SELECT VehicleID, Brand, Model, Category, RentalPricePerDay, AvailabilityStatus FROM vehicles";
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                vehicleList.add(new Vehicle(
                        rs.getInt("VehicleID"),
                        rs.getString("Brand"),
                        rs.getString("Model"),
                        rs.getString("Category"),
                        rs.getDouble("RentalPricePerDay"),
                        rs.getString("AvailabilityStatus")
                ));
            }

            vehicleTable.setItems(vehicleList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Handle Add Vehicle action
    @FXML
    private void handleAddVehicle() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/vehiclemanagement/AddVehicle.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Add New Vehicle");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL); // Makes the new window modal
            stage.showAndWait(); // Wait until the Add Vehicle window is closed before returning

            // After the form closes, refresh the vehicle list
            vehicleList.clear();
            loadVehicleData();

        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Unable to open Add Vehicle form.");
        }
    }


    // Handle Update Vehicle action
    @FXML
    private void handleUpdateVehicle() {
        Vehicle selectedVehicle = vehicleTable.getSelectionModel().getSelectedItem();
        if (selectedVehicle != null) {
            System.out.println("Handle Update Vehicle action");
            // Logic to open an Update Vehicle form or update in the table directly
        } else {
            showAlert(Alert.AlertType.WARNING, "No Vehicle Selected", "Please select a vehicle to update.");
        }
    }

    // Handle Delete Vehicle action
    @FXML
    private void handleDeleteVehicle() {
        Vehicle selectedVehicle = vehicleTable.getSelectionModel().getSelectedItem();
        if (selectedVehicle != null) {
            try (Connection conn = DatabaseConnection.getConnection()) {
                String query = "DELETE FROM vehicles WHERE VehicleID = ?";
                PreparedStatement stmt = conn.prepareStatement(query);
                stmt.setInt(1, selectedVehicle.getVehicleId());
                stmt.executeUpdate();

                // Remove the deleted vehicle from the list and refresh the table
                vehicleList.remove(selectedVehicle);
                System.out.println("Vehicle deleted successfully.");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            showAlert(Alert.AlertType.WARNING, "No Vehicle Selected", "Please select a vehicle to delete.");
        }
    }

    // Handle Search Vehicle action
    @FXML
    private void handleSearchVehicle(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            System.out.println("Handle Search Vehicle action");
            // Logic for searching vehicles (e.g., based on input field or criteria)
        }
    }

    // Show alert with message
    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type, message, ButtonType.OK);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.showAndWait();
    }

    // Action for Logout
    @FXML
    private void handleLogout() {
        System.out.println("Logout action performed.");
        // Implement the logic for logging out
    }

    // Action for Manage Profile
    @FXML
    private void handleManageProfile() {
        System.out.println("Manage Profile action performed.");
        // Implement the logic for managing profile
    }

    // Other button actions
    @FXML
    private void handleVehicles() {
        System.out.println("Vehicles action performed.");
    }

    @FXML
    private void handleCustomers() {
        System.out.println("Customers action performed.");
    }

    @FXML
    private void handlePayments() {
        System.out.println("Payments action performed.");
    }

    @FXML
    private void handleReports() {
        System.out.println("Reports action performed.");
    }

    public void hoverButton(MouseEvent mouseEvent) {
    }

    public void exitButton(MouseEvent mouseEvent) {
    }
}
