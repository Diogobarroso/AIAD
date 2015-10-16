package com.aiad_schedules.schedule;

import java.io.InvalidObjectException;
import java.util.ArrayList;

public class Event {

    static protected int FirstSlot = 8;
    static protected int LastSlot = 19;

    private int Hour;
    private String Description;
    private ArrayList<String> Intervenients = new ArrayList<>();
    private int Priority;

    public Event() {

        Hour = 8;
        Description = "";
        Intervenients = new ArrayList<>();
        Priority = 0;
    }

    public Event(int hour) throws InvalidObjectException {

        Hour = hour;

        if (Hour < FirstSlot) {

            String Error = "Invalid Hour Slot! Needs to be at least 8 and lower than 20! " + "Value is: " + Hour + "!";
            throw new InvalidObjectException(Error);
        }

        if (Hour > LastSlot) {

            String Error = "Invalid Hour Slot! Needs to be at least 8 and lower than 20! " + "Value is: " + Hour + "!";
            throw new InvalidObjectException(Error);
        }

        Description = "";
        Intervenients = new ArrayList<>();
        Priority = 0;
    }

    public Event(int hour, String description, int priority) throws InvalidObjectException {

        Hour = hour;

        if (Hour < FirstSlot) {

            String Error = "Invalid Hour Slot! Needs to be at least 8 and lower than 20! " + "Value is: " + Hour + "!";
            throw new InvalidObjectException(Error);
        }

        if (Hour > LastSlot) {

            String Error = "Invalid Hour Slot! Needs to be at least 8 and lower than 20! " + "Value is: " + Hour + "!";
            throw new InvalidObjectException(Error);
        }

        Description = description;
        Intervenients = new ArrayList<>();
        Priority = priority;
    }

    public Event(int hour, String description, ArrayList<String> intervenients, int priority) throws InvalidObjectException {

        Hour = hour;

        if (Hour < FirstSlot) {

            String Error = "Invalid Hour Slot! Needs to be at least 8 and lower than 20! " + "Value is: " + Hour + "!";
            throw new InvalidObjectException(Error);
        }

        if (Hour > LastSlot) {

            String Error = "Invalid Hour Slot! Needs to be at least 8 and lower than 20! " + "Value is: " + Hour + "!";
            throw new InvalidObjectException(Error);
        }

        Description = description;
        Intervenients = intervenients;
        Priority = priority;
    }

    public int getHour() {

        return Hour;
    }

    public String getDescription() {

        return Description;
    }

    public ArrayList<String> getIntervenients() {

        return Intervenients;
    }

    public int getPriority() {

        return Priority;
    }

    public void setDescription(String description) {

        Description = description;
    }

    public void setIntervenients(ArrayList<String> intervenients) {

        Intervenients = intervenients;
    }

    public void setPriority(int priority) {

        Priority = priority;
    }

    public String toString() {

        return getHour() + " - Description: " + getDescription()
                + " - Intervenients: " + getIntervenients().toString()
                + " - " + "Priority: " + getPriority();
    }
}
