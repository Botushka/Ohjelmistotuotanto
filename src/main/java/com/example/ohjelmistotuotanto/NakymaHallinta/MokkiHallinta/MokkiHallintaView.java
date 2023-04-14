package com.example.ohjelmistotuotanto.NakymaHallinta.MokkiHallinta;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

import java.io.IOException;



public class MokkiHallintaView extends BorderPane {
    public MokkiHallintaView() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/ohjelmistotuotanto/mokki.fxml"));
        loader.setController(new MokkiHallintaController());

        try {
            setCenter(loader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}