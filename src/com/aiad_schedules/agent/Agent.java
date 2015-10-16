package com.aiad_schedules.agent;

import com.aiad_schedules.schedule.Schedule;
import com.aiad_schedules.input.csvReader;

import java.io.IOException;
import java.io.InvalidObjectException;

public class Agent {

    static private String AgentName;
    static private Schedule AgentSchedule;

    public Agent() throws InvalidObjectException {

        AgentName = "";
        AgentSchedule = new Schedule();
    }

    public Agent(String agentFile) throws IOException {

        AgentName = csvReader.getUser(agentFile);
        AgentSchedule = csvReader.getSchedule(agentFile);
    }

    public static String getAgentName() {
        return AgentName;
    }

    public static Schedule getAgentSchedule() {
        return AgentSchedule;
    }

    public static void setAgentName(String agentName) {
        AgentName = agentName;
    }

    public static void setAgentSchedule(Schedule agentSchedule) {
        AgentSchedule = agentSchedule;
    }
}
