package com.aiad_schedules.algorithm.ABT;

import com.aiad_schedules.agent.ABT;

import com.aiad_schedules.schedule.Event;
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

    // ### ACTIVATE FOR TXT DEBUG ###
    static protected boolean DEBUG = false;
    // ### ACTIVATE FOR TXT DEBUG ###

    // Variables
    private boolean endService = false;
    private ABT ABT_Agent = new ABT();
    private int Control_Day = -1;
    private Event Control_Event = null;
    private int Control_Intervenients = 0;

    // Internal Behaviour Classes
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

            // Receives a Message
            ACLMessage msgReceived = blockingReceive();

            if (DEBUG) System.out.println(getLocalName() + ": recebi " + msgReceived.getContent());

            // Parsing
            String[] msgDecode = msgReceived.getContent().split(Pattern.quote(","));
            String msgSender = msgReceived.getSender().getName().split(Pattern.quote("@"))[0];

            ABT_Message msg = new ABT_Message(msgDecode);

            System.out.println("Current Agent: " + ABT_Agent.getAgentName() + "\n Message Received from " + msgSender + "\tMessage type: " + msg.getType());

            // Processes the Message
            if (msgReceived.getPerformative() == ACLMessage.ACCEPT_PROPOSAL) {

                // adl Message Actions
                if (msg.getType().equals("adl")) {

                    try {

                        ABT_Agent = ABT_Procedures.AddLink(ABT_Agent, msg, msgSender);

                        if (DEBUG) System.err.println("Control intervinients: " + Control_Intervenients);
                        if (DEBUG) System.err.println("My view size: " + ABT_Agent.getAgentView().size());
                        if (DEBUG) System.err.println("Message from: " + msgSender);

                        // Verifies if all values are consistent with the view
                        if (Control_Intervenients == ABT_Agent.getAgentView().size()) {

                            if (DEBUG) System.err.println("I am conistent!");

                            if (ABT_Procedures.CheckAgentView(ABT_Agent)) {

                                ABT_Message response;
                                // Send terminate Message
                                for (int i = 0; i < ABT_Agent.getAgentView().size(); i++) {

                                    response = new ABT_Message("done");
                                    sendMessage(response, ABT_Agent.getAgentView().get(i).getStoredAgent(), 3);
                                }

                                end = true;
                            }
                        }
                    } catch (Exception e) {

                        e.printStackTrace();
                    }
                }
            }

            if (msgReceived.getPerformative() == ACLMessage.PROPOSE) {

                // ok? Message Actions
                if (msg.getType().equals("ok?")) {

                    // Sets new Control Values
                    try {

                        Control_Day = msg.getDay();
                        Control_Event = msg.toEvent();
                        Control_Intervenients = msg.getIntervenients().size() - 1;

                        ABT_Message response;

                        switch (ABT_Procedures.ProcessInfo(ABT_Agent, msg, msgSender)) {

                            case 0: // Valid
                                response = new ABT_Message("adl", msg.getDescription(), msg.getPriority(), msg.getDay(), msg.getHour(), msg.getIntervenients());
                                sendMessage(response, msgSender, 2);
                                break;
                            case 1: // Already Assigned
                                break;
                            case 2: // Another Assigned Value
                                response = new ABT_Message("ngd", ABT_Agent.getAgentSelf().getSelfEvent().getDescription(), ABT_Agent.getAgentSelf().getSelfEvent().getPriority(), ABT_Agent.getAgentSelf().getSelfDay(), ABT_Agent.getAgentSelf().getSelfEvent().getHour(), ABT_Agent.getAgentSelf().getSelfEvent().getIntervenients());
                                sendMessage(response, msgSender, 1);
                                break;
                            default:
                                System.err.println("Error Encountered Processing Info!!");
                                doDelete();
                                end = true;
                        }
                    } catch (Exception e) {

                        e.printStackTrace();
                    }
                }
            }

            if (msgReceived.getPerformative() == ACLMessage.REJECT_PROPOSAL) {

                // ngd Message Actions
                if (msg.getType().equals("ngd")) {

                    try {

                        ABT_Message response;

                        // Resolves the conflict
                        ABT_Agent = ABT_Procedures.ResolveConflict(ABT_Agent, msg, msgSender);

                        // In case a new value is set
                        if (ABT_Agent.getAgentSelf() != null) {

                            Control_Day = ABT_Agent.getAgentSelf().getSelfDay();
                            Control_Event = ABT_Agent.getAgentSelf().getSelfEvent();
                            Control_Intervenients = ABT_Agent.getAgentSelf().getSelfEvent().getIntervenients().size() - 1;

                            //String type, String description, int priority, int day, int hour, ArrayList<String> intervenients
                            response = new ABT_Message("ok?", ABT_Agent.getAgentSelf().getSelfEvent().getDescription(), ABT_Agent.getAgentSelf().getSelfEvent().getPriority(), ABT_Agent.getAgentSelf().getSelfDay(), ABT_Agent.getAgentSelf().getSelfEvent().getHour(), ABT_Agent.getAgentSelf().getSelfEvent().getIntervenients());

                            // Runs for every intervenient
                            for (int i = 0; i < response.getIntervenients().size(); i++) {

                                // Only in case the intervenient is not itself
                                if (!response.getIntervenients().get(i).equals(ABT_Agent.getAgentName())) {

                                    sendMessage(response, response.getIntervenients().get(i), 0); //restarts the procedure
                                }
                            }
                        } // In case it is null (no solution)
                        else {

                            response = new ABT_Message("stp");

                            // Runs for every intervenient
                            for (int i = 0; i < Control_Event.getIntervenients().size(); i++) {

                                // Only in case the intervenient is not itself
                                if (!Control_Event.getIntervenients().get(i).equals(ABT_Agent.getAgentName())) {

                                    sendMessage(response, response.getIntervenients().get(i), 4); // sends fail message
                                }
                            }
                        }
                    } catch (Exception e) {

                        e.printStackTrace();
                    }
                }
            }

            if (msgReceived.getPerformative() == ACLMessage.FAILURE) {

                // stp Message Action
                if (msg.getType().equals("stp")) {

                    // Terminates the Agent
                    end = true;
                }
            }

            if (msgReceived.getPerformative() == ACLMessage.INFORM) {

                // done Message Action
                if (msg.getType().equals("done")) {

                    ABT_Agent = ABT_Procedures.ChangeValues(ABT_Agent);

                    // Terminates
                    end = true;
                }
            }
        }

        // Behaviour Finish
        public boolean done() {

            return end;
        }
    }

    // Message Creation Function
    private void sendMessage(ABT_Message msg, String msgReceiver, int type) {

        DFAgentDescription targetAgent = new DFAgentDescription();
        ServiceDescription targetService = new ServiceDescription();
        targetService.setType(msgReceiver);
        targetAgent.addServices(targetService);

        ACLMessage msgToSend = null;

        switch (type) {

            case 0:
                msgToSend = new ACLMessage((ACLMessage.PROPOSE));
                break;
            case 1:
                msgToSend = new ACLMessage((ACLMessage.REJECT_PROPOSAL));
                break;
            case 2:
                msgToSend = new ACLMessage((ACLMessage.ACCEPT_PROPOSAL));
                break;
            case 3:
                msgToSend = new ACLMessage(ACLMessage.INFORM);
                break;
            case 4:
                msgToSend = new ACLMessage(ACLMessage.FAILURE);
                break;
        }
        try {

            DFAgentDescription[] searchAgent = DFService.search(this, targetAgent); // agent find

            msgToSend.addReceiver(searchAgent[0].getName()); // send to agent
            msgToSend.setContent(msg.toString());

            send(msgToSend);
        } catch (FIPAException | NullPointerException e) {

            doDelete();
            e.printStackTrace();
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

        if (DEBUG) System.out.println("Before ARGS check");

        // If there are arguments its an initiator agent
        if (args != null && args.length > 1) {

            if (DEBUG) System.out.println("After ARGS check " + args.length + " " + args[0].toString());

            ABT_Message arguments = new ABT_Message((String[]) args);

            if (arguments.getType().equals("ok?")) {

                try {

                    // Check if there is already an event in the spot selected by the initiator agent
                    if (!ABT_Agent.getAgentSchedule().getWeekdays().get(arguments.getDay()).getSlots().get(arguments.getHour()).isEmpty()) {

                        System.err.println("Found an Event already located in the Initiator Agent! Terminating");
                        doDelete();
                        endService = true;
                    }

                    // Sets Control Event
                    Control_Day = arguments.getDay();
                    Control_Event = arguments.toEvent();
                    Control_Intervenients = arguments.getIntervenients().size() - 1;

                    // Sets Self View
                    ABT_Agent.setAgentSelf(new ABT.Self(Control_Day, Control_Event));
                } catch (Exception e) {

                    e.printStackTrace();
                }

                // Runs for every intervenient
                for (int i = 0; i < arguments.getIntervenients().size(); i++) {

                    // Only in case the intervenient is not itself
                    if (!arguments.getIntervenients().get(i).equals(ABT_Agent.getAgentName())) {

                        sendMessage(arguments, arguments.getIntervenients().get(i), 0);
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
