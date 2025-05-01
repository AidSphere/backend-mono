package org.spring.authenticationservice.Service.donor.impl;

import lombok.AllArgsConstructor;
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
    public Donation createDonation(CreateDonationDTO createDonationDTO) {
        DonationRequest donationRequest = donationRequestRepo.findById(createDonationDTO.getId()).orElseThrow(
                () -> new RuntimeException("Donation request not found")
        );

        Donor donor = donorRepo.findByEmail(securityUtil.getUsername()).orElseThrow(
                () -> new RuntimeException("Donor not found")
        );

        Donation donation = Donation.builder()
                .id(createDonationDTO.getId())
                .donor(donor)
                .donationRequest(donationRequest)
                .donationStatus(createDonationDTO.getDonationStatus())
                .donationDate(createDonationDTO.getDonationDate())
                .donationAmount(createDonationDTO.getDonationAmount())
                .build();
        return donationRepo.save(donation);
    }

    @Override
    public List<Donation> getAllDonationByUser() {
        Donor donor = donorRepo.findByEmail(securityUtil.getUsername())
                .orElseThrow(() -> new RuntimeException("Donor not found"));

        List<Donation> donations = donationRepo.findAllByDonor(donor);

        return donations.stream()
                .map(donation -> Donation.builder()
                        .id(donation.getId())
                        .donationStatus(donation.getDonationStatus())
                        .donationDate(donation.getDonationDate())
                        .donationAmount(donation.getDonationAmount())
                        .donationRequest(donation.getDonationRequest())
                        .donor(donation.getDonor())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public List<Donation> getDonationById(Long id) {
        Donation donation = donationRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Donation not found"));

        Donation result = Donation.builder()
                .id(donation.getId())
                .donationStatus(donation.getDonationStatus())
                .donationDate(donation.getDonationDate())
                .donationAmount(donation.getDonationAmount())
                .donationRequest(donation.getDonationRequest())
                .donor(donation.getDonor())
                .build();

        return List.of(result);
    }

}

