package org.spring.authenticationservice.repository.drugImporter;


import org.spring.authenticationservice.model.drugImporter.QuotationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface QuotationStatusRepository extends JpaRepository<QuotationStatus, Long> {
    List<QuotationStatus> findByDrugImporterId(Long drugImporterId);
    List<QuotationStatus> findByRequestId(Long requestId);
    QuotationStatus findByRequestIdAndDrugImporterId(Long requestId, Long drugImporterId);
}
