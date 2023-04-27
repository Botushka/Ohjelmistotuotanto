package com.example.ohjelmistotuotanto;

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
