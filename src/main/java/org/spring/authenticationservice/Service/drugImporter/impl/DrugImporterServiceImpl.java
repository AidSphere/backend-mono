package org.spring.authenticationservice.Service.drugImporter.impl;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spring.authenticationservice.DTO.drugImporter.DrugImporterRegisterRequest;
import org.spring.authenticationservice.DTO.drugImporter.DrugImporterUpdateRequest;

import org.spring.authenticationservice.Service.drugImporter.DrugImporterService;
import org.spring.authenticationservice.Service.security.EmailService;
import org.spring.authenticationservice.Service.security.JwtService;
import org.spring.authenticationservice.exception.InvalidTokenException;
import org.spring.authenticationservice.exception.ResourceNotFoundException;
import org.spring.authenticationservice.model.drugImporter.DrugImporter;
import org.spring.authenticationservice.model.security.Role;
import org.spring.authenticationservice.model.security.User;
import org.spring.authenticationservice.repository.drugImporter.DrugImporterRepository;
import org.spring.authenticationservice.repository.security.RoleRepository;
import org.spring.authenticationservice.repository.security.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

/**
 * Implementation of the DrugImporterService interface
 */
@Service
@AllArgsConstructor
public class DrugImporterServiceImpl implements DrugImporterService {

    private static final Logger log = LoggerFactory.getLogger(DrugImporterServiceImpl.class);



    private DrugImporterRepository drugImporterRepository;

    private UserRepository userRepository;

    private RoleRepository roleRepository;

    private EmailService emailService;
    private PasswordEncoder encoder;
    private JwtService jwtService;


    @Override
    @Transactional
    public DrugImporter registerDrugImporter(DrugImporterRegisterRequest request) throws Exception {
        log.info("Registering new drug importer with email: {}", request.getEmail());




        // Check if email is already in use
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Email is already registered");
        }


        // Create drug importer entity
        DrugImporter drugImporter = DrugImporter.builder()
                .name(request.getName())
                .phone(request.getPhone())
                .address(request.getAddress())
                .licenseNumber(request.getLicenseNumber())
                .website(request.getWebsite())
                .nic(request.getNic())
                .additionalText(request.getAdditionalText())
                .nicotineProofUrl(request.getNicotineProofUrl())
                .licenseProofUrl(request.getLicenseProofUrl())
                .build();

        // Save to database
        DrugImporter savedDrugImporter = drugImporterRepository.save(drugImporter);
        log.info("Drug importer registered successfully with ID: {}", savedDrugImporter.getId());

        return savedDrugImporter;
    }

//    @Override
//    @Transactional
//    public void activateDrugImporter(String token) throws Exception {
//        log.info("Activating drug importer account with token: {}", token);
//
//        // Find drug importer by token
//        DrugImporter drugImporter = drugImporterRepository.findByActivationToken(token)
//                .orElseThrow(() -> new InvalidTokenException("Invalid activation token"));
//
//        // Check if token is expired
//        if (drugImporter.getActivationTokenExpiry().isBefore(LocalDateTime.now())) {
//            throw new InvalidTokenException("Activation token has expired");
//        }
//
//        // Activate user account
//        User user = drugImporter.getUser();
//        user.setEnabled(true);
//        userRepository.save(user);
//
//        // Clear activation token
//        drugImporter.setActivationToken(null);
//        drugImporter.setActivationTokenExpiry(null);
//        drugImporterRepository.save(drugImporter);
//
//        log.info("Drug importer account activated: {}", user.getEmail());
//    }

    @Override
    public DrugImporter findByEmail(String email) throws Exception {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + email));

        return drugImporterRepository.findByEmail(user.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("Drug importer not found for user with email: " + email));
    }

    @Override
    public DrugImporter findById(Long id) throws Exception {
        return drugImporterRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Drug importer not found with ID: " + id));
    }

    @Override
    public List<DrugImporter> findAll() {
        return drugImporterRepository.findAll();
    }

    @Override
    public DrugImporter registerDrugImporterByAdmin(DrugImporterRegisterRequest registerDto) throws Exception {
        // Check if user already exists
        if (userRepository.existsByEmail(registerDto.getEmail())) {
            throw new RuntimeException("User already exists with email: " + registerDto.getEmail());
        }

        // Create and save drug importer
        DrugImporter importer = DrugImporter.builder()
                .name(registerDto.getName())
                .email(registerDto.getEmail())
                .phone(registerDto.getPhone())
                .licenseNumber(registerDto.getLicenseNumber())
                .address(registerDto.getAddress())
                .build();

        drugImporterRepository.save(importer);

        // Generate default password: firstName@phone
        String defaultPassword = registerDto.getName() + "@" + registerDto.getPhone();

        // Create and save user
        User user = new User();
        user.setEmail(registerDto.getEmail());
        user.setPassword(encoder.encode(defaultPassword));
        Role userRole = roleRepository.findByName("DRUG_IMPORTER");
        user.getRoles().add(userRole);

        String activationToken = jwtService.generateActivationToken(user.getEmail());
        userRepository.save(user);

        // Send activation email
        Map<String, String> emailBody = Map.of(
                "to", user.getEmail(),
                "name", importer.getName(),
                "activationLink", "localhost:8080/activate?token=" + activationToken
        );

        try {
            String mailResponse = emailService.sendEmail("activation", emailBody);
            System.out.println(mailResponse);
        } catch (Exception e) {
            throw new Exception("Email could not be sent");
        }

        return importer;
    }


