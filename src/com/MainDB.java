package com;

import com.gui.MainWindow;
import com.services.database.*;

import java.sql.Connection;
import java.sql.SQLException;

public class MainDB {

    public static void main(String[] args) throws SQLException {
        try (Connection con = General.getConnection()) {
            AgendaDB agendaDB = AgendaDB.getInstance();
            ContactDB contactDB = ContactDB.getInstance();
            NoteDB noteDB = NoteDB.getInstance();
            ScheduledTaskDB scheduledTaskDB = ScheduledTaskDB.getInstance();
            TodoDB todoDB = TodoDB.getInstance();
            PrivacyDB privacyDB = PrivacyDB.getInstance();

//            agendaDB.insertAgenda(con, "Jim's Agenda");
//            agendaDB.renameAgenda(con, "Jim's Agenda", "Tim's Agenda");
//            agendaDB.deleteAgenda(con, "Jim's Agenda");
//            agendaDB.showAgendas(con);

//            contactDB.showAgendaContacts(con, "My agenda");
//            contactDB.insertContact(con, "My agenda", "Morgan", "Dawson",
//                    Map.of("tel1", "123456-789", "email1", "morgan42@email.com"));
//            contactDB.insertContact(con, "Tim's agenda", "Jordan", "Jones",
//                    Collections.emptyMap());
//            contactDB.insertContact(con, "Tim's agenda", "Delete", "Example",
//                    Collections.emptyMap());
//            contactDB.deleteContact(con, "Delete", "Example");
//            contactDB.insertContact(con, "John's agenda", "Update", "Example",
//                    Collections.emptyMap());
//            contactDB.renameContact(con, "Update", "Example", "Example", "Updated");
//            contactDB.updateContact(con, "Example", "Updated",
//                    Map.of("tel1", "abc12345", "tel2", "bcd444"));

//            noteDB.insertNote(con, "My agenda","Chocolate cake recipe");
//            noteDB.renameNote(con, "Chocolate cake recipe", "Interesting websites");
//            noteDB.updateNote(con, "Interesting websites", "youtube.com\n" +
//                    "duckduckgo.com\n" +
//                    "stackoverflow.com");
//            noteDB.showAgendaNotes(con, "My agenda");
//            noteDB.deleteNote(con, "Chocolate cake recipe");
//            noteDB.showAgendaNotes(con, "My agenda");

//            scheduledTaskDB.insertEvent(con, "My agenda", "Visit France", new GregorianCalendar(2020, 11, 10, 11, 0));
//            scheduledTaskDB.insertEvent(con, "My agenda", "Coffee with Johnny",
//                    new GregorianCalendar(2019, 4, 23, 9, 0), "Johnny Logger");
//            scheduledTaskDB.showAgendaEvents(con, "My Agenda");
//            scheduledTaskDB.updateEvent(con, "Coffee with Johnny", "Tea with Johnny");
//            scheduledTaskDB.deleteEvent(con, "Coffee with Johnny");

//            todoDB.insertTodo(con, "My Agenda", "Wash my hands");
//            todoDB.showAgendaTodos(con, "My Agenda");
//            todoDB.deleteTodo(con, "Wash my hands");
//            todoDB.renameTodo(con, "Wash my hands", "Eat healthy");
//            todoDB.toggleTodo(con, "Wash my hands");
//            todoDB.showAgendaTodos(con, "My Agenda");

//            agendaDB.insertAgenda(con, "New Agenda", "password");
//            privacyDB.showAgendaJournal(con, "New agenda", "password");
//            privacyDB.updateJournal(con, "New agenda", "password", "Secure stuff!");
//            privacyDB.showAgendaJournal(con, "New agenda", "password");
//            privacyDB.changePassword(con, "New agenda", "password", "new");

            MainWindow.init(con, agendaDB, contactDB, noteDB, privacyDB, scheduledTaskDB, todoDB);
            while (true) { ; }
        }
    }
}
