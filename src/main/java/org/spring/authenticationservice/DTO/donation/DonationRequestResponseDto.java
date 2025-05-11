package org.spring.authenticationservice.DTO.donation;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.spring.authenticationservice.model.Enum.StatusEnum;
import org.spring.authenticationservice.model.patient.PrescribedMedicine;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

// Data Transfer Object for Donation Request as Response

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DonationRequestResponseDto {
    private Long requestId;
    private Long patientId; // Instead of full Patient object
    private String title;
    private String description;
    private String prescriptionUrl;
    private StatusEnum status;
    private LocalDateTime createdAt;
    private Date expectedDate;
    private String hospitalName;
    private String messageToPatient;
    private Long adminId;
    private LocalDateTime adminApprovedAt;
    private BigDecimal defaultPrice;
    private BigDecimal remainingPrice;
    private List<String> images;
    private List<String> documents;
    private List<PrescribedMedicine> prescribedMedicines;
}
