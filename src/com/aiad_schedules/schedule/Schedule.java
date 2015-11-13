package com.aiad_schedules.schedule;

import java.io.InvalidObjectException;
import java.util.ArrayList;

// Schedule of an Agent
public class Schedule {

    // Static References
    static protected short TotalDays = 5;

    // Variables
    private ArrayList<Day> Weekdays = new ArrayList<>();

    // Constructors
    public Schedule() throws InvalidObjectException{

        for(short i = 0; i < TotalDays; i++){

            Weekdays.add(new Day());
        }
    }

    // Gets
    public ArrayList<Day> getWeekdays() {

        return Weekdays;
    }

    // Sets
    public void setWeekdays(ArrayList<Day> weekdays) {

        Weekdays = weekdays;
    }

    // Functions
    public String toString(){

        String out = "";

        for(short i = 0; i < getWeekdays().size(); i++){

            switch(i){

                case 0:
                    out += "Monday: \n";
                    break;
                case 1:
                    out += "Tuesday: \n";
                    break;
                case 2:
                    out += "Wednesday: \n";
                    break;
                case 3:
                    out += "Thursday: \n";
                    break;
                case 4:
                    out += "Friday: \n";
                    break;
            }
            out += getWeekdays().get(i).toString() + "\n";
        }

        return out;
    }
}
