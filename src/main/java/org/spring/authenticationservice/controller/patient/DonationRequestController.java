package org.spring.authenticationservice.controller.patient;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.spring.authenticationservice.DTO.api.ApiResponse;
import org.spring.authenticationservice.DTO.patient.donation.DonationRequestConfirmDto;
import org.spring.authenticationservice.DTO.patient.donation.DonationRequestCreateDto;
import org.spring.authenticationservice.DTO.patient.donation.DonationRequestResponseDto;
import org.spring.authenticationservice.DTO.patient.donation.DonationRequestUpdateDto;
import org.spring.authenticationservice.Service.patient.DonationRequestService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/donation-requests")
@RequiredArgsConstructor
public class DonationRequestController {

    private final DonationRequestService donationRequestService;

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<?>> createDonationRequest(@RequestBody DonationRequestCreateDto dto) {
        DonationRequestResponseDto responseDto = donationRequestService.createDonationRequest(dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.CREATED.value())
                .message("Donation Request Submitted Successfully")
                .data(responseDto)
                .build());
    }



    @PutMapping("/update/{requestId}")
    public ResponseEntity<ApiResponse<?>> updateDonationRequest(
            @PathVariable Long requestId,
            @Valid @RequestBody DonationRequestUpdateDto dto) {
        DonationRequestResponseDto responseDto = donationRequestService.updateDonationRequest(requestId, dto);

        return ResponseEntity.ok(ApiResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.OK.value())
                .message("Donation Request Updated Successfully")
                .data(responseDto)
                .build());
    }

}
