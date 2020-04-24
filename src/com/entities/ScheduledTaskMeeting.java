package com.entities;

import java.util.GregorianCalendar;

public class ScheduledTaskMeeting extends ScheduledTask {

    private Contact partner;

    public ScheduledTaskMeeting(String name, Contact partner) {
        super(name);
        this.partner = partner;
    }

    public ScheduledTaskMeeting(String name, GregorianCalendar date, Contact partner) {
        super(name, date);
        this.partner = partner;
    }

    public Contact getPartner() {
        return partner;
    }

    public void setPartner(Contact partner) {
        this.partner = partner;
    }

    @Override
    public String toString() {
        return super.toString() + " with contact " + partner;
    }
}
