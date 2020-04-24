package com.entities;

public class Contact implements Comparable<Contact>{

    private String firstName, lastName;
    private String nrTel1, nrTel2, email1, email2;

    public Contact(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getNrTel1() {
        return nrTel1;
    }

    public void setNrTel1(String nrTel1) {
        this.nrTel1 = nrTel1;
    }

    public String getNrTel2() {
        return nrTel2;
    }

    public void setNrTel2(String nrTel2) {
        this.nrTel2 = nrTel2;
    }

    public String getEmail1() {
        return email1;
    }

    public void setEmail1(String email1) {
        this.email1 = email1;
    }

    public String getEmail2() {
        return email2;
    }

    public void setEmail2(String email2) {
        this.email2 = email2;
    }

    @Override
    public String toString() {
        return firstName + " " + lastName;
    }

    @Override
    public int compareTo(Contact contact) {
        if (this.firstName.equals(contact.firstName)) {
            return this.lastName.compareTo(contact.lastName);
        }
        return this.firstName.compareTo(contact.firstName);
    }
}
