package org.spring.authenticationservice.repository.patient;

import org.spring.authenticationservice.model.patient.MedicalRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicalRecordRepo extends JpaRepository<MedicalRecord, Long> {
}
