package org.spring.authenticationservice.DTO.donor;

import lombok.Data;

@Data
public class DonorResponseDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String nic;
    private String dob;
    private String address;

    public DonorResponseDTO(Long id, String firstName, String lastName, String email, String phone, String nic, String dob, String address) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.nic = nic;
        this.dob = dob;
        this.address = address;
    }

    public DonorResponseDTO(Long id, String firstName, String lastName, String email, String phone, String nic) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.nic = nic;
    }

    // Getters and Setters
}
