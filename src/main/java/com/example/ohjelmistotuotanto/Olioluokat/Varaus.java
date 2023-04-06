package com.example.ohjelmistotuotanto.Olioluokat;

import java.util.Date;

public class Varaus {
    private int varaus_id;
    private int asiakas_id;
    private int mokki_mokki_id;
    private Date varattu_pvm;
    private Date vahvistus_pvm;

    public Varaus(int varaus_id, int asiakas_id, int mokki_mokki_id, Date varattu_pvm, Date vahvistus_pvm, Date varattu_alkupvm, Date varattu_loppupvm) {
        this.varaus_id = varaus_id;
        this.asiakas_id = asiakas_id;
        this.mokki_mokki_id = mokki_mokki_id;
        this.varattu_pvm = varattu_pvm;
        this.vahvistus_pvm = vahvistus_pvm;
        this.varattu_alkupvm = varattu_alkupvm;
        this.varattu_loppupvm = varattu_loppupvm;
    }

    private Date varattu_alkupvm;
    private Date varattu_loppupvm;

    public int getVaraus_id() {
        return varaus_id;
    }

    public void setVaraus_id(int varaus_id) {
        this.varaus_id = varaus_id;
    }

    public int getAsiakas_id() {
        return asiakas_id;
    }

    public void setAsiakas_id(int asiakas_id) {
        this.asiakas_id = asiakas_id;
    }

    public int getMokki_mokki_id() {
        return mokki_mokki_id;
    }

    public void setMokki_mokki_id(int mokki_mokki_id) {
        this.mokki_mokki_id = mokki_mokki_id;
    }

    public Date getVarattu_pvm() {
        return varattu_pvm;
    }

    public void setVarattu_pvm(Date varattu_pvm) {
        this.varattu_pvm = varattu_pvm;
    }

    public Date getVahvistus_pvm() {
        return vahvistus_pvm;
    }

    public void setVahvistus_pvm(Date vahvistus_pvm) {
        this.vahvistus_pvm = vahvistus_pvm;
    }

    public Date getVarattu_alkupvm() {
        return varattu_alkupvm;
    }

    public void setVarattu_alkupvm(Date varattu_alkupvm) {
        this.varattu_alkupvm = varattu_alkupvm;
    }

    public Date getVarattu_loppupvm() {
        return varattu_loppupvm;
    }

    public void setVarattu_loppupvm(Date varattu_loppupvm) {
        this.varattu_loppupvm = varattu_loppupvm;
    }
}