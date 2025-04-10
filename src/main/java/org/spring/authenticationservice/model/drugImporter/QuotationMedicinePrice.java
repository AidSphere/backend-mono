package org.spring.authenticationservice.model.drugImporter;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "quotation_medicine_prices")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuotationMedicinePrice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "quotation_id", nullable = false)
    private Quotation quotation;

    @Column(name = "medicine_id", nullable = false)
    private Long medicineId;

    @Column(name = "price", nullable = false)
    private Double price;
}
