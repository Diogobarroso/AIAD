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
    public static ABT addAgentView(ABT Agent, ABT_Message msg, String msgSender, Event controlEvent){

        // Adds message to the view
        if(Agent.getAgentView().isEmpty()){

            Agent.getAgentView().add(new ABT.Stored(msgSender, msg.getDay(), controlEvent));
        }
        else{

            int find = Agent.findAgentView(Agent.getAgentView(), msgSender);
            Agent.getAgentView().get(find).setStoredAgent(msgSender);
            Agent.getAgentView().get(find).setStoredDay(msg.getDay());
            Agent.getAgentView().get(find).setStoredEvent(controlEvent);
        }

        return Agent;
    }
}
