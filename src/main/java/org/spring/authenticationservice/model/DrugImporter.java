package org.spring.authenticationservice.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Data
@Entity
@NoArgsConstructor
@Table(name = "drug_importer")
public class DrugImporter {

    @Id
    @Column(nullable = false, unique = true, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(unique = true,nullable = false)
    private String licenceNumber;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private Number phoneNumber;


    private String website;


    private String description;

    @Column(nullable = false)
    private String uploadDocumentUrl;


}