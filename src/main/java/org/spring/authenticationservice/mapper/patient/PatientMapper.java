package org.spring.authenticationservice.mapper.patient;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.spring.authenticationservice.DTO.patient.PatientCreateDto;
import org.spring.authenticationservice.DTO.patient.PatientResponseDto;
import org.spring.authenticationservice.model.patient.Patient;
import org.spring.authenticationservice.model.patient.PatientVerification;

@Mapper(componentModel = "spring")
public interface PatientMapper {

    @Mapping(target = "patientId", ignore = true)
    @Mapping(target = "verification", ignore = true)
    @Mapping(target = "medicalRecord", ignore = true)
    @Mapping(target = "donationRequests", ignore = true)
    Patient toPatient(PatientCreateDto dto);

    @Mapping(target = "verificationId", ignore = true)
    @Mapping(target = "patient", ignore = true)
    @Mapping(target = "verificationStatus", constant = "PENDING")
    @Mapping(target = "verifiedAt", ignore = true)
    @Mapping(target = "documents", ignore = true)
    @Mapping(source = "governmentIdType", target = "governmentIdType")
    @Mapping(source = "governmentIdNumber", target = "governmentIdNumber")
    @Mapping(source = "governmentIdDocumentUrl", target = "governmentIdDocumentUrl")
    PatientVerification toPatientVerification(PatientCreateDto dto);

    @Mapping(target = "verification", source = "verification")
    PatientResponseDto toResponseDto(Patient patient);

}
