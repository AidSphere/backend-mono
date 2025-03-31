package org.spring.authenticationservice.model.Enum;

public enum MedicalDocumentType {

    PRESCRIPTION,         // Doctor's prescription for medicines
    DIAGNOSIS_REPORT,     // Cancer diagnosis report
    BIOPSY_REPORT,        // Biopsy test results
    SCAN_REPORT,          // MRI, CT, PET, or X-ray scans
    BLOOD_TEST_REPORT,    // Blood test results
    TREATMENT_PLAN,       // Oncologist's treatment plan
    CHEMOTHERAPY_REPORT,  // Chemotherapy session details
    RADIOTHERAPY_REPORT,  // Radiotherapy session details
    SURGERY_REPORT,       // Surgery reports and recommendations
    IMMUNOTHERAPY_REPORT, // Immunotherapy treatment details
    INSURANCE_DOCUMENT,   // Insurance-related documents
    ID_PROOF,             // Patient ID proof (NIC, Passport, etc.)
    BIRTH_CERTIFICATE,    // For child patients
    OTHER                 // Any other relevant document
}
