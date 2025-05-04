package org.spring.authenticationservice.Service.patient;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.spring.authenticationservice.DTO.patient.MedicalRecordDto;
import org.spring.authenticationservice.mapper.patient.PatientMapper;
import org.spring.authenticationservice.model.patient.MedicalRecord;
import org.spring.authenticationservice.model.patient.Patient;
import org.spring.authenticationservice.repository.patient.MedicalRecordRepo;
import org.spring.authenticationservice.repository.patient.PatientRepo;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MedicalRecordService {
    private final MedicalRecordRepo medicalRecordRepo;
    private final PatientRepo patientRepo;
    private final PatientMapper mapper;

    @Transactional
    public MedicalRecordDto createMedicalRecord(Long patientId, MedicalRecordDto medicalRecordDto) {
        Patient patient = patientRepo.findById(patientId)
                .orElseThrow(() -> new EntityNotFoundException("Patient not found with id: " + patientId));

        if (patient.getMedicalRecord() != null) {
            throw new IllegalStateException("Patient already has a medical record");
        }

        MedicalRecord medicalRecord = new MedicalRecord();
        updateMedicalRecordFields(medicalRecord, medicalRecordDto);
        medicalRecord.setPatient(patient);

        MedicalRecord savedRecord = medicalRecordRepo.save(medicalRecord);
        return mapper.toMedicalRecordDto(savedRecord);
    }

    @Transactional
    public MedicalRecordDto getMedicalRecordByPatientId(Long patientId) {
        Patient patient = patientRepo.findById(patientId)
                .orElseThrow(() -> new EntityNotFoundException("Patient not found with id: " + patientId));

        if (patient.getMedicalRecord() == null) {
            throw new EntityNotFoundException("Medical record not found for patient id: " + patientId);
        }

        return mapper.toMedicalRecordDto(patient.getMedicalRecord());
    }

    @Transactional
    public MedicalRecordDto updateMedicalRecord(Long recordId, MedicalRecordDto medicalRecordDto) {
        MedicalRecord medicalRecord = medicalRecordRepo.findById(recordId)
                .orElseThrow(() -> new EntityNotFoundException("Medical record not found with id: " + recordId));

        updateMedicalRecordFields(medicalRecord, medicalRecordDto);
        MedicalRecord updatedRecord = medicalRecordRepo.save(medicalRecord);
        return mapper.toMedicalRecordDto(updatedRecord);
    }

    @Transactional
    public void deleteMedicalRecord(Long recordId) {
        MedicalRecord medicalRecord = medicalRecordRepo.findById(recordId)
                .orElseThrow(() -> new EntityNotFoundException("Medical record not found with id: " + recordId));

        Patient patient = medicalRecord.getPatient();
        patient.setMedicalRecord(null);
        medicalRecordRepo.delete(medicalRecord);
    }

    private void updateMedicalRecordFields(MedicalRecord record, MedicalRecordDto dto) {
        record.setCancerType(dto.getCancerType());
        record.setCancerStage(dto.getCancerStage());
        record.setHospitalName(dto.getHospitalName());
        record.setHospitalAddress(dto.getHospitalAddress());
        record.setDoctorName(dto.getDoctorName());
        record.setDoctorContact(dto.getDoctorContact());
        record.setMedicalDocuments(dto.getMedicalDocuments());
    }
}