package coffee_manager.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "detail_selling")
public class DetailSelling {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "selling_id")
    private Integer sellingId;

    @Column(name = "quantity", nullable = false)
    private int quantity;

    @Column(name = "total_amount", nullable = false)
    private int totalAmount;

    @ManyToOne
    @JoinColumn(name = "bill_id", nullable = false)
    private Bill bill;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;
}
