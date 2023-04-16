package com.example.ohjelmistotuotanto.NakymaHallinta.MokkiHallinta;

public class Mokki {
    private String mokkiId;
    private int alueId;
    private int postinro;
    private String nimi;
    private String katuosoite;
    private double hinta;
    private int henkilomaara;
    private String varustelu;
    private String kuvaus;

    public Mokki(String mokkiId, int alueId, int postinro, String nimi, String katuosoite, double hinta, int henkilomaara, String varustelu, String kuvaus) {
        this.mokkiId = mokkiId;
        this.alueId = alueId;
        this.postinro = postinro;
        this.nimi = nimi;
        this.katuosoite = katuosoite;
        this.hinta = hinta;
        this.henkilomaara = henkilomaara;
        this.varustelu = varustelu;
        this.kuvaus = kuvaus;
    }

    public String getMokkiId() {
        return mokkiId;
    }

    public void setMokkiId(String mokkiId) {
        this.mokkiId = mokkiId;
    }

    public int getAlueId() {
        return alueId;
    }

    public void setAlueId(int alueId) {
        this.alueId = alueId;
    }

    public int getPostinro() {
        return postinro;
    }

    public void setPostinro(int postinro) {
        this.postinro = postinro;
    }

    public String getNimi() {
        return nimi;
    }

    public void setNimi(String nimi) {
        this.nimi = nimi;
    }

    public String getKatuosoite() {
        return katuosoite;
    }

    public void setKatuosoite(String katuosoite) {
        this.katuosoite = katuosoite;
    }

    public double getHinta() {
        return hinta;
    }

    public void setHinta(double hinta) {
        this.hinta = hinta;
    }

    public int getHenkilomaara() {
        return henkilomaara;
    }

    public void setHenkilomaara(int henkilomaara) {
        this.henkilomaara = henkilomaara;
    }

    public String getVarustelu() {
        return varustelu;
    }

    public void setVarustelu(String varustelu) {
        this.varustelu = varustelu;
    }

    public String getKuvaus() {
        return kuvaus;
    }

    public void setKuvaus(String kuvaus) {
        this.kuvaus = kuvaus;
    }

    @Override
    public String toString() {
        return varustelu;
    }
}