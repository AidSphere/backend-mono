package org.spring.authenticationservice.controller;


import org.spring.authenticationservice.DTO.UserSurveyRequestDTO;
import org.spring.authenticationservice.Service.DonorService;
import org.spring.authenticationservice.model.donor.Donation;
import org.spring.authenticationservice.model.donor.Donor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("api/donors")
public class DonorController {

    @Autowired
    private DonorService donorService;

    @PostMapping("/register")
    public ResponseEntity<Donor> registerDonor(@RequestBody Donor donor) {
        Donor d = donorService.registerDonor(donor);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    @PostMapping("/survey/{id}")
    public ResponseEntity<?> surveyDonor( @PathVariable long id,@RequestBody UserSurveyRequestDTO surveyDTO) {
        Donor updateDonor=donorService.saveDescriptionForDonor(id,surveyDTO);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @PostMapping("/donations")
    public ResponseEntity<?> addDonation(@RequestBody Donation donation) {
        Donation donation1 =donorService.saveDonationDetails(donation);
        return new ResponseEntity<>(donation1,HttpStatus.CREATED);
    }

    @GetMapping("/donations/{donorId}")
    public ResponseEntity<?> getDonationHistory(@PathVariable Long donorId) {
        try {
            List<Donation> donations = donorService.getDonationHistory(donorId);

            // If no donations found, return a 404 Not Found
            if (donations.isEmpty()) {
                return new ResponseEntity<>("No donations found for donor ID: " + donorId, HttpStatus.NOT_FOUND);
            }

            // Return the donations with a 200 OK status
            return ResponseEntity.ok(donations);

        } catch (RuntimeException e) {
            // Handle specific exception (e.g., no donation history found)
            return new ResponseEntity<>("Error: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            // Handle any other unexpected exceptions
            return new ResponseEntity<>("Internal server error: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }




}
