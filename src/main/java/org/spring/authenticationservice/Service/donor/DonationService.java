package org.spring.authenticationservice.Service.donor;

import org.spring.authenticationservice.DTO.donation.DonationRequestResponseDto;
import org.spring.authenticationservice.DTO.donor.CreateDonationDTO;
import org.spring.authenticationservice.model.donor.Donation;
import org.spring.authenticationservice.model.patient.DonationRequest;

import java.util.List;

public interface DonationService {
    void createDonation(CreateDonationDTO createDonationDTO);

    List<Donation> getAllDonationByUser();

    List<Donation> getDonationById(Long id);
}
