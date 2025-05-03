package org.spring.authenticationservice.repository.patient;

import org.spring.authenticationservice.model.Enum.StatusEnum;
import org.spring.authenticationservice.model.patient.DonationRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DonationRequestRepo extends JpaRepository<DonationRequest, Long> {
    List<DonationRequest> findByStatus(StatusEnum status);
    List<DonationRequest> findByPatient_PatientId(Long patientId);
}
