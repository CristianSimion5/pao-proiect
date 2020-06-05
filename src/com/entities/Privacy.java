package com.entities;

import com.services.AuditService;

import java.io.IOException;
import java.util.Scanner;

public class Privacy {

    private String password, journal = "";
    private Scanner sc;
    boolean locked = true;

    public Privacy(String password) {
        this.password = password;
        sc = new Scanner(System.in);
    }

    private boolean requestAccess() throws IOException {
        AuditService.writeAudit("requestAccess", Thread.currentThread().getName());
        System.out.println("Enter the password to your agenda:");
        String checkPass = sc.nextLine();
        if (password.equals(checkPass)) {
            return true;
        } else {
            System.out.println("Wrong password");
            return false;
        }
    }

    public String getJournal() {
        if (!locked) {
            return journal;
        } else {
            System.out.println("You do not have permission to do this operation");
            return "";
        }
    }

    public String getJournalWithPassword(String password) {
        if (this.password.equals(password)) {
            return journal;
        } else {
            System.out.println("Wrong password");
            return "";
        }
    }

    public void setJournal(String journal) {
        if (!locked) {
            this.journal = journal;
        } else {
            System.out.println("You do not have permission to do this operation");
        }
    }

    public void setJournalWithPassword(String journal, String password) {
        if (this.password.equals(password)) {
            this.journal = journal;
        } else {
            System.out.println("Wrong password");
        }
    }

    public void appendJournal(String text) {
        if (!locked) {
            this.journal += text;
        } else {
            System.out.println("You do not have permission to do this operation");
        }
    }

    public void lock() {
        this.locked = true;
    }

    public void unlock() throws IOException {
        if (requestAccess()) {
            this.locked = false;
        }
    }

    public String getPassword() {
        return this.password;
    }
}
