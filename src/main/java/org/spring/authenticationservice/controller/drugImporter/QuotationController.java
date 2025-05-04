package org.spring.authenticationservice.controller.drugImporter;

import org.spring.authenticationservice.DTO.drugImporter.QuotationDTO;
import org.spring.authenticationservice.Service.drugImporter.QuotationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/quotations")
//@PreAuthorize("hasRole('DRUG_IMPORTER')")
public class QuotationController {

    @Autowired
    private QuotationService quotationService;

//    // Helper method to check if user has drug_importer role
//    private boolean hasDrugImporterRole() {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        return authentication.getAuthorities().stream()
//                .map(GrantedAuthority::getAuthority)
//                .anyMatch(role -> role.equals("DRUG_IMPORTER"));
//    }
//
//    // Helper method to get drug importer ID from authentication
//    private Long getDrugImporterId() {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        if (authentication != null && authentication.getPrincipal() != null) {
//            return ((UserDetailsImpl) authentication.getPrincipal()).getDrugImporterId();
//        }
//        return null;
//    }
//
//    // Helper method to validate drug importer role and ID
//    private void validateDrugImporter(Long requestedDrugImporterId) {
//        if (!hasDrugImporterRole()) {
//            throw new AccessDeniedException("User does not have the DRUG_IMPORTER role");
//        }
//
//        Long authDrugImporterId = getDrugImporterId();
//        if (authDrugImporterId == null || !authDrugImporterId.equals(requestedDrugImporterId)) {
//            throw new AccessDeniedException("User does not have access to this drug importer's data");
//        }
//    }

    @PostMapping
    public ResponseEntity<QuotationDTO> createQuotation(@RequestBody QuotationDTO quotationDTO) {
//        validateDrugImporter(quotationDTO.getDrugImporterId());
        QuotationDTO createdQuotation = quotationService.createQuotation(quotationDTO);
        return new ResponseEntity<>(createdQuotation, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<QuotationDTO> updateQuotation(
            @PathVariable Long id,
            @RequestBody QuotationDTO quotationDTO) {
//        validateDrugImporter(quotationDTO.getDrugImporterId());
        QuotationDTO updatedQuotation = quotationService.updateQuotation(id, quotationDTO);
        return new ResponseEntity<>(updatedQuotation, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteQuotation(
            @PathVariable Long id,
            @RequestParam Long drugImporterId) {
//        validateDrugImporter(drugImporterId);
        quotationService.deleteQuotation(id, drugImporterId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{id}")
    public ResponseEntity<QuotationDTO> getQuotationById(
            @PathVariable Long id,
            @RequestParam Long drugImporterId) {
//        validateDrugImporter(drugImporterId);
        QuotationDTO quotation = quotationService.getQuotationById(id, drugImporterId);
        return new ResponseEntity<>(quotation, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<QuotationDTO>> getAllQuotationsByDrugImporterId(
            @RequestParam Long drugImporterId) {
//        validateDrugImporter(drugImporterId);
        List<QuotationDTO> quotations = quotationService.getAllQuotationsByDrugImporterId(drugImporterId);
        return new ResponseEntity<>(quotations, HttpStatus.OK);
    }

    @GetMapping("/request/{requestId}")
    public ResponseEntity<List<QuotationDTO>> getAllQuotationsByRequestId(@PathVariable Long requestId) {
        List<QuotationDTO> quotations = quotationService.getAllQuotationsByRequestId(requestId);
        return new ResponseEntity<>(quotations, HttpStatus.OK);
    }

    @PutMapping("/request/{requestId}/reject-pending")
    public ResponseEntity<Void> rejectPendingQuotations(@PathVariable Long requestId) {
        quotationService.rejectPendingQuotationsByRequestId(requestId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/{id}/send")
    public ResponseEntity<QuotationDTO> sendQuotation(
            @PathVariable Long id,
            @RequestParam Long drugImporterId) {
//        validateDrugImporter(drugImporterId);
        QuotationDTO sentQuotation = quotationService.sendQuotation(id, drugImporterId);
        return new ResponseEntity<>(sentQuotation, HttpStatus.OK);
    }
}