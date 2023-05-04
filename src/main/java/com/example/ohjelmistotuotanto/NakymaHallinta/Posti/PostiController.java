package com.example.ohjelmistotuotanto.NakymaHallinta.Posti;

import com.example.ohjelmistotuotanto.DatabaseManager;
import com.example.ohjelmistotuotanto.Olioluokat.Posti;
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

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PostiController extends BorderPane
{
    @FXML
    public TableView<Posti> postiTable;
    @FXML
    private Button lisaaNappi;
    @FXML
    private Button poistaNappi;
    @FXML
    private TableColumn<Posti, String> postinroColumn;
    @FXML
    private TextField postinroKentta;
    @FXML
    private TableColumn<Posti, String> toimipaikkaColumn;
    @FXML
    private TextField toimipaikkaKentta;

    private List<Posti> posti = new ArrayList<>();

    private String url = "jdbc:mysql://localhost:3306/vn";
    private String user = "root";
    private String password = "";

    private void naytaPostit(List<Posti> postit) {
        ObservableList<Posti> postiData = FXCollections.observableArrayList(postit);
        postiTable.setItems(postiData);
    }

    public void initialize() throws SQLException{

        postinroColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPostinro()));
        postinroColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        postinroColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Posti, String>>()
        {
            @Override
            public void handle(TableColumn.CellEditEvent<Posti, String> event)
            {
                Posti posti1 = event.getRowValue();
                posti1.setPostinro(event.getNewValue());
            }
        });

        toimipaikkaColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getToimipaikka()));
        toimipaikkaColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        toimipaikkaColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Posti, String>>()
        {
            @Override
            public void handle(TableColumn.CellEditEvent<Posti, String> event)
            {
                Posti posti1 = event.getRowValue();
                posti1.setToimipaikka(event.getNewValue());

            }
        });

        ObservableList<Posti> postiData = FXCollections.observableArrayList(haePostitTietokannasta());
        postiTable.setItems(postiData);
        postiTable.setEditable(true);
    }

    private List<Posti> haePostitTietokannasta() throws SQLException {
        List<Posti> postit = new ArrayList<>();

        DatabaseManager dbmanager = new DatabaseManager(url, user, password);
        ResultSet rs = dbmanager.retrieveData("Posti", new String[]{"postinro", "toimipaikka"});

        while (rs.next()) {
            String postinro = rs.getString("postinro");
            String toimipaikka = rs.getString("nimi");
            postit.add(new Posti(postinro, toimipaikka));

        }
        return postit;
    }

    public PostiController() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/ohjelmistotuotanto/posti.fxml"));
        loader.setController(this);
        try {
            setCenter(loader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void lisaaPostitoimialue(ActionEvent event) {
        ObservableList<Posti> postiData = postiTable.getItems();

        Posti uusiPosti = new Posti(postinroKentta.getText(), toimipaikkaKentta.getText());

        postiData.add(uusiPosti);
        postinroKentta.clear();
        toimipaikkaKentta.clear();

        naytaPostit(postiData);

    }
    @FXML
    void poistaToimipaikka(ActionEvent event) {
        Posti selectedPosti = postiTable.getSelectionModel().getSelectedItem();
        if (selectedPosti != null) {
            TableView.TableViewSelectionModel<Posti> selectionModel = postiTable.getSelectionModel();
            ObservableList<Posti> tableItems = postiTable.getItems();
            int selectedIndex = selectionModel.getSelectedIndex();
            tableItems.remove(selectedIndex);
            postiTable.setItems(tableItems);
            postiTable.refresh();

            try {
                Connection conn = DriverManager.getConnection(url, user, password);
                String deleteQuery = "DELETE FROM posti WHERE postinro = ?";
                PreparedStatement preparedStatement = conn.prepareStatement(deleteQuery);
                preparedStatement.setString(1, selectedPosti.getPostinro());
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



}
