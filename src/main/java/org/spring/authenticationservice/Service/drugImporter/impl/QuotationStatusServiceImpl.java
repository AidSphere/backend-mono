package org.spring.authenticationservice.Service.drugImporter.impl;

import jakarta.transaction.Transactional;
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
    @Transactional
    public QuotationStatusDTO createQuotationStatus(QuotationStatusDTO quotationStatusDTO) {
        QuotationStatus quotationStatus = new QuotationStatus();
        quotationStatus.setRequestId(quotationStatusDTO.getRequestId());
        quotationStatus.setDrugImporterId(quotationStatusDTO.getDrugImporterId());
        quotationStatus.setStatus(quotationStatusDTO.getStatus());

        QuotationStatus savedStatus = quotationStatusRepository.save(quotationStatus);
        return convertToDTO(savedStatus);
    }

    @Override
    @Transactional
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
    @Transactional
    public QuotationStatusDTO updateStatus(Long requestId, Long drugImporterId, QuotationStatusEnum status) {
        QuotationStatus quotationStatus = quotationStatusRepository.findByRequestIdAndDrugImporterId(requestId,
                drugImporterId);

        // If status doesn't exist, create a new one
        if (quotationStatus == null) {
            quotationStatus = new QuotationStatus();
            quotationStatus.setRequestId(requestId);
            quotationStatus.setDrugImporterId(drugImporterId);
            quotationStatus.setStatus(status != null ? status : QuotationStatusEnum.DRAFT); // Default status
        }
        // If status exists and new status is provided, update it
        else if (status != null) {
            quotationStatus.setStatus(status);
            quotationStatus.setUpdatedDate(LocalDateTime.now());
        }

        QuotationStatus savedStatus = quotationStatusRepository.save(quotationStatus);
        return convertToDTO(savedStatus);
    }

    private QuotationStatusDTO convertToDTO(QuotationStatus status) {
        QuotationStatusDTO dto = new QuotationStatusDTO();
        dto.setId(status.getId());
        dto.setRequestId(status.getRequestId());
        dto.setDrugImporterId(status.getDrugImporterId());
        dto.setStatus(status.getStatus());
        dto.setCreatedDate(status.getCreatedDate());
        dto.setUpdatedDate(status.getUpdatedDate());
        return dto;
    }
}