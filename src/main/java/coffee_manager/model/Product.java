package coffee_manager.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id", unique = true, nullable = false)
    private Integer productId;

    @Column(name = "product_name")
    private String product_name;

    @Column(name = "product_price")
    private Integer productPrice;

    @Column(name = "product_image")
    private String productImage;

}
