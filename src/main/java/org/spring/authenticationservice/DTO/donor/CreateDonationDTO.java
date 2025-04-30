package org.spring.authenticationservice.DTO.donor;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CreateDonationDTO {
    private Long id;
    private String donorEmail;
    private Long donationRequestId;
    private String donationStatus;
    private LocalDateTime donationDate;
    private Double donationAmount;
}
