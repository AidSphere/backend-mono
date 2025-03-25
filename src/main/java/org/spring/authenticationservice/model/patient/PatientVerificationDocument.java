package org.spring.authenticationservice.model.patient;

import jakarta.persistence.*;
import lombok.Data;

import java.time.OffsetDateTime;

@Entity
@Table(name = "patient_verification_documents")
@Data
public class PatientVerificationDocument {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "verification_id", nullable = false)
    private PatientVerification patientVerification;

    private String documentName;
    private String documentUrl;
    private OffsetDateTime uploadedAt = OffsetDateTime.now();
}

