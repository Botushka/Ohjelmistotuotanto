package com.example.ohjelmistotuotanto.NakymaHallinta.Majoitus;

import com.example.ohjelmistotuotanto.DatabaseManager;
import com.example.ohjelmistotuotanto.NakymaHallinta.MokkiHallinta.Mokki;
import com.example.ohjelmistotuotanto.Olioluokat.Palvelu;
import com.example.ohjelmistotuotanto.Olioluokat.Varaus;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class MajoitusMokkiController extends BorderPane {

    @FXML
    private TableView<Varaus> mokkiseurantaTable;

    @FXML
    private TableColumn<Varaus, Integer> seurantavarausid;

    @FXML
    private TableColumn<Varaus, Integer> seurantaasiakasid;

    @FXML
    private TableColumn<Varaus, Integer> seurantamokkiid;

    @FXML
    private TableColumn<Varaus, LocalDate> seurantaalkupvm;

    @FXML
    private TableColumn<Varaus, LocalDate> seurantaloppupvm;

    @FXML
    private TextField alkupvmtext;

    @FXML
    private TextField loppupvmtext;

    @FXML
    private Button poistaseurantanappi;
    private String url = "jdbc:mysql://localhost:3306/vn";
    private String user = "root";
    private String password = "";
    private List<Varaus> mokit = new ArrayList<>();

    @FXML
    public void poistaseuranta() throws SQLException {
        Date alkuPvm = null;
        Date loppuPvm = null;

        try {
            // Parse the start and end dates
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            alkuPvm = formatter.parse(alkupvmtext.getText());
            loppuPvm = formatter.parse(loppupvmtext.getText());
        } catch (ParseException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Syötä päivämäärät muodossa yyyy-mm-dd");
            alert.showAndWait();
            return;
        }

        // Get reservation data from the database
        List<Varaus> palveluData = haeVarauksetTietokannasta();

        List<Varaus> hakutulokset = new ArrayList<>();

        for (Varaus varaus : palveluData) {
            Date varauksenAlkuPvm = varaus.getVarattu_alkupvm();
            Date varauksenLoppuPvm = varaus.getVarattu_loppupvm();
            if ((varauksenAlkuPvm.before(loppuPvm) || varauksenAlkuPvm.equals(loppuPvm)) &&
                    (varauksenLoppuPvm.after(alkuPvm) || varauksenLoppuPvm.equals(alkuPvm))) {
                hakutulokset.add(varaus);
            }
        }

        if (hakutulokset.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Ei varauksia valitulla aikavälillä.");
            alert.showAndWait();
        } else {
            mokkiseurantaTable.setItems(FXCollections.observableArrayList(hakutulokset));
        }

        naytaPalvelut(hakutulokset);
    }





    private void naytaPalvelut(List<Varaus> mokit) {
        ObservableList<Varaus> palveluData = FXCollections.observableArrayList(mokit);
        mokkiseurantaTable.setItems(palveluData);
    }

    @FXML
    public void initialize() throws SQLException {

        seurantavarausid.setCellValueFactory(new PropertyValueFactory<>("varaus_id"));
        seurantaasiakasid.setCellValueFactory(new PropertyValueFactory<>("asiakas_id"));
        seurantamokkiid.setCellValueFactory(new PropertyValueFactory<>("mokki_id"));
        seurantaalkupvm.setCellValueFactory(new PropertyValueFactory<>("varattu_alkupvm"));
        seurantaloppupvm.setCellValueFactory(new PropertyValueFactory<>("varattu_loppupvm"));

        ObservableList<Varaus> varausData = FXCollections.observableArrayList(haeVarauksetTietokannasta());
        mokkiseurantaTable.setItems(varausData);
        mokkiseurantaTable.setEditable(true);

        naytaPalvelut(varausData);

    }

    private Connection connection;

    public ResultSet retrieveData(String table, String[] columns) throws SQLException {
        connection = DriverManager.getConnection(url, user, password);
        String query;
        if (columns != null && columns.length > 0) {
            String columnList = String.join(", ", columns);
            query = "SELECT " + columnList + " FROM " + table;
        } else {
            query = "SELECT * FROM " + table;
        }
        try {
            Statement statement = connection.createStatement();
            return statement.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }



    private List<Varaus> haeVarauksetTietokannasta() throws SQLException {

        try {
            List<Varaus> varaus = new ArrayList<>();
            DatabaseManager dbmanager = new DatabaseManager(url, user, password);
            ResultSet rs = dbmanager.retrieveData("varaus", new String[]{"varaus_id", "asiakas_id", "mokki_mokki_id", "varattu_alkupvm", "varattu_loppupvm"});

            while (rs.next()) {
                int varausid = rs.getInt("varaus_id");
                int asiakasid = rs.getInt("asiakas_id");
                int mokkiid = rs.getInt("mokki_mokki_id");
                Date varattualkupvm = rs.getDate("varattu_alkupvm");
                Date varattuloppupvm = rs.getDate("varattu_loppupvm");
                varaus.add(new Varaus(varausid, asiakasid, mokkiid, null, null, varattualkupvm, varattuloppupvm));
            }
            rs.close();

            return varaus;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }
    public MajoitusMokkiController() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/ohjelmistotuotanto/majoitusmokki.fxml"));
        loader.setController(this);
        try {
            setCenter(loader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
