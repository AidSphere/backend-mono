package org.spring.authenticationservice.DTO.donor;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.spring.authenticationservice.model.patient.Patient;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RequestToGeminiDto {

    private Long requestId;

    private String UserDescription;

    private String patientName;

    private String patientAddress;

    private String donationRequestTitle;

    private  String donationRequestDescription;

    private String donationRequestMessageToPatient;
}
