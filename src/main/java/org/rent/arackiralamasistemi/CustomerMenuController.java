package org.rent.arackiralamasistemi;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;

import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
import java.util.Objects;

public class CustomerMenuController {

    private Parent root;
    private Stage stage;
    private Scene scene;

    @FXML
    private TableView<Customer> TableView;

    @FXML
    private TableColumn<Customer, Integer> MusteriIDview;

    @FXML
    private TableColumn<Customer, String> TCview;

    @FXML
    private TableColumn<Customer, String> EhliyetView;

    @FXML
    private TableColumn<Customer, String> IsimView;

    @FXML
    private TableColumn<Customer, String> SoyisimView;

    @FXML
    private TableColumn<Customer, String> TelefonView;

    @FXML
    public void initialize() {
        // Sütunlara veri bağlama
        MusteriIDview.setCellValueFactory(new PropertyValueFactory<>("musteriID"));
        TCview.setCellValueFactory(new PropertyValueFactory<>("tcKimlikNo"));
        EhliyetView.setCellValueFactory(new PropertyValueFactory<>("ehliyetNo"));
        IsimView.setCellValueFactory(new PropertyValueFactory<>("isim"));
        SoyisimView.setCellValueFactory(new PropertyValueFactory<>("soyisim"));
        TelefonView.setCellValueFactory(new PropertyValueFactory<>("telefon"));

        // Verileri yükleme
        TableView.setItems(getCustomersFromDatabase());
    }
    public DatabaseConnection db = new DatabaseConnection();

    private ObservableList<Customer> getCustomersFromDatabase() {
        ObservableList<Customer> customers = FXCollections.observableArrayList();

        String query = "SELECT * FROM Musteriler";
        try (Connection connection = db.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                customers.add(new Customer(
                        resultSet.getInt("MusteriID"),
                        resultSet.getString("TCKimlikNo"),
                        resultSet.getString("EhliyetNo"),
                        resultSet.getString("Isim"),
                        resultSet.getString("Soyisim"),
                        resultSet.getString("Telefon")
                ));
            }

            System.out.println("Veritabanından veri çekildi.");
            customers.forEach(customer -> System.out.println(
                    "MusteriID: " + customer.getMusteriID() +
                            ", TC: " + customer.getTcKimlikNo() +
                            ", Ehliyet: " + customer.getEhliyetNo() +
                            ", İsim: " + customer.getIsim() +
                            ", Soyisim: " + customer.getSoyisim() +
                            ", Telefon: " + customer.getTelefon()
            ));

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Bağlantı sırasında bir hata oldu tableview ekleme");
        }

        return customers;
    }

    @FXML
    void OnCustomerDeleteClick(ActionEvent event) {

    }

    @FXML
    void OnNewCustomerAddOnClick(ActionEvent event) {
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        changeScene(stage, "add-customer-menu");
    }
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
