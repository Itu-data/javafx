package com.example.vehiclemanagement;

import javafx.beans.property.*;

public class Vehicle {
    private IntegerProperty vehicleId;
    private StringProperty brand;
    private StringProperty model;
    private StringProperty category;
    private DoubleProperty rentalPricePerDay;
    private StringProperty availabilityStatus;

    public Vehicle(int vehicleId, String brand, String model, String category, double rentalPricePerDay, String availabilityStatus) {
        this.vehicleId = new SimpleIntegerProperty(vehicleId);
        this.brand = new SimpleStringProperty(brand);
        this.model = new SimpleStringProperty(model);
        this.category = new SimpleStringProperty(category);
        this.rentalPricePerDay = new SimpleDoubleProperty(rentalPricePerDay);
        this.availabilityStatus = new SimpleStringProperty(availabilityStatus);
    }

    public int getVehicleId() {
        return vehicleId.get();
    }

    public IntegerProperty vehicleIdProperty() {
        return vehicleId;
    }

    public String getBrand() {
        return brand.get();
    }

    public StringProperty brandProperty() {
        return brand;
    }

    public String getModel() {
        return model.get();
    }

    public StringProperty modelProperty() {
        return model;
    }

    public String getCategory() {
        return category.get();
    }

    public StringProperty categoryProperty() {
        return category;
    }

    public double getRentalPricePerDay() {
        return rentalPricePerDay.get();
    }

    public DoubleProperty rentalPricePerDayProperty() {
        return rentalPricePerDay;
    }

    public String getAvailabilityStatus() {
        return availabilityStatus.get();
    }

    public StringProperty availabilityStatusProperty() {
        return availabilityStatus;
    }
}
