package org.spring.authenticationservice.repository.patient;

import org.spring.authenticationservice.model.patient.DonationRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DonationRequestRepo extends JpaRepository<DonationRequest, Long> {
}
