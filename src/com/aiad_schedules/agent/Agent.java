package com.aiad_schedules.agent;

import com.aiad_schedules.schedule.Schedule;

import java.io.InvalidObjectException;

/**
 * Created by RGeneral24 on 04/10/2015.
 */
public class Agent {

    static private String AgentName;
    static private Schedule AgentSchedule;

    public Agent() throws InvalidObjectException {

        AgentName = "";
        AgentSchedule = new Schedule();
    }
/*
    public Agent(String agentfile){

    }
*/
    public Agent(String agentname) throws InvalidObjectException {
        AgentName = agentname;
        AgentSchedule = new Schedule();
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
