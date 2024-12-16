package org.rent.arackiralamasistemi;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class Controller {

    private Parent root;
    private Stage stage;
    private Scene scene;

    @FXML
    private Label createAccountLink;

    @FXML
    private Button loginButton;

    @FXML
    private PasswordField password;


    @FXML
    private TextField username;

    // hatalı login işleminde değiştirilmek için atanmış label:
    @FXML
    private Label wrongCredentialsLabel;

    // Login ekranı, hesap oluştur butonu tıklandığında:
    @FXML
    void onCreateAccount(MouseEvent event) {

    }

    // Login ekranı giriş yap butonu tıklandığında:
    @FXML
    void onLoginButtonClick(ActionEvent event) {
           //databaseden çekme ve kontrol etme işlemi
           // şimdilik doğrudan geçiş izni verilmiş durumda:
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

}
