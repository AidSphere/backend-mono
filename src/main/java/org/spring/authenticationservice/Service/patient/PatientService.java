package org.spring.authenticationservice.Service.patient;


import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.spring.authenticationservice.DTO.patient.PatientCreateDto;
import org.spring.authenticationservice.DTO.patient.PatientResponseDto;
import org.spring.authenticationservice.DTO.patient.PatientUpdateDto;
import org.spring.authenticationservice.DTO.security.RegisterUserDto;
import org.spring.authenticationservice.Service.security.AuthService;
import org.spring.authenticationservice.Utils.FilterSpecification;
import org.spring.authenticationservice.mapper.patient.PatientMapper;
import org.spring.authenticationservice.model.patient.Patient;
import org.spring.authenticationservice.model.patient.PatientVerification;
import org.spring.authenticationservice.repository.patient.PatientRepo;
import org.spring.authenticationservice.repository.patient.PatientVerificationRepo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class PatientService {

    private final PatientRepo patientRepo;
    private final PatientVerificationRepo verificationRepo;
    private final PatientMapper mapper;
    private final AuthService authService;

    @Transactional
    public Patient createPatient(PatientCreateDto dto) {
        // Create patient
        Patient patient = mapper.toPatient(dto);
        Patient savedPatient = patientRepo.save(patient);

        // Create verification
        PatientVerification verification = mapper.toPatientVerification(dto);
        verification.setPatient(savedPatient);
        PatientVerification savedVerification = verificationRepo.save(verification);

        // Set verification in patient
        savedPatient.setVerification(savedVerification);


        RegisterUserDto registerUserDto = new RegisterUserDto();
        registerUserDto.setEmail(dto.getEmail());
        registerUserDto.setPassword(dto.getPassword());
        registerUserDto.setRole("PATIENT");
        authService.RegisterUser(registerUserDto);

        return patientRepo.save(savedPatient);
    }

    public Patient getPatientById(Long id) {
        return patientRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Patient not found with id: " + id));
    }

    @Transactional
    public PatientResponseDto updatePatient(PatientUpdateDto patient, Long id) {
        Patient existingPatient = getPatientById(id);
        Patient updatedPatient = mapper.toPatient(patient, existingPatient);
        Patient savedPatient = patientRepo.save(updatedPatient);
        return mapper.toResponseDto(savedPatient);
    }

    @Transactional
    public void deletePatient(Long id) {
        var patient = getPatientById(id);

        // Delete verification first due to foreign key constraint
        if (patient.getVerification() != null) {
            verificationRepo.delete(patient.getVerification());
        }

        patientRepo.delete(patient);
    }

    public Page<PatientResponseDto> getPatient(
            Map<String, String> filters,
            Pageable pageable) {
        Specification<Patient> specifications = new FilterSpecification<>(filters);
        return patientRepo.findAll(specifications, pageable).map(mapper::toResponseDto);
    }

}