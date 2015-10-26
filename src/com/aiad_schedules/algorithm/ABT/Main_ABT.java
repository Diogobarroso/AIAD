package com.aiad_schedules.algorithm.ABT;

import com.aiad_schedules.agent.ABT_Agent;
import jade.core.behaviours.*;
import jade.lang.acl.ACLMessage;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.DFService;
import jade.domain.FIPAException;

import java.io.InvalidObjectException;


public class Main_ABT extends ABT_Agent {

    public Main_ABT() throws InvalidObjectException {

        super();
    }

    class ABT_Behaviour extends SimpleBehaviour {

        public ABT_Behaviour(ABT_Agent a) {

            super(a);
        }

        public void action(){


        }

        public boolean done() {

            return true;
        }

    }

    protected void setup(){

    }

    protected void takeDown(){

    }
}
