package org.spring.authenticationservice.repository;


import org.spring.authenticationservice.model.DonationRequestApproval;
import org.spring.authenticationservice.model.Enum.StatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DonationRequestRepository extends JpaRepository<DonationRequestApproval, Long> {
        List<DonationRequestApproval> findByStatus(StatusEnum status);
}


