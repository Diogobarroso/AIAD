package com.aiad_schedules.algorithm.ABT;

import com.aiad_schedules.agent.ABT;
import com.aiad_schedules.schedule.Event;

import java.util.ArrayList;
import java.util.concurrent.SynchronousQueue;

// ABT Kernel Functions
public class ABT_Functions {

    // ### ACTIVATE FOR TXT DEBUG ###
    static protected boolean DEBUG = true;
    // ### ACTIVATE FOR TXT DEBUG ###

    // Checks if the AgentView and AgentSelf is Consistent
    public static boolean Consistent(ABT.Self self, ArrayList<ABT.Stored> view) {

        boolean consistent = true;

        if (!(self.getSelfDay() == -1 && view == null)) {

            for (int i = 0; i < view.size(); i++) {

                if (!self.getSelfEvent().equals(view.get(i).getStoredEvent())) {

                    consistent = false;
                    break;
                }
            }
        }

        return consistent;
    }

    // Sets Value in Agent View
    public static ABT addAgentView(ABT Agent, ABT_Message msg, String msgSender, Event controlEvent) {

        // Adds message to the view
        if (Agent.getAgentView().isEmpty()) {

            Agent.getAgentView().add(new ABT.Stored(msgSender, msg.getDay(), controlEvent));
        } else {

            int find = Agent.findAgentView(Agent.getAgentView(), msgSender);
            if (find != -1) {

                Agent.getAgentView().get(find).setStoredAgent(msgSender);
                Agent.getAgentView().get(find).setStoredDay(msg.getDay());
                Agent.getAgentView().get(find).setStoredEvent(controlEvent);
            }
            else{

                Agent.getAgentView().add(new ABT.Stored(msgSender, msg.getDay(), controlEvent));
            }
        }

        return Agent;
    }

    // Sets Value in NoGood Store
    public static ABT addNoGood(ABT Agent, ABT_Message msg, Event controlEvent) {

        if (Agent.getNoGood().isEmpty()) {

            Agent.getNoGood().add(new ABT.Conflict(msg.getDay(), controlEvent.getHour()));
        } else {

            int find = Agent.findNoGood(Agent.getNoGood(), msg.getDay(), controlEvent.getHour());
            if (find == -1) {

                Agent.getNoGood().add(new ABT.Conflict(msg.getDay(), controlEvent.getHour()));
            }
        }

        return Agent;
    }

    // Chooses a new Value for the starter agent
    public static ABT.Self chooseValue(ABT Agent) throws Exception {

        // Sets first day to find as last selected day
        boolean set = true;
        int chosenDay = Agent.getAgentSelf().getSelfDay();
        int currentDay = 0;

        if (DEBUG) System.err.println("CHOICE --- Choosing Value for " + Agent.getAgentName());

        do {

            if (set) {

                if (DEBUG) System.err.println("CHOICE --- set equal");
                currentDay = chosenDay;
            }

            if (!(!set && currentDay == chosenDay)) {

                if (DEBUG) System.err.println("CHOICE --- in choice cycle");
                for (int i = 0; i < Agent.getAgentSchedule().getWeekdays().get(currentDay).getSlots().size(); i++) {

                    // In-case this value is empty on initiator agent
                    if (Agent.getAgentSchedule().getWeekdays().get(currentDay).getSlots().get(i).isEmpty()) {

                        if (DEBUG) System.err.println("CHOICE --- found empty");
                        // Checks NoGood
                        if(Agent.findNoGood(Agent.getNoGood(), currentDay, (i+8)) == -1){

                            if (DEBUG) System.err.println("CHOICE --- i am not on view");
                            if (DEBUG) System.err.println("CHOICE --- i value " + i);
                            Event newSelfEvent = new Event(i + 8, Agent.getAgentSelf().getSelfEvent().getDescription(), Agent.getAgentSelf().getSelfEvent().getIntervenients(), Agent.getAgentSelf().getSelfEvent().getPriority());
                            return new ABT.Self(currentDay, newSelfEvent);
                        }
                    }
                }
            }

            if (set) {

                set = false;
                currentDay = 0;
            } else {

                currentDay++;
            }
        } while (currentDay < 5);

        return null;
    }
}
