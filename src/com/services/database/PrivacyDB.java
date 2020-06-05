package com.services.database;

import com.services.AuditService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PrivacyDB {

    private static PrivacyDB instance = null;

    public static PrivacyDB getInstance() {
        if (instance == null)
            instance = new PrivacyDB();
        return instance;
    }

    private boolean checkPassword(Connection con, String agendaName, String password) {
        try {
            String query =  "SELECT password " +
                    "FROM privates JOIN agendas USING(agenda_id) WHERE name=?";
            PreparedStatement pass = con.prepareStatement(query);
            pass.setString(1, agendaName);
            ResultSet rs = pass.executeQuery();
            boolean ok = false;
            while (rs.next()) {
                if (password.equals(rs.getString("password")))
                    return true;
            }
            System.out.println("Parola incorecta sau agenda nu exista in baza de date!");
        } catch (SQLException e) {
            System.out.println("Eroare SQL!");
            System.out.println(e);
        }
        return false;
    }

    public void showAgendaJournal(Connection con, String agendaName, String password) {
        try {
            if (!checkPassword(con, agendaName, password)) {
                return;
            }
            String query = "SELECT journal " +
                    "FROM privates JOIN agendas USING(agenda_id) WHERE name=?";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setString(1, agendaName);
            ResultSet rs = pstmt.executeQuery();
            System.out.println("Journal in " + agendaName + ":");
            while (rs.next()) {
                System.out.println(rs.getString("journal"));
            }
        } catch (SQLException e) {
            System.out.println("Eroare SQL!");
            System.out.println(e);
        }
    }

    public void updateJournal(Connection con, String agendaName, String password, String text) {
        try {
            if (!checkPassword(con, agendaName, password)) {
                return;
            }
            String query = "UPDATE privates SET journal=? WHERE agenda_id=" +
                    "(SELECT agenda_id FROM agendas WHERE name=?)";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setString(1, text);
            pstmt.setString(2, agendaName);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Eroare SQL!");
            System.out.println(e);
        }
    }

    public void changePassword(Connection con, String agendaName, String oldPassword, String newPassword) {
        AuditService.writeAudit("changePassword", Thread.currentThread().getName());
        try {
            if (!checkPassword(con, agendaName, oldPassword)) {
                return;
            }
            String query = "UPDATE privates set password=? WHERE agenda_id=" +
                    "(SELECT agenda_id FROM agendas WHERE name=?)";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setString(1, newPassword);
            pstmt.setString(2, agendaName);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Eroare SQL!");
            System.out.println(e);
        }
    }
}
