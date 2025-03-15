package org.spring.authenticationservice.model;


import jakarta.persistence.*;
import lombok.Data;
import org.spring.authenticationservice.model.Enum.StatusEnum;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "quotations")
public class DrugImpoQuoteRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long importerId;

    private Long donationRequestId;

    private String licenseNumber;

    private Double totalPrice;

    private String specialNotes;

    private LocalDate expiryDate;

    @Enumerated(EnumType.STRING)
    private StatusEnum status = StatusEnum.PENDING;

    private Long adminId;

    private LocalDate decisionDate;
}
