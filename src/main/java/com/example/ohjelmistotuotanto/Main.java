package com.example.ohjelmistotuotanto;

import com.example.ohjelmistotuotanto.NakymaHallinta.*;
import com.example.ohjelmistotuotanto.NakymaHallinta.MokkiHallinta.Mokki;
import com.example.ohjelmistotuotanto.NakymaHallinta.MokkiHallinta.MokkiHallintaController;
import com.example.ohjelmistotuotanto.NakymaHallinta.MokkiHallinta.MokkiHallintaView;
import com.example.ohjelmistotuotanto.NakymaHallinta.MokkiHallinta.MokkiLisaysView;
import com.example.ohjelmistotuotanto.Olioluokat.MokkiOlio;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    private BorderPane root;
    private MokkiHallintaView mokkiHallintaView;
    private AlueHallintaView alueHallintaView;
    private PalveluHallintaView palveluHallintaView;
    private MajoitusvarausHallintaView majoitusvarausHallintaView;
    private AsiakashallintaView asiakashallintaView;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        root = new BorderPane();
        alueHallintaView = new AlueHallintaView();
        palveluHallintaView = new PalveluHallintaView();
        majoitusvarausHallintaView = new MajoitusvarausHallintaView();
        asiakashallintaView = new AsiakashallintaView();

        MenuBar menuBar = createMenuBar();
        root.setTop(menuBar);

        Scene scene = new Scene(root, 1200, 1000);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Varausjärjestelmä");
        primaryStage.show();
    }

    private MenuBar createMenuBar() {
        MenuBar menuBar = new MenuBar();
        ListView<MokkiOlio> mokkiOlioListView = new ListView<>();
        // Luo uusi ListView-olio, joka sisältää MokkiOlio-olioita
        ListView<Mokki> mokkiListView = (ListView<Mokki>) (ListView<?>) mokkiOlioListView;
        MokkiLisaysView mokkiLisaysView = new MokkiLisaysView(mokkiOlioListView);
        Menu MokkiMenu = new Menu("Mökki");
        MenuItem MokkiMenuItem = new MenuItem("Lisäys");
        MenuItem MokkiMenuItem2 = new MenuItem("Hallinta");
        MokkiHallintaView mokkiHallintaView = new MokkiHallintaView();

        MokkiMenuItem2.setOnAction(e -> {
            root.setCenter(mokkiHallintaView);
        });
        MokkiMenuItem.setOnAction(e -> {
            root.setCenter(mokkiLisaysView);
        });


        MokkiMenu.getItems().addAll(MokkiMenuItem, MokkiMenuItem2);

        Menu AlueMenu = new Menu("Alue");
        MenuItem AlueMenuItem = new MenuItem("Hallinta");
        AlueMenuItem.setOnAction(e -> root.setCenter(alueHallintaView));
        AlueMenu.getItems().add(AlueMenuItem);

        Menu palveluMenu = new Menu("Palvelu");
        MenuItem palveluMenuItem = new MenuItem("Hallinta");
        palveluMenuItem.setOnAction(e -> root.setCenter(palveluHallintaView));
        palveluMenu.getItems().add(palveluMenuItem);

        Menu majoitusvarausMenu = new Menu("Majoitusvaraus");
        MenuItem majoitusvarausMenuItem = new MenuItem("Hallinta");
        majoitusvarausMenuItem.setOnAction(e -> root.setCenter(majoitusvarausHallintaView));
        majoitusvarausMenu.getItems().add(majoitusvarausMenuItem);

        Menu asiakasMenu = new Menu("Asiakashallinta");
        MenuItem asiakasMenuItem = new MenuItem("Hallinta");
        asiakasMenuItem.setOnAction(e -> root.setCenter(asiakashallintaView));
        asiakasMenu.getItems().add(asiakasMenuItem);

        menuBar.getMenus().addAll(MokkiMenu, AlueMenu, palveluMenu, majoitusvarausMenu, asiakasMenu);

        return menuBar;
    }
}

