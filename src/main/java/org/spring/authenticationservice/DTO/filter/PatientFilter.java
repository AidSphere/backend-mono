package org.spring.authenticationservice.DTO.filter;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PatientFilter {
    @Size(min = 2, max = 50, message = "First name must be between 2 and 50 characters")
    private String firstName;
    @Size(min = 2, max = 50, message = "Last name must be between 2 and 50 characters")
    private String lastName;
//    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "Date of birth must be in the format YYYY-MM-DD")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
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
}
