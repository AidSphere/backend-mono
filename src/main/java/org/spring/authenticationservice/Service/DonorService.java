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
        try {
            List<Donation> donations = donationRepository.findDonationByDonorid(donorId);
            return donations;
        } catch (Exception e) {
            throw new RuntimeException("Error occurred while fetching donation history for donor ID: " + donorId, e);
        }
    }



}
