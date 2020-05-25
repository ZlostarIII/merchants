package com.example.gateway.kafka;

import com.example.gateway.model.kafka.Merchant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

@Component
@EnableBinding(ProducerChannel.class)
public class ProducerBroker {

    public static final Logger log = LoggerFactory.getLogger(ProducerBroker.class);

    private final ProducerChannel producerChannel;

    @Autowired
    public ProducerBroker(ProducerChannel producerChannel) {
        this.producerChannel = producerChannel;
    }

    public void sendMerchant(Merchant merchant) {
        log.info("Merchant sent {}", merchant.getName());
        producerChannel.merchant().send(message(merchant));
    }

    private static <T> Message<T> message(T val) {
        return MessageBuilder.withPayload(val).build();
    }

}
