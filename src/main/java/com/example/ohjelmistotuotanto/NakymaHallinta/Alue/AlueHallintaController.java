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
import java.util.Comparator;
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

        ObservableList<AlueOlio> alueetData = FXCollections.observableArrayList(haeAlueetTietokannasta());
        alueTable.setItems(alueetData);

        // Hae mokit tietokannasta ja lisää ne taulukkoon

        aluehaeKentta.textProperty().addListener((observable, oldValue, newValue) -> {
            String hakusana = newValue.toLowerCase();
            if (hakusana.trim().isEmpty()) {
                alueTable.setItems(alueetData);
            } else {
                List<AlueOlio> hakutulokset = new ArrayList<>();
                for (AlueOlio alueolio : alueetData) {
                    if (String.valueOf(alueolio.getAlue_id()).toLowerCase().contains(hakusana) ||
                            alueolio.getArea_nimi().toLowerCase().contains(hakusana))
                    {
                        hakutulokset.add(alueolio);
                    }
                }
                alueTable.setItems(FXCollections.observableArrayList(hakutulokset));
            }
        });
    }

    private List<AlueOlio> haeAlueetTietokannasta() {
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
        // Generate unique idalue
        int newId = 1;
        ObservableList<AlueOlio> alueetData = alueTable.getItems();
        if (!alueetData.isEmpty()) {
            // Sort the list by idalue in descending order
            alueetData.sort(Comparator.comparingInt(AlueOlio::getAlue_id).reversed());
            // Get the highest idalue in the list and add 1 to generate a new idalue
            newId = alueetData.get(0).getAlue_id() + 1;
        }

        // Create a new AlueOlio object with the generated idalue and the entered aluenimi
        AlueOlio newAlue = new AlueOlio(newId, aluenimiKentta.getText());

        // Add the new AlueOlio to the table
        alueetData.add(newAlue);
        aluenimiKentta.clear();
        alueidKentta.clear();

        // Refresh the table view
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
