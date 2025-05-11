package org.spring.authenticationservice.DTO.drugImporter;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.spring.authenticationservice.DTO.donation.DonationRequestResponseDto;
import org.spring.authenticationservice.DTO.patient.PatientResponseDto;
import org.spring.authenticationservice.model.drugImporter.RequestStatus;

/**
 * Data Transfer Object for detailed Drug Importer Request information
 * Combines donation request, patient data, and request status
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DrugImporterRequestDetailDto {
    private DonationRequestResponseDto donationRequest;
    private PatientResponseDto patient;
    private RequestStatus requestStatus;
}
