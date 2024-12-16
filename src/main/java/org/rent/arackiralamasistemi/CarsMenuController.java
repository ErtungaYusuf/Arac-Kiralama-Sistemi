package org.rent.arackiralamasistemi;
import javafx.event.ActionEvent;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class CarsMenuController {

    private Parent root;
    private Stage stage;
    private Scene scene;

    @FXML
    void OnCars(ActionEvent event) {

    }

    @FXML
    void OnCustomersClick(ActionEvent event) {

    }

    @FXML
    void OnExitClick(ActionEvent event) {
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        changeScene(stage, "login-screen");
    }

    @FXML
    void OnInsurancesClick(ActionEvent event) {

    }

    @FXML
    void OnMaintenanceClick(ActionEvent event) {

    }

    @FXML
    void OnRentsClick(ActionEvent event) {

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
