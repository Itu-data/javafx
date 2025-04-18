package com.example.vehiclemanagement;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class AdminDashboardController implements Initializable {

    @FXML
    private Label availableCarsLabel;

    @FXML
    private Label rentedCarsLabel;

    @FXML
    private Label maintenanceCarsLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadCarStatusCounts();
    }

    private void loadCarStatusCounts() {
        Connection connectDB = null;
        try {
            connectDB = DatabaseConnection.getConnection();

            String availableQuery = "SELECT COUNT(*) FROM bookings WHERE status = 'completed'";
            PreparedStatement availableStmt = connectDB.prepareStatement(availableQuery);
            ResultSet availableResult = availableStmt.executeQuery();
            if (availableResult.next()) {
                availableCarsLabel.setText(String.valueOf(availableResult.getInt(1)));
            }

            String rentedQuery = "SELECT COUNT(*) FROM bookings WHERE status = 'pending'";
            PreparedStatement rentedStmt = connectDB.prepareStatement(rentedQuery);
            ResultSet rentedResult = rentedStmt.executeQuery();
            if (rentedResult.next()) {
                rentedCarsLabel.setText(String.valueOf(rentedResult.getInt(1)));
            }

            String maintenanceQuery = "SELECT COUNT(*) FROM bookings WHERE status = 'cancelled'";
            PreparedStatement maintenanceStmt = connectDB.prepareStatement(maintenanceQuery);
            ResultSet maintenanceResult = maintenanceStmt.executeQuery();
            if (maintenanceResult.next()) {
                maintenanceCarsLabel.setText(String.valueOf(maintenanceResult.getInt(1)));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connectDB != null) {
                try {
                    connectDB.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void showFeatureNotImplementedYet() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "This feature is coming soon!", ButtonType.OK);
        alert.showAndWait();
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR, message, ButtonType.OK);
        alert.showAndWait();
    }

    private void loadPage(ActionEvent event, String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Scene scene = new Scene(loader.load());
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showError("Failed to load page.");
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
            showError("Failed to logout.");
        }
    }

    @FXML
    private void handleManageProfile(ActionEvent event) {
        loadPage(event, "/com/example/vehiclemanagement/manageProfile.fxml");
    }

    @FXML
    private void handleVehicles(ActionEvent event) {
        loadPage(event, "/com/example/vehiclemanagement/vehicle.fxml");
    }

    @FXML
    private void handleCustomers(ActionEvent event) {
        loadPage(event, "/com/example/vehiclemanagement/customer.fxml");
    }

    @FXML
    private void handleBookings(ActionEvent event) {
        showFeatureNotImplementedYet();
    }

    @FXML
    private void handlePayments(ActionEvent event) {
        loadPage(event, "/com/example/vehiclemanagement/Payments.fxml");
    }

    @FXML
    private void handleReports(ActionEvent event) {
        loadPage(event, "/com/example/vehiclemanagement/Reports.fxml");
    }

    @FXML
    private void hoverButton(javafx.scene.input.MouseEvent event) {
        // Add hover effect logic here if needed
    }

    @FXML
    private void exitButton(javafx.scene.input.MouseEvent event) {
        // Remove hover effect logic here if needed
    }
}