//    @Override
//    @Transactional
//    public DrugImporter updateDrugImporter(Long id, DrugImporterUpdateRequest request) throws ResourceNotFoundException {
//        log.info("Updating drug importer with ID: {}", id);
//
//        // Find drug importer
//        DrugImporter drugImporter = drugImporterRepository.findById(id)
//                .orElseThrow(() -> new ResourceNotFoundException("Drug importer not found with ID: " + id));
//
//        User user = drugImporter.getUser();
//
//        // Update fields if provided in request
//        if (request.getName() != null) {
//            drugImporter.setName(request.getName());
//        }
//
//        // If email is changing, check that new email is not in use
//        if (request.getEmail() != null && !request.getEmail().equals(user.getEmail())) {
//            if (userRepository.existsByEmail(request.getEmail())) {
//                throw new IllegalArgumentException("Email is already in use");
//            }
//            user.setEmail(request.getEmail());
//            userRepository.save(user);
//        }
//
//        if (request.getPhone() != null) {
//            drugImporter.setPhone(request.getPhone());
//        }
//
//        if (request.getAddress() != null) {
//            drugImporter.setAddress(request.getAddress());
//        }
//
//        if (request.getLicenseNumber() != null) {
//            drugImporter.setLicenseNumber(request.getLicenseNumber());
//        }
//
//        if (request.getWebsite() != null) {
//            drugImporter.setWebsite(request.getWebsite());
//        }
//
//        if (request.getNic() != null) {
//            drugImporter.setNic(request.getNic());
//        }
//
//        if (request.getAdditionalText() != null) {
//            drugImporter.setAdditionalText(request.getAdditionalText());
//        }
//
//        // Handle document URLs
//        if (request.getNicotineProofUrl() != null) {
//            drugImporter.setNicotineProofUrl(request.getNicotineProofUrl());
//        } else if (Boolean.TRUE.equals(request.getRemoveNicotineProof())) {
//            drugImporter.setNicotineProofUrl(null);
//        }
//
//        if (request.getLicenseProofUrl() != null) {
//            drugImporter.setLicenseProofUrl(request.getLicenseProofUrl());
//        } else if (Boolean.TRUE.equals(request.getRemoveLicenseProof())) {
//            drugImporter.setLicenseProofUrl(null);
//        }
//
//        return drugImporterRepository.save(drugImporter);
//    }

//    @Override
//    @Transactional
//    public void deleteDrugImporter(Long id) throws Exception {
//        DrugImporter drugImporter = findById(id);
//        User user = drugImporter.getUser();
//
//        // Delete drug importer profile first (due to foreign key constraint)
//        drugImporterRepository.delete(drugImporter);
//
//        // Then delete the user account
//        userRepository.delete(user);
//
//        log.info("Drug importer with ID {} and associated user account deleted", id);
//    }
}