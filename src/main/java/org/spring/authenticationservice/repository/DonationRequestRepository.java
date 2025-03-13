package org.spring.authenticationservice.repository;


import org.spring.authenticationservice.model.DonationPatientRequests;
import org.spring.authenticationservice.model.Enum.StatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DonationRequestRepository extends JpaRepository<DonationPatientRequests, Long> {
        List<DonationPatientRequests> findByStatus(StatusEnum status);
}


