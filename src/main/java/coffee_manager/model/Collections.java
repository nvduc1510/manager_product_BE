package coffee_manager.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "collections")
public class Collections {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "collection_id")
    private Integer collectionId;

    @Column(name = "collection_name")
    private String collectionName;

    @Column(name = "collection_description")
    private String collectionDescription;

    @OneToMany
    @Column(name = "product_id")
    private List<Product> products;
}

