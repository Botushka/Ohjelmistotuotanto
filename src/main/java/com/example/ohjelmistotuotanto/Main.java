package com.example.ohjelmistotuotanto;

import com.example.ohjelmistotuotanto.NakymaHallinta.AlueMokkiHallintaView;
import com.example.ohjelmistotuotanto.NakymaHallinta.AsiakashallintaView;
import com.example.ohjelmistotuotanto.NakymaHallinta.MajoitusvarausHallintaView;
import com.example.ohjelmistotuotanto.NakymaHallinta.PalveluHallintaView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Main extends Application {
    private BorderPane root;
    private AlueMokkiHallintaView alueMokkiHallintaView;
    private PalveluHallintaView palveluHallintaView;
    private MajoitusvarausHallintaView majoitusvarausHallintaView;
    private AsiakashallintaView asiakashallintaView;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        root = new BorderPane();
        alueMokkiHallintaView = new AlueMokkiHallintaView();
        palveluHallintaView = new PalveluHallintaView();
        majoitusvarausHallintaView = new MajoitusvarausHallintaView();
        asiakashallintaView = new AsiakashallintaView();

        MenuBar menuBar = createMenuBar();
        root.setTop(menuBar);

        Scene scene = new Scene(root, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Varausjärjestelmä");
        primaryStage.show();
    }

    private MenuBar createMenuBar() {
        MenuBar menuBar = new MenuBar();

        Menu alueMokkiMenu = new Menu("Alue ja Mökki");
        MenuItem alueMokkiMenuItem = new MenuItem("Hallinta");
        alueMokkiMenuItem.setOnAction(e -> root.setCenter(alueMokkiHallintaView));
        alueMokkiMenu.getItems().add(alueMokkiMenuItem);

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

        menuBar.getMenus().addAll(alueMokkiMenu, palveluMenu, majoitusvarausMenu, asiakasMenu);

        return menuBar;
    }
}

