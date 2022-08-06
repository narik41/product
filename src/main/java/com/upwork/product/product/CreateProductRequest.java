package com.upwork.product.product;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class CreateProductRequest {

    @NotBlank(message = "Invalid product name")
    @Size(min = 2, max = 255)
    private String name;

    @NotBlank(message = "Please select a product category")
    private String productCategory;

    private String description ;
}
