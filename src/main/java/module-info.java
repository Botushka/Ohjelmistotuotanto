module com.example.ohjelmistotuotanto {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.example.ohjelmistotuotanto.Olioluokat to javafx.base;
    opens com.example.ohjelmistotuotanto.NakymaHallinta.MokkiHallinta to javafx.fxml, java.base;
    exports com.example.ohjelmistotuotanto;

}