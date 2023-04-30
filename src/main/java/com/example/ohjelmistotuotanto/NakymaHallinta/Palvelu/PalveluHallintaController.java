package com.example.ohjelmistotuotanto.NakymaHallinta.Palvelu;

import com.example.ohjelmistotuotanto.DatabaseManager;
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
import java.sql.*;
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

    private String url = "jdbc:mysql://localhost:3306/vn";
    private String user = "root";
    private String password = "";

    private Connection connection = null;
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

            try {
                Connection conn = DriverManager.getConnection(url, user, password);
                String deleteQuery = "DELETE FROM palvelu WHERE palvelu_id = ?";
                PreparedStatement preparedStatement = conn.prepareStatement(deleteQuery);
                preparedStatement.setInt(1, valittuPalvelu.getPalvelu_id());
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

    @FXML
    void tallennapalvelu(ActionEvent event) {

    }

    private void muokkaaPalvelu() throws SQLException {
        Palvelu palvelu = palvelutable.getSelectionModel().getSelectedItem();
        DatabaseManager dbManager = new DatabaseManager(url, user,password);
        dbManager.updatePalvelu(palvelu);
    }
    private void updatePalvelu(Palvelu palvelu) {
        try {
            Connection conn = DriverManager.getConnection(url, user, password);
            String insertQuery = "INSERT INTO palvelu (palvelu_id, alue_id, nimi, tyyppi, kuvaus, hinta, alv) VALUES ( ?, ?,?,?,?,?,?)";
            PreparedStatement preparedStatement = conn.prepareStatement(insertQuery);
            preparedStatement.setInt(1, palvelu.getPalvelu_id());
            preparedStatement.setInt(2, palvelu.getAlue_id());
            preparedStatement.setString(3, palvelu.getNimi());
            preparedStatement.setInt(4, palvelu.getTyyppi());
            preparedStatement.setString(5, palvelu.getKuvaus());
            preparedStatement.setDouble(6, palvelu.getHinta());
            preparedStatement.setDouble(7, palvelu.getAlv());
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Jokin meni pieleen tiedon tallentamisessa tietokantaan");
            }
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void lisaatieto(ActionEvent event) {
        Palvelu newPalvelu = new Palvelu (Integer.parseInt(palveluid.getText()), Integer.parseInt(alueid.getText()), (palvelunimi.getText()),
                Integer.parseInt(palvelutyyppi.getText()), palvelukuvaus.getText(), Double.parseDouble(palveluhinta.getText()),
                Integer.parseInt(palvelualv.getText()));

        ObservableList<Palvelu> palvelutData = palvelutable.getItems();
        palvelutData.add(newPalvelu);
        updatePalvelu(newPalvelu);

        palveluid.clear();
        alueid.clear();
        palvelunimi.clear();
        palvelutyyppi.clear();
        palvelukuvaus.clear();
        palveluhinta.clear();
        palvelualv.clear();

        naytaPalvelut(palvelutData);
    }

    public void initialize() throws SQLException {
        palveluIdColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getPalvelu_id()).asObject());
        palveluIdColumn.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        palveluIdColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Palvelu, Integer>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Palvelu, Integer> event) {
                Palvelu palvelu = event.getRowValue();
                palvelu.setPalvelu_id(event.getNewValue());
                try {
                    muokkaaPalvelu();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        alueIdColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getAlue_id()).asObject());
        alueIdColumn.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        alueIdColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Palvelu, Integer>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Palvelu, Integer> event) {
                Palvelu alue = event.getRowValue();
                alue.setAlue_id(event.getNewValue());
                try {
                    muokkaaPalvelu();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        nimiColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNimi()));
        nimiColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        nimiColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Palvelu, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Palvelu, String> event) {
                Palvelu nimi = event.getRowValue();
                nimi.setNimi(event.getNewValue());
                try {
                    muokkaaPalvelu();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        tyyppiColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty((int)cellData.getValue().getTyyppi()).asObject());
        tyyppiColumn.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        tyyppiColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Palvelu, Integer>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Palvelu, Integer> event) {
                Palvelu tyyppi = event.getRowValue();
                tyyppi.setTyyppi(event.getNewValue());
                try {
                    muokkaaPalvelu();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        kuvausColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getKuvaus()));
        kuvausColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        kuvausColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Palvelu, String>>() {

            @Override
            public void handle(TableColumn.CellEditEvent<Palvelu, String> event) {
                Palvelu kuvaus = event.getRowValue();
                kuvaus.setKuvaus(event.getNewValue());
                try {
                    muokkaaPalvelu();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        hintaColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty((int)cellData.getValue().getHinta()).asObject());
        hintaColumn.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        hintaColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Palvelu, Integer>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Palvelu, Integer> event) {
                Palvelu hinta = event.getRowValue();
                hinta.setHinta(event.getNewValue());
                try {
                    muokkaaPalvelu();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        alvColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty((int)cellData.getValue().getAlv()).asObject());
        alvColumn.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        alvColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Palvelu, Integer>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Palvelu, Integer> event) {
                Palvelu alv = event.getRowValue();
                alv.setAlv(event.getNewValue());
                try {
                    muokkaaPalvelu();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
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

    private List<Palvelu> haePalvelutTietokannasta() throws SQLException {
        List<Palvelu> palvelut = new ArrayList<>();
        DatabaseManager dbmanager = new DatabaseManager(url, user, password);
        ResultSet rs = dbmanager.retrieveData("palvelu", new String[]{"palvelu_id", "alue_id", "nimi", "tyyppi", "kuvaus", "hinta", "alv"});

        while (rs.next()) {
            int palveluid = rs.getInt("palvelu_id");
            int alueId = rs.getInt("alue_id");
            String nimi = rs.getString("nimi");
            int tyyppi = rs.getInt("tyyppi");
            String kuvaus = rs.getString("kuvaus");
            double hinta = rs.getDouble("hinta");
            double alv = rs.getInt("alv");
            palvelut.add(new Palvelu(palveluid, alueId, nimi, tyyppi,kuvaus, hinta, alv));

            palvelut.add(new Palvelu(1, 2, "k", 3, "u", 5, 6));
        }
        return palvelut;
    }

    @FXML
    private void lisaapalvelu(ActionEvent event) {
        ObservableList<Palvelu> palveluData = palvelutable.getItems();
        Palvelu uusiPalvelu = new Palvelu(Integer.parseInt(palveluid.getText()),Integer.parseInt(alueid.getText()),palvelunimi.getText(),
                Integer.parseInt(palvelutyyppi.getText()),palvelukuvaus.getText(), Integer.parseInt(palveluhinta.getText()),Integer.parseInt(palvelualv.getText()));

        palveluData.add(uusiPalvelu);
        updatePalvelu(uusiPalvelu);

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
