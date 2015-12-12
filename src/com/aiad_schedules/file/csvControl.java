package com.aiad_schedules.file;

import java.io.IOException;
import java.util.regex.Pattern;

// Controls the CSV files
public class csvControl {

    // Checks if it is a CSV file
    public static void check(String csvFile) throws IOException {

        String[] filePath = csvFile.split(Pattern.quote("/"));
        String[] fileName = filePath[filePath.length - 1].split(Pattern.quote("."));

        if (fileName.length == 2) {

            if (!fileName[1].equals("csv")) {

                String Error = "Invalid File! Must be a \".csv\" file!";
                throw new IOException(Error);
            }
        } else {

            String Error = "Invalid File! Must be a \".csv\" file!";
            throw new IOException(Error);
        }
    }
}
