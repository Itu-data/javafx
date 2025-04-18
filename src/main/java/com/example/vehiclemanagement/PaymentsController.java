package com.example.vehiclemanagement;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.time.LocalDate;

public class PaymentsController {


    @FXML
    void handleManageProfile(ActionEvent event) {
        loadScene("/com/example/vehiclemanagement/manageProfile.fxml", event);
    }

    @FXML
    void handleVehicles(ActionEvent event) {
        loadScene("/com/example/vehiclemanagement/vehicle.fxml", event);
    }

    @FXML
    void handleCustomers(ActionEvent event) {
        loadScene("/com/example/vehiclemanagement/customer.fxml", event);
    }

    @FXML
    void handleBookings(ActionEvent event) {
        showFeatureNotImplementedYet();
    }

    @FXML
    void handlePayments(ActionEvent event) {
        loadScene("/com/example/vehiclemanagement/Payments.fxml", event);
    }

    @FXML
    void handleReports(ActionEvent event) {
        loadScene("/com/example/vehiclemanagement/Reports.fxml", event);
    }

    @FXML
    private void handleLogout(ActionEvent event) {
        loadScene("/com/example/vehiclemanagement/Login.fxml", event);
    }

    private void loadScene(String fxmlPath, ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(fxmlPath));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error loading scene: " + fxmlPath);
        }
    }

    private void showFeatureNotImplementedYet() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Coming Soon");
        alert.setHeaderText(null);
        alert.setContentText("This feature is not implemented yet.");
        alert.showAndWait();
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

    // ========== PAYMENT HANDLING ==========

    @FXML
    private TextField customerName;

    @FXML
    private TextField vehicleName;

    @FXML
    private TextField amountPaid;

    @FXML
    private ComboBox<String> paymentMethod;

    @FXML
    private DatePicker paymentdate;

    @FXML
    private TableView<ObservableList<String>> paymentHistoryTable;

    @FXML
    private TableColumn<ObservableList<String>, String> customerColumn;
    @FXML
    private TableColumn<ObservableList<String>, String> vehicleColumn;
    @FXML
    private TableColumn<ObservableList<String>, String> amountColumn;
    @FXML
    private TableColumn<ObservableList<String>, String> paymentDateColumn;
    @FXML
    private TableColumn<ObservableList<String>, String> paymentMethodColumn;

    private final ObservableList<ObservableList<String>> paymentData = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        customerColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get(0)));
        vehicleColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get(1)));
        amountColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get(2)));
        paymentDateColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get(3)));
        paymentMethodColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get(4)));

        paymentHistoryTable.setItems(paymentData);

        // Populate combo box
        paymentMethod.setItems(FXCollections.observableArrayList("Credit Card", "Cash", "Mobile Payment"));
    }

    @FXML
    private void handleSavePayment() {
        String customer = customerName.getText();
        String vehicle = vehicleName.getText();
        String amount = amountPaid.getText();
        String method = paymentMethod.getValue();
        LocalDate date = paymentdate.getValue();

        if (customer.isEmpty() || vehicle.isEmpty() || amount.isEmpty() || method == null || date == null) {
            showAlert("Please fill in all fields.");
            return;
        }

        // Save to MySQL
        String sql = "INSERT INTO payments (customer_name, vehicle_name, amount_paid, payment_date, payment_method) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             java.sql.PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, customer);
            pstmt.setString(2, vehicle);
            pstmt.setDouble(3, Double.parseDouble(amount));
            pstmt.setDate(4, java.sql.Date.valueOf(date));
            pstmt.setString(5, method);
            pstmt.executeUpdate();

            // Add to the table in the app
            ObservableList<String> row = FXCollections.observableArrayList(customer, vehicle, amount, date.toString(), method);
            paymentData.add(row);

            clearForm();
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Failed to save payment: " + e.getMessage());
        }
    }


    private void clearForm() {
        customerName.clear();
        vehicleName.clear();
        amountPaid.clear();
        paymentMethod.setValue(null);
        paymentdate.setValue(null);
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
