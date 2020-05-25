package com.example.merchant.config;

import com.example.merchant.listener.TransactionStatesListener;
import com.example.merchant.model.TransactionEvents;
import com.example.merchant.model.TransactionStates;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;

import java.util.EnumSet;

@Configuration
@EnableStateMachine
public class TransactionStateMachineConfig extends EnumStateMachineConfigurerAdapter<TransactionStates, TransactionEvents> {

    @Override
    public void configure(StateMachineStateConfigurer<TransactionStates, TransactionEvents> states) throws Exception {
        states.withStates()
                .initial(TransactionStates.INITIAL)
                .end(TransactionStates.APPROVED)
                .end(TransactionStates.REFUNDED)
                .end(TransactionStates.REVERSED)
                .end(TransactionStates.ERROR)
                .states(EnumSet.allOf(TransactionStates.class));
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<TransactionStates, TransactionEvents> transitions) throws Exception {
        transitions
                .withExternal()
                .source(TransactionStates.INITIAL)
                .target(TransactionStates.APPROVED)
                .event(TransactionEvents.CHARGE)
                .and()
                .withExternal()
                .source(TransactionStates.APPROVED)
                .target(TransactionStates.REFUNDED)
                .event(TransactionEvents.REFUND)
                .and()
                .withExternal()
                .source(TransactionStates.INITIAL)
                .target(TransactionStates.REVERSED)
                .event(TransactionEvents.REVERSE);
    }

    @Override
    public void configure(StateMachineConfigurationConfigurer<TransactionStates, TransactionEvents> config) throws Exception {
        config.withConfiguration()
                .autoStartup(false)
                .listener(new TransactionStatesListener());
    }
    
}
