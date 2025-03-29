package org.spring.authenticationservice.repository.patient;

import org.spring.authenticationservice.model.patient.PatientVerificationDocument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientVerificationDocRepo extends JpaRepository<PatientVerificationDocument, Long> {
}
