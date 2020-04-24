package com.services;

import com.entities.Agenda;
import com.entities.Contact;

import java.io.*;
import java.util.Iterator;
import java.util.Scanner;

import static com.services.HelpService.nvl2;

public class ContactIO {

    private static ContactIO instance = null;

    private ContactIO() {}

    public static ContactIO getInstance() {
        if (instance == null)
            instance = new ContactIO();
        return instance;
    }

    public void loadData(String fileName, AgendaService agendaService) throws IOException {
        Scanner sc = new Scanner(new File(fileName));
        sc.nextLine();

        String line;
        while(sc.hasNextLine()) {
            line = sc.nextLine();
            String[] fields = line.split(",", -1);

            Agenda temp = agendaService.getAgenda(fields[0]);
            if (temp == null)
                continue;

            Contact contact = new Contact(fields[1], fields[2]);
            if (!fields[3].isBlank()) {
                contact.setNrTel1(fields[3]);
            }
            if (!fields[4].isBlank()) {
                contact.setNrTel2(fields[4]);
            }
            if (!fields[5].isBlank()) {
                contact.setEmail1(fields[5]);
            }
            if (!fields[6].isBlank()) {
                contact.setEmail2(fields[6]);
            }

            temp.addContact(contact);
        }
        sc.close();
    }

    public void saveData(String fileName, AgendaService agendaService) throws IOException {
        try (BufferedWriter out = new BufferedWriter(new FileWriter(fileName))) {
            out.write("Agenda name,First name,Last name,TelNum1,TelNum2,Email1,Email2\n");

            Iterator<Agenda> it = agendaService.getAgendaListIterator();
            Iterator<Contact> cit;
            Agenda agenda;
            Contact contact;
            while (it.hasNext()) {
                agenda = it.next();
                cit = agenda.getContactsIterator();

                while (cit.hasNext()) {
                    contact = cit.next();
                    out.write(agenda.getName() + ",");
                    out.write(contact.getFirstName() + "," + contact.getLastName() + ",");
                    out.write(nvl2(contact.getNrTel1()) + "," + nvl2(contact.getNrTel2()) + ",");
                    out.write(nvl2(contact.getEmail1()) + "," + nvl2(contact.getEmail2()) + "\n");
                }
            }
        }
    }

}
