package org.spring.authenticationservice.Service.drugImporter.impl;

import org.spring.authenticationservice.DTO.drugImporter.QuotationStatusDTO;
import org.spring.authenticationservice.Service.drugImporter.QuotationStatusService;
import org.spring.authenticationservice.model.Enum.QuotationStatusEnum;
import org.spring.authenticationservice.model.drugImporter.QuotationStatus;
import org.spring.authenticationservice.repository.drugImporter.QuotationStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuotationStatusServiceImpl implements QuotationStatusService {

    @Autowired
    private QuotationStatusRepository quotationStatusRepository;

    @Override
    public QuotationStatusDTO createQuotationStatus(QuotationStatusDTO quotationStatusDTO) {
        QuotationStatus quotationStatus = new QuotationStatus();
        quotationStatus.setRequestId(quotationStatusDTO.getRequestId());
        quotationStatus.setDrugImporterId(quotationStatusDTO.getDrugImporterId());
        quotationStatus.setStatus(quotationStatusDTO.getStatus());

        QuotationStatus savedStatus = quotationStatusRepository.save(quotationStatus);

        return convertToDTO(savedStatus);
    }

    @Override
    public QuotationStatusDTO updateQuotationStatus(Long id, QuotationStatusDTO quotationStatusDTO) {
        QuotationStatus quotationStatus = quotationStatusRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Quotation status not found with id: " + id));

        quotationStatus.setStatus(quotationStatusDTO.getStatus());
        quotationStatus.setUpdatedDate(LocalDateTime.now());

        QuotationStatus updatedStatus = quotationStatusRepository.save(quotationStatus);

        return convertToDTO(updatedStatus);
    }

    @Override
    public QuotationStatusDTO getQuotationStatusById(Long id) {
        QuotationStatus quotationStatus = quotationStatusRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Quotation status not found with id: " + id));

        return convertToDTO(quotationStatus);
    }

    @Override
    public List<QuotationStatusDTO> getAllQuotationStatusesByDrugImporterId(Long drugImporterId) {
        List<QuotationStatus> statuses = quotationStatusRepository.findByDrugImporterId(drugImporterId);

        return statuses.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public QuotationStatusDTO updateStatus(Long requestId, Long drugImporterId, QuotationStatusEnum status) {
        QuotationStatus quotationStatus = quotationStatusRepository.findByRequestIdAndDrugImporterId(requestId, drugImporterId);

        if (quotationStatus == null) {
            // Create new status if it doesn't exist
            quotationStatus = new QuotationStatus();
            quotationStatus.setRequestId(requestId);
            quotationStatus.setDrugImporterId(drugImporterId);
            quotationStatus.setStatus(status);
        } else {
            // Update existing status
            quotationStatus.setStatus(status);
        }

        quotationStatus.setUpdatedDate(LocalDateTime.now());

        QuotationStatus savedStatus = quotationStatusRepository.save(quotationStatus);

        return convertToDTO(savedStatus);
    }

    private QuotationStatusDTO convertToDTO(QuotationStatus quotationStatus) {
        QuotationStatusDTO dto = new QuotationStatusDTO();
        dto.setId(quotationStatus.getId());
        dto.setRequestId(quotationStatus.getRequestId());
        dto.setDrugImporterId(quotationStatus.getDrugImporterId());
        dto.setStatus(quotationStatus.getStatus());
        dto.setCreatedDate(quotationStatus.getCreatedDate());
        dto.setUpdatedDate(quotationStatus.getUpdatedDate());

        return dto;
    }
}

