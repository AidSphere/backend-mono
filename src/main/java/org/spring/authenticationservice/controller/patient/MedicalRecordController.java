package org.spring.authenticationservice.controller.patient;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.spring.authenticationservice.DTO.api.ApiResponse;
import org.spring.authenticationservice.DTO.patient.MedicalRecordDto;
import org.spring.authenticationservice.Service.patient.MedicalRecordService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/medical-records")
@RequiredArgsConstructor
public class MedicalRecordController {
    private final MedicalRecordService medicalRecordService;

    @PostMapping("/patients/{patientId}")
    public ResponseEntity<ApiResponse<?>> createMedicalRecord(
            @PathVariable Long patientId,
            @Valid @RequestBody MedicalRecordDto medicalRecordDto) {
        var responseDto = medicalRecordService.createMedicalRecord(patientId, medicalRecordDto);
        return ResponseEntity.ok(ApiResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.CREATED.value())
                .message("Medical Record Created Successfully")
                .data(responseDto)
                .build());
    }

    @GetMapping("/patients/{patientId}")
    public ResponseEntity<ApiResponse<?>> getMedicalRecordByPatient(@PathVariable Long patientId) {
        var responseDto = medicalRecordService.getMedicalRecordByPatientId(patientId);
        return ResponseEntity.ok(ApiResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.OK.value())
                .message("Medical Record Retrieved Successfully")
                .data(responseDto)
                .build());
    }

    @PutMapping("/{recordId}")
    public ResponseEntity<ApiResponse<?>> updateMedicalRecord(
            @PathVariable Long recordId,
            @Valid @RequestBody MedicalRecordDto medicalRecordDto) {
        var responseDto = medicalRecordService.updateMedicalRecord(recordId, medicalRecordDto);
        return ResponseEntity.ok(ApiResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.OK.value())
                .message("Medical Record Updated Successfully")
                .data(responseDto)
                .build());
    }

    @DeleteMapping("/{recordId}")
    public ResponseEntity<ApiResponse<?>> deleteMedicalRecord(@PathVariable Long recordId) {
        medicalRecordService.deleteMedicalRecord(recordId);
        return ResponseEntity.ok(ApiResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.OK.value())
                .message("Medical Record Deleted Successfully")
                .build());
    }
}
