package com.services.database;

import com.services.AuditService;

import java.sql.*;
import java.util.ArrayList;
import java.util.Map;

public class ContactDB {

    private static ContactDB instance = null;

    public static ContactDB getInstance() {
        if (instance == null)
            instance = new ContactDB();
        return instance;
    }

    public void showAgendaContacts(Connection con, String agendaName) {
        try {
            String query = "SELECT CONCAT(first_name, ' ', last_name) \"full_name\" " +
                    "FROM contacts JOIN agendas USING(agenda_id) WHERE name=?" +
                    "ORDER BY first_name , last_name";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setString(1, agendaName);
            ResultSet rs = pstmt.executeQuery();
            System.out.println("Contacts in agenda " + agendaName + ":");
            while (rs.next()) {
                System.out.println(rs.getString("full_name"));
            }
        } catch (SQLException e) {
            System.out.println("Eroare SQL!");
            System.out.println(e);
        }
    }

    public ArrayList<String> getAgendaContacts(Connection con, String agendaName) {
        AuditService.writeAudit("getAgendaContacts", Thread.currentThread().getName());
        ArrayList<String> arr = new ArrayList<>();
        try {
            String query = "SELECT CONCAT(first_name, ' ', last_name) \"full_name\" " +
                    "FROM contacts JOIN agendas USING(agenda_id) WHERE name=?" +
                    "ORDER BY first_name , last_name";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setString(1, agendaName);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                arr.add(rs.getString("full_name"));
            }
            return arr;
        } catch (SQLException e) {
            System.out.println("Eroare SQL!");
            System.out.println(e);
        }
        return arr;
    }

    public void insertContact(Connection con, String agendaName, String firstName, String lastName,
                              Map<String, String> kwargs) {
        try {
            String query = "INSERT INTO contacts(first_name, last_name, nr_tel1, nr_tel2, email1, email2, agenda_id) " +
                    "VALUES(?, ?, ?, ?, ?, ?, " +
                    "( SELECT agenda_id" +
                    "  FROM agendas WHERE name=?))";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setString(1, firstName);
            pstmt.setString(2, lastName);
            pstmt.setString(3, kwargs.getOrDefault("tel1", null));
            pstmt.setString(4, kwargs.getOrDefault("tel2", null));
            pstmt.setString(5, kwargs.getOrDefault("email1", null));
            pstmt.setString(6, kwargs.getOrDefault("email2", null));
            pstmt.setString(7, agendaName);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Eroare SQL!");
            System.out.println(e);
        }
    }

    public void deleteContact(Connection con, String firstName, String lastName) {
        try {
            String query = "DELETE FROM contacts WHERE first_name=? AND last_name=?";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setString(1, firstName);
            pstmt.setString(2, lastName);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Eroare SQL!");
            System.out.println(e);
        }
    }

    public void renameContact(Connection con, String oldFirstName, String oldLastName,
                              String newFirstName, String newLastName) {
        try {
            String query = "UPDATE contacts " +
                    "set first_name=?, last_name=? " +
                    "WHERE first_name=? AND last_name=?";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setString(1, newFirstName);
            pstmt.setString(2, newLastName);
            pstmt.setString(3, oldFirstName);
            pstmt.setString(4, oldLastName);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Eroare SQL!");
            System.out.println(e);
        }
    }

    public void updateContact(Connection con, String firstName, String lastName,
                              Map<String, String> kwargs) {
        try {
            String query = "UPDATE contacts " +
                    "set nr_tel1=?, nr_tel2=?, email1=?, email2=? " +
                    "WHERE first_name=? AND last_name=?";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setString(1, kwargs.getOrDefault("tel1", null));
            pstmt.setString(2, kwargs.getOrDefault("tel2", null));
            pstmt.setString(3, kwargs.getOrDefault("email1", null));
            pstmt.setString(4, kwargs.getOrDefault("email2", null));
            pstmt.setString(5, firstName);
            pstmt.setString(6, lastName);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Eroare SQL!");
            System.out.println(e);
        }
    }
}
