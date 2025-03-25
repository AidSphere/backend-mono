package org.spring.authenticationservice.model.patient;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.spring.authenticationservice.model.DonationRequestApproval;
import org.spring.authenticationservice.model.Enum.StatusEnum;
import org.springframework.data.annotation.CreatedDate;

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

    @CreatedDate
    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "expected_date")
    private Date expectedDate;

    @Column(name = "hospital_name")
    private String hospitalName;

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

    @OneToOne(mappedBy = "donationRequest", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private DonationRequestApproval approval;
}

@Embeddable
@Data
@NoArgsConstructor // Required for JPA
@AllArgsConstructor
class PrescribedMedicine {
    private String medicine;
    private String amount; // e.g., "500mg"
}