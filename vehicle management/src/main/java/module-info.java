module com.example.vehiclemanagement {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires mysql.connector.j;
    requires java.desktop;


    opens com.example.vehiclemanagement to javafx.fxml;
    exports com.example.vehiclemanagement;
}