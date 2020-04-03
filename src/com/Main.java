package com;

import java.util.GregorianCalendar;

public class Main {
    public static void main(String[] args) {

        AgendaService agendaService = new AgendaService();
        Agenda myAgenda = new Agenda("My Agenda", "abcdef");

        Contact ct = myAgenda.addContact(new Contact("Johnny", "Logger"));
        ct.setEmail1("jlogger@gmail.com");
        ct = myAgenda.addContact(new Contact("Alfred", "Legster"));
        ct.setNrTel1("4444-555-123");
        myAgenda.addContact(new Contact("Gretta", "Legster"));
        myAgenda.addContact(new Contact("Martha", "Cranley"));
        myAgenda.printContacts();
        System.out.println();

        myAgenda.addTodo(new Todo("Wash my hands"));
        myAgenda.checkTodo(1);
        myAgenda.addTodo(new Todo("Eat healthy"));
        myAgenda.printTodoList();
        System.out.println();

        myAgenda.addScheduledTask(new ScheduledTask("Visit France",
                new GregorianCalendar(2020, 11, 10, 11, 0)));
        myAgenda.addScheduledTask("Buy house",
                new GregorianCalendar(2021, 11, 16, 11, 0));
        myAgenda.addScheduledTask("Sell old car",2020, 11, 10);
        myAgenda.addScheduledTask("Pay debts",2020, 11, 10, 17, 0);
        myAgenda.addScheduledTask(new ScheduledTaskMeeting("Visit Martha",  myAgenda.getContact("Martha", "Cranley")));
        myAgenda.addMeeting("Coffee with Johnny", myAgenda.getContact("Johnny", "Logger"), 2019, 5, 23, 9, 0);
        myAgenda.printSchedule();
        System.out.println();

        myAgenda.printScheduledTasksAfter(new GregorianCalendar(2020, 10, 10, 13, 0));
        System.out.println();
        myAgenda.printScheduledTasksBefore(new GregorianCalendar(2020, 10, 10, 13, 0));
        System.out.println();
        myAgenda.printScheduledTasksInterval(myAgenda.getScheduledTask("Sell old car").getDate(),
                myAgenda.getScheduledTask("Visit France").getDate());
        System.out.println();

        myAgenda.deleteScheduledTask("Buy house");
        myAgenda.printSchedule();
        System.out.println();

        myAgenda.addNote("Chocolate cake recipe");
        myAgenda.addNote("Interesting websites");

        Note note = myAgenda.getNote("Interesting websites");
        note.appendText("youtube.com\nduckduckgo.com");
//        System.out.println(ag.getNote(1).getText());

        myAgenda.printNotes();
        System.out.println();

        Privacy secure = myAgenda.getSecure();
        secure.unlock();
        secure.setJournal("Information stored here is safe");
        System.out.println(secure.getJournal());
        secure.appendJournal(" from danger!");
        System.out.println(secure.getJournal());
        secure.lock();

        secure.setJournal("Modifying journal without unlocking");
        System.out.println();



        Agenda hisAgenda = new Agenda("John's Agenda", "123456");
        hisAgenda.addContact(new Contact("Billy", "Powell"));
        hisAgenda.addContact(new Contact("Chloe", "Winchester"));
        hisAgenda.printContacts();
        hisAgenda.updateContact(hisAgenda.getContact("Billy", "Powell"), "Daniel", "Powell");
        hisAgenda.printContacts();
        System.out.println();

        hisAgenda.addMeeting("Jog with Chloe", hisAgenda.getContact("Chloe", "Winchester"),
                2020, 4, 3, 8, 35);
        hisAgenda.addScheduledTask("Project deadline", 2020, 4, 3, 18, 0);
        hisAgenda.printSchedule();
        ScheduledTask meeting = hisAgenda.getScheduledTask(
                new GregorianCalendar(2020, 3, 3, 8, 35));
        hisAgenda.updateScheduledTask(meeting, new GregorianCalendar());
        hisAgenda.printSchedule();
        System.out.println();

        secure = hisAgenda.getSecure();
        secure.unlock();
        secure.appendJournal("Secure stuff");
        System.out.println(secure.getJournal());
        secure.lock();
        System.out.println();


        agendaService.addAgenda(myAgenda);
        agendaService.addAgenda(hisAgenda);
        agendaService.printAgendas();
    }
}