package com.aiad_schedules.agent;

import java.io.InvalidObjectException;
import java.util.ArrayList;

public class ABT_Agent extends Base_Agent {

    private ArrayList<String> NoGood = new ArrayList<>();
    private ArrayList<String> AgentView = new ArrayList<>();

    public ABT_Agent() throws InvalidObjectException {

        super();
    }

    public ArrayList<String> getNoGood() {

        return NoGood;
    }

    public ArrayList<String> getAgentView() {

        return AgentView;
    }

    public void setNoGood(ArrayList<String> noGood) {

        NoGood = noGood;
    }

    public void setAgentView(ArrayList<String> agentView) {

        AgentView = agentView;
    }
}
