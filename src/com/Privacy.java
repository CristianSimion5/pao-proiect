package com;

import java.util.Scanner;

public class Privacy {

    private String password, journal = "";
    private Scanner sc;
    boolean locked = true;

    public Privacy(String password) {
        this.password = password;
        sc = new Scanner(System.in);
    }

    private boolean requestAcces() {
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

    public void setJournal(String journal) {
        if (!locked) {
            this.journal = journal;
        } else {
            System.out.println("You do not have permission to do this operation");
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

    public void unlock() {
        if (requestAcces()) {
            this.locked = false;
        }
    }
}
