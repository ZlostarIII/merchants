package com.example.gateway.model.kafka;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Merchant {

    private static String active = "ACTIVE";

    private String name;
    private String description;
    private String email;
    private String status;

    public Merchant(String name, String description, String email) {
        this.name = name;
        this.description = description;
        this.email = email;
        this.status = active;
    }

}
