package org.spring.authenticationservice.Service.donor;

import org.spring.authenticationservice.DTO.donor.CreateDonationDTO;
import org.spring.authenticationservice.model.donor.Donation;
import org.spring.authenticationservice.model.patient.DonationRequest;

public interface DonationService {
    Donation createDonation(CreateDonationDTO createDonationDTO);
}
