package org.spring.authenticationservice.controller;
import org.spring.authenticationservice.DTO.DonationRequestConfirmationDTO;
import org.spring.authenticationservice.Service.DonationRequestService;
import org.spring.authenticationservice.model.DonationPatientRequests;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/Donation")
public class DonationRequestController {

    private final DonationRequestService donationRequestService;

    public DonationRequestController(DonationRequestService donationRequestService) {
        this.donationRequestService = donationRequestService;
    }

    @PostMapping("/create")
    public DonationPatientRequests createDonation(@RequestBody DonationPatientRequests donationPatientRequests) {
        return donationRequestService.createDonationRequest(donationPatientRequests);
    }

    @GetMapping("/pending")
    public List<DonationPatientRequests> getPendingDonations() {
        return donationRequestService.getPendingDonationRequests();
    }

    @PutMapping("/confirmation")
    public ResponseEntity<?> confirmDonationRequest(
            @RequestBody DonationRequestConfirmationDTO donationRequestConfirmationDTO,
            @RequestParam Long requestId) {

        try {
            return donationRequestService.updateConfirmation(requestId, donationRequestConfirmationDTO);
        } catch (ChangeSetPersister.NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Donation request not found");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while processing the request");
        }
    }



}

