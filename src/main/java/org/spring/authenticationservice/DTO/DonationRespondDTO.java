package org.spring.authenticationservice.DTO;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import java.util.Date;

@Getter
@Setter
public class DonationRespondDTO {

    private Long patientId;

    private Date createdAt;

    private String prescriptionLink;

    private String hospitalName;

    private String messageToPatient;

    public DonationRespondDTO(Long patientId, Date createdAt, String prescriptionLink, String hospitalName, String messageToPatient) {
        this.patientId = patientId;
        this.createdAt = createdAt;
        this.prescriptionLink = prescriptionLink;
        this.hospitalName = hospitalName;
        this.massageToPatient = massageToPatient;
    }


}
