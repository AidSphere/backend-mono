package org.spring.authenticationservice.Service;


import org.spring.authenticationservice.DTO.UserSurveyRequestDTO;
import org.spring.authenticationservice.model.donor.Donation;
import org.spring.authenticationservice.model.donor.Donor;
import org.spring.authenticationservice.repository.security.donor.DonationRepository;
import org.spring.authenticationservice.repository.security.donor.DonorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DonorService {

    @Autowired
    DonorRepository donorRepository;

    @Autowired
    DonationRepository donationRepository;

    public Donor registerDonor(Donor donor) {
        return donorRepository.save(donor);
    }

    public Donor saveDescriptionForDonor(long id, UserSurveyRequestDTO surveyDTO) {
        Donor donor=donorRepository.findById(id).orElseThrow(() -> new RuntimeException("Donor not found"));
        donor.setDescription(surveyDTO.getDescription());
        return donorRepository.save(donor);
    }
    public Donation saveDonationDetails(Donation donation) {
        return donationRepository.save(donation);
    }

    public List<Donation> getDonationHistory(Long donorId) {
        List<Donation> donations = donationRepository.findDonationByDonorid(donorId); // Use donorid instead of DonationID
        if (donations.isEmpty()) {
            throw new RuntimeException("No donation history found for donor ID: " + donorId);
        }
        return donations;
    }



}
