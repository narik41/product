package com.upwork.product.product;

import com.upwork.product.category.ProductCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@Controller
public class ProductController {

    private static final String TITLE = "title";
    private static final String SUCCESS_MESSAGE = "success_message";
    private static final String ERROR_MESSAGE = "error_message";
    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping(value = "/product", name = "index-product")
    public ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView("product/index.html");
        modelAndView.addObject("products", productService.getProducts());
        modelAndView.addObject(TITLE, "Product List");
        return modelAndView;
    }

    @GetMapping(value = "/product/create", name = "create-product")
    public ModelAndView create(CreateProductRequest productRequest) {
        List<ProductCategory> productCategories = productService.getProductCategories();
        ModelAndView modelAndView = new ModelAndView("product/create.html");
        modelAndView.addObject("productCategories", productCategories);
        modelAndView.addObject("product", productRequest);
        modelAndView.addObject("brands", productService.getBrands());
        modelAndView.addObject("countries", productService.getCountries());
        modelAndView.addObject(TITLE, "Create Product");
        return modelAndView;
    }

    @PostMapping(value = "/product", name = "store-product")
    public ModelAndView store(@Valid @ModelAttribute("product") CreateProductRequest product,
                              BindingResult result,
                              RedirectAttributes ra) {

        if (result.hasErrors()) {
            List<ProductCategory> productCategories = productService.getProductCategories();
            ModelAndView modelAndView = new ModelAndView("product/create.html");
            modelAndView.addObject("productCategories", productCategories);
            modelAndView.addObject("product", product);
            modelAndView.addObject(TITLE, "Create Product");
            return modelAndView;
        }


        this.productService.save(product);
        ra.addFlashAttribute(SUCCESS_MESSAGE, Messages.SUCCESS_CREATE_PRODUCT);
        return new ModelAndView("redirect:/product");
    }

    @GetMapping(value = "/product/{id}", name = "view-product")
    public ModelAndView view(@PathVariable("id") String productId) {
        Product byId = productService.getById(productId);

        ModelAndView modelAndView = new ModelAndView("product/view.html");
        modelAndView.addObject("product", byId);
        modelAndView.addObject(TITLE, "Product Details");
        return modelAndView;
    }

    @GetMapping(value = "/product/{id}/edit", name = "edit-product")
    public ModelAndView edit(@PathVariable("id") String productId,
                             CreateProductRequest productRequest) {
        Product byId = productService.getById(productId);
        productRequest.setName(byId.getName());
        productRequest.setDescription(byId.getDescription());
        productRequest.setBrand(byId.getBrand().getId());
        productRequest.setProductCategory(byId.getProductCategory().getId());
        productRequest.setMadeIn(byId.getCountry().getId());
        productRequest.setPrice(byId.getPrice());
        productRequest.setAvailableItems(byId.getAvailableItems());

        List<ProductCategory> productCategories = productService.getProductCategories();
        ModelAndView modelAndView = new ModelAndView("product/edit.html");
        modelAndView.addObject("productCategories", productCategories);
        modelAndView.addObject("brands", productService.getBrands());
        modelAndView.addObject("countries", productService.getCountries());
        modelAndView.addObject("product", productRequest);
        modelAndView.addObject("id", productId);
        modelAndView.addObject(TITLE, "Update Product");
        return modelAndView;
    }

    @PutMapping(value = "/product/{id}/update", name = "update-product")
    public ModelAndView update(@PathVariable("id") String productId,
                               @Valid @ModelAttribute("product") CreateProductRequest product,
                               BindingResult result,
                               RedirectAttributes ra) {

        if (result.hasErrors()) {
            List<ProductCategory> productCategories = productService.getProductCategories();
            ModelAndView modelAndView = new ModelAndView("product/edit.html");
            modelAndView.addObject("productCategories", productCategories);
            modelAndView.addObject("brands", productService.getBrands());
            modelAndView.addObject("countries", productService.getCountries());
            modelAndView.addObject("product", product);
            modelAndView.addObject("id", productId);
            modelAndView.addObject(TITLE, "Update Product");
            return modelAndView;
        }

        this.productService.update(productId, product);
        ra.addFlashAttribute(SUCCESS_MESSAGE, Messages.SUCCESS_UPDATE_PRODUCT);
        return new ModelAndView("redirect:/product");
    }

    @DeleteMapping(value = "/product/{id}/delete", name = "delete-product")
    public ModelAndView delete(@PathVariable("id") String productId, RedirectAttributes ra) {
        boolean delete = productService.delete(productId);
        if(delete){
            ra.addFlashAttribute(SUCCESS_MESSAGE, Messages.SUCCESS_DELETED_PRODUCT);
        }else{
            ra.addFlashAttribute(ERROR_MESSAGE, Messages.FAIL_PRODUCT_DELETE);
        }

        return new ModelAndView("redirect:/product");
    }
}
