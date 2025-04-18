package com.example.vehiclemanagement;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public class Reports {

    @FXML
    private Label totalRentalsLabel, totalIncomeLabel, newCustomersLabel;

    @FXML
    private BarChart<String, Number> incomeBarChart;

    @FXML
    private PieChart vehicleUsagePieChart;

    @FXML
    private TableView<?> rentalsTable;

    @FXML
    private TableColumn<?, ?> rentalIdCol, customerNameCol, vehicleNameCol, startDateCol, endDateCol, amountPaidCol;

    @FXML
    public void initialize() {
        loadSummaryData();
        loadIncomeChart();
        loadVehicleUsageChart();
        loadRentalsTable();
    }
    private void loadSummaryData() {
        Connection connectDB = null;

        try {
            connectDB = DatabaseConnection.getConnection();

            // 1. Get total income
            String incomeQuery = "SELECT SUM(amount_paid) FROM payments";
            PreparedStatement incomeStmt = connectDB.prepareStatement(incomeQuery);
            ResultSet incomeRs = incomeStmt.executeQuery();
            if (incomeRs.next()) {
                double totalIncome = incomeRs.getDouble(1);
                totalIncomeLabel.setText("M" + String.format("%.2f", totalIncome));
            }

            // 2. Count distinct customer names
            String customerCountQuery = "SELECT COUNT(DISTINCT customer_name) FROM payments";
            PreparedStatement customerStmt = connectDB.prepareStatement(customerCountQuery);
            ResultSet customerRs = customerStmt.executeQuery();
            if (customerRs.next()) {
                int totalCustomers = customerRs.getInt(1);
                totalRentalsLabel.setText(String.valueOf(totalCustomers)); // Assuming 1 rental per customer
                newCustomersLabel.setText(String.valueOf(totalCustomers));
            }

        } catch (SQLException e) {
            e.printStackTrace();
            showError("Failed to load summary data.");
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


    private void loadIncomeChart() {
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Daily Income");

        Connection connectDB = null;

        try {
            connectDB = DatabaseConnection.getConnection();

            // Query to get daily income
            String query = "SELECT payment_date, SUM(amount_paid) AS total FROM payments GROUP BY payment_date ORDER BY payment_date ASC";
            PreparedStatement stmt = connectDB.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String date = rs.getString("payment_date"); // Format: YYYY-MM-DD
                double total = rs.getDouble("total");

                // Use the date string directly on the x-axis
                series.getData().add(new XYChart.Data<>(date, total));
            }

            incomeBarChart.getData().clear(); // Clear previous data
            incomeBarChart.getData().add(series);

        } catch (SQLException e) {
            e.printStackTrace();
            showError("Failed to load income chart.");
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


    private void loadVehicleUsageChart() {
        vehicleUsagePieChart.getData().clear(); // Clear any existing data

        Connection connectDB = null;

        try {
            connectDB = DatabaseConnection.getConnection();

            // Query to count how many times each vehicle_name appears in the payments table
            String query = "SELECT vehicle_name, COUNT(*) AS usage_count FROM payments GROUP BY vehicle_name";
            PreparedStatement stmt = connectDB.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String vehicleName = rs.getString("vehicle_name");
                int count = rs.getInt("usage_count");

                vehicleUsagePieChart.getData().add(new PieChart.Data(vehicleName, count));
            }

        } catch (SQLException e) {
            e.printStackTrace();
            showError("Failed to load vehicle usage chart.");
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

    private void loadRentalsTable() {
        // Implement database loading here if needed
    }

    // Navigation and UI handling
    @FXML
    void handleManageProfile(ActionEvent event) {
        loadPage(event, "/com/example/vehiclemanagement/manageProfile.fxml", "Manage Profile");
    }

    @FXML
    void handleVehicles(ActionEvent event) {
        loadPage(event, "/com/example/vehiclemanagement/vehicle.fxml", "Vehicles");
    }

    @FXML
    void handleCustomers(ActionEvent event) {
        loadPage(event, "/com/example/vehiclemanagement/customer.fxml", "Customers");
    }

    @FXML
    void handleBookings(ActionEvent event) {
        showFeatureNotImplementedYet();
    }

    @FXML
    void handlePayments(ActionEvent event) {
        loadPage(event, "/com/example/vehiclemanagement/Payments.fxml", "Payments");
    }

    @FXML
    void handleReports(ActionEvent event) {
        loadPage(event, "/com/example/vehiclemanagement/Reports.fxml", "Reports");
    }

    @FXML
    void handleLogout(ActionEvent event) {
        loadPage(event, "/com/example/vehiclemanagement/login.fxml", "Login");
    }

    private void loadPage(ActionEvent event, String fxmlPath, String title) {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(fxmlPath)));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle(title);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showError("Failed to open " + title + " page.");
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
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "This feature is coming soon!", javafx.scene.control.ButtonType.OK);
        alert.showAndWait();
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR, message, javafx.scene.control.ButtonType.OK);
        alert.showAndWait();
    }
}
