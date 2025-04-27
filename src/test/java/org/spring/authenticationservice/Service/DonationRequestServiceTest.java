package org.spring.authenticationservice.Service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.spring.authenticationservice.DTO.donation.DonationRequestCreateDto;
import org.spring.authenticationservice.DTO.donation.DonationRequestResponseDto;
import org.spring.authenticationservice.DTO.donation.DonationRequestUpdateDto;
import org.spring.authenticationservice.Service.patient.DonationRequestService;
import org.spring.authenticationservice.mapper.patient.DonationRequestMapper;
import org.spring.authenticationservice.model.patient.DonationRequest;
import org.spring.authenticationservice.model.patient.Patient;
import org.spring.authenticationservice.repository.patient.DonationRequestRepo;
import org.spring.authenticationservice.repository.patient.PatientRepo;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DonationRequestServiceTest {

    @Mock
    private DonationRequestRepo donationRequestRepository;

    @Mock
    private PatientRepo patientRepository;

    @Mock
    private DonationRequestMapper mapper;

    @InjectMocks
    private DonationRequestService donationRequestService;

    private DonationRequest existingRequest;
    private DonationRequestUpdateDto updateDto;
    private DonationRequest updatedRequest;
    private DonationRequestResponseDto responseDto;

    @BeforeEach
    void setUp() {
        existingRequest = new DonationRequest();
        updateDto = new DonationRequestUpdateDto();
        updatedRequest = new DonationRequest();
        responseDto = new DonationRequestResponseDto();
    }

//    create donation request
    @Test
    void createDonationRequest_Success() {
        // Arrange
        DonationRequestCreateDto createDto = new DonationRequestCreateDto();
        Patient patient = new Patient();
        patient.setPatientId(1L);
        createDto.setPatientId(1L);

        when(patientRepository.findById(1L)).thenReturn(Optional.of(patient));
        when(mapper.FromCreatetoDonationRequest(any(DonationRequestCreateDto.class)))
                .thenReturn(existingRequest);
        when(donationRequestRepository.save(any(DonationRequest.class)))
                .thenReturn(existingRequest);
        when(mapper.toResponseDto(any(DonationRequest.class)))
                .thenReturn(responseDto);

        // Act
        DonationRequestResponseDto result = donationRequestService.createDonationRequest(createDto);

        // Assert
        assertNotNull(result);
        verify(patientRepository).findById(1L);
        verify(mapper).FromCreatetoDonationRequest(createDto);
        verify(donationRequestRepository).save(existingRequest);
        verify(mapper).toResponseDto(existingRequest);
    }

    @Test
    void createDonationRequest_PatientNotFound() {
        // Arrange
        DonationRequestCreateDto createDto = new DonationRequestCreateDto();
        createDto.setPatientId(1L);

        when(patientRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () ->
                donationRequestService.createDonationRequest(createDto));

        verify(patientRepository).findById(1L);
        verify(mapper, never()).FromCreatetoDonationRequest(any());
        verify(donationRequestRepository, never()).save(any());
        verify(mapper, never()).toResponseDto(any());
    }

    //    update donation request

    @Test
    void updateDonationRequest_Success() {
        when(donationRequestRepository.findById(any(Long.class))).thenReturn(Optional.of(existingRequest));
        when(mapper.updateDonationRequestFromDto(any(DonationRequestUpdateDto.class), any(DonationRequest.class)))
                .thenReturn(updatedRequest);
        when(donationRequestRepository.save(any(DonationRequest.class))).thenReturn(updatedRequest);
        when(mapper.toResponseDto(any(DonationRequest.class))).thenReturn(responseDto);

        DonationRequestResponseDto result = donationRequestService.updateDonationRequest(1L, updateDto);

        assertNotNull(result);
        verify(donationRequestRepository).findById(1L);
        verify(donationRequestRepository).save(updatedRequest);
        verify(mapper).updateDonationRequestFromDto(updateDto, existingRequest);
        verify(mapper).toResponseDto(updatedRequest);
    }

    @Test
    void updateDonationRequest_NotFound() {
        when(donationRequestRepository.findById(any(Long.class))).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            donationRequestService.updateDonationRequest(1L, updateDto);
        });

        verify(donationRequestRepository).findById(1L);
        verify(donationRequestRepository, never()).save(any(DonationRequest.class));
        verify(mapper, never()).updateDonationRequestFromDto(any(DonationRequestUpdateDto.class), any(DonationRequest.class));
        verify(mapper, never()).toResponseDto(any(DonationRequest.class));
    }

    //    delete donation request

    @Test
    void deleteDonationRequest_Success() {
        // Arrange
        Long requestId = 1L;
        when(donationRequestRepository.findById(requestId))
                .thenReturn(Optional.of(existingRequest));

        // Act
        assertDoesNotThrow(() -> donationRequestService.deleteDonationRequest(requestId));

        // Assert
        verify(donationRequestRepository).findById(requestId);
        verify(donationRequestRepository).delete(existingRequest);
    }

    @Test
    void deleteDonationRequest_NotFound() {
        // Arrange
        Long requestId = 1L;
        when(donationRequestRepository.findById(requestId))
                .thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class,  //change to resource not found
                () -> donationRequestService.deleteDonationRequest(requestId));

        verify(donationRequestRepository).findById(requestId);
        verify(donationRequestRepository, never()).delete(any());
    }

    // test for controller
    @Test
    void testDeleteEndpoint() {
        // Arrange
        Long requestId = 1L;
        when(donationRequestRepository.findById(requestId))
                .thenReturn(Optional.of(existingRequest));

        // Act & Assert
        assertDoesNotThrow(() -> donationRequestService.deleteDonationRequest(requestId));

        verify(donationRequestRepository).findById(requestId);
        verify(donationRequestRepository).delete(existingRequest);
    }
}