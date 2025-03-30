package org.spring.authenticationservice.controller.patient;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.spring.authenticationservice.DTO.api.ApiResponse;
import org.spring.authenticationservice.DTO.filter.PatientFilter;
import org.spring.authenticationservice.DTO.patient.PatientCreateDto;
import org.spring.authenticationservice.DTO.patient.PatientResponseDto;
import org.spring.authenticationservice.DTO.patient.PatientUpdateDto;
import org.spring.authenticationservice.Service.patient.PatientService;
import org.spring.authenticationservice.Utils.ApiUtil;
import org.spring.authenticationservice.mapper.patient.PatientMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequestMapping("/patients")
@RequiredArgsConstructor
public class PatientController {

    private final PatientService patientService;
    private final PatientMapper patientMapper;

    @PostMapping("/create")
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

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> updatePatient(
            @PathVariable Long id,
            @Valid @RequestBody PatientUpdateDto dto) {
            PatientResponseDto responseDto = patientService.updatePatient(dto, id);

        return ResponseEntity.ok(ApiResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.OK.value())
                .message("Patient Updated Successfully")
                .data(responseDto)
                .build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> deletePatient(@PathVariable Long id) {
        patientService.deletePatient(id);

        return ResponseEntity.ok(ApiResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.OK.value())
                .message("Patient Deleted Successfully")
                .build());
    }

    @GetMapping
    public ResponseEntity<ApiResponse<?>> getPatient(
            @Valid @ModelAttribute PatientFilter filterRequest,
            Pageable pageable
            ) {
        Map<String, String> filters = ApiUtil.getFilters(filterRequest); // generate filters from request params
        Page<PatientResponseDto> response = patientService.getPatient(filters, pageable);

        return ResponseEntity.ok(ApiResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.OK.value())
                .message("Patient Retrieved Successfully")
                .data(response.getContent())  // set content only
                .pagination(ApiUtil.getPagination(response)) // set pagination details
                .build());
    }

}