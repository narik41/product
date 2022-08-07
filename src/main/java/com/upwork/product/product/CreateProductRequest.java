package com.upwork.product.product;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class CreateProductRequest {

    @NotBlank(message = "Invalid product name")
    @Size(min = 2, max = 255, message = "Name must be of 2-255 char!!")
    private String name;

    @NotBlank(message = "Please select a product category")
    private String productCategory;

    @NotBlank(message = "Please select a brand")
    private String brand;

    @NotBlank(message = "Please select where the product is made in")
    private String madeIn;

    @NotNull(message = "Please input available items")
    @Min(value = 1, message = "Please input available items")
    private long availableItems;

    @NotNull(message = "Please provide the price of the product")
    @Min(value = 1, message = "Please provide the price of the item")
    private double price;

    private String description;

    private MultipartFile image;
}
