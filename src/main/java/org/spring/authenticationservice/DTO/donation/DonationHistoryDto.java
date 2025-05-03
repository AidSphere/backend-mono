package org.spring.authenticationservice.DTO.donation;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class DonationHistoryDto {
    private Long id;
    private Double amount;
    private String message;
    private LocalDateTime date;
    private Boolean status;
}
