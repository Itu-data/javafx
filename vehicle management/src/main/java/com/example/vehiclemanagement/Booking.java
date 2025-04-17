package com.example.vehiclemanagement;

import javafx.beans.property.*;
import javafx.event.ActionEvent;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.time.LocalDate;

public class Booking{

    private final IntegerProperty id;
    private final StringProperty customerName;
    private final StringProperty vehicleModel;
    private final ObjectProperty<LocalDate> rentalStartDate;
    private final ObjectProperty<LocalDate> rentalEndDate;
    private final StringProperty bookingStatus;

    public Booking(int id, String customerName, String vehicleModel, LocalDate rentalStartDate, LocalDate rentalEndDate, String bookingStatus) {
        this.id = new SimpleIntegerProperty(id);
        this.customerName = new SimpleStringProperty(customerName);
        this.vehicleModel = new SimpleStringProperty(vehicleModel);
        this.rentalStartDate = new SimpleObjectProperty<>(rentalStartDate);
        this.rentalEndDate = new SimpleObjectProperty<>(rentalEndDate);
        this.bookingStatus = new SimpleStringProperty(bookingStatus);
    }

    // Getters
    public int getId() { return id.get(); }
    public String getCustomerName() { return customerName.get(); }
    public String getVehicleModel() { return vehicleModel.get(); }
    public LocalDate getRentalStartDate() { return rentalStartDate.get(); }
    public LocalDate getRentalEndDate() { return rentalEndDate.get(); }
    public String getBookingStatus() { return bookingStatus.get(); }

    // Properties
    public IntegerProperty idProperty() {
        return null;
    }
    public StringProperty customerNameProperty() { return customerName; }
    public StringProperty vehicleModelProperty() { return vehicleModel; }
    public ObjectProperty<LocalDate> rentalStartDateProperty() { return rentalStartDate; }
    public ObjectProperty<LocalDate> rentalEndDateProperty() { return rentalEndDate; }
    public StringProperty bookingStatusProperty() { return bookingStatus; }

    public void handleSaveBooking(ActionEvent actionEvent) {
        try {
            Connection connectDB = DatabaseConnection.getConnection();

            String insertBooking = "INSERT INTO bookings (customer_name, vehicle_model, rental_start_date, rental_end_date, booking_status) VALUES (?, ?, ?, ?, ?)";

            PreparedStatement preparedStatement = connectDB.prepareStatement(insertBooking);
            preparedStatement.setString(1, getCustomerName());
            preparedStatement.setString(2, getVehicleModel());
            preparedStatement.setDate(3, java.sql.Date.valueOf(getRentalStartDate()));
            preparedStatement.setDate(4, java.sql.Date.valueOf(getRentalEndDate()));
            preparedStatement.setString(5, getBookingStatus());

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Booking saved successfully!");
            } else {
                System.out.println("Failed to save booking.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void handleUpdateBooking(ActionEvent actionEvent) {
    }

    public void handleCancelBooking(ActionEvent actionEvent) {
    }
}
