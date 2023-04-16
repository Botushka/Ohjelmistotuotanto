package com.example.ohjelmistotuotanto.NakymaHallinta.Majoitus;

import javafx.scene.layout.VBox;
import javafx.scene.control.Button;

public class MajoitusvarausHallintaView extends VBox {
    public MajoitusvarausHallintaView() {
        Button lisaaVaraus = new Button("Lisää varaus");
        lisaaVaraus.setOnAction(e -> lisaaVaraus());

        Button poistaVaraus = new Button("Poista varaus");
        poistaVaraus.setOnAction(e -> poistaVaraus());

        Button muokkaaVaraus = new Button("Muokkaa varaus");
        muokkaaVaraus.setOnAction(e -> muokkaaVaraus());

        Button haeVaraus = new Button("Hae varaus");
        haeVaraus.setOnAction(e -> haeVaraus());

        getChildren().addAll(lisaaVaraus, poistaVaraus, muokkaaVaraus, haeVaraus);
    }

    private void lisaaVaraus() {
        // Toteuta varauksen lisääminen
    }

    private void poistaVaraus() {
        // Toteuta varauksen poistaminen
    }

    private void muokkaaVaraus() {
        // Toteuta varauksen muokkaaminen
    }

    private void haeVaraus() {
        // Toteuta varauksen hakeminen
    }
}