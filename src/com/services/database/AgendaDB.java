package com.services.database;

import com.services.AuditService;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

public class AgendaDB {

    private static AgendaDB instance = null;

    private AgendaDB() {}

    public static AgendaDB getInstance() {
        if (instance == null)
            instance = new AgendaDB();
        return instance;
    }

    public void showAgendas(Connection con) {
        AuditService.writeAudit("showAgendas", Thread.currentThread().getName());
        try {
            Statement stmt = con.createStatement();
            String query = "SELECT name FROM agendas";
            ResultSet rs = stmt.executeQuery(query);
            System.out.println("Agendas in database:");
            while (rs.next()) {
                System.out.println(rs.getString("name"));
            }
        } catch (SQLException e) {
            System.out.println("Eroare SQL!");
            System.out.println(e);
        }
    }

    public ArrayList<String> getAgendas(Connection con) {
        AuditService.writeAudit("getAgendas", Thread.currentThread().getName());
        ArrayList<String> arr = new ArrayList<>();
        try {
            Statement stmt = con.createStatement();
            String query = "SELECT name FROM agendas";
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                arr.add(rs.getString("name"));
            }
        } catch (SQLException e) {
            System.out.println("Eroare SQL!");
            System.out.println(e);
        }
        return arr;
    }

    public void insertAgenda(Connection con, String agendaName, String password) {
        AuditService.writeAudit("insertAgenda", Thread.currentThread().getName());
        try {
            String query = "INSERT INTO agendas(name) VALUES(?)";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setString(1, agendaName);
            pstmt.executeUpdate();

            query = "INSERT INTO privates(password, agenda_id)" +
                    "VALUES (?, (SELECT agenda_id FROM agendas WHERE name=?))";
            pstmt = con.prepareStatement(query);
            pstmt.setString(1, password);
            pstmt.setString(2, agendaName);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Eroare SQL!");
            System.out.println(e);
        }
    }

    public void deleteAgenda(Connection con, String agendaName) {
        AuditService.writeAudit("deleteAgenda", Thread.currentThread().getName());
        try {
            String query = "DELETE FROM agendas WHERE name=?";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setString(1, agendaName);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Eroare SQL!");
            System.out.println(e);
        }
    }

    public void renameAgenda(Connection con, String oldName, String newName) {
        AuditService.writeAudit("renameAgenda", Thread.currentThread().getName());
        try {
            String query = "UPDATE agendas set name=? WHERE name=?";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setString(1, newName);
            pstmt.setString(2, oldName);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Eroare SQL!");
            System.out.println(e);
        }
    }
}
