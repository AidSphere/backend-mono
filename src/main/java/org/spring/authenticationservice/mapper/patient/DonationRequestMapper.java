package org.spring.authenticationservice.mapper.patient;

import org.mapstruct.InheritConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.spring.authenticationservice.DTO.patient.donation.DonationRequestCreateDto;
import org.spring.authenticationservice.DTO.patient.donation.DonationRequestResponseDto;
import org.spring.authenticationservice.DTO.patient.donation.DonationRequestUpdateDto;
import org.spring.authenticationservice.model.patient.DonationRequest;

@Mapper(componentModel = "spring")
public interface DonationRequestMapper {

    @Mapping(target = "status", ignore = true)
    @Mapping(target = "requestId", ignore = true)
    @Mapping(target = "patient", ignore = true)
    @Mapping(target = "messageToPatient", ignore = true)
    @Mapping(target = "defaultPrice", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "adminId", ignore = true)
    @Mapping(target = "adminApprovedAt", ignore = true)
    @Mapping(target = "expectedDate", source = "expectedDate" , dateFormat = "yyyy-MM-dd")
    DonationRequest FromCreatetoDonationRequest(DonationRequestCreateDto dto);

    @InheritConfiguration
    @Mapping(source = "patient.patientId", target = "patientId") // Extract only patientId
    @Mapping(target = "expectedDate", source = "expectedDate" , dateFormat = "yyyy-MM-dd")
    DonationRequestResponseDto toResponseDto(DonationRequest donationRequest);

    @Mapping(target = "status", ignore = true)
    @Mapping(target = "requestId", ignore = true)
    @Mapping(target = "patient", ignore = true)
    @Mapping(target = "messageToPatient", ignore = true)
    @Mapping(target = "defaultPrice", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "adminId", ignore = true)
    @Mapping(target = "adminApprovedAt", ignore = true)
    @Mapping(target = "expectedDate", source = "expectedDate" , dateFormat = "yyyy-MM-dd")
    DonationRequest updateDonationRequestFromDto(DonationRequestUpdateDto donationRequestUpdateDto, @MappingTarget DonationRequest existingRequest);
}
