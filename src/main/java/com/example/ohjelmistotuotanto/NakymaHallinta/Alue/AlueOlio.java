package com.example.ohjelmistotuotanto.NakymaHallinta.Alue;

public class AlueOlio {
    private int alue_id;
    private String area_nimi;

    public AlueOlio(int alue_id, String area_nimi) {
        this.alue_id = alue_id;
        this.area_nimi = area_nimi;
    }

    public int getAlue_id() {
        return alue_id;
    }

    public void setAlue_id(int alue_id) {
        this.alue_id = alue_id;
    }

    public String getArea_nimi() {
        return area_nimi;
    }

    public void setArea_nimi(String area_nimi) {
        this.area_nimi = area_nimi;
    }
}