package com.aiad_schedules.algorithm.ABT;

import com.aiad_schedules.agent.ABT;
import com.aiad_schedules.schedule.Event;

import java.util.regex.Pattern;

// PROTOTYPE
public class ABT_Procedures {

    // Agent View Checker
    public static ABT ABT_CheckAgentView(ABT Agent, ABT_Message msg, String msgSender) throws Exception {

        boolean msgFlag = false;

        // Checks if Message Agent is present in the View
        for(int i = 0; i < Agent.getAgentView().size(); i++){

            if(Agent.getAgentView().get(i).getStoredAgent().equals(msgSender)){

                msgFlag = true;
                break;
            }
        }
        // If it ain't present it adds it to the view
        if(!msgFlag){

            Agent.getAgentView().add(new ABT.Stored(msgSender, msg.getDay(), new Event(msg.getHour(), msg.getDescription(), msg.getIntervenients(), msg.getPriority())));
        }

        // TODO: Consistency!!
        return Agent;
    }

    public static short ABT_ProcessInfo(ABT Agent, ABT_Message msg, String msgSender) {

        if(Agent.getAgentSchedule().getWeekdays().get(msg.getDay()).getSlots().get(msg.getHour()).isEmpty()){

            return 1;
        }

        //if(Agent.getAgentSchedule().getWeekdays().get(msg.getDay()).getSlots().get(msg.getHour()).equals()
        return 0;
    }

    public static ABT ABT_ResolveConflict(ABT Agent, ABT_Message msg, String msgSender) {


        return Agent;
    }

    public static ABT ABT_AddLink(ABT Agent, ABT_Message msg, String msgSender) {


        return Agent;
    }
}
