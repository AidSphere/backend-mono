package org.spring.authenticationservice.controller.admin;

import lombok.AllArgsConstructor;
import org.spring.authenticationservice.DTO.api.ApiResponse;
import org.spring.authenticationservice.DTO.donor.DonorResponseDTO;
import org.spring.authenticationservice.DTO.drugImporter.DrugImporterResponse;
import org.spring.authenticationservice.DTO.patient.PatientResponseDto;
import org.spring.authenticationservice.Service.donor.DonorService;
import org.spring.authenticationservice.Service.drugImporter.DrugImporterService;
import org.spring.authenticationservice.Service.security.AuthService;
import org.spring.authenticationservice.exception.ResourceNotFoundException;
import org.spring.authenticationservice.model.donor.Donor;
import org.spring.authenticationservice.model.drugImporter.DrugImporter;
import org.spring.authenticationservice.model.patient.Patient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("admin")
@AllArgsConstructor
public class AccessController {

    private DrugImporterService drugImporterService;
    private DonorService donorService;
    private AuthService authService;


    //drug importer pending
    @GetMapping("/access/drug-importer/pending")
    public ResponseEntity<ApiResponse<List<DrugImporterResponse>>> getPendingDrugImporters() throws ResourceNotFoundException {
        List<DrugImporter> pendingDrugImporters = drugImporterService.getPendingDrugImporters();
        List<DrugImporterResponse> drugImporterResponses = pendingDrugImporters.stream()
                .map(drugImporter -> new DrugImporterResponse(
                        drugImporter.getId(),
                        drugImporter.getName(),
                        drugImporter.getEmail(),
                        drugImporter.getPhone(),
                        drugImporter.getLicenseNumber()))
                .collect(Collectors.toList());

        return ResponseEntity.ok(ApiResponse.<List<DrugImporterResponse>>builder()
                .status(HttpStatus.OK.value())
                .message("Pending drug importers retrieved successfully")
                .data(drugImporterResponses)
                .timestamp(LocalDateTime.now())
                .build());
    }

    //donor pending
    @GetMapping("/access/patient/pending")
    public ResponseEntity<ApiResponse<List<PatientResponseDto>>> getPendingDonors() throws ResourceNotFoundException {
        List<Patient> pendingDonors = donorService.getPendingPatients();
        List<PatientResponseDto> patientResponseDtos = pendingDonors.stream()
                .map(patient -> PatientResponseDto.builder()
                        .patientId(patient.getPatientId())
                        .firstName(patient.getFirstName())
                        .lastName(patient.getLastName())
                        .email(patient.getEmail())
                        .phoneNumber(patient.getPhoneNumber())
                        .permanentAddress(patient.getPermanentAddress())
                        .currentAddress(patient.getCurrentAddress())
                        .profileImageUrl(patient.getProfileImageUrl())
                        .build(
                ))
                .collect(Collectors.toList());

        return ResponseEntity.ok(ApiResponse.<List<PatientResponseDto>>builder()
                .status(HttpStatus.OK.value())
                .message("Pending donors retrieved successfully")
                .data(patientResponseDtos)
                .timestamp(LocalDateTime.now())
                .build());
    }

    //patient pending
    @PostMapping("/access/approve/{email}")
    public ResponseEntity<ApiResponse<?>> approveUser(@PathVariable String email) {
        authService.adminApproval(email);

        return ResponseEntity.ok(ApiResponse.builder()
                .status(HttpStatus.OK.value())
                .message("User approved successfully")
                .timestamp(LocalDateTime.now())
                .build());
    }

    @PostMapping("/access/reject/{email}")
    public ResponseEntity<ApiResponse<?>> rejectUser(@PathVariable String email) {
        authService.adminApprovalReject(email);

        return ResponseEntity.ok(ApiResponse.builder()
                .status(HttpStatus.OK.value())
                .message("User rejected successfully")
                .timestamp(LocalDateTime.now())
                .build());
    }

}
