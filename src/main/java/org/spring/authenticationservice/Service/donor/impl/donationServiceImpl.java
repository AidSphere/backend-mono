package org.spring.authenticationservice.Service.donor.impl;

import lombok.AllArgsConstructor;
import org.spring.authenticationservice.DTO.donor.CreateDonationDTO;
import org.spring.authenticationservice.Service.donor.DonationService;
import org.spring.authenticationservice.model.donor.Donation;
import org.spring.authenticationservice.model.donor.Donor;
import org.spring.authenticationservice.model.patient.DonationRequest;
import org.spring.authenticationservice.repository.donor.DonationRepository;
import org.spring.authenticationservice.repository.donor.DonorRepository;
import org.spring.authenticationservice.repository.patient.DonationRequestRepo;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class donationServiceImpl implements DonationService {
    private DonationRequestRepo donationRequestRepo;
    private DonorRepository donorRepo;
    private DonationRepository donationRepo;



    @Override
    public Donation createDonation(CreateDonationDTO createDonationDTO) {
        DonationRequest donationRequest = donationRequestRepo.findById(createDonationDTO.getId()).orElseThrow(
                () -> new RuntimeException("Donation request not found")
        );

        Donor donor = donorRepo.findByEmail(createDonationDTO.getDonorEmail()).orElseThrow(
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
}
