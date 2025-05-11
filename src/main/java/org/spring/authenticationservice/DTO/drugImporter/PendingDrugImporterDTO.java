package org.spring.authenticationservice.DTO.drugImporter;

import lombok.Data;

@Data
public class PendingDrugImporterDTO {
    private String name;
    private String phone;
    private String email;
    private String licenseNumber;
    private String website;
}