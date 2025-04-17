package com.example.vehiclemanagement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.sql.*;
import java.time.LocalDate;

public class BookingsController {

    @FXML
    private TextField customerNameField;

    @FXML
    private TextField vehicleModelField;

    @FXML
    private DatePicker rentalStartDatePicker;

    @FXML
    private DatePicker rentalEndDatePicker;

    @FXML
    private ComboBox<String> bookingStatusComboBox;

    @FXML
    private TableView<Booking> bookingTable;

    @FXML
    private TableColumn<Booking, String> customerNameColumn;

    @FXML
    private TableColumn<Booking, String> vehicleModelColumn;

    @FXML
    private TableColumn<Booking, LocalDate> startDateColumn;

    @FXML
    private TableColumn<Booking, LocalDate> endDateColumn;

    @FXML
    private TableColumn<Booking, String> statusColumn;

    private Connection connection;
    private ObservableList<Booking> bookingList = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        connectDatabase();

        bookingStatusComboBox.getItems().addAll("Pending", "Confirmed", "Cancelled", "Completed");

        customerNameColumn.setCellValueFactory(data -> data.getValue().customerNameProperty());
        vehicleModelColumn.setCellValueFactory(data -> data.getValue().vehicleModelProperty());
        startDateColumn.setCellValueFactory(data -> data.getValue().rentalStartDateProperty());
        endDateColumn.setCellValueFactory(data -> data.getValue().rentalEndDateProperty());
        statusColumn.setCellValueFactory(data -> data.getValue().bookingStatusProperty());

        bookingTable.setItems(bookingList);

        loadBookings();
    }

    private void connectDatabase() {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/vehiclemanagement", "root", "901017297");
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Database Error", "Failed to connect to database: " + e.getMessage());
        }
    }

    @FXML
    private void handleSaveBooking() {
        String customerName = customerNameField.getText();
        String vehicleModel = vehicleModelField.getText();
        LocalDate rentalStart = rentalStartDatePicker.getValue();
        LocalDate rentalEnd = rentalEndDatePicker.getValue();
        String bookingStatus = bookingStatusComboBox.getValue();

        if (customerName.isEmpty() || vehicleModel.isEmpty() || rentalStart == null || rentalEnd == null || bookingStatus == null) {
            showAlert(Alert.AlertType.ERROR, "Validation Error", "Please fill all fields before saving.");
            return;
        }

        try {
            String sql = "INSERT INTO bookings (customer_name, vehicle_model, rental_start_date, rental_end_date, booking_status) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, customerName);
            statement.setString(2, vehicleModel);
            statement.setDate(3, Date.valueOf(rentalStart));
            statement.setDate(4, Date.valueOf(rentalEnd));
            statement.setString(5, bookingStatus);

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                showAlert(Alert.AlertType.INFORMATION, "Success", "Booking saved successfully!");
                clearForm();
                loadBookings();
            }
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Database Error", "Failed to save booking: " + e.getMessage());
        }
    }

    @FXML
    public void handleUpdateBooking(ActionEvent actionEvent) {
        Booking selectedBooking = bookingTable.getSelectionModel().getSelectedItem();
        if (selectedBooking == null) {
            showAlert(Alert.AlertType.WARNING, "No Selection", "Please select a booking to update.");
            return;
        }

        try {
            String sql = "UPDATE bookings SET customer_name=?, vehicle_model=?, rental_start_date=?, rental_end_date=?, booking_status=? WHERE id=?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, customerNameField.getText());
            statement.setString(2, vehicleModelField.getText());
            statement.setDate(3, Date.valueOf(rentalStartDatePicker.getValue()));
            statement.setDate(4, Date.valueOf(rentalEndDatePicker.getValue()));
            statement.setString(5, bookingStatusComboBox.getValue());
            statement.setInt(6, selectedBooking.getId());

            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                showAlert(Alert.AlertType.INFORMATION, "Success", "Booking updated successfully!");
                clearForm();
                loadBookings();
            }
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Database Error", "Failed to update booking: " + e.getMessage());
        }
    }

    @FXML
    public void handleCancelBooking(ActionEvent actionEvent) {
        Booking selectedBooking = bookingTable.getSelectionModel().getSelectedItem();
        if (selectedBooking == null) {
            showAlert(Alert.AlertType.WARNING, "No Selection", "Please select a booking to delete.");
            return;
        }

        try {
            String sql = "DELETE FROM bookings WHERE id=?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, selectedBooking.getId());

            int rowsDeleted = statement.executeUpdate();
            if (rowsDeleted > 0) {
                showAlert(Alert.AlertType.INFORMATION, "Success", "Booking deleted successfully!");
                clearForm();
                loadBookings();
            }
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Database Error", "Failed to delete booking: " + e.getMessage());
        }
    }

    private void loadBookings() {
        bookingList.clear();
        try {
            String sql = "SELECT * FROM bookings";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                Booking booking = new Booking(
                        resultSet.getInt("id"),
                        resultSet.getString("customer_name"),
                        resultSet.getString("vehicle_model"),
                        resultSet.getDate("rental_start_date").toLocalDate(),
                        resultSet.getDate("rental_end_date").toLocalDate(),
                        resultSet.getString("booking_status")
                );
                bookingList.add(booking);
            }
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Database Error", "Failed to load bookings: " + e.getMessage());
        }
    }

    private void clearForm() {
        customerNameField.clear();
        vehicleModelField.clear();
        rentalStartDatePicker.setValue(null);
        rentalEndDatePicker.setValue(null);
        bookingStatusComboBox.setValue(null);
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

