package com.example.ohjelmistotuotanto.NakymaHallinta.VarauksenPalvelut;

import com.example.ohjelmistotuotanto.Olioluokat.VarauksenPalvelut;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;

import java.sql.*;

public class VarauksenPalvelutController extends BorderPane
{
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

    private Connection connection;
    private PreparedStatement varausStatement;
    private PreparedStatement palveluStatement;



    public void initialize() throws SQLException
    {

    }
}
