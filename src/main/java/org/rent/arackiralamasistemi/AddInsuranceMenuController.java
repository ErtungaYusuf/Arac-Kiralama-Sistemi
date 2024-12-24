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

public class AddInsuranceMenuController {

    private Parent root;
    private Stage stage;
    private Scene scene;

    @FXML
    private TextField AracIDField;

    @FXML
    private TextField EndDateField;

    @FXML
    private TextField MaliyetField;

    @FXML
    private TextField PoliceNoField;

    @FXML
    private TextField SigortaTuruField;

    @FXML
    private TextField StartDateField;

    @FXML
    private Button ekleButton;

    @FXML
    void OnExitClick(ActionEvent event) {
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        changeScene(stage, "login-screen");
    }

    @FXML
    void onEkleButtonClick(ActionEvent event) {
        // Retrieve the data from the text fields
        String aracID = AracIDField.getText();
        String policeNo = PoliceNoField.getText();
        String sigortaTuru = SigortaTuruField.getText();
        String startDate = StartDateField.getText();
        String endDate = EndDateField.getText();
        String maliyetStr = MaliyetField.getText();

        // Validate the fields (check for empty fields or invalid input)
        if (aracID.isEmpty() || policeNo.isEmpty() || sigortaTuru.isEmpty() || startDate.isEmpty() || endDate.isEmpty() || maliyetStr.isEmpty()) {
            showAlert("Hata", "Tüm alanları doldurmanız gerekmektedir!", Alert.AlertType.ERROR);
            return;
        }

        // Try to parse the cost as a double
        double maliyet;
        try {
            maliyet = Double.parseDouble(maliyetStr);
        } catch (NumberFormatException e) {
            showAlert("Hata", "Geçerli bir maliyet değeri girin!", Alert.AlertType.ERROR);
            return;
        }

        // Insert the data into the database
        String query = "INSERT INTO Sigortalar (AracID, PoliceNo, SigortaTuru, BaslangicTarihi, BitisTarihi, Maliyet) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/AracKiralama", "root", "mannertribomb19A");
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, Integer.parseInt(aracID));  // Set AracID as integer
            preparedStatement.setString(2, policeNo);
            preparedStatement.setString(3, sigortaTuru);
            preparedStatement.setDate(4, Date.valueOf(startDate));  // Convert String to Date
            preparedStatement.setDate(5, Date.valueOf(endDate));    // Convert String to Date
            preparedStatement.setDouble(6, maliyet);

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                showAlert("Başarılı", "Sigorta başarıyla eklendi!", Alert.AlertType.INFORMATION);
                clearFields();  // Clear fields after successful insertion
            } else {
                showAlert("Hata", "Sigorta eklenemedi!", Alert.AlertType.ERROR);
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

    // Helper method to clear fields
    private void clearFields() {
        AracIDField.clear();
        PoliceNoField.clear();
        SigortaTuruField.clear();
        StartDateField.clear();
        EndDateField.clear();
        MaliyetField.clear();
    }



    @FXML
    void onGeriClick(ActionEvent event) {
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        changeScene(stage, "insurance-menu");
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
