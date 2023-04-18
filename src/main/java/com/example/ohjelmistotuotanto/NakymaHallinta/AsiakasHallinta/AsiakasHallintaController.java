package com.example.ohjelmistotuotanto.NakymaHallinta.AsiakasHallinta;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

public class AsiakasHallintaController extends BorderPane
{

    @FXML
    public TableColumn<Asiakas, String> asiakasColumn;

    @FXML
    public TableView<Asiakas> asiakasTable;

    @FXML
    public TextField asiakasidkentta;

    @FXML
    public TableColumn<Asiakas, String> emailColumn;

    @FXML
    public TextField emailkentta;

    @FXML
    public TableColumn<Asiakas, String> etunimiColumn;

    @FXML
    public TextField etunimikentta;

    @FXML
    public TextField haeasiakas;

    @FXML
    public TableColumn<Asiakas, String> lahiosoiteColumn;

    @FXML
    public TextField lahiosoitekentta;

    @FXML
    public Button lisaaasiakasnappula;

    @FXML
    public Button muokkaaasiakasnappula;

    @FXML
    public Button poistaasiakasnappula;

    @FXML
    public TableColumn<Asiakas, Integer> postinroColumn;

    @FXML
    public TextField postinrokentta;

    @FXML
    public TextField puhnrokentta;

    @FXML
    public TableColumn<Asiakas, Integer> puhnumColumn;

    @FXML
    public TableColumn<Asiakas, String> sukunimiColumn;

    @FXML
    public TextField sukunimikentta;

    @FXML
    void lisaaasiakas(ActionEvent event) {

    }

    @FXML
    void muokkaaasiakas(ActionEvent event) {

    }

    @FXML
    void poistaasiakas(ActionEvent event) {

    }

    public AsiakasHallintaController()
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/ohjelmistotuotanto/asiakas.fxml"));
        loader.setController(this);
        try
        {
            setCenter(loader.load());
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

}

