package org.spring.authenticationservice.model.patient;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PrescribedMedicine {
    private String medicine;
    private String amount;
    private String unit;
}
