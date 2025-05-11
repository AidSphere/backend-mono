package org.spring.authenticationservice.DTO.drugImporter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.spring.authenticationservice.model.Enum.QuotationStatusEnum;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuotationStatusDTO {
    private Long id;
    private Long requestId;
    private Long drugImporterId;
    private QuotationStatusEnum status;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
}

