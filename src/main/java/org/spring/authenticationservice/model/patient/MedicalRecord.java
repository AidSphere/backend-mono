package org.spring.authenticationservice.model.patient;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Entity
@Table(name = "medical_records")
@Data
public class MedicalRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "record_id")
    private Long recordId;

    @OneToOne
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    @Column(name = "cancer_type")
    private String cancerType;

    @Column(name = "cancer_stage")
    private String cancerStage;

    @Column(name = "hospital_name")
    private String hospitalName;

    @Column(name = "hospital_address")
    private String hospitalAddress;

    @Column(name = "doctor_name")
    private String doctorName;

    @Column(name = "doctor_contact")
    private String doctorContact;

    @ElementCollection
    @CollectionTable(name = "medical_documents", joinColumns = @JoinColumn(name = "record_id"))
    private List<MedicalDocument> medicalDocuments;

}

