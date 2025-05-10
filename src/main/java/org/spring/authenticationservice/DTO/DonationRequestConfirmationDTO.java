package org.spring.authenticationservice.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import org.spring.authenticationservice.model.Enum.StatusEnum;


public class DonationRequestConfirmationDTO {

    @JsonProperty("Status")
    private StatusEnum Status;

    public StatusEnum getStatus() {
        return Status;
    }
}
