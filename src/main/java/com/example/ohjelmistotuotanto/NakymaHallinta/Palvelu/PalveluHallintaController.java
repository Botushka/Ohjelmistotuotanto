package com.example.ohjelmistotuotanto.NakymaHallinta.Palvelu;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

public class PalveluHallintaController extends BorderPane {

    @FXML
    private TextField alueid;

    @FXML
    private TextField haepalvelu;

    @FXML
    private Button lisaapalvelubtn;

    @FXML
    private TextField palvelualv;

    @FXML
    private TextField palveluhinta;

    @FXML
    private TextField palveluid;

    @FXML
    private TextField palvelukuvaus;

    @FXML
    private TextField palvelunimi;

    @FXML
    private TableView<?> palvelutable;

    @FXML
    private TextField palvelutyyppi;

    @FXML
    private Button poistapalvelubtn;

    @FXML
    private Button tallennapalvelubtn;

    @FXML
    void lisaapalvelu(ActionEvent event) {

    }

    @FXML
    void poistapalvelu(ActionEvent event) {

    }

    @FXML
    void tallennapalvelu(ActionEvent event) {

    }
    public PalveluHallintaController() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/ohjelmistotuotanto/palvelu.fxml"));
        loader.setController(this);
        try {
            setCenter(loader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
