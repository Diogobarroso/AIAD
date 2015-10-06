package com.aiad_schedules.schedule;

import java.io.InvalidObjectException;
import java.util.ArrayList;

/**
 * Created by RGeneral24 on 04/10/2015.
 */
public class Schedule {

    static protected short TotalDays = 5;

    static private ArrayList<Day> Weekdays;

    public Schedule() throws InvalidObjectException{

        for(short i = 0; i < TotalDays; i++){

            Weekdays.add(new Day());
        }
    }

    public static ArrayList<Day> getWeekdays() {

        return Weekdays;
    }

    public static void setWeekdays(ArrayList<Day> weekdays) {

        Weekdays = weekdays;
    }

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
            out += "\t" + getWeekdays().get(i).toString() + "\n";
        }

        return out;
    }
}
