package com.upwork.product.product;

import com.upwork.product.category.ProductCategory;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "products")
@Data
public class Product {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "uuid"
    )
    @Column(name = "id", columnDefinition = "CHAR(36)", unique = true, nullable = false)
    private String id;

    @Column(name = "name", nullable = false)
    private String name;
    private String description;

    @ManyToOne
    @JoinColumn(name = "product_category_id", nullable = false)
    private ProductCategory productCategory;
}
