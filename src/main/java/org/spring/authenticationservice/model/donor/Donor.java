package org.spring.authenticationservice.model.donor;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name="donors")
public class Donor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonProperty("firstName")
    @Column(nullable = false)
    private String firstName;


    @JsonProperty("lastName")
    @Column(nullable = false)
    private String lastName;


    @JsonProperty("email")
    @Column(nullable = false, unique = true)
    private String email;


    @JsonProperty("NIC")
    @Column(nullable = false, unique = true)
    private String NIC;


    @JsonProperty("phoneNumber")
    @Column(nullable = false, unique = true)
    private String phoneNumber;


    @Column(nullable = false)
    @JsonProperty("address")
    private String address;


    @JsonProperty("description")
    private String description;




    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNIC() {
        return NIC;
    }

    public void setNIC(String NIC) {
        this.NIC = NIC;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}







