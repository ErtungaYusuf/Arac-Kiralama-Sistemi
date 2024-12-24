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

public class RentsMenuController {

    private Parent root;
    private Stage stage;
    private Scene scene;

    @FXML
    private TableView<Rent> TableView;

    @FXML
    private TableColumn<Rent, Integer> KiraIDView;

    @FXML
    private TableColumn<Rent, Integer> MusteriIDView;

    @FXML
    private TableColumn<Rent, Integer> AracIDView;

    @FXML
    private TableColumn<Rent, String> StartDateView;

    @FXML
    private TableColumn<Rent, String> EndDateView;

    @FXML
    private TableColumn<Rent, Double> ToplamUcretView;

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
        KiraIDView.setCellValueFactory(new PropertyValueFactory<>("kiraID"));
        MusteriIDView.setCellValueFactory(new PropertyValueFactory<>("musteriID"));
        AracIDView.setCellValueFactory(new PropertyValueFactory<>("aracID"));
        StartDateView.setCellValueFactory(new PropertyValueFactory<>("baslangicTarihi"));
        EndDateView.setCellValueFactory(new PropertyValueFactory<>("bitisTarihi"));
        ToplamUcretView.setCellValueFactory(new PropertyValueFactory<>("toplamUcret"));

        // Verileri yükleme
        TableView.setItems(getRentsFromDatabase());
    }
    public DatabaseConnection db = new DatabaseConnection();

    private ObservableList<Rent> getRentsFromDatabase() {
        ObservableList<Rent> rents = FXCollections.observableArrayList();

        String query = "SELECT * FROM Kiralama";
        try (Connection connection = db.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                rents.add(new Rent(
                        resultSet.getInt("KiraID"),
                        resultSet.getInt("MusteriID"),
                        resultSet.getInt("AracID"),
                        resultSet.getString("BaslangicTarihi"),
                        resultSet.getString("BitisTarihi"),
                        resultSet.getDouble("ToplamUcret")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return rents;
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

