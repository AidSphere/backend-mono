package org.spring.authenticationservice.DTO.patient;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.spring.authenticationservice.model.Enum.PatientIDType;
import org.spring.authenticationservice.model.Enum.StatusEnum;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class VerificationResponseDto {
    private Long verificationId;
    private PatientIDType governmentIdType;
    private String governmentIdNumber;
    private String governmentIdDocumentUrl;
    private StatusEnum verificationStatus;
}