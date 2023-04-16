package com.example.ohjelmistotuotanto.NakymaHallinta.MokkiHallinta;

import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MokkiHallintaController extends BorderPane {

    @FXML
    public TableView<Mokki> mokkiTable;

    @FXML
    public TableColumn<Mokki, Integer> mokkiIdColumn;

    @FXML
    public TableColumn<Mokki, Integer> alueIdColumn;

    @FXML
    public TableColumn<Mokki, String> postinroColumn;

    @FXML
    public TableColumn<Mokki, String> nimiColumn;

    @FXML
    public TableColumn<Mokki, String> katuosoiteColumn;

    @FXML
    public TableColumn<Mokki, Double> hintaColumn;

    @FXML
    public TableColumn<Mokki, Integer> henkilomaaraColumn;

    @FXML
    public TableColumn<Mokki, String> varusteluColumn;

    @FXML
    public TableColumn<Mokki, String> kuvausColumn;

    @FXML
    public TextField hakukentta;

    private void naytaMokit(List<Mokki> mokit) {
        if (mokkiTable != null) {
            mokkiTable.getItems().addAll(mokit);
        }
    }

    @FXML
    public void initialize() {
        // Alusta taulukon sarakkeet
        mokkiIdColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(Integer.parseInt(cellData.getValue().getMokkiId())).asObject());
        alueIdColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getAlueId()).asObject());
        postinroColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getPostinro()).asObject().asString());
        nimiColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNimi()));
        katuosoiteColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getKatuosoite()));
        hintaColumn.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getHinta()).asObject());
        henkilomaaraColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getHenkilomaara()).asObject());
        varusteluColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getVarustelu()));
        kuvausColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getKuvaus()));

        List<Mokki> mokit = haeMokitTietokannasta();
        naytaMokit(mokit);
        // Hae mokit tietokannasta ja lisää ne taulukkoon
    }

    private List<Mokki> haeMokitTietokannasta() {
        // Tämä on vain esimerkki, voit korvata tämän oikealla tietokannan käyttöä hoitavalla koodilla
        List<Mokki> mokit = new ArrayList<>();
        mokit.add(new Mokki("1", 1, 12345, "Mökki1", "Katu1", 100.0, 2, "Hyvä", "Luksusmökki järven rannalla"));
        mokit.add(new Mokki("2", 2, 54321, "Mökki2", "Katu2", 200.0, 3, "Hyvä", "Kaunis mökki metsän keskellä"));
        return mokit;
    }


    @FXML
    private void lisaaUusi() {
        // Tässä voit avata uuden ikkunan tai vaihtoehtoisesti vaihtaa näkymää käyttäen Scene:n root-elementtiä.
    }

    public MokkiHallintaController() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/ohjelmistotuotanto/mokki.fxml"));
        loader.setController(this);

        try {
            setCenter(loader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
