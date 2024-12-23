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

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

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

    @FXML
    private Label wrongCredentialsLabel;

    @FXML
    void onCreateAccount(MouseEvent event) {

    }

    @FXML
    void onLoginButtonClick(ActionEvent event) {
        if (username.getText().equals("b") && password.getText().equals("b")){
            // Şimdilik atlamalı giriş
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            changeScene(stage, "customers-menu");
        }else{
        if (validateLogin(username.getText(), password.getText())) {
            // Giriş başarılı
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            changeScene(stage, "customers-menu");
        } else {
            wrongCredentialsLabel.setText("Şifreniz veya kullanıcı adınız hatalıdır");

         }
        }
    }


    private boolean validateLogin(String username, String password) {
        DatabaseConnection databaseConnection = new DatabaseConnection();
        Connection connection = databaseConnection.getConnection();
        boolean isValid = false;

        String loginQuery = "SELECT * FROM Users WHERE username = ? AND password = ?";
        String allUsersQuery = "SELECT username FROM Users";

        try {
            // Kullanıcı adını konsola yazdır
            System.out.println("Giriş yapılmaya çalışılan kullanıcı adı: " + username);

            // Tüm kullanıcı adlarını yazdır
            PreparedStatement allUsersStatement = connection.prepareStatement(allUsersQuery);
            ResultSet allUsersResultSet = allUsersStatement.executeQuery();
            System.out.println("Veritabanındaki tüm kullanıcı adları:");
            while (allUsersResultSet.next()) {
                System.out.println("- " + allUsersResultSet.getString("username"));
            }
            allUsersResultSet.close();
            allUsersStatement.close();

            // Giriş kontrolü
            PreparedStatement loginStatement = connection.prepareStatement(loginQuery);
            loginStatement.setString(1, username);
            loginStatement.setString(2, password);
            ResultSet loginResultSet = loginStatement.executeQuery();

            if (loginResultSet.next()) {
                isValid = true; // Kullanıcı bulundu
            } else {
                System.out.println("Hatalı giriş: Kullanıcı adı veya şifre yanlış.");
            }

            loginResultSet.close();
            loginStatement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isValid;
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
