package org.spring.authenticationservice.controller.drugImporter;

import org.spring.authenticationservice.DTO.drugImporter.RequestStatusDTO;
import org.spring.authenticationservice.Service.drugImporter.RequestStatusService;
import org.spring.authenticationservice.model.drugImporter.RequestStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/request-status")
public class RequestStatusController {

    private final RequestStatusService requestStatusService;

    @Autowired
    public RequestStatusController(RequestStatusService requestStatusService) {
        this.requestStatusService = requestStatusService;
    }

    /**
     * Update an existing request status
     * Only DRUG_IMPORTER role can access this endpoint
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('DRUG_IMPORTER')")
    public ResponseEntity<RequestStatusDTO.Response> updateRequestStatus(
            @PathVariable Long id,
            @RequestBody RequestStatusDTO.UpdateRequest updateRequest) {

        RequestStatus updatedRequestStatus = requestStatusService.updateStatus(id, updateRequest.getStatus());
        RequestStatusDTO.Response response = mapToResponse(updatedRequestStatus);
        return ResponseEntity.ok(response);
    }



    private RequestStatusDTO.Response mapToResponse(RequestStatus requestStatus) {
        return new RequestStatusDTO.Response(
                requestStatus.getId(),
                requestStatus.getRequestId(),
                requestStatus.getDrugImporterId(),
                requestStatus.getStatus()
        );
    }
}