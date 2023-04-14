package com.example.ohjelmistotuotanto.NakymaHallinta.MokkiHallinta;

import com.example.ohjelmistotuotanto.Olioluokat.MokkiOlio;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

public class MokkiLisaysView extends VBox {
    private TextField idField;
    private TextField alueIdField;
    private TextField postinroField;
    private TextField nimiField;
    private TextField katuosoiteField;
    private TextField hintaField;
    private TextField kuvausField;
    private TextField henkilomaaraField;
    private TextField varusteluField;
    private Button lisaaMokkiButton;
    private ListView<MokkiOlio> mokkiListView;

    public MokkiLisaysView(ListView<MokkiOlio> mokkiListView) {
        super();
        this.mokkiListView = mokkiListView;

        Label uusiMokkiLabel = new Label("Uusi mökki");

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


        lisaaMokkiButton = new Button("Lisää");
        lisaaMokkiButton.setOnAction(e -> lisaaMokki());

        VBox lisaysBox = new VBox();
        lisaysBox.getChildren().addAll(
                uusiMokkiLabel, idField, alueIdField, postinroField, nimiField,
                katuosoiteField, hintaField, kuvausField, henkilomaaraField,
                varusteluField, lisaaMokkiButton);
        lisaysBox.setSpacing(10);
        lisaysBox.setPadding(new Insets(10, 10, 10, 10));

        this.getChildren().add(lisaysBox);
    }

    private void poistamokki(){

    }
    private void lisaaMokki() {
        // Tarkista, että kaikki kentät on täytetty
        if (idField.getText().isEmpty() || alueIdField.getText().isEmpty() ||
                postinroField.getText().isEmpty() || nimiField.getText().isEmpty() ||
                katuosoiteField.getText().isEmpty() || hintaField.getText().isEmpty() ||
                kuvausField.getText().isEmpty() || henkilomaaraField.getText().isEmpty() ||
                varusteluField.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Virhe!");
            alert.setHeaderText(null);
            alert.setContentText("Kaikki kentät on täytettävä!");
            alert.showAndWait();
            return;
        }


        // Luo uusi mökki-olio käyttäjän syöttämistä tiedoista
        Mokki uusiMokki = new Mokki(
                idField.getText(),
                Integer.parseInt(alueIdField.getText()),
                Integer.parseInt(postinroField.getText()),
                nimiField.getText(),
                katuosoiteField.getText(),
                Double.parseDouble(hintaField.getText()),
                kuvausField.getText(),
                Integer.parseInt(henkilomaaraField.getText()),
                varusteluField.getText()
        );

        // Lisää uusi mökki ListView-näkymään
        mokkiListView.getItems().add(uusiMokki);

        // Tyhjennä syötekentät
        idField.clear();
        alueIdField.clear();
        postinroField.clear();
        nimiField.clear();
        katuosoiteField.clear();
        hintaField.clear();
        kuvausField.clear();
        henkilomaaraField.clear();
        varusteluField.clear();
    }
}