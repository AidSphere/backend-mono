package org.spring.authenticationservice.DTO.patient;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.spring.authenticationservice.model.patient.MedicalDocument;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MedicalRecordDto {
    private Long recordId;
    private String cancerType;
    private String cancerStage;
    private String hospitalName;
    private String hospitalAddress;
    private String doctorName;
    private String doctorContact;
    private List<MedicalDocument> medicalDocuments;
}