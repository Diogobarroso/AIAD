package com.aiad_schedules.schedule;

import java.io.InvalidObjectException;
import java.util.ArrayList;

public class Day {

    static protected short AmountSlots = 12;

    private ArrayList<Event> Slots = new ArrayList<>();

    public Day() throws InvalidObjectException {

        for(short i = 0; i < AmountSlots; i++){

            Slots.add(new Event(i + 8));
        }
    }

    public Day(ArrayList<Event> slots){

        Slots = slots;
    }

    public ArrayList<Event> getSlots() {

        return Slots;
    }

    public void setSlots(ArrayList<Event> slots) {

        Slots = slots;
    }

    public String toString(){

        String out = "";

        for(short i = 0; i < getSlots().size(); i++){
            out += getSlots().get(i).toString() + "\n";
        }

        return out;
    }
}
