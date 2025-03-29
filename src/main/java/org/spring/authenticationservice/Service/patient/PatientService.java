package org.spring.authenticationservice.Service.patient;


import lombok.RequiredArgsConstructor;
import org.spring.authenticationservice.DTO.patient.PatientCreateDto;
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
}