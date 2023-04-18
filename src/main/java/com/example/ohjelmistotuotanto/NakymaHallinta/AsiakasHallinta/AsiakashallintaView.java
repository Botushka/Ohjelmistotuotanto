package com.example.ohjelmistotuotanto.NakymaHallinta.AsiakasHallinta;

import javafx.scene.layout.VBox;
import javafx.scene.control.Button;

public class AsiakashallintaView extends VBox {
    public AsiakashallintaView() {
        Button lisaaAsiakas = new Button("Lisää asiakas");
        lisaaAsiakas.setOnAction(e -> lisaaAsiakas());

        Button poistaAsiakas = new Button("Poista asiakas");
        poistaAsiakas.setOnAction(e -> poistaAsiakas());

        Button muokkaaAsiakas = new Button("Muokkaa asiakas");
        muokkaaAsiakas.setOnAction(e -> muokkaaAsiakas());

        Button haeAsiakas = new Button("Hae asiakas");
        haeAsiakas.setOnAction(e -> haeAsiakas());

        getChildren().addAll(lisaaAsiakas, poistaAsiakas, muokkaaAsiakas, haeAsiakas);
    }

    private void lisaaAsiakas() {
        // Toteuta asiakkaan lisääminen
    }

    private void poistaAsiakas() {
        // Toteuta asiakkaan poistaminen
    }

    private void muokkaaAsiakas() {
        // Toteuta asiakkaan muokkaaminen
    }

    private void haeAsiakas() {
        // Toteuta asiakkaan hakeminen
    }
}