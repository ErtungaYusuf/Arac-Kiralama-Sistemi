package org.rent.arackiralamasistemi;

import javafx.beans.property.SimpleStringProperty;
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
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public class InsuranceMenuController {

    private Parent root;
    private Stage stage;
    private Scene scene;

    public DatabaseConnection db = new DatabaseConnection();
    private ObservableList<ObservableList> data;

    @FXML
    private TableView<ObservableList> TableView;
    @FXML
    public void initialize() {
        try {
            buildData();
        } catch (SQLException e) {
            System.out.println("Veri yüklenirken hata: " + e.getMessage());
        }
    }
    public void buildData() throws SQLException {
        Connection c = db.getConnection();
        data = FXCollections.observableArrayList();

        String SQL = "SELECT * FROM Sigortalar";
        ResultSet rs = c.createStatement().executeQuery(SQL);

        TableView.getColumns().clear();

        for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
            final int j = i;
            TableColumn<ObservableList, String> col = new TableColumn<>(rs.getMetaData().getColumnName(i + 1));
            col.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().get(j).toString()));
            TableView.getColumns().add(col);
        }

        while (rs.next()) {
            ObservableList<String> row = FXCollections.observableArrayList();
            for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                row.add(rs.getString(i));
            }
            data.add(row);
        }

        TableView.setItems(data);
    }

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
