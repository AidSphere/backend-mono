package org.spring.authenticationservice.controller;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.spring.authenticationservice.DTO.api.ApiResponse;
import org.spring.authenticationservice.DTO.patient.donation.DonationRequestCreateDto;
import org.spring.authenticationservice.DTO.patient.donation.DonationRequestResponseDto;
import org.spring.authenticationservice.DTO.patient.donation.DonationRequestUpdateDto;
import org.spring.authenticationservice.Service.patient.DonationRequestService;
import org.spring.authenticationservice.controller.patient.DonationRequestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DonationRequestControllerTest {

    @Mock
    private DonationRequestService donationRequestService;

    @InjectMocks
    private DonationRequestController controller;

    @Test
    void createDonationRequest_Success() {
        // Arrange
        DonationRequestCreateDto createDto = new DonationRequestCreateDto();
        DonationRequestResponseDto responseDto = new DonationRequestResponseDto();

        when(donationRequestService.createDonationRequest(any(DonationRequestCreateDto.class)))
                .thenReturn(responseDto);

        // Act
        ResponseEntity<ApiResponse<?>> response = controller.createDonationRequest(createDto);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Donation Request Submitted Successfully", response.getBody().getMessage());
        verify(donationRequestService).createDonationRequest(createDto);
    }

    @Test
    void updateDonationRequest_Success() {
        // Arrange
        Long requestId = 1L;
        DonationRequestUpdateDto updateDto = new DonationRequestUpdateDto();
        DonationRequestResponseDto responseDto = new DonationRequestResponseDto();

        when(donationRequestService.updateDonationRequest(eq(requestId), any(DonationRequestUpdateDto.class)))
                .thenReturn(responseDto);

        // Act
        ResponseEntity<ApiResponse<?>> response = controller.updateDonationRequest(requestId, updateDto);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Donation Request Updated Successfully", response.getBody().getMessage());
        verify(donationRequestService).updateDonationRequest(requestId, updateDto);
    }

    @Test
    void deleteDonationRequest_Success() {
        // Arrange
        Long requestId = 1L;
        doNothing().when(donationRequestService).deleteDonationRequest(requestId);

        // Act
        ResponseEntity<ApiResponse<?>> response = controller.deleteDonationRequest(requestId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Donation Request Deleted Successfully", response.getBody().getMessage());
        verify(donationRequestService).deleteDonationRequest(requestId);
    }

    @Test
    void deleteDonationRequest_NotFound() {
        // Arrange
        Long requestId = 1L;
        doThrow(new RuntimeException("Donation request not found"))
                .when(donationRequestService).deleteDonationRequest(requestId);

        // Act & Assert
        assertThrows(RuntimeException.class,
                () -> controller.deleteDonationRequest(requestId));
        verify(donationRequestService).deleteDonationRequest(requestId);
    }
}