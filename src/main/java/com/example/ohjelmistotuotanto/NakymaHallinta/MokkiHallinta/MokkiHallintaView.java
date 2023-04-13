package com.example.ohjelmistotuotanto.NakymaHallinta.MokkiHallinta;

import com.example.ohjelmistotuotanto.Olioluokat.MokkiOlio;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class MokkiHallintaView extends VBox {
    private TableView<MokkiOlio> mokkiTable;
    private TextField idField;
    private TextField alueIdField;
    private TextField postinroField;
    private TextField nimiField;
    private TextField katuosoiteField;
    private TextField hintaField;
    private TextField kuvausField;
    private TextField henkilomaaraField;
    private TextField varusteluField;
    private BorderPane root;

    public MokkiHallintaView(BorderPane root) {
        this.root = root;
        Label headerLabel = new Label("Mökkien hallinta");
        headerLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));

        // Luo taulukko kaikille mökeille
        mokkiTable = new TableView<>();
        TableColumn<MokkiOlio, Integer> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("mokki_id"));
        TableColumn<MokkiOlio, Integer> alueIdColumn = new TableColumn<>("Alue ID");
        alueIdColumn.setCellValueFactory(new PropertyValueFactory<>("alue_id"));
        TableColumn<MokkiOlio, String> postinroColumn = new TableColumn<>("Postinumero");
        postinroColumn.setCellValueFactory(new PropertyValueFactory<>("postinro"));
        TableColumn<MokkiOlio, String> nimiColumn = new TableColumn<>("Nimi");
        nimiColumn.setCellValueFactory(new PropertyValueFactory<>("mokkinimi"));
        TableColumn<MokkiOlio, String> katuosoiteColumn = new TableColumn<>("Katuosoite");
        katuosoiteColumn.setCellValueFactory(new PropertyValueFactory<>("katuosoite"));
        TableColumn<MokkiOlio, Double> hintaColumn = new TableColumn<>("Hinta");
        hintaColumn.setCellValueFactory(new PropertyValueFactory<>("hinta"));
        TableColumn<MokkiOlio, String> kuvausColumn = new TableColumn<>("Kuvaus");
        kuvausColumn.setCellValueFactory(new PropertyValueFactory<>("kuvaus"));
        TableColumn<MokkiOlio, Integer> henkilomaaraColumn = new TableColumn<>("Henkilömäärä");
        henkilomaaraColumn.setCellValueFactory(new PropertyValueFactory<>("henkilomaara"));
        TableColumn<MokkiOlio, String> varusteluColumn = new TableColumn<>("Varustelu");
        varusteluColumn.setCellValueFactory(new PropertyValueFactory<>("varustelu"));
        mokkiTable.getColumns().addAll(idColumn, alueIdColumn, postinroColumn, nimiColumn, katuosoiteColumn,
                hintaColumn, kuvausColumn, henkilomaaraColumn, varusteluColumn);

        // Luo syötekentät uuden mökin lisäämistä varten
        idField = new TextField();
        idField.setPromptText("ID");
        alueIdField = new TextField();
        alueIdField.setPromptText("Alue ID");
        postinroField = new TextField();
        postinroField.setPromptText("Postinumero");
        nimiField = new TextField();
        nimiField.setPromptText("Nimi");
        katuosoiteField = new TextField();
        Label uusiMokkiLabel = new Label("Lisää uusi mökki");
        idField = new TextField();
        idField.setPromptText("ID");
        alueIdField = new TextField();
        alueIdField.setPromptText("Alue ID");
        postinroField = new TextField();
        postinroField.setPromptText("Postinumero");
        nimiField = new TextField();
        nimiField.setPromptText("Nimi");
        katuosoiteField = new TextField();
        katuosoiteField.setPromptText("Katuosoite");
        hintaField = new TextField();
        hintaField.setPromptText("Hinta");
        kuvausField = new TextField();
        kuvausField.setPromptText("Kuvaus");
        henkilomaaraField = new TextField();
        henkilomaaraField.setPromptText("Henkilömäärä");
        varusteluField = new TextField();
        varusteluField.setPromptText("Varustelu");

// Luo napit uuden mökin lisäämistä ja peruuttamista varten
        Button lisaaMokkiButton = new Button("Lisää");
        lisaaMokkiButton.setOnAction(e -> lisaaMokki());

// Luo VBoxin syötekentille ja napille
        VBox lisaysBox = new VBox();
        lisaysBox.getChildren().addAll(uusiMokkiLabel, idField, alueIdField, postinroField, nimiField, katuosoiteField, hintaField, kuvausField, henkilomaaraField, varusteluField, lisaaMokkiButton);
        lisaysBox.setSpacing(10);
        lisaysBox.setPadding(new Insets(10, 10, 10, 10));

// Aseta lisäysboxin keskelle
        root.setCenter(lisaysBox);
    }

    private void lisaaMokki() {
// Tarkista, että kaikki kentät on täytetty
        if (idField.getText().isEmpty() || alueIdField.getText().isEmpty() || postinroField.getText().isEmpty() || nimiField.getText().isEmpty() || katuosoiteField.getText().isEmpty() || hintaField.getText().isEmpty() || kuvausField.getText().isEmpty() || henkilomaaraField.getText().isEmpty() || varusteluField.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Virhe!");
            alert.setHeaderText(null);
            alert.setContentText("Kaikki kentät on täytettävä!");
            alert.showAndWait();
            return;
        }
    }

    private void poistaMokki() {
        // Toteuta mökin poistaminen
    }

    private void muokkaaMokki() {
        // Toteuta mökin muokkaaminen
    }

    private void haeMokki() {
        // Toteuta mökin hakeminen
    }
}
