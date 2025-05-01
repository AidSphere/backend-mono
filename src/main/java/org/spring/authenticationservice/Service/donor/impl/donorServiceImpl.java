package org.spring.authenticationservice.Service.donor.impl;

import lombok.AllArgsConstructor;
import org.spring.authenticationservice.DTO.donor.DonorRegDto;
import org.spring.authenticationservice.DTO.security.RegisterUserDto;
import org.spring.authenticationservice.Service.donor.DonorService;
import org.spring.authenticationservice.Service.security.AuthService;
import org.spring.authenticationservice.Service.security.EmailService;
import org.spring.authenticationservice.Service.security.JwtService;
import org.spring.authenticationservice.exception.ResourceNotFoundException;
import org.spring.authenticationservice.model.donor.Donor;
import org.spring.authenticationservice.model.drugImporter.DrugImporter;
import org.spring.authenticationservice.model.security.AccessControl;
import org.spring.authenticationservice.model.security.Role;
import org.spring.authenticationservice.model.security.User;
import org.spring.authenticationservice.repository.donor.DonorRepository;
import org.spring.authenticationservice.repository.security.RoleRepository;
import org.spring.authenticationservice.repository.security.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@Service
public class donorServiceImpl implements DonorService {

    private UserRepository userRepository;
    private DonorRepository donorRepository;
    private PasswordEncoder encoder;
    private JwtService jwtService;
    private RoleRepository roleRepository;
    private EmailService emailService;
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

        //need to setup seperate function later
        RegisterUserDto userDto = new RegisterUserDto();
        userDto.setEmail(dto.getEmail());
        userDto.setPassword(dto.getPassword());
        userDto.setRole("DONOR");
        authService.RegisterUser(userDto);

        return donorRepository.save(donor);
    }

    @Override
    public Donor createDonorByAdmin(DonorRegDto dto) {
        // Check if user already exists
        if(userRepository.existsByEmail(dto.getEmail())) {
            throw new RuntimeException("User already exists with email: " + dto.getEmail());
        }

        // Create and save donor
        Donor donor = Donor.builder()
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .email(dto.getEmail())
                .phone(dto.getPhone())
                .nic(dto.getNic())
                .dob(dto.getDob())
                .address(dto.getAddress())
                .build();

        donorRepository.save(donor);

        // Generate default password: firstname@phone
        String defaultPassword = dto.getFirstName() + "@" + dto.getPhone();

        // Create new user
        User user = new User();
        user.setEmail(dto.getEmail());
        user.setPassword(encoder.encode(defaultPassword)); // Hash the password
        Role userRole = roleRepository.findByName("DONOR"); // or from dto if dynamic
        user.getRoles().add(userRole);

        String activationToken = jwtService.generateActivationToken(user.getEmail());
        userRepository.save(user);

        // Send activation email
        Map<String, String> emailBody = Map.of(
                "to", user.getEmail(),
                "name", donor.getFirstName(), // use first name instead of email
                "activationLink", "localhost:8080/activate?token=" + activationToken
        );

        try {
            String mailResponse = emailService.sendEmail("activation", emailBody);
            System.out.println(mailResponse);
        } catch (Exception e) {
            throw new RuntimeException("Email could not be sent");
        }

        return donor;
    }

    @Override
    public List<Donor> getPendingDonors() throws ResourceNotFoundException {
        List<User> pendingUsers = userRepository.findByAdminApproval(AccessControl.PENDING);
        if (pendingUsers.isEmpty()) {
            throw new ResourceNotFoundException("No pending drug importers found");
        }
        List<Donor> pendingDonors = new ArrayList<>();
        for (User user : pendingUsers) {
            Donor donor = donorRepository.findByEmail(user.getEmail())
                    .orElseThrow(() -> new ResourceNotFoundException("Drug importer not found with email: " + user.getEmail()));
            pendingDonors.add(donor);
        }
        return pendingDonors;

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
