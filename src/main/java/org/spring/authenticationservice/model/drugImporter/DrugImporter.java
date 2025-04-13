package org.spring.authenticationservice.model.drugImporter;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.spring.authenticationservice.model.security.User;

import java.time.LocalDateTime;

/**
 * Entity class for the drug importers
 * Represents a drug importer profile that is associated with a User entity
 * User entity handles authentication while this entity stores profile data
 */
@Entity
@Table(name = "drug_importers")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DrugImporter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Column(nullable = false)
    private String name;


    private String phone;

    private String email;

    private String address;

    @Column(name = "license_number", nullable = false)
    private String licenseNumber;

    private String website;

    private String nic;

    @Column(name = "additional_text", columnDefinition = "TEXT")
    private String additionalText;

    // Store URLs to documents instead of file paths
    @Column(name = "nicotine_proof_url")
    private String nicotineProofUrl;

    @Column(name = "license_proof_url")
    private String licenseProofUrl;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

}