package com.example.ohjelmistotuotanto.NakymaHallinta;

import javafx.scene.layout.VBox;
import javafx.scene.control.Button;

public class AlueMokkiHallintaView extends VBox {
    public AlueMokkiHallintaView() {
        Button lisaaAlue = new Button("Lisää alue");
        lisaaAlue.setOnAction(e -> lisaaAlue());

        Button poistaAlue = new Button("Poista alue");
        poistaAlue.setOnAction(e -> poistaAlue());

        Button muokkaaAlue = new Button("Muokkaa alue");
        muokkaaAlue.setOnAction(e -> muokkaaAlue());

        Button haeAlue = new Button("Hae alue");
        haeAlue.setOnAction(e -> haeAlue());

        Button lisaaMokki = new Button("Lisää mökki");
        lisaaMokki.setOnAction(e -> lisaaMokki());

        Button poistaMokki = new Button("Poista mökki");
        poistaMokki.setOnAction(e -> poistaMokki());

        Button muokkaaMokki = new Button("Muokkaa mökki");
        muokkaaMokki.setOnAction(e -> muokkaaMokki());

        Button haeMokki = new Button("Hae mökki");
        haeMokki.setOnAction(e -> haeMokki());

        getChildren().addAll(lisaaAlue, poistaAlue, muokkaaAlue, haeAlue, lisaaMokki, poistaMokki, muokkaaMokki, haeMokki);
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

    private void lisaaMokki() {
        // Toteuta mökin lisääminen
    }

    private void poistaMokki() {
        // Toteuta mökin poistaminen
    }

    private void muokkaaMokki() {
        // Toteuta mökin muokkaaminen
    }

    private void haeMokki() {
        // Toteuta mökin hakeminen
    }
}
