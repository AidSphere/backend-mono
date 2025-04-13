package org.spring.authenticationservice.DTO.donor;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.util.Date;

@Data
public class DonorRegDto {

    @NotEmpty(message = "FirstName can not be a null or empty")
    @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
    private String firstName;

    @NotEmpty(message = "LastName can not be a null or empty")
    @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
    private String lastName;

    @NotNull(message = "Date of Birth can not be a null or empty")
    @Email(message = "Invalid email format")
    private String email;

    @NotNull(message = "Date of Birth can not be a null or empty")
    @Pattern(regexp="(^$|[0-9]{10})",message = "Phone number must be 10 digits")
    private String phone;

    @NotNull(message = "Date of Birth can not be a null or empty")
    //@Pattern(regexp="(^$|[0-9]{9}[vV])",message = "nic number must be 10 digits and end letter can be v only")
    private String nic;

    @NotNull(message = "Date of Birth can not be a null or empty")
    private Date dob;

    @NotEmpty(message = "Address can not be a null or empty")
    private String address;

}
