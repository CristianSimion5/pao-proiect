package com.services;

import com.entities.Agenda;
import com.entities.Todo;

import java.io.*;
import java.util.Iterator;
import java.util.Scanner;

public class TodoIO {

    private static TodoIO instance = null;

    private TodoIO() {}

    public static TodoIO getInstance() {
        if (instance == null)
            instance = new TodoIO();
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

            Todo todo = new Todo(fields[1]);
            if (fields[2].equals("TRUE")) {
                todo.setDone(true);
            }

            temp.addTodo(todo);
        }
        sc.close();
    }

    public void saveData(String fileName, AgendaService agendaService) throws IOException {
        try (BufferedWriter out = new BufferedWriter(new FileWriter(fileName))) {
            out.write("Agenda name,Task Name,Done\n");

            Iterator<Agenda> it = agendaService.getAgendaListIterator();
            Iterator<Todo> tdit;
            Agenda agenda;
            Todo todo;
            while (it.hasNext()) {
                agenda = it.next();
                tdit = agenda.getTodosIterator();

                while (tdit.hasNext()) {
                    todo = tdit.next();
                    out.write(agenda.getName() + ",");
                    out.write(todo.getTaskName() + "," );
                    if (todo.isDone()) {
                        out.write("TRUE");
                    } else {
                        out.write("FALSE");
                    }
                    out.write('\n');
                }
            }
        }
    }

}
