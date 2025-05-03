package org.spring.authenticationservice.controller.drugImporter;//package org.spring.authenticationservice.controller.drugImporter;
//
//import org.spring.authenticationservice.DTO.drugImporter.QuotationStatusDTO;
//import org.spring.authenticationservice.model.Enum.QuotationStatusEnum;
//import org.spring.authenticationservice.Service.drugImporter.QuotationStatusService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.AccessDeniedException;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.web.bind.annotation.*;
//import java.util.List;
//
//@RestController
//@RequestMapping("/quotation-statuses")
//@PreAuthorize("hasRole('DRUG_IMPORTER')")
//public class QuotationStatusController {
//
//    @Autowired
//    private QuotationStatusService quotationStatusService;
//
////    // Helper method to check if user has drug_importer role
////    private boolean hasDrugImporterRole() {
////        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
////        return authentication.getAuthorities().stream()
////                .map(GrantedAuthority::getAuthority)
////                .anyMatch(role -> role.equals("ROLE_DRUG_IMPORTER"));
////    }
////
////    // Helper method to get drug importer ID from authentication
////    private Long getDrugImporterId() {
////        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
////        if (authentication != null && authentication.getPrincipal() != null) {
////            return ((UserDetailsImpl) authentication.getPrincipal()).getDrugImporterId();
////        }
////        return null;
////    }
////
////    // Helper method to validate drug importer role and ID
////    private void validateDrugImporter(Long requestedDrugImporterId) {
////        if (!hasDrugImporterRole()) {
////            throw new AccessDeniedException("User does not have the DRUG_IMPORTER role");
////        }
////
////        Long authDrugImporterId = getDrugImporterId();
////        if (authDrugImporterId == null || !authDrugImporterId.equals(requestedDrugImporterId)) {
////            throw new AccessDeniedException("User does not have access to this drug importer's data");
////        }
////    }
//
//
//
//    @PutMapping("/{id}")
//    public ResponseEntity<QuotationStatusDTO> updateQuotationStatus(
//            @PathVariable Long id,
//            @RequestBody QuotationStatusDTO quotationStatusDTO) {
////        validateDrugImporter(quotationStatusDTO.getDrugImporterId());
//        QuotationStatusDTO updatedStatus = quotationStatusService.updateQuotationStatus(id, quotationStatusDTO);
//        return new ResponseEntity<>(updatedStatus, HttpStatus.OK);
//    }
//
//    @GetMapping("/{id}")
//    public ResponseEntity<QuotationStatusDTO> getQuotationStatusById(
//            @PathVariable Long id,
//            @RequestParam Long drugImporterId) {
////        validateDrugImporter(drugImporterId);
//        QuotationStatusDTO status = quotationStatusService.getQuotationStatusById(id);
//        return new ResponseEntity<>(status, HttpStatus.OK);
//    }
//
//    @GetMapping
//    public ResponseEntity<List<QuotationStatusDTO>> getAllQuotationStatusesByDrugImporterId(
//            @RequestParam Long drugImporterId) {
////        validateDrugImporter(drugImporterId);
//        List<QuotationStatusDTO> statuses = quotationStatusService.getAllQuotationStatusesByDrugImporterId(drugImporterId);
//        return new ResponseEntity<>(statuses, HttpStatus.OK);
//    }
//
//    @PutMapping("/update-status")
//    public ResponseEntity<QuotationStatusDTO> updateStatus(
//            @RequestParam Long requestId,
//            @RequestParam Long drugImporterId,
//            @RequestParam QuotationStatusEnum status) {
////        validateDrugImporter(drugImporterId);
//        QuotationStatusDTO updatedStatus = quotationStatusService.updateStatus(requestId, drugImporterId, status);
//        return new ResponseEntity<>(updatedStatus, HttpStatus.OK);
//    }
//}