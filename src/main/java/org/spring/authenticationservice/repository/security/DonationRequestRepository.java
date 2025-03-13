package org.spring.authenticationservice.repository.security;


import org.spring.authenticationservice.model.DonationRequest;
import org.spring.authenticationservice.model.Enum.StatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DonationRequestRepository extends JpaRepository<DonationRequest, Long> {
    List<DonationRequest> findByStatus(StatusEnum status);
}

