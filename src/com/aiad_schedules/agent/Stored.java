package com.aiad_schedules.agent;

import com.aiad_schedules.schedule.Event;

// Stored Info on AgentView and Nogoods
public class Stored {

    // Variables
    private String storedAgent;
    private int storedDay;
    private Event storedEvent;

    // Constructors
    public Stored(){

        storedAgent = "";
        storedDay = 0;
        storedEvent = null;
    }

    // Gets
    public String getStoredAgent() {

        return storedAgent;
    }

    public int getStoredDay() {

        return storedDay;
    }

    public Event getStoredEvent() {
        return storedEvent;
    }

    // Sets

    public void setStoredAgent(String storedAgent) {

        this.storedAgent = storedAgent;
    }

    public void setStoredDay(int storedDay) {

        this.storedDay = storedDay;
    }

    public void setStoredEvent(Event storedEvent) {

        this.storedEvent = storedEvent;
    }
}
