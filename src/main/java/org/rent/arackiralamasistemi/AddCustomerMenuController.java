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

public class AddCustomerMenuController {

    private Parent root;
    private Stage stage;
    private Scene scene;

    @FXML
    private TextField EliyetNoField;

    @FXML
    private TextField SoiyismField;

    @FXML
    private TextField TCKimlikNoField;

    @FXML
    private TextField TelefonField;

    @FXML
    private Button ekleButton;

    @FXML
    private TextField İsimField;

    @FXML
    void OnExitClick(ActionEvent event) {
        // Uygulamayı kapatma
        Stage stage = (Stage) ekleButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    void onEkleButtonClick(ActionEvent event) {
        // Metin alanlarından verileri al
        String tcKimlikNo = TCKimlikNoField.getText();
        String ehliyetNo = EliyetNoField.getText();
        String isim = İsimField.getText();
        String soyisim = SoiyismField.getText();
        String telefon = TelefonField.getText();

        // Boş alan kontrolü
        if (tcKimlikNo.isEmpty() || ehliyetNo.isEmpty() || isim.isEmpty() || soyisim.isEmpty() || telefon.isEmpty()) {
            showAlert("Hata", "Tüm alanları doldurmanız gerekmektedir!", Alert.AlertType.ERROR);
            return;
        }

        // Veritabanına ekleme işlemi
        String query = "INSERT INTO Musteriler (TCKimlikNo, EhliyetNo, Isim, Soyisim, Telefon) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/AracKiralama", "root", "mannertribomb19A")) {
            // Transaction başlat
            connection.setAutoCommit(false);

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

                preparedStatement.setString(1, tcKimlikNo);
                preparedStatement.setString(2, ehliyetNo);
                preparedStatement.setString(3, isim);
                preparedStatement.setString(4, soyisim);
                preparedStatement.setString(5, telefon);

                int rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected > 0) {
                    // İşlem başarılı, commit yap
                    connection.commit();
                    showAlert("Başarılı", "Müşteri başarıyla eklendi!", Alert.AlertType.INFORMATION);
                    clearFields();
                } else {
                    // İşlem başarısız, rollback yap
                    connection.rollback();
                    showAlert("Hata", "Müşteri eklenemedi!", Alert.AlertType.ERROR);
                }
            } catch (SQLException e) {
                // Hata durumunda rollback yap
                connection.rollback();
                e.printStackTrace();
                showAlert("Hata", "Veritabanı hatası: " + e.getMessage(), Alert.AlertType.ERROR);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Hata", "Bağlantı hatası: " + e.getMessage(), Alert.AlertType.ERROR);
        }

    }


    @FXML
    void onGeriClick(ActionEvent event) {
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        changeScene(stage, "customers-menu");
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

    private void showAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void clearFields() {
        // Tüm metin alanlarını temizle
        TCKimlikNoField.clear();
        EliyetNoField.clear();
        İsimField.clear();
        SoiyismField.clear();
        TelefonField.clear();
    }
}

