package org.spring.authenticationservice.repository.drugImporter;

import org.spring.authenticationservice.model.drugImporter.QuotationMedicinePrice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface QuotationMedicinePriceRepository extends JpaRepository<QuotationMedicinePrice, Long> {
    List<QuotationMedicinePrice> findByQuotationId(Long quotationId);
    void deleteByQuotationId(Long quotationId);
}
