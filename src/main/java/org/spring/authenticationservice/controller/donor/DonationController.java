package org.spring.authenticationservice.controller.donor;

import lombok.AllArgsConstructor;
import org.spring.authenticationservice.DTO.api.ApiResponse;
import org.spring.authenticationservice.DTO.donation.DonationRequestResponseDto;
import org.spring.authenticationservice.DTO.donor.CreateDonationDTO;
import org.spring.authenticationservice.Service.donor.DonationService;
import org.spring.authenticationservice.Service.patient.DonationRequestService;
import org.spring.authenticationservice.model.donor.Donation;
import org.spring.authenticationservice.model.patient.DonationRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RequestMapping("/donation")
@RestController
public class DonationController {

    private final DonationService donationService;
    private final DonationRequestService donationRequestService;

    //create donation request
    @PostMapping("payment")
    public ResponseEntity<ApiResponse<?>> createDonationRequest(CreateDonationDTO createDonationDTO) {
        Donation donation = donationService.createDonation(createDonationDTO);
        return ResponseEntity.ok(ApiResponse.builder()
                .message("Donation Requests fetched successfully")
                .data(donation)
                .build());
    }

    //get donation by user

    //admin approved but not accepted
    @GetMapping("/notAccepted")
    public ResponseEntity<ApiResponse<?>> getDonationRequestNotAccepted() {
        //use donation requests as a list
        List<DonationRequestResponseDto> donationRequests = donationRequestService.getAllDonationPatientRequestsNotAccepted();
        return ResponseEntity.ok(ApiResponse.builder()
                .message("Donation Requests fetched successfully")
                .data(donationRequests)
                .build());
    }


    //list donation request
    @GetMapping("/donationRequest")
    public ResponseEntity<ApiResponse<?>> getDonationRequest() {
        //use donation requests as a list
        List<DonationRequestResponseDto> donationRequests = donationRequestService.getAllDonationPatientRequests();
        return ResponseEntity.ok(ApiResponse.builder()
                .message("Donation Requests fetched successfully")
                .data(donationRequests)
                .build());
    }


}
