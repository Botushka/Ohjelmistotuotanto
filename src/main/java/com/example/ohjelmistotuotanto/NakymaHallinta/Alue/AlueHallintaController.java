package com.example.ohjelmistotuotanto.NakymaHallinta.Alue;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class AlueHallintaController extends BorderPane {


    private void lisaaAlue() {
        // Toteuta alueen lisääminen
    }

    private void poistaAlue() {
        // Toteuta alueen poistaminen
    }

    private void muokkaaAlue() {
        // Toteuta alueen muokkaaminen
    }

    private void haeAlue() {
        // Toteuta alueen hakeminen
    }

    public AlueHallintaController() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/ohjelmistotuotanto/alue.fxml"));
        loader.setController(this);
        try {
            setCenter(loader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
