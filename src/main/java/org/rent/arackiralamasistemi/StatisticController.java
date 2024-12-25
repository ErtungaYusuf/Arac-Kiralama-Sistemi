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
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
public class StatisticController {
    private Parent root;
    private Stage stage;
    private Scene scene;

    String url = "jdbc:mysql://localhost:3306/AracKiralama";
    String username = "root";
    String password = "mannertribomb19A";

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
    private void connectToDatabase() {
        try {
            connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public DatabaseConnection db = new DatabaseConnection();
    private ObservableList<ObservableList> data;
    private  Connection connection;


    @FXML
    private TableView<ObservableList> TableView;
    @FXML
    Button CarCountButton;
    @FXML
    Button MaintenceCarsButton;
    @FXML
    Button RentAmountButton;
    @FXML
    Button CustomerStatisticButton;

    @FXML
    public void initialize() {
        connectToDatabase();
    }
    public void buildData(String sql) throws SQLException {
        data = FXCollections.observableArrayList();

        ResultSet rs = connection.createStatement().executeQuery(sql);

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
    void OnExitClick(ActionEvent event) {
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        changeScene(stage, "login-screen");
    }

    @FXML
    void OnBackClick(ActionEvent event) {
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        changeScene(stage, "customers-menu");
    }

    @FXML
    void maintenceCarsAction(){
        String query = """
                SELECT 
                    Araclar.AracID,
                    MarkaveModel.Marka,
                    MarkaveModel.Model,
                    Araclar.Plaka,
                    Bakim.BakimTarihi,
                    Bakim.Maliyet,
                    Bakim.Aciklama
                FROM 
                    Araclar
                INNER JOIN 
                    MarkaveModel ON Araclar.MarkaModelID = MarkaveModel.MarkaModelID
                INNER JOIN 
                    Bakim ON Araclar.AracID = Bakim.AracID
                WHERE 
                    Araclar.Durum = 'Bakımda';
                """;
        try {
            buildData(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    void rentAmountAction(){
        String query = """
                SELECT 
                    Araclar.Plaka,
                    MarkaveModel.Marka,
                    MarkaveModel.Model,
                    COUNT(Kiralama.KiraID) AS KiralanmaSayisi
                FROM 
                    Kiralama
                INNER JOIN 
                    Araclar ON Kiralama.AracID = Araclar.AracID
                INNER JOIN 
                    MarkaveModel ON Araclar.MarkaModelID = MarkaveModel.MarkaModelID
                GROUP BY 
                    Araclar.AracID, Araclar.Plaka, MarkaveModel.Marka, MarkaveModel.Model
                ORDER BY 
                    KiralanmaSayisi DESC;
                """;
        try {
            buildData(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    void customerStatisticAction(){
        String query = """
                SELECT 
                    Musteriler.Isim,
                    Musteriler.Soyisim,
                    COUNT(Kiralama.KiraID) AS KiralamaSayisi,
                    SUM(Kiralama.ToplamUcret) AS ToplamOdeme
                FROM 
                    Kiralama
                INNER JOIN 
                    Musteriler ON Kiralama.MusteriID = Musteriler.MusteriID
                GROUP BY 
                    Musteriler.MusteriID, Musteriler.Isim, Musteriler.Soyisim
                ORDER BY 
                    ToplamOdeme DESC;
                """;
        try {
            buildData(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    void carCountAction(){
        String query = """
                SELECT 
                    Araclar.Plaka,
                    MarkaveModel.Marka,
                    MarkaveModel.Model,
                    Araclar.GundelikKiraBedeli
                FROM 
                    Araclar
                INNER JOIN 
                    MarkaveModel ON Araclar.MarkaModelID = MarkaveModel.MarkaModelID
                ORDER BY 
                    Araclar.GundelikKiraBedeli DESC;
                """;
        try {
            buildData(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
