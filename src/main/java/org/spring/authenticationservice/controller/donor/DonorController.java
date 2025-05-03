package org.spring.authenticationservice.controller.donor;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.spring.authenticationservice.DTO.api.ApiResponse;
import org.spring.authenticationservice.DTO.donor.DonorRegDto;
import org.spring.authenticationservice.Service.donor.DonorService;
import org.spring.authenticationservice.Utils.SecurityUtil;
import org.spring.authenticationservice.model.donor.Donor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@RestController
@RequestMapping("/donor")
public class DonorController {

    private final DonorService donorService;
    private final SecurityUtil securityUtil;

    //create donor
    @PostMapping("")
    public ResponseEntity<ApiResponse<?>> createDonor(@Valid @RequestBody DonorRegDto dto) throws Exception {
        Donor createdDonor = donorService.createDonor(dto);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.builder()
                        .timestamp(LocalDateTime.now())
                        .status(HttpStatus.CREATED.value())
                        .message("Donor Created Successfully")
                        .data(createdDonor)
                        .build());
    }

    //update donor profile
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> updateDonor(@Valid @RequestBody DonorRegDto dto,
                                                      @PathVariable Long id) {
        DonorRegDto updatedDonor = donorService.updateDonor(dto,id);

        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.builder()
                        .timestamp(LocalDateTime.now())
                        .status(HttpStatus.OK.value())
                        .message("Donor Updated Successfully")
                        .data(updatedDonor)
                        .build());
    }


    //delete donor profile
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> deleteDonor(@PathVariable Long id) {
        boolean isDeleted = donorService.deleteDonor(id);

        if (isDeleted) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(ApiResponse.builder()
                            .timestamp(LocalDateTime.now())
                            .status(HttpStatus.OK.value())
                            .message("Donor Deleted Successfully")
                            .build());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.builder()
                            .timestamp(LocalDateTime.now())
                            .status(HttpStatus.NOT_FOUND.value())
                            .message("Donor Not Found")
                            .build());
        }

    }

    //get donor profile
    @GetMapping("")
    public ResponseEntity<ApiResponse<?>> getDonor() {
        System.out.println(securityUtil.getUsername());
        Donor donor = donorService.getDonor();

        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.builder()
                        .timestamp(LocalDateTime.now())
                        .status(HttpStatus.OK.value())
                        .message("Donor Fetched Successfully")
                        .data(donor)
                        .build());
    }

    //description funcion




}
