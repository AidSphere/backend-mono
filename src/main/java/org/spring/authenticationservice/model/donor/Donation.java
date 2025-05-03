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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "donor_id", nullable = false)
    private Donor donor;

    @ManyToOne
    @JoinColumn(name = "donation_request_id", nullable = false)
    private DonationRequest donationRequest;

    @Column(name = "donation_message")
    private String donationMessage;

    @Column(name = "donation_message_visibility")
    private Boolean donationMessageVisibility;


    private Boolean donationStatus = true;

    private LocalDateTime donationDate = LocalDateTime.now();

    private Double donationAmount;


}
