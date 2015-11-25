package com.aiad_schedules.agent;

import java.util.ArrayList;

// Agent with ABT algorithm structure
public class ABT extends Base {

    // Variables
    private ArrayList<Stored> NoGood = new ArrayList<>();
    private ArrayList<Stored> AgentView = new ArrayList<>();

    // Constructors
    public ABT() {

        super();
    }

    // Gets
    public ArrayList<Stored> getNoGood() {

        return NoGood;
    }

    public ArrayList<Stored> getAgentView() {

        return AgentView;
    }

    // Sets
    public void setNoGood(ArrayList<Stored> noGood) {

        NoGood = noGood;
    }

    public void setAgentView(ArrayList<Stored> agentView) {

        AgentView = agentView;
    }
}
