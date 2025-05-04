package org.spring.authenticationservice.DTO.donation;

import lombok.Builder;
import lombok.Data;

import java.math.BigInteger;
import java.time.LocalDateTime;

@Data
@Builder
public class DonationForRequestDTO {

    private String donorName;
    private Double donatedAmount;
    private String donationMessage;
    LocalDateTime donationDate;
    private String donationRequestTitle;

}
