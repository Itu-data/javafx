package com.example.vehiclemanagement;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class Vehicle implements Initializable {

    @FXML
    private ImageView topCustomerImage;

    private Stage stage;
    private Scene scene;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            // Load the image into the ImageView
            Image image = new Image(Objects.requireNonNull(getClass().getResource("/com/example/vehiclemanagement/car.jpg")).toExternalForm());
            topCustomerImage.setImage(image);
        } catch (Exception e) {
            System.out.println("Failed to load image: " + e.getMessage());
        }

        // TODO: Load frequent customers from the database
    }

    @FXML
    private void handleManageProfile(ActionEvent event) {
        loadPage("/com/example/vehiclemanagement/manageProfile.fxml", "Manage Profile", event);
    }

    @FXML
    private void handleVehicles(ActionEvent event) {
        loadPage("/com/example/vehiclemanagement/vehicle.fxml", "Vehicles", event);
    }

    @FXML
    private void handleCustomers(ActionEvent event) {
        loadPage("/com/example/vehiclemanagement/customer.fxml", "Customers", event);
    }

    @FXML
    private void handleBookings(ActionEvent event) {
        showFeatureNotImplementedYet();
    }

    @FXML
    private void handlePayments(ActionEvent event) {
        showFeatureNotImplementedYet();
    }

    @FXML
    private void handleReports(ActionEvent event) {
        showFeatureNotImplementedYet();
    }

    @FXML
    private void handleLogout(ActionEvent event) {
        loadPage("/com/example/vehiclemanagement/Login.fxml", "Login", event);
    }

    private void loadPage(String fxmlPath, String title, ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(fxmlPath)));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
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
