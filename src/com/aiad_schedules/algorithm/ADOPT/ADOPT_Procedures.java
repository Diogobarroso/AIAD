package com.aiad_schedules.algorithm.ADOPT;

import com.aiad_schedules.agent.ADOPT;
import com.aiad_schedules.schedule.Event;

// ADOPT Kernel Procedures
public class ADOPT_Procedures {

    // TODO: REVIEW!!!
    final class AgentValue {

        // Variables
        private Event Event;
        private ADOPT Agent;

        // Constructors
        public AgentValue(Event event, ADOPT agent) {

            Event = event;
            Agent = agent;
        }

        // Sets
        public Event getEvent() {

            return Event;
        }

        public ADOPT getAgent() {

            return Agent;
        }

        // Sets
        public void setEvent(Event event) {

            Event = event;
        }

        public void setAgent(ADOPT agent) {

            Agent = agent;
        }
    }

    // Agent View Checker
    public static boolean CheckAgentView(ADOPT Agent) throws Exception {

        return ADOPT_Functions.Consistent(Agent.getAgentSelf(), Agent.getAgentView());
    }

    // ProcessInfo Method for "ok?" messages
    public static short ProcessInfo(ADOPT Agent, ADOPT_Message msg, String msgSender) throws Exception {

        Event controlEvent = new Event(msg.getHour(), msg.getDescription(), msg.getIntervenients(), msg.getCost());

        // Adds message to the view
        Agent = ADOPT_Functions.addAgentView(Agent, msg, msgSender, controlEvent);

        // Sets the agent self view
        Agent.setAgentSelf(new ADOPT.Self(msg.getDay(), Agent.getAgentSchedule().getWeekdays().get(msg.getDay()).getSlots().get(msg.getHour() - 8)));

        // Checks if the current description is equal to what he wants to assign
        if (Agent.getAgentSelf().getSelfEvent().getDescription().equals("null")) {

            return 0; // In case it is valid
        } else if (Agent.getAgentSelf().getSelfEvent().equals(controlEvent)) {

            return 1; // In case it is already assigned with this value
        } else {

            return 2; // In case of another assigned Value
        }
    }

    // ResolveConflict method for "nogood" messages
    public static ADOPT ResolveConflict(ADOPT Agent, ADOPT_Message msg, String msgSender) {



        return Agent;
    }

    // If it receives a link if it is an accepted message
    public static ADOPT AddLink(ADOPT Agent, ADOPT_Message msg, String msgSender) throws Exception {

        Event controlEvent = new Event(msg.getHour(), msg.getDescription(), msg.getIntervenients(), msg.getCost());

        // Adds message to the view
        Agent = ADOPT_Functions.addAgentView(Agent, msg, msgSender, controlEvent);

        // Removes Inconsistent Values from NoGood Store (All assigned Values)
        int i = 0;
        while (i < Agent.getNoGood().size()) {

            if (Agent.getNoGood().get(i).getStoredAgent().equals(msgSender)) {

                Agent.getNoGood().remove(i);
            } else {

                i++;
            }
        }

        return Agent;
    }

    // Assigns Finished Values to the Agent
    public static ADOPT ChangeValues(ADOPT Agent) {

        Agent.getAgentSchedule().getWeekdays().get(Agent.getAgentSelf().getSelfDay()).getSlots().get(Agent.getAgentSelf().getSelfEvent().getHour() - 8).setDescription(Agent.getAgentSelf().getSelfEvent().getDescription());
        Agent.getAgentSchedule().getWeekdays().get(Agent.getAgentSelf().getSelfDay()).getSlots().get(Agent.getAgentSelf().getSelfEvent().getHour() - 8).setPriority(Agent.getAgentSelf().getSelfEvent().getPriority());
        Agent.getAgentSchedule().getWeekdays().get(Agent.getAgentSelf().getSelfDay()).getSlots().get(Agent.getAgentSelf().getSelfEvent().getHour() - 8).setIntervenients(Agent.getAgentSelf().getSelfEvent().getIntervenients());

        return Agent;
    }
}