package com.services.database;

import com.services.AuditService;

import java.sql.*;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Map;

public class ScheduledTaskDB {

    private static ScheduledTaskDB instance = null;

    public static ScheduledTaskDB getInstance() {
        if (instance == null)
            instance = new ScheduledTaskDB();
        return instance;
    }

    public void showAgendaEvents(Connection con, String agendaName) {
        try {
            String query =
                "SELECT e.name, e.date, CONCAT(c.first_name, \" \", c.last_name) \"partner_name\"" +
                "FROM events e LEFT JOIN agendas a USING(agenda_id) " +
                    "LEFT JOIN contacts c ON e.partner_id = c.contact_id WHERE a.name=?" +
                "ORDER BY e.date";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setString(1, agendaName);
            ResultSet rs = pstmt.executeQuery();

            System.out.println("Events in agenda " + agendaName + ":");
            while (rs.next()) {
                String pname = rs.getString("partner_name");
                if (pname == null) {
                    System.out.println(rs.getString("e.name") + ", Scheduled for " + rs.getTimestamp("e.date"));
                } else {
                    System.out.println(rs.getString("e.name") + ", Scheduled for " + rs.getTimestamp("e.date") + " with contact " + pname);
                }
            }
        } catch (SQLException e) {
            System.out.println("Eroare SQL!");
            System.out.println(e);
        }
    }

    public ArrayList<String> getAgendaEvents(Connection con, String agendaName) {
        AuditService.writeAudit("getAgendaEvents", Thread.currentThread().getName());
        ArrayList<String> arr = new ArrayList<>();
        try {
            String query =
                    "SELECT e.name, e.date, CONCAT(c.first_name, \" \", c.last_name) \"partner_name\"" +
                            "FROM events e LEFT JOIN agendas a USING(agenda_id) " +
                            "LEFT JOIN contacts c ON e.partner_id = c.contact_id WHERE a.name=?" +
                            "ORDER BY e.date";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setString(1, agendaName);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                String pname = rs.getString("partner_name");
                if (pname == null) {
                    arr.add(rs.getString("e.name") + ", Scheduled for " + rs.getTimestamp("e.date"));
                } else {
                    arr.add(rs.getString("e.name") + ", Scheduled for " + rs.getTimestamp("e.date") + " with contact " + pname);
                }
            }
        } catch (SQLException e) {
            System.out.println("Eroare SQL!");
            System.out.println(e);
        }
        return arr;
    }

    public void insertEvent(Connection con, String agendaName, String name, GregorianCalendar gc) {
        try {
            String query = "INSERT INTO events(name, date, agenda_id) " +
                    "VALUES(?, ?, " +
                    "( SELECT agenda_id" +
                    "  FROM agendas WHERE name=?))";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setString(1, name);
            pstmt.setTimestamp(2, new Timestamp(gc.getTimeInMillis()));
            pstmt.setString(3, agendaName);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Eroare SQL!");
            System.out.println(e);
        }
    }

    public void insertEvent(Connection con, String agendaName, String name, GregorianCalendar gc, String partnerName) {
        try {
            String query;
            query = "INSERT INTO events(name, date, agenda_id, partner_id) " +
                    "VALUES(?, ?, " +
                    "( SELECT agenda_id" +
                    "  FROM agendas WHERE name=?)," +
                    "( SELECT contact_id" +
                    "  FROM contacts WHERE CONCAT(first_name, \" \", last_name) = ?)" +
                    ")";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setString(1, name);
            pstmt.setTimestamp(2, new Timestamp(gc.getTimeInMillis()));
            pstmt.setString(3, agendaName);
            pstmt.setString(4, partnerName);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Eroare SQL!");
            System.out.println(e);
        }
    }

    public void deleteEvent(Connection con, String name) {
        try {
            String query = "DELETE FROM events WHERE name=?";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setString(1, name);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Eroare SQL!");
            System.out.println(e);
        }
    }

    public void updateEvent(Connection con, String oldName, String newName) {
        try {
            String query = "UPDATE events SET name=? WHERE name=?";
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
