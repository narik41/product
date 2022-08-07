package com.upwork.product.product;

import com.upwork.product.brand.Brand;
import com.upwork.product.brand.BrandRepository;
import com.upwork.product.category.ProductCategory;
import com.upwork.product.category.ProductCategoryRepository;
import com.upwork.product.country.Country;
import com.upwork.product.country.CountryRepository;
import com.upwork.product.helper.FileUploadHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductCategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final CountryRepository countryRepository;
    private final BrandRepository brandRepository;
    private final FileUploadHelper fileUploadHelper;

    @Autowired
    public ProductService(ProductCategoryRepository categoryRepository,
                          ProductRepository productRepository,
                          CountryRepository countryRepository,
                          BrandRepository brandRepository,
                          FileUploadHelper fileUploadHelper) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
        this.brandRepository = brandRepository;
        this.countryRepository = countryRepository;
        this.fileUploadHelper = fileUploadHelper;
    }

    public List<ProductCategory> getProductCategories() {
        return categoryRepository.findAll();
    }

    public List<Country> getCountries() {
        return countryRepository.findAll();
    }

    public List<Brand> getBrands() {
        return brandRepository.findAll();
    }

    public Page<Product> getAll(Integer page, Integer size, String name) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id"));
        return productRepository.findByNameIgnoreCaseContaining(name, pageable);

    }

    public void save(CreateProductRequest createProductRequest) {

        ProductCategory productCategory = new ProductCategory();
        productCategory.setId(createProductRequest.getProductCategory());

        Brand brand = new Brand();
        brand.setId(createProductRequest.getBrand());

        Country country = new Country();
        country.setId(createProductRequest.getMadeIn());

        Product product = new Product();
        product.setName(createProductRequest.getName());
        product.setDescription(createProductRequest.getDescription());
        product.setProductCategory(productCategory);
        product.setBrand(brand);
        product.setCountry(country);
        product.setAvailableItems(createProductRequest.getAvailableItems());
        product.setPrice(createProductRequest.getPrice());
        product.setCreatedAt(System.currentTimeMillis());
        product.setUpdatedAt(System.currentTimeMillis());

        if (!createProductRequest.getImage().isEmpty()) {
            String filepath = fileUploadHelper.uploadFile(createProductRequest.getImage());
            product.setImage(filepath);
        }

        productRepository.save(product);
    }

    public void update(String productId, CreateProductRequest createProductRequest) {

        Product product = getById(productId);

        if (!createProductRequest.getImage().isEmpty()) {
            String filepath = fileUploadHelper.uploadFile(createProductRequest.getImage());
            product.setImage(filepath);
        }

        ProductCategory productCategory = new ProductCategory();
        productCategory.setId(createProductRequest.getProductCategory());

        Brand brand = new Brand();
        brand.setId(createProductRequest.getBrand());

        Country country = new Country();
        country.setId(createProductRequest.getMadeIn());

        product.setName(createProductRequest.getName());
        product.setDescription(createProductRequest.getDescription());
        product.setProductCategory(productCategory);
        product.setBrand(brand);
        product.setCountry(country);
        product.setAvailableItems(createProductRequest.getAvailableItems());
        product.setPrice(createProductRequest.getPrice());
        product.setUpdatedAt(System.currentTimeMillis());
        productRepository.save(product);
    }

    /**
     * Get the product details by id
     *
     * @param productId
     * @return
     */
    Product getById(String productId) {
        return productRepository.findById(productId)
                .orElse(new Product());

    }

    /**
     * Delete the product details and the image of the product
     *
     * @param productId product id
     * @return
     */
    boolean delete(String productId) {
        Product byId = getById(productId);
        if (byId.getId() != null) {
            productRepository.delete(byId);
            this.fileUploadHelper.deleteFile(byId.getImage());
            return true;
        }
        return false;
    }

    CreateProductRequest getProductForEdit(String productId) {
        Product byId = this.getById(productId);
        CreateProductRequest productRequest = new CreateProductRequest();
        if (byId.getName() != null) {
            productRequest.setName(byId.getName());
            productRequest.setDescription(byId.getDescription());
            productRequest.setBrand(byId.getBrand().getId());
            productRequest.setProductCategory(byId.getProductCategory().getId());
            productRequest.setMadeIn(byId.getCountry().getId());
            productRequest.setPrice(byId.getPrice());
            productRequest.setAvailableItems(byId.getAvailableItems());
        }
        return productRequest;
    }
}
