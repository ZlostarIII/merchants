package com.example.merchant.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "transactions")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Transaction {

    @Id
    private String id;

    @Min(0)
    private int amount;

    @Enumerated(EnumType.STRING)
    @NaturalId
    @Column(length = 60, columnDefinition = "ENUM('APPROVED', 'REVERSED', 'REFUNDED', 'ERROR')")
    private TransactionStates status;

    @NotBlank
    @Size(max = 40)
    @Email
    private String customerEmail;

    private String customerPhone;

    private String referenceId;

    @NotNull
    private Instant created;

    @ManyToOne
    @JoinColumn(name = "merchant_id", referencedColumnName = "id")
    private Merchant merchant;

    @PrePersist
    public void prePersist() {
        this.id = UUID.randomUUID().toString();
        this.created = Instant.now();
    }

}
