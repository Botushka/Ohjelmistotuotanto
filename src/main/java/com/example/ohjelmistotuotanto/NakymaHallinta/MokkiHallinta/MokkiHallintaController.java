package com.example.ohjelmistotuotanto.NakymaHallinta.MokkiHallinta;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class MokkiHallintaController {

    @FXML
    private TableView<Mokki> mokkiTable;

    @FXML
    private TableColumn<Mokki, Integer> mokkiIdColumn;

    @FXML
    private TableColumn<Mokki, Integer> alueIdColumn;

    @FXML
    private TableColumn<Mokki, String> postinroColumn;

    @FXML
    private TableColumn<Mokki, String> nimiColumn;

    @FXML
    private TableColumn<Mokki, String> katuosoiteColumn;

    @FXML
    private TableColumn<Mokki, Double> hintaColumn;

    @FXML
    private TableColumn<Mokki, Integer> henkilomaaraColumn;

    @FXML
    private TableColumn<Mokki, String> varusteluColumn;

    @FXML
    private TableColumn<Mokki, String> kuvausColumn;

    @FXML
    private TextField hakukentta;

    public void initialize() {
        // Tässä voit alustaa taulukon sarakkeet ja lisätä tarvittaessa kuuntelijoita
        // esim. kun käyttäjä klikkaa taulukon riviä.
    }

    // Tässä voit määritellä kaikki tarvittavat tapahtumankäsittelijät, esim. nappuloiden painallukset.
    // Voit myös käyttää annotaatioita (@FXML) tarvittavien graafisten elementtien injektoimiseksi.
    // Esimerkiksi seuraava metodi käsittelee "Lisää uusi" -nappulan painalluksen:

    @FXML
    private void lisaaUusi() {
        // Tässä voit avata uuden ikkunan tai vaihtoehtoisesti vaihtaa näkymää käyttäen Scene:n root-elementtiä.
    }
}
