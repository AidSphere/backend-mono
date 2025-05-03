package org.spring.authenticationservice.controller.drugImporter;

import org.spring.authenticationservice.DTO.drugImporter.RequestStatusDTO;
import org.spring.authenticationservice.Service.drugImporter.DrugImporterService;
import org.spring.authenticationservice.Service.drugImporter.RequestStatusService;
import org.spring.authenticationservice.Utils.SecurityUtil;
import org.spring.authenticationservice.model.drugImporter.DrugImporter;
import org.spring.authenticationservice.model.drugImporter.RequestStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/request-status")
public class RequestStatusController {
    private final RequestStatusService requestStatusService;
    private final DrugImporterService drugImporterService;
    private final SecurityUtil securityUtil;

    @Autowired
    public RequestStatusController(RequestStatusService requestStatusService,
            DrugImporterService drugImporterService,
            SecurityUtil securityUtil) {
        this.requestStatusService = requestStatusService;
        this.drugImporterService = drugImporterService;
        this.securityUtil = securityUtil;
    }

    /**
     * Update an existing request status
     * Only DRUG_IMPORTER role can access this endpoint
     * 
     * @throws Exception
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('DRUG_IMPORTER')")
    public ResponseEntity<RequestStatusDTO.Response> updateRequestStatus(
            @PathVariable Long id,
            @RequestBody RequestStatusDTO.UpdateRequest updateRequest) throws Exception {
        // Get the current drug importer ID
        Long drugImporterId = getCurrentDrugImporterId();

        // Update the request status for the specific drug importer
        RequestStatus updatedRequestStatus = requestStatusService.saveOrUpdateByRequestId(
                id, drugImporterId, updateRequest.getStatus());

        RequestStatusDTO.Response response = mapToResponse(updatedRequestStatus);
        return ResponseEntity.ok(response);
    }

    private Long getCurrentDrugImporterId() throws Exception {
        // Get username (email) from security context
        String email = securityUtil.getUsername();
        if (email == null) {
            throw new SecurityException("User not authenticated");
        }

        // Find the drug importer profile using the email
        DrugImporter drugImporter = drugImporterService.findByEmail(email);
        if (drugImporter == null) {
            throw new SecurityException("Drug importer profile not found for current user");
        }

        return drugImporter.getId();
    }

    private RequestStatusDTO.Response mapToResponse(RequestStatus requestStatus) {
        return new RequestStatusDTO.Response(
                requestStatus.getId(),
                requestStatus.getRequestId(),
                requestStatus.getDrugImporterId(),
                requestStatus.getStatus());
    }
}