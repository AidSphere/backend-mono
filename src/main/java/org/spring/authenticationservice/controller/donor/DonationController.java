package org.spring.authenticationservice.controller.donor;

import lombok.AllArgsConstructor;
import org.spring.authenticationservice.DTO.api.ApiResponse;
import org.spring.authenticationservice.DTO.donation.DonationForRequestDTO;
import org.spring.authenticationservice.DTO.donation.DonationHistoryDto;
import org.spring.authenticationservice.DTO.donation.DonationRequestResponseDto;
import org.spring.authenticationservice.DTO.donor.CreateDonationDTO;
import org.spring.authenticationservice.Service.donor.DonationService;
import org.spring.authenticationservice.Service.patient.DonationRequestService;
import org.spring.authenticationservice.model.donor.Donation;
import org.spring.authenticationservice.model.patient.DonationRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RequestMapping("/donation")
@RestController
public class DonationController {

    private final DonationService donationService;
    private final DonationRequestService donationRequestService;

    //create donation request
    @PostMapping("payment")
    public ResponseEntity<ApiResponse<?>> createDonationRequest(@RequestBody CreateDonationDTO createDonationDTO) {
        donationService.createDonation(createDonationDTO);
        return ResponseEntity.ok(ApiResponse.builder()
                .message("Donation Successfully")
                .build());
    }

    //get donation by user
    @GetMapping("/donationByUser")
    public ResponseEntity<ApiResponse<?>> getDonationByUser() {
        //use donation requests as a list
        List<DonationHistoryDto> donationRequests = donationService.getAllDonationByUser();
        return ResponseEntity.ok(ApiResponse.builder()
                .message("Donation Requests fetched successfully")
                .data(donationRequests)
                .build());
    }

    //get donation request by user
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> getDonationRequestByUser(@PathVariable Long id) {
        List<DonationForRequestDTO> donationRequests = donationService.getDonationByRequest(id);
        return ResponseEntity.ok(ApiResponse.builder()
                .message("Donation Requests fetched successfully")
                .data(donationRequests)
                .build());
    }


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
