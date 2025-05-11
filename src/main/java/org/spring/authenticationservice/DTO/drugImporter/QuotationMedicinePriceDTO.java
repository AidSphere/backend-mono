package org.spring.authenticationservice.DTO.drugImporter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuotationMedicinePriceDTO {
    private Long id;
    private Long medicineId;
    private Double price;
}
