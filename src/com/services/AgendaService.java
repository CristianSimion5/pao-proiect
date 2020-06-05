package com.services;

import com.entities.Agenda;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class AgendaService {

    private ArrayList<Agenda> agendaArrayList;

    public AgendaService() {
        this.agendaArrayList = new ArrayList<>();
    }

    public void addAgenda(Agenda agenda) throws IOException {
        AuditService.writeAudit("addAgenda", Thread.currentThread().getName());
        agendaArrayList.add(agenda);
    }

    public Agenda getAgenda(String name) {
        for (Agenda agenda : agendaArrayList) {
            if (agenda.getName().equals(name)) {
                return agenda;
            }
        }
        return null;
    }

    public Iterator<Agenda> getAgendaListIterator() {
        return agendaArrayList.iterator();
    }

    public void printAgendas() throws IOException {
        AuditService.writeAudit("printAgendas", Thread.currentThread().getName());
        for (Agenda agenda : agendaArrayList) {
            System.out.println(agenda.getName());
        }
    }
}
