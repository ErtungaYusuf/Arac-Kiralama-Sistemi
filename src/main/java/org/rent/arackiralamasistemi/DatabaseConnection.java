package org.rent.arackiralamasistemi;

import java.sql.*;

public class DatabaseConnection {
    public Connection getConnection() {
        Connection connection = null;
        try {
            String url = "jdbc:mysql://localhost:3306/AracKiralama"; // Veritabanı URL'si
            String username = "root"; // Veritabanı kullanıcı adı
            String password = "mannertribomb19A"; // Veritabanı şifresi
            connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            System.out.println("Hata Mesajı: " + e.getMessage()); // Hata mesajını yazdırır
            System.out.println("Hata Kodu: " + e.getErrorCode()); // Hata kodunu yazdırır
            System.out.println("SQL Durum Kodu: " + e.getSQLState()); // SQL durum kodunu yazdırır
            e.printStackTrace(); // Hata izini yazdırır
        }

        if (connection != null) {
            System.out.println("Connection established successfully.");
        } else {
            System.out.println("Failed to establish connection.");
        }

        return connection;
    }

}
