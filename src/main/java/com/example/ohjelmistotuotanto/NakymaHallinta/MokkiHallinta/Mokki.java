package com.example.ohjelmistotuotanto.NakymaHallinta.MokkiHallinta;

import com.example.ohjelmistotuotanto.Olioluokat.MokkiOlio;

public class Mokki extends MokkiOlio {
    private String id;
    private String alueId;
    private String postinro;
    private String nimi;
    private String katuosoite;
    private String hinta;
    private String kuvaus;
    private String henkilomaara;
    private String varustelu;

    public Mokki() {
        this.id = String.valueOf(0);
        this.alueId = String.valueOf(0);
        this.postinro = "";
        this.nimi = "";
        this.katuosoite = "";
        this.hinta = String.valueOf(0);
        this.kuvaus = "";
        this.henkilomaara = String.valueOf(0);
        this.varustelu = "";
    }

    public Mokki(String id, int alueId, int postinro, String nimi, String katuosoite, double hinta, String kuvaus, int henkilomaara, String varustelu) {
        super();
    }

    // Getterit ja setterit

    @Override
    public String toString() {
        return "ID: " + id + " | Alue ID: " + alueId + " | Postinumero: " + postinro +
                " | Nimi: " + nimi + " | Katuosoite: " + katuosoite + " | Hinta: " + hinta +
                " | Kuvaus: " + kuvaus + " | Henkilömäärä: " + henkilomaara +
                " | Varustelu: " + varustelu;
    }
}