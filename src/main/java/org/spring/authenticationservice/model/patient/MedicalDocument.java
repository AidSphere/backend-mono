package org.spring.authenticationservice.model.patient;

import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.spring.authenticationservice.model.Enum.MedicalDocumentType;
import org.spring.authenticationservice.model.Enum.RecordVisibility;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MedicalDocument {
    private String documentName;
    private MedicalDocumentType documentType;
    private String documentUrl;
    @Enumerated(EnumType.STRING)
    private RecordVisibility visibility = RecordVisibility.PRIVATE;
}
