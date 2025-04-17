package com.example.vehiclemanagement;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Objects;

public class Payments {

    // Payment data fields
    private String paymentId;
    private String customer;
    private String vehicle;
    private double amount;
    private LocalDate paymentDate;
    private String status;

    // FXML UI elements
    @FXML private DatePicker paymentdate;
    @FXML private ComboBox<String> paymentMethodComboBox;

    // Required no-arg constructor for FXMLLoader
    public Payments() {
    }

    // Optional full constructor (not used by FXMLLoader)
    public Payments(String paymentId, String customer, String vehicle, double amount,
                    LocalDate paymentDate, String status) {
        this.paymentId = paymentId;
        this.customer = customer;
        this.vehicle = vehicle;
        this.amount = amount;
        this.paymentDate = paymentDate;
        this.status = status;
    }

    // Getters and Setters...
    public String getPaymentId() { return paymentId; }
    public void setPaymentId(String paymentId) { this.paymentId = paymentId; }

    public String getCustomer() { return customer; }
    public void setCustomer(String customer) { this.customer = customer; }

    public String getVehicle() { return vehicle; }
    public void setVehicle(String vehicle) { this.vehicle = vehicle; }

    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }

    public LocalDate getPaymentDate() { return paymentDate; }
    public void setPaymentDate(LocalDate paymentDate) { this.paymentDate = paymentDate; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    @FXML
    public void initialize() {
        paymentMethodComboBox.setItems(FXCollections.observableArrayList("Credit Card", "Debit Card", "Cash", "Bank Transfer"));
    }

    // Navigation and utility methods
    @FXML
    void handleManageProfile(ActionEvent event) { switchScene(event, "/com/example/vehiclemanagement/manageProfile.fxml"); }

    @FXML
    void handleVehicles(ActionEvent event) { switchScene(event, "/com/example/vehiclemanagement/vehicle.fxml"); }

    @FXML
    void handleCustomers(ActionEvent event) { switchScene(event, "/com/example/vehiclemanagement/customer.fxml"); }

    @FXML
    void handleBookings(ActionEvent event) { showFeatureNotImplementedYet(); }

    @FXML
    void handlePayments(ActionEvent event) { switchScene(event, "/com/example/vehiclemanagement/Payments.fxml"); }

    @FXML
    void handleReports(ActionEvent event) { switchScene(event, "/com/example/vehiclemanagement/Reports.fxml"); }

    @FXML
    private void handleLogout(ActionEvent event) {
        switchScene(event, "/com/example/vehiclemanagement/Login.fxml");
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
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "This feature is coming soon!", javafx.scene.control.ButtonType.OK);
        alert.showAndWait();
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR, message, javafx.scene.control.ButtonType.OK);
        alert.showAndWait();
    }

    private void switchScene(ActionEvent event, String fxmlPath) {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(fxmlPath)));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showError("Failed to open the requested page.");
        }
    }

    @FXML
    public void handleSavePayment(ActionEvent actionEvent) {
        // TODO: Add logic to save the payment
    }

    @FXML
    public void handleGenerateReceipt(ActionEvent actionEvent) {
        // TODO: Add logic to generate receipt
    }
}
