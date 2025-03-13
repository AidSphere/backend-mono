package org.spring.authenticationservice.model;

import jakarta.persistence.*;
import lombok.Data;
import org.spring.authenticationservice.model.Enum.StatusEnum;
import org.springframework.data.annotation.CreatedDate;

import java.util.Date;

@Entity
@Data
@Table(name = "donationRequest")
public class DonationRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long patientId;

    @CreatedDate
    private Date createdAt;

    @Column(nullable = true)
    private String hospitalName;

    @Column(nullable = true)
    private String massageToPatient;

    @Column(nullable = false)
    private String prescriptionLink;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private StatusEnum Status;

    private Long AdminId;

    private Date AdminApprovedDate;
}
