package org.spring.authenticationservice.DTO.patient.donation;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.spring.authenticationservice.model.Enum.StatusEnum;

// DonationRequestConfirmDto is used to confirm a donation request (admin only)
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DonationRequestConfirmDto {
    @NotNull(message = "Status is required")
    private StatusEnum status;
    private String messageToPatient;
}
