package com.example.ohjelmistotuotanto.NakymaHallinta.Palvelu;

import com.example.ohjelmistotuotanto.NakymaHallinta.Alue.AlueOlio;
import com.example.ohjelmistotuotanto.NakymaHallinta.AsiakasHallinta.Asiakas;
import com.example.ohjelmistotuotanto.NakymaHallinta.MokkiHallinta.Mokki;
import com.example.ohjelmistotuotanto.Olioluokat.Palvelu;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.BorderPane;
import javafx.util.converter.IntegerStringConverter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PalveluHallintaController extends BorderPane {

    @FXML
    private TableColumn<Palvelu, Integer> alueIdColumn;

    @FXML
    private TableColumn<Palvelu, Integer> alvColumn;

    @FXML
    private TableColumn<Palvelu, Integer> hintaColumn;

    @FXML
    private TableColumn<Palvelu, String> kuvausColumn;

    @FXML
    private TableColumn<Palvelu, String> nimiColumn;

    @FXML
    private TableColumn<Palvelu, Integer> tyyppiColumn;

    @FXML
    private TextField alueid;

    @FXML
    private TextField haepalvelu;

    @FXML
    private Button lisaapalvelubtn;

    @FXML
    private TableColumn<Palvelu, Integer> palveluIdColumn;

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
    private TableView<Palvelu> palvelutable;

    @FXML
    private TextField palvelutyyppi;

    @FXML
    private Button poistapalvelubtn;

    @FXML
    private Button tallennapalvelubtn;

    private List<Palvelu> palvelut = new ArrayList<>();
    private void naytaPalvelut(List<Palvelu> palvelut) {
        ObservableList<Palvelu> palveluData = FXCollections.observableArrayList(palvelut);
        palvelutable.setItems(palveluData);
    }


    @FXML
    public void poistapalvelu(ActionEvent event) {
        Palvelu valittuPalvelu = palvelutable.getSelectionModel().getSelectedItem();
        if (valittuPalvelu != null) {
            TableView.TableViewSelectionModel<Palvelu> selectionModel = palvelutable.getSelectionModel();
            ObservableList<Palvelu> tableItems = palvelutable.getItems();
            int selectedIndex = selectionModel.getSelectedIndex();
            tableItems.remove(selectedIndex);
            palvelutable.setItems(tableItems);
            palvelutable.refresh();
        }
    }

    @FXML
    void tallennapalvelu(ActionEvent event) {

    }

    @FXML
    public void lisaatieto(ActionEvent event) {
        Palvelu newPalvelu = new Palvelu (Integer.parseInt(palveluid.getText()), Integer.parseInt(alueid.getText()), (palvelunimi.getText()),
                Integer.parseInt(palvelutyyppi.getText()), palvelukuvaus.getText(), Double.parseDouble(palveluhinta.getText()),
                Integer.parseInt(palvelualv.getText()));

        ObservableList<Palvelu> palvelutData = palvelutable.getItems();
        palvelutData.add(newPalvelu);

        // Clear the input fields
        palveluid.clear();
        alueid.clear();
        palvelunimi.clear();
        palvelutyyppi.clear();
        palvelukuvaus.clear();
        palveluhinta.clear();
        palvelualv.clear();

        naytaPalvelut(palvelutData);
    }

    public void initialize() {
        palveluIdColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getPalvelu_id()).asObject());
        palveluIdColumn.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        palveluIdColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Palvelu, Integer>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Palvelu, Integer> event) {
                Palvelu palvelu = event.getRowValue();
                palvelu.setPalvelu_id(event.getNewValue());
            }
        });

        alueIdColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getAlue_id()).asObject());
        alueIdColumn.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        alueIdColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Palvelu, Integer>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Palvelu, Integer> event) {
                Palvelu alue = event.getRowValue();
                alue.setAlue_id(event.getNewValue());
            }
        });

        nimiColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNimi()));
        nimiColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        nimiColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Palvelu, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Palvelu, String> event) {
                Palvelu nimi = event.getRowValue();
                nimi.setNimi(event.getNewValue());
            }
        });

        tyyppiColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty((int)cellData.getValue().getTyyppi()).asObject());
        tyyppiColumn.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        tyyppiColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Palvelu, Integer>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Palvelu, Integer> event) {
                Palvelu tyyppi = event.getRowValue();
                tyyppi.setTyyppi(event.getNewValue());
            }
        });

        kuvausColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getKuvaus()));
        kuvausColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        kuvausColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Palvelu, String>>() {

            @Override
            public void handle(TableColumn.CellEditEvent<Palvelu, String> event) {
                Palvelu kuvaus = event.getRowValue();
                kuvaus.setKuvaus(event.getNewValue());
            }
        });

        hintaColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty((int)cellData.getValue().getHinta()).asObject());
        hintaColumn.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        hintaColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Palvelu, Integer>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Palvelu, Integer> event) {
                Palvelu hinta = event.getRowValue();
                hinta.setHinta(event.getNewValue());
            }
        });

        alvColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty((int)cellData.getValue().getAlv()).asObject());
        alvColumn.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        alvColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Palvelu, Integer>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Palvelu, Integer> event) {
                Palvelu alv = event.getRowValue();
                alv.setAlv(event.getNewValue());
            }
        });



        ObservableList<Palvelu> palveluData = FXCollections.observableArrayList(haePalvelutTietokannasta());
        palvelutable.setItems(palveluData);
        palvelutable.setEditable(true);

        naytaPalvelut(palveluData);

        haepalvelu.textProperty().addListener((observable, oldValue, newValue) -> {
            String hakusana = newValue.toLowerCase();
            if (hakusana.trim().isEmpty()) {
                palvelutable.setItems(palveluData);
            } else {
                List<Palvelu> hakutulokset = new ArrayList<>();
                for (Palvelu palvelu : palveluData) {
                    if (String.valueOf(palvelu.getPalvelu_id()).toLowerCase().contains(hakusana) ||
                            String.valueOf(palvelu.getAlue_id()).toLowerCase().contains(hakusana) ||
                            String.valueOf(palvelu.getNimi()).toLowerCase().contains(hakusana) ||
                            String.valueOf(palvelu.getTyyppi()).toLowerCase().contains(hakusana) ||
                            palvelu.getKuvaus().toLowerCase().contains(hakusana) ||
                            String.valueOf(palvelu.getHinta()).toLowerCase().contains(hakusana) ||
                            String.valueOf(palvelu.getAlv()).toLowerCase().contains(hakusana))
                    {
                        hakutulokset.add(palvelu);
                    }
                }
                palvelutable.setItems(FXCollections.observableArrayList(hakutulokset));
            }
        });
    }

    private List<Palvelu> haePalvelutTietokannasta()
    {
        List<Palvelu> palvelut = new ArrayList<>();
        palvelut.add(new Palvelu( 1, 2, "k", 3, "u",5,6));
        return palvelut;
    }

    @FXML
    private void lisaapalvelu(ActionEvent event) {
        ObservableList<Palvelu> palveluData = palvelutable.getItems();
        Palvelu uusiPalvelu = new Palvelu(Integer.parseInt(palveluid.getText()),Integer.parseInt(alueid.getText()),palvelunimi.getText(),
                Integer.parseInt(palvelutyyppi.getText()),palvelukuvaus.getText(), Integer.parseInt(palveluhinta.getText()),Integer.parseInt(palvelualv.getText()));

        palveluData.add(uusiPalvelu);

        // Clear the input fields
        palveluid.clear();
        alueid.clear();
        palvelunimi.clear();
        palvelutyyppi.clear();
        palvelukuvaus.clear();
        palveluhinta.clear();
        palvelualv.clear();

        naytaPalvelut(palveluData);

    }


    public PalveluHallintaController()
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/ohjelmistotuotanto/palvelu.fxml"));
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
