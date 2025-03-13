package org.spring.authenticationservice.Service;


import org.spring.authenticationservice.model.DonationPatientRequests;
import org.spring.authenticationservice.model.Enum.StatusEnum;
import org.spring.authenticationservice.repository.DonationRequestRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DonationRequestService {
    private final DonationRequestRepository donationRequestRepository;

    public DonationRequestService(DonationRequestRepository donationRequestRepository) {
        this.donationRequestRepository = donationRequestRepository;
    }

    public List<DonationPatientRequests> getPendingDonationRequests() {
        return donationRequestRepository.findByStatus(StatusEnum.PENDING);
    }

    public DonationPatientRequests createDonationRequest(DonationPatientRequests donationPatientRequests) {
        return donationRequestRepository.save(donationPatientRequests);
    }
}
