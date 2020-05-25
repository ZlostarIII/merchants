package com.example.merchant.kafka;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;
import org.springframework.stereotype.Component;

@Component
public interface ConsumerChannel {

    String INPUT_CHANNEL = "merchant";
    String ADMIN_CHANNEL = "merchant-for-deletion";

    @Input(INPUT_CHANNEL)
    SubscribableChannel merchantSource();

    @Input(ADMIN_CHANNEL)
    SubscribableChannel adminSource();

}
