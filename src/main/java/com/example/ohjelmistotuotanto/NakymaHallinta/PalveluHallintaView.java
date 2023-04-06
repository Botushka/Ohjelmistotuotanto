package com.example.ohjelmistotuotanto.NakymaHallinta;

import javafx.scene.layout.VBox;
import javafx.scene.control.Button;

public class PalveluHallintaView extends VBox {
    public PalveluHallintaView() {
        Button lisaaPalvelu = new Button("Lisää palvelu");
        lisaaPalvelu.setOnAction(e -> lisaaPalvelu());

        Button poistaPalvelu = new Button("Poista palvelu");
        poistaPalvelu.setOnAction(e -> poistaPalvelu());

        Button muokkaaPalvelu = new Button("Muokkaa palvelu");
        muokkaaPalvelu.setOnAction(e -> muokkaaPalvelu());

        Button haePalvelu = new Button("Hae palvelu");
        haePalvelu.setOnAction(e -> haePalvelu());

        getChildren().addAll(lisaaPalvelu, poistaPalvelu, muokkaaPalvelu, haePalvelu);
    }

    private void lisaaPalvelu() {
        // Toteuta palvelun lisääminen
    }

    private void poistaPalvelu() {
        // Toteuta palvelun poistaminen
    }

    private void muokkaaPalvelu() {
        // Toteuta palvelun muokkaaminen
    }

    private void haePalvelu() {
        // Toteuta palvelun hakeminen
    }
}