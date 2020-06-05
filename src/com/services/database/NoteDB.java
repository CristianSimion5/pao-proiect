package com.services.database;

import com.services.AuditService;

import java.sql.*;
import java.util.ArrayList;
import java.util.Map;

public class NoteDB {

    private static NoteDB instance = null;

    public static NoteDB getInstance() {
        if (instance == null)
            instance = new NoteDB();
        return instance;
    }

    public void showAgendaNotes(Connection con, String agendaName) {
        try {
            String query = "SELECT title " +
                    "FROM notes JOIN agendas USING(agenda_id) WHERE name=?";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setString(1, agendaName);
            ResultSet rs = pstmt.executeQuery();
            System.out.println("Notes in agenda " + agendaName + ":");
            while (rs.next()) {
                System.out.println(rs.getString("title"));
            }
        } catch (SQLException e) {
            System.out.println("Eroare SQL!");
            System.out.println(e);
        }
    }

    public ArrayList<String> getAgendaNotes(Connection con, String agendaName) {
        AuditService.writeAudit("getAgendaNotes", Thread.currentThread().getName());
        ArrayList<String> arr = new ArrayList<>();
        try {
            String query = "SELECT title " +
                    "FROM notes JOIN agendas USING(agenda_id) WHERE name=?";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setString(1, agendaName);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                arr.add(rs.getString("title"));
            }
        } catch (SQLException e) {
            System.out.println("Eroare SQL!");
            System.out.println(e);
        }
        return arr;
    }

    public void insertNote(Connection con, String agendaName, String title) {
        try {
            String query = "INSERT INTO notes(title, agenda_id) VALUES(?, " +
                    "(SELECT agenda_id FROM agendas WHERE name=?))";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setString(1, title);
            pstmt.setString(2, agendaName);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Eroare SQL!");
            System.out.println(e);
        }
    }

    public void deleteNote(Connection con, String title) {
        try {
            String query = "DELETE FROM notes WHERE title=?";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setString(1, title);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Eroare SQL!");
            System.out.println(e);
        }
    }

    public void renameNote(Connection con, String oldTitle, String newTitle) {
        try {
            String query = "UPDATE notes set title=? WHERE title=?";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setString(1, newTitle);
            pstmt.setString(2, oldTitle);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Eroare SQL!");
            System.out.println(e);
        }
    }

    public void updateNote(Connection con, String title, String text) {
        try {
            String query = "UPDATE notes SET text=? WHERE title=?";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setString(1, text);
            pstmt.setString(2, title);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Eroare SQL!");
            System.out.println(e);
        }
    }
}
