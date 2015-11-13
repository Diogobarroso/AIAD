package com.aiad_schedules.schedule;

import java.io.InvalidObjectException;
import java.util.ArrayList;

// Day on the schedule
public class Day {

    // Static References
    static protected short AmountSlots = 12;

    // Variables
    private ArrayList<Event> Slots = new ArrayList<>();

    // Constructors
    public Day() throws InvalidObjectException {

        for(short i = 0; i < AmountSlots; i++){

            Slots.add(new Event(i + 8));
        }
    }

    public Day(ArrayList<Event> slots){

        Slots = slots;
    }

    // Gets
    public ArrayList<Event> getSlots() {

        return Slots;
    }

    // Sets

    public void setSlots(ArrayList<Event> slots) {

        Slots = slots;
    }

    // Functions
    public String toString(){

        String out = "";

        for(short i = 0; i < getSlots().size(); i++){
            out += getSlots().get(i).toString() + "\n";
        }

        return out;
    }
}
