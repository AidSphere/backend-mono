package org.spring.authenticationservice.DTO.donor;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CreateDonationDTO {
    private Long donationRequestId;
    private boolean donationStatus;
    private Double donationAmount;
    private String donationMessage;
    private Boolean donationMessageVisibility;
}
