package org.spring.authenticationservice.model.drugImporter;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.spring.authenticationservice.model.Enum.QuotationStatusEnum;

import java.time.LocalDateTime;

@Entity
@Table(name = "quotation_statuses")
@Data
@AllArgsConstructor
public class QuotationStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "request_id", nullable = false)
    private Long requestId;

    @Column(name = "drug_importer_id", nullable = false)
    private Long drugImporterId;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private QuotationStatusEnum status;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @Column(name = "updated_date")
    private LocalDateTime updatedDate;

    @PrePersist
    protected void onCreate() {
        this.createdDate = LocalDateTime.now();
        this.updatedDate = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedDate = LocalDateTime.now();
    }

    public QuotationStatus() {
        this.status = QuotationStatusEnum.SENT;
    }
}

