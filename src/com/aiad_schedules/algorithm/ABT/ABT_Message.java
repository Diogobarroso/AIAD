package com.aiad_schedules.algorithm.ABT;

import com.aiad_schedules.schedule.Event;

import java.util.ArrayList;

// ABT Agent Message Structure Class
public class ABT_Message {

    // Variables
    private String type;
    private String description;
    private int priority;
    private int day;
    private int hour;
    private ArrayList<String> intervenients = new ArrayList<>();

    // Constructors
    public ABT_Message(String type, String description, int priority, int day, int hour, ArrayList<String> intervenients) {

        this.type = type;
        this.description = description;
        this.priority = priority;
        this.day = day;
        this.hour = hour;
        this.intervenients = intervenients;
    }

    public ABT_Message(String type) {

        this.type = type;
        this.description = "";
        this.priority = 0;
        this.day = 0;
        this.hour = 0;
    }

    public ABT_Message(String type, String description, int priority, int day, int hour) {

        this.type = type;
        this.description = description;
        this.priority = priority;
        this.day = day;
        this.hour = hour;
    }

    public ABT_Message(String[] msg) {

        this.type = msg[0];
        this.description = msg[1];
        this.priority = Integer.parseInt(msg[2]);
        this.day = Integer.parseInt(msg[3]);
        this.hour = Integer.parseInt(msg[4]);

        if (msg.length > 5) {

            for (int i = 5; i < msg.length; i++) {

                intervenients.add(msg[i]);
            }
        } else {

            this.intervenients = null;
        }
    }

    // Gets
    public String getType() {

        return type;
    }

    public String getDescription() {

        return description;
    }

    public int getPriority() {

        return priority;
    }

    public int getDay() {

        return day;
    }

    public int getHour() {

        return hour;
    }

    public ArrayList<String> getIntervenients() {

        return intervenients;
    }

    // Sets
    public void setType(String type) {

        this.type = type;
    }

    public void setDescription(String description) {

        this.description = description;
    }

    public void setPriority(int priority) {

        this.priority = priority;
    }

    public void setDay(int day) {

        this.day = day;
    }

    public void setHour(int hour) {

        this.hour = hour;
    }

    public void setIntervenients(ArrayList<String> intervenients) {

        this.intervenients = intervenients;
    }

    // Functions
    @Override
    public String toString(){

        String out = getType() + "," + getDescription() + "," + getPriority() + "," + getDay() + "," + getHour();

        for(int i = 0; i < getIntervenients().size(); i++){

            out += "," + getIntervenients().get(i);
        }

        return out;
    }

    // Creates an Event from the Message
    public Event toEvent() throws Exception{

        return new Event(getHour(), getDescription(), getIntervenients(), getPriority());
    }
}
