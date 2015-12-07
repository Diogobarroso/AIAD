package com.aiad_schedules.algorithm.ABT;

import com.aiad_schedules.agent.ABT;

import java.util.ArrayList;

// ABT Kernel Functions
public class ABT_Functions {

    // Checks if the AgentView and AgentSelf is Consistent
    public static boolean Consistent (ABT.Self self, ArrayList<ABT.Stored> view){

        boolean consistent = true;

        if(!(self.getSelfDay() == -1 && view == null)) {

            for (int i = 0; i < view.size(); i++) {

                if (!self.getSelfEvent().equals(view.get(i).getStoredEvent())){

                    consistent = false;
                    break;
                }
            }
        }

        return consistent;
    }

    //public static ABT_Procedures.AgentValue chooseValue()
}
