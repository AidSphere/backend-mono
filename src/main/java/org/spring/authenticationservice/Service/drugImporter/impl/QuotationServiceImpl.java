package org.spring.authenticationservice.Service.drugImporter.impl;

import jakarta.transaction.Transactional;
import org.spring.authenticationservice.DTO.drugImporter.QuotationDTO;
import org.spring.authenticationservice.DTO.drugImporter.QuotationMedicinePriceDTO;
import org.spring.authenticationservice.Service.drugImporter.QuotationService;
import org.spring.authenticationservice.Service.drugImporter.QuotationStatusService;
import org.spring.authenticationservice.model.Enum.QuotationStatusEnum;
import org.spring.authenticationservice.model.drugImporter.Quotation;
import org.spring.authenticationservice.model.drugImporter.QuotationMedicinePrice;
import org.spring.authenticationservice.model.drugImporter.QuotationStatus;
import org.spring.authenticationservice.repository.drugImporter.QuotationMedicinePriceRepository;
import org.spring.authenticationservice.repository.drugImporter.QuotationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuotationServiceImpl implements QuotationService {

    @Autowired
    private QuotationRepository quotationRepository;

    @Autowired
    private QuotationMedicinePriceRepository medicinePriceRepository;

    @Autowired
    private QuotationStatusService quotationStatusService;

    @Override
    @Transactional
    public QuotationDTO createQuotation(QuotationDTO quotationDTO) {
        Quotation quotation = new Quotation();
        quotation.setDrugImporterId(quotationDTO.getDrugImporterId());
        quotation.setRequestId(quotationDTO.getRequestId());
        quotation.setDiscount(quotationDTO.getDiscount());

        Quotation savedQuotation = quotationRepository.save(quotation);

        List<QuotationMedicinePrice> medicinePrices = new ArrayList<>();
        if (quotationDTO.getMedicinePrices() != null) {
            medicinePrices = quotationDTO.getMedicinePrices().stream()
                    .map(medicinePrice -> {
                        QuotationMedicinePrice price = new QuotationMedicinePrice();
                        price.setQuotation(savedQuotation);
                        price.setMedicineId(medicinePrice.getMedicineId());
                        price.setPrice(medicinePrice.getPrice());
                        return price;
                    })
                    .collect(Collectors.toList());
        }

        medicinePriceRepository.saveAll(medicinePrices);
        savedQuotation.setMedicinePrices(medicinePrices);

        return convertToDTO(savedQuotation);
    }

    @Override
    @Transactional
    public QuotationDTO updateQuotation(Long id, QuotationDTO quotationDTO) {
        Quotation quotation = quotationRepository.findByIdAndDrugImporterId(id, quotationDTO.getDrugImporterId());

        if (quotation == null) {
            throw new RuntimeException("Quotation not found with id: " + id);
        }

        quotation.setDiscount(quotationDTO.getDiscount());
        quotation.setUpdatedDate(LocalDateTime.now());

        // Delete existing medicine prices and create new ones
        medicinePriceRepository.deleteByQuotationId(id);

        List<QuotationMedicinePrice> medicinePrices = quotationDTO.getMedicinePrices().stream()
                .map(medicinePrice -> {
                    QuotationMedicinePrice price = new QuotationMedicinePrice();
                    price.setQuotation(quotation);
                    price.setMedicineId(medicinePrice.getMedicineId());
                    price.setPrice(medicinePrice.getPrice());
                    return price;
                })
                .collect(Collectors.toList());

        medicinePriceRepository.saveAll(medicinePrices);
        quotation.setMedicinePrices(medicinePrices);

        return convertToDTO(quotation);
    }

    @Override
    @Transactional
    public void deleteQuotation(Long id, Long drugImporterId) {
        Quotation quotation = quotationRepository.findByIdAndDrugImporterId(id, drugImporterId);

        if (quotation == null) {
            throw new RuntimeException("Quotation not found with id: " + id);
        }

        quotationRepository.delete(quotation);
    }

    @Override
    public QuotationDTO getQuotationById(Long id, Long drugImporterId) {
        Quotation quotation = quotationRepository.findByIdAndDrugImporterId(id, drugImporterId);

        if (quotation == null) {
            throw new RuntimeException("Quotation not found with id: " + id);
        }

        return convertToDTO(quotation);
    }

    @Override
    public List<QuotationDTO> getAllQuotationsByDrugImporterId(Long drugImporterId) {
        List<Quotation> quotations = quotationRepository.findByDrugImporterId(drugImporterId);

        return quotations.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public QuotationDTO sendQuotation(Long id, Long drugImporterId) {
        Quotation quotation = quotationRepository.findByIdAndDrugImporterId(id, drugImporterId);

        if (quotation == null) {
            throw new RuntimeException("Quotation not found with id: " + id);
        }


        // Update or create quotation status
        quotationStatusService.updateStatus(quotation.getRequestId(), drugImporterId, QuotationStatusEnum.SENT);

        return convertToDTO(quotation);
    }

    private QuotationDTO convertToDTO(Quotation quotation) {
        QuotationDTO dto = new QuotationDTO();
        dto.setId(quotation.getId());
        dto.setDrugImporterId(quotation.getDrugImporterId());
        dto.setRequestId(quotation.getRequestId());
        dto.setDiscount(quotation.getDiscount());
        dto.setCreatedDate(quotation.getCreatedDate());
        dto.setUpdatedDate(quotation.getUpdatedDate());

        List<QuotationMedicinePriceDTO> medicinePriceDTOs = quotation.getMedicinePrices().stream()
                .map(medicinePrice -> {
                    QuotationMedicinePriceDTO priceDTO = new QuotationMedicinePriceDTO();
                    priceDTO.setId(medicinePrice.getId());
                    priceDTO.setMedicineId(medicinePrice.getMedicineId());
                    priceDTO.setPrice(medicinePrice.getPrice());
                    return priceDTO;
                })
                .collect(Collectors.toList());

        dto.setMedicinePrices(medicinePriceDTOs);

        return dto;
    }
}
