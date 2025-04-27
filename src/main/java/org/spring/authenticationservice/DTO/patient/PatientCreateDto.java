package org.spring.authenticationservice.DTO.patient;

import jakarta.validation.constraints.*;
import lombok.Data;
import org.hibernate.validator.constraints.URL;
import org.spring.authenticationservice.model.Enum.PatientIDType;

import java.time.LocalDate;

// DTO class to represent the patient creation request
@Data
public class PatientCreateDto {
    @Size(min = 2, max = 50, message = "First name must be between 2 and 50 characters")
    private String firstName;

    @NotBlank(message = "Last name is required")
    @Size(min = 2, max = 50, message = "Last name must be between 2 and 50 characters")
    private String lastName;

    @NotNull(message = "Date of birth is required")
    @Past(message = "Date of birth must be in the past")
    private LocalDate dateOfBirth;

    @Pattern(regexp = "^(MALE|FEMALE|OTHER)$", message = "Gender must be MALE, FEMALE, or OTHER")
    private String gender;

    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "^\\+?[0-9]{10,15}$", message = "Invalid phone number format")
    private String phoneNumber;

    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "Permanent address is required")
    @Size(max = 255, message = "Permanent address cannot exceed 255 characters")
    private String permanentAddress;

    @Size(max = 255, message = "Current address cannot exceed 255 characters")
    private String currentAddress;

    @URL(message = "Invalid profile image URL")
    private String profileImageUrl;

    @NotNull(message = "Government ID type is required")
    private PatientIDType governmentIdType;

    @NotBlank(message = "Government ID number is required")
    @Pattern(regexp = "^[A-Z0-9]{5,20}$", message = "Invalid government ID number format")
    private String governmentIdNumber;

    @NotNull(message = "Government ID document URL is required")
    @URL(message = "Invalid government ID document URL")
    private String governmentIdDocumentUrl;
}
