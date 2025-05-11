package org.spring.authenticationservice.controller.admin;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.spring.authenticationservice.DTO.api.ApiResponse;
import org.spring.authenticationservice.DTO.donor.DonorRegDto;
import org.spring.authenticationservice.DTO.drugImporter.DrugImporterRegisterRequest;
import org.spring.authenticationservice.DTO.patient.PatientCreateDto;
import org.spring.authenticationservice.Service.donor.DonorService;
import org.spring.authenticationservice.Service.drugImporter.DrugImporterService;
import org.spring.authenticationservice.Service.patient.PatientService;
import org.spring.authenticationservice.Service.security.AuthService;
import org.spring.authenticationservice.mapper.patient.PatientMapper;
import org.spring.authenticationservice.model.donor.Donor;
import org.spring.authenticationservice.model.security.ResetPasswordRequest;
import org.spring.authenticationservice.model.security.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

import static org.spring.authenticationservice.controller.drugImporter.DrugImporterController.getCurrentRequestPath;

@RestController
@RequestMapping("/admin")
@CrossOrigin(origins = "*")
@AllArgsConstructor
public class AccountController {

    private PatientService patientService;
    private PatientMapper patientMapper;
    private DrugImporterService drugImporterService;
    private DonorService donorService;
    private AuthService   authService;


    //create patient
    @PostMapping("/createPatient")
    public ResponseEntity<ApiResponse<?>> createPatient(@Valid @RequestBody PatientCreateDto dto) {
        var createdPatient = patientService.createPatient(dto);
        var responseDto = patientMapper.toResponseDto(createdPatient);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.builder()
                        .timestamp(LocalDateTime.now())
                        .status(HttpStatus.CREATED.value())
                        .message("Patient Created Successfully")
                        .data(responseDto)
                        .build());
    }

    //create donor
    @PostMapping("/createDonor")
    public ResponseEntity<ApiResponse<?>> createDonor(@Valid @RequestBody DonorRegDto dto){
        Donor createdDonor = donorService.createDonorByAdmin(dto);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.builder()
                        .timestamp(LocalDateTime.now())
                        .status(HttpStatus.CREATED.value())
                        .message("Donor Created Successfully")
                        .data(createdDonor)
                        .build());
    }


    //create drug importer
    @PostMapping(value = "/createDrgImporter", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<Void>> registerDrugImporter(
            @RequestBody @Valid DrugImporterRegisterRequest request) throws Exception {

        drugImporterService.registerDrugImporter(request);

        ApiResponse<Void> response = ApiResponse.<Void>builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.CREATED.value())
                .message("Drug Importer registered successfully.")
                .path(getCurrentRequestPath())
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/users")
    public ResponseEntity<ApiResponse<?>> getUserByEmail(@RequestParam String email) {

        Object user = authService.getUserByEmail(email);
        System.out.println(user);


        return ResponseEntity.ok(ApiResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.OK.value())
                .message("Fetched user by email successfully")
                .data(user)
                .build());
    }

    @PostMapping("/reset-password")
    public ResponseEntity<ApiResponse<?>> resetPassword(@RequestBody ResetPasswordRequest request) {
        authService.resetPassword(request.getEmail(), request.getNewPassword());
        return ResponseEntity.ok(ApiResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.OK.value())
                .message("Password reset successfully")
                .build());
    }


}
