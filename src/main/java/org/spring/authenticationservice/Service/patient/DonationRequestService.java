package org.spring.authenticationservice.Service.patient;

import lombok.RequiredArgsConstructor;
import org.spring.authenticationservice.DTO.patient.donation.DonationRequestCreateDto;
import org.spring.authenticationservice.DTO.patient.donation.DonationRequestResponseDto;
import org.spring.authenticationservice.DTO.patient.donation.DonationRequestUpdateDto;
import org.spring.authenticationservice.mapper.patient.DonationRequestMapper;
import org.spring.authenticationservice.model.patient.DonationRequest;
import org.spring.authenticationservice.model.patient.Patient;
import org.spring.authenticationservice.repository.patient.DonationRequestRepo;
import org.spring.authenticationservice.repository.patient.PatientRepo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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



}
