package org.rent.arackiralamasistemi;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;

public class BakimMenuController  {

    private Parent root;
    private Stage stage;
    private Scene scene;

    @FXML
    private TableView<Maintenance> TableView;

    @FXML
    private TableColumn<Maintenance, Integer> BakimIDView;

    @FXML
    private TableColumn<Maintenance, Integer> AracIDView;

    @FXML
    private TableColumn<Maintenance, String> BakimTarihiView;

    @FXML
    private TableColumn<Maintenance, Double> MaliyetView;

    @FXML
    private TableColumn<Maintenance, String> AciklamaView;

    @FXML
    private Button filtersButton;

    @FXML
    void OnCars(ActionEvent event) {
        // Boş bırakıldı
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
        // Boş bırakıldı
    }

    @FXML
    void OnInsurancesClick(ActionEvent event) {
        // Boş bırakıldı
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
    public void initialize() {
        // Sütunlara veri bağlama
        BakimIDView.setCellValueFactory(new PropertyValueFactory<>("bakimID"));
        AracIDView.setCellValueFactory(new PropertyValueFactory<>("aracID"));
        BakimTarihiView.setCellValueFactory(new PropertyValueFactory<>("bakimTarihi"));
        MaliyetView.setCellValueFactory(new PropertyValueFactory<>("maliyet"));
        AciklamaView.setCellValueFactory(new PropertyValueFactory<>("aciklama"));

        // Verileri yükleme
        TableView.setItems(getMaintenanceFromDatabase());
    }

    private ObservableList<Maintenance> getMaintenanceFromDatabase() {
        ObservableList<Maintenance> maintenanceList = FXCollections.observableArrayList();

        String query = "SELECT * FROM Bakim";
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/AracKiralama", "root", "mannertribomb19A");
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                maintenanceList.add(new Maintenance(
                        resultSet.getInt("BakimID"),
                        resultSet.getInt("AracID"),
                        resultSet.getString("BakimTarihi"),
                        resultSet.getDouble("Maliyet"),
                        resultSet.getString("Aciklama")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return maintenanceList;
    }
    private void changeScene(Stage stage, String fxmlName) {
        try {
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(fxmlName + ".fxml")));
            scene = new Scene(root);
            stage.setTitle("Araç Kiralama Sistemi");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            System.out.println("Hata fxmle giriş Yapılamıyor.");
        }
    }
}