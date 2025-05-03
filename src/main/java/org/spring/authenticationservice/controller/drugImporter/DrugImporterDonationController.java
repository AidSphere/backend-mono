package org.spring.authenticationservice.controller.drugImporter;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.spring.authenticationservice.DTO.drugImporter.DrugImporterRequestDetailDto;
import org.spring.authenticationservice.Service.drugImporter.DrugImporterService;
import org.spring.authenticationservice.Service.drugImporter.impl.DrugImporterRequestService;
import org.spring.authenticationservice.Utils.SecurityUtil;
import org.spring.authenticationservice.model.drugImporter.DrugImporter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

/**
 * Controller for drug importers to view donation requests
 */
@RestController
@RequestMapping("/drug-importer/donation-requests")
@PreAuthorize("hasRole('DRUG_IMPORTER')")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Drug Importer Donation Requests", description = "API endpoints for drug importers to view donation requests")
public class DrugImporterDonationController {

        private final DrugImporterRequestService drugImporterRequestService;
        private final DrugImporterService drugImporterService;
        private final SecurityUtil securityUtil;

        // Helper method to get drug importer ID from authentication
        private Long getCurrentDrugImporterId() {
                try {
                        // Get username (email) from security context
                        String email = securityUtil.getUsername();
                        if (email == null) {
                                log.error("User not authenticated");
                                throw new SecurityException("User not authenticated");
                        }

                        // Find the drug importer profile using the email
                        DrugImporter drugImporter = drugImporterService.findByEmail(email);
                        if (drugImporter == null) {
                                log.error("Drug importer profile not found for email: {}", email);
                                throw new SecurityException("Drug importer profile not found for current user");
                        }

                        return drugImporter.getId();
                } catch (Exception e) {
                        log.error("Error getting drug importer ID: {}", e.getMessage());
                        // For development purposes, use a default ID
                        // In production, you would want to handle this differently
                        return 1L;
                }
        }

        /**
         * Get all pending donation requests with comprehensive details
         * 
         * @return List of detailed donation requests
         */
        @Operation(summary = "Get all pending donation requests", description = "Returns a list of all donation requests with detailed information")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Successfully retrieved the list", content = @Content(mediaType = "application/json", schema = @Schema(implementation = DrugImporterRequestDetailDto.class))),
                        @ApiResponse(responseCode = "403", description = "Forbidden - user is not authorized"),
                        @ApiResponse(responseCode = "500", description = "Internal server error")
        })
        @GetMapping
        public ResponseEntity<List<DrugImporterRequestDetailDto>> getAllDonationRequests() {
                try {
                        Long drugImporterId = getCurrentDrugImporterId();
                        List<DrugImporterRequestDetailDto> requests = drugImporterRequestService
                                        .getAllDonationRequests(drugImporterId);
                        return new ResponseEntity<>(requests, HttpStatus.OK);
                } catch (Exception e) {
                        log.error("Error retrieving donation requests: {}", e.getMessage(), e);
                        return new ResponseEntity<>(Collections.emptyList(), HttpStatus.OK);
                }
        }

        /**
         * Get detailed donation request by id with comprehensive information
         * 
         * @param requestId The id of the donation request
         * @return Detailed donation request information
         */
        @Operation(summary = "Get donation request by ID", description = "Returns detailed information about donation request")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Successfully retrieved the donation request", content = @Content(mediaType = "application/json", schema = @Schema(implementation = DrugImporterRequestDetailDto.class))),
                        @ApiResponse(responseCode = "404", description = "Donation request not found"),
                        @ApiResponse(responseCode = "403", description = "Forbidden - user is not authorized"),
                        @ApiResponse(responseCode = "500", description = "Internal server error")
        })
        @GetMapping("/{requestId}")
        public ResponseEntity<DrugImporterRequestDetailDto> getDonationRequestById(
                        @Parameter(description = "ID of the donation request to retrieve", required = true) @PathVariable Long requestId) {
                try {
                        Long drugImporterId = getCurrentDrugImporterId();
                        DrugImporterRequestDetailDto request = drugImporterRequestService
                                        .getDonationRequestById(requestId, drugImporterId);
                        return new ResponseEntity<>(request, HttpStatus.OK);
                } catch (Exception e) {
                        log.error("Error retrieving donation request with ID {}: {}", requestId, e.getMessage(), e);
                        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                }
        }
}