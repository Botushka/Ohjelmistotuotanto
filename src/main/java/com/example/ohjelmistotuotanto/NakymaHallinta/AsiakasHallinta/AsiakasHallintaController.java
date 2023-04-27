package com.example.ohjelmistotuotanto.NakymaHallinta.AsiakasHallinta;

import com.example.ohjelmistotuotanto.DatabaseManager;
import com.example.ohjelmistotuotanto.NakymaHallinta.MokkiHallinta.Mokki;
import javafx.beans.property.SimpleDoubleProperty;
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
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.IntegerStringConverter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AsiakasHallintaController extends BorderPane
{

    @FXML
    public TableView<Asiakas> asiakasTable;
    @FXML
    public TableColumn<Asiakas, Integer> asiakasIdColumn;
    @FXML
    public TableColumn<Asiakas, Integer> postinroColumn;
    @FXML
    public TableColumn<Asiakas, String> etunimiColumn;
    @FXML
    public TableColumn<Asiakas, String> sukunimiColumn;
    @FXML
    public TableColumn<Asiakas, String> emailColumn;
    @FXML
    public TableColumn<Asiakas, Integer> puhnumColumn;
    @FXML
    public TableColumn<Asiakas, String> lahiosoiteColumn;

    @FXML
    public TextField asiakasidkentta;
    @FXML
    public TextField postinrokentta;
    @FXML
    public TextField etunimikentta;
    @FXML
    public TextField sukunimikentta;
    @FXML
    public TextField emailkentta;
    @FXML
    public TextField puhnrokentta;
    @FXML
    public TextField lahiosoitekentta;
    @FXML
    public TextField haeasiakas;
    @FXML
    public Button lisaaasiakasnappula;
    @FXML
    public Button muokkaaasiakasnappula;
    @FXML
    public Button poistaasiakasnappula;
    private String url = "jdbc:mysql://localhost:3306/vn";
    private String user = "root";
    private String password = "";

    private DatabaseManager dbManager;

    private List<Asiakas> asiakkaat = new ArrayList<>();
    private void naytaAsiakkaat(List<Asiakas> asiakkaat) {
        ObservableList<Asiakas> asiakasData = FXCollections.observableArrayList(asiakkaat);
        asiakasTable.setItems(asiakasData);
    }

    public void initialize()
    {
        asiakasIdColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getAsiakasId()).asObject());
        asiakasIdColumn.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        asiakasIdColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Asiakas, Integer>>()
        {
            @Override
            public void handle(TableColumn.CellEditEvent<Asiakas, Integer> event)
            {
                Asiakas asiakas = event.getRowValue();
                asiakas.setAsiakasId(event.getNewValue());
            }
        });

        postinroColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getPostiNro()).asObject());
        postinroColumn.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        postinroColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Asiakas, Integer>>()
        {
            @Override
            public void handle(TableColumn.CellEditEvent<Asiakas, Integer> event)
            {
                Asiakas asiakas = event.getRowValue();
                asiakas.setPostiNro(event.getNewValue());
            }
        });

        etunimiColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEtunimi()));
        etunimiColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        etunimiColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Asiakas, String>>()
        {
            @Override
            public void handle(TableColumn.CellEditEvent<Asiakas, String> event)
            {
                Asiakas asiakas = event.getRowValue();
                asiakas.setEtunimi(event.getNewValue());
            }
        });
        sukunimiColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getSukunimi()));
        sukunimiColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        sukunimiColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Asiakas, String>>()
        {
            @Override
            public void handle(TableColumn.CellEditEvent<Asiakas, String> event)
            {
                Asiakas asiakas = event.getRowValue();
                asiakas.setSukunimi(event.getNewValue());
            }
        });
        emailColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEmail()));
        emailColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        emailColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Asiakas, String>>()
        {
            @Override
            public void handle(TableColumn.CellEditEvent<Asiakas, String> event)
            {
                Asiakas asiakas = event.getRowValue();
                asiakas.setEmail(event.getNewValue());
            }
        });
        puhnumColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getPuhnumero()).asObject());
        puhnumColumn.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        puhnumColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Asiakas, Integer>>()
        {
            @Override
            public void handle(TableColumn.CellEditEvent<Asiakas, Integer> event)
            {
                Asiakas asiakas = event.getRowValue();
                asiakas.setPuhnumero(event.getNewValue());
            }
        });
        lahiosoiteColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getLahiosoite()));
        lahiosoiteColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        lahiosoiteColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Asiakas, String>>()
        {
            @Override
            public void handle(TableColumn.CellEditEvent<Asiakas, String> event)
            {
                Asiakas asiakas = event.getRowValue();
                asiakas.setLahiosoite(event.getNewValue());
            }
        });
        ObservableList<Asiakas> asiakasData = FXCollections.observableArrayList(haeAsiakkaatTietokannasta());
        asiakasTable.setItems(asiakasData);
        asiakasTable.setEditable(true);

        naytaAsiakkaat(asiakasData);
        // Hae asiakkaat tietokannasta ja lisää ne taulukkoon

        haeasiakas.textProperty().addListener((observable, oldValue, newValue) -> {
            String hakusana = newValue.toLowerCase();
            if (hakusana.trim().isEmpty()) {
                asiakasTable.setItems(asiakasData);
            } else {
                List<Asiakas> hakutulokset = new ArrayList<>();
                for (Asiakas asiakas : asiakasData) {
                    if (asiakas.getEtunimi().toLowerCase().contains(hakusana) ||
                            asiakas.getSukunimi().toLowerCase().contains(hakusana) ||
                            String.valueOf(asiakas.getAsiakasId()).toLowerCase().contains(hakusana) ||
                            String.valueOf(asiakas.getPostiNro()).toLowerCase().contains(hakusana) ||
                            asiakas.getLahiosoite().toLowerCase().contains(hakusana) ||
                            String.valueOf(asiakas.getPuhnumero()).toLowerCase().contains(hakusana) ||
                            asiakas.getEmail().toLowerCase().contains(hakusana))
                    {
                        hakutulokset.add(asiakas);
                    }
                }
                asiakasTable.setItems(FXCollections.observableArrayList(hakutulokset));
            }
        });
    }

    private List<Asiakas> haeAsiakkaatTietokannasta()
    {
        List<Asiakas> asiakkaat = new ArrayList<>();
        DatabaseManager dbmanager = new DatabaseManager(url, user, password);
        //Lisää oikea tietokanta myähemmin
        //asiakkaat.add(new Asiakas(1,2,"Testi","Tieto","Testitie","testi@email.com",0000000000));
        return asiakkaat;
    }
    @FXML
    private void lisaaasiakas(ActionEvent event) {
        ObservableList<Asiakas> asiakasData = asiakasTable.getItems();
        Asiakas uusiAsiakas = new Asiakas(Integer.parseInt(asiakasidkentta.getText()),Integer.parseInt(postinrokentta.getText()),etunimikentta.getText(),
                sukunimikentta.getText(),lahiosoitekentta.getText(), emailkentta.getText(),Integer.parseInt(puhnrokentta.getText()));

        asiakasData.add(uusiAsiakas);

        // Clear the input fields
        asiakasidkentta.clear();
        postinrokentta.clear();
        etunimikentta.clear();
        sukunimikentta.clear();
        lahiosoitekentta.clear();
        emailkentta.clear();
        puhnrokentta.clear();

        naytaAsiakkaat(asiakasData);

    }

    public void poistaasiakas(ActionEvent event) {
        Asiakas valittuAsiakas = asiakasTable.getSelectionModel().getSelectedItem();
        if (valittuAsiakas != null) {
            TableView.TableViewSelectionModel<Asiakas> selectionModel = asiakasTable.getSelectionModel();
            ObservableList<Asiakas> tableItems = asiakasTable.getItems();
            int selectedIndex = selectionModel.getSelectedIndex();
            tableItems.remove(selectedIndex);
            asiakasTable.setItems(tableItems);
            asiakasTable.refresh();
        }
    }

    @FXML
    void muokkaaasiakas(ActionEvent event) {

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

