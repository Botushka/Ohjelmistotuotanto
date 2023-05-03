package com.example.ohjelmistotuotanto.NakymaHallinta.AsiakasHallinta;

import com.example.ohjelmistotuotanto.DatabaseManager;
import com.example.ohjelmistotuotanto.NakymaHallinta.Alue.AlueOlio;
import com.example.ohjelmistotuotanto.NakymaHallinta.MokkiHallinta.Mokki;
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

public class AsiakasHallintaController extends BorderPane
{

    private String url = "jdbc:mysql://localhost:3306/vn";
    private String user = "root";
    private String password = "";
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

    private List<Asiakas> asiakkaat = new ArrayList<>();
    private void naytaAsiakkaat(List<Asiakas> asiakkaat) {
        ObservableList<Asiakas> asiakasData = FXCollections.observableArrayList(asiakkaat);
        asiakasTable.setItems(asiakasData);
    }

    public void initialize() throws SQLException {
        asiakasIdColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getAsiakasId()).asObject());
        asiakasIdColumn.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        asiakasIdColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Asiakas, Integer>>()
        {
            @Override
            public void handle(TableColumn.CellEditEvent<Asiakas, Integer> event)
            {
                Asiakas asiakas = event.getRowValue();
                asiakas.setAsiakasId(event.getNewValue());
                try {
                    muokkaaAsiakas();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
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
                try {
                    muokkaaAsiakas();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
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
                try {
                    muokkaaAsiakas();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
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
                try {
                    muokkaaAsiakas();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
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
                try {
                    muokkaaAsiakas();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
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
                try {
                    muokkaaAsiakas();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
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
                try {
                    muokkaaAsiakas();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        ObservableList<Asiakas> asiakasData = FXCollections.observableArrayList(haeAsiakkaatTietokannasta());
        asiakasTable.setItems(asiakasData);
        asiakasTable.setEditable(true);

        naytaAsiakkaat(asiakasData);


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

    private List<Asiakas> haeAsiakkaatTietokannasta() throws SQLException {
        List<Asiakas> asiakkaat = new ArrayList<>();

        DatabaseManager dbmanager = new DatabaseManager(url, user, password);
        ResultSet rs = dbmanager.retrieveData("asiakas", new String[]{"asiakas_id", "postinro", "etunimi", "sukunimi", "lahiosoite", "email", "puhelinnro"});

        while (rs.next()) {
            int asiakasid = rs.getInt("asiakas_id");
            int postinro = rs.getInt("postinro");
            String asiakasenimi = rs.getString("etunimi");
            String asiakassnimi = rs.getString("sukunimi");
            String lahiosoite = rs.getString("lahiosoite");
            String email = rs.getString("email");
            int puhnro = rs.getInt("puhelinnro");

            asiakkaat.add(new Asiakas(asiakasid, postinro,asiakasenimi,asiakassnimi,lahiosoite,email,puhnro));

        }
        return asiakkaat;
    }

    private void updateAsiakas(Asiakas asiakas) {
        try {
            Connection conn = DriverManager.getConnection(url, user, password);
            String insertQuery = "INSERT INTO asiakas (asiakas_id, postinro, etunimi, sukunimi, lahiosoite, email, puhelinnro) VALUES ( ?, ?,?,?,?,?,?)";
            PreparedStatement preparedStatement = conn.prepareStatement(insertQuery);
            preparedStatement.setInt(1, asiakas.getAsiakasId());
            preparedStatement.setInt(2, asiakas.getPostiNro());
            preparedStatement.setString(3, asiakas.getEtunimi());
            preparedStatement.setString(4, asiakas.getSukunimi());
            preparedStatement.setString(5, asiakas.getLahiosoite());
            preparedStatement.setString(6, asiakas.getEmail());
            preparedStatement.setInt(7, asiakas.getPuhnumero());
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
    private void lisaaasiakas(ActionEvent event) {
        ObservableList<Asiakas> asiakasData = asiakasTable.getItems();
        Asiakas uusiAsiakas = new Asiakas(Integer.parseInt(asiakasidkentta.getText()),Integer.parseInt(postinrokentta.getText()),etunimikentta.getText(),
                sukunimikentta.getText(),lahiosoitekentta.getText(), emailkentta.getText(),Integer.parseInt(puhnrokentta.getText()));

        asiakasData.add(uusiAsiakas);

        updateAsiakas(uusiAsiakas);
        asiakasidkentta.clear();
        postinrokentta.clear();
        etunimikentta.clear();
        sukunimikentta.clear();
        lahiosoitekentta.clear();
        emailkentta.clear();
        puhnrokentta.clear();

        naytaAsiakkaat(asiakasData);

    }

    private void muokkaaAsiakas() throws SQLException {
        Asiakas asiakas = asiakasTable.getSelectionModel().getSelectedItem();
        DatabaseManager dbManager = new DatabaseManager(url, user,password);
        dbManager.updateAsiakas(asiakas);
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

            try {
                Connection conn = DriverManager.getConnection(url, user, password);
                String deleteQuery = "DELETE FROM asiakas WHERE asiakas_id = ?";
                PreparedStatement preparedStatement = conn.prepareStatement(deleteQuery);
                preparedStatement.setInt(1, valittuAsiakas.getAsiakasId());
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

