package org.spring.authenticationservice.Service;


import org.spring.authenticationservice.DTO.DonationRequestConfirmationDTO;
import org.spring.authenticationservice.Utils.SecurityUtil;
import org.spring.authenticationservice.model.DonationRequestApproval;
import org.spring.authenticationservice.model.Enum.StatusEnum;
import org.spring.authenticationservice.repository.DonationRequestRepository;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class DonationRequestService {
    private final DonationRequestRepository donationRequestRepository;
    private final SecurityUtil securityUtil;

    public DonationRequestService(DonationRequestRepository donationRequestRepository,SecurityUtil securityUtil) {
        this.donationRequestRepository = donationRequestRepository;
        this.securityUtil = securityUtil;
    }

    public List<DonationRequestApproval> getPendingDonationRequests() {
        return donationRequestRepository.findByStatus(StatusEnum.PENDING);
    }

    public DonationRequestApproval createDonationRequest(DonationRequestApproval donationRequestApproval) {
        return donationRequestRepository.save(donationRequestApproval);
    }

    public ResponseEntity<?> updateConfirmation(Long requestId, DonationRequestConfirmationDTO donationRequestConfirmationDTO) throws ChangeSetPersister.NotFoundException {

        DonationRequestApproval donationRequestApproval = donationRequestRepository.findById(requestId).orElseThrow(ChangeSetPersister.NotFoundException::new);

        // Update fields properly
        //update this field when security enables
        donationRequestApproval.setAdminId(213123123L);
        donationRequestApproval.setAdminApprovedDate(LocalDateTime.now());
        donationRequestApproval.setStatus(donationRequestConfirmationDTO.getStatus());

        // Save the updated record
        donationRequestRepository.save(donationRequestApproval);

        return ResponseEntity.ok().build();
    }

}
