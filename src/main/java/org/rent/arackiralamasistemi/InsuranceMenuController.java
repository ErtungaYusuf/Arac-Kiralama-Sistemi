package org.rent.arackiralamasistemi;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class InsuranceMenuController {

    private Parent root;
    private Stage stage;
    private Scene scene;

    @FXML
    private TableColumn<?, ?> AracIDView;

    @FXML
    private TableColumn<?, ?> EndDateView;

    @FXML
    private TableColumn<?, ?> MaliyetView;

    @FXML
    private TableColumn<?, ?> PoliceNoView;

    @FXML
    private TableColumn<?, ?> SigortaIDView;

    @FXML
    private TableColumn<?, ?> SigortaTürüView;

    @FXML
    private TableColumn<?, ?> StartDateView;

    @FXML
    private TableView<?> TableView;

    @FXML
    private Button filtersButton;

    @FXML
    void OnCars(ActionEvent event) {
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        changeScene(stage, "cars-menu");
    }

    @FXML
    void OnCustomersClick(ActionEvent event) {
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        changeScene(stage, "customers-menu");
    }

    @FXML
    void OnExitClick(ActionEvent event) {
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        changeScene(stage, "login-screen");
    }

    @FXML
    void OnFiltersClick(ActionEvent event) {

    }

    @FXML
    void OnInsurancesClick(ActionEvent event) {
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        changeScene(stage, "insurance-menu");
    }

    @FXML
    void OnMaintenanceClick(ActionEvent event) {
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        changeScene(stage, "maintenance-menu");
    }

    @FXML
    void OnRentsClick(ActionEvent event) {
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        changeScene(stage, "rents-menu");
    }

    @FXML
    void OnDeleteInsuranceClick(ActionEvent event) {

    }
    @FXML
    void OnNewInsuranceAddClick(ActionEvent event) {

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
