package org.spring.authenticationservice.Service.drugImporter;

import org.spring.authenticationservice.DTO.drugImporter.QuotationStatusDTO;
import org.spring.authenticationservice.model.Enum.QuotationStatusEnum;
import org.spring.authenticationservice.model.drugImporter.QuotationStatus;

import java.util.List;

public interface QuotationStatusService {
    QuotationStatusDTO createQuotationStatus(QuotationStatusDTO quotationStatusDTO);
    QuotationStatusDTO updateQuotationStatus(Long id, QuotationStatusDTO quotationStatusDTO);
    QuotationStatusDTO getQuotationStatusById(Long id);
    List<QuotationStatusDTO> getAllQuotationStatusesByDrugImporterId(Long drugImporterId);
    QuotationStatusDTO updateStatus(Long requestId, Long drugImporterId, QuotationStatusEnum
            status);

}
