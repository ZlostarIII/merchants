package com.example.admin.kafka;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.stereotype.Component;

@Component
public interface ProducerChannel {

    @Output("merchant-for-deletion")
    MessageChannel merchant();

}
