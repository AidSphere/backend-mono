package org.spring.authenticationservice.dto;

import jakarta.validation.constraints.Email;

public class DrugImporterUpdateRequest {
    private String name;

    @Email(message = "Invalid email format")
    private String email;

    private String phone;
    private String address;
    private String licenseNumber;
    private String website;
    private String nic;
    private String additionalText;
    private Boolean removeNicotineProof;
    private Boolean removeLicenseProof;

    // Default constructor
    public DrugImporterUpdateRequest() {
    }

    // Getters
    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public String getWebsite() {
        return website;
    }

    public String getNic() {
        return nic;
    }

    public String getAdditionalText() {
        return additionalText;
    }

    public Boolean getRemoveNicotineProof() {
        return removeNicotineProof;
    }

    public Boolean getRemoveLicenseProof() {
        return removeLicenseProof;
    }

    // Setters
    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public void setNic(String nic) {
        this.nic = nic;
    }

    public void setAdditionalText(String additionalText) {
        this.additionalText = additionalText;
    }

    public void setRemoveNicotineProof(Boolean removeNicotineProof) {
        this.removeNicotineProof = removeNicotineProof;
    }

    public void setRemoveLicenseProof(Boolean removeLicenseProof) {
        this.removeLicenseProof = removeLicenseProof;
    }
}