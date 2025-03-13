package org.spring.authenticationservice.model.Enum;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

public class Donation {
    @JsonProperty("DonationID")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int DonationID;


    @JsonProperty("QuotationID")
    @Column(unique = true, nullable = false)
    private Long QuotationID;

    @JsonProperty("PatientID")
    @Column(unique = true, nullable = false)
    private Long PatientID;

    @JsonProperty("Amount")
    @Column( nullable = false)
    private Double Amount;

    @JsonProperty("personalizedMessage")
    @Column(unique = true, nullable = false)
    private String personalizedMessage;

    @JsonProperty("isAnonymous")
    @Column(nullable = false)
    private Boolean isAnonymous;


    @JsonProperty("visibility")
    @Column(nullable = false)
    private Boolean visibility;


}
