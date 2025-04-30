package org.spring.authenticationservice.model.donor;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.spring.authenticationservice.model.patient.DonationRequest;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "donation")
public class Donation {

    @Id
    private Long id;

    @ManyToOne
    @JoinColumn(name = "donor_id", nullable = false)
    private Donor donor;

    @ManyToOne
    @JoinColumn(name = "donation_request_id", nullable = false)
    private DonationRequest donationRequest;

    private String donationStatus;

    private LocalDateTime donationDate;

    private Double donationAmount;

}
