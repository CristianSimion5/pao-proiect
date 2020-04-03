package com;

import java.util.GregorianCalendar;

public class ScheduledTask implements Comparable<ScheduledTask> {

    private String name;
    private GregorianCalendar date;

    public ScheduledTask(String name) {
        this.name = name;
        this.date = new GregorianCalendar();
    }

    public ScheduledTask(String name, GregorianCalendar date) {
        this.name = name;
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public GregorianCalendar getDate() {
        return date;
    }

    void setDate(GregorianCalendar date) {
        this.date = date;
    }

    @Override
    public int compareTo(ScheduledTask scheduledTask) {
        return this.date.compareTo(scheduledTask.date);
    }

    @Override
    public String toString() {
        return  name  + ", Scheduled for " + date.getTime();
    }
}
