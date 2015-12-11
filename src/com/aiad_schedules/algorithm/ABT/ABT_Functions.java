package com.aiad_schedules.algorithm.ABT;

import com.aiad_schedules.agent.ABT;
import com.aiad_schedules.schedule.Event;

import java.util.ArrayList;

// ABT Kernel Functions
public class ABT_Functions {

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
            Agent.getAgentView().get(find).setStoredAgent(msgSender);
            Agent.getAgentView().get(find).setStoredDay(msg.getDay());
            Agent.getAgentView().get(find).setStoredEvent(controlEvent);
        }

        return Agent;
    }

    // Sets Value in NoGood Store
    public static ABT addNoGood(ABT Agent, ABT_Message msg, Event controlEvent) {

        Agent.getNoGood().add(new ABT.Conflict(msg.getDay(), controlEvent.getHour()));

        return Agent;
    }

    // Chooses a new Value for the starter agent
    public static ABT.Self chooseValue(ABT Agent) throws Exception {

        // Sets first day to find as last selected day
        boolean set = true;
        int chosenDay = Agent.getAgentSelf().getSelfDay();
        int currentDay = 0;

        do {

            if (set) {

                currentDay = chosenDay;
            }

            if (!(!set && currentDay == chosenDay)) {
                for (int i = 0; i < Agent.getAgentSchedule().getWeekdays().get(currentDay).getSlots().size(); i++) {

                    // In-case this value is empty on initiator agent
                    if (Agent.getAgentSchedule().getWeekdays().get(currentDay).getSlots().get(i).isEmpty()) {

                        // Checks NoGood
                        for (int j = 0; i < Agent.getNoGood().size(); j++) {

                            // If it is not in the view it will assign
                            if (!Agent.getNoGood().get(j).hasConflict(currentDay, i)) {

                                Event newSelfEvent = new Event(i, Agent.getAgentSelf().getSelfEvent().getDescription(), Agent.getAgentSelf().getSelfEvent().getIntervenients(), Agent.getAgentSelf().getSelfEvent().getPriority());
                                return new ABT.Self(currentDay, newSelfEvent);
                            }
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
