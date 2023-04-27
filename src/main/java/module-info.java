module com.example.ohjelmistotuotanto {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;

    opens com.example.ohjelmistotuotanto.Olioluokat to javafx.base;
    opens com.example.ohjelmistotuotanto.NakymaHallinta.MokkiHallinta to javafx.fxml, java.base;
    opens com.example.ohjelmistotuotanto.NakymaHallinta.AsiakasHallinta to java.base, javafx.fxml;
    opens com.example.ohjelmistotuotanto.NakymaHallinta.Alue to javafx.base, javafx.fxml;
    opens com.example.ohjelmistotuotanto.NakymaHallinta.Palvelu to javafx.base, javafx.fxml;
    opens com.example.ohjelmistotuotanto.NakymaHallinta.Majoitus to java.base, javafx.fxml;
    exports com.example.ohjelmistotuotanto;
    exports com.example.ohjelmistotuotanto.NakymaHallinta.AsiakasHallinta to javafx.fxml;
    exports com.example.ohjelmistotuotanto.NakymaHallinta.Palvelu to javafx.base, javafx.fxml;
    exports com.example.ohjelmistotuotanto.NakymaHallinta.Majoitus to java.base, javafx.fxml;



}
