package com.upwork.product.country;

import com.upwork.product.product.Product;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "countries")
@Data
public class Country {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
        name = "UUID",
        strategy = "uuid"
    )
    private String id;

    @Column(name = "name", nullable = false)
    private String name;

    @OneToMany(targetEntity = Product.class, mappedBy = "country", fetch = FetchType.LAZY)
    private Set<Product> products;

}
