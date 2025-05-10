package org.spring.authenticationservice.model.drugImporter;


import jakarta.persistence.*;
import lombok.*;
import org.spring.authenticationservice.model.security.User;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "drug_importers")
public class DrugImporter {

    @Id
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false)
    private String name;

    @Column
    private String phone;

    @Column
    private String address;

    @Column(nullable = false, unique = true)
    private String licenseNumber;

    @Column(length = 255)
    private String nicotineProofFilePath;

    @Column(length = 255)
    private String licenseProofFilePath;

    @Column
    private String website;

    @Column
    private String nic;

    @Column(length = 1000)
    private String additionalText;



    // Constructor with user
    public DrugImporter(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "DrugImporter{" +
                "id=" + id +
                ", user=" + (user != null ? user.getEmail() : "null") +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                ", licenseNumber='" + licenseNumber + '\'' +
                ", website='" + website + '\'' +
                ", nic='" + nic + '\'' +
                '}';
    }
}
