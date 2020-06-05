package com.services.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class General {

    public static Connection getConnection() throws SQLException {
        System.out.println("Se incearca conexiunea la baza de date...");
        String url = "jdbc:mysql://localhost:3306/mydb?serverTimezone=UTC";
        Connection con = DriverManager.getConnection(url, "root", "root");
        System.out.println("Conexiune reusita");
        return con;
    }
}
