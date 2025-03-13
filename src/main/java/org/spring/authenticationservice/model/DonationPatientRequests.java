package org.spring.authenticationservice.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;
import org.spring.authenticationservice.model.Enum.StatusEnum;
import org.springframework.data.annotation.CreatedDate;

import java.util.Date;

@Entity
@Data
public class DonationPatientRequests {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("id")
    private Long id;

    @JsonProperty("patientId")
    private Long patientId;

    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @JsonProperty("hospitalName")
    private String hospitalName;

    @JsonProperty("massageToPatient")
    private String messageToPatient;

    @Column(nullable = false)
    @JsonProperty("prescriptionLink")
    private String prescriptionLink;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    @JsonProperty("status")
    private StatusEnum status = StatusEnum.PENDING;

    @JsonProperty("adminId")
    private Long adminId;

    @JsonProperty("adminApprovedDate")
    private Date adminApprovedDate;
}