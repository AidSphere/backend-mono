package org.spring.authenticationservice.service;

import org.spring.authenticationservice.dto.DrugImporterRegisterRequest;
import org.spring.authenticationservice.dto.DrugImporterUpdateRequest;
import org.spring.authenticationservice.model.DrugImporter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface DrugImporterService {

    /**
     * Register a new Drug Importer
     *
     * @param registerDto The registration data
     * @param nicotineProofFile The nicotine proof document (optional)
     * @param licenseProofFile The license proof document (optional)
     * @return The created DrugImporter
     * @throws Exception If registration fails
     */
    DrugImporter registerDrugImporter(DrugImporterRegisterRequest registerDto,
                                      MultipartFile nicotineProofFile,
                                      MultipartFile licenseProofFile) throws Exception;

    /**
     * Find a Drug Importer by ID
     *
     * @param id The Drug Importer ID
     * @return The DrugImporter
     * @throws Exception If not found
     */
    DrugImporter findById(Long id) throws Exception;

    /**
     * Find a Drug Importer by email
     *
     * @param email The email address
     * @return The DrugImporter
     * @throws Exception If not found
     */
    DrugImporter findByEmail(String email) throws Exception;

    /**
     * Find all Drug Importers
     *
     * @return List of all DrugImporters
     */
    List<DrugImporter> findAll();

    /**
     * Update a Drug Importer
     *
     * @param id The Drug Importer ID
     * @param updateDto The update data
     * @param nicotineProofFile New nicotine proof document (optional)
     * @param licenseProofFile New license proof document (optional)
     * @return The updated DrugImporter
     * @throws Exception If update fails
     */
    DrugImporter updateDrugImporter(Long id,
                                    DrugImporterUpdateRequest updateDto,
                                    MultipartFile nicotineProofFile,
                                    MultipartFile licenseProofFile) throws Exception;

    /**
     * Delete a Drug Importer
     *
     * @param id The Drug Importer ID
     * @throws Exception If deletion fails
     */
    void deleteDrugImporter(Long id) throws Exception;

    /**
     * Activate a Drug Importer account
     *
     * @param token The activation token
     * @throws Exception If activation fails
     */
    void activateDrugImporter(String token) throws Exception;
}