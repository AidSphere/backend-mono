package org.spring.authenticationservice.DTO.patient;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

// Data transfer object for sending the patient as response
@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PatientResponseDto {
    private Long patientId;
    private String firstName;
    private String lastName;
    private String dateOfBirth;
    private String gender;
    private String phoneNumber;
    private String email;
    private String permanentAddress;
    private String currentAddress;
    private String profileImageUrl;
    private VerificationResponseDto verification;
    private MedicalRecordDto medicalRecord;
}