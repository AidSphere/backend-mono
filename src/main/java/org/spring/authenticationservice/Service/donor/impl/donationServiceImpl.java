package org.spring.authenticationservice.Service.donor.impl;

import lombok.AllArgsConstructor;
import org.spring.authenticationservice.DTO.donation.DonationForRequestDTO;
import org.spring.authenticationservice.DTO.donation.DonationHistoryDto;
import org.spring.authenticationservice.DTO.donation.DonationRequestResponseDto;
import org.spring.authenticationservice.DTO.donor.CreateDonationDTO;
import org.spring.authenticationservice.Service.donor.DonationService;
import org.spring.authenticationservice.Utils.SecurityUtil;
import org.spring.authenticationservice.model.donor.Donation;
import org.spring.authenticationservice.model.donor.Donor;
import org.spring.authenticationservice.model.patient.DonationRequest;
import org.spring.authenticationservice.repository.donor.DonationRepository;
import org.spring.authenticationservice.repository.donor.DonorRepository;
import org.spring.authenticationservice.repository.patient.DonationRequestRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class donationServiceImpl implements DonationService {
    private final SecurityUtil securityUtil;
    private DonationRequestRepo donationRequestRepo;
    private DonorRepository donorRepo;
    private DonationRepository donationRepo;


    @Override
    public void createDonation(CreateDonationDTO createDonationDTO) {
        DonationRequest donationRequest = donationRequestRepo.findById(createDonationDTO.getDonationRequestId()).orElseThrow(
                () -> new RuntimeException("Donation request not found")
        );

        Donor donor = donorRepo.findByEmail(securityUtil.getUsername()).orElseThrow(
                () -> new RuntimeException("Donor not found")
        );

        Donation donation = Donation.builder()
                .donor(donor)
                .donationRequest(donationRequest)
                .donationMessage(createDonationDTO.getDonationMessage())
                .donationMessageVisibility(createDonationDTO.getDonationMessageVisibility())
                .donationStatus(createDonationDTO.isDonationStatus())
                .donationAmount(createDonationDTO.getDonationAmount())
                .build();
        donationRepo.save(donation);
    }

    @Override
    public List<DonationHistoryDto> getAllDonationByUser() {
        Donor donor = donorRepo.findByEmail(securityUtil.getUsername())
                .orElseThrow(() -> new RuntimeException("Donor not found"));

        List<Donation> donations = donationRepo.findAllByDonor(donor);

        return donations.stream()
                .map(donation -> DonationHistoryDto.builder()
                        .id(donation.getId())
                        .status(donation.getDonationStatus())
                        .date(donation.getDonationDate())
                        .amount(donation.getDonationAmount())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public List<DonationForRequestDTO> getDonationByRequest(Long id) {
        DonationRequest donationRequest = donationRequestRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Donation request not found"));

        List<Donation> donations = donationRepo.findAllByDonationRequest(donationRequest);

        return donations.stream()
                .map(donation -> DonationForRequestDTO.builder()
                        .donationRequestTitle(donation.getDonationRequest().getTitle())
                        .donationMessage(donation.getDonationMessage())
                        .donationDate(donation.getDonationDate())
                        .donatedAmount(donation.getDonationAmount())
                        .donorName(donation.getDonor().getFirstName() + " " + donation.getDonor().getLastName())
                        .build())
                .toList();
    }


}

