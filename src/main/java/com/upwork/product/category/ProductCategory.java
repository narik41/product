package com.upwork.product.category;

import com.upwork.product.product.Product;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "product_categories")
@Data
public class ProductCategory {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
        name = "UUID",
        strategy = "uuid"
    )
    private String id;

    @Column(name = "name", nullable = false)
    private String name;
    private String description;

    @OneToMany(mappedBy = "productCategory")
    private Set<Product> products;

}
