package com.example.vehiclemanagement;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Signup {

    @FXML
    private TextField usernameField;
    @FXML
    private TextField emailField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private PasswordField confirmPasswordField;
    @FXML
    private ComboBox<String> roleComboBox;

    // Called when the user clicks the Register button
    @FXML
    private void handleRegisterAction() {
        String username = usernameField.getText();
        String email = emailField.getText();
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();
        String role = roleComboBox.getValue();

        if (username.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() || role == null) {
            showAlert("All fields must be filled!", AlertType.ERROR);
            return;
        }

        if (!password.equals(confirmPassword)) {
            showAlert("Passwords do not match!", AlertType.ERROR);
            return;
        }

        // Insert the user into the database with the plain text password
        try {
            insertUserIntoDatabase(username, email, password, role);
            showAlert("Registration successful!", AlertType.INFORMATION);
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Registration failed! Please try again later.", AlertType.ERROR);
        }
    }

    // Method to insert user into the database
    private void insertUserIntoDatabase(String username, String email, String password, String role) throws SQLException {
        String insertSQL = "INSERT INTO users (username, password, role, confirmPassword) VALUES (?, ?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)) {

            // Set the parameters for the SQL query
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);  // Store password as plain text
            preparedStatement.setString(3, role);
            preparedStatement.setString(4, password);  // Confirm password will be the same as password

            // Execute the query
            int result = preparedStatement.executeUpdate();
            if (result == 0) {
                throw new SQLException("User registration failed.");
            }
        }
    }

    // Helper method to show alerts
    private void showAlert(String message, AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle("Notification");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private void handleBackToLoginAction(ActionEvent actionEvent) {
        try {
            // Load the login screen (assuming it's Login.fxml)
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/path/to/Login.fxml"));
            Scene loginScene = new Scene(loader.load());

            // Get the current window (Stage) and set the scene to the login screen
            Stage stage = (Stage) usernameField.getScene().getWindow();
            stage.setScene(loginScene);
            stage.setTitle("Login");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Failed to load login screen.", AlertType.ERROR);
        }
    }
}
