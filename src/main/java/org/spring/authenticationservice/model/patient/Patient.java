package org.spring.authenticationservice.model.patient;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "patient")
@Data
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "patient_id")
    private Long patientId;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "dob", nullable = false)
    private Date dateOfBirth;

    @Column(name = "gender")
    private String gender;

    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    @Column(name = "email")
    private String email;

    @Column(name = "permanent_address", nullable = false)
    private String permanentAddress;

    @Column(name = "current_address")
    private String currentAddress;

    @URL
    @Column(name = "profile_image_url")
    private String profileImageUrl;

    @OneToOne
    @JoinColumn(name = "verification_id")
    @JsonBackReference
    private PatientVerification verification;

    @OneToOne(mappedBy = "patient", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private MedicalRecord medicalRecord;

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DonationRequest> donationRequests;

}

