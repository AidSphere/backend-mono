package org.spring.authenticationservice.Service.donor.impl;

import lombok.AllArgsConstructor;
import org.spring.authenticationservice.DTO.donor.DonorRegDto;
import org.spring.authenticationservice.Service.donor.DonorService;
import org.spring.authenticationservice.model.donor.Donor;
import org.spring.authenticationservice.repository.donor.DonorRepository;
import org.spring.authenticationservice.repository.security.UserRepository;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class donorServiceImpl implements DonorService {

    private UserRepository userRepository;
    private DonorRepository donorRepository;

    @Override
    public Donor createDonor(DonorRegDto dto) {
        if(userRepository.existsByEmail(dto.getEmail())) {
            throw new RuntimeException("User already exists with email: " + dto.getEmail());
        }

        // Here you would typically save the donor to the database
        Donor donor = Donor.builder()
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .email(dto.getEmail())
                .phone(dto.getPhone())
                .nic(dto.getNic())
                .dob(dto.getDob())
                .address(dto.getAddress())
                .build();

        return donorRepository.save(donor);
    }

    @Override
    public DonorRegDto updateDonor(DonorRegDto dto, Long id) {
        return null;
    }

    @Override
    public void deleteDonor(Long id) {

    }

    @Override
    public DonorRegDto getDonorById(Long id) {
        return null;
    }
}
