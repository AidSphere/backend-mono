package org.spring.authenticationservice.Service;


import org.spring.authenticationservice.DTO.DonationRequestConfirmationDTO;
import org.spring.authenticationservice.Utils.SecurityUtil;
import org.spring.authenticationservice.model.DonationPatientRequests;
import org.spring.authenticationservice.model.Enum.StatusEnum;
import org.spring.authenticationservice.repository.DonationRequestRepository;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Service
public class DonationRequestService {
    private final DonationRequestRepository donationRequestRepository;
    private final SecurityUtil securityUtil;

    public DonationRequestService(DonationRequestRepository donationRequestRepository,SecurityUtil securityUtil) {
        this.donationRequestRepository = donationRequestRepository;
        this.securityUtil = securityUtil;
    }

    public List<DonationPatientRequests> getPendingDonationRequests() {
        return donationRequestRepository.findByStatus(StatusEnum.PENDING);
    }

    public DonationPatientRequests createDonationRequest(DonationPatientRequests donationPatientRequests) {
        return donationRequestRepository.save(donationPatientRequests);
    }

    public ResponseEntity<?> updateConfirmation(Long requestId, DonationRequestConfirmationDTO donationRequestConfirmationDTO) throws ChangeSetPersister.NotFoundException {

        DonationPatientRequests donationPatientRequests = donationRequestRepository.findById(requestId).orElseThrow(ChangeSetPersister.NotFoundException::new);

        // Update fields properly
        //update this field when security enables
        donationPatientRequests.setAdminId(213123123L);
        donationPatientRequests.setAdminApprovedDate(LocalDateTime.now());
        donationPatientRequests.setStatus(donationRequestConfirmationDTO.getStatus());

        // Save the updated record
        donationRequestRepository.save(donationPatientRequests);

        return ResponseEntity.ok().build();
    }

}
