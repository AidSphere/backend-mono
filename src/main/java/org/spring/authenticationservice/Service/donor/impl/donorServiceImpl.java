package org.spring.authenticationservice.Service.donor.impl;

import lombok.AllArgsConstructor;
import org.spring.authenticationservice.DTO.donor.DonorRegDto;
import org.spring.authenticationservice.DTO.security.RegisterUserDto;
import org.spring.authenticationservice.Service.donor.DonorService;
import org.spring.authenticationservice.Service.security.AuthService;
import org.spring.authenticationservice.Service.security.EmailService;
import org.spring.authenticationservice.Service.security.JwtService;
import org.spring.authenticationservice.exception.ResourceNotFoundException;
import org.spring.authenticationservice.exception.UserAlreadyExistedException;
import org.spring.authenticationservice.model.donor.Donor;
import org.spring.authenticationservice.model.drugImporter.DrugImporter;
import org.spring.authenticationservice.model.patient.Patient;
import org.spring.authenticationservice.model.security.AccessControl;
import org.spring.authenticationservice.model.security.Role;
import org.spring.authenticationservice.model.security.User;
import org.spring.authenticationservice.repository.donor.DonorRepository;
import org.spring.authenticationservice.repository.patient.PatientRepo;
import org.spring.authenticationservice.repository.security.RoleRepository;
import org.spring.authenticationservice.repository.security.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

import static org.springframework.data.repository.util.ClassUtils.ifPresent;

@AllArgsConstructor
@Service
public class donorServiceImpl implements DonorService {

    private  PatientRepo patientRepo;
    private UserRepository userRepository;
    private DonorRepository donorRepository;
    private AuthService authService;

    @Override
    public Donor createDonor(DonorRegDto dto) throws Exception {
        if(userRepository.existsByEmail(dto.getEmail())) {
            throw new RuntimeException("User already exists with email: " + dto.getEmail());
        }

        Donor donor = Donor.builder()
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .email(dto.getEmail())
                .phone(dto.getPhone())
                .nic(dto.getNic())
                .dob(dto.getDob())
                .address(dto.getAddress())
                .build();

        RegisterUserDto userDto = new RegisterUserDto();
        userDto.setEmail(dto.getEmail());
        userDto.setPassword(dto.getPassword());
        userDto.setRole("DONOR");
        authService.RegisterUser(userDto);

        return donorRepository.save(donor);
    }

    @Override
    public Donor createDonorByAdmin(DonorRegDto dto){
        // 1. Check if email already exists
        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new UserAlreadyExistedException(dto.getEmail() + " already exists");
        }

        // 2. Create and save donor entity
        Donor donor = donorRepository.save(
                Donor.builder()
                        .firstName(dto.getFirstName())
                        .lastName(dto.getLastName())
                        .email(dto.getEmail())
                        .phone(dto.getPhone())
                        .nic(dto.getNic())
                        .dob(dto.getDob())
                        .address(dto.getAddress())
                        .build()
        );

        // 3. Register the donor as a user
        RegisterUserDto userDto = new RegisterUserDto(
                dto.getEmail(),
                dto.getFirstName() + "@" + dto.getPhone(),  // Temp password logic
                "DONOR"
        );
            authService.RegisterUser(userDto);
        return donor;
    }


    @Override
    public List<Patient> getPendingPatients() throws ResourceNotFoundException {
        List<User> pendingUsers = Optional.ofNullable(userRepository.findByAdminApproval(AccessControl.PENDING))
                .orElse(Collections.emptyList());

        if (pendingUsers.isEmpty()) {
            throw new ResourceNotFoundException("No pending users found");
        }

        List<Patient> pendingPatient = new ArrayList<>();
        for (User user : pendingUsers) {
            patientRepo.findByEmail(user.getEmail()).ifPresent(pendingPatient::add);
        }

        if (pendingPatient.isEmpty()) {
            throw new ResourceNotFoundException("No pending donors found");
        }

        return pendingPatient;
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
