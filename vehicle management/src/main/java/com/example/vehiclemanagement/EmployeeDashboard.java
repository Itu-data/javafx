package com.example.vehiclemanagement;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class EmployeeDashboard {

    @FXML
    private void handleManageBookings(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/com/example/vehiclemanagement/manageProfile.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
             Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showError("Failed to open Manage Profile page.");
        }
    }

    private void showError(String s) {
    }

    @FXML
    private void handleManagePayments(ActionEvent event) {


        try {
            Parent root = FXMLLoader.load(getClass().getResource("/com/example/vehiclemanagement/Manage_payment.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showError("Failed to open Manage Profile page.");
        }
    }

    @FXML
    private void handleBookingHistory(ActionEvent event) {
        loadPage("/com/example/vehiclemanagement/BookingHistory.fxml", event, "Booking History");
    }

    @FXML
    private void handleLogout(ActionEvent event) {
        loadPage("/com/example/vehiclemanagement/Login.fxml", event, "Login");
    }
    private void loadPage(String fxmlPath, ActionEvent event, String title) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);

            stage.setScene(scene);
            stage.setTitle(title);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Navigation Error", "Failed to load page: " + fxmlPath);
        }
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


    public void hoverButton(MouseEvent mouseEvent) {
    }

    public void handleCustomers(ActionEvent actionEvent) {
    }

    public void exitButton(MouseEvent mouseEvent) {
    }

    public void handlePayments(ActionEvent actionEvent) {
    }

    public void handleReports(ActionEvent actionEvent) {
        
    }

    public void handleManageProfile(ActionEvent actionEvent) {
    }

    public void handleVehicles(ActionEvent actionEvent) {
    }
}
