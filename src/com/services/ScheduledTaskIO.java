package com.services;

import com.entities.Agenda;
import com.entities.Contact;
import com.entities.ScheduledTask;
import com.entities.ScheduledTaskMeeting;

import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.Scanner;

public class ScheduledTaskIO {

    private static ScheduledTaskIO instance = null;

    private ScheduledTaskIO() {}

    public static ScheduledTaskIO getInstance() {
        if (instance == null)
            instance = new ScheduledTaskIO();
        return instance;
    }

    public void loadData(String fileName, AgendaService agendaService) throws IOException, ParseException {
        Scanner sc = new Scanner(new File(fileName));
        sc.nextLine();

        String line;
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss zzz");
        Date date;

        while(sc.hasNextLine()) {
            line = sc.nextLine();
            String[] fields = line.split(",", -1);

            Agenda temp = agendaService.getAgenda(fields[0]);
            if (temp == null)
                continue;

            ScheduledTask scheduledTask;


            date = dateFormat.parse(fields[2]);

            GregorianCalendar gc = new GregorianCalendar();
            gc.setTime(date);

            if (fields[3].isBlank() && fields[4].isBlank()) {
                scheduledTask = new ScheduledTask(fields[1], gc);
            }
            else {
                Contact contact = temp.getContact(fields[3], fields[4]);
                if (contact == null)
                    continue;
                scheduledTask = new ScheduledTaskMeeting(fields[1], gc, contact);
            }

            temp.addScheduledTask(scheduledTask);
        }
        sc.close();
    }

    public void saveData(String fileName, AgendaService agendaService) throws IOException {
        try (BufferedWriter out = new BufferedWriter(new FileWriter(fileName))) {
            out.write("Agenda name,Task name,Date,First Name,Last Name\n");

            Iterator<Agenda> it = agendaService.getAgendaListIterator();
            Iterator<ScheduledTask> stit;

            Agenda agenda;
            ScheduledTask st;
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss zzz");
            String dateString;
            Contact contact;

            while (it.hasNext()) {
                agenda = it.next();
                stit = agenda.getScheduleIterator();

                while (stit.hasNext()) {
                    st = stit.next();
                    out.write(agenda.getName() + "," + st.getName() + ",");

                    dateString = dateFormat.format(st.getDate().getTime());
                    out.write(dateString + ",");

                    if (st instanceof ScheduledTaskMeeting) {
                        contact = ((ScheduledTaskMeeting) st).getPartner();
                        out.write(contact.getFirstName() + "," + contact.getLastName() + '\n');
                    } else {
                        out.write(",\n");
                    }
                }
            }
        }
    }

}
