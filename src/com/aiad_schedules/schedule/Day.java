package com.aiad_schedules.schedule;

import java.io.InvalidObjectException;
import java.util.ArrayList;

/**
 * Created by RGeneral24 on 06/10/2015.
 */
public class Day {

    static protected short AmountSlots = 12;

    static private ArrayList<Event> Slots;

    public Day() throws InvalidObjectException {

        for(short i = 0; i < AmountSlots; i++){

            Slots.add(new Event(i));
        }
    }

    public Day(ArrayList<Event> slots){

        Slots = slots;
    }

    public static ArrayList<Event> getSlots() {

        return Slots;
    }

    public static void setSlots(ArrayList<Event> slots) {

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
