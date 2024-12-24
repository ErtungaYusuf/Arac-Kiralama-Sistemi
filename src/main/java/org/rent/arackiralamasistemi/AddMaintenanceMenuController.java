package org.rent.arackiralamasistemi;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
import java.util.Objects;

public class AddMaintenanceMenuController {

    private Parent root;
    private Stage stage;
    private Scene scene;

    @FXML
    private TextField AciklamaField;

    @FXML
    private TextField AracIDField;

    @FXML
    private TextField BakimTarihField;

    @FXML
    private TextField MaliyetField;

    @FXML
    private Button ekleButton;

    @FXML
    void OnExitClick(ActionEvent event) {
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        changeScene(stage, "login-screen");
    }

    @FXML
    void onEkleButtonClick(ActionEvent event) {
        // Retrieve data from the text fields
        String aracID = AracIDField.getText();
        String bakimTarihi = BakimTarihField.getText();
        String maliyet = MaliyetField.getText();
        String aciklama = AciklamaField.getText();

        // Validate that none of the fields are empty
        if (aracID.isEmpty() || bakimTarihi.isEmpty() || maliyet.isEmpty() || aciklama.isEmpty()) {
            showAlert("Hata", "Tüm alanları doldurmanız gerekmektedir!", Alert.AlertType.ERROR);
            return;
        }

        // Convert the cost to a double
        double maliyetValue;
        try {
            maliyetValue = Double.parseDouble(maliyet);
        } catch (NumberFormatException e) {
            showAlert("Hata", "Maliyet geçerli bir sayı olmalıdır!", Alert.AlertType.ERROR);
            return;
        }

        // Insert the data into the database
        String query = "INSERT INTO Bakim (AracID, BakimTarihi, Maliyet, Aciklama) VALUES (?, ?, ?, ?)";
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/AracKiralama", "root", "mannertribomb19A");
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            // Set parameters for the query
            preparedStatement.setInt(1, Integer.parseInt(aracID)); // Set the AracID as an integer
            preparedStatement.setDate(2, Date.valueOf(bakimTarihi)); // Set the BakimTarihi as a Date (ensure correct format: YYYY-MM-DD)
            preparedStatement.setDouble(3, maliyetValue); // Set the Maliyet as a double
            preparedStatement.setString(4, aciklama); // Set the Aciklama as a string

            // Execute the query and check the result
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                showAlert("Başarılı", "Bakım başarıyla eklendi!", Alert.AlertType.INFORMATION);
                clearFields(); // Clear the input fields after successful insertion
            } else {
                showAlert("Hata", "Bakım eklenemedi!", Alert.AlertType.ERROR);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Hata", "Veritabanı hatası: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    // Helper method to show alerts
    private void showAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Helper method to clear all fields
    private void clearFields() {
        AracIDField.clear();
        BakimTarihField.clear();
        MaliyetField.clear();
        AciklamaField.clear();
    }



    @FXML
    void onGeriClick(ActionEvent event) {
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        changeScene(stage, "maintenance-menu");
    }

    private void changeScene(Stage stage, String fxmlName) {
        try {
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(fxmlName + ".fxml")));
            scene = new Scene(root);
            stage.setTitle("Araç Kiralama Sistemi");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            System.out.println("Hata Giriş Yapılamıyor.");
        }
    }

}

