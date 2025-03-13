package org.spring.authenticationservice.controller;
import org.spring.authenticationservice.Service.DonationRequestService;
import org.spring.authenticationservice.model.DonationPatientRequests;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class DonationRequestController {

    private final DonationRequestService donationRequestService;

    public DonationRequestController(DonationRequestService donationRequestService) {
        this.donationRequestService = donationRequestService;
    }

    @GetMapping("/pending")
    public List<DonationPatientRequests> getPendingDonations() {
        return donationRequestService.getPendingDonationRequests();
    }

    @PostMapping("/create")
    public DonationPatientRequests createDonation(@RequestBody DonationPatientRequests donationPatientRequests) {
        return donationRequestService.createDonationRequest(donationPatientRequests);
    }


}

