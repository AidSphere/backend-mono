package org.spring.authenticationservice.model.patient;

import jakarta.persistence.*;
import lombok.Data;
import org.spring.authenticationservice.model.Enum.StatusEnum;
import org.spring.authenticationservice.model.donor.Donation;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "donation_request")
@Data
public class DonationRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "request_id")
    private Long requestId;

    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "prescription_url")
    private String prescriptionUrl;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private StatusEnum status = StatusEnum.PENDING;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "expected_date")
    private Date expectedDate;

    @Column(name = "hospital_name")
    private String hospitalName;

    @Column(name = "message_to_patient")
    private String messageToPatient;

    @Column(name = "admin_id")
    private Long adminId;

    @Column(name = "admin_approved_date")
    private LocalDateTime adminApprovedAt;

    @Column(name="default_price")
    private BigDecimal defaultPrice;  // Default price for the prescribed items

    @OneToMany(mappedBy = "donationRequest")
    private List<Donation> donations;


    @ElementCollection
    @CollectionTable(name = "donation_request_images", joinColumns = @JoinColumn(name = "request_id"))
    @Column(name = "images")
    private List<String> images;

    @ElementCollection
    @CollectionTable(name = "donation_request_documents", joinColumns = @JoinColumn(name = "request_id"))
    @Column(name = "documents")
    private List<String> documents;

    @ElementCollection
    @CollectionTable(name = "patient_prescribed_medicines", joinColumns = @JoinColumn(name = "request_id"))
    private List<PrescribedMedicine> prescribedMedicines;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}

