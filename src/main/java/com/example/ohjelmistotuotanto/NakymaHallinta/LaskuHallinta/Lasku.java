package com.example.ohjelmistotuotanto.NakymaHallinta.LaskuHallinta;

public class Lasku {
    private int laskuId;
    private int varausId;
    private double summa;
    private double alv;

    public Lasku(int laskuId, int varausId, double summa, double alv) {
        this.laskuId = laskuId;
        this.varausId = varausId;
        this.summa = summa;
        this.alv = alv;
    }

    public int getLaskuId() {
        return laskuId;
    }

    public void setLaskuId(int laskuId) {
        this.laskuId = laskuId;
    }

    public int getVarausId() {
        return varausId;
    }

    public void setVarausId(int varausId) {
        this.varausId = varausId;
    }

    public double getSumma() {
        return summa;
    }

    public void setSumma(double summa) {
        this.summa = summa;
    }

    public double getAlv() {
        return alv;
    }

    public void setAlv(double alv) {
        this.alv = alv;
    }
}