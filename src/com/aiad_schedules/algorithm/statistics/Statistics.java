package com.aiad_schedules.algorithm.statistics;

import java.util.HashMap;
import java.util.Map;

// Creates Statistics for messages
public class Statistics {

    // Variables
    private HashMap<String, Integer> sent = new HashMap<>();
    private HashMap<String, Integer> received = new HashMap<>();
    private String agent;

    // Constructors
    public Statistics(){

        this.agent = "";
    }

    public Statistics(String agent){

        this.agent = agent;
    }

    // Gets
    public String getAgent() {

        return agent;
    }

    public int getSent(String messageType) {

        return sent.getOrDefault(messageType, 0);
    }

    public int getReceived(String messageType) {

        return received.getOrDefault(messageType, 0);
    }

    // Sets
    public void setVariableName(String agent) {

        this.agent = agent;
    }

    // Functions
    @Override
    public String toString() {

        int total = 0;
        int totalSent = 0;
        int totalReceived = 0;

        String out = "Agent " + agent + "\nMessages Sent: \n";

        for(Map.Entry<String, Integer> entry: sent.entrySet()) {

            total += entry.getValue();
            totalSent += entry.getValue();
            out += "\t-> " + entry.getKey() + ": " + entry.getValue() + "\n";
        }
        out += "Total Sent: " + totalSent + "\nMessages Received: \n";
        for(Map.Entry<String, Integer> entry: received.entrySet()) {

            total += entry.getValue();
            totalReceived += entry.getValue();
            out += "\t-> " + entry.getKey() + ": " + entry.getValue() + "\n";
        }
        out += "Total Received: " + totalReceived + "\nTotal amount of Messages: " + total + "\n";

        return out;
    }

    public void sentMessage(String messageType) {

        sent.put(messageType, sent.getOrDefault(messageType, 0) + 1);
    }

    public void receivedMessage(String messageType) {

        received.put(messageType, received.getOrDefault(messageType, 0) + 1);
    }
}
