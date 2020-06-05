package com.gui;

import com.services.database.*;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;

public class MainWindow {

    private static AgendaDB agDB;

    private static void addTab(JTabbedPane tabbedPane, String label) {
        JPanel panel = new OptionsPanel();
        tabbedPane.addTab(label, panel);
    }

    private static void initAgenda(OptionsPanel panel, Connection con, AgendaDB agendaDB) {
        OptionsButton button1 = new OptionsButton("Show Agendas");
        panel.addButton(button1);
        button1.addActionListener(actionEvent -> {
            ArrayList<String> arr = null;
            arr = agendaDB.getAgendas(con);
            panel.textArea.setText("Agendas in database:\n");
            for (String ag : arr) {
                panel.textArea.append(ag + '\n');
            }
        });

        OptionsButton button2 = new OptionsButton("Insert Agenda");
        button2.setEnabled(false);
        panel.addButton(button2);

        OptionsButton button3 = new OptionsButton("Delete Agenda");
        button3.setEnabled(false);
        panel.addButton(button3);

        OptionsButton button4 = new OptionsButton("Rename Agenda");
        button4.setEnabled(false);
        panel.addButton(button4);
    }

    private static void initContacts(OptionsPanel panel, Connection con, ContactDB contactDB) {
        OptionsButton button1 = new OptionsButton("Show Contacts");
        panel.addButton(button1);

        ArrayList<String> agendas = agDB.getAgendas(con);
        JList agList = new JList(agendas.toArray());
        agList.setSize(100, 20);
        agList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane agScroll = new JScrollPane((agList));
        panel.leftPanel.add(agScroll);

        button1.addActionListener(actionEvent -> {
            String agendaName = (String) agList.getSelectedValue();
            if (agendaName == null) {
                panel.textArea.setText("Select an agenda from the list!");
                return;
            }
            ArrayList<String> arr = contactDB.getAgendaContacts(con, agendaName);
            panel.textArea.setText("Contacts in " + agendaName +":\n");
            for (String ag : arr) {
                panel.textArea.append(ag + '\n');
            }
        });
    }

    private static void initNotes(OptionsPanel panel, Connection con, NoteDB noteDB) {
        OptionsButton button1 = new OptionsButton("Show Notes");
        panel.addButton(button1);

        ArrayList<String> agendas = agDB.getAgendas(con);
        JList agList = new JList(agendas.toArray());
        agList.setSize(100, 20);
        agList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane agScroll = new JScrollPane((agList));
        panel.leftPanel.add(agScroll);

        button1.addActionListener(actionEvent -> {
            String agendaName = (String) agList.getSelectedValue();
            if (agendaName == null) {
                panel.textArea.setText("Select an agenda from the list!");
                return;
            }
            ArrayList<String> arr = noteDB.getAgendaNotes(con, agendaName);
            panel.textArea.setText("Notes in " + agendaName +":\n");
            for (String ag : arr) {
                panel.textArea.append(ag + '\n');
            }
        });
    }

    private static void initTodos(OptionsPanel panel, Connection con, TodoDB todoDB) {
        OptionsButton button1 = new OptionsButton("Show To Dos");
        panel.addButton(button1);

        ArrayList<String> agendas = agDB.getAgendas(con);
        JList agList = new JList(agendas.toArray());
        agList.setSize(100, 20);
        agList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane agScroll = new JScrollPane((agList));
        panel.leftPanel.add(agScroll);

        button1.addActionListener(actionEvent -> {
            String agendaName = (String) agList.getSelectedValue();
            if (agendaName == null) {
                panel.textArea.setText("Select an agenda from the list!");
                return;
            }
            ArrayList<String> arr = todoDB.getAgendaTodos(con, agendaName);
            panel.textArea.setText("To Dos in " + agendaName +":\n");
            for (String ag : arr) {
                panel.textArea.append(ag + '\n');
            }
        });
    }

    private static void initEvents(OptionsPanel panel, Connection con, ScheduledTaskDB scheduledTaskDB) {
        OptionsButton button1 = new OptionsButton("Show Events");
        panel.addButton(button1);

        ArrayList<String> agendas = agDB.getAgendas(con);
        JList agList = new JList(agendas.toArray());
        agList.setSize(100, 20);
        agList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane agScroll = new JScrollPane((agList));
        panel.leftPanel.add(agScroll);

        button1.addActionListener(actionEvent -> {
            String agendaName = (String) agList.getSelectedValue();
            if (agendaName == null) {
                panel.textArea.setText("Select an agenda from the list!");
                return;
            }
            ArrayList<String> arr = scheduledTaskDB.getAgendaEvents(con, agendaName);
            panel.textArea.setText("Events in " + agendaName +":\n");
            for (String ag : arr) {
                panel.textArea.append(ag + '\n');
            }
        });
    }

    public static void init(Connection con, AgendaDB agendaDB, ContactDB contactDB, NoteDB noteDB, PrivacyDB privacyDB,
                      ScheduledTaskDB scheduledTaskDB, TodoDB todoDB) {
        agDB = agendaDB;

        JFrame mainWindow = new JFrame("Agenda Project");
        mainWindow.setSize(800, 600);

        JTabbedPane tabbedPane = new JTabbedPane();
        addTab(tabbedPane, "Agenda");
        initAgenda((OptionsPanel) tabbedPane.getComponentAt(0), con, agendaDB);

        addTab(tabbedPane, "Contacts");
        initContacts((OptionsPanel) tabbedPane.getComponentAt(1), con, contactDB);

        addTab(tabbedPane, "Notes");
        initNotes((OptionsPanel) tabbedPane.getComponentAt(2), con, noteDB);

        addTab(tabbedPane, "Todos");
        initTodos((OptionsPanel) tabbedPane.getComponentAt(3), con, todoDB);

        addTab(tabbedPane, "Events");
        initEvents((OptionsPanel) tabbedPane.getComponentAt(4), con, scheduledTaskDB);

        addTab(tabbedPane, "Journal");
        tabbedPane.setEnabledAt(5, false);

        mainWindow.add(tabbedPane);
        mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainWindow.setVisible(true);
    }
}
