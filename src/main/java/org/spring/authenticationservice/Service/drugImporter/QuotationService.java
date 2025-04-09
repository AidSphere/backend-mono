package org.spring.authenticationservice.Service.drugImporter;
import org.spring.authenticationservice.DTO.drugImporter.QuotationDTO;

import java.util.List;

public interface QuotationService {
    QuotationDTO createQuotation(QuotationDTO quotationDTO);
    QuotationDTO updateQuotation(Long id, QuotationDTO quotationDTO);
    void deleteQuotation(Long id, Long drugImporterId);
    QuotationDTO getQuotationById(Long id, Long drugImporterId);
    List<QuotationDTO> getAllQuotationsByDrugImporterId(Long drugImporterId);
    QuotationDTO sendQuotation(Long id, Long drugImporterId);
}
