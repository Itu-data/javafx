package com.example.vehiclemanagement;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Objects;

public class ManageProfile {

    @FXML
    private ImageView profileImageView;

    @FXML
    private TextField nameField;

    @FXML
    private TextField emailField;

    @FXML
    private TextField phoneField;

    @FXML
    private PasswordField passwordField;

    private Stage stage;
    private Scene scene;

    private File selectedProfilePicFile; // Store the selected profile picture file

    // ----- Profile Handling -----

    @FXML
    private void handleUploadPhoto(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Profile Photo");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg")
        );
        selectedProfilePicFile = fileChooser.showOpenDialog(((Node) event.getSource()).getScene().getWindow());
        if (selectedProfilePicFile != null) {
            try {
                Image image = new Image(new FileInputStream(selectedProfilePicFile));
                profileImageView.setImage(image);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                showError("Failed to load selected image.");
            }
        }
    }


        @FXML
        private void handleSaveChanges(ActionEvent event) {
            String name = nameField.getText();
            String email = emailField.getText();
            String phone = phoneField.getText();
            String password = passwordField.getText();

            int userId = 1;  // Replace with the actual user ID from session or authentication system


            try (Connection connection = DatabaseConnection.getConnection()) {
                String query = "UPDATE profiles SET full_name = ?, email = ?, phone_number = ?, change_password = ? WHERE user_id = ?";
                try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                    preparedStatement.setString(1, name);
                    preparedStatement.setString(2, email);
                    preparedStatement.setString(3, phone);
                    preparedStatement.setString(4, password); // Saving into "change_password"
                    preparedStatement.setInt(5, userId); // Set user_id

                    int rowsAffected = preparedStatement.executeUpdate();

                    if (rowsAffected > 0) {
                        showInfo("Profile updated successfully!");
                    } else {
                        showError("Failed to update profile.");
                    }
                }
            } catch (SQLException e) {
                // Handle any SQL exceptions
                e.printStackTrace();
                showError("Database error: " + e.getMessage());
            }
        }
    @FXML
    private void handleBack(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/vehiclemanagement/adminDashboard.fxml"));
            Parent root = loader.load();
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showError("Failed to go back to Dashboard.");
        }
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR, message, javafx.scene.control.ButtonType.OK);
        alert.showAndWait();
    }

    private void showInfo(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, message, javafx.scene.control.ButtonType.OK);
        alert.showAndWait();
    }

    // ----- Navigation Handling -----




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
        showFeatureNotImplementedYet();
    }

    @FXML
    void handleReports(ActionEvent event) {
        showFeatureNotImplementedYet();
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


}
