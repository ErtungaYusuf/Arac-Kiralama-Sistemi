package org.rent.arackiralamasistemi;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import java.io.IOException;
import java.sql.*;

import java.util.Objects;

public class AddCarsMenuController {

    private Parent root;
    private Stage stage;
    private Scene scene;

    @FXML
    private TextField DailyRentField;

    @FXML
    private TextField DurumField;

    @FXML
    private TextField MarkaModelIDField;

    @FXML
    private TextField PlakaField;

    @FXML
    private TextField RenkField;

    @FXML
    private TextField YakıitTipiField;

    @FXML
    private Button ekleButton;

    @FXML
    void OnExitClick(ActionEvent event) {
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        changeScene(stage, "login-screen");
    }

    @FXML
    void onEkleButtonClick(ActionEvent event) {
        // Formdaki alanlardan alınan veriler
        String plaka = PlakaField.getText();
        String markaModelID = MarkaModelIDField.getText();
        String renk = RenkField.getText();
        String yakitTipi = YakıitTipiField.getText();
        String durum = DurumField.getText();
        double dailyRent = Double.parseDouble(DailyRentField.getText());

        // Veritabanı bağlantısı ve INSERT işlemi
        String query = "INSERT INTO Araclar (Plaka, MarkaModelID, Renk, YakitTipi, Durum, GundelikKiraBedeli) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/AracKiralama", "root", "mannertribomb19A");
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            // Sorguya değerleri bağlama
            preparedStatement.setString(1, plaka);
            preparedStatement.setString(2, markaModelID);
            preparedStatement.setString(3, renk);
            preparedStatement.setString(4, yakitTipi);
            preparedStatement.setString(5, durum);
            preparedStatement.setDouble(6, dailyRent);

            // INSERT işlemini gerçekleştir
            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                // Başarı mesajı
                Alert successAlert = new Alert(AlertType.INFORMATION);
                successAlert.setTitle("Başarılı");
                successAlert.setHeaderText(null);
                successAlert.setContentText("Araç başarıyla eklendi.");
                successAlert.showAndWait();

                // Formu temizle
                clearForm();
            } else {
                // Başarısızlık mesajı
                Alert errorAlert = new Alert(AlertType.ERROR);
                errorAlert.setTitle("Hata");
                errorAlert.setHeaderText(null);
                errorAlert.setContentText("Araç eklenemedi. Lütfen tekrar deneyin.");
                errorAlert.showAndWait();
            }

        } catch (SQLException e) {
            e.printStackTrace();
            // Veritabanı hatası mesajı
            Alert errorAlert = new Alert(AlertType.ERROR);
            errorAlert.setTitle("Veritabanı Hatası");
            errorAlert.setHeaderText(null);
            errorAlert.setContentText("Veritabanı hatası oluştu. Lütfen tekrar deneyin.");
            errorAlert.showAndWait();
        }
    }

    @FXML
    void onGeriClick(ActionEvent event) {
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        changeScene(stage, "cars-menu");
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

    private void clearForm() {
        // Formdaki alanları temizleme
        PlakaField.clear();
        MarkaModelIDField.clear();
        RenkField.clear();
        YakıitTipiField.clear();
        DurumField.clear();
        DailyRentField.clear();
    }
}
