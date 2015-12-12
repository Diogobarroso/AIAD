package com.aiad_schedules.file;

import com.aiad_schedules.schedule.Schedule;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

// Writes the schedule information into a CSV File
public class csvWriter {

    // Completely rewrites the file
    public static void setSchedule(String csvFile, String name, Schedule schedule) throws IOException {

        // Clears the file
        PrintWriter clearFile = new PrintWriter(csvFile);
        clearFile.close();

        PrintWriter bufferFile = new PrintWriter(new BufferedWriter(new FileWriter(csvFile, true)));

        // Writes the Name line
        bufferFile.println(name+";;;");

        // Writes the Schedule lines
        for(int i = 0; i < schedule.getWeekdays().size(); i++){

            for (int j = 0; j < schedule.getWeekdays().get(i).getSlots().size(); j++){

                bufferFile.println(schedule.getWeekdays().get(i).getSlots().get(j).toFile());
            }
        }

        bufferFile.close();
    }
}
