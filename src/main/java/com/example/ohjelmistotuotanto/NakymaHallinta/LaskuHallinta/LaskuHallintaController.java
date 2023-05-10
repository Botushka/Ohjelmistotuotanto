package com.example.ohjelmistotuotanto.NakymaHallinta.LaskuHallinta;

import com.example.ohjelmistotuotanto.DatabaseManager;
import com.example.ohjelmistotuotanto.NakymaHallinta.AsiakasHallinta.Asiakas;
import com.example.ohjelmistotuotanto.Olioluokat.Palvelu;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.BorderPane;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.IntegerStringConverter;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LaskuHallintaController extends BorderPane {
    @FXML
    public TableColumn<Lasku, Integer> laskuidcolumn;
    @FXML
    public TableColumn<Lasku, Integer> laskuvarausidcolumn;
    @FXML
    public TableColumn<Lasku, Double> laskusummacolumn;
    @FXML
    public TableColumn<Lasku, Double> laskualvcolumn;
    @FXML
    public TableView<Lasku> laskutableview;
    @FXML
    public Button muokkaalaskunappula;
    @FXML
    public Button poistalaskunappula;
    @FXML
    public TextField haelasku;
    @FXML
    public TextField laskukenttaid;
    @FXML
    public TextField varauskenttaid;
    @FXML
    public TextField summakentta;
    @FXML
    public TextField alvkentta;
    @FXML
    public Button lisaalaskunappula;
    private String url = "jdbc:mysql://localhost:3306/vn";
    private String user = "root";
    private String password = "";

    private List<Lasku> laskut = new ArrayList<>();
    private void naytaLaskut(List<Lasku> laskut) {
        ObservableList<Lasku> laskuData = FXCollections.observableArrayList(laskut);
        laskutableview.setItems(laskuData);
    }

    public void initialize() throws SQLException {
        laskuidcolumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getLaskuId()).asObject());
        laskuidcolumn.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        laskuidcolumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Lasku, Integer>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Lasku, Integer> event) {
                Lasku lasku = event.getRowValue();
                lasku.setLaskuId(event.getNewValue());
                try {
                    muokkaaLasku();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        laskuvarausidcolumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getVarausId()).asObject());
        laskuvarausidcolumn.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        laskuvarausidcolumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Lasku, Integer>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Lasku, Integer> event) {
                Lasku lasku = event.getRowValue();
                lasku.setVarausId(event.getNewValue());
                try {
                    muokkaaLasku();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        laskusummacolumn.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getSumma()).asObject());
        laskusummacolumn.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        laskusummacolumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Lasku, Double>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Lasku, Double> event) {
                Lasku hinta = event.getRowValue();
                hinta.setSumma(event.getNewValue());
                try {
                    muokkaaLasku();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        laskualvcolumn.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getAlv()).asObject());
        laskualvcolumn.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        laskualvcolumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Lasku, Double>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Lasku, Double> event) {
                Lasku alv = event.getRowValue();
                alv.setAlv(event.getNewValue());
                try {
                    muokkaaLasku();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        });


        ObservableList<Lasku> laskuData = FXCollections.observableArrayList(haePalvelutTietokannasta());
        laskutableview.setItems(laskuData);
        laskutableview.setEditable(true);

        naytaLaskut(laskuData);

        haelasku.textProperty().addListener((observable, oldValue, newValue) -> {
            String hakusana = newValue.toLowerCase();
            if (hakusana.trim().isEmpty()) {
                laskutableview.setItems(laskuData);
            } else {
                List<Lasku> hakutulokset = new ArrayList<>();
                for (Lasku palvelu : laskuData) {
                    if (String.valueOf(palvelu.getLaskuId()).toLowerCase().contains(hakusana) ||
                            String.valueOf(palvelu.getVarausId()).toLowerCase().contains(hakusana) ||
                            String.valueOf(palvelu.getSumma()).toLowerCase().contains(hakusana) ||
                            String.valueOf(palvelu.getAlv()).toLowerCase().contains(hakusana))
                    {
                        hakutulokset.add(palvelu);
                    }
                }
                laskutableview.setItems(FXCollections.observableArrayList(hakutulokset));
            }
        });
    }

    @FXML
    public void muokkaalasku(){}
    @FXML
    public void lisaalasku(){
        ObservableList<Lasku> palveluData = laskutableview.getItems();
        Lasku uusiPalvelu = new Lasku(Integer.parseInt(laskukenttaid.getText()),Integer.parseInt(varauskenttaid.getText()),Double.parseDouble(alvkentta.getText()),
                Integer.parseInt(summakentta.getText()));

        palveluData.add(uusiPalvelu);
        updateLasku(uusiPalvelu);

        laskukenttaid.clear();
        varauskenttaid.clear();
        summakentta.clear();
        alvkentta.clear();

        naytaLaskut(palveluData);
    }

    private void updateLasku(Lasku lasku) {
        try {
            Connection conn = DriverManager.getConnection(url, user, password);
            String insertQuery = "INSERT INTO lasku (lasku_id, varaus_id, summa, alv) VALUES ( ?, ?,?,?)";
            PreparedStatement preparedStatement = conn.prepareStatement(insertQuery);
            preparedStatement.setInt(1, lasku.getLaskuId());
            preparedStatement.setInt(2, lasku.getVarausId());
            preparedStatement.setDouble(3, lasku.getSumma());
            preparedStatement.setDouble(4, lasku.getAlv());
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Jokin meni pieleen tiedon tallentamisessa tietokantaan");
            }
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private List<Lasku> haePalvelutTietokannasta() throws SQLException {
        List<Lasku> palvelut = new ArrayList<>();
        DatabaseManager dbmanager = new DatabaseManager(url, user, password);
        ResultSet rs = dbmanager.retrieveData("lasku", new String[]{"lasku_id", "varaus_id", "summa", "alv"});

        while (rs.next()) {
            int laskuid = rs.getInt("lasku_id");
            int varausid = rs.getInt("varaus_id");
            double hinta = rs.getDouble("summa");
            double alv = rs.getInt("alv");
            palvelut.add(new Lasku(laskuid, varausid, hinta, alv));

            palvelut.add(new Lasku(1, 2,  3, 20));
        }
        return palvelut;
    }

    private void muokkaaLasku() throws SQLException {
        Lasku lasku = laskutableview.getSelectionModel().getSelectedItem();
        DatabaseManager dbManager = new DatabaseManager(url, user,password);
        dbManager.updateLasku(lasku);
    }

    @FXML
    public void poistalasku(ActionEvent event) {
        Lasku valittuPalvelu = laskutableview.getSelectionModel().getSelectedItem();
        if (valittuPalvelu != null) {
            TableView.TableViewSelectionModel<Lasku> selectionModel = laskutableview.getSelectionModel();
            ObservableList<Lasku> tableItems = laskutableview.getItems();
            int selectedIndex = selectionModel.getSelectedIndex();
            tableItems.remove(selectedIndex);
            laskutableview.setItems(tableItems);
            laskutableview.refresh();

            try {
                Connection conn = DriverManager.getConnection(url, user, password);
                String deleteQuery = "DELETE FROM lasku WHERE lasku_id = ?";
                PreparedStatement preparedStatement = conn.prepareStatement(deleteQuery);
                preparedStatement.setInt(1, valittuPalvelu.getLaskuId());
                int affectedRows = preparedStatement.executeUpdate();
                if (affectedRows == 0) {
                    throw new SQLException("Jokin meni pieleen tiedon poistamisessa tietokannasta");
                }
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    public LaskuHallintaController()
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/ohjelmistotuotanto/lasku.fxml"));
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
