package com.example.ohjelmistotuotanto.NakymaHallinta.VarauksenPalvelut;

import com.example.ohjelmistotuotanto.DatabaseManager;
import com.example.ohjelmistotuotanto.Olioluokat.VarauksenPalvelut;
import com.example.ohjelmistotuotanto.Olioluokat.Varaus;
import javafx.application.Application;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.BorderPane;
import javafx.util.converter.IntegerStringConverter;

import java.sql.*;
import java.util.List;

public class VarauksenPalvelutController extends BorderPane
{
    @FXML
    private TableView<VarauksenPalvelut> varatutPalvelutTable;
    @FXML
    private TextField hakuKentta;
    @FXML
    private Button lisaaNappain;
    @FXML
    private TableColumn<VarauksenPalvelut, Integer> lukumaaraColumn;
    @FXML
    private TextField lukumaaraKentta;
    @FXML
    private TableColumn<VarauksenPalvelut, Integer> palveluidColumn;
    @FXML
    private ComboBox<Integer> palveluidComboBOx;
    @FXML
    private Button poistaNappain;
    @FXML
    private TableColumn<VarauksenPalvelut, Integer> varausidColumn;
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

    private void naytaVaratutPalvelut(List<VarauksenPalvelut> varatutPalvelut)
    {
        ObservableList<VarauksenPalvelut> varausData = FXCollections.observableArrayList(varatutPalvelut);
        varatutPalvelutTable.setItems(varausData);
    }



    public void initialize() throws SQLException
    {
        try
        {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/vn","root","");
            String query = "SELECT DISTINCT varaus_id FROM varaus";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            ObservableList<Integer> varausIds = FXCollections.observableArrayList();

            while (rs.next()) {
                varausIdList.add(rs.getInt("varaus_id"));
            }
            varausidComboBox.setItems(varausIds);
            conn.close();

        } catch (SQLException e)
        {
            e.printStackTrace();
        }

        try
        {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/vn","root","");
            ResultSet rs = conn.createStatement().executeQuery("SELECT palvelu_id FROM palvelu");

            while (rs.next())
            {
                palveluIdList.add(rs.getInt("palvelu_id"));
            }
            palveluidComboBOx.setItems(palveluIdList);

        } catch (SQLException e){
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
    }
}
