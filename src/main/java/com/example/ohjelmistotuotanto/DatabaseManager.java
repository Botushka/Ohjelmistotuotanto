package com.example.ohjelmistotuotanto;

import com.example.ohjelmistotuotanto.NakymaHallinta.Alue.AlueOlio;
import com.example.ohjelmistotuotanto.NakymaHallinta.AsiakasHallinta.Asiakas;
import com.example.ohjelmistotuotanto.NakymaHallinta.LaskuHallinta.Lasku;
import com.example.ohjelmistotuotanto.NakymaHallinta.MokkiHallinta.Mokki;
import com.example.ohjelmistotuotanto.Olioluokat.Palvelu;

import java.sql.*;

public class DatabaseManager {
    private Connection connection;
    private String url;
    private String username;
    private String password;

    public DatabaseManager(String url, String username, String password) {
        this.url = "jdbc:mysql://localhost:3306/vn";
        this.username = "root";
        this.password = "";
    }

    public void connect() throws SQLException {
        this.url = "jdbc:mysql://localhost:3306/vn";
        this.username = "root";
        this.password = "";
        connection = DriverManager.getConnection(url, username, password);
    }

    public void disconnect() throws SQLException {
        if (connection != null) {
            connection.close();
        }
    }

    public void saveOrUpdate(Mokki mokki) throws SQLException {
        connect();

        PreparedStatement statement;
        if (mokki.getMokkiId() == null) {
            String sql = "INSERT INTO Mokki (alue_id, postinro, mokkinimi, katuosoite, hinta, henkilomaara, varustelu, kuvaus) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            statement = connection.prepareStatement(sql);
        } else {
            String sql = "UPDATE Mokki SET alue_id = ?, postinro = ?, mokkinimi = ?, katuosoite = ?, hinta = ?, henkilomaara = ?, varustelu = ?, kuvaus = ? WHERE mokki_id = ?";
            statement = connection.prepareStatement(sql);
            statement.setInt(9, Integer.parseInt(mokki.getMokkiId()));
        }
        statement.setInt(1, mokki.getAlueId());
        statement.setInt(2, mokki.getPostinro());
        statement.setString(3, mokki.getNimi());
        statement.setString(4, mokki.getKatuosoite());
        statement.setDouble(5, mokki.getHinta());
        statement.setInt(6, mokki.getHenkilomaara());
        statement.setString(7, mokki.getVarustelu());
        statement.setString(8, mokki.getKuvaus());
        int affectedRows = statement.executeUpdate();
        if (affectedRows == 0) {
            throw new SQLException("Jokin meni pieleen tiedon tallentamisessa tietokantaan");
        }
        disconnect();
    }

    public void updatePalvelu(Palvelu palvelu) throws SQLException {
        connect();

        String sql = "UPDATE palvelu SET palvelu_id = ?, alue_id = ?, nimi = ?, tyyppi = ?, kuvaus = ?, hinta = ?, alv = ? WHERE palvelu_id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, palvelu.getPalvelu_id());
        preparedStatement.setInt(2, palvelu.getAlue_id());
        preparedStatement.setString(3, palvelu.getNimi());
        preparedStatement.setInt(4, palvelu.getTyyppi());
        preparedStatement.setString(5, palvelu.getKuvaus());
        preparedStatement.setDouble(6, palvelu.getHinta());
        preparedStatement.setDouble(7, palvelu.getAlv());
        preparedStatement.setInt(8, palvelu.getPalvelu_id());
        int affectedRows = preparedStatement.executeUpdate();
        if (affectedRows == 0) {
            throw new SQLException("Jokin meni pieleen tiedon tallentamisessa tietokantaan");
        }
        disconnect();
    }
    public void updateLasku(Lasku lasku) throws SQLException {
        connect();

        String sql = "UPDATE lasku SET lasku_id = ?, varaus_id = ?, summa = ?, alv = ? WHERE lasku_id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, lasku.getLaskuId());
        preparedStatement.setInt(2, lasku.getVarausId());
        preparedStatement.setDouble(3, lasku.getSumma());
        preparedStatement.setDouble(4, lasku.getAlv());
        preparedStatement.setInt(5, lasku.getLaskuId());
        int affectedRows = preparedStatement.executeUpdate();
        if (affectedRows == 0) {
            throw new SQLException("Jokin meni pieleen tiedon tallentamisessa tietokantaan");
        }
        disconnect();
    }

    public void updateAsiakas(Asiakas asiakas) throws SQLException {
        connect();

        String sql = "UPDATE asiakas SET asiakas_id = ?, postinro = ?, etunimi = ?, sukunimi = ?, lahiosoite = ?, email = ?, puhelinnro = ? WHERE asiakas_id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, asiakas.getAsiakasId());
        preparedStatement.setInt(2, asiakas.getPostiNro());
        preparedStatement.setString(3, asiakas.getEtunimi());
        preparedStatement.setString(4, asiakas.getSukunimi());
        preparedStatement.setString(5, asiakas.getLahiosoite());
        preparedStatement.setString(6, asiakas.getEmail());
        preparedStatement.setInt(7, asiakas.getPuhnumero());
        preparedStatement.setInt(8, asiakas.getAsiakasId());
        int affectedRows = preparedStatement.executeUpdate();
        if (affectedRows == 0) {
            throw new SQLException("Jokin meni pieleen tiedon tallentamisessa tietokantaan");
        }
        disconnect();
    }
    public void updateAlue(AlueOlio alue) throws SQLException {
        connect();

        String sql = "UPDATE alue SET alue_id = ?, nimi = ? WHERE alue_id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, alue.getAlue_id());
        preparedStatement.setString(2, alue.getArea_nimi());
        preparedStatement.setInt(3, alue.getAlue_id());
        int affectedRows = preparedStatement.executeUpdate();
        if (affectedRows == 0) {
            throw new SQLException("Jokin meni pieleen tiedon tallentamisessa tietokantaan");
        }
        disconnect();
    }
    public ResultSet retrieveData(String tableName, String[] columnNames) throws SQLException {
        connect();

        if (connection == null) {
            throw new SQLException("Database connection is not established.");
        }

        String sql = "SELECT " + String.join(",", columnNames) + " FROM " + tableName;

        Statement statement = connection.createStatement();
        return statement.executeQuery(sql);
    }
}
