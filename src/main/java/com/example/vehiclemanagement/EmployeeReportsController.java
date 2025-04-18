package com.example.vehiclemanagement;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.TableView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileOutputStream;
import java.sql.*;
import java.util.Date;

public class EmployeeReportsController {

    @FXML private TableView<PaymentReport> paymentReportTable;
    @FXML private TableColumn<PaymentReport, String> customerNameColumn;
    @FXML private TableColumn<PaymentReport, String> vehicleNameColumn;
    @FXML private TableColumn<PaymentReport, Double> amountPaidColumn;
    @FXML private TableColumn<PaymentReport, String> paymentDateColumn;
    @FXML private TableColumn<PaymentReport, String> paymentMethodColumn;

    private ObservableList<PaymentReport> reportList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        customerNameColumn.setCellValueFactory(cell -> cell.getValue().customerNameProperty());
        vehicleNameColumn.setCellValueFactory(cell -> cell.getValue().vehicleNameProperty());
        amountPaidColumn.setCellValueFactory(cell -> cell.getValue().amountPaidProperty().asObject());
        paymentDateColumn.setCellValueFactory(cell -> cell.getValue().paymentDateProperty());
        paymentMethodColumn.setCellValueFactory(cell -> cell.getValue().paymentMethodProperty());

        loadPaymentData();
    }

    private void loadPaymentData() {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "SELECT customer_name, vehicle_name, amount_paid, payment_date, payment_method FROM payments";
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                // Add each payment to the reportList
                reportList.add(new PaymentReport(
                        rs.getString("customer_name"),
                        rs.getString("vehicle_name"),
                        rs.getDouble("amount_paid"),
                        rs.getString("payment_date"),
                        rs.getString("payment_method")
                ));
            }

            // Set the table items to the reportList
            paymentReportTable.setItems(reportList);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @FXML
    private void handleExportReport() {
        // Create a FileChooser instance
        FileChooser fileChooser = new FileChooser();

        // Set the extension filter to PDF files
        FileChooser.ExtensionFilter pdfFilter = new FileChooser.ExtensionFilter("PDF Files (*.pdf)", "*.pdf");
        fileChooser.getExtensionFilters().add(pdfFilter);

        // Show the save file dialog and get the file the user wants to save the PDF as
        File file = fileChooser.showSaveDialog(new Stage());

        // If the user selected a file, proceed with the PDF export
        if (file != null) {
            Document document = new Document();
            try {
                // Create a PdfWriter instance and specify the file chosen by the user
                PdfWriter.getInstance(document, new FileOutputStream(file));

                // Open the document for writing
                document.open();

                // Add some content to the document (you can replace this with your own content)
                document.add(new com.itextpdf.text.Paragraph("This is a sample customer payments report"));

                // Close the document after writing
                document.close();

                // Optionally, show a success message
                showAlert(Alert.AlertType.INFORMATION, "Success", "Report exported successfully to: " + file.getAbsolutePath());

            } catch (Exception e) {
                // Handle any exceptions that occur during PDF creation
                showAlert(Alert.AlertType.ERROR, "Error", "An error occurred while exporting the report.");
                e.printStackTrace();
            }
        }
    }

    // Utility method for displaying alerts
    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }}
