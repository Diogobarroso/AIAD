package com.aiad_schedules.schedule;

import java.io.InvalidObjectException;
import java.util.ArrayList;

/**
 * Created by RGeneral24 on 06/10/2015.
 */
public class Event {

    static protected short FirstSlot = 8;
    static protected short LastSlot = 20;

    static private short Hour;
    static private String Description;
    static private ArrayList<String> Intervenients;
    static private short Priority;

    public Event(){

        Hour = 8;
        Description = "";
        Intervenients = new ArrayList<>();
        Priority = 0;
    }

    public Event(short hour) throws InvalidObjectException {

        Hour = (short) (8 + hour);

        if(Hour < FirstSlot){
            String Error = "Invalid Hour Slot! Needs to be at least 8 and lower than 20! " + "Value is: " + Hour + "!";
            throw new InvalidObjectException(Error);
        }

        if(Hour > LastSlot){
            String Error = "Invalid Hour Slot! Needs to be at least 8 and lower than 20! " + "Value is: " + Hour + "!";
            throw new InvalidObjectException(Error);
        }

        Description = "";
        Intervenients = new ArrayList<>();
        Priority = 0;
    }

    public Event(short hour, String description, short priority) throws InvalidObjectException {

        Hour = (short) (8 + hour);

        if(Hour < FirstSlot){
            String Error = "Invalid Hour Slot! Needs to be at least 8 and lower than 20! " + "Value is: " + Hour + "!";
            throw new InvalidObjectException(Error);
        }

        if(Hour > LastSlot){
            String Error = "Invalid Hour Slot! Needs to be at least 8 and lower than 20! " + "Value is: " + Hour + "!";
            throw new InvalidObjectException(Error);
        }

        Description = description;
        Intervenients = new ArrayList<>();
        Priority = priority;
    }

    public Event(short hour, String description, ArrayList<String> intervenients, short priority) throws InvalidObjectException {

        Hour = (short) (8 + hour);

        if(Hour < FirstSlot){
            String Error = "Invalid Hour Slot! Needs to be at least 8 and lower than 20! " + "Value is: " + Hour + "!";
            throw new InvalidObjectException(Error);
        }

        if(Hour > LastSlot){
            String Error = "Invalid Hour Slot! Needs to be at least 8 and lower than 20! " + "Value is: " + Hour + "!";
            throw new InvalidObjectException(Error);
        }

        Description = description;
        Intervenients = intervenients;
        Priority = priority;
    }

    public static short getHour() {

        return Hour;
    }

    public static String getDescription() {

        return Description;
    }

    public static ArrayList<String> getIntervenients() {

        return Intervenients;
    }

    public static short getPriority() {

        return Priority;
    }

    public static void setDescription(String description) {

        Description = description;
    }

    public static void setIntervenients(ArrayList<String> intervenients) {

        Intervenients = intervenients;
    }

    public static void setPriority(short priority) {

        Priority = priority;
    }

    public String toString(){

        return getHour() + " - Description: " + getDescription()
                + " - Intervenients: " + getIntervenients().toString()
                + " - " + "Priority: " + getPriority();
    }
}
