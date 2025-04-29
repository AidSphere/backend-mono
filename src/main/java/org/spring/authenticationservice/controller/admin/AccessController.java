package org.spring.authenticationservice.controller.admin;

import lombok.AllArgsConstructor;
import org.spring.authenticationservice.DTO.api.ApiResponse;
import org.spring.authenticationservice.DTO.donor.DonorResponseDTO;
import org.spring.authenticationservice.DTO.drugImporter.DrugImporterResponse;
import org.spring.authenticationservice.Service.donor.DonorService;
import org.spring.authenticationservice.Service.drugImporter.DrugImporterService;
import org.spring.authenticationservice.exception.ResourceNotFoundException;
import org.spring.authenticationservice.model.donor.Donor;
import org.spring.authenticationservice.model.drugImporter.DrugImporter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("admin")
@AllArgsConstructor
public class AccessController {

    private DrugImporterService drugImporterService;
    private DonorService donorService;


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
    @GetMapping("/access/donor/pending")
    public ResponseEntity<ApiResponse<List<DonorResponseDTO>>> getPendingDonors() throws ResourceNotFoundException {
        List<Donor> pendingDonors = donorService.getPendingDonors();
        List<DonorResponseDTO> donorResponses = pendingDonors.stream()
                .map(donor -> new DonorResponseDTO(
                        donor.getId(),
                        donor.getFirstName(),
                        donor.getLastName(),
                        donor.getEmail(),
                        donor.getPhone(),
                        donor.getNic()
                ))
                .collect(Collectors.toList());

        return ResponseEntity.ok(ApiResponse.<List<DonorResponseDTO>>builder()
                .status(HttpStatus.OK.value())
                .message("Pending donors retrieved successfully")
                .data(donorResponses)
                .timestamp(LocalDateTime.now())
                .build());
    }

    //patient pending

    //any user approve

    //any user reject

}
