package com.services;

import com.entities.Agenda;
import com.entities.Note;
import com.entities.Privacy;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.Iterator;
import java.util.Scanner;

public class PrivacyIO {

    private static PrivacyIO instance = null;

    private PrivacyIO() {}

    public static PrivacyIO getInstance() {
        if (instance == null)
            instance = new PrivacyIO();
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

            Path path = Paths.get("data","private", fields[0]);
            if (!Files.exists(path)) {
                continue;
            }

            Privacy privacy = temp.getSecure();
            privacy.setJournalWithPassword(String.join("\n",
                    Files.readAllLines(path)), fields[1]);
        }
        sc.close();
    }

    public void saveData(String fileName, AgendaService agendaService) throws IOException {
        try (BufferedWriter out = new BufferedWriter(new FileWriter(fileName))) {
            out.write("Agenda name,Password\n");

            Iterator<Agenda> it = agendaService.getAgendaListIterator();
            Agenda agenda;
            Privacy privacy;

            while (it.hasNext()) {
                agenda = it.next();
                privacy = agenda.getSecure();
                out.write(agenda.getName() + "," + privacy.getPassword() + "\n");

                Files.write(Paths.get("data","private", agenda.getName()),
                        Collections.singletonList(privacy.getJournalWithPassword(privacy.getPassword())));
            }
        }
    }

}
