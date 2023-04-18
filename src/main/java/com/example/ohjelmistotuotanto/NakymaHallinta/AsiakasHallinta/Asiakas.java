package com.example.ohjelmistotuotanto.NakymaHallinta.AsiakasHallinta;

public class Asiakas
{
    private int asiakasId;
    private int postiNro;
    private String etunimi;
    private String sukunimi;
    private String lahiosoite;
    private String email;
    private int puhnumero;

    public Asiakas(int asiakasId, int postiNro, String etunimi, String sukunimi, String lahiosoite, String email, int puhnumero)
    {
        this.asiakasId = asiakasId;
        this.postiNro = postiNro;
        this.etunimi = etunimi;
        this.sukunimi = sukunimi;
        this.lahiosoite = lahiosoite;
        this.email = email;
        this.puhnumero = puhnumero;

    }


    public int getAsiakasId()
    {
        return asiakasId;
    }

    public void setAsiakasId(int asiakasId)
    {
        this.asiakasId = asiakasId;
    }

    public int getPostiNro()
    {
        return postiNro;
    }

    public void setPostiNro(int postiNro)
    {
        this.postiNro = postiNro;
    }

    public String getEtunimi()
    {
        return etunimi;
    }

    public void setEtunimi(String etunimi)
    {
        this.etunimi = etunimi;
    }

    public String getSukunimi()
    {
        return sukunimi;
    }

    public void setSukunimi(String sukunimi)
    {
        this.sukunimi = sukunimi;
    }

    public String getLahiosoite()
    {
        return lahiosoite;
    }

    public void setLahiosoite(String lahiosoite)
    {
        this.lahiosoite = lahiosoite;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public int getPuhnumero()
    {
        return puhnumero;
    }

    public void setPuhnumero(int puhnumero)
    {
        this.puhnumero = puhnumero;
    }
}
