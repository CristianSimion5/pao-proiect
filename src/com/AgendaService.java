package com;

import java.util.ArrayList;

public class AgendaService {

    private ArrayList<Agenda> agendaArrayList;

    public AgendaService() {
        this.agendaArrayList = new ArrayList<>();
    }

    public void addAgenda(Agenda agenda) {
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

    public void printAgendas() {
        for (Agenda agenda : agendaArrayList) {
            System.out.println(agenda.getName());
        }
    }
}
