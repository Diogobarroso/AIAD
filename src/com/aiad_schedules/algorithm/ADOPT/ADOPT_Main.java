package com.aiad_schedules.algorithm.ADOPT;

import com.aiad_schedules.agent.ADOPT;

import com.aiad_schedules.schedule.Event;
import jade.core.Agent;
import jade.core.behaviours.SimpleBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Pattern;

// Main ADOPT based Agent Class
public class ADOPT_Main extends Agent {

    // ### ACTIVATE FOR TXT DEBUG ###
    static public boolean DEBUG = true;
    // ### ACTIVATE FOR TXT DEBUG ###

    // Variables
    private boolean endService = false;
    private ADOPT ADOPT_Agent = new ADOPT();
    private int Control_Day = -1;
    private Event Control_Event = null;
    private int Control_Intervenients = 0;
    private boolean root = false;
    private int[] options; //The different times the scheduler considers scheduling the event to
    int threshold;
    int lowerBound;
    int upperBound;
    boolean sentValue = false;
    boolean search = true; //best first search for true, depth first for false
    String child;
    String father;
    HashMap<String, ADOPT_Message> currentContext;
    HashMap<String, Integer> lb;
    HashMap<String, Integer> ub;
    HashMap<Integer, HashMap<String, Integer>> t;

    // Internal Behaviour Classes
    // ADOPT Kernel Behaviour Class
    class ADOPT_Kernel extends SimpleBehaviour {

        // Variables
        protected boolean end = false; // Kernel Control

        // Constructor
        public ADOPT_Kernel(Agent a) {

            super(a);
        }

        // Behaviour Action
        public void action() {

            if(root)
            {
                if(threshold == upperBound)
                {
                    ADOPT_Message terminate = new ADOPT_Message(Control_Event.getDescription(), Control_Event.getCost(), Control_Day, Control_Event.getHour(), Control_Event.getIntervenients());
                    terminate.setCost(-1);
                    sendMessage(terminate, child, false);
                }
                if(threshold < lowerBound || search) {
                    selectBestLB();
                    ADOPT_Message aMsg = new ADOPT_Message(Control_Event.getDescription(), Control_Event.getCost(), Control_Day, Control_Event.getHour(), Control_Event.getIntervenients());
                    sendMessage(aMsg, child, false);
                } else {
                    ADOPT_Message aMsg = new ADOPT_Message(Control_Event.getDescription(), Control_Event.getCost(), Control_Day, Control_Event.getHour(), Control_Event.getIntervenients());
                    sendMessage(aMsg, child, true);
                }
            }

            // Receives a Message
            MessageTemplate msgTemplate = MessageTemplate.MatchConversationId("ADOPT");
            ACLMessage msgReceived = blockingReceive(msgTemplate);

            if(father == null)
               father = msgReceived.getSender().getName();


            if (DEBUG) System.out.println(getLocalName() + ": recebi " + msgReceived.getContent());

            // Parsing
            String[] msgDecode = msgReceived.getContent().split(Pattern.quote(","));
            String msgSender = msgReceived.getSender().getName().split(Pattern.quote("@"))[0];
            ADOPT_Message msg = new ADOPT_Message(msgDecode);

            // Processes the Message
            if (msgReceived.getPerformative() == ACLMessage.INFORM) { //The sender is informing me of their value by my request or because they changed it
                if(msg.getCost() == -1) {
                    if(child != null)
                        sendMessage(msg, child, false);
                    done();
                }
                if(msgDecode[0] == "COST")
                        receiveCost(msgDecode, msgSender);
                else if(msgDecode[0] == "VALUE")
                    receiveValue(msg, msgSender);

            } else if (msgReceived.getPerformative() == ACLMessage.REQUEST) { //I'm being requested to communicate my value
                receiveRequest(msg, msgSender);
            }
        }

        // Behaviour Finish
        public boolean done() {

            return end;
        }
    }

    public void selectBestLB() {
        for(Integer value : lb.values()) {
            if(value < lowerBound) {
                lowerBound = value;
                search = false;
            }
            if(lowerBound >= threshold) {
                threshold = lowerBound;
                search = true;
            }
        }
    }

