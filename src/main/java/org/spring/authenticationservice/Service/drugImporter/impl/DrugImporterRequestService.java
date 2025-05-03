package org.spring.authenticationservice.Service.drugImporter.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.spring.authenticationservice.DTO.donation.DonationRequestResponseDto;
import org.spring.authenticationservice.DTO.drugImporter.DrugImporterRequestDetailDto;
import org.spring.authenticationservice.DTO.patient.PatientResponseDto;
import org.spring.authenticationservice.Service.patient.DonationRequestService;
import org.spring.authenticationservice.Service.patient.PatientService;
import org.spring.authenticationservice.mapper.patient.PatientMapper;
import org.spring.authenticationservice.model.Enum.RequestStatusEnum;
import org.spring.authenticationservice.model.drugImporter.RequestStatus;
import org.spring.authenticationservice.model.patient.Patient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service class for handling drug importer donation request operations
 * Aggregates data from multiple services to provide comprehensive information
 */
@Service
@RequiredArgsConstructor
public class DrugImporterRequestService {

    private final DonationRequestService donationRequestService;
    private final PatientService patientService;
    private final RequestStatusServiceImpl requestStatusService;
    private final PatientMapper patientMapper;

    /**
     * Get all pending donation requests with detailed information
     * including patient details and request status
     *
     * @param drugImporterId The ID of the drug importer
     * @return List of detailed donation request DTOs
     */
    public List<DrugImporterRequestDetailDto> getAllDonationRequests(Long drugImporterId) {
        // Get all pending donation requests
        List<DonationRequestResponseDto> pendingRequests = donationRequestService.getAllDonationRequests();

        // Convert to detailed DTOs with additional information
        return pendingRequests.stream()
                .map(request -> convertToDrugImporterRequestDetail(request, drugImporterId))
                .collect(Collectors.toList());
    }

    /**
     * Get a specific donation request with detailed information
     * including patient details and request status
     *
     * @param requestId      The ID of the donation request
     * @param drugImporterId The ID of the drug importer
     * @return Detailed donation request DTO
     */
    public DrugImporterRequestDetailDto getDonationRequestById(Long requestId, Long drugImporterId) {
        // Get all pending donation requests
        List<DonationRequestResponseDto> allRequests = donationRequestService.getAllDonationRequests();

        // Find the specific request
        DonationRequestResponseDto request = allRequests.stream()
                .filter(req -> req.getRequestId().equals(requestId))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("Donation request not found with id: " + requestId));

        // Convert to detailed DTO with additional information
        return convertToDrugImporterRequestDetail(request, drugImporterId);
    }

    /**
     * Convert a donation request to a detailed DTO with patient and status
     * information. If no status exists for this drug importer, create a PENDING
     * status.
     *
     * @param request        The donation request DTO
     * @param drugImporterId The ID of the drug importer
     * @return Detailed donation request DTO
     */
    @Transactional
    protected DrugImporterRequestDetailDto convertToDrugImporterRequestDetail(
            DonationRequestResponseDto request, Long drugImporterId) {

        // Get patient information
        Patient patient = patientService.getPatientById(request.getPatientId());
        PatientResponseDto patientDto = patientMapper.toResponseDto(patient);

        // Get request status or create a new PENDING status if none exists
        RequestStatus currentStatus = getOrCreateRequestStatus(request.getRequestId(), drugImporterId);

        // Build the detailed DTO
        return DrugImporterRequestDetailDto.builder()
                .donationRequest(request)
                .patient(patientDto)
                .requestStatus(currentStatus)
                .build();
    }

    /**
     * Get existing request status or create a new PENDING status if none exists
     * 
     * @param requestId      The request ID
     * @param drugImporterId The drug importer ID
     * @return The current or newly created request status
     */
    @Transactional
    protected RequestStatus getOrCreateRequestStatus(Long requestId, Long drugImporterId) {
        // Try to find existing status for this drug importer and request
        Optional<RequestStatus> existingStatus = requestStatusService.findByRequestIdAndDrugImporterId(requestId,
                drugImporterId);

        // If status exists, return it
        if (existingStatus.isPresent()) {
            return existingStatus.get();
        }

        // Otherwise, create a new PENDING status
        RequestStatus newStatus = new RequestStatus();
        newStatus.setRequestId(requestId);
        newStatus.setDrugImporterId(drugImporterId);
        newStatus.setStatus(RequestStatusEnum.PENDING);

        // Save and return the new status
        return requestStatusService.save(newStatus);
    }
}