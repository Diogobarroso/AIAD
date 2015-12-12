package com.aiad_schedules.algorithm.ABT;

import com.aiad_schedules.agent.ABT;
import com.aiad_schedules.schedule.Event;
import com.sun.xml.internal.ws.api.message.ExceptionHasMessage;

// ABT Kernel Procedures
public class ABT_Procedures {

    // ### ACTIVATE FOR TXT DEBUG ###
    static protected boolean DEBUG = false;
    // ### ACTIVATE FOR TXT DEBUG ###

    // Agent View Checker
    public static boolean CheckAgentView(ABT Agent) throws Exception {

        return ABT_Functions.Consistent(Agent.getAgentSelf(), Agent.getAgentView());
    }

    // ProcessInfo Method for "ok?" messages
    public static short ProcessInfo(ABT Agent, ABT_Message msg, String msgSender) throws Exception {

        Event controlEvent = new Event(msg.getHour(), msg.getDescription(), msg.getIntervenients(), msg.getPriority());

        // Adds message to the view
        Agent = ABT_Functions.addAgentView(Agent, msg, msgSender, controlEvent);

        // Sets the agent self view
        Agent.setAgentSelf(new ABT.Self(msg.getDay(), Agent.getAgentSchedule().getWeekdays().get(msg.getDay()).getSlots().get(msg.getHour() - 8)));

        if (DEBUG) System.err.println("Agent self info: " + Agent.getAgentSelf().getSelfEvent().toString());

        // Checks if the current description is equal to what he wants to assign
        if (Agent.getAgentSelf().getSelfEvent().getDescription().equals("null")) {

            // Sets new self value that is assigned
            Agent.setAgentSelf(new ABT.Self(msg.getDay(), controlEvent));
            if (DEBUG) System.err.println("Swapped Agent self info: " + Agent.getAgentSelf().getSelfEvent().toString());
            return 0; // In case it is valid
        } else if (Agent.getAgentSelf().getSelfEvent().equals(controlEvent)) {

            return 1; // In case it is already assigned with this value
        } else {

            return 2; // In case of another assigned Value
        }
    }

    // ResolveConflict method for "nogood" messages
    public static ABT ResolveConflict(ABT Agent, ABT_Message msg, String msgSender) throws Exception {

        Event controlEvent = new Event(msg.getHour(), msg.getDescription(), msg.getIntervenients(), msg.getPriority());

        // Adds message to the view
        Agent = ABT_Functions.addAgentView(Agent, msg, msgSender, controlEvent);

        // Adds value to nogood store
        Agent = ABT_Functions.addNoGood(Agent, msg, controlEvent);

        // Sets a new value for Self
        Agent.setAgentSelf(ABT_Functions.chooseValue(Agent));

        return Agent;
    }

    // If it receives a link if it is an accepted message
    public static ABT AddLink(ABT Agent, ABT_Message msg, String msgSender) throws Exception {

        Event controlEvent = new Event(msg.getHour(), msg.getDescription(), msg.getIntervenients(), msg.getPriority());

        // Adds message to the view
        Agent = ABT_Functions.addAgentView(Agent, msg, msgSender, controlEvent);

        // Removes Inconsistent Values from NoGood Store (All assigned Values)
        // ### Might not be needed ###
        /*
        int i = 0;
        while (i < Agent.getNoGood().size()) {

            if (Agent.getNoGood().get(i).getStoredAgent().equals(msgSender)) {

                Agent.getNoGood().remove(i);
            } else {

                i++;
            }
        }*/

        return Agent;
    }

    // Assigns Finished Values to the Agent
    public static ABT ChangeValues(ABT Agent) {

        if (DEBUG) System.err.println("Agent self info: " + Agent.getAgentSelf().getSelfEvent().toString());

        Agent.getAgentSchedule().getWeekdays().get(Agent.getAgentSelf().getSelfDay()).getSlots().get(Agent.getAgentSelf().getSelfEvent().getHour() - 8).setDescription(Agent.getAgentSelf().getSelfEvent().getDescription());
        Agent.getAgentSchedule().getWeekdays().get(Agent.getAgentSelf().getSelfDay()).getSlots().get(Agent.getAgentSelf().getSelfEvent().getHour() - 8).setPriority(Agent.getAgentSelf().getSelfEvent().getPriority());
        Agent.getAgentSchedule().getWeekdays().get(Agent.getAgentSelf().getSelfDay()).getSlots().get(Agent.getAgentSelf().getSelfEvent().getHour() - 8).setIntervenients(Agent.getAgentSelf().getSelfEvent().getIntervenients());

        return Agent;
    }
}