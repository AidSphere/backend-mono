package org.spring.authenticationservice.dto;

import org.springframework.web.multipart.MultipartFile;

public class UpdateDocumentsRequest {
    private MultipartFile nicotineProof;
    private MultipartFile licenseProof;

    // Default constructor
    public UpdateDocumentsRequest() {
    }

    // Constructor with all fields
    public UpdateDocumentsRequest(MultipartFile nicotineProof, MultipartFile licenseProof) {
        this.nicotineProof = nicotineProof;
        this.licenseProof = licenseProof;
    }

    // Getters
    public MultipartFile getNicotineProof() {
        return nicotineProof;
    }

    public MultipartFile getLicenseProof() {
        return licenseProof;
    }

    // Setters
    public void setNicotineProof(MultipartFile nicotineProof) {
        this.nicotineProof = nicotineProof;
    }

    public void setLicenseProof(MultipartFile licenseProof) {
        this.licenseProof = licenseProof;
    }

    // toString method
    @Override
    public String toString() {
        return "UpdateDocumentsRequest{" +
                "nicotineProof=" + (nicotineProof != null ? "provided" : "not provided") +
                ", licenseProof=" + (licenseProof != null ? "provided" : "not provided") +
                '}';
    }
}