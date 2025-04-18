package com.example.vehiclemanagement;

import javafx.beans.property.*;

public class PaymentReport {
    private final StringProperty customerName;
    private final StringProperty vehicleName;
    private final DoubleProperty amountPaid;
    private final StringProperty paymentDate;
    private final StringProperty paymentMethod;

    public PaymentReport(String customerName, String vehicleName, double amountPaid, String paymentDate, String paymentMethod) {
        this.customerName = new SimpleStringProperty(customerName);
        this.vehicleName = new SimpleStringProperty(vehicleName);
        this.amountPaid = new SimpleDoubleProperty(amountPaid);
        this.paymentDate = new SimpleStringProperty(paymentDate);
        this.paymentMethod = new SimpleStringProperty(paymentMethod);
    }

    public String getCustomerName() { return customerName.get(); }
    public StringProperty customerNameProperty() { return customerName; }

    public String getVehicleName() { return vehicleName.get(); }
    public StringProperty vehicleNameProperty() { return vehicleName; }

    public double getAmountPaid() { return amountPaid.get(); }
    public DoubleProperty amountPaidProperty() { return amountPaid; }

    public String getPaymentDate() { return paymentDate.get(); }
    public StringProperty paymentDateProperty() { return paymentDate; }

    public String getPaymentMethod() { return paymentMethod.get(); }
    public StringProperty paymentMethodProperty() { return paymentMethod; }
}
