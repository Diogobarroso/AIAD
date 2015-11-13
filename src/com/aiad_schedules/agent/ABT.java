package com.aiad_schedules.agent;

import java.util.ArrayList;

// Agent with ABT algorithm structure
public class ABT extends Base {

    // Variables
    private ArrayList<String> NoGood = new ArrayList<>();
    private ArrayList<String> AgentView = new ArrayList<>();

    // Constructors
    public ABT(){

        super();
    }

    // Gets
        public ArrayList<String> getNoGood() {

        return NoGood;
    }

    public ArrayList<String> getAgentView() {

        return AgentView;
    }

    // Sets
    public void setNoGood(ArrayList<String> noGood) {

        NoGood = noGood;
    }

    public void setAgentView(ArrayList<String> agentView) {

        AgentView = agentView;
    }
}
