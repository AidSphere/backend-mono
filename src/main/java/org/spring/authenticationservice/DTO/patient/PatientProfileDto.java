package org.spring.authenticationservice.DTO.patient;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PatientProfileDto {
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