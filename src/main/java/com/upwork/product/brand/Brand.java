package com.upwork.product.brand;

import com.upwork.product.product.Product;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "brands")
@Data
public class Brand {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
        name = "UUID",
        strategy = "uuid"
    )
    private String id;

    @Column(name = "name", nullable = false)
    private String name;

    @OneToMany(targetEntity = Product.class, mappedBy = "brand", fetch = FetchType.LAZY)
    private Set<Product> products;

}
