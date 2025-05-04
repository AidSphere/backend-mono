package org.spring.authenticationservice.mapper.patient;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.spring.authenticationservice.DTO.patient.*;
import org.spring.authenticationservice.model.patient.MedicalRecord;
import org.spring.authenticationservice.model.patient.Patient;
import org.spring.authenticationservice.model.patient.PatientVerification;

@Mapper(componentModel = "spring")
public interface PatientMapper {

    @Mapping(target = "patientId", ignore = true)
    @Mapping(target = "verification", ignore = true)
    @Mapping(target = "medicalRecord", ignore = true)
    @Mapping(target = "donationRequests", ignore = true)
    @Mapping(target = "dateOfBirth", source = "dateOfBirth", dateFormat = "yyyy-MM-dd")
    Patient toPatient(PatientCreateDto dto);

    @Mapping(target = "verification", ignore = true)
    @Mapping(target = "patientId", ignore = true)
    @Mapping(target = "medicalRecord", ignore = true)
    @Mapping(target = "donationRequests", ignore = true)
    @Mapping(target = "dateOfBirth", source = "dateOfBirth", dateFormat = "yyyy-MM-dd")
    Patient toPatient(PatientUpdateDto dto, @MappingTarget Patient existingPatient);

    @Mapping(target = "verificationId", ignore = true)
    @Mapping(target = "patient", ignore = true)
    @Mapping(target = "verificationStatus", constant = "PENDING")
    @Mapping(target = "verifiedAt", ignore = true)
    @Mapping(target = "documents", ignore = true)
    @Mapping(source = "governmentIdType", target = "governmentIdType")
    @Mapping(source = "governmentIdNumber", target = "governmentIdNumber")
    @Mapping(source = "governmentIdDocumentUrl", target = "governmentIdDocumentUrl")
    PatientVerification toPatientVerification(PatientCreateDto dto);

    MedicalRecordDto toMedicalRecordDto(MedicalRecord medicalRecord);

    @Mapping(target = "verification", source = "verification")
    @Mapping(target = "dateOfBirth", source = "dateOfBirth", dateFormat = "yyyy-MM-dd")
    @Mapping(target = "medicalRecord", source = "medicalRecord")
    PatientProfileDto toProfileDto(Patient patient);


    @Mapping(target = "dateOfBirth", source = "dateOfBirth", dateFormat = "yyyy-MM-dd")
    @Mapping(target = "medicalRecord", source = "medicalRecord")
    PatientResponseDto toResponseDto(Patient patient);

}
