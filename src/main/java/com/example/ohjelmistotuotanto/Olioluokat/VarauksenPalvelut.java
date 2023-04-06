package com.example.ohjelmistotuotanto.Olioluokat;

public class VarauksenPalvelut {
    private int varaus_id;
    private int palvelu_id;
    private int lkm;

    public VarauksenPalvelut(int varaus_id, int palvelu_id, int lkm) {
        this.varaus_id = varaus_id;
        this.palvelu_id = palvelu_id;
        this.lkm = lkm;
    }

    public int getVaraus_id() {
        return varaus_id;
    }

    public void setVaraus_id(int varaus_id) {
        this.varaus_id = varaus_id;
    }

    public int getPalvelu_id() {
        return palvelu_id;
    }

    public void setPalvelu_id(int palvelu_id) {
        this.palvelu_id = palvelu_id;
    }

    public int getLkm() {
        return lkm;
    }

    public void setLkm(int lkm) {
        this.lkm = lkm;
    }
}