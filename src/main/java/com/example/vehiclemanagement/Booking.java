package com.example.vehiclemanagement;

import javafx.beans.property.*;
import javafx.event.ActionEvent;

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
    }

    public void handleUpdateBooking(ActionEvent actionEvent) {
    }

    public void handleCancelBooking(ActionEvent actionEvent) {
    }
}
