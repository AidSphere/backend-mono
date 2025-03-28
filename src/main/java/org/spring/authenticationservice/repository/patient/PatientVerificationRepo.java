package org.spring.authenticationservice.repository.patient;

import org.spring.authenticationservice.model.patient.PatientVerification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientVerificationRepo extends JpaRepository<PatientVerification, Long> {
}
