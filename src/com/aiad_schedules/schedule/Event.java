package com.aiad_schedules.schedule;

import java.io.InvalidObjectException;
import java.util.ArrayList;
import java.util.regex.Pattern;

// Event of a day in the schedule (meeting)
public class Event {

    // Static References
    static protected int FirstSlot = 8;
    static protected int LastSlot = 19;

    // Variables
    private int Hour;
    private String Description;
    private ArrayList<String> Intervenients = new ArrayList<>();
    private int Priority;

    // Constructors
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

        if (description.equals("null")) {

            Description = "";
        } else {

            Description = description;
        }

        if (intervenients.get(0).equals("null") && intervenients.size() == 1) {

            Intervenients = new ArrayList<>();
        } else {

            Intervenients = intervenients;
        }

        Priority = priority;
    }

    // Gets
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

    // Sets
    public void setDescription(String description) {

        Description = description;
    }

    public void setIntervenients(ArrayList<String> intervenients) {

        Intervenients = intervenients;
    }

    public void setPriority(int priority) {

        Priority = priority;
    }

    // Functions
    @Override
    public String toString() {

        return getHour() + ":00h - Description: " + getDescription()
                + " - Intervenients: " + getIntervenients().toString()
                + " - " + "Priority: " + getPriority();
    }

    public String toFile(){

        String out = getHour() - 8 + ";" + getDescription() + ";" + getPriority() + ";" + getIntervenients().get(0);

        for(int i = 1; i < getIntervenients().size(); i++){

            out += "," + getIntervenients().get(i);
        }

        return out;
    }

    public static ArrayList<String> setArrayList(String[] in) {

        ArrayList<String> out = new ArrayList<>();

        for (int i = 0; i < in.length; i++) {

            out.add(in[i]);
        }

        return out;
    }

    public boolean equals(Event e) {

        if (this.Hour != e.Hour) {

            return false;
        }
        if (!(this.Description.equals(e.Description))) {

            return false;
        }

        return this.Priority == e.Priority;
    }

    public boolean isEmpty() {

        return this.Description.equals("null");
    }
}
