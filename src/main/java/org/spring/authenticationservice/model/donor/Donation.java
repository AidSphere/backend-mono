package org.spring.authenticationservice.model.donor;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="donations")
@Getter
@Setter
public class Donation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // Automatically generate ID
    private Long DonationID;

    @JsonProperty("donorid")
    @Column(nullable = false)
    private Long donorid;

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
    @Column(nullable = false)
    private String personalizedMessage;

    @JsonProperty("isAnonymous")
    @Column(nullable = false)
    private Boolean isAnonymous;


    @JsonProperty("visibility")
    @Column(nullable = false)
    private Boolean visibility;

    public Boolean getAnonymous() {
        return isAnonymous;
    }

    public void setDonationID(Long donationID) {
        DonationID = donationID;
    }

    public void setQuotationID(Long quotationID) {
        QuotationID = quotationID;
    }

    public void setPatientID(Long patientID) {
        PatientID = patientID;
    }

    public void setAmount(Double amount) {
        Amount = amount;
    }

    public void setPersonalizedMessage(String personalizedMessage) {
        this.personalizedMessage = personalizedMessage;
    }

    public void setAnonymous(Boolean anonymous) {
        isAnonymous = anonymous;
    }

    public void setVisibility(Boolean visibility) {
        this.visibility = visibility;
    }

    public String getPersonalizedMessage() {
        return personalizedMessage;
    }

    public Double getAmount() {
        return Amount;
    }

    public Long getPatientID() {
        return PatientID;
    }

    public Long getDonationID() {
        return DonationID;
    }

    public Long getQuotationID() {
        return QuotationID;
    }

    public Boolean getVisibility() {
        return visibility;
    }
}
