package com.services.database;

import com.services.AuditService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class TodoDB {

    private static TodoDB instance = null;

    private TodoDB() {}

    public static TodoDB getInstance() {
        if (instance == null)
            instance = new TodoDB();
        return instance;
    }

    public void showAgendaTodos(Connection con, String agendaName) {
        try {
            String query = "SELECT task_name, done " +
                    "FROM todos JOIN agendas USING(agenda_id) WHERE name=?";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setString(1, agendaName);
            ResultSet rs = pstmt.executeQuery();
            System.out.println("To Dos in agenda " + agendaName + ":");
            while (rs.next()) {
                System.out.println(rs.getString("task_name") + ", " +
                        (rs.getBoolean("done") == true ? "done" : "not done"));
            }
        } catch (SQLException e) {
            System.out.println("Eroare SQL!");
            System.out.println(e);
        }
    }

    public ArrayList<String> getAgendaTodos(Connection con, String agendaName) {
        AuditService.writeAudit("getAgendaTodos", Thread.currentThread().getName());
        ArrayList<String> arr = new ArrayList<>();
        try {
            String query = "SELECT task_name, done " +
                    "FROM todos JOIN agendas USING(agenda_id) WHERE name=?";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setString(1, agendaName);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                arr.add(rs.getString("task_name") + ", " +
                        (rs.getBoolean("done") == true ? "done" : "not done"));
            }
        } catch (SQLException e) {
            System.out.println("Eroare SQL!");
            System.out.println(e);
        }
        return arr;
    }

    public void insertTodo(Connection con, String agendaName, String taskName) {
        try {
            String query = "INSERT INTO todos(task_name, agenda_id) VALUES(?, " +
                    "(SELECT agenda_id FROM agendas WHERE name=?))";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setString(1, taskName);
            pstmt.setString(2, agendaName);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Eroare SQL!");
            System.out.println(e);
        }
    }

    public void deleteTodo(Connection con, String taskName) {
        try {
            String query = "DELETE FROM todos WHERE task_name=?";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setString(1, taskName);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Eroare SQL!");
            System.out.println(e);
        }
    }

    public void renameTodo(Connection con, String oldName, String newName) {
        try {
            String query = "UPDATE todos set task_name=? WHERE task_name=?";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setString(1, newName);
            pstmt.setString(2, oldName);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Eroare SQL!");
            System.out.println(e);
        }
    }

    public void toggleTodo(Connection con, String taskName) {
        try {
            String query = "UPDATE todos set done=NOT(done) WHERE task_name=?";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setString(1, taskName);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Eroare SQL!");
            System.out.println(e);
        }
    }
}
