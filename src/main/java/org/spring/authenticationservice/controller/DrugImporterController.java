package org.spring.authenticationservice.controller;

import lombok.extern.slf4j.Slf4j;
import org.spring.authenticationservice.dto.ApiResponse;
import org.spring.authenticationservice.dto.DrugImporterRegisterRequest;
import org.spring.authenticationservice.dto.DrugImporterUpdateRequest;
import org.spring.authenticationservice.model.DrugImporter;
import org.spring.authenticationservice.service.DrugImporterService;
import org.spring.authenticationservice.utils.SecurityContextUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@RestController
@RequestMapping("/drug-importer")
public class DrugImporterController {

    private static final long MAX_FILE_SIZE = 5 * 1024 * 1024; // 5MB

    @Autowired
    private DrugImporterService drugImporterService;

    @PostMapping(value = "/register", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse> registerDrugImporter(
            @ModelAttribute @Valid DrugImporterRegisterRequest request) {

        log.info("Received drug importer registration request with file uploads");

        try {
            log.debug("Drug Importer details: {}", request);
            logFileDetails(request.getNicotineProofFile(), "Nicotine proof");
            logFileDetails(request.getLicenseProofFile(), "License proof");

            validateFileSize(request.getNicotineProofFile());
            validateFileSize(request.getLicenseProofFile());

            DrugImporter registeredImporter = drugImporterService.registerDrugImporter(
                    request,
                    request.getNicotineProofFile(),
                    request.getLicenseProofFile()
            );

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ApiResponse(true, "Drug Importer registered successfully. Account activation email has been sent."));
        } catch (IllegalArgumentException e) {
            log.error("Validation error during registration", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse(false, "Validation error: " + e.getMessage()));
        } catch (Exception e) {
            log.error("Error during drug importer registration", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "Error during registration: " + e.getMessage()));
        }
    }



    @GetMapping("/profile")
    @PreAuthorize("hasRole('DRUG_IMPORTER')")
    public ResponseEntity<?> getCurrentUserProfile() {
        try {
            String email = SecurityContextUtils.getCurrentUserEmail();
            if (email == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(new ApiResponse(false, "User not authenticated"));
            }

            DrugImporter drugImporter = drugImporterService.findByEmail(email);
            if (drugImporter == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse(false, "Drug Importer not found"));
            }
            return ResponseEntity.ok(drugImporter);
        } catch (Exception e) {
            log.error("Error fetching profile", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "Error fetching profile: " + e.getMessage()));
        }
    }

    @PutMapping(value = "/profile", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasRole('DRUG_IMPORTER')")
    public ResponseEntity<?> updateCurrentUserProfile(
            @ModelAttribute @Valid DrugImporterUpdateRequest request,
            @RequestPart(value = "nicotineProof", required = false) MultipartFile nicotineProof,
            @RequestPart(value = "licenseProof", required = false) MultipartFile licenseProof) {

        try {
            String email = SecurityContextUtils.getCurrentUserEmail();
            if (email == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(new ApiResponse(false, "User not authenticated"));
            }

            DrugImporter drugImporter = drugImporterService.findByEmail(email);
            if (drugImporter == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse(false, "Drug Importer not found"));
            }

            validateFileSize(nicotineProof);
            validateFileSize(licenseProof);

            DrugImporter updatedDrugImporter = drugImporterService.updateDrugImporter(
                    drugImporter.getId(), request, nicotineProof, licenseProof);

            return ResponseEntity.ok(updatedDrugImporter);
        } catch (IllegalArgumentException e) {
            log.error("Validation error during profile update", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse(false, "Validation error: " + e.getMessage()));
        } catch (Exception e) {
            log.error("Error updating profile", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "Error updating profile: " + e.getMessage()));
        }
    }

    @DeleteMapping("/profile")
    @PreAuthorize("hasRole('DRUG_IMPORTER')")
    public ResponseEntity<ApiResponse> deleteCurrentUserAccount() {
        try {
            String email = SecurityContextUtils.getCurrentUserEmail();
            if (email == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(new ApiResponse(false, "User not authenticated"));
            }

            DrugImporter drugImporter = drugImporterService.findByEmail(email);
            if (drugImporter == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse(false, "Drug Importer not found"));
            }

            drugImporterService.deleteDrugImporter(drugImporter.getId());
            return ResponseEntity.ok(new ApiResponse(true, "Your account has been deleted successfully"));
        } catch (Exception e) {
            log.error("Error deleting profile", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "Error deleting profile: " + e.getMessage()));
        }
    }

    private void validateFileSize(MultipartFile file) throws IOException {
        if (file != null && !file.isEmpty() && file.getSize() > MAX_FILE_SIZE) {
            throw new IOException("File size exceeds maximum limit of 5MB");
        }
    }

    private void logFileDetails(MultipartFile file, String fileType) {
        if (file != null && !file.isEmpty()) {
            log.debug("{} file: {}, size: {}", fileType, file.getOriginalFilename(), file.getSize());
        }
    }
}