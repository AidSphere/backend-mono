package org.spring.authenticationservice.controller.patient;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.spring.authenticationservice.DTO.api.ApiResponse;
import org.spring.authenticationservice.DTO.donation.DonationRequestConfirmDto;
import org.spring.authenticationservice.DTO.donation.DonationRequestCreateDto;
import org.spring.authenticationservice.DTO.donation.DonationRequestResponseDto;
import org.spring.authenticationservice.DTO.donation.DonationRequestUpdateDto;
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


    @PutMapping("/{requestId}")
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

    @DeleteMapping("/{requestId}")
    public ResponseEntity<ApiResponse<?>> deleteDonationRequest(@PathVariable Long requestId) {
        donationRequestService.deleteDonationRequest(requestId);

        return ResponseEntity.ok(ApiResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.OK.value())
                .message("Donation Request Deleted Successfully")
                .build());
    }

    @GetMapping
    public ResponseEntity<ApiResponse<?>> getAllDonationRequests() {
        List<DonationRequestResponseDto> requests = donationRequestService.getAllDonationRequests();
        return ResponseEntity.ok(ApiResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.OK.value())
                .message("Donation Requests Retrieved Successfully")
                .data(requests)
                .build());
    }

    @GetMapping("/{requestId}")
    public ResponseEntity<ApiResponse<?>> getDonationRequestById(@PathVariable Long requestId) {
        var responseDto = donationRequestService.getDonationRequestById(requestId);
        return ResponseEntity.ok(ApiResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.OK.value())
                .message("Donation Request Retrieved Successfully")
                .data(responseDto)
                .build());
    }

    // get donation requests of a patient (only REJECTED, PENDING, ADMIN_APPROVED)
    @GetMapping("/patient/{patientId}")
    public ResponseEntity<ApiResponse<?>> getDonationRequestsByPatient(@PathVariable Long patientId) {
        List<DonationRequestResponseDto> requests = donationRequestService.getDonationRequestsByPatientIdAndStatus(patientId);

        return ResponseEntity.ok(ApiResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.OK.value())
                .message("Patient's Donation Requests Retrieved Successfully")
                .data(requests)
                .build());
    }

    // get donation requests of a patient (only ADMIN_APPROVED)
    @GetMapping("/patient/{patientId}/patient-approved")
    public ResponseEntity<ApiResponse<?>> getPatientApprovedRequestsByPatient(@PathVariable Long patientId) {
        List<DonationRequestResponseDto> requests = donationRequestService.getPatientApprovedRequestsByPatientId(patientId);

        return ResponseEntity.ok(ApiResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.OK.value())
                .message("Patient's Approved Donation Requests Retrieved Successfully")
                .data(requests)
                .build());
    }



    //admin approved
    @GetMapping("/approved")
    public ResponseEntity<ApiResponse<?>> getApprovedDonations() {
        List<DonationRequestResponseDto> approvedRequests =
                donationRequestService.getApprovedDonationRequests();

        return ResponseEntity.ok(ApiResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.OK.value())
                .message("Approved Donation Requests Retrieved Successfully")
                .data(approvedRequests)
                .build());
    }


    @GetMapping("/pending")
    public ResponseEntity<ApiResponse<?>> getPendingDonations() {
        List<DonationRequestResponseDto> pendingRequests =
                donationRequestService.getPendingDonationRequests();

        return ResponseEntity.ok(ApiResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.OK.value())
                .message("Pending Donation Requests Retrieved Successfully")
                .data(pendingRequests)
                .build());
    }

    @GetMapping("/rejected")
    public ResponseEntity<ApiResponse<?>> getRejectedDonations() {
        List<DonationRequestResponseDto> pendingRequests =
                donationRequestService.getRejectedDonationRequests();

        return ResponseEntity.ok(ApiResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.OK.value())
                .message("Pending Donation Requests Retrieved Successfully")
                .data(pendingRequests)
                .build());
    }

    @GetMapping("/patient-accepted")
    public ResponseEntity<ApiResponse<?>> getPatientAcceptedDonations() {
        List<DonationRequestResponseDto> acceptedRequests =
                donationRequestService.getPatientAcceptedDonationRequests();

        return ResponseEntity.ok(ApiResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.OK.value())
                .message("Patient Accepted Donation Requests Retrieved Successfully")
                .data(acceptedRequests)
                .build());
    }

    @PutMapping("/{requestId}/approve")
    public ResponseEntity<ApiResponse<?>> confirmDonationRequest(
            @PathVariable Long requestId,
            @Valid @RequestBody DonationRequestConfirmDto dto) {
        DonationRequestResponseDto responseDto = donationRequestService
                .updateConfirmation(requestId, dto.getStatus(), dto.getMessageToPatient());

        return ResponseEntity.ok(ApiResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.OK.value())
                .message("Donation Request Status Updated Successfully")
                .data(responseDto)
                .build());
    }

}
