package org.spring.authenticationservice.DTO.patient.donation;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;
import org.spring.authenticationservice.model.patient.PrescribedMedicine;

import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DonationRequestUpdateDto {

    @NotBlank(message = "Title is required")
    @Size(min = 3, max = 100, message = "Title must be between 3 and 100 characters")
    private String title;

    @NotBlank(message = "Description is required")
    @Size(min = 10, max = 1000, message = "Description must be between 10 and 1000 characters")
    private String description;

    @NotBlank(message = "Prescription URL is required")
    @URL( message = "Invalid URL format")
    private String prescriptionUrl;

    @NotNull(message = "Expected date is required")
    @Future(message = "Expected date must be in the future")
    private Date expectedDate;

    @NotBlank(message = "Hospital name is required")
    @Size(min = 2, max = 100, message = "Hospital name must be between 2 and 100 characters")
    private String hospitalName;

    @Size(max = 5, message = "Maximum 5 images allowed")
    private List<@URL(message = "Invalid image URL format")  String> images;

    @Size(max = 5, message = "Maximum 5 documents allowed")
    private List<@URL(message = "Invalid document URL format") String> documents;

    @NotEmpty(message = "At least one prescribed medicine is required")
    @Size(max = 20, message = "Maximum 20 prescribed medicines allowed")
    private List<PrescribedMedicine> prescribedMedicines;

}
