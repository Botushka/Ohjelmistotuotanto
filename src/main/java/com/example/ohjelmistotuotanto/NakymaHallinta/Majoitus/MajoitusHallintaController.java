package com.example.ohjelmistotuotanto.NakymaHallinta.Majoitus;
import com.example.ohjelmistotuotanto.DatabaseManager;
import com.example.ohjelmistotuotanto.Olioluokat.Palvelu;
import com.example.ohjelmistotuotanto.Olioluokat.Varaus;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.BorderPane;
import javafx.util.converter.DateStringConverter;
import javafx.util.converter.IntegerStringConverter;

import java.io.IOException;
import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.Date;
import java.util.stream.Collectors;

public class MajoitusHallintaController extends BorderPane
{
    @FXML
    private TableView<Varaus> varausTable;
    @FXML
    private TextField alkupvmtxtKentta;

    @FXML
    private TextField loppupvmtxtKentta;

    @FXML
    private TableColumn<Varaus, Integer> varausIDColumn;
    @FXML
    private TableColumn<Varaus, Date> alkupvmColumn;
    @FXML
    private TableColumn<Varaus, Integer> alueColumn;
    @FXML
    private TableColumn<Varaus, Integer> asiakasColumn;
    @FXML
    private TableColumn<Varaus, Date> loppupvmColumn;
    @FXML
    private TableColumn<Varaus, Integer> mokkiColumn;
    @FXML
    private TableColumn<Varaus, String> palvelutColumn;
    @FXML
    private TableColumn<Varaus, Date> vahvistettuColumn;
    @FXML
    private TableColumn<Varaus, Date> varattuColumn;
    @FXML
    private DatePicker alkupvmkentta;
    @FXML
    private ComboBox<Integer> aluekentta;
    @FXML
    private ComboBox<Integer> asiakaskentta;
    @FXML
    private Button lisaapainike;
    @FXML
    private DatePicker loppupvmkentta;
    @FXML
    private ComboBox<Integer> mokkikentta;
    @FXML
    private ComboBox<Integer> palvelutkentta;
    @FXML
    private Button poistapainike;
    @FXML
    private Button tallennapainike;
    @FXML
    private TextField varausidkentta;
    @FXML
    private TextField haevaraus;


    private String url = "jdbc:mysql://localhost:3306/vn";
    private String user = "root";
    private String password = "";

    private Connection conn;
    private PreparedStatement stmt;
    private ResultSet rs;
    private ObservableList<Integer> alueList = FXCollections.observableArrayList();
    private ObservableList<Integer> mokkiList = FXCollections.observableArrayList();


    private DatabaseManager dbManager;

    private void naytaVaraukset(List<Varaus> varaukset)
    {
        ObservableList<Varaus> varausData = FXCollections.observableArrayList(varaukset);
        varausTable.setItems(varausData);
    }

    @FXML
    public void alueComboBoxAction() {
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/vn", "root", "");

            stmt = conn.prepareStatement("SELECT * FROM mokki WHERE alue_id = ?");
            stmt.setInt(1, (aluekentta.getSelectionModel().getSelectedItem()));
            rs = stmt.executeQuery();

            mokkiList.clear();

            while (rs.next()) {
                mokkiList.add(rs.getInt("mokki_id"));
            }

            mokkikentta.setItems(mokkiList);

            stmt.close();
            rs.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void initialize()
    {
        try
        {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/vn","root","");
            ResultSet rs = conn.createStatement().executeQuery("SELECT alue_id FROM mokki");

            while (rs.next())
            {
                alueList.add(rs.getInt("alue_id"));
            }
            aluekentta.setItems(alueList);

        } catch (SQLException e){
            e.printStackTrace();
        }

        mokkikentta.disableProperty().bind(aluekentta.getSelectionModel().selectedItemProperty().isNull());
        aluekentta.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            try
            {
                mokkiList.clear();
                String sql = "SELECT mokki_id FROM mokki WHERE alue_id = ?";
                PreparedStatement statement = conn.prepareStatement(sql);
                statement.setInt(1, newValue);
                ResultSet rs = statement.executeQuery();
                while (rs.next()) {
                    mokkiList.add(rs.getInt("mokki_id"));
                }
                mokkikentta.setItems(mokkiList);
            }catch (SQLException e){
                e.printStackTrace();
            }
                });

        asiakasColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getAsiakas_id()).asObject());
        asiakasColumn.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        asiakasColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Varaus, Integer>>()
        {
            @Override
            public void handle(TableColumn.CellEditEvent<Varaus, Integer> event)
            {
                Varaus varaus = event.getRowValue();
                varaus.setAsiakas_id(event.getNewValue());
            }
        });

        //alueColumn.setCellValueFactory(cellData -> SimpleIntegerProperty(cellData.getValue().getA));

        mokkiColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getMokki_id()).asObject());
        mokkiColumn.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        mokkiColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Varaus, Integer>>()
        {
            @Override
            public void handle(TableColumn.CellEditEvent<Varaus, Integer> event)
            {
                Varaus varaus = event.getRowValue();
                varaus.setMokki_id(event.getNewValue());
            }
        });

        alkupvmColumn.setCellFactory(column -> {
            TableCell<Varaus, Date> cell = new TableCell<Varaus, Date>() {
                private SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");

                @Override
                protected void updateItem(Date item, boolean empty) {
                    super.updateItem(item, empty);
                    if(empty) {
                        setText(null);
                    }
                    else {
                        this.setText(format.format(item));

                    }
                }
            };

            return cell;
        });

        varattuColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getVarattu_pvm()));
        varattuColumn.setCellFactory(TextFieldTableCell.forTableColumn(new DateStringConverter()));
        varattuColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Varaus, Date>>()
        {
            @Override
            public void handle(TableColumn.CellEditEvent<Varaus, Date> event)
            {
                Varaus varaus = event.getRowValue();
                varaus.setVarattu_pvm(event.getNewValue());
            }
        });

        alkupvmColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getVarattu_alkupvm()));
        alkupvmColumn.setCellFactory(TextFieldTableCell.forTableColumn(new DateStringConverter()));
        alkupvmColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Varaus, Date>>()
        {
            @Override
            public void handle(TableColumn.CellEditEvent<Varaus, Date> event)
            {
                Varaus varaus = event.getRowValue();
                varaus.setVarattu_alkupvm(event.getNewValue());
            }
        });

        loppupvmColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getVarattu_loppupvm()));
        loppupvmColumn.setCellFactory(TextFieldTableCell.forTableColumn(new DateStringConverter()));
        loppupvmColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Varaus, Date>>()
        {
            @Override
            public void handle(TableColumn.CellEditEvent<Varaus, Date> event)
            {
                Varaus varaus = event.getRowValue();
                varaus.setVarattu_loppupvm(event.getNewValue());
            }
        });

        //palvelutColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get));

        vahvistettuColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getVahvistus_pvm()));
        vahvistettuColumn.setCellFactory(TextFieldTableCell.forTableColumn(new DateStringConverter()));
        vahvistettuColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Varaus, Date>>()
        {
            @Override
            public void handle(TableColumn.CellEditEvent<Varaus, Date> event)
            {
                Varaus varaus = event.getRowValue();
                varaus.setVahvistus_pvm(event.getNewValue());
            }
        });



        ObservableList<Varaus> varausData = FXCollections.observableArrayList(haeVarauksetTietokannasta());
        varausTable.setItems(varausData);
        varausTable.setEditable(true);

        naytaVaraukset(varausData);

        haevaraus.textProperty().addListener((observable, oldValue, newValue) ->
        {
            String hakusana = newValue.toLowerCase();
            if (hakusana.trim().isEmpty()) {
                varausTable.setItems(varausData);
            } else {
                List<Varaus> hakutulokset = new ArrayList<>();
                for (Varaus varaus : varausData) {
                    if (String.valueOf(varaus.getVaraus_id()).toLowerCase().contains(hakusana) ||
                            String.valueOf(varaus.getAsiakas_id()).toLowerCase().contains(hakusana) ||
                            //String.valueOf(varaus.getAlueID()).toLowerCase().contains(hakusana) ||
                            String.valueOf(varaus.getMokki_id()).toLowerCase().contains(hakusana) ||
                            //varaus.getVarattu_alkupvm().toLowerCase().contains(hakusana) ||
                            String.valueOf(varaus.getVarattu_loppupvm()).toLowerCase().contains(hakusana) ||
                            String.valueOf(varaus.getVarattu_pvm()).toLowerCase().contains(hakusana))
                    //varaus.getVahvistus_pvm().toLowerCase().contains(hakusana) ||
                    //varaus.getPalvelut().toLowerCase().contains(hakusana))
                    {
                        hakutulokset.add(varaus);
                    }
                }
                varausTable.setItems(FXCollections.observableArrayList(hakutulokset));
            }
        });
    }


    private List<Varaus> haeVarauksetTietokannasta()
    {
        List<Varaus> varaus = new ArrayList<>();

        return varaus;
    }
    @FXML
    public void lisaapainike(ActionEvent event) {

        int newId = 1;
        ObservableList<Varaus> varausData = varausTable.getItems();
        if (!varausData.isEmpty()) {

            varausData.sort(Comparator.comparingInt(Varaus::getVaraus_id).reversed());

            newId = varausData.get(0).getVaraus_id() + 1;
        }

        String dateStringAlku = loppupvmtxtKentta.getText();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = dateFormat.parse(dateStringAlku);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        String dateStringLoppu = loppupvmtxtKentta.getText();
        DateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
        Date date1 = null;
        try {
            date1 = dateFormat1.parse(dateStringLoppu);
        } catch (ParseException e) {
            e.printStackTrace();
        }


        Date alkuDate = java.sql.Date.valueOf(alkupvmkentta.getValue());
        Date loppuDate = java.sql.Date.valueOf(loppupvmkentta.getValue());

        LocalDate localDate = LocalDate.now();
        ZoneId zoneId = ZoneId.systemDefault();
        ZonedDateTime zonedDateTime = localDate.atStartOfDay(zoneId);
        Instant instant = zonedDateTime.toInstant();
        Date varattuDate = Date.from(instant);

        Varaus uusiVaraus = new Varaus(1, 2,
                3, varattuDate, varattuDate, date, date1);

        varausData.add(uusiVaraus);

        // Clear the input fields
        varausidkentta.clear();
        asiakaskentta.setValue(null);
        mokkikentta.setValue(null);
        alkupvmkentta.setValue(null);
        loppupvmkentta.setValue(null);

        naytaVaraukset(varausData);
    }

    public MajoitusHallintaController() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/ohjelmistotuotanto/majoitus.fxml"));
        loader.setController(this);
        try {
            setCenter(loader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    void lisaavaraus(ActionEvent event) {

    }

    @FXML
    void poistavaraus(ActionEvent event) {

    }

    @FXML
    void tallennavaraus(ActionEvent event) {

    }

}
