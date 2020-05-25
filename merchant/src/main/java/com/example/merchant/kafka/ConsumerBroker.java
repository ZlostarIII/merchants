package com.example.merchant.kafka;

import com.example.merchant.model.Merchant;
import com.example.merchant.service.MerchantService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@EnableBinding(ConsumerChannel.class)
public class ConsumerBroker {

    public static final Logger log = LoggerFactory.getLogger(ConsumerBroker.class);

    private final MerchantService merchantService;
    private final ConsumerChannel consumerChannel;

    @Autowired
    public ConsumerBroker(MerchantService merchantService, ConsumerChannel consumerChannel) {
        this.merchantService = merchantService;
        this.consumerChannel = consumerChannel;
    }

    @StreamListener(ConsumerChannel.INPUT_CHANNEL)
    public void receiveMerchant(String merchantStr) throws IOException {
        log.info("Imported merchant {}", merchantStr);
        Merchant merchant = new ObjectMapper().readValue(merchantStr, Merchant.class);
        merchant.setTotalTransactionSum(0);
        merchantService.save(merchant);
    }

}
