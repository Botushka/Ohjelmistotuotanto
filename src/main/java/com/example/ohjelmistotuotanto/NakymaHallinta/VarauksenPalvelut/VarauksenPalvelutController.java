package com.example.ohjelmistotuotanto.NakymaHallinta.VarauksenPalvelut;

import com.example.ohjelmistotuotanto.DatabaseManager;
import com.example.ohjelmistotuotanto.NakymaHallinta.Alue.AlueOlio;
import com.example.ohjelmistotuotanto.Olioluokat.VarauksenPalvelut;
import javafx.application.Application;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.BorderPane;
import javafx.util.converter.IntegerStringConverter;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class VarauksenPalvelutController extends BorderPane
{
    @FXML
    private TableView<VarauksenPalvelut> varatutPalvelutTable;
    @FXML
    private TableColumn<VarauksenPalvelut, Integer> lukumaaraColumn;
    @FXML
    private TableColumn<VarauksenPalvelut, Integer> palveluidColumn;
    @FXML
    private TableColumn<VarauksenPalvelut, Integer> varausidColumn;
    @FXML
    private TextField hakuKentta;
    @FXML
    private Button lisaaNappain;
    @FXML
    private TextField lukumaaraKentta;
    @FXML
    private ComboBox<Integer> palveluidComboBOx;
    @FXML
    private Button poistaNappain;
    @FXML
    private ComboBox<Integer> varausidComboBox;

    private String url = "jdbc:mysql://localhost:3306/vn";
    private String user = "root";
    private String password = "";

    private Connection conn;
    private PreparedStatement stmt;
    private ResultSet rs;
    private ObservableList<Integer> varausIdList = FXCollections.observableArrayList();
    private ObservableList<Integer> palveluIdList = FXCollections.observableArrayList();
    private DatabaseManager dbManager;

    private void naytaVaratutPalvelut(List<VarauksenPalvelut> varatut)
    {
        ObservableList<VarauksenPalvelut> varatutData = FXCollections.observableArrayList(varatut);
        varatutPalvelutTable.setItems(varatutData);
    }

    private void muokkaaVarauksenPalvelut() throws SQLException
    {
        VarauksenPalvelut varauksenPalvelut = varatutPalvelutTable.getSelectionModel().getSelectedItem();
        DatabaseManager dbManager = new DatabaseManager(url, user, password);
        dbManager.updateVarauksenPalvelut(varauksenPalvelut);
    }

    public void initialize() throws SQLException
    {
        try
        {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/vn", "root", "");
            String query = "SELECT DISTINCT varaus_id FROM varaus";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            ObservableList<Integer> varausIds = FXCollections.observableArrayList();

            while (rs.next())
            {
                varausIds.add(rs.getInt("varaus_id"));
            }
            varausidComboBox.setItems(varausIds);
            conn.close();

        } catch (SQLException e)
        {
            e.printStackTrace();
        }

        try
        {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/vn", "root", "");
            String query = "SELECT DISTINCT palvelu_id FROM palvelu";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            ObservableList<Integer> palveluIds = FXCollections.observableArrayList();

            while (rs.next())
            {
                palveluIds.add(rs.getInt("palvelu_id"));
            }
            palveluidComboBOx.setItems(palveluIds);
            conn.close();

        } catch (SQLException e)
        {
            e.printStackTrace();
        }


        varausidColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getVaraus_id()).asObject());
        varausidColumn.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        varausidColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<VarauksenPalvelut, Integer>>()
        {
            @Override
            public void handle(TableColumn.CellEditEvent<VarauksenPalvelut, Integer> event)
            {
                VarauksenPalvelut varaus = event.getRowValue();
                varaus.setVaraus_id(event.getNewValue());
            }
        });

        palveluidColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getPalvelu_id()).asObject());
        palveluidColumn.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        palveluidColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<VarauksenPalvelut, Integer>>()
        {
            @Override
            public void handle(TableColumn.CellEditEvent<VarauksenPalvelut, Integer> event)
            {
                VarauksenPalvelut varaus = event.getRowValue();
                varaus.setPalvelu_id(event.getNewValue());
            }
        });

        lukumaaraColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getLkm()).asObject());
        lukumaaraColumn.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        lukumaaraColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<VarauksenPalvelut, Integer>>()
        {
            @Override
            public void handle(TableColumn.CellEditEvent<VarauksenPalvelut, Integer> event)
            {
                VarauksenPalvelut varaus = event.getRowValue();
                varaus.setLkm(event.getNewValue());
            }
        });

        ObservableList<VarauksenPalvelut> varatutData = FXCollections.observableArrayList(haeVaratutPalvelutTietokannasta());
        varatutPalvelutTable.setItems(varatutData);
        varatutPalvelutTable.setEditable(true);

        naytaVaratutPalvelut(varatutData);

        hakuKentta.textProperty().addListener((observable, oldValue, newValue) ->
        {
            String hakusana = newValue.toLowerCase();
            if (hakusana.trim().isEmpty()) {
                varatutPalvelutTable.setItems(varatutData);
            } else {
                List<VarauksenPalvelut> hakutulokset = new ArrayList<>();
                for (VarauksenPalvelut varaus : varatutData) {
                    if (String.valueOf(varaus.getVaraus_id()).toLowerCase().contains(hakusana) ||
                            String.valueOf(varaus.getPalvelu_id()).toLowerCase().contains(hakusana) ||
                            String.valueOf(varaus.getLkm()).toLowerCase().contains(hakusana))
                    {
                        hakutulokset.add(varaus);
                    }
                }
                varatutPalvelutTable.setItems(FXCollections.observableArrayList(hakutulokset));
            }
        });
    }

    private List<VarauksenPalvelut> haeVaratutPalvelutTietokannasta() throws SQLException
    {
        List<VarauksenPalvelut> varaus = new ArrayList<>();
        /*DatabaseManager dbmanager = new DatabaseManager(url, user, password);
        ResultSet rs = dbmanager.retrieveData("varauksen_palvelut", new String[]{"varaus_id", "palvelu_id", "lkm"});

        while (rs.next()) {
            int varausId = rs.getInt("varaus_id");
            int palveluId = rs.getInt("palvelu_id");
            int lkm = rs.getInt("lkm");
            varaus.add(new VarauksenPalvelut(varausId, palveluId,lkm));
        }*/
        return varaus;
    }

    private void updateVarauksenPalvelut(VarauksenPalvelut varaus)
    {
        try
        {
            Connection conn = DriverManager.getConnection(url, user, password);
            String insertQuery = "INSERT INTO varauksen_palvelut (varaus_id, palvelu_id, lkm) VALUES ( ?, ?, ?)";
            PreparedStatement preparedStatement = conn.prepareStatement(insertQuery);
            preparedStatement.setInt(1, varaus.getVaraus_id());
            preparedStatement.setInt(2, varaus.getPalvelu_id());
            preparedStatement.setInt(3, varaus.getLkm());
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0)
            {
                throw new SQLException("Jokin meni pieleen tiedon tallentamisessa tietokantaan");
            }
            conn.close();
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    @FXML
    private void lisaaVarattuPalvelu(ActionEvent event)
    {
        ObservableList<VarauksenPalvelut> varausData = varatutPalvelutTable.getItems();


        VarauksenPalvelut newVaraus = new VarauksenPalvelut(Integer.parseInt(varausidComboBox.getEditor().getText()),
                Integer.parseInt(palveluidComboBOx.getEditor().getText()), Integer.parseInt(lukumaaraKentta.getText()));

        varausData.add(newVaraus);
        updateVarauksenPalvelut(newVaraus);
        varausidComboBox.setValue(null);
        palveluidComboBOx.setValue(null);
        lukumaaraKentta.clear();

        naytaVaratutPalvelut(varausData);
    }

    @FXML
    private void poistaVarauksenPalvelu(ActionEvent event) {
        VarauksenPalvelut valittuVaraus = varatutPalvelutTable.getSelectionModel().getSelectedItem();
        if (valittuVaraus != null) {
            TableView.TableViewSelectionModel<VarauksenPalvelut> selectionModel = varatutPalvelutTable.getSelectionModel();
            ObservableList<VarauksenPalvelut> tableItems = varatutPalvelutTable.getItems();
            int selectedIndex = selectionModel.getSelectedIndex();
            tableItems.remove(selectedIndex);
            varatutPalvelutTable.setItems(tableItems);
            varatutPalvelutTable.refresh();

            try {
                Connection conn = DriverManager.getConnection(url, user, password);
                String deleteQuery = "DELETE FROM varauksen_palvelut WHERE varaus_id = ?";
                PreparedStatement preparedStatement = conn.prepareStatement(deleteQuery);
                preparedStatement.setInt(1, valittuVaraus.getVaraus_id());
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

    public VarauksenPalvelutController()
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/ohjelmistotuotanto/varauksenpalvelut.fxml"));
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
