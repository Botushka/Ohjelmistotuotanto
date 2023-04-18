package com.example.ohjelmistotuotanto.NakymaHallinta.Alue;

import com.example.ohjelmistotuotanto.NakymaHallinta.MokkiHallinta.Mokki;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class AlueHallintaController extends BorderPane {
    @FXML
    public TableView<AlueOlio> alueTable;
    @FXML
    public TableColumn<AlueOlio, String> nimiAlueColumn;

    @FXML
    public TableColumn<AlueOlio, Integer> idalueColumn;
    @FXML
    public Button addnappi;
    @FXML
    public Button delnappi;
    @FXML
    public Button editNappi;

    private List<AlueOlio> alueOlio = new ArrayList<>();
    public TextField aluehaeKentta;
    public TextField aluenimiKentta;
    public TextField alueidKentta;


    private void naytaAlue(List<AlueOlio> alueet) {
        ObservableList<AlueOlio> alueetData = FXCollections.observableArrayList(alueet);
        alueTable.setItems(alueetData);
    }
    @FXML
    public void initialize() {
        // Alusta taulukon sarakkeet
        idalueColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getAlue_id()).asObject());
        nimiAlueColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getArea_nimi()));

        List<AlueOlio> alueOlio = haeMokitTietokannasta();
        naytaAlue(alueOlio);
        // Hae mokit tietokannasta ja lisää ne taulukkoon

        aluehaeKentta.textProperty().addListener((observable, oldValue, newValue) -> {
            String hakusana = newValue.toLowerCase();
            if (hakusana.trim().isEmpty()) {
                naytaAlue(haeMokitTietokannasta());
            } else {
                List<AlueOlio> hakutulokset = new ArrayList<>();
                for (AlueOlio alueolio : haeMokitTietokannasta()) {
                    if (String.valueOf(alueolio.getAlue_id()).toLowerCase().contains(hakusana) ||
                            alueolio.getArea_nimi().toLowerCase().contains(hakusana))
                    {
                        hakutulokset.add(alueolio);
                    }
                }
                naytaAlue(hakutulokset);
            }
        });

    }

    private List<AlueOlio> haeMokitTietokannasta() {
        // Tämä on vain esimerkki, voit korvata tämän oikealla tietokannan käyttöä hoitavalla koodilla
        List<AlueOlio> alueet = new ArrayList<>();
        alueet.add(new AlueOlio(1, "Kuopio"));
        alueet.add(new AlueOlio(2, "Vantaa"));
        return alueet;

    }

    public AlueHallintaController() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/ohjelmistotuotanto/alue.fxml"));
        loader.setController(this);
        try {
            setCenter(loader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void addAlue(ActionEvent event) {
        AlueOlio newAlue = new AlueOlio(Integer.parseInt(alueidKentta.getText()), aluenimiKentta.getText());

        ObservableList<AlueOlio> alueetData = alueTable.getItems();
        alueetData.add(newAlue);
        aluenimiKentta.clear();
        alueidKentta.clear();

        naytaAlue(alueetData);
    }
    @FXML
    private void delAlue(ActionEvent event) {
        AlueOlio selectedMokki = alueTable.getSelectionModel().getSelectedItem();
        if (selectedMokki != null) {
            TableView.TableViewSelectionModel<AlueOlio> selectionModel = alueTable.getSelectionModel();
            ObservableList<AlueOlio> tableItems = alueTable.getItems();
            int selectedIndex = selectionModel.getSelectedIndex();
            tableItems.remove(selectedIndex);
            alueTable.setItems(tableItems);
            alueTable.refresh();
        }
    }
    @FXML
    private void editAlue() {
        // Toteuta alueen muokkaaminen
    }
    @FXML
    private void haeAlue() {
        // Toteuta alueen hakeminen
    }
}
