package org.spring.authenticationservice.mapper.patient.donation;

import org.spring.authenticationservice.DTO.donation.DonationRequestResponseDto;
import org.spring.authenticationservice.model.patient.DonationRequest;

public class DonationRequestMapperMannual {

    public static DonationRequestResponseDto toDonationResponseDto(
            DonationRequest donationRequest,
            DonationRequestResponseDto dto) {

        dto.setRequestId(donationRequest.getRequestId());
        dto.setPatientId(donationRequest.getPatient().getPatientId());
        dto.setTitle(donationRequest.getTitle());
        dto.setDescription(donationRequest.getDescription());
        dto.setPrescriptionUrl(donationRequest.getPrescriptionUrl());
        dto.setStatus(donationRequest.getStatus());
        dto.setCreatedAt(donationRequest.getCreatedAt());
        dto.setExpectedDate(donationRequest.getExpectedDate());
        dto.setHospitalName(donationRequest.getHospitalName());
        dto.setMessageToPatient(donationRequest.getMessageToPatient());
        dto.setAdminId(donationRequest.getAdminId());
        dto.setAdminApprovedAt(donationRequest.getAdminApprovedAt());
        dto.setDefaultPrice(donationRequest.getDefaultPrice());
        dto.setImages(donationRequest.getImages());
        dto.setDocuments(donationRequest.getDocuments());
        dto.setPrescribedMedicines(donationRequest.getPrescribedMedicines());

        return dto;
    }

}
