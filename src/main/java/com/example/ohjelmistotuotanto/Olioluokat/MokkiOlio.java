package com.example.ohjelmistotuotanto.Olioluokat;

public class MokkiOlio {
    private int mokki_id;
    private int alue_id;
    private int postinro;
    private String mokkinimi;
    private String katuosoite;
    private double hinta;
    private String kuvaus;
    private int henkilomaara;
    private String varustelu;

    public MokkiOlio(int mokki_id, int alue_id, int postinro, String mokkinimi, String katuosoite, double hinta, String kuvaus, int henkilomaara, String varustelu) {
        this.mokki_id = mokki_id;
        this.alue_id = alue_id;
        this.postinro = postinro;
        this.mokkinimi = mokkinimi;
        this.katuosoite = katuosoite;
        this.hinta = hinta;
        this.kuvaus = kuvaus;
        this.henkilomaara = henkilomaara;
        this.varustelu = varustelu;
    }

    public MokkiOlio() {

    }

    public int getMokki_id() {
        return mokki_id;
    }

    public void setMokki_id(int mokki_id) {
        this.mokki_id = mokki_id;
    }

    public int getAlue_id() {
        return alue_id;
    }

    public void setAlue_id(int alue_id) {
        this.alue_id = alue_id;
    }

    public int getPostinro() {
        return postinro;
    }

    public void setPostinro(int postinro) {
        this.postinro = postinro;
    }

    public String getMokkinimi() {
        return mokkinimi;
    }

    public void setMokkinimi(String mokkinimi) {
        this.mokkinimi = mokkinimi;
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

    public String getKuvaus() {
        return kuvaus;
    }

    public void setKuvaus(String kuvaus) {
        this.kuvaus = kuvaus;
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
}