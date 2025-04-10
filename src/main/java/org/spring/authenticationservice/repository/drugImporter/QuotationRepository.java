package org.spring.authenticationservice.repository.drugImporter;


import org.spring.authenticationservice.model.drugImporter.Quotation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface QuotationRepository extends JpaRepository<Quotation, Long> {
    List<Quotation> findByDrugImporterId(Long drugImporterId);
    List<Quotation> findByRequestId(Long requestId);
    Quotation findByIdAndDrugImporterId(Long id, Long drugImporterId);
}
