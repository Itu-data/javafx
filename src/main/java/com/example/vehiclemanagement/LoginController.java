package com.example.vehiclemanagement;

import javafx.animation.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.stage.Stage;
import javafx.util.Duration;

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
    private Circle floatingCircle1, floatingCircle2;

    @FXML
    private VBox formBox;

    @FXML
    public void initialize() {
        fadeInForm();
        setupAnimations();
        setupHoverEffects();
    }

    private void fadeInForm() {
        FadeTransition fade = new FadeTransition(Duration.seconds(1.5), formBox);
        fade.setFromValue(0);
        fade.setToValue(1);
        fade.play();
    }

    private void setupAnimations() {
        if (floatingCircle1 != null && floatingCircle2 != null) {
            animateCircle(floatingCircle1, 80, 120, 25, 4000);
            animateCircle(floatingCircle2, 250, 200, 18, 4500);
        }

        if (formBox != null) {
            Timeline formFloat = new Timeline(
                    new KeyFrame(Duration.ZERO, new KeyValue(formBox.translateYProperty(), 0)),
                    new KeyFrame(Duration.seconds(3), new KeyValue(formBox.translateYProperty(), -5)),
                    new KeyFrame(Duration.seconds(6), new KeyValue(formBox.translateYProperty(), 0))
            );
            formFloat.setCycleCount(Timeline.INDEFINITE);
            formFloat.setAutoReverse(true);
            formFloat.play();
        }
    }

    private void animateCircle(Circle circle, double centerX, double centerY, double radius, double duration) {
        Path path = new Path();
        path.getElements().add(new MoveTo(centerX + radius, centerY));
        path.getElements().add(new ArcTo(radius, radius, 0, centerX - radius, centerY, false, true));
        path.getElements().add(new ArcTo(radius, radius, 0, centerX + radius, centerY, false, true));

        PathTransition transition = new PathTransition(Duration.millis(duration), path, circle);
        transition.setCycleCount(PathTransition.INDEFINITE);
        transition.play();
    }

    private void setupHoverEffects() {
        if (usernameField != null) setupFieldHover(usernameField);
        if (passwordField != null) setupFieldHover(passwordField);
    }

    private void setupFieldHover(Control control) {
        final double SCALE = 1.03;
        final Duration DURATION = Duration.millis(150);
        DropShadow shadow = new DropShadow(10, Color.web("#d9c7ff"));

        control.setOnMouseEntered(e -> {
            ScaleTransition st = new ScaleTransition(DURATION, control);
            st.setToX(SCALE);
            st.setToY(SCALE);
            control.setEffect(shadow);
            st.play();
        });

        control.setOnMouseExited(e -> {
            ScaleTransition st = new ScaleTransition(DURATION, control);
            st.setToX(1.0);
            st.setToY(1.0);
            control.setEffect(null);
            st.play();
        });
    }

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
                shakeLoginForm();
                showAlert("Invalid username or password!", AlertType.ERROR);
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
            showAlert("An error occurred. Please try again later.", AlertType.ERROR);
        }
    }

    private void shakeLoginForm() {
        if (formBox != null) {
            Timeline shake = new Timeline(
                    new KeyFrame(Duration.millis(0), new KeyValue(formBox.translateXProperty(), 0)),
                    new KeyFrame(Duration.millis(100), new KeyValue(formBox.translateXProperty(), 10)),
                    new KeyFrame(Duration.millis(200), new KeyValue(formBox.translateXProperty(), -10)),
                    new KeyFrame(Duration.millis(300), new KeyValue(formBox.translateXProperty(), 10)),
                    new KeyFrame(Duration.millis(400), new KeyValue(formBox.translateXProperty(), 0))
            );
            shake.play();
        }
    }

    private boolean isValidLogin(String username, String password) throws SQLException {
        String query = "SELECT password FROM users WHERE username = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String storedPassword = rs.getString("password");
                    return password.equals(storedPassword);
                }
            }
        }
        return false;
    }

    private String getUserRole(String username) throws SQLException {
        String query = "SELECT role FROM users WHERE username = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("role");
                }
            }
        }
        return null;
    }

    private void navigateToDashboard(String role) throws IOException {
        String fxmlFile = role.equalsIgnoreCase("admin") ?
                "/com/example/vehiclemanagement/admin_dashboard.fxml" :
                "/com/example/vehiclemanagement/employee_dashboard.fxml";

        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
        Scene scene = new Scene(loader.load());
        Stage stage = (Stage) usernameField.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void handleSignUpAction(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/vehiclemanagement/signup.fxml"));
            Scene scene = new Scene(loader.load());
            Stage stage = (Stage) usernameField.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Unable to open sign-up form.", AlertType.ERROR);
        }
    }

    private void showAlert(String message, AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle("Login");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}