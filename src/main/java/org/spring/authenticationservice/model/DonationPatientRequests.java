package org.spring.authenticationservice.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;
import org.spring.authenticationservice.model.Enum.StatusEnum;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
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
    private LocalDateTime adminApprovedDate;

    public StatusEnum getStatus() {
        return status;
    }

    public void setStatus(StatusEnum status) {
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getHospitalName() {
        return hospitalName;
    }

    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }

    public String getMessageToPatient() {
        return messageToPatient;
    }

    public void setMessageToPatient(String messageToPatient) {
        this.messageToPatient = messageToPatient;
    }

    public String getPrescriptionLink() {
        return prescriptionLink;
    }

    public void setPrescriptionLink(String prescriptionLink) {
        this.prescriptionLink = prescriptionLink;
    }

    public Long getAdminId() {
        return adminId;
    }

    public void setAdminId(Long adminId) {
        this.adminId = adminId;
    }

    public LocalDateTime getAdminApprovedDate() {
        return adminApprovedDate;
    }

    public void setAdminApprovedDate(LocalDateTime adminApprovedDate) {
        this.adminApprovedDate = adminApprovedDate;
    }
}