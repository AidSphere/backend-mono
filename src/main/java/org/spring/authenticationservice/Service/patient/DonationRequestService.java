package org.spring.authenticationservice.Service.patient;

import lombok.RequiredArgsConstructor;
import org.spring.authenticationservice.DTO.donation.DonationRequestCreateDto;
import org.spring.authenticationservice.DTO.donation.DonationRequestResponseDto;
import org.spring.authenticationservice.DTO.donation.DonationRequestUpdateDto;
import org.spring.authenticationservice.mapper.patient.DonationRequestMapper;
import org.spring.authenticationservice.model.Enum.StatusEnum;
import org.spring.authenticationservice.model.patient.DonationRequest;
import org.spring.authenticationservice.model.patient.Patient;
import org.spring.authenticationservice.repository.patient.DonationRequestRepo;
import org.spring.authenticationservice.repository.patient.PatientRepo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DonationRequestService {

    private final DonationRequestRepo donationRequestRepository;
    private final PatientRepo patientRepository;
    private final DonationRequestMapper mapper;

    @Transactional
    public DonationRequestResponseDto createDonationRequest(DonationRequestCreateDto dto) {
        // Fetch patient
        Patient patient = patientRepository.findById(dto.getPatientId())
                .orElseThrow(() -> new RuntimeException("Patient not found"));
        DonationRequest donationRequest = mapper.FromCreatetoDonationRequest(dto);
        donationRequest.setPatient(patient);
        // Save to DB
        DonationRequest savedRequest = donationRequestRepository.save(donationRequest);

        return mapper.toResponseDto(savedRequest);
    }

    @Transactional
    public DonationRequestResponseDto updateDonationRequest(Long requestId, DonationRequestUpdateDto dto) {
        DonationRequest existingRequest = donationRequestRepository.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Donation request not found"));
        // authorization to be handled

        if(existingRequest.getStatus() != StatusEnum.PENDING) {
            throw new RuntimeException("Only pending requests can be updated");
        }
        DonationRequest updatedRequest = mapper
                .updateDonationRequestFromDto(dto, existingRequest);

        DonationRequest savedRequest = donationRequestRepository.save(updatedRequest);

        return mapper.toResponseDto(savedRequest);
    }


    @Transactional
    public void deleteDonationRequest(Long requestId) {
        DonationRequest existingRequest = donationRequestRepository.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Donation request not found"));

        // logic to be added (e.g., checking ownership)
        donationRequestRepository.delete(existingRequest);
    }


    @Transactional(readOnly = true)
    public List<DonationRequestResponseDto> getPendingDonationRequests() {
        List<DonationRequest> pendingRequests = donationRequestRepository
                .findByStatus(StatusEnum.PENDING);
        return pendingRequests.stream()
                .map(mapper::toResponseDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public DonationRequestResponseDto updateConfirmation(
            Long requestId,
            StatusEnum status,
            String messageToPatient) {
        DonationRequest request = donationRequestRepository.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Donation request not found"));

        request.setStatus(status);
        request.setMessageToPatient(messageToPatient);
        request.setAdminApprovedAt(LocalDateTime.now());
        // TODO: Set adminId from security context
//        request.setAdminId(SecurityContextHolder.getContext().getAuthentication().getName());

        DonationRequest savedRequest = donationRequestRepository.save(request);
        return mapper.toResponseDto(savedRequest);
    }

}