    public void receiveCost(String[] msg, String sender) {
        if(root)
            if(threshold < Integer.parseInt(msg[1]))
                ub.put(sender, Integer.parseInt(msg[1]));
            else
                lb.put(sender, Integer.parseInt(msg[1]));
        else {
            ADOPT_Message msgToSend = new ADOPT_Message(msg);

            if (!ADOPT_Agent.getAgentSchedule().getWeekdays().get(msgToSend.getDay()).getSlots().get(msgToSend.getHour()).getDescription().isEmpty())
                msgToSend.incrementCost();
            sendMessage(msgToSend, father, false);
        }
    }

    public void receiveValue(ADOPT_Message msg, String sender) { //event
        currentContext.put(sender, msg);
    }

    public void receiveRequest(ADOPT_Message msg, String sender) {
        if(sentValue)
        {
            sendMessage(msg, child, true);
        } else {
            if (ADOPT_Agent.getAgentSchedule().getWeekdays().get(msg.getDay()).getSlots().get(msg.getHour()).getDescription().isEmpty())
                msg.setCost(0);
            else
                msg.setCost(1);

            sentValue = true;

            sendMessage(msg, father, false);
        }
    }

    // Message Creation Function
    private void sendMessage(ADOPT_Message msg, String msgReceiver, boolean type){

        DFAgentDescription targetAgent = new DFAgentDescription();
        ServiceDescription targetService = new ServiceDescription();
        targetService.setType(msgReceiver);
        targetAgent.addServices(targetService);

        ACLMessage msgToSend = null;

        if(type) //0 for inform, 1 for request
            msgToSend = new ACLMessage(ACLMessage.INFORM);
        else
            msgToSend = new ACLMessage(ACLMessage.REQUEST);


        try {

            DFAgentDescription[] searchAgent = DFService.search(this, targetAgent); // agent find

            msgToSend.setConversationId("ADOPT");
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
        ADOPT_Agent.setAgentName(nameTrim[0]);
        try {

            // All files present in the "files" directory
            ADOPT_Agent.setAgentSchedule("files/" + nameTrim[0] + ".csv");
        } catch (IOException e) {

            doDelete();
            e.printStackTrace();
        }

        // Register in DF
        DFAgentDescription dfd = new DFAgentDescription(); // all agent descriptions
        dfd.setName(getAID());
        ServiceDescription sd = new ServiceDescription(); // All service; all functionalities
        sd.setName(getName()); // Describes agent name by JADE name
        sd.setType(ADOPT_Agent.getAgentName()); // Describes agent real name on the service
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
        equalAgentName.setType(ADOPT_Agent.getAgentName()); // Search on the type
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

        // Creates the ADOPT Behaviour
        ADOPT_Kernel b = new ADOPT_Kernel(this);
        addBehaviour(b);

        // Reads arguments
        Object[] args = getArguments();

        if (DEBUG) System.out.println("Before ARGS check");

        // If there are arguments its an initiator agent
        if (args != null && args.length > 1) {
            root = true;
            if (DEBUG) System.out.println("After ARGS check " + args.length + " " + args[0].toString());
            System.out.println(args);
            ADOPT_Message arguments = new ADOPT_Message((String[]) args);

            if (arguments.getType().equals("ok?")) {
                try {

                    // Check if there is already an event in the spot selected by the initiator agent

                    if (ADOPT_Agent.getAgentSchedule().getWeekdays().get(arguments.getDay()).getSlots().get(arguments.getHour()).getDescription().isEmpty()) {

                        System.err.println("Found an Event already located in the Initiator Agent! Terminating");
                        doDelete();
                    }

                    // Sets Control Event
                    Control_Day = arguments.getDay();
                    Control_Event = arguments.toEvent();
                    Control_Intervenients = arguments.getIntervenients().size();

                    // Sets Self View
                    ADOPT_Agent.setAgentSelf(new ADOPT.Self(Control_Day, Control_Event));
                } catch (Exception e) {

                    e.printStackTrace();
                }
            }
        }
    }

    // Agent Shutdown
    protected void takeDown() {

        System.out.println("Agent " + getAID().getName() + " terminating.");
    }
}
