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
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Objects;

public class AddRentsMenuController {

    private Parent root;
    private Stage stage;
    private Scene scene;


    @FXML
    private TextField AracIDField;

    @FXML
    private TextField EndDateField;

    @FXML
    private TextField MusteriIDField;

    @FXML
    private TextField StartDateField;

    @FXML
    private TextField ToplamUcretField;

    @FXML
    private Button ekleButton;

    @FXML
    void OnExitClick(ActionEvent event) {
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        changeScene(stage, "login-screen");
    }

    @FXML
    void onEkleButtonClick(ActionEvent event) {
        // Retrieve data from text fields
        String musteriIDStr = MusteriIDField.getText();
        String aracIDStr = AracIDField.getText();
        String startDate = StartDateField.getText();
        String endDate = EndDateField.getText();
        String toplamUcretStr = ToplamUcretField.getText();

        // Validation: Ensure no fields are empty
        if (musteriIDStr.isEmpty() || aracIDStr.isEmpty() || startDate.isEmpty() || endDate.isEmpty() || toplamUcretStr.isEmpty()) {
            showAlert("Hata", "Tüm alanları doldurmanız gerekmektedir!", Alert.AlertType.ERROR);
            return;
        }

        // Convert values to appropriate types
        int musteriID = Integer.parseInt(musteriIDStr);
        int aracID = Integer.parseInt(aracIDStr);
        double toplamUcret = Double.parseDouble(toplamUcretStr);

        // SQL query to insert data into the Kiralama table
        String query = "INSERT INTO Kiralama (MusteriID, AracID, BaslangicTarihi, BitisTarihi, ToplamUcret) VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/AracKiralama", "root", "mannertribomb19A");
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, musteriID);
            preparedStatement.setInt(2, aracID);
            preparedStatement.setString(3, startDate);
            preparedStatement.setString(4, endDate);
            preparedStatement.setDouble(5, toplamUcret);

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                showAlert("Başarılı", "Kiralama başarıyla eklendi!", Alert.AlertType.INFORMATION);
                clearFields();
            } else {
                showAlert("Hata", "Kiralama eklenemedi!", Alert.AlertType.ERROR);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Hata", "Veritabanı hatası: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    void onGeriClick(ActionEvent event) {
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        changeScene(stage, "rents-menu");
    }
    private void showAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void clearFields() {
        // Clear all the text fields
        MusteriIDField.clear();
        AracIDField.clear();
        StartDateField.clear();
        EndDateField.clear();
        ToplamUcretField.clear();
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
