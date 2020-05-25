package com.example.merchant.listener;

import com.example.merchant.model.TransactionEvents;
import com.example.merchant.model.TransactionStates;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.listener.StateMachineListener;
import org.springframework.statemachine.state.State;
import org.springframework.statemachine.transition.Transition;

public class TransactionStatesListener implements StateMachineListener<TransactionStates, TransactionEvents> {

    private static final Logger LOGGER = LoggerFactory.getLogger(TransactionStatesListener.class);

    @Override
    public void stateChanged(State<TransactionStates, TransactionEvents> from, State<TransactionStates, TransactionEvents> to) {
        LOGGER.info("State changed from {} to {}", from.getId().name(), to.getId().name());
    }

    @Override
    public void stateEntered(State<TransactionStates, TransactionEvents> state) {
        LOGGER.info("Entered state {}", state.getId().name());
    }

    @Override
    public void stateExited(State<TransactionStates, TransactionEvents> state) {
        LOGGER.info("Exited state {}",  state.getId().name());
    }

    @Override
    public void eventNotAccepted(Message<TransactionEvents> event) {
        LOGGER.error("Event not accepted at this state: {}", event.getPayload());
    }

    @Override
    public void transition(Transition<TransactionStates, TransactionEvents> transition) {
        LOGGER.info("Transition from state {} to state {}", transition.getSource(), transition.getTarget());
    }

    @Override
    public void transitionStarted(Transition<TransactionStates, TransactionEvents> transition) {

    }

    @Override
    public void transitionEnded(Transition<TransactionStates, TransactionEvents> transition) {

    }

    @Override
    public void stateMachineStarted(StateMachine<TransactionStates, TransactionEvents> stateMachine) {
        LOGGER.info("Machine started: {}", stateMachine);
    }

    @Override
    public void stateMachineStopped(StateMachine<TransactionStates, TransactionEvents> stateMachine) {
        LOGGER.info("Machine stopped: {}", stateMachine);
    }

    @Override
    public void stateMachineError(StateMachine<TransactionStates, TransactionEvents> stateMachine, Exception exception) {
        LOGGER.info("Machine error: {}", stateMachine);
    }

    @Override
    public void extendedStateChanged(Object key, Object value) {

    }

    @Override
    public void stateContext(StateContext<TransactionStates, TransactionEvents> stateContext) {

    }
    
}
