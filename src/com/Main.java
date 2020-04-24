package com;

import com.entities.*;
import com.services.*;

import java.io.IOException;
import java.text.ParseException;
import java.util.GregorianCalendar;

public class Main {

    public static void main(String[] args) throws IOException, ParseException {

        AgendaService agendaService = new AgendaService();
        Agenda myAgenda = new Agenda("My Agenda", "abcdef");
        Agenda hisAgenda = new Agenda("John's Agenda", "123456");

        agendaService.addAgenda(myAgenda);
        agendaService.addAgenda(hisAgenda);

        // incarcarea datelor cu ajutorul serviciilor singleton
        ContactIO contactIO = ContactIO.getInstance();
        contactIO.loadData("data/Contacts.csv", agendaService);

        TodoIO todoIO = TodoIO.getInstance();
        todoIO.loadData("data/Todos.csv", agendaService);

        ScheduledTaskIO scheduledTaskIO = ScheduledTaskIO.getInstance();
        scheduledTaskIO.loadData("data/ScheduledTasks.csv", agendaService);

        NoteIO noteIO = NoteIO.getInstance();
        noteIO.loadData("data/Notes.csv", agendaService);

        PrivacyIO privacyIO = PrivacyIO.getInstance();
        privacyIO.loadData("data/Passwords.csv", agendaService);

        // afiseaza informatii din prima agenda
        System.out.println("First agenda");
        myAgenda.printContacts();
        myAgenda.printTodoList();
        myAgenda.printSchedule();
        myAgenda.printNotes();
        System.out.println(myAgenda.getSecure().getJournalWithPassword("abcdef"));

        // afiseaza informatii din a doua agenda
        System.out.println("\n*************************\nSecond agenda");
        hisAgenda.printContacts();
        hisAgenda.printTodoList();
        hisAgenda.printSchedule();
        hisAgenda.printNotes();
        System.out.println(hisAgenda.getSecure().getJournalWithPassword("123456"));

        // modificari ale datelor pentru a verifica actualizarea fisierelor dupa rulare
        myAgenda.addScheduledTask(new ScheduledTaskMeeting("Visit Martha",  myAgenda.getContact("Martha", "Cranley"))); // adauga un meeting nou la data curenta
        myAgenda.deleteScheduledTask("Buy house");  // sterge un eveniment
        Note note = myAgenda.getNote("Interesting websites");
        note.appendText("\ngithub.com\n");  // modifica textul (vezi folderul "notes")

        Privacy secure = myAgenda.getSecure();
        secure.unlock();    // se cere parola (abcdef)
        secure.appendJournal("\nUnless the developer created a getPassword() method...");
        secure.lock();

        // modificari la a doua agenda
        hisAgenda.updateContact(hisAgenda.getContact("Billy", "Powell"), "Daniel", "Powell");   // se schimba prenumele unui contact
        ScheduledTask meeting = hisAgenda.getScheduledTask(
                new GregorianCalendar(2020, 3, 3, 8, 35));
        hisAgenda.updateScheduledTask(meeting, new GregorianCalendar());    // se reprogrameaza o intalnire la data curenta

        secure = hisAgenda.getSecure();
        secure.unlock();    // se cere parola (123456)
        secure.appendJournal("\nEven more secure stuff");
        secure.lock();

        // salvarea datelor
        contactIO.saveData("data/contacts.csv", agendaService);
        todoIO.saveData("data/Todos.csv", agendaService);
        scheduledTaskIO.saveData("data/ScheduledTasks.csv", agendaService);
        noteIO.saveData("data/Notes.csv", agendaService);

        Agenda thirdAgenda = new Agenda("New Agenda", "password");
        agendaService.addAgenda(thirdAgenda);
        // va fi adaugata o noua linie corespunaztoarea thirdAgenda
        privacyIO.saveData("data/Passwords.csv", agendaService);

    }
}
