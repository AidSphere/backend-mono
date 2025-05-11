package org.spring.authenticationservice.repository.drugImporter;


import org.spring.authenticationservice.model.drugImporter.Quotation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface QuotationRepository extends JpaRepository<Quotation, Long> {
    List<Quotation> findByDrugImporterId(Long drugImporterId);

    Quotation findByIdAndDrugImporterId(Long id, Long drugImporterId);

    @Query("SELECT q FROM Quotation q WHERE q.requestId = :requestId AND q.status IN ('PENDING', 'ACCEPTED')")
    List<Quotation> findByRequestId(@Param("requestId") Long requestId);

    @Query("SELECT q FROM Quotation q WHERE q.requestId = :requestId AND q.status = :status")
    List<Quotation> findByRequestIdAndStatus(@Param("requestId") Long requestId, @Param("status") String status);
}
