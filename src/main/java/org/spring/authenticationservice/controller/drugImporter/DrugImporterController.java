package org.spring.authenticationservice.controller.drugImporter;

import org.spring.authenticationservice.DTO.api.ApiResponse;
import org.spring.authenticationservice.DTO.api.Pagination;
import org.spring.authenticationservice.DTO.drugImporter.DrugImporterRegisterRequest;
import org.spring.authenticationservice.DTO.drugImporter.DrugImporterResponse;
import org.spring.authenticationservice.DTO.drugImporter.DrugImporterUpdateRequest;
import org.spring.authenticationservice.Service.drugImporter.DrugImporterService;
import org.spring.authenticationservice.Utils.SecurityUtil;
import org.spring.authenticationservice.model.drugImporter.DrugImporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Controller for managing Drug Importer operations
 * This controller handles registration, profile management, and administrative functions
 * related to drug importers in the authentication service.
 */
@RestController
@RequestMapping("/drug-importer")
public class DrugImporterController {

    // Define logger manually instead of using Lombok's @Slf4j to avoid compilation issues
    private static final Logger log = LoggerFactory.getLogger(DrugImporterController.class);

    // Inject the service responsible for drug importer business logic
    @Autowired
    private DrugImporterService drugImporterService;

    // Inject utility for accessing security context information
    @Autowired
    private SecurityUtil securityUtil;

    /**
     * Registers a new drug importer with required documentation
     *
     * @param request The registration data including personal info and document URLs
     * @return Standardized API response with registration result
     * @throws Exception If registration fails for any reason
     */
    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<Void>> registerDrugImporter(
            @RequestBody @Valid DrugImporterRegisterRequest request) throws Exception {

        log.info("Received drug importer registration request");
        log.debug("Drug Importer details: {}", request);



        drugImporterService.registerDrugImporter(request);

        ApiResponse<Void> response = ApiResponse.<Void>builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.CREATED.value())
                .message("Drug Importer registered successfully. Account activation email has been sent.")
                .path(getCurrentRequestPath())
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }



//    /**
//     * Retrieves the profile of the currently authenticated drug importer
//     *
//     * @return Standardized API response with the drug importer profile data
//     * @throws Exception If profile retrieval fails
//     */
//    @GetMapping("/profile")
//    @PreAuthorize("hasRole('DRUG_IMPORTER')")
//    public ResponseEntity<ApiResponse<DrugImporterResponse>> getCurrentUserProfile() throws Exception {
//        // Get username (email) from security context
//        String email = securityUtil.getUsername();
//        if (email == null) {
//            throw new SecurityException("User not authenticated");
//        }
//
//        // Find and return the drug importer profile
//        DrugImporter drugImporter = drugImporterService.findByEmail(email);
//        DrugImporterResponse response = convertToResponse(drugImporter);
//
//        ApiResponse<DrugImporterResponse> apiResponse = ApiResponse.<DrugImporterResponse>builder()
//                .timestamp(LocalDateTime.now())
//                .status(HttpStatus.OK.value())
//                .data(response)
//                .message("Profile retrieved successfully")
//                .path(getCurrentRequestPath())
//                .build();
//
//        return ResponseEntity.ok(apiResponse);
//    }

     
     

//    /**
//     * Updates the current drug importer's profile
//     *
//     * @param request The update data with document URLs
//     * @return Standardized API response with the updated drug importer profile
//     * @throws Exception If update fails
//     */
//    @PutMapping(value = "/profile", consumes = MediaType.APPLICATION_JSON_VALUE)
//    @PreAuthorize("hasRole('DRUG_IMPORTER')")
//    public ResponseEntity<ApiResponse<DrugImporterResponse>> updateCurrentUserProfile(
//            @RequestBody @Valid DrugImporterUpdateRequest request) throws Exception {
//
//        // Get username (email) from security context
//        String email = securityUtil.getUsername();
//        if (email == null) {
//            throw new SecurityException("User not authenticated");
//        }
//
//        // Find the drug importer profile
//        DrugImporter drugImporter = drugImporterService.findByEmail(email);
//
//
//
//        // Update profile through service
//        DrugImporter updatedDrugImporter = drugImporterService.updateDrugImporter(
//                drugImporter.getId(), request);
//
//        DrugImporterResponse responseDto = convertToResponse(updatedDrugImporter);
//
//        ApiResponse<DrugImporterResponse> response = ApiResponse.<DrugImporterResponse>builder()
//                .timestamp(LocalDateTime.now())
//                .status(HttpStatus.OK.value())
//                .data(responseDto)
//                .message("Profile updated successfully")
//                .path(getCurrentRequestPath())
//                .build();
//
//        return ResponseEntity.ok(response);
//    }

//    /**
//     * Deletes the current drug importer's account
//     *
//     * @return Standardized API response confirming deletion
//     * @throws Exception If deletion fails
//     */
//    @DeleteMapping("/profile")
//    @PreAuthorize("hasRole('DRUG_IMPORTER')")
//    public ResponseEntity<ApiResponse<Void>> deleteCurrentUserAccount() throws Exception {
//        // Get username (email) from security context
//        String email = securityUtil.getUsername();
//        if (email == null) {
//            throw new SecurityException("User not authenticated");
//        }
//
//        // Find and delete the drug importer
//        DrugImporter drugImporter = drugImporterService.findByEmail(email);
//        drugImporterService.deleteDrugImporter(drugImporter.getId());
//
//        ApiResponse<Void> response = ApiResponse.<Void>builder()
//                .timestamp(LocalDateTime.now())
//                .status(HttpStatus.OK.value())
//                .message("Your account has been deleted successfully")
//                .path(getCurrentRequestPath())
//                .build();
//
//        return ResponseEntity.ok(response);
//    }

    

//    /**
//     * Helper method to convert DrugImporter entity to DrugImporterResponse DTO
//     *
//     * @param drugImporter The drug importer entity
//     * @return DrugImporterResponse DTO
//     */
//    private DrugImporterResponse convertToResponse(DrugImporter drugImporter) {
//        DrugImporterResponse response = new DrugImporterResponse();
//        response.setId(drugImporter.getId());
//        response.setEmail(drugImporter.getEmail());
//        response.setName(drugImporter.getName());
//        response.setPhone(drugImporter.getPhone());
//        response.setAddress(drugImporter.getAddress());
//        response.setLicenseNumber(drugImporter.getLicenseNumber());
//        response.setNicotineProofFilePath(drugImporter.getNicotineProofUrl());
//        response.setLicenseProofFilePath(drugImporter.getLicenseProofUrl());
//        response.setEnabled(drugImporter.isEnabled());
//        return response;
//    }

    /**
     * Helper method to get the current request path
     *
     * @return The current request path as a string
     */
    public static String getCurrentRequestPath() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes != null) {
            return attributes.getRequest().getRequestURI();
        }
        return null;
    }
}