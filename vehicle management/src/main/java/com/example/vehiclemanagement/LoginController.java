package com.example.vehiclemanagement;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.control.Alert.AlertType;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    public void handleLoginAction(ActionEvent actionEvent) {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (username.isEmpty() || password.isEmpty()) {
            showAlert("Both fields are required!", AlertType.ERROR);
            return;
        }

        try {
            if (isValidLogin(username, password)) {
                String role = getUserRole(username);
                navigateToDashboard(role);
            } else {
                showAlert("Invalid username or password!", AlertType.ERROR);
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
            showAlert("An error occurred. Please try again later.", AlertType.ERROR);
        }
    }

    private boolean isValidLogin(String username, String password) throws SQLException {
        String query = "SELECT password FROM users WHERE username = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, username);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    String storedPassword = resultSet.getString("password");
                    return password.equals(storedPassword); // Just plain text comparison
                }
            }
        }
        return false;
    }

    private String getUserRole(String username) throws SQLException {
        String query = "SELECT role FROM users WHERE username = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, username);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getString("role");
                }
            }
        }
        return null;
    }

    private void navigateToDashboard(String role) throws IOException {
        String fxmlFile = null;

        if ("admin".equalsIgnoreCase(role)) {
            fxmlFile = "/com/example/vehiclemanagement/admin_dashboard.fxml"; // Correct path
        } else if ("employee".equalsIgnoreCase(role)) {
            fxmlFile = "/com/example/vehiclemanagement/employeeDashboard.fxml"; // Correct path
        }

        if (fxmlFile != null) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            Scene dashboardScene = new Scene(loader.load());

            Stage stage = (Stage) usernameField.getScene().getWindow();
            stage.setScene(dashboardScene);
            stage.setTitle(role + " Dashboard");
            stage.show();
        } else {
            showAlert("Role not recognized!", AlertType.ERROR);
        }
    }

    @FXML
    public void handleSignUpAction(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/vehiclemanagement/Signup.fxml"));
            Scene signupScene = new Scene(loader.load());

            Stage stage = (Stage) usernameField.getScene().getWindow();
            stage.setScene(signupScene);
            stage.setTitle("Sign Up");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Failed to load signup screen.", AlertType.ERROR);
        }
    }

    private void showAlert(String message, AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle("Notification");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
