package com.example.merchant.kafka;

import com.example.merchant.exception.AppException;
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
import java.util.Optional;

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

    @StreamListener(ConsumerChannel.ADMIN_CHANNEL)
    public void deleteMerchant(String merchantIdentifier) throws IOException {
        log.info("Merchant set for deletion {}", merchantIdentifier);
        Optional<Merchant> merchant = merchantService.findByNameOrEmail(merchantIdentifier, merchantIdentifier);
        merchant.ifPresent(m -> {
            if (m.getTransactions().stream().count() > 0) {
                throw new AppException(String.format("Cannot delete merchant {} with active transactions", merchantIdentifier));
            } else {
                merchantService.delete(m);
            }
        });
    }

}
