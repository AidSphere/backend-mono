package org.spring.authenticationservice.DTO.patient.donation;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.spring.authenticationservice.model.Enum.StatusEnum;

@Data
public class DonationRequestConfirmDto {
    @NotNull(message = "Status is required")
    private StatusEnum status;
    private String messageToPatient;
}
