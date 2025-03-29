package org.spring.authenticationservice.DTO.donation;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.spring.authenticationservice.model.Enum.StatusEnum;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DonationRequestConfirmDto {
    @NotNull(message = "Status is required")
    private StatusEnum status;
    private String messageToPatient;
}
