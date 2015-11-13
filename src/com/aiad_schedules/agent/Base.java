package com.aiad_schedules.agent;

import com.aiad_schedules.input.csvReader;
import com.aiad_schedules.schedule.Schedule;

import java.io.IOException;

// Base for Agent Structure
public class Base {

    // Variables
    private String AgentName;
    private Schedule AgentSchedule;

    // Constructors
    public Base() {

        AgentName = "";
        AgentSchedule = null;
    }

    public Base(String agentFile) throws IOException {

        AgentName = csvReader.getUser(agentFile);
        AgentSchedule = csvReader.getSchedule(agentFile);
    }

    // Gets
    public String getAgentName() {

        return AgentName;
    }

    public Schedule getAgentSchedule() {

        return AgentSchedule;
    }

    // Sets
    public void setAgentName(String agentName) {

        AgentName = agentName;
    }

    public void setAgentSchedule(String agentFile) throws IOException {

        AgentSchedule = csvReader.getSchedule(agentFile);
    }
}
