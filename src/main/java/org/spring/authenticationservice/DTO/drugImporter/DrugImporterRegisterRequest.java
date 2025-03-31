package org.spring.authenticationservice.DTO.drugImporter;



import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DrugImporterRegisterRequest {
    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password must be at least 6 characters")
    private String password;

    private String phone;
    private String address;

    @NotBlank(message = "License number is required")
    private String licenseNumber;


    private String website;
    private String nic;
    private String additionalText;

    private MultipartFile nicotineProofFile;
    private MultipartFile licenseProofFile;

}
