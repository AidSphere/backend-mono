package org.spring.authenticationservice.repository.security.donor;


import org.spring.authenticationservice.model.donor.Donation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DonationRepository extends JpaRepository<Donation, Long> {
    List<Donation> findDonationByDonorid(Long donorid);
}

