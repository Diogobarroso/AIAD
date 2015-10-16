package com.aiad_schedules.input;

import com.aiad_schedules.schedule.Event;
import com.aiad_schedules.schedule.Schedule;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Pattern;

// Reads the related CSV file with the schedule information of an Agent
public class csvReader {

    // Checks if it is a CSV file
    private static void csvControl(String csvFile) throws IOException {

        String[] filePath = csvFile.split(Pattern.quote("/"));
        String[] fileName = filePath[filePath.length - 1].split(Pattern.quote("."));

        if (fileName.length == 2) {

            if (fileName[1].equals("csv")) {

                return;
            } else {

                String Error = "Invalid File! Must be a \".csv\" file!";
                throw new IOException(Error);
            }
        }
        else{

            String Error = "Invalid File! Must be a \".csv\" file!";
            throw new IOException(Error);
        }

    }

    // Gets the User/Agent name from CSV file
    public static String getUser(String csvFile) throws IOException {

        String fileUser;

        BufferedReader bufferFile = new BufferedReader(new FileReader(csvFile));

        // Checks the file extension
        csvControl(csvFile);

        String line;

        if ((line = bufferFile.readLine()) != null) {

            String[] lineSplit = line.split(Pattern.quote(";"));

            if (lineSplit.length == 1) {

                fileUser = lineSplit[0];
                bufferFile.close();
                return fileUser;
            } else {

                String Error = "Wrong CSV file formatting! User Name should be the only value present!";
                bufferFile.close();
                throw new IOException(Error);
            }
        } else {

            String Error = "Agent name in file Undetected!";
            bufferFile.close();
            throw new IOException(Error);
        }
    }

    // Gets the Schedule from CSV file
    public static Schedule getSchedule(String csvFile) throws IOException {

        Schedule fileSchedule = new Schedule();

        BufferedReader bufferFile = new BufferedReader(new FileReader(csvFile));

        // Checks the file extension
        csvControl(csvFile);

        String line;
        String[] lineSplit;
        short lineCounter = 0, weekdayCounter = 0;

        while ((line = bufferFile.readLine()) != null) {
            if (lineCounter != 0) {

                lineSplit = line.split(Pattern.quote(";"));

                fileSchedule.getWeekdays().get(weekdayCounter).getSlots().get(lineCounter - 1).setDescription(lineSplit[1]);
                fileSchedule.getWeekdays().get(weekdayCounter).getSlots().get(lineCounter - 1).setPriority(Integer.parseInt(lineSplit[2]));
                fileSchedule.getWeekdays().get(weekdayCounter).getSlots().get(lineCounter - 1).setIntervenients(new ArrayList<>(Arrays.asList(lineSplit[3].split(Pattern.quote(",")))));

                //System.out.println(lineSplit[0] + " - " + lineSplit[1] + " - " + lineSplit[2] + " - " + lineSplit[2]);

                if (lineCounter == 12) {

                    lineCounter = 1;
                    weekdayCounter++;
                } else {

                    lineCounter++;
                }
            } else {

                lineCounter++;
            }
        }

        bufferFile.close();

        return fileSchedule;
    }

}