module org.rent.arackiralamasistemi {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.rent.arackiralamasistemi to javafx.fxml;
    exports org.rent.arackiralamasistemi;
}