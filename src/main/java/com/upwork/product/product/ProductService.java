package com.upwork.product.product;

import com.upwork.product.category.ProductCategory;
import com.upwork.product.category.ProductCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductCategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductCategoryRepository categoryRepository,
                          ProductRepository productRepository) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
    }

    public List<ProductCategory> getProductCategories() {
        return categoryRepository.findAll();
    }

    public List<Product> getProducts(){
        return productRepository.findAll();
    }

    public void save(CreateProductRequest createProductRequest) {

        ProductCategory productCategory = new ProductCategory();
        productCategory.setId(createProductRequest.getProductCategory());

        Product product = new Product();
        product.setName(createProductRequest.getName());
        product.setDescription(createProductRequest.getDescription());
        product.setProductCategory(productCategory);

        productRepository.save(product);
    }

    Product getById(String productId){
        Optional<Product> byId = productRepository.findById(productId);
        if(byId.isEmpty()){
            return byId.orElse(new Product());
        }

        return byId.get();
    }

    void delete(String productId){
        Product byId = getById(productId);
        if(byId.getId() != null){
            productRepository.delete(byId);
        }
    }
}
