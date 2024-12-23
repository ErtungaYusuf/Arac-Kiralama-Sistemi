module org.rent.arackiralamasistemi {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens org.rent.arackiralamasistemi to javafx.fxml;
    exports org.rent.arackiralamasistemi;
}