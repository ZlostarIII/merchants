package com.example.merchant.controller.utils;

import com.example.merchant.exception.AppException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Base64;

@Component
public class ControllerUtils {

    private static Logger logger = LoggerFactory.getLogger(ControllerUtils.class);

    public String extractEmailFromToken(String header) {
        String email;
        try {
            email = getEmailFromToken(header);
        } catch (Exception e) {
            logger.error("Error in ControllerUtils.extractEmailFromToken: ", e);
            throw new AppException("No email found");
        }
        return email;
    }

    public String getEmailFromToken(String header) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        String json = parseTokenToJson(header);
        JsonNode node = mapper.readTree(json);
        String email = node.findValue("email").asText();
        return email != null ? email.toLowerCase() : null;
    }

    private String parseTokenToJson(String header) throws IOException {
        String[] tokenValue = header.split("Bearer");
        String token = tokenValue[1].trim();
        String[] parts = token.split("\\.");
        return new String(Base64.getDecoder().decode(parts[1]));
    }

}
