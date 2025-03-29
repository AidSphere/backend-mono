package org.spring.authenticationservice.Service.patient;


import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.spring.authenticationservice.DTO.patient.PatientCreateDto;
import org.spring.authenticationservice.DTO.patient.PatientResponseDto;
import org.spring.authenticationservice.DTO.patient.PatientUpdateDto;
import org.spring.authenticationservice.mapper.patient.PatientMapper;
import org.spring.authenticationservice.model.patient.Patient;
import org.spring.authenticationservice.model.patient.PatientVerification;
import org.spring.authenticationservice.repository.patient.PatientRepo;
import org.spring.authenticationservice.repository.patient.PatientVerificationRepo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PatientService {

    private final PatientRepo patientRepo;
    private final PatientVerificationRepo verificationRepo;
    private final PatientMapper mapper;

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
}