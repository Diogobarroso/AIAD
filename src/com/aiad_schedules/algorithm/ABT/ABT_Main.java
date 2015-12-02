package com.aiad_schedules.algorithm.ABT;

import com.aiad_schedules.agent.ABT;

import com.aiad_schedules.schedule.Event;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.SimpleBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Pattern;

// Main ABT based Agent Class
public class ABT_Main extends Agent {

    // ### ACTIVATE FOR TXT DEBUG ###
    static public boolean DEBUG = true;
    // ### ACTIVATE FOR TXT DEBUG ###

    // Variables
    private ABT ABT_Agent = new ABT();
    private int Control_Day = -1;
    private Event Control_Event = null;

    // Internal Behaviour Classes

    /* ### TESTING ### //
    // Simple Test Message
    class ABT_Behaviour extends SimpleBehaviour {

        // Variables
        protected int counter = 0;

        // Constructor
        public ABT_Behaviour(Agent a) {

            super(a);
        }

        // Behaviour Action
        public void action() {

            //ACLMessage msg = receive();
            ACLMessage msg = blockingReceive();

            if (msg.getPerformative() == ACLMessage.INFORM) {

                System.out.println(getLocalName() + ": recebi " + msg.getContent());
            }

            ACLMessage reply = msg.createReply();

            if (msg.getContent().equals("hello")) {

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
    // ### END TESTING ### */

    // ABT Kernel Behaviour Class
    class ABT_Kernel extends SimpleBehaviour {

        // Variables
        protected boolean end = false; // Kernel Control

        // Constructor
        public ABT_Kernel(Agent a) {

            super(a);
        }

        // Behaviour Action
        public void action() {

            ACLMessage msgReceived = blockingReceive();

            if (msgReceived.getPerformative() == ACLMessage.INFORM) {

                if (DEBUG) System.out.println(getLocalName() + ": recebi " + msgReceived.getContent());

                String[] msgDecode = msgReceived.getContent().split(Pattern.quote(" "));

                ABT_Message msg = new ABT_Message(msgDecode);

                // Check values with Agent View
                ABT_Agent = ABT_Procedures.ABT_CheckAgentView(ABT_Agent, msg);

                // ok? Message Actions
                if (msg.getType().equals("ok?")) {

                    ABT_Agent = ABT_Procedures.ABT_ProcessInfo(ABT_Agent, msg);
                }

                // ngd Message Actions
                if (msg.getType().equals("ngd")) {

                    ABT_Agent = ABT_Procedures.ABT_ResolveConflict(ABT_Agent, msg);
                }

                // adl Message Actions
                if (msg.getType().equals("adl")) {

                    ABT_Agent = ABT_Procedures.ABT_AddLink(ABT_Agent, msg);
                }

                // stp Message Action
                if (msg.getType().equals("stp")) {

                    // Terminates the Agent
                    end = true;
                }
            }
        }

        // Behaviour Finish
        public boolean done() {

            return end;
        }
    }

    // Agent Setup Operations
    protected void setup() {

        if (DEBUG) System.out.println("Agent " + getAID().getName() + " starting.");

        // Set Agent info
        String[] nameTrim = getAID().getName().split(Pattern.quote("@"));
        ABT_Agent.setAgentName(nameTrim[0]);
        try {

            // All files present in the "files" directory
            ABT_Agent.setAgentSchedule("files/" + nameTrim[0] + ".csv");
        } catch (IOException e) {

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
        } catch (FIPAException e) {

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
            if (equalAgents.length > 1) {

                System.err.println("Found equal Agent! Agents must be unique!");
                doDelete();
            }
        } catch (FIPAException e) {

            doDelete();
            e.printStackTrace();
        }

        // Creates the ABT Behaviour
        ABT_Kernel b = new ABT_Kernel(this);
        addBehaviour(b);

        // Reads arguments
        Object[] args = getArguments();

        // If there are arguments its an initiator agent
        if (args != null && args.length > 0) {

            ABT_Message arguments = new ABT_Message((String[]) args);

            if (arguments.getType().equals("ok?")) {

                try {

                    Control_Event = new Event(arguments.getHour(), arguments.getDescription(), Event.setArrayList(arguments.getIntervenients()), arguments.getPriority());
                    Control_Day = arguments.getDay();
                } catch (Exception e) {

                    e.printStackTrace();
                }

                // Runs for every intervenient
                for (int i = 0; i < arguments.getIntervenients().length; i++) {

                    // Only in case the intervenient is not itself
                    if (!arguments.getIntervenients()[i].equals(ABT_Agent.getAgentName())) {

                        DFAgentDescription targetAgent = new DFAgentDescription();
                        ServiceDescription targetService = new ServiceDescription();
                        targetService.setType(arguments.getIntervenients()[i]);
                        targetAgent.addServices(targetService);

                        ACLMessage msg = new ACLMessage(ACLMessage.REQUEST); // REQUEST for 'adl' and 'ok?' messages
                        try {

                            DFAgentDescription[] searchAgent = DFService.search(this, targetAgent); // agent find

                            msg.addReceiver(searchAgent[0].getName()); // send to agent
                            msg.setContent(arguments.toString());

                            send(msg);
                        } catch (FIPAException e) {

                            doDelete();
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    // Agent Shutdown
    protected void takeDown() {

        System.out.println("Agent " + getAID().getName() + " terminating.");
    }
}
