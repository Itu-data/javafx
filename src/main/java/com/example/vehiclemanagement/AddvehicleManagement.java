package com.example.vehiclemanagement;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AddvehicleManagement {

    @FXML private TextField brandField;
    @FXML private TextField modelField;
    @FXML private TextField rentalPriceField;
    @FXML private ComboBox<String> availabilityField;
    @FXML
    private ComboBox<String> categoryField;

    @FXML
    public void initialize() {
        // Populate ComboBox with availability options
        availabilityField.getItems().addAll("Rented", "maintenance","Available");
        availabilityField.setValue("Available");
        categoryField.getItems().addAll("Car", "Bike", "Van", "Truck", "SUV");
    }

    @FXML
    private void handleAddVehicleForm() {
        String brand = brandField.getText();
        String model = modelField.getText();
        String category = categoryField.getValue();
        String rentalPriceStr = rentalPriceField.getText();
        String availability = availabilityField.getValue();

        // Validate input fields
        if (brand.isEmpty() || model.isEmpty() || category.isEmpty() || rentalPriceStr.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Validation Error", "All fields must be filled out.");
            return;
        }

        double rentalPrice = 0;
        try {
            rentalPrice = Double.parseDouble(rentalPriceStr);
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Validation Error", "Rental Price must be a valid number.");
            return;
        }

        // Add the vehicle to the database
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "INSERT INTO vehicles (Brand, Model, Category, RentalPricePerDay, AvailabilityStatus) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, brand);
            stmt.setString(2, model);
            stmt.setString(3, category);
            stmt.setDouble(4, rentalPrice);
            stmt.setString(5, availability);
            stmt.executeUpdate();

            // Show success alert
            showAlert(Alert.AlertType.INFORMATION, "Success", "Vehicle added successfully!");

            // Close the form window after successful insertion
            closeWindow();
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Database Error", "Failed to add vehicle to the database.");
        }
    }

    @FXML
    private void handleCancel() {
        // Close the form window without saving anything
        closeWindow();
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type, message, ButtonType.OK);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.showAndWait();
    }

    private void closeWindow() {
        // Close the Add Vehicle form window
        Stage stage = (Stage) brandField.getScene().getWindow();
        stage.close();
    }
}
