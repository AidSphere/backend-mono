package org.spring.authenticationservice.DTO.drugImporter;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuotationDTO {
    private Long id;
    private Long drugImporterId;
    private Long requestId;
    private Double discount;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
    private String status;
    private List<QuotationMedicinePriceDTO> medicinePrices;
}