package com.example.merchant.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "merchants")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Merchant {

    @Id
    private String id;

    @NotBlank
    @Size(max = 40)
    private String name;

    @NotBlank
    @Size(max = 200)
    private String description;

    @NotBlank
    @Size(max = 40)
    @Email
    private String email;

    @Enumerated(EnumType.STRING)
    @NaturalId
    @Column(length = 60, columnDefinition = "ENUM('ACTIVE', 'INACTIVE')")
    private MerchantStatus status;

    @Min(0)
    private int totalTransactionSum;

    @Builder.Default
    @OneToMany(
            mappedBy = "merchant",
            fetch = FetchType.EAGER)
    private Set<Transaction> transactions = new HashSet<>();

    @PrePersist
    public void prePersist() {
        this.id = UUID.randomUUID().toString();
    }
}
