package org.spring.authenticationservice.Service.donor;

import org.spring.authenticationservice.DTO.donor.DonorRegDto;
import org.spring.authenticationservice.DTO.donor.DonorResponseDTO;
import org.spring.authenticationservice.exception.ResourceNotFoundException;
import org.spring.authenticationservice.model.donor.Donor;
import org.spring.authenticationservice.model.patient.Patient;

import java.util.List;

public interface DonorService {

    Donor createDonor(DonorRegDto dto) throws Exception;

    DonorResponseDTO updateDonor(DonorResponseDTO dto);

    boolean deleteDonor(Long id);

    Donor getDonor();

    Donor createDonorByAdmin(DonorRegDto dto);

    List<Patient> getPendingPatients() throws ResourceNotFoundException;
}
