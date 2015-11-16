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
import java.util.regex.Pattern;

// Main ABT based Agent Class
public class ABT_Main extends Agent {

    // ### DEBUG ###
    static public boolean DEBUG = false;
    // ### DEBUG ###

    // Variables
    public ABT ABT_Agent = new ABT();

    // Internal Behaviour Classes

    // ### TESTING ### //
    // Simple Test Message
    class ABT_Behaviour extends SimpleBehaviour {

        protected int counter = 0;

        // Constructor
        public ABT_Behaviour(Agent a) {

            super(a);
        }

        // Behaviour Action
        public void action(){

            //ACLMessage msg = receive();
            ACLMessage msg = blockingReceive();

            if(msg.getPerformative() == ACLMessage.INFORM) {

                System.out.println(getLocalName() + ": recebi " + msg.getContent());
            }

            ACLMessage reply = msg.createReply();

            if(msg.getContent().equals("hello")){

                reply.setContent("hi");
                send(reply);

                counter++;
            }
        }

        // Behaviour Finish
        public boolean done() {

            return counter == 1;
            //return true;
        }

    }
    // ### TESTING ### //

    // Agent Setup Operations
    protected void setup() {

        System.out.println("Agent "+ getAID().getName() +" starting.");

        // Set Agent info
        String[] nameTrim = getAID().getName().split(Pattern.quote("@"));
        ABT_Agent.setAgentName(nameTrim[0]);
        try {

            // All files present in the "files" directory
            ABT_Agent.setAgentSchedule("files/" + nameTrim[0] + ".csv");
        }
        catch (IOException e){

            doDelete();
            e.printStackTrace();
        }

        // Register in DF
        DFAgentDescription dfd = new DFAgentDescription(); // all agent descriptions
        dfd.setName(getAID());
        ServiceDescription sd = new ServiceDescription(); // All service; all functionalities
        sd.setName(getName()); // Describes agent name by JADE name
        sd.setType(ABT_Agent.getAgentName()); // Describes agent real name on the service
        dfd.addServices(sd);

        try {

            DFService.register(this, dfd);
        } catch(FIPAException e) {

            doDelete();
            System.err.println("Error Detected! Can't Register Agent!");
            e.printStackTrace();
        }

        // Verify if there is an existing agent
        DFAgentDescription equalAgent = new DFAgentDescription();
        ServiceDescription equalAgentName = new ServiceDescription();
        equalAgentName.setType(ABT_Agent.getAgentName()); // Search on the type
        equalAgent.addServices(equalAgentName);

        try {

            DFAgentDescription[] equalAgents = DFService.search(this, equalAgent);
            if(equalAgents.length > 1){

                System.err.println("Found equal Agent! Agents must be unique!");
                doDelete();
            }
        }
        catch(FIPAException e) {

            doDelete();
            e.printStackTrace();
        }

        // ### TESTING ### //

        // Creates Sample Behaviour
        ABT_Behaviour b = new ABT_Behaviour(this);
        addBehaviour(b);

        // Reads arguments
        Object[] args = getArguments();

        // If there are arguments its an initiator agent
        if(args != null && args.length > 0) {

            String[] arguments = (String[]) args;
            if(arguments[0].equals("start")){ // TEMP: start sample transaction

                DFAgentDescription targetAgent = new DFAgentDescription();
                ServiceDescription targetService = new ServiceDescription();
                targetService.setType("Jeremy"); // base find
                targetAgent.addServices(targetService);

                ACLMessage msg = new ACLMessage(ACLMessage.INFORM); // msg create
                try { // try is needed for fipa dependencies

                    DFAgentDescription[] searchAgent = DFService.search(this, targetAgent); // agent find

                    msg.addReceiver(searchAgent[0].getName()); // send to agent
                    msg.setContent("hello");
                    send(msg);
                }
                catch (FIPAException e) {

                    doDelete();
                    e.printStackTrace();
                }
            }
        }

        // ### TESTING ### //
    }

    // Agent Shutdown
    protected void takeDown(){

        System.out.println("Agent "+ getAID().getName() +" terminating.");
    }
}
