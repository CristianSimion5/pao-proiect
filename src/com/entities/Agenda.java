package com.entities;

import com.services.AuditService;

import java.io.IOException;
import java.util.*;

public class Agenda {

    private String name;
    private TreeSet<Contact> contacts;
    private ArrayList<Todo> todoList;
    private TreeSet<ScheduledTask> schedule;
    private ArrayList<Note> notes;
    private Privacy secure;

    public Agenda(String name, String password) {
        this.name = name;
        this.contacts = new TreeSet<>();
        this.todoList = new ArrayList<>();
        this.schedule = new TreeSet<>();
        this.notes = new ArrayList<>();
        this.secure = new Privacy(password);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * Contact related methods
     */

    public Contact addContact(Contact c) {
        contacts.add(c);
        return c;
    }

    public Contact getContact(String firstName, String lastName) {
        Contact searchContact = new Contact(firstName, lastName);
        Contact ceil = contacts.ceiling(searchContact);
        Contact floor = contacts.floor(searchContact);
        return ceil == floor? ceil : null;
    }

    public void updateContact(Contact oldContact, String newFirstName, String newLastName) {
        contacts.remove(oldContact);
        oldContact.setFirstName(newFirstName);
        oldContact.setLastName(newLastName);
        contacts.add(oldContact);
    }

    public void deleteContact(String firstName, String lastName) {
        Contact toDel = this.getContact(firstName, lastName);
        contacts.remove(toDel);
    }

    public Iterator<Contact> getContactsIterator() {
        return contacts.iterator();
    }

    public void printContacts() throws IOException {
        AuditService.writeAudit("printContacts");
        if (contacts.isEmpty()) {
            System.out.println("No contacts");
        } else {
            System.out.println("Your contacts are:");
            int nr = 1;
            for (Contact c : contacts) {
                System.out.println("    " + nr + ": " + c);
                nr++;
            }
        }
    }

    /**
     * todoList related methods
     */

    public void addTodo(Todo td) {
        todoList.add(td);
    }

    public void deleteTodo(int idx) { todoList.remove(idx - 1); }

    public void deleteTodo(String name) {
        todoList.removeIf(td -> td.getTaskName().equals(name));
    }

    public void checkTodo(int idx) { todoList.get(idx - 1).setDone(true); }

    public void uncheckTodo(int idx) { todoList.get(idx - 1).setDone(false); }

    public Iterator<Todo> getTodosIterator() {
        return todoList.iterator();
    }

    public void printTodoList() throws IOException {
        AuditService.writeAudit("printTodoList");
        if (todoList.isEmpty()) {
            System.out.println("Nothing in the To-Do list");
        } else {
            System.out.println("Your To-Do list:");
            int nr = 1;
            for (Todo td : todoList) {
                System.out.println("    " + nr + ": " + td);
                nr++;
            }
        }
    }

    /**
     * Schedule related methods
     */

    public void addScheduledTask(ScheduledTask st) { schedule.add(st); }

    public void addScheduledTask(String name, GregorianCalendar gc) { schedule.add(new ScheduledTask(name, gc)); }

    public void addScheduledTask(String name, int year, int month, int dayOfMonth) {
        schedule.add(new ScheduledTask(name, new GregorianCalendar(year, month - 1, dayOfMonth)));
    }

    public void addScheduledTask(String name, int year, int month, int dayOfMonth, int hourOfDay, int minute) {
        schedule.add(new ScheduledTask(name, new GregorianCalendar(year, month - 1, dayOfMonth, hourOfDay, minute)));
    }

    public void addMeeting(String name, Contact contact, GregorianCalendar gc) {
        schedule.add(new ScheduledTaskMeeting(name, gc, contact));
    }

    public void addMeeting(String name, Contact contact, int year, int month, int dayOfMonth) {
        schedule.add(new ScheduledTaskMeeting(name,
                new GregorianCalendar(year, month - 1, dayOfMonth), contact));
    }

    public void addMeeting(String name, Contact contact, int year, int month, int dayOfMonth, int hourOfDay, int minute) {
        schedule.add(new ScheduledTaskMeeting(name,
                new GregorianCalendar(year, month - 1, dayOfMonth, hourOfDay, minute), contact));
    }

    public ScheduledTask getScheduledTask(String taskName) {
        for (ScheduledTask st: schedule) {
            if (st.getName().equals(taskName))
                return st;
        }
        return null;
    }

    public ScheduledTask getScheduledTask(GregorianCalendar date) {
        ScheduledTask searchTask = new ScheduledTask("", date);
        ScheduledTask ceil  = schedule.ceiling(searchTask);
        ScheduledTask floor = schedule.floor(searchTask);
        return ceil == floor? ceil : null;
    }

    public void updateScheduledTask(ScheduledTask oldSchedTask, GregorianCalendar newDate) {
        schedule.remove(oldSchedTask);
        oldSchedTask.setDate(newDate);
        schedule.add(oldSchedTask);
    }

    public void deleteScheduledTask(String taskName) {
        ScheduledTask toDel = this.getScheduledTask(taskName);
        schedule.remove(toDel);
    }

    public void deleteScheduledTask(GregorianCalendar date) {
        ScheduledTask toDel = this.getScheduledTask(date);
        schedule.remove(toDel);
    }

    public Iterator<ScheduledTask> getScheduleIterator() {
        return schedule.iterator();
    }

    public void printSchedule() throws IOException {
        AuditService.writeAudit("printSchedule");
        if (schedule.isEmpty()) {
            System.out.println("You have nothing on your schedule!");
        } else {
            System.out.println("Your scheduled tasks:");
            for (ScheduledTask st : schedule) {
                System.out.println("    " + st);
            }
        }
    }

    public void printScheduledTasksBefore(GregorianCalendar date) throws IOException {
        AuditService.writeAudit("printScheduledTasksBefore");
        SortedSet<ScheduledTask> beforeDate = schedule.headSet(new ScheduledTask("", date), true);
        System.out.println("Scheduled tasks before " + date.getTime() + ":");
        for (ScheduledTask st : beforeDate) {
            System.out.println("    " + st);
        }
    }

    public void printScheduledTasksAfter(GregorianCalendar date) throws IOException {
        AuditService.writeAudit("printScheduledTasksAfter");
        SortedSet<ScheduledTask> afterDate = schedule.tailSet(new ScheduledTask("", date), true);
        System.out.println("Scheduled tasks after " + date.getTime() + ":");
        for (ScheduledTask st : afterDate) {
            System.out.println("    " + st);
        }
    }

    public void printScheduledTasksBetween(GregorianCalendar date1, GregorianCalendar date2) throws IOException {
        AuditService.writeAudit("printScheduledTasksBetween");
        SortedSet<ScheduledTask> interval = schedule.subSet(new ScheduledTask("", date1), true,
                new ScheduledTask("", date2), true);
        System.out.println("Scheduled tasks between " + date1.getTime() + " and " + date2.getTime() + ":");
        for (ScheduledTask st : interval) {
            System.out.println("    " + st);
        }
    }

    /**
     * Notes related methods
     */

    public void addNote(Note note) { notes.add(note); }

    public void addNote(String title) { notes.add(new Note(title)); }

    public Note getNote(int idx) { return notes.get(idx - 1); }

    public Note getNote(String title) {
        for (Note note: notes) {
            if (note.getTitle().equals(title)) {
                return note;
            }
        }
        return null;
    }

    public void deleteNoteByIndex(int idx) { notes.remove(idx - 1); }

    public void deleteNoteByTitle(String title) {
        notes.removeIf(note -> note.getTitle().equals(title));
    }

    public Iterator<Note> getNotesIterator() {
        return notes.iterator();
    }

    public void printNotes() throws IOException {
        AuditService.writeAudit("printNotes");
        if (notes.isEmpty()) {
            System.out.println("You have no notes");
        } else {
            System.out.println("Your notes:");
            int nr = 1;
            for (Note note : notes) {
                System.out.println("   " + nr + ": " + note);
                nr++;
            }
        }
    }

    /**
     * Secure (password protected) related methods
     */

    public Privacy getSecure() throws IOException {
        AuditService.writeAudit("getSecure");
        return secure;
    }
}
