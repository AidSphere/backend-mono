package org.spring.authenticationservice.Service.donor;

import org.spring.authenticationservice.DTO.donor.DonorRegDto;
import org.spring.authenticationservice.exception.ResourceNotFoundException;
import org.spring.authenticationservice.model.donor.Donor;
import org.spring.authenticationservice.model.patient.Patient;

import java.util.List;

public interface DonorService {

    Donor createDonor(DonorRegDto dto) throws Exception;

    DonorRegDto updateDonor(DonorRegDto dto, Long id);

    boolean deleteDonor(Long id);

    DonorRegDto getDonorById(Long id);

    Donor createDonorByAdmin(DonorRegDto dto);

    List<Patient> getPendingPatients() throws ResourceNotFoundException;
}
