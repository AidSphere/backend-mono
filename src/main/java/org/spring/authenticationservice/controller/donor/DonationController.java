package org.spring.authenticationservice.controller.donor;

import lombok.AllArgsConstructor;
import org.spring.authenticationservice.DTO.api.ApiResponse;
import org.spring.authenticationservice.DTO.donation.DonationRequestResponseDto;
import org.spring.authenticationservice.Service.patient.DonationRequestService;
import org.spring.authenticationservice.model.patient.DonationRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RequestMapping("/donation")
@RestController
public class DonationController {

    private final DonationRequestService donationRequestService;

    //create donation

    //get donation by user

    //get all donations not hidden

    //list donation request accept by patient


    //list donation request not accept by patient
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
