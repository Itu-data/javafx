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
import java.util.Objects;

public class Reports {

    @FXML
    void handleManageProfile(ActionEvent event) {
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

    @FXML
    void handleVehicles(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/vehiclemanagement/vehicle.fxml")));
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
    void handleCustomers(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/vehiclemanagement/customer.fxml")));
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
    void handleBookings(ActionEvent event) {
        showFeatureNotImplementedYet();
    }

    @FXML
    void handlePayments(ActionEvent event) {

        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/vehiclemanagement/Payments.fxml")));
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
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "This feature is coming soon!", javafx.scene.control.ButtonType.OK);
        alert.showAndWait();
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR, message, javafx.scene.control.ButtonType.OK);
        alert.showAndWait();
    }


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
            totalRentalsLabel.setText("45");
            totalIncomeLabel.setText("M45,000");
            newCustomersLabel.setText("5");
        }

        private void loadIncomeChart() {
            XYChart.Series<String, Number> series = new XYChart.Series<>();
            series.setName("2025");

            series.getData().add(new XYChart.Data<>("Jan", 8000));
            series.getData().add(new XYChart.Data<>("Feb", 9000));
            series.getData().add(new XYChart.Data<>("Mar", 12000));
            series.getData().add(new XYChart.Data<>("Apr", 15000));

            incomeBarChart.getData().add(series);
        }

        private void loadVehicleUsageChart() {
            vehicleUsagePieChart.getData().add(new PieChart.Data("Toyota Corolla", 35));
            vehicleUsagePieChart.getData().add(new PieChart.Data("Mazda 3", 25));
            vehicleUsagePieChart.getData().add(new PieChart.Data("Honda Civic", 40));
        }

        private void loadRentalsTable() {
            // You would load real data here (maybe from database)
        }
    }


