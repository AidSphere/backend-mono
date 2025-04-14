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

        Donor donor = donorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Donor not found with id: " + id));

        // Update the donor's details
        donor.setFirstName(dto.getFirstName());
        donor.setLastName(dto.getLastName());
        donor.setEmail(dto.getEmail());
        donor.setPhone(dto.getPhone());
        donor.setNic(dto.getNic());
        donor.setDob(dto.getDob());
        donor.setAddress(dto.getAddress());

        // Save the updated donor
        Donor updatedDonor = donorRepository.save(donor);

        //update the users table as well

        return DonorRegDto.builder()
                .firstName(updatedDonor.getFirstName())
                .lastName(updatedDonor.getLastName())
                .email(updatedDonor.getEmail())
                .phone(updatedDonor.getPhone())
                .nic(updatedDonor.getNic())
                .dob(updatedDonor.getDob())
                .address(updatedDonor.getAddress())
                .build();
    }

    @Override
    public boolean deleteDonor(Long id) {

        Donor donor = donorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Donor not found with id: " + id));

        //also delete from the users table uncheck is deleted button

        // Delete the donor
        donorRepository.delete(donor);
        return true;
    }

    @Override
    public DonorRegDto getDonorById(Long id) {
        Donor donor = donorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Donor not found with id: " + id));

        return DonorRegDto.builder()
                .firstName(donor.getFirstName())
                .lastName(donor.getLastName())
                .email(donor.getEmail())
                .phone(donor.getPhone())
                .nic(donor.getNic())
                .dob(donor.getDob())
                .address(donor.getAddress())
                .build();
    }
}
