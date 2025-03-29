package org.spring.authenticationservice.DTO.patient;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

import java.time.LocalDate;

// DTO class for updating patient information
@Data
public class PatientUpdateDto {
    @Size(min = 2, max = 50, message = "Last name must be between 2 and 50 characters")
    private String firstName;
    @Size(min = 2, max = 50, message = "Last name must be between 2 and 50 characters")
    private String lastName;

    @Past(message = "Date of birth must be in the past")
    private LocalDate dateOfBirth;

    @Pattern(regexp = "^(MALE|FEMALE|OTHER)$", message = "Gender must be MALE, FEMALE, or OTHER")
    private String gender;

    @Pattern(regexp = "^\\+?[0-9]{10,15}$", message = "Invalid phone number format")
    private String phoneNumber;

    @Email(message = "Invalid email format")
    private String email;

    @Size(max = 255, message = "Permanent address cannot exceed 255 characters")
    private String permanentAddress;

    @Size(max = 255, message = "Current address cannot exceed 255 characters")
    private String currentAddress;

    @URL(message = "Invalid profile image URL")
    private String profileImageUrl;
}
