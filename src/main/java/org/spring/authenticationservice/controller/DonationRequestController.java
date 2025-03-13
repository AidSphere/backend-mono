package org.spring.authenticationservice.controller;
import org.spring.authenticationservice.Service.DonationRequestService;
import org.spring.authenticationservice.model.DonationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class DonationRequestController {

    @Autowired
    private DonationRequestService donationRequestService;

    @GetMapping("/donation/pending")
    public List<DonationRequest> getPendingDonations() {
        List<DonationRequest> donationRequests =  donationRequestService.getPendingDonationRequests();
        System.out.println(donationRequests);
        return donationRequests;
    }


}

