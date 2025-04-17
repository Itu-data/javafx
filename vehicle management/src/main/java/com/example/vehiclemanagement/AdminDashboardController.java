package com.example.vehiclemanagement;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
// other imports you need


import java.awt.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Objects;

public class AdminDashboardController {

    private Stage stage;
    private Scene scene;

    @FXML
    private Label availableCarsLabel;

    @FXML
    private Label rentedCarsLabel;

    @FXML
    private Label maintenanceCarsLabel;

    @FXML
    void handleManageProfile(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/com/example/vehiclemanagement/manageProfile.fxml"));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showError("Failed to open Manage Profile page.");
        }
    }

    @FXML
    void handleVehicles(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/vehiclemanagement/vehicle.fxml")));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showError("Failed to open customer page.");
        }
    }

    @FXML
    void handleCustomers(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/vehiclemanagement/customer.fxml")));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showError("Failed to open customer page.");
        }
    }


    @FXML
    void handleBookings(ActionEvent event) {
        showFeatureNotImplementedYet();
    }

    @FXML
    void handlePayments(ActionEvent event) {

        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/vehiclemanagement/Payments.fxml")));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showError("Failed to open customer page.");
        }
    }

    @FXML
    void handleReports(ActionEvent event) {

        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/vehiclemanagement/Reports.fxml")));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showError("Failed to open customer page.");
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
    

    @FXML
    public void hoverButton(MouseEvent event) {
        Node node = (Node) event.getSource();
        node.setStyle("-fx-background-color: #3d566e; -fx-text-fill: white; -fx-font-size: 16px; -fx-alignment: CENTER_LEFT; -fx-cursor: hand;");
    }

    @FXML
    public void exitButton(MouseEvent event) {
        Node node = (Node) event.getSource();
        node.setStyle("-fx-background-color: transparent; -fx-text-fill: white; -fx-font-size: 16px; -fx-alignment: CENTER_LEFT; -fx-cursor: hand;");
    }

    private void showFeatureNotImplementedYet() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "This feature is coming soon!", ButtonType.OK);
        alert.showAndWait();
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR, message, ButtonType.OK);
        alert.showAndWait();
    }
    private void loadDashboardData() {
        try {
            Connection connectDB = DatabaseConnection.getConnection();

            // Query Available Cars
            String availableQuery = "SELECT COUNT(*) FROM bookings WHERE booking_status = 'Available'";
            PreparedStatement availableStmt = connectDB.prepareStatement(availableQuery);
            ResultSet availableResult = availableStmt.executeQuery();
            if (availableResult.next()) {
                Label availableCarsLabel = null;
                availableCarsLabel.setText(String.valueOf(availableResult.getInt(1)));
            }

            // Query Rented Cars
            String rentedQuery = "SELECT COUNT(*) FROM bookings WHERE booking_status = 'Rented'";
            PreparedStatement rentedStmt = connectDB.prepareStatement(rentedQuery);
            ResultSet rentedResult = rentedStmt.executeQuery();
            if (rentedResult.next()) {
                rentedCarsLabel.setText(String.valueOf(rentedResult.getInt(1)));
            }

            // Query On Maintenance Cars
            String maintenanceQuery = "SELECT COUNT(*) FROM bookings WHERE booking_status = 'Maintenance'";
            PreparedStatement maintenanceStmt = connectDB.prepareStatement(maintenanceQuery);
            ResultSet maintenanceResult = maintenanceStmt.executeQuery();
            if (maintenanceResult.next()) {
                maintenanceCarsLabel.setText(String.valueOf(maintenanceResult.getInt(1)));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
