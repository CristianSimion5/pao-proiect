package com.services;

import com.entities.Agenda;
import com.entities.Note;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.Iterator;
import java.util.Scanner;

public class NoteIO {

    private static NoteIO instance = null;

    private NoteIO() {}

    public static NoteIO getInstance() {
        if (instance == null)
            instance = new NoteIO();
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

            Note note = new Note(fields[1]);
            note.setText(String.join("\n",
                    Files.readAllLines(Paths.get("data","notes", fields[1] + ".txt"))));

            temp.addNote(note);
        }
        sc.close();
    }

    public void saveData(String fileName, AgendaService agendaService) throws IOException {
        try (BufferedWriter out = new BufferedWriter(new FileWriter(fileName))) {
            out.write("Agenda name,Title\n");

            Iterator<Agenda> it = agendaService.getAgendaListIterator();
            Iterator<Note> nit;
            Agenda agenda;
            Note note;

            while (it.hasNext()) {
                agenda = it.next();
                nit = agenda.getNotesIterator();

                while (nit.hasNext()) {
                    note = nit.next();
                    out.write(agenda.getName() + "," + note.getTitle() + "\n");

                    Files.write(Paths.get("data","notes", note.getTitle() + ".txt"),
                            Collections.singletonList(note.getText()));
                }
            }
        }
    }

}
