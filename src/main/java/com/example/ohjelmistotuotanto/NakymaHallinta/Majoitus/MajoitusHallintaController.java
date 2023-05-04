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
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class MajoitusHallintaController extends BorderPane
{
    @FXML
    private TableView<Varaus> varausTable;

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
    private TextField varattuKentta;
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
    private DatePicker vahvistusKentta;
    @FXML
    private ComboBox<Integer> mokkikentta;
    @FXML
    private ComboBox<Palvelu> palvelutkentta;
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

    private DatabaseManager dbManager;

    private void naytaVaraukset(List<Varaus> varaukset)
    {
        ObservableList<Varaus> varausData = FXCollections.observableArrayList(varaukset);
        varausTable.setItems(varausData);
    }

    public class SearchableComboBox extends ComboBox<String>
    {
        private final ObservableList<String> allItems;
        private final TextField aluekentta1;

        public SearchableComboBox(ObservableList<String> items)
        {
            super(items);
            this.allItems = FXCollections.observableArrayList(items);
            this.aluekentta1 = getEditor();

            //Set up filter listener
            aluekentta1.textProperty().addListener((observable, oldValue, newValue) ->
            {
                String filter = newValue.trim();
                if (filter.isEmpty())
                {
                    setItems(allItems);
                    show();
                } else {
                    ObservableList<String> filteredItems = FXCollections.observableArrayList();
                    for (String item : allItems)
                    {
                        if (item.toLowerCase().contains(filter.toLowerCase()))
                        {
                            filteredItems.add(item);
                        }
                    }
                    setItems(filteredItems);
                    if (!filter.isEmpty())
                    {
                        show();
                    } else {
                        hide();
                    }
                }
            });
        }
    }


    public void initialize()
    {
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


        ObservableList<Integer> items = FXCollections.observableArrayList(1, 2, 3, 4, 5);
        aluekentta.setItems(items);

        aluekentta.setEditable(true);
        aluekentta.setOnKeyReleased(event ->{
            String input = aluekentta.getEditor().getText();
            updateAluekentta(input);
        });


        ObservableList<Integer> items2 = FXCollections.observableArrayList(1, 2, 3, 4, 5);

        asiakaskentta.setItems(items2);

        asiakaskentta.setEditable(true);
        asiakaskentta.getEditor().textProperty().addListener((observable, oldValue, newValue) -> {
            asiakaskentta.hide();
            asiakaskentta.getItems().setAll(filterItems2(newValue, items2));
            asiakaskentta.setVisibleRowCount(Math.min(asiakaskentta.getItems().size(), 10));
            asiakaskentta.show();
        });

        ObservableList<Integer> items3 = FXCollections.observableArrayList(1, 2, 3, 4, 5);

        mokkikentta.setItems(items3);

        mokkikentta.setEditable(true);
        mokkikentta.getEditor().textProperty().addListener((observable, oldValue, newValue) -> {
            mokkikentta.hide();
            mokkikentta.getItems().setAll(filterItems3(newValue, items3));
            mokkikentta.setVisibleRowCount(Math.min(asiakaskentta.getItems().size(), 10));
            mokkikentta.show();
        });

        ObservableList<Integer> items4 = FXCollections.observableArrayList(1, 2, 3, 4, 5);

        mokkikentta.setItems(items4);


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

    private List<Integer> filterOptions(String input)
    {
        List<Integer> options = FXCollections.observableArrayList(1,2,3,4,5);
        return options.stream()
                .filter(option -> option.toString().startsWith(input))
                .collect(Collectors.toList());
    }

    private void updateAluekentta(String input){
        List<Integer> filteredOptions = filterOptions(input);
        aluekentta.setItems(FXCollections.observableArrayList(filteredOptions));
    }
    private List<Integer> filterItems(String text, ObservableList<Integer> items) {
        if (text == null || text.isEmpty()) {
            return new ArrayList<>(items);
        }

        List<Integer> filteredItems = new ArrayList<>();
        for (Integer item : items) {
            if (item.toString().toLowerCase().contains(text.toLowerCase())) {
                filteredItems.add(item);
            }
        }
        return filteredItems;
    }
    private List<Integer> filterItems2(String text, ObservableList<Integer> items2) {
        if (text == null || text.isEmpty()) {
            return new ArrayList<>(items2);
        }

        List<Integer> filteredItems = new ArrayList<>();
        for (Integer item : items2) {
            if (item.toString().toLowerCase().contains(text.toLowerCase())) {
                filteredItems.add(item);
            }
        }
        return filteredItems;
    }
    private List<Integer> filterItems3(String text, ObservableList<Integer> items3) {
        if (text == null || text.isEmpty()) {
            return new ArrayList<>(items3);
        }

        List<Integer> filteredItems = new ArrayList<>();
        for (Integer item : items3) {
            if (item.toString().toLowerCase().contains(text.toLowerCase())) {
                filteredItems.add(item);
            }
        }
        return filteredItems;
    }
    private List<Integer> filterItems4(String text, ObservableList<Integer> items4) {
        if (text == null || text.isEmpty()) {
            return new ArrayList<>(items4);
        }

        List<Integer> filteredItems = new ArrayList<>();
        for (Integer item : items4) {
            if (item.toString().toLowerCase().contains(text.toLowerCase())) {
                filteredItems.add(item);
            }
        }
        return filteredItems;
    }
    private List<Varaus> haeVarauksetTietokannasta()
    {
        List<Varaus> varaus = new ArrayList<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyy-MM-dd");
        Date newDate = new Date(0,0,0);
        String formattedDate = dateFormat.format(newDate);
        Date newDate2 = new Date(2020,1,1);
        Date newDate3 = new Date(2019,9,9);
        varaus.add(new Varaus(1,1,1,newDate,newDate2,newDate3,newDate));
        return varaus;
    }
    @FXML
    public void lisaapainike(ActionEvent event) {

        // Generate unique idalue
        int newId = 1;
        ObservableList<Varaus> varausData = varausTable.getItems();
        if (!varausData.isEmpty()) {
            // Sort the list by idalue in descending order
            varausData.sort(Comparator.comparingInt(Varaus::getVaraus_id).reversed());
            // Get the highest idalue in the list and add 1 to generate a new idalue
            newId = varausData.get(0).getVaraus_id() + 1;
        }

        Date varattuDate = java.sql.Date.valueOf(varattuKentta.getText());
        Date vahvistusDate = java.sql.Date.valueOf(vahvistusKentta.getValue());
        Date alkuDate = java.sql.Date.valueOf(alkupvmkentta.getValue());
        Date loppuDate = java.sql.Date.valueOf(loppupvmkentta.getValue());

        Varaus uusiVaraus = new Varaus(newId, Integer.parseInt(asiakaskentta.getEditor().getText()),
                Integer.parseInt(mokkikentta.getEditor().getText()), varattuDate,vahvistusDate, alkuDate, loppuDate);

        varausData.add(uusiVaraus);

        // Clear the input fields
        varausidkentta.clear();
        asiakaskentta.setValue(null);
        mokkikentta.setValue(null);
        varattuKentta.clear();
        vahvistusKentta.setValue(null);
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
