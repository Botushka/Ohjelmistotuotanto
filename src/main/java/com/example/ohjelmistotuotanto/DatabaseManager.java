package com.example.ohjelmistotuotanto;

import com.example.ohjelmistotuotanto.NakymaHallinta.MokkiHallinta.Mokki;

import java.sql.*;

public class DatabaseManager {
    private Connection connection;
    private String url;
    private String username;
    private String password;

    public DatabaseManager(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    public void connect() throws SQLException {
        connection = DriverManager.getConnection(url, username, password);
    }

    public void disconnect() throws SQLException {
        if (connection != null) {
            connection.close();
        }
    }

    public void saveOrUpdate(Mokki mokki) throws SQLException {
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
        statement.executeUpdate();
    }

    public ResultSet retrieveData(String tableName, String[] columnNames, String whereClause) throws SQLException {
        String sql = "SELECT " + String.join(",", columnNames) + " FROM " + tableName;
        if (whereClause != null && !whereClause.isEmpty()) {
            sql += " WHERE " + whereClause;
        }
        Statement statement = connection.createStatement();
        return statement.executeQuery(sql);
    }
}
