package com.aiad_schedules.algorithm.ADOPT;

import com.aiad_schedules.schedule.Event;

import java.util.ArrayList;

// ADOPT Agent Message Structure Class
public class ADOPT_Message {

    // Variables
    private String description;
    private int cost;
    private int day;
    private int hour;
    private String type;
    private ArrayList<String> intervenients = new ArrayList<>();

    // Constructors
    public ADOPT_Message(String description, int cost, int day, int hour, ArrayList<String> intervenients) {

        this.description = description;
        this.cost = cost;
        this.day = day;
        this.hour = hour;
        this.intervenients = intervenients;
    }

    public ADOPT_Message() {

        this.description = "";
        this.cost = 0;
        this.day = 0;
        this.hour = 0;
    }

    public ADOPT_Message(String description, int cost, int day, int hour) {

        this.description = description;
        this.cost = cost;
        this.day = day;
        this.hour = hour;
    }

    public ADOPT_Message(String[] msg) {
        this.type = msg[0];
        this.description = msg[1];
        this.cost = Integer.parseInt(msg[2]);
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
    public String getDescription() {

        return description;
    }

    public int getCost() {

        return cost;
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

    public String getType() {
        return type;
    }

    // Sets

    public void setDescription(String description) {

        this.description = description;
    }

    public void setCost(int cost) {

        this.cost = cost;
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

    public void setType(String type) {
        this.type = type;
    }

    // Functions
    @Override
    public String toString(){

        String out = getDescription() + "," + getCost() + "," + getDay() + "," + getHour();

        for(int i = 0; i < getIntervenients().size(); i++){

            out += "," + getIntervenients().get(i);
        }

        return out;
    }

    // Creates an Event from the Message
    public Event toEvent() throws Exception{

        return new Event(getHour(), getDescription(), getIntervenients(), getCost());
    }

    public void incrementCost() {
        this.cost++;
    }
}
