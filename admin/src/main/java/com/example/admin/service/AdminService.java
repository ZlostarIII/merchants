package com.example.admin.service;

import com.example.admin.contoller.AdminController;
import com.example.admin.kafka.ProducerBroker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

    private static Logger logger = LoggerFactory.getLogger(AdminController.class);

    private final ProducerBroker producerBroker;

    public AdminService(ProducerBroker producerBroker) {
        this.producerBroker = producerBroker;
    }

    public String setMerchantForDeletion(String merchantIdentifier) {
        producerBroker.setMerchantForDeletion(merchantIdentifier);
        return String.format("Merchant with identifier {} marked for deletion", merchantIdentifier);
    }

}
