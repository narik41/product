package com.upwork.product.product;

import com.upwork.product.brand.Brand;
import com.upwork.product.category.ProductCategory;
import com.upwork.product.country.Country;
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
    @Column(name = "id", unique = true, nullable = false)
    private String id;

    @Column(name = "name", nullable = false)
    private String name;
    private String description;
    private String image;

    @Column(name = "available_items")
    private Long availableItems;

    @Column(name = "price")
    private Double price;

    @ManyToOne
    @JoinColumn(name = "product_category_id")
    private ProductCategory productCategory;

    @ManyToOne
    @JoinColumn(name = "brand_id")
    private Brand brand;

    @ManyToOne
    @JoinColumn(name = "country_id")
    private Country country;

    @Column(name = "created_at", updatable = false)
    private Long createdAt ;

    @Column(name = "updated_at")
    private Long updatedAt;
}
