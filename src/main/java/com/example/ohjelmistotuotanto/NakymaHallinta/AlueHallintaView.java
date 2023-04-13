package com.example.ohjelmistotuotanto.NakymaHallinta;

import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class AlueHallintaView extends VBox {
    public AlueHallintaView() {
        Button lisaaAlue = new Button("Lisää alue");
        lisaaAlue.setOnAction(e -> lisaaAlue());

        Button poistaAlue = new Button("Poista alue");
        poistaAlue.setOnAction(e -> poistaAlue());

        Button muokkaaAlue = new Button("Muokkaa alue");
        muokkaaAlue.setOnAction(e -> muokkaaAlue());

        Button haeAlue = new Button("Hae alue");
        haeAlue.setOnAction(e -> haeAlue());

        getChildren().addAll(lisaaAlue, poistaAlue, muokkaaAlue, haeAlue);
    }

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
}
