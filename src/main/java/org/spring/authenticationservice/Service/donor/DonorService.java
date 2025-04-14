package org.spring.authenticationservice.Service.donor;

import org.spring.authenticationservice.DTO.donor.DonorRegDto;
import org.spring.authenticationservice.model.donor.Donor;

public interface DonorService {

    Donor createDonor(DonorRegDto dto);

    DonorRegDto updateDonor(DonorRegDto dto, Long id);

    boolean deleteDonor(Long id);

    DonorRegDto getDonorById(Long id);


}
