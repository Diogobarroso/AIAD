package com.aiad_schedules.algorithm.ABT;

import com.aiad_schedules.agent.ABT;

import jade.core.Agent;
import jade.core.behaviours.SimpleBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;

import java.io.IOException;

// Main ABT based Agent Class
public class ABT_Main extends Agent {

    // Variables
    public ABT ABT_Agent = new ABT();

    // Internal Behaviour Classes
    class ABT_Behaviour extends SimpleBehaviour {

        // Constructor
        public ABT_Behaviour(Agent a) {

            super(a);
        }

        // Behaviour Action
        public void action(){

            ACLMessage msg = blockingReceive();

            if(msg.getPerformative() == ACLMessage.INFORM) {

                System.out.println(getLocalName() + ": recebi " + msg.getContent());
            }

            ACLMessage reply = msg.createReply();

            if(msg.getContent().equals("hello")){

                reply.setContent("hi");
            }
            else {

                reply.setContent("hello");
            }

            send(reply);
        }

        // Behaviour Finish
        public boolean done() {

            return true;
        }

    }

    // Agent Setup Operations
    protected void setup() {

        System.out.println("Agent "+ getAID().getName() +" starting.");

        // Set Agent info
        ABT_Agent.setAgentName(getAID().getName());
        try {

            // All files present in the "files" directory
            ABT_Agent.setAgentSchedule("files/" + getAID().getName() + ".csv");
        }
        catch (IOException e){

            e.printStackTrace();
            doDelete();
        }

        // Register in DF
        DFAgentDescription dfd = new DFAgentDescription(); // all agent descriptions
        dfd.setName(getAID());
        ServiceDescription sd = new ServiceDescription(); // All service; all functionalities
        sd.setName(getName());
        sd.setType(ABT_Agent.getAgentName());
        dfd.addServices(sd);

        try {

            DFService.register(this, dfd);
        } catch(FIPAException e) {

            e.printStackTrace();
        }

        // Verify if there is an existing agent
        DFAgentDescription equalAgent = new DFAgentDescription();
        ServiceDescription equalAgentName = new ServiceDescription();
        equalAgentName.setType(ABT_Agent.getAgentName());
        equalAgent.addServices(equalAgentName);

        try {

            DFAgentDescription[] equalAgents = DFService.search(this, equalAgent);
            if(equalAgents.length != 0){

                System.err.println("Found equal Agent! Agents must be unique!");
                doDelete();
            }
        }
        catch(FIPAException e) {

            e.printStackTrace();
            doDelete();
        }

        // Creates Sample Behaviour
        ABT_Behaviour b = new ABT_Behaviour(this);
        addBehaviour(b);

    }

    // Agent Shutdown
    protected void takeDown(){

        System.out.println("Agent "+ getAID().getName() +" terminating.");
    }
}
