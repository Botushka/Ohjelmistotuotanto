package com.example.ohjelmistotuotanto.NakymaHallinta.Alue;

import com.example.ohjelmistotuotanto.DatabaseManager;
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
import javafx.scene.layout.VBox;
import javafx.util.converter.IntegerStringConverter;

import java.io.IOException;
import java.sql.*;
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

    private String url = "jdbc:mysql://localhost:3306/vn";
    private String user = "root";
    private String password = "";

    private Connection connection = null;

    {
        try {
            connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    private void updateAlue(AlueOlio alue) {
        try {
            Connection conn = DriverManager.getConnection(url, user, password);
            String insertQuery = "INSERT INTO alue (alue_id, nimi) VALUES ( ?, ?)";
            PreparedStatement preparedStatement = conn.prepareStatement(insertQuery);
            preparedStatement.setInt(1, alue.getAlue_id());
            preparedStatement.setString(2, alue.getArea_nimi());
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Jokin meni pieleen tiedon tallentamisessa tietokantaan");
            }
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void muokkaaAlue() throws SQLException {
        AlueOlio alue = alueTable.getSelectionModel().getSelectedItem();
        DatabaseManager dbManager = new DatabaseManager(url, user,password);
        dbManager.updateAlue(alue);
    }

    private void naytaAlue(List<AlueOlio> alueet) {
        ObservableList<AlueOlio> alueetData = FXCollections.observableArrayList(alueet);
        alueTable.setItems(alueetData);
    }
    @FXML
    public void initialize() throws SQLException {
       
        nimiAlueColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getArea_nimi()));
        nimiAlueColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        nimiAlueColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<AlueOlio, String>>()
        {
            @Override
            public void handle(TableColumn.CellEditEvent<AlueOlio, String> event)
            {
                AlueOlio alueOlio1 = event.getRowValue();
                alueOlio1.setArea_nimi(event.getNewValue());
                try {
                    muokkaaAlue();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        idalueColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getAlue_id()).asObject());
        idalueColumn.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        idalueColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<AlueOlio, Integer>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<AlueOlio, Integer> event) {
                AlueOlio alue = event.getRowValue();
                alue.setAlue_id(event.getNewValue());
                try {
                    muokkaaAlue();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        ObservableList<AlueOlio> alueetData = FXCollections.observableArrayList(haeAlueetTietokannasta());
        alueTable.setItems(alueetData);
        alueTable.setEditable(true);

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

    private List<AlueOlio> haeAlueetTietokannasta() throws SQLException {
        List<AlueOlio> alueet = new ArrayList<>();

        DatabaseManager dbmanager = new DatabaseManager(url, user, password);
        ResultSet rs = dbmanager.retrieveData("alue", new String[]{"alue_id", "nimi"});

        while (rs.next()) {
            int alueId = rs.getInt("alue_id");
            String alue_nimi = rs.getString("nimi");
            alueet.add(new AlueOlio(alueId, alue_nimi));

        }
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

        int newId = 1;
        ObservableList<AlueOlio> alueetData = alueTable.getItems();
        if (!alueetData.isEmpty()) {

            alueetData.sort(Comparator.comparingInt(AlueOlio::getAlue_id).reversed());

            newId = alueetData.get(0).getAlue_id() + 1;
        }
        AlueOlio newAlue = new AlueOlio(newId, aluenimiKentta.getText());

        alueetData.add(newAlue);
        updateAlue(newAlue);
        aluenimiKentta.clear();
        alueidKentta.clear();

        naytaAlue(alueetData);
    }

    @FXML
    private void delAlue(ActionEvent event) {
        AlueOlio selectedAlue = alueTable.getSelectionModel().getSelectedItem();
        if (selectedAlue != null) {
            TableView.TableViewSelectionModel<AlueOlio> selectionModel = alueTable.getSelectionModel();
            ObservableList<AlueOlio> tableItems = alueTable.getItems();
            int selectedIndex = selectionModel.getSelectedIndex();
            tableItems.remove(selectedIndex);
            alueTable.setItems(tableItems);
            alueTable.refresh();

            try {
                Connection conn = DriverManager.getConnection(url, user, password);
                String deleteQuery = "DELETE FROM alue WHERE alue_id = ?";
                PreparedStatement preparedStatement = conn.prepareStatement(deleteQuery);
                preparedStatement.setInt(1, selectedAlue.getAlue_id());
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
    private void editAlue() {

    }
    @FXML
    private void haeAlue() {

    }
}
