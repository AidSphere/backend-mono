package org.spring.authenticationservice.Service;


import org.spring.authenticationservice.model.DonationRequest;
import org.spring.authenticationservice.model.Enum.StatusEnum;
import org.spring.authenticationservice.repository.security.DonationRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DonationRequestService {

    @Autowired
    private DonationRequestRepository donationRequestRepository;

    public List<DonationRequest> getPendingDonationRequests() {
        //return donationRequestRepository.findByStatus(StatusEnum.PENDING);
        List<DonationRequest> donationRequests = donationRequestRepository.findAll();
        System.out.println(donationRequests);
        return donationRequests;
    }
}
