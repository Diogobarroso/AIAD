package com.aiad_schedules.agent;

import com.aiad_schedules.schedule.Event;

import java.util.ArrayList;

// Agent with ABT algorithm structure
public class ABT extends Base {

    // Inner Classes
    // Stored Info on AgentView
    public static class Stored {

        // Variables
        private String storedAgent;
        private int storedDay;
        private Event storedEvent;

        // Constructors
        public Stored() {

            storedAgent = "";
            storedDay = 0;
            storedEvent = null;
        }

        public Stored(String storedAgent, int storedDay, Event storedEvent) {

            this.storedAgent = storedAgent;
            this.storedDay = storedDay;
            this.storedEvent = storedEvent;
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

    // Conflict Info on NoGood
    public static class Conflict{

        // Variables
        private int conflictDay;
        private int conflictHour;

        // Constructors
        public Conflict(int conflictDay, int conflictHour) {

            this.conflictDay = conflictDay;
            this.conflictHour = conflictHour;
        }

        // Gets
        public int getConflictDay() {

            return conflictDay;
        }

        public int getConflictHour() {

            return conflictHour;
        }

        // Sets
        public void setConflictDay(int conflictDay) {

            this.conflictDay = conflictDay;
        }

        public void setConflictHour(int conflictHour) {

            this.conflictHour = conflictHour;
        }
    }

    // Agent's self view
    public static class Self {

        // Variables
        private int selfDay;
        private Event selfEvent;

        // Constructors
        public Self() {

            selfDay = -1;
            selfEvent = null;
        }

        public Self(int selfDay, Event selfEvent) {
            this.selfDay = selfDay;
            this.selfEvent = selfEvent;
        }

        // Gets
        public int getSelfDay() {

            return selfDay;
        }

        public void setSelfDay(int selfDay) {

            this.selfDay = selfDay;
        }

        // Sets
        public Event getSelfEvent() {

            return selfEvent;
        }

        public void setSelfEvent(Event selfEvent) {

            this.selfEvent = selfEvent;
        }
    }

    // Variables
    private ArrayList<Conflict> NoGood = new ArrayList<>();
    private ArrayList<Stored> AgentView = new ArrayList<>();
    private Self AgentSelf = new Self();

    // Constructors
    public ABT() {

        super();
    }

    // Gets
    public ArrayList<Conflict> getNoGood() {

        return NoGood;
    }

    public ArrayList<Stored> getAgentView() {

        return AgentView;
    }

    public Self getAgentSelf() {

        return AgentSelf;
    }

    // Sets
    public void setNoGood(ArrayList<Conflict> noGood) {

        NoGood = noGood;
    }

    public void setAgentView(ArrayList<Stored> agentView) {

        AgentView = agentView;
    }

    public void setAgentSelf(Self agentSelf) {

        AgentSelf = agentSelf;
    }

    // Functions
    public int findAgentView(ArrayList<ABT.Stored> view, String agent) {

        for (int i = 0; i < view.size(); i++) {

            if (view.get(i).getStoredAgent().equals(agent)) {

                return i;
            }
        }

        return -1;
    }

    public int findNoGood(ArrayList<ABT.Conflict> nogood, int day, int hour){

        for (int i = 0; i < nogood.size(); i++) {

            if (nogood.get(i).getConflictDay() == day && nogood.get(i).getConflictHour() == hour) {

                return i;
            }
        }

        return -1;
    }
}
