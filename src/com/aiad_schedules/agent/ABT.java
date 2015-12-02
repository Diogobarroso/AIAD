package com.aiad_schedules.agent;

import com.aiad_schedules.schedule.Event;

import java.util.ArrayList;

// Agent with ABT algorithm structure
public class ABT extends Base {

    // Inner Classes
    // Stored Info on AgentView and Nogoods
    public class Stored {

        // Variables
        private String storedAgent;
        private int storedDay;
        private Event storedEvent;

        // Constructors
        public Stored(){

            storedAgent = "";
            storedDay = 0;
            storedEvent = null;
        }

        // Gets
        public String getStoredAgent() {

            return storedAgent;
        }

        public int getStoredDay() {

            return storedDay;
        }

        public Event getStoredEvent() {
            return storedEvent;
        }

        // Sets
        public void setStoredAgent(String storedAgent) {

            this.storedAgent = storedAgent;
        }

        public void setStoredDay(int storedDay) {

            this.storedDay = storedDay;
        }

        public void setStoredEvent(Event storedEvent) {

            this.storedEvent = storedEvent;
        }
    }

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
