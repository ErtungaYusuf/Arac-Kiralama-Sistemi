package org.rent.arackiralamasistemi;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
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
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public class CarsMenuController {
    private Parent root;
    private Stage stage;
    private Scene scene;

    @FXML
    private TextField SilField;
    @FXML
    private Button silButton;

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

        String SQL = "SELECT A.AracID, A.Plaka, MM.Marka, MM.Model, A.GundelikKiraBedeli, A.YakitTipi, A.Renk, A.Durum " +
                "FROM araclar A " +
                "JOIN markavemodel MM ON A.MarkaModelID = MM.MarkaModelID";
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
    void OnCars(ActionEvent event) {
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        changeScene(stage, "cars-menu");
    }

    @FXML
    void onSilClick(ActionEvent event) {
        // Get the vehicle ID from the text field
        String aracIDStr = SilField.getText();

        // Check if the input is empty
        if (aracIDStr.isEmpty()) {
            System.out.println("Araç ID'si girilmedi.");
            return;
        }

        // Convert the input to an integer (vehicle ID)
        int aracID = Integer.parseInt(aracIDStr);

        // Database connection and delete query
        String query = "DELETE FROM araclar WHERE AracID = ?";

        try (Connection connection = db.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            // Bind the vehicle ID to the query
            preparedStatement.setInt(1, aracID);

            // Execute the delete query
            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Araç başarıyla silindi.");
                // If deletion is successful, refresh the table data
                buildData();
            } else {
                System.out.println("ID ile araç bulunamadı.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Silme işlemi sırasında bir hata oluştu.");
        }
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
    void OnNewCarAddClick(ActionEvent event) {

    }

}
