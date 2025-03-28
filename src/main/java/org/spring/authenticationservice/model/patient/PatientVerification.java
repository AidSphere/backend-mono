package org.spring.authenticationservice.model.patient;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import org.spring.authenticationservice.model.Enum.PatientIDType;
import org.spring.authenticationservice.model.Enum.StatusEnum;

import java.time.OffsetDateTime;
import java.util.List;

@Entity
@Table(name = "patient_verifications")
@Data
public class PatientVerification {
    @Id
    @Column(name = "verification_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long verificationId;

    @OneToOne
    @JoinColumn(name = "patient_id", nullable = false)
    @JsonBackReference
    private Patient patient;

    @Column(name = "verification_status")
    @Enumerated(EnumType.STRING)
    private StatusEnum verificationStatus = StatusEnum.PENDING; // PENDING, APPROVED, REJECTED

    @Column(name = "verified_at")
    private OffsetDateTime verifiedAt;

    @Column(name = "government_id_type")
    @Enumerated(EnumType.STRING)
    private PatientIDType governmentIdType; // NIC, Birth Certificate, Passport

    @Column(name = "government_id_number")
    private String governmentIdNumber; // NIC or Birth Certificate Number

    @Column(name = "document_url")
    private String governmentIdDocumentUrl; // URL to uploaded NIC or Birth Certificate

    @OneToMany(mappedBy = "patientVerification", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PatientVerificationDocument> documents; // Multiple documents
}
