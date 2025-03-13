package org.spring.authenticationservice.controller;


import org.spring.authenticationservice.DTO.UserSurveyRequestDTO;
import org.spring.authenticationservice.Service.DonorService;
import org.spring.authenticationservice.model.donor.Donor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


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

}
